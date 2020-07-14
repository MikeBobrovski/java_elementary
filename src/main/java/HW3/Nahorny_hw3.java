package HW3;

import java.lang.reflect.Array;

public class Nahorny_hw3 {
    public static void main(String[] args) {
//        System.out.println(sumOfNumbers(12345));
//        System.out.println(sumOfNumbers("12345"));
        int[] point = new int [] {1,1,9,9,5,5};
        System.out.println(isPointInside(point));
    }

    public static int sumOfNumbers(int arg) {

        int result = 0;
        while (arg >= 1) {
            result += arg % 10;
            arg /= 10;
            //System.out.println("arg=" + arg + "   result = " + result);
        }
        return result;
    }

    public static int sumOfNumbers(String arg) {
        int result = 0;
        for (int i = 0; i < arg.length(); i++) {
            result += ((int) arg.charAt(i) - 48);
        }
        return result;
    }



    public static boolean isPointInside (int [] args){
        return isPointInside(args[0], args[1], args[2], args[3], args[4], args[5]); //сухой код и все такое
    }

    public static boolean isPointInside (int beginRectX, int beginRectY, int endRectX, int endRectY, int pointX, int pointY){
        return (pointX >=beginRectX) && (pointX <= endRectX) && (pointY >= beginRectY) && (pointY <= endRectY);
    }

}
