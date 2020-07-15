package HW20;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NumbersTest {
    int[] listOfInt = {40, 0, 3, 7, 10, 15};
    int sum = 40;

    List<Integer> argList = new ArrayList<>();
    {
        for (int cur : listOfInt){
            argList.add(cur);
        }
    }


    @Test
    public void toFind() {
        Assert.assertEquals("[0, 1]", Numbers.toFind(listOfInt, sum));
    }

    @Test
    public void toFindList() {
        Assert.assertEquals("[0, 1]", Numbers.toFindList(argList, sum));
    }


}
