package money_Transaction;

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

;

public class Test {
	final static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		String path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";
		FileInputStream file = null;
		Properties pro = null;

		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			file = new FileInputStream(path);
			pro = new Properties();
			pro.load(file);
			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String password = pro.getProperty("password");
			connection = DriverManager.getConnection(url, user, password);

			trans(connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void trans(Connection connection) throws SQLException {
		connection.setAutoCommit(false);

		System.out.print("\u001B[33m Enter Your Account Number : \u001B[0m");
		String sender = scan.next();

		System.out.print("\u001B[33m Enter the Receiver Account Number : \u001B[0m");
		String receiver = scan.next();

		System.out.println("Enter the Amount: ");
		int amount = scan.nextInt();

		String fetchBalance = "SELECT balance from Bank_Account WHERE accountNumber = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(fetchBalance);
		preparedStatement.setString(1, sender);
		ResultSet result = preparedStatement.executeQuery();
		int balance = 0;
		if (result.next()) {
			balance = result.getInt("balance");
		}

		int i = updateBal(connection, sender, -amount);
		int j = updateBal(connection, receiver, amount);

		boolean flag = checkTransuction(i, j, balance, amount);
		if (flag) {
			connection.commit();
			System.out.println("ok");
		} else {
			connection.rollback();
			System.out.println("no");
		}
	}

	public static boolean checkTransuction(int i, int j, int balance, int amount) {
		System.out.println("Confirm the Transaction: YES ✅ / NO ❌ ");
		String choice = scan.next();
		return choice.equalsIgnoreCase("YES") && balance >= amount && i == 1 && j == 1;
	}

	public static int updateBal(Connection connection, String sender, int amount) throws SQLException {
		String update = "UPDATE Bank_Account SET balance = balance + ? WHERE accountNumber = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(update);
		preparedStatement.setInt(1, amount);
		preparedStatement.setString(2, sender);
		int row = preparedStatement.executeUpdate();
		return row;
	}

}
