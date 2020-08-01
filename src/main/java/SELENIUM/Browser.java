package SELENIUM;/*
SingleTon class to get one instance of browser
 */

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class Browser {
    private static WebDriver driver;

    public enum BrowserName {
        CHROME,
        FIREFOX,
        OPERA,
    }//выбрать только из меющихся драйверов

    Browser() {
    }

    public static WebDriver getBrowser(String browserName) {
        if (driver == null) {
            driver = giveMeBrowser(browserName);
        }//возможно, если браузер уже открыт, а мы хотим новый, то стоит на старом переключиться на новую вклаку
        return driver;
    }

    private static WebDriver giveMeBrowser(String browserName) {
        WebDriver browser;
        switch (browserName.toLowerCase().trim()) {
            case "firefox": {
                final String binPath = String.format("%s/bin/geckodriver.exe", System.getProperty("user.dir"));
                System.setProperty("webdriver.gecko.driver", binPath);
                browser = new FirefoxDriver();
                break;
            }
            case "chrome": {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--verbose");
                chromeOptions.addArguments("--whitelisted-ips=\"\"");
                final String binPath = String.format("%s/bin/chromedriver.exe", System.getProperty("user.dir"));
                System.setProperty("webdriver.chrome.driver", binPath);
                browser = new ChromeDriver(chromeOptions);
                break;
            }
            case "opera": {
                final String binPath = String.format("%s/bin/operadriver.exe", System.getProperty("user.dir"));
                System.setProperty("webdriver.opera.driver", binPath);
                browser = new OperaDriver();
                break;
            }
            default:
                throw new IllegalArgumentException("Have no such driver");
        }
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        browser.manage().window().setSize(new Dimension(50, 50));
        //browser.manage().window().fullscreen();
        //browser.manage().window().maximize();

        return browser;
    }

    private static WebDriver giveMeBrowser(BrowserName browserName) {
        WebDriver browser;
        switch (browserName) {
            case FIREFOX: {
                final String binPath = String.format("%s/bin/geckodriver.exe", System.getProperty("user.dir"));
                System.setProperty("webdriver.gecko.driver", binPath);
                browser = new FirefoxDriver();
                break;
            }
            case CHROME: {
                final String binPath = String.format("%s/bin/chromedriver.exe", System.getProperty("user.dir"));
                System.setProperty("webdriver.chrome.driver", binPath);
                browser = new ChromeDriver();
                break;
            }
            case OPERA: {
                final String binPath = String.format("%s/bin/operadriver.exe", System.getProperty("user.dir"));
                System.setProperty("webdriver.opera.driver", binPath);
                browser = new OperaDriver();
                break;
            }
            default:
                throw new IllegalArgumentException("Have no such driver");//если арг через перечисление, то это не нужно?
        }
        try {
            Thread.sleep(50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*browser.manage().window().setSize(new Dimension(500, 380));
        browser.manage().window().fullscreen();*/
        browser.manage().window().maximize();
        return browser;
    }
}
