package sqlitedb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class viewAvailableTickets {
    public static int getAvailableTicketsFromDB(int eventId) {
        String sql = "SELECT totalTickets - soldTickets AS availableTickets FROM Event WHERE id = ?";
        int availableTickets = 0;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                availableTickets = rs.getInt("availableTickets");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return availableTickets;
    }

    public static int getSoldTicketsFromDB(int eventId) {
        String sql = "SELECT soldTickets FROM Event WHERE id = ?";
        int soldTickets = 0;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                soldTickets = rs.getInt("soldTickets");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return soldTickets;
    }
    
}
