package com.alexbleasdale.xquery.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.alexbleasdale.util.string.StringUtils;
import com.alexbleasdale.util.xml.XmlPrettyPrinter;

public class HibernateXQueryReadExample {

	// TODO - make this class NOT static
	public static Logger LOG = Logger.getLogger("TestOne");
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
			List<HashMap<?, ?>> results = session.createSQLQuery(sql)
					.addScalar("xmlResultSet", Hibernate.STRING)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

			System.out.println("******  RESPONSE ******");
			for (HashMap<?, ?> h : results) {
				String response = (String) h.get("xmlResultSet");
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
		query
				.append("declare @results xml SELECT @results = customerDocs.query('");
		query.append(StringUtils.getStringFromFilename(
				"xq/get-customer-orders.xq", "UTF-8"));
		query.append("') FROM customerData select @results as xmlResultSet");
		return query.toString();
	}
}
