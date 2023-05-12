import java.util.Scanner;

// Celsius to Fahrenheit.
class TemperatureConverterC_F {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Degree Celsius: ");
        int celsius = sc.nextInt();

        // Formula -> (°C × 9/5) + 32 = °F
        double fahrenheit = (celsius * 9.0 / 5) + 32;

        System.out.println(fahrenheit + "Fahrenheit");
    }
}

// Celsius to Kelvin.
class TemperatureConverterC_K {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Degree Celsius: ");
        int celsius = sc.nextInt();

        // Formula -> °C + 273.15 = K
        double kelvin = celsius + 273.15;

        System.out.println(kelvin + "Kelvin");
    }
}

// Fahrenheit to Celsius.
class TemperatureConverterF_C {
    // Fahrenheit to Celsius.
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Degree Fahrenheit: ");
        int fahrenheit = sc.nextInt();

        // Formula -> (°F - 32) * 5/9 = °C
        double celsius = (fahrenheit - 32) * 5 / 9.0;

        System.out.println(celsius + "Celsius");
    }
}

// Fahrenheit to Kelvin
class TemperatureConverterF_K {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Fahrenheit: ");
        int fahrenheit = sc.nextInt();

        // Formula -> (°F − 32) × 5/9 + 273.15 = K
        double kelvin = (fahrenheit - 32) * 5.0 / 9 + 273.15;
        System.out.println(kelvin + "Kelvin");
    }
}

// Kelvin to Celsius
class TemperatureConverterK_C {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Kelvin: ");
        int kelvin = sc.nextInt();

        // Formula -> K − 273.15 = °C
        double celsius = kelvin - 273.15;

        System.out.println(celsius + "Celsius");

    }
}

// Kelvin to Fahrenheit
class TemperatureConverterK_F {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Kelvin: ");
        int kelvin = sc.nextInt();

        // Formula -> (K − 273.15) × 9/5 + 32 = °F
        double fahrenheit = (kelvin - 273.15) * 9 / 5.0 + 32;
        System.out.println(fahrenheit + "Fahrenheit");
    }
}