package HW2;

import java.util.Scanner;

public class Graphics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter width of figure: ");
        int arg1 = scanner.nextInt();

        System.out.print("enter height of figure: ");
        int arg2 = scanner.nextInt();

        System.out.print("enter type of figure: ");
        char mode = scanner.next().charAt(0);

        pseudoGraphics(arg1, arg2, mode);
    }

    /*
    a - прямоугольник
    b - конверт
    с - шахматный
     */


    public static void pseudoGraphics(int arg1, int arg2, char mode) {
        if (
                ((mode == 'a') || (mode == 'c') || ((mode == 'b'))) && (arg2 >= 5) && (arg2 < 70) && (arg1 >= 5) && (arg1 < 50)
        ) {
            double width = arg1;
            double height = arg2;
            double factor = (width - 1) / (height - 1);

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    boolean diagonal = w == (int) Math.round(factor * h) || w + 1 == (int) Math.round(width - factor * h);
                    if (
                            (((w == 0) || (h == 0) || (w + 1 == width) || (h + 1 == height)) && (mode == 'a')) ||
                                    (((w == 0 || w + 1 == width || diagonal || h == 0 || h + 1 == height)) && (mode == 'b')) ||
                                    ((((w % 2 == 0) && (h % 2 == 0)) || ((w % 2 == 1) && (h % 2 == 1))) && (mode == 'c'))

                    ) {System.out.print("*");
                    } else System.out.print(" ");
                }//width
                System.out.println();
            }//height
        } else System.out.println("side from 5 to 50, mode a,b,c");
    }//pseudoGraphics

}//Graphics
