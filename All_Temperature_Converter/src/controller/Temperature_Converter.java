package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

class Temperature_Converter {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the Temperature: ");
            int temperature = sc.nextInt();

            // Display conversion options
            System.out.println("1. Celsius to Fahrenheit");
            System.out.println("2. Celsius to Kelvin");
            System.out.println("3. Fahrenheit to Celsius");
            System.out.println("4. Fahrenheit to Kelvin");
            System.out.println("5. Kelvin to Celsius");
            System.out.println("6. Kelvin to Fahrenheit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            double convertedTemperature = 0.0;

            switch (choice) {
                case 1:
                    convertedTemperature = celsiusToFahrenheit(temperature);
                    System.out.println("Temperature in Fahrenheit: " + convertedTemperature);
                    break;
                case 2:
                    convertedTemperature = celsiusToKelvin(temperature);
                    System.out.println("Temperature in Kelvin: " + convertedTemperature);
                    break;
                case 3:
                    convertedTemperature = fahrenheitToCelsius(temperature);
                    System.out.println("Temperature in Celsius: " + convertedTemperature);
                    break;
                case 4:
                    convertedTemperature = fahrenheitToKelvin(temperature);
                    System.out.println("Temperature in Kelvin: " + convertedTemperature);
                    break;
                case 5:
                    convertedTemperature = kelvinToCelsius(temperature);
                    System.out.println("Temperature in Celsius: " + convertedTemperature);
                    break;
                case 6:
                    convertedTemperature = kelvinToFahrenheit(temperature);
                    System.out.println("Temperature in Fahrenheit: " + convertedTemperature);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer value.");
        }
    }

    // Celsius to Fahrenheit conversion
    public static double celsiusToFahrenheit(int celsius) {
        return (celsius * 9.0 / 5) + 32;
    }

    // Celsius to Kelvin conversion
    public static double celsiusToKelvin(int celsius) {
        return celsius + 273.15;
    }

    // Fahrenheit to Celsius conversion
    public static double fahrenheitToCelsius(int fahrenheit) {
        return (fahrenheit - 32) * 5 / 9.0;
    }

    // Fahrenheit to Kelvin conversion
    public static double fahrenheitToKelvin(int fahrenheit) {
        return (fahrenheit - 32) * 5.0 / 9 + 273.15;
    }

    // Kelvin to Celsius conversion
    public static double kelvinToCelsius(int kelvin) {
        return kelvin - 273.15;
    }

    // Kelvin to Fahrenheit conversion
    public static double kelvinToFahrenheit(int kelvin) {
        return (kelvin - 273.15) * 9 / 5.0 + 32;
    }
}
