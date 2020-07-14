package HW19;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static File fileJSON = new File("D://hw19", "students.txt");

    public static void main(String[] args) throws JsonProcessingException {
        ObjJSONEach jsEach = new ObjJSONEach(fileJSON);
        jsEach.setupAll();

        ArrayList<Student> stud = Student.studentGenerator(10_000);
        Student[] stArr = new Student[stud.size()];
        stud.toArray(stArr);
        Util.Logger.getLogger().log("начали писать");
        for (int i = 0; i < stArr.length; i++) {
            jsEach.write(fileJSON, stArr[i]);
        }
        Util.Logger.getLogger().log("письнули поштучно в кривой джсон");

        //читаем сканнером
        String str = jsEach.read(fileJSON);
        System.out.println("сканером: " + str);
        //делим на строки ждсон и кладем в массив
        String[] arStrSt = str.split("[}{]");

        //массив для студентов, восстановленных сканером и самописным парсингом из невалидного джсона
        Student[] restored = new Student[arStrSt.length];
        ArrayList<Student> studRestored = new ArrayList<>();
        for (String retval : arStrSt) {
            if (retval.length() > 1) {
                System.out.println(retval);
                //дописываем скобки, которых не сзватает
                retval = "{" + retval + "}";
                //восстанавливаем студента из ждсон строки
                Student st = jsEach.read(retval);
                studRestored.add(st);
            }
        }

        Assert.assertEquals(stud, studRestored);

        fileJSON.delete();
    }

}

/*
final String ob = "[\\{][\\n](([\\s]{2}((\\\"([\\w]{1,127})\\\"\\s\\:\\s\\\"?([\\w]{1,127})\\\"?)\\,?\\n)){3})[\\n\\}\\n]";//дохлый номер
 */
