package HW5;

public class ContainerToTest {
    public static void main(String[] args) {
        Container container1 = new Container(5);
        Container container2 = new Container(0);
        Container container3 = new Container();
        Container container4 = new Container(new int[]{1, 2, 3});

        System.out.println(container1.equals(container2));


        System.out.println();
        System.out.println(container3);
        container3.add(6);
        System.out.println(container3);
        System.out.println(container4.contains(2));
        System.out.println(container4.getSize());
        System.out.println(container4.get(1));
        System.out.println("-----------------------------");
        Container res = container3.addAll(container4);
        System.out.println("конт 4: " + container4);
        System.out.println("конт 3: " + container3);
        System.out.println("конт рез: " + res);
        System.out.println(container4.equals(res));
        System.out.println(container4.equals(container4));
        System.out.println(container4.equals(2, 2));

        container2.add(5);
        container2 = container2.addAll(container4);
        container2.add(0);
        container2 = container2.addAll(container4);
        System.out.println(container2);
        container2.addIndex(9, container2.getSize() - 3);
        System.out.println(container2);
        System.out.println(container2.indexOf(3));
        //container2.clear();
        System.out.println(container2.endIndexOf(3));
        container2.sort();
        System.out.println(container2);


    }
}
