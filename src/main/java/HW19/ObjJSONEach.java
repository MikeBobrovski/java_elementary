/*
изобрел велосипед: пишу в JSON объекты поштучно, получаю невалидный JSON,
но потом героически его восстанавливаю в объекты через сканнер, сплит, манипуляции со строками
получаю неприемлемые 30 сек (на моем железе), вывод - не надо изобретать
быстродействие может хромать на обработке строк, но StringBuffer выдает чушь местами (я поднимал вопрос), потому оставил его на потом.
 */

package HW19;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.Scanner;

public class ObjJSONEach {
    File fileJSON;

    public ObjJSONEach(File f) {
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

    public String read(File f) {
        StringBuffer res = new StringBuffer("");
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNext()) {
                res.append(scanner.next());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return res.toString();
    }
/*
FIXME сюда не смотреть, это я себе развлечение на потом оставил

    public String read2(File f) {
        StringBuffer res = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            int c;
            while ((c = br.read()) != -1) {
                res.append((char)c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return res.toString();
    }

    public String remover(String s){
        StringBuffer res = new StringBuffer(s);
        while(res.indexOf("\n") != -1){
            res.deleteCharAt(res.indexOf("\n"));
        }
        while(res.indexOf(" ") != -1){
            res.deleteCharAt(res.indexOf(" "));
        }
        return res.toString();
    }
*/

    public void write(File f, Student st) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(st);
        try (PrintStream printStream = new PrintStream(new FileOutputStream(f.toString(), true))) {
            printStream.println(json);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Student read(String j) {
        ObjectMapper mapper = new ObjectMapper();
        Student st = null;
        try {
            st = mapper.readValue(j, Student.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
}

//final String regEx = "[\\{][\\n](([\\s]{2}((\\\"([\\w]{1,127})\\\"\\s\\:\\s\\\"?([\\w]{1,127})\\\"?)\\,?\\n)){3})[\\n\\}\\n]";