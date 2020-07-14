package HW10;

import java.util.Arrays;
import java.util.Iterator;

public class ToTest {
    public static void main(String[] args) {

        /*
        проверено:
push
pop
toString
size
isEmpty
contains
iterator
add
remove
addAll
clear
retainAll
removeAll
containsAll
toArray
         */

        MyCollectList myCollect = new MyCollectList();
        System.out.println("пустой ли? ждем правду: " + myCollect.isEmpty());
        myCollect.push(1);
        System.out.println("размер. ожидаем 1: " + myCollect.size());
        System.out.println("пустой ли? ждем ложь: " + myCollect.isEmpty());
        myCollect.push(2);
        myCollect.push(3);
        System.out.println("размер. ожидаем 3: " + myCollect.size());
        System.out.println(myCollect);
        System.out.println("тут ПОПаем, ожидаем 3: " + myCollect.pop());
        System.out.println("ожидаем весь стек с 2 (потому что 3 попнули выще): " + myCollect);
        System.out.println("размер. ожидаем 2: " + myCollect.size());
        myCollect.add(0);
        System.out.println("размер после ЭДД. ожидаем 3: " + myCollect.size());
        System.out.println("ожидаем стек с 2 до 0 (потому что 0 ЭДДнули выше и он добавлен в хвост: " + myCollect);
        for (int i = 4; i <= 20; myCollect.push(i++)) {
        }//наполнили стек


        System.out.println(myCollect);//вывели все это безобразие строкой


        Iterator it = myCollect.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());// вывели стольбцом для проверки итератора
        }



        Object toRemove = 20;
        System.out.println("стек до удаления: " + myCollect);
        System.out.println("успешно ли удаление, ждем правду: " + myCollect.remove(toRemove));

        System.out.println("стек без " + toRemove + "-ого: " + myCollect);
        System.out.println("рповерка метода контейнс, ждем ложь: " + myCollect.contains(25));

        MyCollectList other = new MyCollectList();
        other.add(11);
        other.add(21);
        other.add(31);
        System.out.println("другой: " + other);

        myCollect.addAll(other);

        System.out.println("стек после прибавления другого: " + myCollect);
        System.out.println("контейн олл, ждем правду: " + myCollect.containsAll(other));
        other.add(41);

        System.out.println("контейн олл, ждем ложь: " + myCollect.containsAll(other));


        System.out.println("стек до ретейнОлл: " + myCollect);
        System.out.println("другой до ретейнОлл: " + other);

        /*System.out.println("проверяем ретейнОлл, ждем правду: " + stack.retainAll(other));
        System.out.println("после ретейнОлл, ждем только 11, 11, 21, 31: " + stack);*/

        System.out.println("проверяем ремувОлл, ждем правду: " + myCollect.removeAll(other));
        System.out.println("после ремувОлл, ждем все, кроме 11, 21, 31: " + myCollect);

        Object[] ob = myCollect.toArray();
        System.out.println("stack.toArray()--------------(см стр выше): " + Arrays.toString(ob));

    }
}
