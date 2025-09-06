package sqlitedb;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ValidateLogin {
        public static boolean Validation(String  LoginUsername,String LoginPassword ) {
         boolean valid = false;
		final String TABLE_NAME = "USER";

		try (Connection con = DatabaseConnection.getConnection();
				Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = '" + LoginUsername + "'" + " AND password = '" + LoginPassword +"'";

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				if (resultSet.next()) {
                    System.out.println("Login Successfull");
					// System.out.printf("Id: %d | username: %s | password: %s \n",
					// 		resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));


                    valid = true;  
				}
                else {
                     System.out.println("Invalid Username or Password");
                }
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

        return valid;
	}
}
