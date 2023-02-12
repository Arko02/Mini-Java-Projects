import java.util.Scanner;

public class Calculator {
  public static void main(String[] args) {
    // Create a Scanner object for user input
    Scanner sc = new Scanner(System.in);

    // Prompt the user to enter the first number
    System.out.print("Enter the first number: ");
    double num1 = sc.nextDouble();

    // Prompt the user to enter the second number
    System.out.print("Enter the second number: ");
    double num2 = sc.nextDouble();

    // Prompt the user to enter an operator
    System.out.print("Enter an operator (+, -, *, /): ");
    char operator = sc.next().charAt(0);

    double result;

    // Use a switch statement to perform the calculation based on the operator
    switch (operator) {
      case '+':
        result = num1 + num2;
        break;
      case '-':
        result = num1 - num2;
        break;
      case '*':
        result = num1 * num2;
        break;
      case '/':
        result = num1 / num2;
        break;
      default:
        System.out.println("Invalid operator");
        return; // Exit the program if an invalid operator is entered
    }

    // Display the result to the user
    System.out.println("The result is: " + result);
  }
}
