package HW17;

import HW8.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class CollMapTest {

    CollMap<HW17.Student, String> MyMap, MyMap2, MyMap3;
    Student sidorov, ivanov, petrov;
    String valP, valI, valS;

    @Before
    public void setup() {
        MyMap = new CollMap();
        MyMap3 = new CollMap();
        MyMap2 = new CollMap();
        sidorov = new Student(100, "Sidorov");
        ivanov = new Student(1, "Ivanov");
        petrov = new Student(42, "Petrov");

        valS = "sidorov's value";
        valI = "ivanov's value";
        valP = "petrov's value";
    }

    @After
    public void tearDown() {
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
        Assert.assertEquals(valP, MyMap.remove(petrov));
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

        Set<Student> set;
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

        Set<Map.Entry<Student, String>> res = MyMap.entrySet();
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

        Set<Map.Entry<Student, String>> res = MyMap.entrySet();
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

        Set<Map.Entry<Student, String>> res = MyMap.entrySet();
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

    @Test
    public void test_entrySet_toArray() {
        Object[] arAc = new Object[3];
        MyMap.put(ivanov, valI);
        MyMap.put(sidorov, valS);
        MyMap.put(petrov, valP);
        Object[] arEx = MyMap.entrySet().toArray(arAc);
        Assert.assertEquals(arEx, arAc);
        Object[] test = new Object[3];
        Assert.assertNotEquals(arEx, test);
        Iterator it = MyMap.entrySet().iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(arAc[0], it.next());
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(arAc[1], it.next());
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals(arAc[2], it.next());
        Assert.assertFalse(it.hasNext());
    }

    //---------------------------------------------------------------------------/entrySet


    @Test
    public void test_Generic() {
        //ну и один тест на любой метод, но с другой типизацией
        Car car1 = new Car("Reno", "Logan", 2019, "grey", true, 15000);
        Car car2 = new Car("Nissan", "Patrol", 2010, "black", false, 25000);
        Car car3 = new Car("Toyota", "Mark2", 1980, "white", true, 700);
        Car car4 = new Car("Nissan", "Patrol", 2010, "black", false, 25000);

        Animal animal1 = new Wolf(1, 50);
        Animal animal2 = new Hamster(4, 2);
        Animal animal3 = new Fish(1, 1);
        Animal animal4 = new Cat(2, 4);

        CollMap<Car, Animal> colG = new HW17.CollMap<>();

        colG.put(car1, animal1);
        colG.put(car2, animal2);
        colG.put(car3, animal3);
        colG.put(car4, animal4);

        Assert.assertEquals(animal1, colG.get(car1));
        Assert.assertNotEquals(animal2, colG.get(car1));

    }

    Deque<Integer> stack = new ArrayDeque<Integer>();

}
