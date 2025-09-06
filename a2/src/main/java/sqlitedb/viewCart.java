package sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


import rmit.fp.Cart;

public class viewCart {
        public static List<Cart> getCartfromDB(String username) {
        System.out.println("Username: " + username);//debugging line
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT * FROM Cart WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cart cart = new Cart(
                            rs.getInt("eventId"),
                            rs.getString("eventName"),
                            rs.getString("venue"),
                            rs.getString("day"),
                            rs.getDouble("price"),
                            rs.getInt("ticketQuantity"),
                            rs.getDouble("calcPrice"),
                            rs.getString("username") // Assuming username is stored in the Cart table
                    );
                    carts.add(cart);
                }
            }
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Loaded carts: " + carts.size());
        return carts;
    }
    
}
