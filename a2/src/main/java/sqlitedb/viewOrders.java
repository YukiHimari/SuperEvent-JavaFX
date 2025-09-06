package sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import rmit.fp.Order;

public class viewOrders {
        public static List<Order> getOrdersfromDB(String username) {
        System.out.println("Username: " + username);//debugging line
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order(
                            rs.getInt("orderId"),
                            rs.getString("username"),
                            rs.getString("OrderDateTime"),
                            rs.getDouble("totalPrice")

                    );
                    orders.add(order);
                }
            }
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Loaded orders: " + orders.size());
        return orders;
    }
    
}
