package task8;

public class Task8 {

    public static int processNumbers(int... numbers) {
        int paramSum = 0;
        int runningTotal = 0;

        System.out.println("--- Processing Sequence ---");
        for (int num : numbers) {
            paramSum += num;             
            runningTotal += paramSum;     
            System.out.println("Current parameter: " + num + " | Running cumulative sum: " + paramSum);
        }

        return paramSum; 
    }

    public static void main(String[] args) {
        int totalSum = processNumbers(4, 5, 10);
        System.out.println("---------------------------");
        System.out.println("Sum of all parameters: " + totalSum);
    }
}