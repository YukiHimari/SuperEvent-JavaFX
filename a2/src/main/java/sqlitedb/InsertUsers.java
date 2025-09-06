package sqlitedb;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertUsers {
    	public static void main(String[] args) {
		final String TABLE_NAME = "User";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + TABLE_NAME +
					" VALUES (1, 'user1', 'password1'),(2, 'user2', 'password2'), (3, 'user3', 'password3'),(4,'admin','Admin123')";

			int result = stmt.executeUpdate(query);

			if (result == 1) {
				System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
				System.out.println(result + " row(s) affected");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    
}
