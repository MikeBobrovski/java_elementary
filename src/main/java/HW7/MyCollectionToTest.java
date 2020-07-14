package HW7;

public class MyCollectionToTest {
    public static void main(String args[]) {
        /*
        список методов, которые протестил
        col.add(car1);
        col.remove(car2);
        col.isEmpty()
        col.size()
        col.contains(car4)
        public boolean addAll(MyCollection collection);
        public boolean removeAll(MyCollection collection);
        public boolean containsAll(MyCollection collection);
        осталось

        public boolean retainAll(MyCollection collection);

         */

        //--------------------------------------------------инициализация коллекций
        MyCollection col = new MyCollection();
        Car car1 = new Car("Reno", "Logan", 2019, "grey", true, 15000);
        Car car2 = new Car("Nissan", "Patrol", 2010, "black", false, 25000);
        Car car3 = new Car("Toyota", "Mark2", 1980, "white", true, 700);
        Car car4 = new Car("Nissan", "Patrol", 2010, "black", false, 25000);
        col.add(car1);
        col.add(car2);
        col.add(car3);
        col.add(car4);

        MyCollection col2 = new MyCollection();
        int[] color = {190, 16, 35};
        Animal animal1 = new Wolf(1, 50);
        Animal animal2 = new Hamster(4, 2);
        Animal animal3 = new Fish(1, 1);
        Animal animal4 = new Cat(2, 4);
        Animal animal5 = new Cat(2, 4);
        col2.add(animal1);
        col2.add(animal2);
        col2.add(animal3);
        col2.add(animal4);
        //--------------------------------------------------инициализация коллекций


//        System.out.println(col.isEmpty());
//        System.out.println(col.size());
//        System.out.println(col.contains(car4));
//        col.clear();
//        System.out.println(col.isEmpty());
//        System.out.println(col.size());
//        System.out.println(col);
        System.out.println("коллекция машинок: \n" + col);
        col.remove(car1);
        System.out.println("после удаления \"" + car1 + "\" коллекция содержит\n" + col);


        System.out.println("коллекция живности: \n" + col2);


        col.addAll(col2);
        System.out.println("после добавления животных к машинам получим: \n" + col);
        System.out.println("размер всего этого: " + col.size());

        System.out.println("ждем ложь после проверки на вхождения животного, которое не вошло: " + col2.contains(animal5));//здесь ложно ложь (из Обжекта) потому что не хочу переопределять equals для этого класса, как сделал это для машин
        System.out.println("проверка контейнОлл, ждем правду: " + col.containsAll(col2));

        col.remove(animal3);
        System.out.println("удалили животное \"" + animal3 + "\" и снова проверка контейнОлл, ждем ложь: " + col.containsAll(col2));


        System.out.println();
        System.out.println();
        System.out.println("первое множество:");
        System.out.println(col);
        System.out.println("второе множество:");
        System.out.println(col2);

        col.retainAll(col2);
        System.out.println("пересечение множеств:\n" + col);

        System.out.println("количество элементов, которые входили в обе коллекции: " + col.size());

        col.removeAll(col2);
        System.out.println("проверка контейнОлл, ждем ложь: " + col.containsAll(col2));
        System.out.println("после удаления животных здесь должно быть пусто: " + col);

    }
}
