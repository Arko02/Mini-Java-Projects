// This Java program is an example of a simple command-line application that interacts with a MySQL database. 
// It prompts the user to select an action (save, update, delete, fetch, or login) and then performs the appropriate action based on the user's choice.

package arko.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Project 
{
    public static void main(String[] args) 
    {
    	// Initialize variables
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url, user, password;

        url = "jdbc:mysql://localhost:3306/jdbc_demo";
        user = "root";
        password = "8240/apple";
        
        // Prompt user for input
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER THE NUMBER YOU WANT TO GO WITH :-");
        System.out.println("1: TO SAVE THE PERSON");
        System.out.println("2: TO UPDATE THE PERSON");
        System.out.println("3: TO DELETE THE PERSON BY ID");
        System.out.println("4: TO FETCH THE PERSON BY ID");
        System.out.println("5: TO LOGIN THE PERSON");
        
        int choice = sc.nextInt();

        try 
        { // Try Block.
        	
        	// Load MySQL driver and connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            
            // Perform appropriate action based on user's choice
            switch (choice) 
            { // Switch-Block.
            
                // Save new person record to database
                case 1: 
                {
                    pst = con.prepareStatement("insert into person values(?,?,?,?,?)");
                    
                    System.out.println("TO SAVE THE PERSON GIVE THE FOLLOWING DETAILS");
                    
                    System.out.println("Enter ID");
                    int id = sc.nextInt();
                    
                    System.out.println("Enter Name");
                    String name = sc.next();
                    
                    System.out.println("Enter Age");
                    int age = sc.nextInt();
                    
                    System.out.println("Enter Phone Number");
                    long phone = sc.nextLong();
                    
                    System.out.println("Enter Password");
                    String password1 = sc.next();

                    pst.setInt(1, id);
                    pst.setString(2, name);
                    pst.setInt(3, age);
                    pst.setLong(4, phone);
                    pst.setString(5, password1);
                    pst.executeUpdate();
                    
                    System.out.println("Your details are registered");
                    
                    break;
                }
                
                // Update an existing person record in the database
                case 2: 
                {
                    pst = con.prepareStatement("update person set name=?, age=?, phone=?, password=? where id=?");
                    
                    System.out.println("Enter ID");
                    int id = sc.nextInt();
                    
                    System.out.println("Enter Name");
                    String name = sc.next();
                    
                    System.out.println("Enter Age");
                    int age = sc.nextInt();
                    
                    System.out.println("Enter Phone Number");
                    long phone = sc.nextLong();
                    
                    System.out.println("Enter Password");
                    String password1 = sc.next();

                    pst.setString(1, name);
                    pst.setInt(2, age);
                    pst.setLong(3, phone);
                    pst.setString(4, password1);
                    pst.setInt(5, id);
                    pst.executeUpdate();

                    System.out.println("Record updated");
                    break;
                }
                
                // Delete a person record from the database
                case 3: 
                {
                    pst = con.prepareStatement("delete from person where id=?");
                    System.out.println("Enter ID");
                    int id = sc.nextInt();

                    pst.setInt(1, id);
                    pst.executeUpdate();

                    System.out.println("Record deleted");
                    break;
                }
                
                // Fetch a person record from the database by ID
                case 4: 
                {
                    pst = con.prepareStatement("select * from person where id=?");
                    System.out.println("Enter ID");
                    int id = sc.nextInt();

                    pst.setInt(1, id);
                    rs = pst.executeQuery();
                    while (rs.next())
        			{
        				System.out.println(rs.getInt("id")+ " " + rs.getString(2) + " " + rs.getInt(3) +" "+rs.getLong(4)+" "+rs.getString(5));
        			}
                    System.out.println("Record fetch");
                    break;
                }
                
                // To log in a person
                case 5: 
                {
                    pst = con.prepareStatement("select * from person where id=? and password=?");
                    
                    System.out.println("Enter The register Id");
            		long phone =sc.nextLong();
            		
            		System.out.println("Enter the register Password");
            		String ps =sc.next();
            		
            		pst.setLong(1, phone);
            		pst.setString(2,ps);
            		
            		rs=pst.executeQuery();
            		
            		if(rs.next())
        			{
        				System.out.println("login succ");
        				System.out.println("Id " + rs.getInt(1));
        				System.out.println("name " + rs.getString(2));
        				System.out.println("age " + rs.getInt(3));
        				System.out.println("phone " + rs.getLong(4));
        			}
            		else
        			{
        				System.out.println("invalid");
        			}
            		
                }  
           } // Switch-Block.
        } // Try Block.
                    
                    catch(ClassNotFoundException|SQLException e) 
            		{
            			System.out.println("Invalid Drivery ClassName");
            			
            			e.printStackTrace();
            			
            		}
            		
            
            		finally 
                { 
            			
            			if(con!=null) 
            		{ 	
            				try
            				{
            					con.close();
            					System.out.println("Connection Closed");
            				}
            				
            				catch(SQLException e)
            				{
            					e.printStackTrace();
            				}
            	    } 
                }
            			
            			if(pst!=null) 
            	    { 
            				try 
            				{
            					pst.close(); //[vi]
            					System.out.println("Statement Closed");
            				}
            				catch (SQLException e)
            				{
            					e.printStackTrace();
            				}
            				
            		} 
            			
            	   if(rs!=null) 
            	   { 
            				try 
            				{
            					rs.close(); //[vi]
            					System.out.println("Result Set Closed");
            				}
            				catch (SQLException e)
            				{
            					e.printStackTrace();
            				}
            			
                   } 
      } //Main-Block
} // Class-Block
