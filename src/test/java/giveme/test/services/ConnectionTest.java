package giveme.test.services;

import java.sql.Connection;

import giveme.services.JdbcConnector;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ConnectionTest {

	public static Logger LOGGER = Logger.getLogger(ConnectionTest.class
	        .getName());
	
	@Test
	public void testConnection()
	{
		LOGGER.info("Testing database connection");
		Connection con = JdbcConnector.getConnection();
		LOGGER.info("Success!");
	}
	
}
