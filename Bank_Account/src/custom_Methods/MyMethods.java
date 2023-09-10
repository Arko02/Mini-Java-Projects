package custom_Methods;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MyMethods {

//	public static ResultSet printDetails(Statement statement, ResultSet result) throws SQLException {
//		result = statement.executeQuery("SELECT * FROM Bank_Account");
//
//		while (result.next()) {
//			System.out.printf("%-4s %-15s %-20s %-30s %-10s %-15s %-20s %-20s %-20s\n",
//					result.getInt("id") + " " + result.getString("accountNumber") + " " + result.getString("name") + " "
//							+ result.getString("email") + " " + result.getInt("balance") + " "
//							+ result.getString("branch") + " " + result.getBinaryStream("profile picture") + " "
//							+ result.getAsciiStream("resume"));
//		}
//		return result;
//	}

	public static ResultSet printDetails(Statement statement, ResultSet result) throws SQLException {
		result = statement.executeQuery("SELECT * FROM Bank_Account");

		System.out.println("User Details:");
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-4s | %-15s | %-20s | %-30s | %-10s | %-15s | %-20s | %-20s | %-20s\n", "ID",
				"Account Number", "Name", "Email", "Balance", "Branch", "Occupation", "Profile Picture", "Resume");
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		while (result.next()) {
			int id = result.getInt("id");
			String accountNumber = result.getString("accountNumber");
			String name = result.getString("name");
			String email = result.getString("email");
			int balance = result.getInt("balance");
			String branch = result.getString("branch");
			String occupation = result.getString("occupation");
			// Handle the profile picture and resume separately
			InputStream profilePicture = result.getBinaryStream("profile picture");
			InputStream resume = result.getAsciiStream("resume");

			System.out.printf("%-4d | %-15s | %-20s | %-30s | %-10d | %-15s | %-20s | %-20s | %-20s\n", id,
					accountNumber, name, email, balance, branch, occupation, profilePicture, resume);
		}

		return result;
	}

	public static void closeResource(FileInputStream file, Properties pro, Connection connection, Statement statement,
			ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (file != null) {
				file.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (pro != null) {
				pro.clone();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
