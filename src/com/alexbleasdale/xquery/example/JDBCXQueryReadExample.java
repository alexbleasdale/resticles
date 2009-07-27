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

public class JDBCXQueryReadExample {

	private static final Log LOG = LogFactory
			.getLog(JDBCXQueryReadExample.class);

	/*
	 * 
	 * public ArrayList<Dealer> getSelectedDealers(String country, String city)
	 * throws SQLException, IsoCountryCodeError { ArrayList<Dealer> al = new
	 * ArrayList<Dealer>(); Connection conn = datasource.getConnection();
	 * PreparedStatement sql = null; ResultSet rs = null; try { sql =
	 * getCountryQuery(conn, country, city); rs = sql.executeQuery(); al =
	 * parseDealerList(rs, al, country); } finally {
	 * JDBCUtil.silentlyClose(conn, sql, rs); } return al; }
	 */

	public static void main(String[] args) throws SQLException, IOException,
			ParsingException {
		// Connection con =
		// DriverManager.getConnection("jdbc:sqlserver://;integratedSecurity=true;",
		// pro);

		XmlPrettyPrinter xpp = new XmlPrettyPrinter();

		Connection conn = DriverManager
				.getConnection("jdbc:sqlserver://192.168.0.196:1433;databaseName=hib-test;user=db_user;password=s3cr3t;");// datasource.getConnection();
		PreparedStatement sql = null;
		ResultSet rs = null;
		try {
			sql = getBasicQuery(conn);
			// getCountryQuery(conn, country, city);

			rs = sql.executeQuery();
			while (rs.next()) {
				LOG.info(rs.getObject(1).getClass());
				LOG.info(rs.getObject(1));
				System.out.println(xpp.xomXmlPrettyPrint(rs.getObject(1)
						.toString()));
				// LOG.info(rs.getInt("ID"));
				// LOG.info(rs.getSQLXML("customerDocs"));
				// LOG.info(rs.getDate("updated"));
				LOG.info("stuff");
			}
		} finally {
			JDBCUtil.silentlyClose(conn, sql, rs);
		}
	}

	public static PreparedStatement getBasicQuery(Connection c)
			throws IOException, SQLException {
		StringBuffer query = new StringBuffer();
		// query
		// .append("declare @results xml SELECT @results = customerDocs.query('");
		query.append("SELECT customerDocs.query('");
		query.append(StringUtils.getStringFromFilename(
				"xq/get-customer-orders.xq", "UTF-8"));
		query
				.append("') FROM xmlData FOR XML RAW('Order'), ROOT ('OrderCollection')");

		// query.append("') FROM xmlData select @results as xmlResultSet");
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!!
	// CRAP below...
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static PreparedStatement getBasicQuery2(Connection c)
			throws SQLException, IOException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT customerDocs.query('");
		// manually add the xq in
		// query
		// .append("<CustomerOrders> {for $i in //customer let $name := concat($i/@FirstName, \" \", $i/@LastName) order by $i/@LastName return <Customer Name=\"{$name}\">{$i/order}</Customer>}</CustomerOrders>");

		// auto-include the xq this time
		query.append(StringUtils.getStringFromFilename(
				"xq/get-customer-orders.xq", "UTF-8"));

		// query.append("for $i in //customer ");
		// query
		// .append("let $name := concat($i/@FirstName, \" \", $i/@LastName) ");
		// query.append("order by $i/@LastName ");
		// query.append("return ");
		// query.append("<Customer Name=\"{$name}\">{$i/order}</Customer>");
		// query.append("</CustomerOrders>')}");
		query.append("') FROM customerData");
		PreparedStatement s = c.prepareStatement(query.toString());
		return s;
	}

}
