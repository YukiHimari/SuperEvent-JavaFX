package sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCart {

        public static void updateQuantity(int eventId, int quantity) {
        String sql = "UPDATE Cart SET ticketQuantity = ? WHERE eventId = ?";
        String sql2 = "UPDATE Cart SET calcPrice = price * ticketQuantity WHERE eventId = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
            System.out.println("Ticket Quantity updated successfully for event: " + eventId);

            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, eventId);
            pstmt2.executeUpdate();
            System.out.println("Total price updated successfully for event: " + eventId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeEventFromCart(int eventId) {
        String sql = "DELETE FROM Cart WHERE eventId = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
            System.out.println("Event removed successfully from cart: " + eventId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
