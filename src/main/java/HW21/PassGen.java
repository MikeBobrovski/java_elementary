/**
 * здесь ничего интересного
 */

package HW21;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class PassGen {


    public static String[] getFromWEB(WebDriver browser) {
        //уже не нужен, оставил себе на потом
        final String link = "https://www.thefreedictionary.com/4-letter-words.htm";
        browser.get(link);
        List<WebElement> wordsElements = browser.findElements(By.xpath("//*[@id=\"dCont\"]/div[1]/ul/li/a"));
        String[] words = new String[wordsElements.size()];
        WebElement[] elementsInArr = new WebElement[wordsElements.size()];
        wordsElements.toArray(elementsInArr);
        for (int i = 0; i < elementsInArr.length; i++) {
            words[i] = elementsInArr[i].getText();
        }
        return words;
    }


    public static String[] getFromWEB2(WebDriver browser) {
        //для второго пароля, если надумаю тянуть из сети
        //но, скорее всего, из файла. на стр ного ветвлений. можно, но есть же файл)
        final String link = "https://dictionary.cambridge.org/browse/english/a/";
        browser.get(link);
        List<WebElement> wordsElements = browser.findElements(By.xpath("//*[@id=\"dCont\"]/div[1]/ul/li/a"));
        String[] words = new String[wordsElements.size()];
        WebElement[] elementsInArr = new WebElement[wordsElements.size()];
        wordsElements.toArray(elementsInArr);
        for (int i = 0; i < elementsInArr.length; i++) {
            words[i] = elementsInArr[i].getText();
        }
        return words;
    }

    public static String[] getFromFile() {
        File dictionary = new File("D://hw19", "words.txt");
        StringBuffer res = new StringBuffer("");
        try (Scanner scanner = new Scanner(dictionary)) {
            while (scanner.hasNext()) {
                res.append(scanner.next());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        Util.Logger.getLogger().log(res.toString());
        String[] words = res.toString().split("\n");
        Util.Logger.getLogger().log(words.length + "");
        return words;
    }

}
