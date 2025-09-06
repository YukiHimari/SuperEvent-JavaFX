package sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import rmit.fp.Cart;

public class Checkout {

    public static void CheckoutOrder(String username, List<Cart> cartItems, double totalPrice) {

        String sql = "INSERT INTO Orders(username,orderDateTime,totalPrice) VALUES(?,?,?)";
        String sql2 = "INSERT INTO OrderItems(orderId,eventId,eventName,venue,day,price,ticketQuantity) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, LocalDateTime.now().toString()); // Store current date and time
                pstmt.setDouble(3, totalPrice);
                pstmt.executeUpdate();
                System.out.println("Order placed successfully for user: " + username);
            } catch (SQLException e) {
                System.out.println("Error during checkout: " + e.getMessage());
            }

            try (PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
                for (Cart item : cartItems) {
                    pstmt2.setInt(1, getLastOrderId(conn)); // Assuming you have a method to get the last inserted order ID
                    pstmt2.setInt(2, item.getEventId());
                    pstmt2.setString(3, item.getEventName());
                    pstmt2.setString(4, item.getVenue());
                    pstmt2.setString(5, item.getDay());
                    pstmt2.setDouble(6, item.getPrice());
                    pstmt2.setInt(7, item.getTicketQuantity());
                    pstmt2.executeUpdate();
                }
                System.out.println("Order items added successfully.");
            } catch (SQLException e) {
                System.out.println("Error adding order items: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }


        //clearing the users cart after checkout
        String sql3 = "DELETE FROM Cart WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt3 = conn.prepareStatement(sql3)) {
            pstmt3.setString(1, username);
            pstmt3.executeUpdate();
            System.out.println("Cart cleared successfully for user: " + username);
        } catch (SQLException e) {
            System.out.println("Error clearing cart: " + e.getMessage());
        }

    }

    //Method to get the last inserted order ID into the OrderItems table
    private static int getLastOrderId(Connection conn) throws SQLException {
        String sql = "SELECT last_insert_rowid()";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve last order ID.");
                }
            }
        }
    }
    
}
