package sqlitedb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createTable {
	public static void main(String[] args) {
		final String TABLE_NAME = "User";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
										+ "(id INT NOT NULL,"
										+ "username VARCHAR(8) NOT NULL,"
										+ "password VARCHAR(20) NOT NULL,"
										+ "PRIMARY KEY (id))");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

