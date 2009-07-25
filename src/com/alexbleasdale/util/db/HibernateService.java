package com.alexbleasdale.util.db;
import org.hibernate.SessionFactory;

/**
 * An abstract class that provides basic methods for implementing Hibernate.
 */
public abstract class HibernateService {

	private SessionFactory sf;

	public HibernateService() {
		super();
	}

	public void setSessionFactory(SessionFactory factory) {
		sf = factory;
	}

	protected SessionFactory getSessionFactory() {
		if (sf == null)
			throw new IllegalStateException(
					"Please pass a SessionFactory by way of setSessionFactory.");
		return sf;
	}

}
/*
 * package com.olson.www.service.hibernate;
 * 
 * import java.util.List;
 * 
 * 
 * import com.olson.www.bean.Person; //import
 * com.olson.www.service.PersonService;
 * 
 * 
 * public class HibernatePersonService extends HibernateService //implements
 * PersonService {
 * 
 * public HibernatePersonService() {}
 * 
 * 
 * public void savePerson(Person p) {
 * getSessionFactory().getCurrentSession().save(p); }
 * 
 * 
 * public void deleteAllPeople() { System.out.println("before delete / get.");
 * List<Person> all = getAllPeople(); System.out.println("after delete / get = "
 * + all.size()); for(Person p : all ){ System.out.println("before delete " +
 * p.getName()); getSessionFactory().getCurrentSession().delete(p);
 * System.out.println("after delete"); } }
 * 
 * 
 * @SuppressWarnings("unchecked") public List<Person> getAllPeople() {
 * List<Person> people =
 * getSessionFactory().getCurrentSession().createQuery("from Person").list();
 * return people; }
 * 
 * //@Override public void savePeople(List<Person> people) { for( Person p :
 * people ) getSessionFactory().getCurrentSession().save(p); }
 * 
 * }
 */

/*
 * package com.olson.www.service.hibernate;
 * 
 * import java.io.OutputStreamWriter; import java.util.Calendar; import
 * java.util.HashSet; import java.util.List; import java.util.UUID;
 * 
 * import org.apache.log4j.ConsoleAppender; import org.apache.log4j.Level;
 * import org.apache.log4j.Logger; import org.apache.log4j.SimpleLayout; import
 * org.hibernate.SessionFactory; import
 * org.hibernate.cfg.AnnotationConfiguration;
 * 
 * import com.olson.www.bean.Person; import
 * com.olson.www.service.hibernate.HibernatePersonService;
 * 
 * import org.testng.Assert; import org.testng.annotations.AfterClass; import
 * org.testng.annotations.BeforeClass; import org.testng.annotations.Test;
 * 
 * public class HibernateDatabaseConnectionTest { private SessionFactory sf;
 * 
 * * This test begins & commits (or rolls back) the transaction per 'Transaction
 * demarcation with plain JDBC' method on http://www.hibernate.org/42.html. This
 * is how the TESTS run. In production the app server and/or bean will do this
 * (probably using the 'Transaction demarcation with JTA' method)
 * 
 * The main point being that the SERVICE layer code doesn't do it - ever.
 * 
 * !!!!!!!!!!!!!!!! REFACTOR THE HIBERNATE STUFF OUT OF HERE into a common
 * hibernate test class... !!!!!!!!!!!!!!!!
 * 
 * @throws Exception
 * 
 * @Test public void testIt() throws Exception { HashSet<String> names = new
 * HashSet<String>();
 * 
 * HibernatePersonService svc = new HibernatePersonService();
 * svc.setSessionFactory(sf);
 * 
 * sf.getCurrentSession().beginTransaction();
 * 
 * try{ svc.deleteAllPeople();
 * 
 * List<Person> before = svc.getAllPeople();
 * Assert.assertEquals(0,before.size());
 * 
 * Calendar cal = Calendar.getInstance(); for( int i = 1; i <= 10; i++){ String
 * name = UUID.randomUUID().toString(); names.add(name); Person p = new
 * Person(); p.setName(name); cal.set(2009, Calendar.JANUARY, i);
 * p.setBirthdate(cal.getTime()); svc.savePerson(p); }
 * 
 * List<Person> after = svc.getAllPeople();
 * Assert.assertEquals(10,after.size()); for( Person p : after ){
 * System.out.println(p.getName() + ", " + p.getBirthdate());
 * Assert.assertTrue(names.contains(p.getName())); // remove, to make sure we
 * have unique matches. names.remove(p.getName()); }
 * 
 * sf.getCurrentSession().getTransaction().commit(); } catch (Exception e){
 * sf.getCurrentSession().getTransaction().rollback(); } }
 * 
 * @BeforeClass protected void setUp() throws Exception {
 * Logger.getLogger("org").setLevel(Level.DEBUG); ConsoleAppender a = new
 * ConsoleAppender(); a.setWriter(new OutputStreamWriter(System.out));
 * a.setLayout(new SimpleLayout()); Logger.getLogger("org").addAppender(a);
 * 
 * AnnotationConfiguration c = new AnnotationConfiguration();
 * c.addAnnotatedClass(Person.class); c.setProperty("hibernate.connection.url",
 * "jdbc:mysql://localhost/jpa_test");
 * c.setProperty("hibernate.connection.username", "root");
 * c.setProperty("hibernate.hbm2ddl.auto", "create");
 * c.setProperty("hibernate.show_sql", "false");
 * c.setProperty("hibernate.format_sql", "false");
 * c.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
 * c.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
 * c.setProperty("hibernate.current_session_context_class", "thread");
 * c.setProperty("hibernate.transaction.factory_class",
 * "org.hibernate.transaction.JDBCTransactionFactory");
 * c.setProperty("hibernate.connection.pool_size", "1");
 * c.setProperty("cache.provider_class","org.hibernate.cache.NoCacheProvider");
 * c.setProperty("hibernate.bytecode.provider","cglib"); sf =
 * c.buildSessionFactory(); //super.setUp(); }
 * 
 * @AfterClass protected void tearDown() throws Exception { sf.close();
 * 
 * } }
 */