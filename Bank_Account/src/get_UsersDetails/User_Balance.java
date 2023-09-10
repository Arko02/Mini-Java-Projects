package get_UsersDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.util.Scanner;

public class User_Balance {
	static final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		String path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";
		FileInputStream file = null;
		Properties pro = null;

		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet result = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			file = new FileInputStream(path);
			pro = new Properties();
			pro.load(file);

			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String password = pro.getProperty("password");
			connection = DriverManager.getConnection(url, user, password);

			callableStatement = connection.prepareCall("{call balance_amount(?)}");

			System.out.println("Enter the amount: ");
			int amount = scan.nextInt();

			callableStatement.setInt(1, amount);
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.execute();
			int count = callableStatement.getInt(1);

			callableStatement.getInt("name");

			System.out.println("Count: " + count);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources in the finally block
			try {
				if (result != null) {
					result.close();
				}
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
