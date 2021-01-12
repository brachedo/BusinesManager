package JDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBConnection {
	
	private static String USERNAME = "goshi";
	private static String PASSWORD = "goshi";
	private static String CONN = "jdbc:mysql://localhost/company?serverTimezone=UTC";
	
	public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(CONN, USERNAME, PASSWORD);
		
	}
	public static void createTable() throws Exception{
		try {
			Connection conn = getConnection();
			PreparedStatement create = conn.prepareStatement(SQLCommands.CREATE_TABLE_CATALOGUE);
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}finally {
			System.out.println("Func comlpeted");
		}
	}
}
