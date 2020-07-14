package HW19;

import Experiment.TimeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FIOTest {
    //константа колличества студентов
    public static final int QUANTITY = 10_000;
    //файлы для записи
    static File objEach, objWhole, jsonWhole, jsonEach;
    //коллекции для пересыпания студентов в файл/из файла, сравнения рез.
    ArrayList<Student> stud;
    ArrayList<Student> studentsFromObjWhole;
    ArrayList<Student> studentsFromObjEach;
    //для библиотечного метода, который не умеет работать с коллекциями
    Student[] arrayOfStud;


    @Before
    public void setup() {
        //генерируем коллекцию случайных студентов
        stud = Student.studentGenerator(QUANTITY);
        //необходимые инициаллизации
        studentsFromObjEach = new ArrayList<>();
        //для работы с JSON используем массив, потому что в библиотеке нет метода, умеющего работать с коллекцией
        arrayOfStud = new Student[stud.size()];
        stud.toArray(arrayOfStud);//как раз тот метод, где пишем в типизированный массив
    }

    @After
    public void tearDown() {
    }

    @BeforeClass
    public static void setupAll() {
        System.out.println();
        objEach = new File("D://hw19", "stSingleObj.data");
        objWhole = new File("D://hw19", "stWholesale.data");
        jsonWhole = new File("D://hw19", "studentsJSWhole.txt");
        jsonEach = new File("D://hw19", "studentsJSEach.txt");
        try {
            if (objEach.createNewFile() & objWhole.createNewFile() & jsonWhole.createNewFile() & jsonEach.createNewFile()) {
                Util.Logger.getLogger().log("Files has been created");
            } else {
                Assert.fail("Files has NOT been created");//если какой-то файл не создался, то нечего тянуть, все равно тест не пройдет
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Util.Logger.getLogger().log("время выполнения будет выведено в формате min:sec.millis.micros.nanos,\n\t\t\t\t\t\t\tколичество объектов = " + QUANTITY/1000 + "K");
    }

    @AfterClass
    public static void tearDownAll() {
        System.out.println();
        if (objEach.delete() & objWhole.delete() & jsonWhole.delete() & jsonEach.delete()) {
            Util.Logger.getLogger().log("Files has been deleted");
        } else {
            System.err.println("Files has NOT been deleted");//тут без ассерта потому что предупредить бы надо, но уже не критично, если тест прошел
        }
    }

    @Test
    public void ObjWhole() {
        System.out.println();
        Util.Logger.getLogger().log("сериализация джава / коллекции из объектов");
        ObjFileWhole wholeInst = new ObjFileWhole(objWhole);
        TimeHandler.start();
        wholeInst.write(stud);
        Util.Logger.getLogger().log("запись: " + TimeHandler.stop());
        Util.Logger.getLogger().log("размер файла: " + objWhole.length() + " байт");
        TimeHandler.start();
        studentsFromObjWhole = wholeInst.read();
        Util.Logger.getLogger().log("чтение: " + TimeHandler.stop());
        Assert.assertEquals(stud, studentsFromObjWhole);
        Util.Logger.getLogger().log("сериализация джава / коллекции окончена");
    }

    @Test
    public void ObjEach() {
        System.out.println();
        Util.Logger.getLogger().log("сериализация джава / поштучно для каждого объекта");
        ObjFileEach eachInst = new ObjFileEach(objEach);
        TimeHandler.start();
        Iterator<Student> stIt = stud.iterator();
        while (stIt.hasNext()) {
            eachInst.write(stIt.next());
        }
        Util.Logger.getLogger().log("запись: " + TimeHandler.stop());
        Util.Logger.getLogger().log("размер файла: " + objEach.length() + " байт");
        TimeHandler.start();
        for (int i = 0; i < QUANTITY; i++) {
            studentsFromObjEach.add(eachInst.read());
        }
        Util.Logger.getLogger().log("чтение: " + TimeHandler.stop());
        Assert.assertEquals(stud, studentsFromObjEach);
        studentsFromObjEach.add(new Student(20, "dsfsfs"));//на всякий случай
        Assert.assertNotEquals(stud, studentsFromObjEach);
        eachInst.close();
        Util.Logger.getLogger().log("сериализация джава / поштучно для каждого объекта окончена");
    }

    @Test
    public void jsonEach() throws JsonProcessingException {
        System.out.println();
        Util.Logger.getLogger().log("сериализация посредством JACKSON lib поштучно для каждого объекта");
        ObjJSONEach jsEach = new ObjJSONEach(jsonEach);
        Student[] stArr = new Student[stud.size()];
        stud.toArray(stArr);
        TimeHandler.start();
        for (int i = 0; i < stArr.length; i++) {
            jsEach.write(jsonEach, stArr[i]);
        }
        Util.Logger.getLogger().log("запись: " + TimeHandler.stop());
        Util.Logger.getLogger().log("размер файла: " + jsonEach.length() + " байт");
        TimeHandler.start();
        //читаем в одну строку
        String str = jsEach.read(jsonEach);
        //делим на json-строки (1 стр = 1 объект) и кладем в массив
        String[] arStrSt = str.split("[}{]");

        //массив для студентов, восстановленных сканером и самописным парсингом из невалидного джсона
        Student[] restored = new Student[arStrSt.length];
        ArrayList<Student> studRestored = new ArrayList<>();
        for (String retval : arStrSt) {
            if (retval.length() > 1) {//этот if - костыль. я писал в группу, split("[}{]") в два раза больше строк, половина - пустые. буду потом разбираться
                //дописываем скобки, которых не сзватает
                retval = "{" + retval + "}";
                //восстанавливаем студента из json-строки
                studRestored.add(jsEach.read(retval));
            }
        }
        Util.Logger.getLogger().log("чтение: " + TimeHandler.stop());
        Assert.assertEquals(stud, studRestored);
        studRestored.add(new Student());
        Assert.assertNotEquals(stud, studRestored);
        Util.Logger.getLogger().log("сериализация посредством JACKSON lib поштучно для каждого объекта окончена");
    }

    @Test
    public void jsonWhole() throws JsonProcessingException {
        System.out.println();
        Util.Logger.getLogger().log("сериализация посредством JACKSON lib коллекции объектов");
        ObjJSONWhole js = new ObjJSONWhole(jsonWhole);
        TimeHandler.start();
        js.write(jsonWhole, arrayOfStud);
        Util.Logger.getLogger().log("запись: " + TimeHandler.stop());
        Util.Logger.getLogger().log("размер файла: " + jsonWhole.length() + " байт");
        TimeHandler.start();
        Student[] rest = js.read(jsonWhole);
        Util.Logger.getLogger().log("чтение: " + TimeHandler.stop());
        ArrayList<Student> studentsFromWhole = new ArrayList(Arrays.asList(rest));
        Assert.assertEquals(stud, studentsFromWhole);
        Util.Logger.getLogger().log("сериализация посредством JACKSON lib коллекции окончена");
    }

}//class FIOTest
