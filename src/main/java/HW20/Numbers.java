package HW20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Numbers {

    public static String toFind(int[] arg, int sum) {
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
        //если выщли по условию цикла, то искомого не нашли, иначе индексы лежат в голове и хвосте
        if (head >= tail) {
            return "No such elements which sum equal to given number";
        } else {
            return "[" + head + ", " + tail + "]";
        }
    }

    public static String toFindList(List<Integer> arg, int sum) {
        Integer[] tmp = new Integer[arg.size()];
        int[] intArray = Arrays.stream(arg.toArray(tmp)).mapToInt(Integer::intValue).toArray();
        return toFind(intArray, sum);
    }


}