package get_UsersDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

// Stored Procedures
public class User_Occupation {
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

            callableStatement = connection.prepareCall("{call job_details(?)}");

            System.out.println("Enter the Occupation: ");
            String job = scan.next();

            callableStatement.setString(1, job);
            callableStatement.execute();

            result = callableStatement.getResultSet();

            System.out.println("User Details:- ");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-4s | %-15s | %-20s | %-30s | %-10s | %-15s | %-20s\n", "ID",
                    "Account Number", "Name", "Email", "Balance", "Branch", "Occupation");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");

            while (result.next()) {
                int id = result.getInt("id");
                String accountNumber = result.getString("accountNumber");
                String name = result.getString("name");
                String email = result.getString("email");
                int balance = result.getInt("balance");
                String branch = result.getString("branch");
                String occupation = result.getString("occupation");

                System.out.printf("%-4d | %-15s | %-20s | %-30s | %-10d | %-15s | %-20s\n", id,
                        accountNumber, name, email, balance, branch, occupation);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
