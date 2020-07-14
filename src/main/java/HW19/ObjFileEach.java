/*
этот файл - продукт решения проблемы "invalid type code: AC" при попытке писать файлы один задругим через код в файле ObjFileWhole.
изобретение велосипеда снова, но здесь результат производительности соизмерим с сериализацией одной коллекции студентов, так что цель достигнута
 */


package HW19;

import java.io.*;

public class ObjFileEach {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public ObjFileEach(File file) {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void write(Student st) {
        try {
            oos.writeObject(st);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Student read() {
        Student student = null;
        try {
            student = (Student) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return student;
    }

    public void close() {
        try {
            oos.close();
            ois.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
