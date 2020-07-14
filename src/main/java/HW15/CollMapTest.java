package HW15;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class CollMapTest {
    CollMap MyMap, MyMap3;
    HashMap MyMap2;
    Student sidorov, ivanov, petrov;
    String valP, valI, valS;

    @Before
    public void setup() {
        Util.Logger.getLogger().log("BeforeTest");
        MyMap = new CollMap();
        MyMap3 = new CollMap();
        MyMap2 = new HashMap();
        sidorov = new Student(100, "Sidorov");
        ivanov = new Student(1, "Ivanov");
        petrov = new Student(42, "Petrov");

        valS = "sidorov's value";
        valI = "ivanov's value";
        valP = "petrov's value";

    }

    @After
    public void tearDown() {
        Util.Logger.getLogger().log("AfterTest");
        MyMap = null;
        sidorov = null;
        ivanov = null;
        petrov = null;
        valI = null;
        valP = null;
        valS = null;
    }

    @Test
    public void test_put_get_size_isEmpty() {
        Assert.assertTrue(MyMap.isEmpty());
        Assert.assertEquals(0, MyMap.size());
        MyMap.put(ivanov, valI);
        Assert.assertFalse(MyMap.isEmpty());
        Assert.assertEquals(1, MyMap.size());
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);
        Assert.assertEquals(3, MyMap.size());

        Assert.assertEquals(valP, MyMap.get(petrov));
        Assert.assertEquals(valI, MyMap.get(ivanov));
        Assert.assertEquals(valS, MyMap.get(sidorov));
        Assert.assertEquals(3, MyMap.size());
    }

    @Test
    public void test_containsKey_containsValue() {
        Assert.assertFalse(MyMap.containsKey(petrov));
        Assert.assertFalse(MyMap.containsValue(valP));

        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);

        String noSuchValue = "no value";

        Assert.assertTrue(MyMap.containsKey(petrov));
        Assert.assertTrue(MyMap.containsValue(valP));
        Assert.assertFalse(MyMap.containsValue(noSuchValue));
    }

    @Test(expected = NullPointerException.class)
    public void test_containsKeyNull() {
        //прям все методы проверять на нуловый арг не будем, согласно принципа разумной достаточности
        MyMap.containsKey(null);
    }

    @Test(expected = NullPointerException.class)
    public void test_containsValueNull() {
        MyMap.containsValue(null);
    }

    @Test
    public void test_clear_remove() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);

        Assert.assertTrue(MyMap.containsKey(petrov));
        Assert.assertTrue(MyMap.containsValue(valP));
        Assert.assertEquals(3, MyMap.size());
        Assert.assertEquals(petrov, MyMap.remove(petrov));
        Assert.assertEquals(2, MyMap.size());
        Assert.assertEquals(null, MyMap.remove(petrov));
        Assert.assertEquals(2, MyMap.size());
        Assert.assertFalse(MyMap.containsKey(petrov));
        Assert.assertFalse(MyMap.containsValue(valP));

        MyMap.clear();

        Assert.assertFalse(MyMap.containsKey(petrov));
        Assert.assertFalse(MyMap.containsValue(valP));

    }

    @Test
    public void test_keySet() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        //MyMap.put(petrov, valP);

        Set<Object> set;
        set = MyMap.keySet();

        Assert.assertEquals(2, set.size());

        Assert.assertTrue(set.contains(ivanov));
        Assert.assertFalse(set.contains(petrov));
        Assert.assertTrue(set.contains(sidorov));
    }

    @Test
    public void test_values() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        //MyMap.put(petrov, valP);

        Collection col;
        col = MyMap.values();

        Assert.assertEquals(2, col.size());

        Assert.assertTrue(col.contains(valI));
        Assert.assertFalse(col.contains(valP));
        Assert.assertTrue(col.contains(valS));
    }

    @Test
    public void test_putAll() {
        MyMap2.put(ivanov, valI);
        MyMap2.put(sidorov, valS);
        MyMap2.put(petrov, valP);
        System.out.println(MyMap2.toString());
        System.out.println("test_putAll");

        Assert.assertTrue(MyMap.isEmpty());
        MyMap.putAll(MyMap2);
        Assert.assertEquals(3, MyMap2.size());
    }


    //---------------------------------------------------------------------------entrySet

    @Test
    public void test_entrySet_size_isEmpty_clear() {
        Assert.assertEquals(0, MyMap.entrySet().size());
        Assert.assertTrue(MyMap.entrySet().isEmpty());
        MyMap.put(ivanov, valI);
        Assert.assertFalse(MyMap.entrySet().isEmpty());
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);
        Assert.assertEquals(3, MyMap.entrySet().size());
        MyMap.entrySet().clear();
        Assert.assertTrue(MyMap.entrySet().isEmpty());
        Assert.assertTrue(MyMap.isEmpty());
    }

    @Test
    public void test_entrySet_iterator() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);
        Object[] tmp = MyMap.entrySet().toArray();
        //заморачиваться с циклом не стал. возможно и стоило.
        Iterator it = MyMap.entrySet().iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(tmp[0], it.next());
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(tmp[1], it.next());
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(tmp[2], it.next());
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void test_entrySet_remove_contains_add() {

        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);

        Set<Map.Entry> res = MyMap.entrySet();
        Object[] r = res.toArray();

        Assert.assertTrue(MyMap.entrySet().contains(r[0]));
        Assert.assertTrue(MyMap.entrySet().contains(r[1]));
        Assert.assertTrue(MyMap.entrySet().remove(r[0]));
        Assert.assertTrue(MyMap.entrySet().remove(r[1]));
        Assert.assertFalse(MyMap.entrySet().remove(r[0]));
        Assert.assertFalse(MyMap.entrySet().remove(r[1]));
        Assert.assertFalse(MyMap.entrySet().contains(r[0]));
        Assert.assertFalse(MyMap.entrySet().contains(r[1]));

        MyMap.entrySet().add((Map.Entry) r[0]);
        Assert.assertTrue(MyMap.entrySet().contains(r[0]));

        MyMap.clear();
        Assert.assertTrue(MyMap.isEmpty());
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);
        Assert.assertTrue(MyMap3.isEmpty());
        MyMap3.entrySet().addAll(MyMap.entrySet());
        Assert.assertEquals(3, MyMap3.entrySet().size());
        Assert.assertTrue(MyMap3.entrySet().contains((Map.Entry) r[0]));
        Assert.assertTrue(MyMap3.entrySet().contains((Map.Entry) r[1]));
        Assert.assertTrue(MyMap3.entrySet().contains((Map.Entry) r[2]));
    }

    @Test
    public void test_entrySet_containsAll() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);

        MyMap3.put(ivanov, valI);
        MyMap3.put(sidorov, valS);


        Assert.assertTrue(MyMap.entrySet().containsAll(MyMap3.entrySet()));
        Assert.assertFalse(MyMap3.entrySet().containsAll(MyMap.entrySet()));

        MyMap.remove(ivanov, valI);
        Assert.assertFalse(MyMap.entrySet().containsAll(MyMap3.entrySet()));

    }

    @Test
    public void test_entrySet_removeAll() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);

        Set<Map.Entry> res = MyMap.entrySet();
        Object[] r = res.toArray();

        MyMap3.put(ivanov, valI);
        MyMap3.put(sidorov, valS);

        Assert.assertTrue(MyMap.entrySet().contains(r[0]));
        Assert.assertTrue(MyMap.entrySet().contains(r[1]));

        Assert.assertTrue(MyMap.entrySet().removeAll(MyMap3.entrySet()));
        Assert.assertFalse(MyMap.entrySet().removeAll(MyMap3.entrySet()));

        Assert.assertFalse(MyMap.entrySet().contains(r[0]));
        Assert.assertFalse(MyMap.entrySet().contains(r[1]));
    }

    @Test
    public void test_entrySet_retainAll() {
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);

        Set<Map.Entry> res = MyMap.entrySet();
        Object[] r = res.toArray();

        MyMap3.put(ivanov, valI);
        MyMap3.put(sidorov, valS);

        Assert.assertFalse(MyMap.entrySet().retainAll(MyMap3.entrySet()));

        MyMap.put(petrov, valP);
        Object[] r2 = MyMap.entrySet().toArray();
        Assert.assertTrue(MyMap.entrySet().contains(r2[2]));

        Assert.assertTrue(MyMap.entrySet().retainAll(MyMap3.entrySet()));
        Assert.assertFalse(MyMap.entrySet().retainAll(MyMap3.entrySet()));

        Assert.assertTrue(MyMap.entrySet().contains(r[0]));
        Assert.assertTrue(MyMap.entrySet().contains(r[1]));
        Assert.assertFalse(MyMap.entrySet().contains(r2[2]));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_entrySet_toArray() {
        Object[] tmp = new Object[3];
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);
        MyMap.entrySet().toArray(tmp);
    }

    //---------------------------------------------------------------------------/entrySet


    @Test
    public void test_method() {
    }

}
