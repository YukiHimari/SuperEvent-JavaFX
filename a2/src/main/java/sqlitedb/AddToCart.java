package sqlitedb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import rmit.fp.Event;

public class AddToCart {
    public static void addEventToCart(Event event, int ticketQuantity, String username) {

        // Insert the event into the Cart table
        try {
            // Assuming DatabaseConnection is a utility class that provides a connection to the database
        
		String sql = "INSERT INTO Cart (eventId,eventName, venue, day, price, ticketQuantity, calcPrice, username) VALUES (?,?, ?, ?, ?, ?, ?,?)";
        	try (Connection conn = DatabaseConnection.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {


                pstmt.setInt(1, event.getId());
		        pstmt.setString(2, event.getName());
		        pstmt.setString(3, event.getVenue());
		        pstmt.setString(4, event.getDay());
		        pstmt.setDouble(5, event.getPrice());
		        pstmt.setInt(6, ticketQuantity);
		        pstmt.setDouble(7, event.getPrice() * ticketQuantity); // Calculate total price for the event
				pstmt.setString(8,username); // to differentiate each users cart
		        pstmt.executeUpdate();
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}

      
    }
    
}
