import java.util.Scanner;

public class RectangleArea {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner sc = new Scanner(System.in);
        
        // Prompt the user to enter the width of the rectangle
        System.out.print("Enter the width of the rectangle: ");
        double width = sc.nextDouble();
        
        // Prompt the user to enter the height of the rectangle
        System.out.print("Enter the height of the rectangle: ");
        double height = sc.nextDouble();
        
        // Calculate the area of the rectangle
        double area = width * height;
        
        // Display the area of the rectangle to the user
        System.out.println("The area of the rectangle is " + area);
    }
}
