package HW20;

import java.util.Arrays;
import java.util.List;

public class Numbers {

    public static void main(String[] args) {
        int[] arg = {40, 0, 3, 7, 10, 15};
        int sum = 40;
        System.out.println(toFind(arg, sum));
    }

    public static String toFind(int[] arg, int sum) {
        int[] inOrder = Arrays.copyOf(arg, arg.length);
        int head = 0, tail = arg.length - 1;
        //отсортируем
        Arrays.sort(arg);
        //ищем в цикле
        while (head < tail) {
            if (arg[head] + arg[tail] == sum)
                break;//если нашли, то выйдем не по условию цикла, т е, после выхода !(head < tail)
            else if (arg[head] + arg[tail] < sum) {
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
                if (!h && inOrder[i] == arg[head]) {//если нашли, то
                    head = i;//переприсвоили индекс
                    h = true;//и запомнили, что уже переприсволили
                }
                if (!t && inOrder[i] == arg[tail]) {
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


}