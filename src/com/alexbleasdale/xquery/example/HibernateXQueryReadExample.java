package com.alexbleasdale.xquery.example;

/**
 * This currently doesn't work because hibernate can't cast unless the resultset comes back with a fixed column name.  SQL Server generates this... 
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.alexbleasdale.util.string.StringUtils;
import com.alexbleasdale.util.xml.XmlPrettyPrinter;

public class HibernateXQueryReadExample {

	// TODO - make this class NOT static
	private static final Log LOG = LogFactory
			.getLog(HibernateXQueryReadExample.class);
	public static XmlPrettyPrinter xpp;

	public static void main(String[] args) throws IOException {
		LOG.debug("Query: " + getBasicQuery());

		if (xpp == null) {
			xpp = new XmlPrettyPrinter();
		}
		Session session = null;

		try {

			SessionFactory sessionFactory = new Configuration().configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			String sql = getBasicQuery();

			// java.sql.Types.REAL = 7.
			// TODO - need to get SQL Server to return with col name
			List<HashMap<?, ?>> results = session.createSQLQuery(sql)
					.addScalar("", Hibernate.STRING).setResultTransformer(
							Criteria.ALIAS_TO_ENTITY_MAP).list();

			System.out.println("******  RESPONSE ******");
			LOG.info("returning " + results.size() + " records");
			for (HashMap<?, ?> h : results) {

				// TODO - get "" because currently we're returning results from
				// an unnamed column
				// TODO - need to try to get SQL Server to return with col name
				String response = (String) h.get("");
				System.out.println(xpp.xomXmlPrettyPrint(response));
				// prints out the three values returned from my HQL query
			}

			session.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getBasicQuery() throws IOException {
		StringBuffer query = new StringBuffer();
		// query
		// .append("declare @results xml SELECT @results = customerDocs.query('");
		query.append("SELECT customerDocs.query('");
		query.append(StringUtils.getStringFromFilename(
				"xq/get-customer-orders.xq", "UTF-8"));
		query.append("') FROM xmlData");

		// query.append("') FROM xmlData select @results as xmlResultSet");
		return query.toString();
	}
}
