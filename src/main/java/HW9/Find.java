package HW9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Find {
    public static void main(String[] args) {

        ArrayList<Number> numbers = toDoCollection();
        print(numbers, 25);

        System.out.print("введите искомое: ");
        Scanner scanner = new Scanner(System.in);
        Number toFind = new Number(scanner.nextInt());

        Collections.sort(numbers, Number.byValue);
        System.out.println("\nпосле сортировки:");
        print(numbers, 25);
        System.out.println("если искомый элемент есть, то ждем от поиска правду: " + search(numbers, toFind));
    }

    public static ArrayList<Number> toDoCollection() {
        ArrayList<Number> numbers = new ArrayList<>();
        System.out.print("введите размаер коллекции: ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        for (int i = 0; i < size; i++) {
            Number item = new Number((int) ((Math.random() * 20 + 1) * Math.sin(i)));
            numbers.add(item);
        }
        return numbers;
    }

    public static void print(ArrayList<Number> numbers, int line) {
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(fixLength(numbers.get(i)) + ", ");
            if ((i + 1) % line == 0) {
                System.out.println();
            }
        }
    }

    public static String fixLength(Number number) {
        String res = "";
        int arg = number.getData();
        if (Math.abs(arg) > 99) res = "  " + arg;
        else if (Math.abs(arg) > 9) res = "  " + arg;
        else res = "   " + arg;
        return (arg >= 0) ? " " + res : res;
    }

    public static boolean search(ArrayList<Number> numbers, Number toFind) {
        Number[] numbersA = new Number[numbers.size()];
        numbersA = numbers.toArray(numbersA);
        int index = binarySearch(numbersA, 0, numbersA.length - 1, toFind);
        System.out.println("индекс искомого эелемента" + toFind + " (или один из них) : " + index);
        return index != -1;
    }

    private static int binarySearch(Number[] numbers, int l, int r, Number toFind) {//если получаем Number[]
        if (r >= l) {
            int mid = l + (r - l) / 2;

            if (numbers[mid].getData() == toFind.getData()) return mid;//сравнение через equals, определенный в Number

            if (numbers[mid].getData() > toFind.getData())
                return binarySearch(numbers, l, mid - 1, toFind);//через компаратор, определенный в Number

            return binarySearch(numbers, mid + 1, r, toFind);
        }

        return -1;
    }

}
