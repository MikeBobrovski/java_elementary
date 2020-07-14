/*
для чисел болше 47 BigInteger
 */

package HW9;

import java.math.BigInteger;
import java.util.Scanner;

public class Fibonacci {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter ordinal number for Fibonacci: ");
        int arg1 = scanner.nextInt();

        for (int i = 0; i < arg1; i++) {
            System.out.println(fibFormula(i));
        }

    }


    public static int fibCycle(int n) {
        if (n <= 1) return n;
        int previous = 0, next = 1, sum;
        for (int i = 2; i <= n; i++) {
            sum = previous;
            previous = next;
            next = sum + previous;
        }
        return next;
    }

    public static int fibRecursion(int n) {
        if (n <= 1) return n;
        else return fibRecursion(n - 1) + fibRecursion(n - 2);
    }

    public static BigInteger fibBigInt(int n) {
        if (n <= 1) return BigInteger.valueOf(n);
        BigInteger previous = BigInteger.ZERO, next = BigInteger.ONE, sum;
        for (int i = 2; i <= n; i++) {
            sum = previous;
            previous = next;
            next = sum.add(previous);
        }

        return next;
    }

    public static int fibFormula(int n) {
        double phi = (1 + Math.sqrt(5)) / 2;
        return (int) Math.round(Math.pow(phi, n)
                / Math.sqrt(5));
    }

}//class