package HW2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Arithmetic {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        calc1();
        System.out.println();

        System.out.println("");
        bank(1000, 10, 15);//double
        bank("1000", 10, 15);//BigDecimal

        System.out.println("enter the number to check for simplicity: ");
        System.out.println(isSimple(scanner.nextInt()));

        System.out.println("enter the number to check for parity: ");
        System.out.println(isEven(scanner.nextInt()));


        System.out.print("enter the number to find denominators: ");
        System.out.println(denominators(scanner.nextInt()));

    }

    public static void calc1() {
        double number1 = 0;
        double number2 = 0;
        char operation;
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input first number:");
        System.out.printf("Your input %s as a %S number.", number1 = (double) scan.nextFloat(), 1);
        System.out.println();
        System.out.print("Please input second number:");
        System.out.printf("Your input %s as a %S number.", number2 = (double) scan.nextFloat(), 2);
        System.out.println();
        System.out.print("Please input operation like +-*/:");
        operation = scan.next().charAt(0);
        switch (operation) {
            case '+': {
                System.out.println();
                System.out.printf("Sum is %s", number1 + number2);
                System.out.println();
                break;
            }
            case '-': {
                System.out.printf("Difference is %s", number1 - number2);
                System.out.println();
            }
            case '*': {
                System.out.printf("Multiplication is %s", number1 * number2);
                System.out.println();
            }
            case '/': {
                System.out.printf("Division is %s", number1 / number2);
                System.out.println();
            }
        }
    }

    public static double average(double arg1, double arg2) {
        return (arg1 + arg2) / 2;
    }

    public static double average(double[] args) {
        double result = 0;
        for (int i = 0; i < args.length; i++) {
            result = +args[i];
        }
        return result / args.length;
    }

    public static BigDecimal bank(String sum, int term, int percent) {
        BigDecimal result = new BigDecimal(sum);
        double mult = 1 + (double) percent / 100;
        for (int i = 1; i <= term; i++) {
            result = result.multiply(BigDecimal.valueOf(mult));
            BigDecimal scaled = result.setScale(2, RoundingMode.HALF_UP);
            System.out.println("sum to end of " + i + " period is " + scaled);
        }
        return result;
    }

    public static double bank(double sum, int term, int percent) {
        double result = sum;
        for (int i = 1; i <= term; i++) {
            result = result * (1 + (double) percent / 100);
            System.out.printf("sum to end of " + i + " period is %.2f", result);
            System.out.println();
        }
        return result;
    }

    public static boolean isEven(int arg) {
        return arg % 2 == 0;
    }

    public static boolean isSimple(int arg) {
        for (int i = 2; i < arg; i++) {
            //System.out.println(arg + "%" + i + "=" + arg % i);
            if (arg % i == 0) return false;
        }
        return true;
    }

    public static String denominators (int arg) {
        String result = "for your number denominators are: ";
        for (int i = 2; i < arg; i++) if (arg % i == 0) result += String.valueOf(i) + ", ";
        return (result.equals("for your number denominators are: ")) ? "you number is simple" : result;
    }

}
