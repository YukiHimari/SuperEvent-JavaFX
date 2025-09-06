package sqlitedb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createEventTable {
    	public static void main(String[] args) {
		final String TABLE_NAME = "Event";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
            stmt.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
										+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
										+ "name VARCHAR(100) NOT NULL,"
										+ "venue VARCHAR(100) NOT NULL,"
                                        + "day VARCHAR(15) NOT NULL,"
                                        + "price REAL NOT NULL,"
                                        + "soldTickets INT NOT NULL,"
                                        + "totalTickets INT NOT NULL"
										+ ")");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    
}
