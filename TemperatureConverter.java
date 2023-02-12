import java.util.Scanner;

public class TemperatureConverter {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner sc = new Scanner(System.in);
        
        // Prompt the user to enter a temperature in Celsius
        System.out.print("Enter temperature in Celsius: ");
        double celsius = sc.nextDouble();
        
        // Convert the temperature from Celsius to Fahrenheit
        double fahrenheit = (celsius * 9.0 / 5.0) + 32;
        
        // Display the converted temperature to the user
        System.out.println(celsius + "°C is equivalent to " + fahrenheit + "°F");
    }
}
