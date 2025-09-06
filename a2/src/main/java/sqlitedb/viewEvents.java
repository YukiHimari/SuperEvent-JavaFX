package sqlitedb;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import rmit.fp.Event;

public class viewEvents {
    public static List<Event> getEventsfromDB() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Event";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("venue"),
                        rs.getString("day"),
                        rs.getDouble("price"),
                        rs.getInt("soldTickets"),
                        rs.getInt("totalTickets")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }
    
}
