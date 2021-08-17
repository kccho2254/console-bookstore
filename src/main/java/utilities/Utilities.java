package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.services.*;

public class Utilities {

	private Utilities () {}
	private static final String CONNECTION_USERNAME = System.getenv("CONNECTION_USERNAME");
	private static final String CONNECTION_PASSWORD = System.getenv("CONNECTION_PASSWORD");
	private static final String URL = System.getenv("CONNECTION_URL");
	
	private static Connection connection;
	
	public synchronized static Connection connect() throws SQLException {
		
//		String URL =  System.getenv("CONNECTION_URL");

		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		// If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	public static ShopDAO getBookDAO() { 
		return new ShopDAOImpl();
	}
	
	public static CustomerDAO getCustomerDAO() {
		return new CustomerDAOImpl();
	}
}