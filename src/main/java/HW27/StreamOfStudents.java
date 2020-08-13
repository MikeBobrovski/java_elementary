package HW27;

import HW19.Student;

import java.util.List;

public class StreamOfStudents {
    public static void main(String[] args) {
        final int QUANTITY = 20;
        List<Student> students = Student.studentGenerator(QUANTITY);
        students.stream()
                .peek(System.out::println)
                .map(Student::getLastName)
                .map(String::toUpperCase)
                .filter(StreamOfStudents::vowels)
                .sorted()
                .forEach(System.out::println);
    }

    private static boolean vowels(String s) {
        int counter = 3;
        String vowelStr = "AaEeIiOoUuYy";//можно сократить алфавит до больших букв, если уж используем после  .map(String::toUpperCase)
        char[] word = s.toCharArray();
        for (char w : word) {
            if (vowelStr.indexOf(w) != -1) {
                counter --;
                if (counter == 0) return true;
            }
        }
        return false;
    }
}
