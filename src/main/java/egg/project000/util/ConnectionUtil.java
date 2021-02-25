package egg.project000.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtil {
private static Connection aConnection= null;
	
	private static final Logger aLogger = LoggerFactory.getLogger(ConnectionUtil.class);
	
	private ConnectionUtil() {
		super();
	}
	
	public static Connection getConnection() {
		try {
			if(aConnection != null && !aConnection.isClosed()) {
				return aConnection;
			}
		} catch (SQLException e) {
			aLogger.error("connection failed", e);
			return null;

		}
		
		
		String url = System.getenv("DB_URL"); //"jdbc:postgresql://us-ohio-1-training.c0dgtxyjm9jm.us-east-2.rds.amazonaws.com:5432/postgres";
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		//String url = "jdbc:postgresql://us-ohio-1-training.c0dgtxyjm9jm.us-east-2.rds.amazonaws.com:5432/postgres";
		//String username = "root";
		//String password = "Scipio32";
		
		try {
			Class.forName("org.postgresql.Driver");
			aConnection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			aLogger.error("connection failed", e);
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return aConnection;
	}
}

