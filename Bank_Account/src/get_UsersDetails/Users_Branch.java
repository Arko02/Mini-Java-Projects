package get_UsersDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

//Store Procedures
public class Users_Branch {

	static final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		String path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";

		try (FileInputStream file = new FileInputStream(path)) {
			Properties pro = new Properties();
			pro.load(file);

			String url = pro.getProperty("url"); // Get the database URL from properties file
			String user = pro.getProperty("user"); // Get the database username from properties file
			String password = pro.getProperty("password"); // Get the database password from properties file

			try (Connection connection = DriverManager.getConnection(url, user, password)) {

				// Prepare a callable statement to call the stored procedure
				CallableStatement call = connection.prepareCall("{call count_user(?,?)}");

				try {
					// Read the branch location from the user
					System.out.println("Enter Branch Location: ");
					String location = scan.next();

					// Set the branch location as a parameter for the stored procedure
					call.setString(1, location);

					// Register the output parameter to retrieve the user count
					call.registerOutParameter(2, Types.INTEGER);

					// Execute the stored procedure
					call.execute();

					// Retrieve the user count from the output parameter
					int usernum = call.getInt(2);

					// Print the user count
					System.out.println(usernum);

					// Create a statement for executing SQL queries SQL query to fetch user details
					// based on branch location
					Statement statement = connection.createStatement();
					String query = "SELECT * FROM Bank_Account WHERE branch = '" + location + "'";

					// Execute the query and retrieve the result set
					ResultSet resultSet = statement.executeQuery(query);

					while (resultSet.next()) {
						// Retrieve and print user details from the result set
						System.out.println(resultSet.getInt("id") + " " + resultSet.getString("accountNumber") + " "
								+ resultSet.getString("name") + " " + resultSet.getString("email") + " "
								+ resultSet.getInt("balance") + " " + resultSet.getString("branch"));
					}
				} catch (InputMismatchException e) {
					System.out.println("Give the correct input");
				}
			}
		} catch (IOException | SQLException | InputMismatchException e) {
			e.printStackTrace();
		}
	}
}
