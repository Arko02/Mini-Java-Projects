package Resources;

import java.util.Scanner;

public interface Private_Resource {

	//
	Scanner SCAN = new Scanner(System.in);

	// MySQL Properties.
	String PATH = "/Users/arkodey/eclipse-workspace/JDBC/Bank_Account_ManagementSystem/src/Resources/mysql.properties";

	// MySQL Connector.
	String CONNECTOR = "com.mysql.cj.jdbc.Driver";

	//
	String SAVE_USER = "INSERT INTO Bank_Account(`accountNumber`, `name`, `email`, `pan`, `aadhar`, `phone`, `balance`, `branch`, `occupation`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

}
