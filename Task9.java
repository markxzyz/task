import static java.lang.Math.*;

public class Task9 {

    public static int add(int a, int b) {
        return addExact(a, b);
    }

    public static int subtract(int a, int b) {
        return subtractExact(a, b);
    }

    public static int multiply(int a, int b) {
        return multiplyExact(a, b);
    }

    public static float divide(int a, int b) {
        return (float) floorDiv(a, b);
    }

    public static void main(String[] args) {
        int num1 = 15;
        int num2 = 4;

        System.out.println("--- Math Operations using Static Imports ---");
        System.out.println("Addition (" + num1 + " + " + num2 + "): " + add(num1, num2));
        System.out.println("Subtraction (" + num1 + " - " + num2 + "): " + subtract(num1, num2));
        System.out.println("Multiplication (" + num1 + " * " + num2 + "): " + multiply(num1, num2));
        System.out.println("Division (" + num1 + " / " + num2 + "): " + divide(num1, num2));
    }
}