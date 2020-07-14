/*
студент, который умееет генерировать коллекцию заданого размера самого себя
 */

package HW19;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int lastID;
    private int id;
    private String lastName;
    private int age;


    public Student(int age, String lastName) {
        if (lastName == null || lastName.length() < 2 || age < 7 || age > 70)
            throw new IllegalArgumentException("bad student");
        this.id = lastID++;
        this.lastName = lastName;
        this.age = age;
    }

    public Student() {
    }

    public static int getLastID() {
        return lastID;
    }

    public int getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return age;
    }
    /*public void setId(int id) {
        this.id = id;
    }*/

    /*public void setLastName(String lastName) {
        this.lastName = lastName;
    }*/

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("'" + lastName + "'")
                .add("age=" + age)
                .toString();
    }//toString

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        Student otherStudent = (Student) other;
        if (this.id != otherStudent.id) return false;
        boolean ln = (lastName != null) ? this.lastName.equals(otherStudent.lastName) : otherStudent.lastName == null;
        boolean id = this.id == otherStudent.id;
        return id & ln;
    }//equals

    @Override
    public int hashCode() {
        return this.id;
    }

    public static ArrayList<Student> studentGenerator(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("введите приемлемое колличество студентвов");
        ArrayList<Student> st = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int age = (int) (8 + Math.random() * 60);
            st.add(new Student(age, generateString()));
        }
        return st;
    }


    public static String generateString() {
        int targetStringLength = (int) (3 + Math.random() * 17);
        char[] ch = new char[targetStringLength];
        ch[0] = (char) (65 + Math.random() * (90 - 65));
        for (int i = 1; i < targetStringLength; i++) {
            ch[i] = (char) (97 + Math.random() * (122 - 97));
        }
        return new String(ch);
    }

}//Student
