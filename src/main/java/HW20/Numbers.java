/*
FIXME
решение в методе toFindFaster, на него тест писать не стал, и так очевидно.
использовал свою мапу из 17 домашки, докинул в нее пару специфичных методов: getInd, который вернет индекс и putInt, в который я могу положить еще и индекс
в энтери добавил поле private int index, в котором лежат индексы (соответственно входного массива)
 */
package HW20;

import java.util.Arrays;
import java.util.List;

public class Numbers {

    public static void main(String[] args) {
        int[] arg = {50, 40, 0, 3, 7, 10, 15};
        int sum = 17;
        //System.out.println(toFind(arg, sum));

        toFindFaster(arg, sum);
    }

    public static String toFind(final int[] inOrder, int sum) {
        int[] toSort = Arrays.copyOf(inOrder, inOrder.length);
        //int[] inOrder = Arrays.copyOf(toSort, toSort.length);
        int head = 0, tail = toSort.length - 1;
        //отсортируем
        Arrays.sort(toSort);
        //ищем в цикле
        while (head < tail) {
            if (toSort[head] + toSort[tail] == sum)
                break;//если нашли, то выйдем не по условию цикла, т е, после выхода !(head < tail)
            else if (toSort[head] + toSort[tail] < sum) {
                head++;
            } else {
                tail--;
            }
        }
        //если выщли по условию цикла, то искомого не нашли, иначе индексы сортированного массива лежат в голове и хвосте, найдем индексы в изначальном массиве
        if (head >= tail) {
            return "No such elements which sum equal to given number";
        } else {
            //пройдем изначальный массив, сравним его элементы с елементами, по правильному инд сортированного массива
            boolean h = false, t = false;//флаги того, что нужное уже нашли и заходить дальше нет нужды
            for (int i = 0; i < inOrder.length; i++) {
                if (!h && inOrder[i] == toSort[head]) {//если нашли, то
                    head = i;//переприсвоили индекс
                    h = true;//и запомнили, что уже переприсволили
                }
                if (!t && inOrder[i] == toSort[tail]) {
                    tail = i;
                    t = true;
                }
                if (h && t) break;//если нашли все, то выйти не дожидаясь условия
            }
            return (head < tail) ? "[" + head + ", " + tail + "]" : "[" + tail + ", " + head + "]";
        }
    }

    public static String toFindList(List<Integer> arg, int sum) {
        Integer[] tmp = new Integer[arg.size()];
        int[] intArray = Arrays.stream(arg.toArray(tmp)).mapToInt(Integer::intValue).toArray();
        return toFind(intArray, sum);
    }

    public static void toFindFaster(int[] input, int sum) {
        CollMap<Integer, Integer> map = new CollMap<>();
        for (int i = 0; i < input.length; i++) {//идем в цикле по массиву
            //2. если мы нашли ключ, равный очередному элементу массива, а он был вычеслен, то по нему лежит значение, которого не хватает до суммы
            if (map.containsKey(input[i])) {
                //System.out.println("элементы: " + map.get(input[i]) + ", " + input[i]);
                System.out.println("[" + (map.getInd(input[i])) + ", " + i + "]");//либо ретурном, тогда удалить брик
                break;//если предполагаем, что решение всего одно
            } else {
                //1. кладем в мапу, где ключем будет разница, а значением очередное число из массива
                map.putInt(sum - input[i], input[i], i);
            }
        }
    }


}