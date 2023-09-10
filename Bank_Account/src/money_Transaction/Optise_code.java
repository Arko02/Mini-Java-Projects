package money_Transaction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Optise_code {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";
        try (FileInputStream file = new FileInputStream(path)) {
            Properties pro = new Properties();
            pro.load(file);

            String url = pro.getProperty("url");
            String user = pro.getProperty("user");
            String password = pro.getProperty("password");

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                // Start the transaction
                transaction(connection);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void transaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);

        System.out.print("\u001B[33m Enter Your Account Number : \u001B[0m");
        String sender = scan.next();

        // Check if sender account is valid
        if (!checkAccountExistence(connection, sender)) {
            System.out.println("Invalid Sender Account Number Transaction Failed ❌");
            playInsufficientBalanceSound();
            return;
        }

        System.out.print("\u001B[33m Enter the Receiver Account Number : \u001B[0m");
        String receiver = scan.next();

        // Check if receiver account is valid
        if (!checkAccountExistence(connection, receiver)) {
            System.out.println("Invalid Receiver Account Number Transaction Failed ❌");
            playInsufficientBalanceSound();
            return;
        }

        printReceiverAccountDetails(connection, receiver, "Receiver");
        System.out.println();

        System.out.println("Enter the Amount: ");
        int amount = scan.nextInt();

        // Fetch the balance of the sender
        String fetchBalance = "SELECT balance from Bank_Account WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(fetchBalance)) {
            preparedStatement.setString(1, sender);
            try (ResultSet result = preparedStatement.executeQuery()) {
                int balance = 0;
                if (result.next()) {
                    balance = result.getInt("balance");
                }

                // Check if the sender has sufficient balance
                if (balance < amount) {
                    System.out.println("Insufficient Balance Transaction Failed ❌");
                    playInsufficientBalanceSound();
                    return;
                }

                // Update the balances for sender and receiver
                int row1 = updateBalance(connection, sender, -amount);
                int row2 = updateBalance(connection, receiver, amount);

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
                    printAccountDetails(connection, sender, "Your");
                    printReceiverAccountDetails(connection, receiver, "Receiver");
                }
            }
        }
    }

    public static boolean checkAccountExistence(Connection connection, String accountNumber) throws SQLException {
        String checkAccount = "SELECT accountNumber FROM Bank_Account WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkAccount)) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static void printAccountDetails(Connection connection, String accountNumber, String accountType)
            throws SQLException {
        String fetchAccountDetails = "SELECT * from Bank_Account WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(fetchAccountDetails)) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("\u001B[33m" + accountType + " Account Details : \u001B[0m");
                    System.out.println("Account Number: " + resultSet.getString("accountNumber"));
                    System.out.println("Account Holder Name: " + resultSet.getString("accountHolderName"));
                    System.out.println("Balance: " + resultSet.getInt("balance"));
                }
            }
        }
    }

    public static void printReceiverAccountDetails(Connection connection, String accountNumber, String accountType)
            throws SQLException {
        String fetchReceiverAccountDetails = "SELECT * from Bank_Account WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(fetchReceiverAccountDetails)) {
            preparedStatement.setString(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("\u001B[33m" + accountType + " Account Details : \u001B[0m");
                    System.out.println("Account Number: " + resultSet.getString("accountNumber"));
                    System.out.println("Account Holder Name: " + resultSet.getString("accountHolderName"));
                    System.out.println("Balance: " + resultSet.getInt("balance"));
                }
            }
        }
    }

    public static int updateBalance(Connection connection, String accountNumber, int amount) throws SQLException {
        String updateBalanceQuery = "UPDATE Bank_Account SET balance = balance + ? WHERE accountNumber = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, accountNumber);
            return preparedStatement.executeUpdate();
        }
    }

    public static boolean checkTransaction(int row1, int row2, int balance, int amount) {
        return row1 > 0 && row2 > 0 && (balance - amount) >= 0;
    }

    public static void playInsufficientBalanceSound() {
        String soundPath = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/ringtones/Windows-error.wav";
        playSound(soundPath);
    }

    public static void playSuccessfulSound() {
        String soundPath = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/ringtones/Notification.wav";
        playSound(soundPath);
    }

    public static void playFailedSound() {
        String soundPath = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/ringtones/Error-Text-Message_1.wav";
        playSound(soundPath);
    }

    public static void playSound(String soundPath) {
        try {
            File soundFile = new File(soundPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
