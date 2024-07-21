package fetch.exercise.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.Objects;

public class Driver {
    private static WebDriver driver = null;
    private static final String URL_GRID_DOCKER = ConfigReader.readProperty("URL_GRID_DOCKER");
    private static final String URL_GRID_LOCAL = ConfigReader.readProperty("URL_GRID_LOCAL");
    private static final int RETRY_DELAY_MS = 5000;
    private static final int MAX_RETRIES = 5;

    public static void initialize() {
        if (Objects.equals(ConfigReader.readProperty("runLocally"), "true")) {
            initializeLocalDriver();
        } else {
            if (Objects.equals(ConfigReader.readProperty("runInDocker"), "true")) {
                initializeRemoteDriver(URL_GRID_DOCKER);
            } else {
                initializeRemoteDriver(URL_GRID_LOCAL);
            }
        }
    }

    private static void initializeLocalDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    private static void initializeRemoteDriver(String grid_url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                URI uri = new URI(grid_url);
                driver = new RemoteWebDriver(uri.toURL(), options);
                break;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize remote WebDriver", e);
            } catch (Exception e) {
                retries++;
                System.out.println("Retrying to connect to Selenium Grid... (" + retries + "/" + MAX_RETRIES + ")");
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (driver == null) {
            throw new RuntimeException("Failed to initialize remote WebDriver after: " + MAX_RETRIES + " attempts");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    public static WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            initialize();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null)
            driver.quit();
        driver = null;
    }
}