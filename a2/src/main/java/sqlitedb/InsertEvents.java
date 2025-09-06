package sqlitedb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import rmit.fp.Event;

public class InsertEvents {
 
    	public static void main(String[] args) {
		List<Event> events = Event.getEvents();

		String sql = "INSERT INTO Event (name, venue, day, price, soldTickets, totalTickets) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {

		    for (Event event : events) {
		        pstmt.setString(1, event.getName());
		        pstmt.setString(2, event.getVenue());
		        pstmt.setString(3, event.getDay());
		        pstmt.setDouble(4, event.getPrice());
		        pstmt.setInt(5, event.getSoldTickets());
		        pstmt.setInt(6, event.getTotalTickets());
		        pstmt.executeUpdate();
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}   
    
}
