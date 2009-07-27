package com.alexbleasdale.xmlschema;

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

public class ValidateXMLInstanceAgainstSchema {

	private static final Log LOG = LogFactory
			.getLog(ValidateXMLInstanceAgainstSchema.class);
	public static XmlPrettyPrinter xpp;

	public static void main(String[] args) throws SQLException, IOException,
			ParsingException {

		/*
		 * Create Schema (on first use), if it doesn't exist already
		 */
		Connection conn = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();
		PreparedStatement sql = null;

		try {
			sql = getXmlSchemaCreationQuery(conn);
			// getCountryQuery(conn, country, city);

			int i = sql.executeUpdate();
			LOG.info("SQL Server returned " + i);

		} finally {
			JDBCUtil.silentlyClose(conn, sql);
		}

		/*
		 * Try first with a valid instance
		 */

		Connection conn2 = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();
		ResultSet rs = null;
		try {
			sql = getValidXml(conn2);
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
		 * Now try with an invalid instance
		 */

		Connection conn3 = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();
		ResultSet rs2 = null;
		try {
			sql = getInvalidXml(conn3);
			// getCountryQuery(conn, country, city);

			rs2 = sql.executeQuery();
			while (rs2.next()) {
				LOG.info("We should not get to this without an exception");
			}

		} finally {
			JDBCUtil.silentlyClose(conn, sql);
		}
	}

	public static PreparedStatement getXmlSchemaCreationQuery(Connection c)
			throws IOException, SQLException {
		StringBuffer query = new StringBuffer();
		query.append(StringUtils.getStringFromFilename(
				"sql/register-schema.sql", "UTF-8"));
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

	public static PreparedStatement getValidXml(Connection c)
			throws SQLException, IOException {
		StringBuffer query = new StringBuffer();
		query.append("DECLARE @x XML (dbo.MyNewExampleSchema6);");
		query.append("SET @x = N'");
		query.append(StringUtils.getStringFromFilename(
				"xml/example-valid-xml-instance.xml", "UTF-8"));
		query.append("';");
		query.append("SELECT @x as resultSet");

		// GO")
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

	public static PreparedStatement getInvalidXml(Connection c)
			throws SQLException, IOException {
		StringBuffer query = new StringBuffer();
		query.append("DECLARE @x XML (dbo.MyNewExampleSchema6);");
		query.append("SET @x = N'");
		query.append(StringUtils.getStringFromFilename(
				"xml/example-invalid-xml-instance.xml", "UTF-8"));
		query.append("';");
		query.append("SELECT @x as resultSet");

		// GO")
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

	// query looks a bit like this
	// query framework DECLARE @x XML (dbo.ExampleSchemaCollection4_all);
	// SET @x = N' {xmldoc}
	// ';
	// SELECT @x as resultSet;
	// GO

}
