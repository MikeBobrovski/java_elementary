package HW12;

import HW8.*;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ToTest {
    public static void main(String[] args) {
        //--------------------------------------------------инициализация коллекций
        MyCollectArr col = new MyCollectArr();
        Car car1 = new Car("Reno", "Logan", 2019, "grey", true, 15000);
        Car car2 = new Car("Nissan", "Patrol", 2010, "black", false, 25000);
        Car car3 = new Car("Toyota", "Mark2", 1980, "white", true, 700);
        Car car4 = new Car("Nissan", "Patrol", 2010, "black", false, 25000);
        Car car5 = new Car("Reno", "Megane", 0, "grey", true, 35000);
        col.add(car1);
        col.add(car2);
        col.add(car3);
        col.add(car4);
        col.add(car5);

        MyCollectArr col2 = new MyCollectArr();
        Animal animal1 = new Wolf(1, 50);
        Animal animal2 = new Hamster(4, 2);
        Animal animal3 = new Fish(1, 1);
        Animal animal4 = new Cat(2, 4);
        Animal animal5 = new Cat(2, 5);
        Animal animal6 = new Wolf(3, 80);
        col2.add(animal1);
        col2.add(animal2);
        col2.add(animal3);
        col2.add(animal4);
        col2.add(animal5);
        col2.add(animal6);

//        System.out.println("первая коллекция:");
//        System.out.println(col);
//        System.out.println("размер первой коллекции: " + col.size());
//        System.out.println();
//        System.out.println("вторае коллекция:");
//        System.out.println(col2);
//        System.out.println("размер второй коллекции: " + col2.size());
//        System.out.println();
//        System.out.println();
        //--------------------------------------------------инициализация коллекций


//        col.sort(Car.ByPrice);
//        System.out.println("коллекция после сортировки: \n" + col);
//
//        col2.sort(Animal.ByWeight);
//        System.out.println("коллекция после сортировки: \n" + col2);


//        System.out.println("ремув(индекс 1) ждем {" + car2 + "}: " + "{" + col.remove(1) + "}");
//        System.out.println(col);
//        System.out.println("размер первой коллекции, ждем 3: " + col.size());
//        System.out.println();

//        System.out.println("индексОф ждем 1: " + col.indexOf(car2));
//        System.out.println("ластИндексОф ждем 4: " + col2.lastIndexOf(animal5));

//        System.out.println("вместо {" + col2.set(1, car5) + "} ждем {" + car5 + "}");
//        System.out.println(col2);

//        System.out.println("ждем марк2: " + col.get(2));

/*

        col.add(1, car5);
        System.out.println("ждем {" + car5 + "} между Логан и Патруль:\n" + col);
        System.out.println("ждем размер 5: " + col.size());
*/


        //коллекция1.addAll(1, коллекция2)
        System.out.println("коллекция1.addAll(1, коллекция2) по индексу, ждем правду: " + col.addAll(1, col2));
        System.out.println("ждем животных между рено и ниссан:\n" + col);
        System.out.println("размер равен сумме: " + col.size());

        System.out.println();

        {
            System.out.println("------------------------------------проверка итератора:\n");
            ListIterator it = col.listIterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
            System.out.println("nextIndex: " + it.nextIndex());
            System.out.println("---назад");
            while (it.hasPrevious()) {
                System.out.println(it.previous());
            }

            System.out.println("nextIndex: " + it.nextIndex());
            System.out.println("---еще разок вперед, удалим хомяка:");
            System.out.println(it.next());
            System.out.println(it.next());
            System.out.println("этого удалим: " + it.next());
            it.remove();
            System.out.println(it.next());
            System.out.println("этого перетрем пустым объектом: " + it.next());
            it.set(new Object());
            System.out.println("после этого вставим что-то новое: " + it.next());
            it.add("что-то новое");
            System.out.println(it.next());

            System.out.println("---назад");
            while (it.hasPrevious()) {
                System.out.println(it.previous());
            }
            System.out.println("---вперед");
            while (it.hasNext()) {
                System.out.println(it.next());
            }
            System.out.println("------------------------------------конец проверки итератора.\n");
        }

        List<String> subList = col2.subList(2, 4);
        System.out.println(subList.size());

//List l = new LinkedList();
//l.subList(2,10);
    }//main


}//ToTest
