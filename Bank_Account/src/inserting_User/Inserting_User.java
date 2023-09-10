package inserting_User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import custom_Methods.MyMethods;

public class Inserting_User {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String choice = null;

		String path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";
		FileInputStream file = null;
		Properties pro = null;

		Connection connection = null;
		PreparedStatement preparedstatement = null;
		ResultSet result = null;

		InputStream inputStream = null;
		FileReader fileReader = null;

		try (scan) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			file = new FileInputStream(path);
			pro = new Properties();
			pro.load(file);
			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String password = pro.getProperty("password");
			connection = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO Bank_Account(`accountNumber`, `name`, `email`, `balance`, `branch`, `occupation`) VALUES (?, ?, ?, ?, ?, ?)";
			preparedstatement = connection.prepareStatement(sql);
			do {
				System.out.println("Register the User 🏦");

				System.out.print("Enter Account Number 🔐 : ");
				String accountNumber = scan.next();

				System.out.print("Enter Name 🗣️ : ");
				scan.nextLine();
				String name = scan.nextLine();

				System.out.print("Enter Email 📧 : ");
				String email = scan.next();

				System.out.print("Enter Balance ₹ : ");
				int balance = scan.nextInt();

				System.out.print("Enter Branch 🌍 : ");
				String branch = scan.next();

				System.out.print("Enter occupation 🌍 : ");
				String occupation = scan.next();

				preparedstatement.setString(1, accountNumber);
				preparedstatement.setString(2, name);
				preparedstatement.setString(3, email);
				preparedstatement.setInt(4, balance);
				preparedstatement.setString(5, branch);
				preparedstatement.setString(6, occupation);
				preparedstatement.addBatch();

				System.out.println("Continue to Inserting User ♾️ ");
				System.out.println("YES ✅ /NO ❌ ");
				choice = scan.next();

			} while (choice.equalsIgnoreCase("YES"));
			preparedstatement.executeBatch();
			MyMethods.printDetails(preparedstatement, result);

			System.out.print("Data is Processing .");
			Thread.sleep(1000);
			System.out.print(".");
			Thread.sleep(1000);
			System.out.print(".");
			System.out.println();
			Thread.sleep(500);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			System.out.println("Please give valid input for all credentials");
			e.getMessage();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyMethods.closeResource(file, pro, connection, preparedstatement, result);
			closeReader(fileReader);
		}
	}

	private static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
