package HW26;

import Util.TimeHandler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class Hw26 {
    public static void main(String[] args) {
        final int DATA_SIZE = 5_000_000;//больше задавать не стоит, ибо переполнение и сумма квадратов станет отрицательной
        System.out.println("первое задание");
        LinkedList<Double> numbers = new LinkedList<>();
        for (int i = 0; i < DATA_SIZE; i++) {
            numbers.add(Math.random() * 10);
        }
        Util.TimeHandler.start();
        System.out.println(mean(numbers));
        System.out.println("руками: " + TimeHandler.stop());

        Util.TimeHandler.start();
        System.out.println(mean2(numbers));
        System.out.println("стримом: " + TimeHandler.stop());
        System.out.println();
        System.out.println();
        System.out.println("второе задание");
        //вики говорит, что четные - подмножество интежеров, так что косвенно, из поставленной задачи следует, что лист интов
        LinkedList<Integer> integers = new LinkedList<>();

        for (int i = 0; i < DATA_SIZE; i++) {
            integers.add((int) (Math.random() * 100 * Math.sin(i)));
        }
        //TODO усли хочется посмотреть поэлементно, то раскомментить
//        Iterator<Integer> it = integers.iterator();
//        for (int i = 0; i < 20; i++) {
//            for (int j = 0; j < 10; j++) {
//                System.out.print(it.next() + "         ");
//            }
//            System.out.println();
//        }
        Util.TimeHandler.start();
        System.out.println(mean1(integers));
        System.out.println("руками: " + TimeHandler.stop());

        Util.TimeHandler.start();
        System.out.println(mean3(integers));
        System.out.println("стримом: " + TimeHandler.stop());
    }

    public static double mean(LinkedList<Double> numbers) {
        //колхозно
        if (numbers == null) {
            throw new IllegalArgumentException("bad argument");
        }
        double sum = 0;
        Iterator<Double> it = numbers.iterator();
        while (it.hasNext()) {
            sum += it.next();
        }
        return sum / numbers.size();
    }

    public static double mean2(LinkedList<Double> numbers) {
        //модно
        if (numbers == null) {
            throw new IllegalArgumentException("bad argument");
        }
        return numbers
                .stream()
                .mapToDouble(i -> i)
                .average()
                .getAsDouble();
    }

    public static int mean1(LinkedList<Integer> numbers) {
        //колхозно
        if (numbers == null) {
            throw new IllegalArgumentException("bad argument");
        }
        int sum = 0;
        Iterator<Integer> it = numbers.iterator();
        while (it.hasNext()) {
            int current = it.next();
            //квадрат нечетного всегда нечетное. это видно из табл Пифагора и разложения (2к + 1)^2 = 4k^2 + 4k + 1,
            // потому сначала проверим, стоит ли возводить
            if (current % 2 == 0 && current > 0) {
                sum += current * current;
            }
        }
        return sum;
    }

    public static int mean3(LinkedList<Integer> numbers) {
        //модно
        if (numbers == null) {
            throw new IllegalArgumentException("bad argument");
        }
        return numbers
                .stream()
                .mapToInt(i -> i)
                //.flatMapToInt(num -> IntStream.of(Integer.parseInt(String.valueOf(num))))
                .filter(i -> i % 2 == 0)
                .filter(i -> i > 0)
//                .peek(System.out::println) //распечатка
                .map(i -> i * i)
                .sum();

    }
}

