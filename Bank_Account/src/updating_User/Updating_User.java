package updating_User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import custom_Methods.MyMethods;

public class Updating_User {
	public static void main(String[] args) {
		Scanner scan   = new Scanner(System.in);
		String  choice = null;

		String          path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";
		FileInputStream file = null;
		Properties      pro  = null;

		Connection        connection        = null;
		PreparedStatement preparedstatement = null;
		ResultSet         result            = null;

		try (scan) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			file = new FileInputStream(path);
			pro  = new Properties();
			
			pro.load(file);
			String url      = pro.getProperty("url");
			String user     = pro.getProperty("user");
			String password = pro.getProperty("password");
			
			connection        = DriverManager.getConnection(url, user, password);
			
			String sql = "UPDATE `Bank_Account` SET `balance` = `balance` + ? WHERE `name` = ?";
			preparedstatement = connection.prepareStatement(sql);

			do {
				System.out.println("Enter the Amount: ");
				int balance = scan.nextInt();
				scan.nextLine();
				
				System.out.println("Enter the Person: ");
				String name = scan.nextLine();
				
				preparedstatement.setInt(1, balance);
				preparedstatement.setString(2, name);
				preparedstatement.addBatch();

				System.out.println("YES/NO");
				choice = scan.next();
				
			} while (choice.equalsIgnoreCase("YES"));
			preparedstatement.executeBatch();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyMethods.closeResource(file, pro, connection, preparedstatement, result);
		}
	}
}
