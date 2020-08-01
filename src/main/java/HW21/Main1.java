/**
 * здесь пароль перебирался по словарю из файла или веб-страницы (последнее не имеет практического применения ввиду скорости)
 */

package HW21;

import HW21.Encryptor;
import SELENIUM.Browser;
import Util.TimeHandler;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        //исходные данные
        String hash1 = "f016441d00c16c9b912d05e9d81d894d";
        String pass1 = "very";
        String hash2 = "5ebe2294ecd0e0f08eab7690d2a6ee69";
        String pass2 = "secret";
        String hash3 = "13d70e09909669272b19647c2a55dacb";
        String pass3 = "goodforyou";
        String hash4 = "5f50dfa5385e66ce46ad8d08a9c9be68";

        //первый пароль через браузер и словарь
        TimeHandler.start();
        String passGetFromWeb = getPassThroughBrowser(hash1);
        //Assert.assertEquals(pass1, passGetFromWeb);
        System.out.println("через браузер и словарь: " + passGetFromWeb);
        Util.Logger.getLogger().log("взлом первого пароля в одном потоке: " + TimeHandler.stop());

        //первый пароль через файл и словарь
        TimeHandler.start();
        String passGetFromFile = getPassThroughFile(hash1);
        //Assert.assertEquals(pass1, passGetFromFile);
        System.out.println("через файл и словарь: " + passGetFromFile);
        Util.Logger.getLogger().log("взлом первого пароля в одном потоке: " + TimeHandler.stop());

        //второй пароль
        TimeHandler.start();
        String passGetFromDict = getPassThroughFile(hash2);
        //Assert.assertEquals(pass2, passGetFromDict);
        System.out.println("через файл и словарь: " + passGetFromDict);
        Util.Logger.getLogger().log("взлом второго пароля в одном потоке: " + TimeHandler.stop());

    }

    public static String getPassThroughFile(final String hash) {
        //этот метод работает стабильно быстрее, чем getPassThroughFile2(). наверно, сканер медленнее
        try (BufferedReader br = new BufferedReader(new FileReader("D://hw19/words.txt"))) {
            //чтение построчно
            String s;
            while ((s = br.readLine()) != null) {
                if (hash.equals(Encryptor.md5(s))) {
                    return s;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "can't find";
    }

    public static String getPassThroughFile2(String hash) {
        File dictionary = new File("D://hw19", "words.txt");
        try (Scanner scanner = new Scanner(dictionary)) {
            while (scanner.hasNext()) {
                String s = scanner.next();
                if (hash.equals(Encryptor.md5(s))) {
                    return s;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return "can't find";
    }

    public static String getPassThroughBrowser(final String hash) {
        //этот метод я написал первым, думал будет быстрее, чем перебор посимвольно, но браузер оказался довольно медленным, а апи, скорее всего нет у этого сайта
        //перепиывать уже не буду, особенно, учитывая, что метод second() так же отработает

        final String link = "https://www.thefreedictionary.com/4-letter-words.htm";//тут словарь
        WebDriver browser = Browser.getBrowser("chrome");//инициализация браузера
        browser.get(link);//идем по ссылке
        List<WebElement> wordsElements = browser.findElements(By.xpath("//*[@id=\"dCont\"]/div[1]/ul/li/a"));//получаем лист веб елементов с нужными словами

        WebElement[] elementsInArr = new WebElement[wordsElements.size()];//лист в массив
        wordsElements.toArray(elementsInArr);
        String res = "can't find";
        for (int i = 0; i < elementsInArr.length; i++) {
            if (hash.equals(Encryptor.md5(elementsInArr[i].getText()))) {
                res = elementsInArr[i].getText();
                break;
            }
        }
        browser.quit();//больше не нужен
        return res;
    }

}

