package HW9;

import java.util.Arrays;
import java.util.Scanner;


public class Search {
    public static void main(String[] args) {
        int[] arr = toDoArr();
        arrPrint(arr, 25);

        sort(arr);

        System.out.println("\nпосле сортировки:");
        arrPrint(arr, 25);

        System.out.print("введите искомое: ");
        Scanner scanner = new Scanner(System.in);
        int toFind = scanner.nextInt();

        System.out.println("если искомый элемент есть, то ждем от поиска правду: " + search(arr, toFind));
    }

    public static boolean search(int[] arr, int toFind) {
        int index = binarySearch(arr, 0, arr.length - 1, toFind);
        System.out.println("индекс искомого эелемента " + toFind + " (или один из них) : " + index);
        /* ------------------------------------------------------------------------------------------------TODO прочитай, пожалуйста:
        закомментировано ниже - дополнение
        бинарный поиск только скажет, что элемент присутствует и даже вернет индекс, где его впервые встретил, но не факт, что первого, равного искомому
        он обернут в этот метод для удобства использования и формального следования требованию вернуть будеан
        если раскомментить ниже, то в массиве res соберутся все индексы, по которым элемент равен искомому
        никуда, кроме консоли я это отдаю, но идея мне показалась интересной: быстро находим область, где лежат элементы, равные искомому, потом пробегаем "назад"
        пока эеленты все еще равны искомому, потом один шаг вперед (потому, что после предыдущего цикла мы выскочили из этой обл) и бижим вперед, пока равны
         */


        int size = 0;
        while (arr[index] == toFind) {
            index--;
            //System.out.println("едем назад по массиву" + index);
        }
        index++;
        while (arr[index] == toFind) {
            index++;
            size++;
            //System.out.println("едем вперед по массиву" + index);
        }
        System.out.println("всего " + size + " элементов, равных " + toFind + " найдено");
        int[] res = new int[size];
        //while(arr[index] == toFind){res[size--] = index--}
        for (int i = 0; i < size; i++) {
            res[i] = index - size + i;
        }
        System.out.println("индексы: " + Arrays.toString(res));


        return index != -1;
    }

    private static int binarySearch(int arr[], int l, int r, int x) {
        if (r >= l) {
            int mid = l + (r - l) / 2;

            if (arr[mid] == x) return mid;

            if (arr[mid] > x) return binarySearch(arr, l, mid - 1, x);

            return binarySearch(arr, mid + 1, r, x);
        }

        return -1;
    }

    public static int[] toDoArr() {
        System.out.print("введите размаер массива: ");
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            int item = (int) ((Math.random() * 30 + 1) * Math.sin(i));
            arr[i] = item;
        }

        return arr;
    }

    public static void arrPrint(int[] arg, int line) {
        for (int i = 0; i < arg.length; i++) {
            System.out.print(fixLength(arg[i]) + ", ");
            if ((i + 1) % line == 0) {
                System.out.println();
            }
        }
    }

    public static String fixLength(int arg) {
        String res = "";
        if (Math.abs(arg) > 99) res = "  " + arg;
        else if (Math.abs(arg) > 9) res = "  " + arg;
        else res = "   " + arg;
        return (arg >= 0) ? " " + res : res;
    }

    public static void sort(int[] arg) {
        qsort(arg, 0, arg.length - 1);
    }

    private static void qsort(int[] source, int leftBorder, int rightBorder) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;

        int pivot = source[(leftMarker + rightMarker) / 2];
        do {

            while (source[leftMarker] < pivot) {
                leftMarker++;
            }

            while (source[rightMarker] > pivot) {
                rightMarker--;
            }

            if (leftMarker <= rightMarker) {
                if (leftMarker < rightMarker) {
                    int tmp = source[leftMarker];
                    source[leftMarker] = source[rightMarker];
                    source[rightMarker] = tmp;
                }
                leftMarker++;
                rightMarker--;
            }

        } while (leftMarker <= rightMarker);
        if (leftMarker < rightBorder) {
            qsort(source, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            qsort(source, leftBorder, rightMarker);
        }
    }
}
