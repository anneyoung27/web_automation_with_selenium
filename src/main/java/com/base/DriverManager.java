package com.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class DriverManager {
    public static WebDriver driver;
    public static Properties setUp = new Properties();
    public static FileInputStream fis;
    public static WebDriverWait wait;

    public static String browser;

    public static Logger log = LogManager.getLogger();

    public static void setUp() {
        if (driver == null) {
            try {
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\main\\resources\\config\\setUp.properties");
                log.info("setUp.properties file has been found");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                setUp.load(fis);
                log.info("setUp.properties file has been loaded");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
                browser = System.getenv("browser");

            } else {
                browser = setUp.getProperty("browser");
            }

            setUp.setProperty("browser", browser);
            initializeDriver();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(setUp.getProperty("implicit.wait"))));
            driver.manage().window().maximize();
            driver.get(setUp.getProperty("url"));
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(setUp.getProperty("explicit.wait"))));
        }
    }

    private static void initializeDriver() {
        switch (setUp.getProperty("browser").toLowerCase()){
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Invalid browser specified in configuration: " + setUp.getProperty("browser"));
        }
        log.info("{} browser has been selected", setUp.getProperty("browser"));
    }

    public static void tearDown(){
        if (driver != null){
            driver.quit();
            log.info("Browser closed");
        }
    }
}