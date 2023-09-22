package app.warzone;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

/**
 * This is the main runner class which is the root of the project
 * @author Burhanuddin Savliwala
 */
public class Main {
    /**
     *
     * @param firstNum The first number
     * @param secondNum The second number
     * @return The sum of the two integers
     */
    public static int calcSum(int firstNum , int secondNum) {
        return firstNum + secondNum;
    }
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        System.out.printf("The sum of %d and %d is %d",a,b,calcSum(a,b));

    }
}