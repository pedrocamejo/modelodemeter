package cpc.persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.DriverManagerConnectionProvider;








public class SessionAux {
	private static SessionAux INSTANCE = null;
	private static SessionFactory sessionFactory;
	private static Configuration configuration;
	private static Session session = null;
	/*private static Logger log = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	Level  INFO;
	root.setLevel(Level.INFO);;*/

	// Private constructor suppresses
	private SessionAux() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciación múltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SessionAux();
		}
	}

	public static SessionAux getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}
	static {

			SessionAux.configuration = new AnnotationConfiguration()
					.configure();
			SessionAux.configuration.setProperty("hibernate.cache.use_second_level_cache","false");
			SessionAux.configuration.setProperty("hibernate.cache.use_query_cache","false");
			
			SessionAux.sessionFactory = SessionAux.configuration
					.buildSessionFactory();
					

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
		SessionAux.configuration = configuration;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}