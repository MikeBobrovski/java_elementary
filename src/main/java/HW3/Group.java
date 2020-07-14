/*
1. класс Группа - это группа, синглтон, имеет публичные методы добавить/ удалить студента, поставить оценку, отсортировать (пока только по рейтингу)
2. в метод, возвращающий группу передаем кол занятий. оно задается раз и неизменно
3. метод монитор результатов "проводит урок", определяет, задано ли ДЗ занятия следуют строго по порядку (недьзя провести 5 занятие после третьего или 4 после 4 или 5)
4. оценка может быть выставлена только если ДЗ было задано, если оценить несуществующее задание это вызовет исключение
5. как пробросить режим сортировки из публичного метода во внутренний???--кривое название методов. решено
6. группа - это эрейлист студентов (внутренний класс), у которых есть имя, фамилия, ИД, массив посещений и домашек
7. массив посещений - фальш - пропустил, правда - был
8. массив оценок: 0 - не сдано, -1 - не было ДЗ, 1-10 - оценка
9. ПОСЕЩЕНИЯ студента  в метод "проведене урока" передавать одномерный масси булин, значение - наличие студента на уроке, номер элемента массива == ИД студента

ДОБАВИТЬ
проверка на дублирование студента при добавлении - спорно, возможны полные тезки, так что однозначно определяется только по ИД
добавить развернутый вывод группы по занятиям - добавлено

править
добавить сортировку по следующему полю, если то, по которому сортировка, совпадает
 */

package HW3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//ArrayList
public class Group {

    //-------------------------------------------------------------------------поля группы
    private static Group group; //инстанция группы
    private int lessonsCount;//занятий было
    private final int lessons;//занятий всего
    private int counterID;
    private ArrayList<Student> studentS;

    //-------------------------------------------------------------------------приватные методы группы
    private Group(int lessons) {
        studentS = new ArrayList<Student>();
        this.lessons = lessons;
    }//приватный конструктор, чтоб организовать синглтон: группа у нас пока только одна. групппа - это список студентов

    private static boolean nameIsOK(String arg) {
        final String nameRegEx = "^[A-Z][a-z]{2,16}";
        return arg.matches(nameRegEx);
    }//хелпер для валидации имени/фамилии при добавлении студента

    /*private void removeStudent(Student student) {
        int idRemoved = student.ID;
        studentS.remove(student);
        for (Student st : studentS)
            if (st.ID - 1 == idRemoved) {
                st.ID -= 1;
                idRemoved -= 1;
            }//переписать на цикл фор потому что не применяется для последнего студента
    }//получает объект студент, удаляет, меняет ИД для каждого, у кого он больше (уменьшить все последующие на 1), не меняя сортировки*/

    private void removeStudent(Student student) {
        int idRemoved = student.ID;
        studentS.remove(student);
        for (int i = 0; i < studentS.size(); i++)
            if (studentS.get(i).ID - 1 >= idRemoved) {
                studentS.get(i).ID--;
            }
        counterID--;
    }//получает объект студент, удаляет, меняет ИД для каждого, у кого он больше (уменьшить все последующие на 1), не меняя сортировки. уменьшает счетчик студентов в группе

    public enum Mode {
        BySurname,
        ByRating,
        ByPresenceCount,
        ByID
    }//сортировка студентов по фамилии, посещению, рейтингу, ИД

    private void sortInternal(Mode mode) {
        switch (mode) {
            case BySurname: {
                Collections.sort(studentS, Student.BySurname);
                break;
            }//case surname
            case ByRating: {
                Collections.sort(studentS, Student.ByRating);
                break;
            }//case rating
            case ByPresenceCount: {
                Collections.sort(studentS, Student.ByPresence);
                break;
            }//case presence
            case ByID: {
                Collections.sort(studentS, Student.ByID);
                break;
            }//case ID
        }//switch
    }//сортировка

    @Override
    public String toString() {
        String res = "ID: Name:         Surname:   Rating: Presence:\n";
        for (Student st : studentS) {
            res += st.toString() + "\n";
        }
        return res;
    }//группа в таблицу

    //-------------------------------------------------------------------------контракт группы
    public String printAll() {
        String res = "ID: Name:         Surname:    Marks:    ";
        for (int i = 0; i < group.lessonsCount - 1; i++) res = res + "   ";
        res = res + "Presence:\n";
        for (Student st : studentS) {
            res += st.printAll() + "\n";
        }
        return res;
    }//группа в таблицу

    public static Group getGroup(int lessons) {
        return group = (group == null) ? new Group(lessons) : group;
    }//получить группу

    public void addStudent(String name, String surname) {
        if (nameIsOK(name) && nameIsOK(surname)) studentS.add(new Student(name, surname, counterID++));
        else throw new IllegalArgumentException("something wrong when add student");
    }

    public Student getStudent(int ID) {
        return studentS.get(ID);
    }//не помню зачем, но нужно

    public void removeStudent(int ID) {
        for (Student st : studentS)
            if (st.ID == ID) {
                System.out.println("removeStudent method find" + st);
                removeStudent(st);
                break;
            }
        //throw new IllegalArgumentException("no such student/can't remove");
    }

    public void removeStudent(String surname) {
        Student stToRemove = null;
        boolean theOne = true;
        for (Student st : studentS)
            if (st.surname.equals(surname))
                if (stToRemove == null) stToRemove = st;
                else theOne = false;

        if (theOne && stToRemove != null) removeStudent(stToRemove);
        else
            throw new IllegalArgumentException("no such student/can't remove OR more than ONE student was found by surname");
    }

    public void setMark(int mark, Student student, int lesson) {
        boolean allOK = student != null && lesson <= lessonsCount && lesson <= lessons && mark >= 1 && mark <= 10;//если такой студент существует, урок уже состоялся, урок не вышел за рамки курса, оценка в допуствимом диапазоне
        if (allOK && student.marks[lesson] == 0) student.marks[lesson] = mark;
        else throw new IllegalArgumentException("something wrong when make mark");//и задание "не сдано"
    }

    public void currentLesson(int lessonOrdinal, boolean HW, boolean[] presence) {
        if (++lessonsCount == lessonOrdinal)  //инкремент счетчика занятий
            studentS.forEach(st -> {
                st.marks[lessonsCount - 1] = ((HW) ? 0 : -1);
                st.presence[lessonOrdinal] = presence[st.ID];
            });//установка нуля в оценку, если домашка была задана, оставить дефолтное -1, если не было. получить массивом присутствующих, передать в соответ. поле
        else throw new IllegalArgumentException("wrong lesson's ordinal number");//если попытилсь провести занятие по второму разу или пропустили занятие
    }//аргументом номер занятия, урок состоялся, обновить рейтинги, оценки, была ли домашка (0 или -1)

    public void sort(Mode m) {
        sortInternal(m);
    }

    public void clear() {
        studentS.clear();
    }

    public int getStudentsQuantity() {
        return this.studentS.size();
    }

    public  void updateAchievementsPub(){
        group.studentS.forEach(st -> st.updateAchievements());//-------------------------------------пример вызова приватного метода приватного вложенного класса через вызов паблик метода, который видно из стороннего класса
    }

    public int getMark(int lesson, int student) {
        return studentS.get(student).getMarkSt(lesson);
    }



    //-------------------------------------------------------------------------контракт группы-----------------конец

    //класс студент
    private static class Student {
        //у студента есть имя, фамилия, список посещенных занятий и оценок, номер по порядку записи на курс
        private int ID;
        private String name;
        private String surname;
        private boolean[] presence;//0 - прокосил, размерность берется при создании студеньа из группы, поле уроки - нужно байтов
        private int[] marks;// 0 - не сдано, -1 - не задано/еще не было занятия, 1-10 - оценка
        private int rating;//сумма балов за все домашки
        private int presAll;

        //конструтор приватный, вызывается только через метод группы "добавить". зачем нам самозванцы?
        private Student(String name, String surname, int ID) {
            this.name = name;
            this.surname = surname;
            this.ID = ID;
            marks = new int[group.lessons];
            presence = new boolean[group.lessons];
            for (int i = 0; i < marks.length - 1; i++) {
                marks[i] = -1;
                presence[i] = false;
            }
        }

        @Override
        public String toString() {
            updateAchievements();
            String res = "";
            res = ((this.ID < 10) ? " " + this.ID : this.ID) + " "
                    + " " + fixLength(this.name, 10) + " "
                    + "   " + fixLength(this.surname, 10) + " "
                    + "     " + fixLength(this.rating) + "     "
                    + "   " + fixLength(this.presAll);
            return res;
        }


        public String printAll() {
            String res = "";
            res = ((this.ID < 10) ? " " + this.ID : this.ID) + " "
                    + " " + fixLength(this.name, 10) + " "
                    + "   " + fixLength(this.surname, 10) + " ";
            for (int i = 0; i <= group.lessonsCount; i++) {
                res = res + ((this.marks[i] != -1) ? (((this.marks[i] < 10) ? (" " + this.marks[i]) : this.marks[i]) + " ") : " - ");
            }
            res += "     ";
            for (int i = 0; i <= group.lessonsCount; i++) {
                res = res + ((this.presence[i]) ? 1 : 0) + " ";
            }
            return res;
        }//toString будет выводить кратко. для подробного вывода будет другой метод

        private String fixLength(String arg, int length) {
            if (arg.length() < length) while (arg.length() < length) arg += " ";
            return arg;
        }//хелпер для стройной таблицы

        private String fixLength(int arg) {
            if (arg > 99) return String.valueOf(arg);
            if (arg > 10) return " " + arg;
            return "  " + arg;
        }//хелпер для стройной таблицы

        private void updateAchievements() {
            this.rating = 0;
            this.presAll = 0;
            for (int i = 0; i < marks.length - 1; i++) {
                if (marks[i] != -1) this.rating += marks[i];
                if (presence[i]) this.presAll += 1;
            }//обновить посещения и рейтинг студента

        }//обновить рейтинг



        //--------------------------------------------------------------------------------компараторы
        public static Comparator<Student> ByRating = new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                Integer rating1 = s1.rating;
                Integer rating2 = s2.rating;
                //return rating1.compareTo(rating2); //вперед
                return rating2.compareTo(rating1);//назад
            }
        };

        public static Comparator<Student> ByPresence = new Comparator<>() {
            public int compare(Student s1, Student s2) {
                int presence1 = 0;
                for (boolean p : s1.presence) if (p) presence1++;
                Integer pr1 = presence1;
                int presence2 = 0;
                for (boolean p : s2.presence) if (p) presence2++;
                Integer pr2 = presence2;
                //return (pr1.compareTo(pr2)); //вперед
                return pr2.compareTo(pr1);//назад
            }
        };

        public static Comparator<Student> BySurname = (s1, s2) -> {
            String surname1 = s1.surname;
            String surname2 = s2.surname;
            return surname1.compareTo(surname2); //вперед
            //return surname2.compareTo(surname1);//назад
        };

        public static Comparator<Student> ByID = new Comparator<Student>() {
            public int compare(Student s1, Student s2) {
                Integer id1 = s1.ID;
                Integer id2 = s2.ID;
                return id1.compareTo(id2); //вперед
                //return id2.compareTo(id1);//назад
            }
        };

        public int getMarkSt(int lesson) {
            return this.marks[lesson];
        }
        //--------------------------------------------------------------------------------компараторы-----------------конец
    }//Student
}//Group, ArrayList<Student>


/*
сортировка по одному полю 
implements Comparable
@Override
        public int compareTo(Object arg) {
            int otherRating = ((Student) arg).getRating();
            return otherRating - this.rating;
        }
Collections.sort(studentS);


System.out.println("method clear called");
        if (studentS.size() == 0) return;
        for (int i = 0; i < studentS.size(); ) {
            System.out.println("next student will be removed: " + i);
            studentS.remove(group.getStudent(0));//в этой строке протсходит i++. как? тут отвечают, но не убедительно "https://overcoder.net/q/3230757/arraylist-remove-%D0%BD%D0%B5-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D0%B5%D1%82"
        }

    public void clear(){
        System.out.println("method clear called");
        if (studentS.size() == 0) return;
        studentS.forEach(this::removeStudent);
    }
    public void clear(){
        System.out.println("method clear called");
        if (studentS.size() == 0) return;
        studentS.forEach(st -> removeStudent(st));
    }

 */

