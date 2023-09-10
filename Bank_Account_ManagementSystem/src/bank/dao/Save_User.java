package bank.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import Resources.Private_Resource;

public class Save_User {
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			FileInputStream file = null;
			Properties pro = new Properties();

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet result = null;

			try {
				Class.forName(Private_Resource.CONNECTOR);

				file = new FileInputStream(Private_Resource.PATH);
				pro.load(file);

				String url = pro.getProperty("url");
				String user = pro.getProperty("user");
				String password = pro.getProperty("password");

				connection = DriverManager.getConnection(url, user, password);

				preparedStatement = connection.prepareStatement(Private_Resource.SAVE_USER);

				String choice;
				do {
					System.out.println("Register the User 🏦");

					System.out.print("Enter Account Number 🔐 : ");
					String accountNumber = scan.next();

					System.out.print("Enter Name 🗣️ : ");
					scan.nextLine();
					String name = scan.nextLine();

					System.out.print("Enter Email 📧 : ");
					String email = scan.next();

					System.out.print("Enter Phone Number 📱 : ");
					String phone = scan.next();

					System.out.println("Enter Aadhar Number 🔍");
					String aadhar = scan.next();

					System.out.println("Enter PAN Number 🆔");
					String pan = scan.next();

					System.out.print("Enter Balance ₹ : ");
					int balance = scan.nextInt();

					System.out.print("Enter Branch 🌍 : ");
					String branch = scan.next();

					System.out.print("Enter Occupation 🏢 : ");
					String occupation = scan.next();

					preparedStatement.setString(1, accountNumber);
					preparedStatement.setString(2, name);
					preparedStatement.setString(3, email);
					preparedStatement.setString(4, phone);
					preparedStatement.setString(5, aadhar);
					preparedStatement.setString(6, pan);
					preparedStatement.setInt(7, balance);
					preparedStatement.setString(8, branch);
					preparedStatement.setString(9, occupation);
					preparedStatement.addBatch();

					System.out.println("Continue to Inserting User ♾️ ");
					System.out.println("YES ✅ / NO ❌ ");
					choice = scan.next();

				} while (choice.equalsIgnoreCase("YES"));
				preparedStatement.executeBatch();

				System.out.print("Data is Processing .");
				Thread.sleep(1000);
				System.out.print(".");
				Thread.sleep(1000);
				System.out.print(".");
				System.out.println();
				Thread.sleep(500);

			} catch (ClassNotFoundException | IOException | SQLException | InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (file != null) {
					try {
						file.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
