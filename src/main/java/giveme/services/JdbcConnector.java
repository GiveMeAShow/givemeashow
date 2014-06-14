package giveme.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JdbcConnector {
	private static String PASSWORD;
	private static String USERNAME;
	public static String dbUrl = null;
	public static Logger LOGGER = Logger.getLogger(JdbcConnector.class
			.getName());

	private static Connection connection = null;

	public static Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (dbUrl == null) {
				Properties props = new Properties();
				props.load(JdbcConnector.class.getClassLoader()
						.getResourceAsStream("givemeashow.properties"));
				dbUrl = props.getProperty("dbUrl");
				USERNAME = props.getProperty("dbUser");
				PASSWORD = props.getProperty("dbPwd");
			}
			connection = DriverManager.getConnection(dbUrl, USERNAME, PASSWORD);
			return connection;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setDbUrl(String url) {
		dbUrl = url;
	}
}
