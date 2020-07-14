/*
сохранение и восстановление студентов мотодом сериализации без попыток выстрелить в ногу
 */

package HW19;

import java.io.*;
import java.util.ArrayList;

public class ObjFileWhole {
    File fileWholesale;

    public ObjFileWhole(File fileWholesale) {
        this.fileWholesale = fileWholesale;
    }

    public void write(ArrayList<Student> st) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileWholesale))) {
            oos.writeObject(st);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Student> read() {
        ArrayList<Student> students = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileWholesale))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return students;
    }
}
