package com.alexbleasdale.xquery.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nu.xom.ParsingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alexbleasdale.util.db.JDBCUtil;
import com.alexbleasdale.util.string.StringUtils;
import com.alexbleasdale.util.xml.XmlPrettyPrinter;

public class JDBCXQueryUpdateRecordExample {

	private static final Log LOG = LogFactory
			.getLog(JDBCXQueryUpdateRecordExample.class);

	public static void main(String[] args) throws SQLException, IOException,
			ParsingException {

		Connection conn = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();
		PreparedStatement sql = null;
		ResultSet rs = null;

		/*
		 * Update a single attribute with the first query
		 */
		try {
			sql = getUpdateAttributeQuery(conn);
			// getCountryQuery(conn, country, city);

			rs = sql.executeQuery();
			while (rs.next()) {
				LOG.info(rs.getObject(1).getClass());
				LOG.info(rs.getObject(1));
				System.out.println(XmlPrettyPrinter.xomXmlPrettyPrint(rs
						.getObject(1).toString()));
			}

		} finally {
			JDBCUtil.silentlyClose(conn, sql);
		}

		/*
		 * Update an element value with the second query
		 */

		Connection conn2 = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();

		ResultSet rs2 = null;

		try {
			sql = getUpdateElementQuery(conn2);
			// getCountryQuery(conn, country, city);

			rs2 = sql.executeQuery();
			while (rs2.next()) {
				LOG.info(rs2.getObject(1).getClass());
				LOG.info(rs2.getObject(1));
				System.out.println(XmlPrettyPrinter.xomXmlPrettyPrint(rs2
						.getObject(1).toString()));
			}

		} finally {
			JDBCUtil.silentlyClose(conn2, sql);
		}

		/*
		 * Completely replace the XML document
		 */

		String recordId = "10";
		Connection conn3 = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();

		try {
			sql = getUpdateXMLDocQuery(conn3, recordId);
			// getCountryQuery(conn, country, city);
			int i = sql.executeUpdate();
			LOG.info("Update record " + recordId + " SQL Server returned " + i);

		} finally {
			JDBCUtil.silentlyClose(conn3, sql);
		}

	}

	public static PreparedStatement getUpdateAttributeQuery(Connection c)
			throws IOException, SQLException {
		StringBuffer query = new StringBuffer();
		query
				.append("DECLARE @x xml SELECT @x = customerDocs FROM xmlData WHERE ID = 2; SET @x.modify('");
		query
				.append("replace value of (//customer/@FirstName)[1] with xs:string(\"TEST HERE\")");
		query.append("'); SELECT @x;");
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

	public static PreparedStatement getUpdateElementQuery(Connection c)
			throws IOException, SQLException {
		StringBuffer query = new StringBuffer();
		query
				.append("DECLARE @x xml SELECT @x = customerDocs FROM xmlData WHERE ID = 2; SET @x.modify('");
		query
				.append("replace value of (//order/text())[1] with xs:string(\"New Value here\")");
		query.append("'); SELECT @x;");
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

	public static PreparedStatement getUpdateXMLDocQuery(Connection c, String id)
			throws SQLException, IOException {
		StringBuffer query = new StringBuffer();

		query.append("UPDATE xmlData SET customerDocs = (N'");
		query.append(StringUtils.getStringFromFilename(
				"xml/example-record.xml", "UTF-8"));
		query.append("') WHERE ID = ?");

		PreparedStatement s = c.prepareStatement(query.toString());
		s.setString(1, id);
		return s;

	}

}
