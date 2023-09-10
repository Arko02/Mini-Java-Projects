package money_Transaction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Transaction {
	static final Scanner scan = new Scanner(System.in);

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

			// Start the transaction
			transaction(connection);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close all resources
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void transaction(Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		connection.setAutoCommit(false);

		System.out.print("\u001B[33m Enter Your Account Number : \u001B[0m");
		String sender = scan.next();

		// Check if sender and receiver accounts are valid
		boolean senderValid = checkAccountExistence(connection, sender);
		if (!senderValid) {
			System.out.println("Invalid Sender Account Number Transaction Failed ❌");
			playInsufficientBalanceSound();
			return;
		}
		// =====================================================================================================================-->
		System.out.print("\u001B[33m Enter the Receiver Account Number : \u001B[0m");
		String receiver = scan.next();

		// Check if sender and receiver accounts are valid
		boolean receiverValid = checkAccountExistence(connection, receiver);
		if (!receiverValid) {
			System.out.println("Invalid Receiver Account Number Transaction Failed ❌");
			playInsufficientBalanceSound();
			return;
		}
		printReceiverAccountDetails(connection, receiver, "Receiver");
		System.out.println();
		// =====================================================================================================================
		System.out.println("Enter the Amount: ");
		int amount = scan.nextInt();

		// Fetch the balance of the sender
		String fetchBalance = "SELECT balance from Bank_Account WHERE accountNumber = ?";
		preparedStatement = connection.prepareStatement(fetchBalance);
		preparedStatement.setString(1, sender);
		ResultSet result = preparedStatement.executeQuery();

		// Update the balances for sender and receiver [
		int row1 = updateBalance(connection, sender, -amount);
		int row2 = updateBalance(connection, receiver, amount);
		// ]

		int balance = 0;
		if (result.next()) {
			balance = result.getInt("balance");
		}

		// Check if the transaction was successful
		boolean transactionSuccessful = checkTransaction(row1, row2, balance, amount);

		if (transactionSuccessful) {
			try {
				System.out.print("Transaction Processing .");
				Thread.sleep(1000);
				connection.commit();
				Thread.sleep(1000);
				System.out.print(".");
				Thread.sleep(1000);
				System.out.print(".");
				Thread.sleep(500);
				System.out.println();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			connection.commit();
			System.out.println("Transaction Successful ✅");

			// Play notification sound after successful transaction
			playSuccessfulSound();

			// Print sender and receiver details
			printAccountDetails(connection, sender, "Your");
		} else {
			connection.rollback();
			System.out.println("Transaction Failed ❌");

			// Play notification sound after Failed transaction
			playFailedSound();

			// Print sender and receiver details
			printAccountDetails(connection, sender, "Sender");
		}
		// Check if the sender has sufficient balance
		if (balance < amount) {
			System.out.println("Insufficient Balance Transaction Failed ❌");
			playInsufficientBalanceSound();
			return;
		}

	}

	public static boolean checkTransaction(int row1, int row2, int balance, int amount) {
		System.out.println("Confirm the Transaction: YES ✅ / NO ❌ ");
		String choice = scan.next();
		return choice.equalsIgnoreCase("YES") && balance >= amount && row1 == 1 && row2 == 1;
	}

	public static boolean checkAccountExistence(Connection connection, String accountNumber) throws SQLException {
		String query = "SELECT accountNumber FROM Bank_Account WHERE accountNumber = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, accountNumber);
		ResultSet result = preparedStatement.executeQuery();
		return result.next();
	}

	public static int updateBalance(Connection connection, String accountNumber, int amount) throws SQLException {
		String update = "UPDATE Bank_Account SET balance = balance + ? WHERE accountNumber = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(update);

		preparedStatement.setInt(1, amount);
		preparedStatement.setString(2, accountNumber);
		int row = preparedStatement.executeUpdate();
		return row;
	}

	// Print Details [
	public static void printAccountDetails(Connection connection, String accountNumber, String accountType)
			throws SQLException {
		String query = "SELECT * FROM Bank_Account WHERE accountNumber = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, accountNumber);
		ResultSet result = preparedStatement.executeQuery();

		System.out.println("\n" + accountType + " Account Details:");
		while (result.next()) {
			System.out.println("Id : " + result.getInt("ID"));
			System.out.println("Account Number : " + result.getString("accountNumber"));
			System.out.println("Name : " + result.getString("name"));
			System.out.println("Email : " + result.getString("email"));
			System.out.println("Balance : " + result.getInt("balance"));
			System.out.println("Branch : " + result.getString("branch"));
		}
	}

	public static void printReceiverAccountDetails(Connection connection, String accountNumber, String accountType)
			throws SQLException {
		String query = "SELECT name, accountNumber, email FROM Bank_Account WHERE accountNumber = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, accountNumber);
		ResultSet result = preparedStatement.executeQuery();

		System.out.println("\n" + accountType + " Account Details:");
		if (result.next()) {
			System.out.println("Name: " + result.getString("name"));
			System.out.println("Account Number: " + result.getString("accountNumber"));
			System.out.println("Email: " + result.getString("email"));
		}
	}
	// ]

	// Notification Sound [
	public static void playSuccessfulSound() {
		try {
			File soundFile = new File(
					"/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/ringtones/Notification.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

			// Get the audio format
			AudioFormat audioFormat = audioInputStream.getFormat();

			// Create a data line for audio playback
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Open the data line for audio playback
			sourceDataLine.open(audioFormat);
			// Start audio playback
			sourceDataLine.start();

			// Read and play the audio data
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesRead = 0;
			while ((bytesRead = audioInputStream.read(buffer, 0, bufferSize)) != -1) {
				sourceDataLine.write(buffer, 0, bytesRead);
			}

			// Stop audio playback and close the data line
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();

			// Close the audio input stream
			audioInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playFailedSound() {
		try {
			File soundFile = new File(
					"/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/ringtones/Error-Text-Message_1.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

			// Get the audio format
			AudioFormat audioFormat = audioInputStream.getFormat();

			// Create a data line for audio playback
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Open the data line for audio playback
			sourceDataLine.open(audioFormat);
			// Start audio playback
			sourceDataLine.start();

			// Read and play the audio data
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesRead = 0;
			while ((bytesRead = audioInputStream.read(buffer, 0, bufferSize)) != -1) {
				sourceDataLine.write(buffer, 0, bytesRead);
			}

			// Stop audio playback and close the data line
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();

			// Close the audio input stream
			audioInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playInsufficientBalanceSound() {
		try {
			File soundFile = new File(
					"/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/ringtones/Windows-error.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

			// Get the audio format
			AudioFormat audioFormat = audioInputStream.getFormat();

			// Create a data line for audio playback
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Open the data line for audio playback
			sourceDataLine.open(audioFormat);
			// Start audio playback
			sourceDataLine.start();

			// Read and play the audio data
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesRead = 0;
			while ((bytesRead = audioInputStream.read(buffer, 0, bufferSize)) != -1) {
				sourceDataLine.write(buffer, 0, bytesRead);
			}

			// Stop audio playback and close the data line
			sourceDataLine.drain();
			sourceDataLine.stop();
			sourceDataLine.close();

			// Close the audio input stream
			audioInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ]

	// Close Resources
	public static void closeResources(FileInputStream file, Properties pro, Connection connection, Statement statement,
			ResultSet result) {
		try {
			if (file != null)
				file.close();
			if (pro != null)
				pro.clear();
			if (result != null)
				result.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

}
