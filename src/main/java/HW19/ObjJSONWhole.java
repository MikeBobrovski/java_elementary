/*
сохранение и восстановление студентов в JSON-файл без попыток выстрелить в ногу (методом записи одной коллекции с множеством студентов)
 */
package HW19;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;

public class ObjJSONWhole {
    File fileJSON;

    public ObjJSONWhole(File f) {
        this.fileJSON = f;
    }

    public void setupAll() {
        fileJSON = new File("D://hw19", "students.txt");
        try {
            if (fileJSON.createNewFile())
                System.out.println("Files has been created");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void write(File f, Student[] st) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(st);
        try (PrintStream printStream = new PrintStream(new FileOutputStream(f.toString(), true))) {
            printStream.println(json);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Student[] read(File f) {
        ObjectMapper mapper = new ObjectMapper();
        Student[] st = null;
        try {
            st = mapper.readValue(f, Student[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
}
