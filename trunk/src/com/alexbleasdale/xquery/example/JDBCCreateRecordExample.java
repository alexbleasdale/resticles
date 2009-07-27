package com.alexbleasdale.xquery.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import nu.xom.ParsingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alexbleasdale.util.db.JDBCUtil;
import com.alexbleasdale.util.string.StringUtils;
import com.alexbleasdale.util.xml.XmlPrettyPrinter;

public class JDBCCreateRecordExample {

	private static final Log LOG = LogFactory
			.getLog(JDBCCreateRecordExample.class);
	public static XmlPrettyPrinter xpp;

	public static void main(String[] args) throws SQLException, IOException,
			ParsingException {
		// Connection con =
		// DriverManager.getConnection("jdbc:sqlserver://;integratedSecurity=true;",
		// pro);

		if (xpp == null) {
			xpp = new XmlPrettyPrinter();
		}

		Connection conn = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();
		PreparedStatement sql = null;

		try {
			sql = getBasicQuery(conn);
			// getCountryQuery(conn, country, city);

			int i = sql.executeUpdate();
			LOG.info("SQL Server returned " + i);

		} finally {
			JDBCUtil.silentlyClose(conn, sql);
		}
	}

	public static PreparedStatement getBasicQuery(Connection c)
			throws IOException, SQLException {
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO xmlData(customerDocs) VALUES(N'");
		query.append(StringUtils.getStringFromFilename(
				"xml/example-record.xml", "UTF-8"));
		query.append("')");
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

}
