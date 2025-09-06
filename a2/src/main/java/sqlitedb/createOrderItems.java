package sqlitedb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createOrderItems {
            public static void main(String[] args) {
		final String TABLE_NAME = "OrderItems";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
            stmt.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME);
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
										+ "(OrderItemid INTEGER PRIMARY KEY AUTOINCREMENT,"
                                        +"Orderid INTEGER NOT NULL,"
                                        + "eventId INTEGER NOT NULL,"
                                        + "eventName TEXT NOT NULL,"
                                        + "venue TEXT NOT NULL,"
                                        + "day TEXT NOT NULL,"
                                        + "price REAL NOT NULL,"
                                        + "ticketQuantity INT NOT NULL,"
                                        +"Foreign Key (Orderid) REFERENCES Orders(Orderid)"
										+ ")");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    
}
