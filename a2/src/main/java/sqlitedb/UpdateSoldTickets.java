package sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSoldTickets {
    public static void updateTickets(int eventId, int quantity, String eventName) {
        String sql = "UPDATE Event SET soldTickets = soldTickets + ? WHERE id = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
            System.out.println("Tickets updated successfully for event: " + eventName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


        public static void updateSoldTicketsFromCart(int eventId, int quantity, String eventName) {
        String sql = "UPDATE Event SET soldTickets =  ? WHERE id = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
            System.out.println("Tickets updated successfully for event: " + eventName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

        public static void removeSold(int eventId, int quantity, String eventName) {
        String sql = "UPDATE Event SET soldTickets = soldTickets -  ? WHERE id = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
            System.out.println("Tickets updated successfully for event: " + eventName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    
}
