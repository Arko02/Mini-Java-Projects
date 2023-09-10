package fetch_User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import custom_Methods.MyMethods;

public class FetchUser {
	public static void main(String[] args) {

		String          path = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account/src/mysql/utilities/mysql.properties";
		FileInputStream file = null;
		Properties      pro  = null;

		Connection connection = null;
		Statement  statement  = null;
		ResultSet  result     = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			file = new FileInputStream(path);
			pro  = new Properties();

			pro.load(file);
			String url      = pro.getProperty("url");
			String user     = pro.getProperty("user");
			String password = pro.getProperty("password");

			connection = DriverManager.getConnection(url, user, password);
			statement  = connection.createStatement();
			
			MyMethods.printDetails(statement, result);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyMethods.closeResource(file, pro, connection, statement, result);
		}
	}
}
