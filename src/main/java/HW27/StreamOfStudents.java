package HW27;

import HW19.Student;

import java.util.List;

public class StreamOfStudents {
    public static void main(String[] args) {
        final int QUANTITY = 20;
        List<Student> students = Student.studentGenerator(QUANTITY);//это ненератор случайных студентов из 19 домашки
        students.stream()
                .peek(System.out::println) //посмотрим всех
                .map(Student::getLastName)//получим имена
                .map(String::toUpperCase)//вроде очевидно
                .filter(StreamOfStudents::vowelsRegEx)// фильтр по заданому условию. если так не канает, то .matches("(.*[AaEeIiOoUuYy].*){3}"))
                .sorted()//сортировка от А до Я
                .forEach(System.out::println);//распечатаем всех. если не канает, то .collect(Collectors.toList());, а перед студентами лист =
    }

    private static boolean vowels(String s) {
        int counter = 3;
        String vowelStr = "AaEeIiOoUuYy";//можно сократить алфавит до больших букв, если уж используем после  .map(String::toUpperCase),
        // но тут спроный момент: y может быть глвсным или согласным, и есть подозрение, что вначале, когда а большой, это согласная, а в конце - Nagorny - гласная
        char[] word = s.toCharArray();
        for (char w : word) {
            if (vowelStr.indexOf(w) != -1) {
                counter --;
                if (counter == 0) return true;
            }
        }
        return false;
    }

    private static boolean vowelsRegEx(String s) {
        final String RegEx = "(.*[AaEeIiOoUuYy].*){3}";
        return s.matches(RegEx);
    }
}
