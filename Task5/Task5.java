import java.util.Scanner; // <-- Make sure this line is at the top!

public class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Prompt user for three numbers
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        System.out.print("Enter third number: ");
        double num3 = scanner.nextDouble();

        // Check if all numbers are equal
        if (num1 == num2 && num2 == num3) {
            System.out.println("All numbers are equal");
        } else {
            // Find the largest number
            double largest = num1;

            if (num2 > largest) {
                largest = num2;
            }
            if (num3 > largest) {
                largest = num3;
            }

            System.out.println("The largest number is: " + largest);
        }

        scanner.close();
    }
}