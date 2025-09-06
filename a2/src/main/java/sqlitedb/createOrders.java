package sqlitedb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createOrders {

        public static void main(String[] args) {
		final String TABLE_NAME = "Orders";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
            stmt.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
										+ "(Orderid INTEGER PRIMARY KEY AUTOINCREMENT,"
										+ "username TEXT NOT NULL,"
                                        + "OrderDateTime DateTime NOT NULL,"
                                        + "totalPrice REAL NOT NULL"
										+ ")");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    
}
