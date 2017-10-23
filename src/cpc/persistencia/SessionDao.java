package cpc.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.DriverManagerConnectionProvider;
import org.hibernate.jdbc.Work;

public class SessionDao {
	private static SessionDao INSTANCE = null;
	private static SessionFactory sessionFactory = null;
	private static Configuration configuration = null;
	private static Session session = null;

	private SessionDao() {
		if(configuration == null){
			configuration = new AnnotationConfiguration().configure();
		}
		sessionFactory = SessionDao.configuration.buildSessionFactory();
	}

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SessionDao();
		}
	}

	public static SessionDao getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	public static void configurarNuevaSessionFactory(Configuration cfg) throws Exception  {
		if(INSTANCE == null){
			try {
				configuration = cfg; //cfg.setInterceptor(new Interceptor());
				sessionFactory = configuration.buildSessionFactory();
				session = sessionFactory.openSession();
				probarConexion();
			} catch (Exception e) {
				INSTANCE = null;
				sessionFactory = null;
				configuration = null;
				session = null;
				e.printStackTrace();
				throw new Exception("Error al intentar conectar a la base de datos "+cfg.getProperty("hibernate.connection.url"));
			}
		}
	}
	
	public static void probarConexion(){
		Session em = getInstance().getCurrentSession();
		Transaction t = em.beginTransaction();
		em.createSQLQuery("select 1").uniqueResult();
		t.commit();
	}
	public Session openSession() {
		return sessionFactory.openSession();
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public static void close() {
		if (sessionFactory != null)
			sessionFactory.close();
		sessionFactory = null;
	}

	public static Connection getconnecion() throws SQLException {
		Properties dbcpProperties = new AnnotationConfiguration().configure()
				.getProperties();
		DriverManagerConnectionProvider driver = new DriverManagerConnectionProvider();
		driver.configure(dbcpProperties);
		return driver.getConnection();
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {
		SessionDao.configuration = configuration;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}