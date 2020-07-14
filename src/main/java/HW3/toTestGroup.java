package HW3;

import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;

public class toTestGroup {
    public static void main(String[] args) {
        Group group = Group.getGroup(32);


        group.addStudent("Bob", "Johnston");
        group.addStudent("John", "Doe");
        group.addStudent("Tom", "Sawyer");
        group.addStudent("Sasha", "Grey");
        group.addStudent("Ivan", "Petrov");
        group.addStudent("Andrew", "Grey");

        boolean[] presence = new boolean[group.getStudentsQuantity()];

        System.out.println("surname sort");
        group.sort(Group.Mode.BySurname);
        System.out.println(group);


        group.removeStudent(3);


        System.out.println("после удаления студента: ");
        System.out.println(group);

        for (int i = 0; i < presence.length; i++) presence[i] = (Math.random() * 10 > 3);
        group.currentLesson(1, true, presence);

        group.setMark(5, group.getStudent(0), 0);
        group.setMark(9, group.getStudent(1), 0);
        group.setMark(7, group.getStudent(2), 0);
        System.out.println(group);

        Arrays.fill(presence, true);
        group.currentLesson(2, true, presence);

        group.setMark(6, group.getStudent(0), 1);
        group.setMark(2, group.getStudent(1), 1);
        group.setMark(10, group.getStudent(2), 1);
        System.out.println(group);

        System.out.println("presence sort");
        group.sort(Group.Mode.ByPresenceCount);
        System.out.println(group);

        System.out.println("rating sort");
        group.sort(Group.Mode.ByRating);
        System.out.println(group);

        System.out.println("ID sort");
        group.sort(Group.Mode.ByID);
        System.out.println(group);


        System.out.println("пока первые 3 урока:");
        System.out.println(group.printAll());


        for (int i = 3; i < 32; i++) {
            for (int j = 0; j < presence.length; j++) presence[j] = (Math.random() * 10 > 3);
            boolean hw = (Math.random() * 10 > 2);
            group.currentLesson(i, hw, presence);
        }

        System.out.println("провели 32 урока, получили:");
        System.out.println(group.printAll());

        for (int i = 3; i < 32; i++) {
            for (int j = 0; j < group.getStudentsQuantity(); j++) {
                int mark = (int) (Math.random() * 5 + 5);
                //проверить наличие ДЗ перед оцениванием (student.marks[lesson] != -1), а то исключение при попытке оценить ДЗ, которого не было
                if (group.getMark(i, j) != -1)
                group.setMark(mark, group.getStudent(j), i);
            }
        }
        System.out.println("поставили оценки:");
        System.out.println(group.printAll());
        System.out.println();
        System.out.println("поставили оценки. коротко");
        System.out.println(group);


        group.addStudent("Sarah", "Connor");
        System.out.println("добавили после удаления. ждем правильный ИД");
        System.out.println(group);
        //group.setMark(6, group.getStudent(5), 1); //если студент добавлен после i-того урока, то урока i-1 для него не было и поставить за него оценку не получится


        group.clear();
        group.clear();
        System.out.println("после очистки");
        System.out.println(group);


    }
}
