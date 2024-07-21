package fetch.exercise.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SeleniumUtils {
    private static final WebDriver driver = Driver.getDriver();

    public static void sleep(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void waitForClickability(WebElement element) {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibilityOfElement(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOf(element));

    }

    public static void waitForOperatorToLoad(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> element.getText().matches(".*[<>=].*"));

    }

    public static void click(WebElement element) {
        waitForClickability(element);
        highlightElement(element);
        element.click();
    }

    public static void sendKeys(WebElement element, String input) {
        waitForVisibilityOfElement(element);
        element.sendKeys(input);
    }

    public static String getText(WebElement element) {
        waitForVisibilityOfElement(element);
        highlightElement(element);
        captureScreenshot();
        return element.getText();
    }

    public static void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 2; i++) {
            try {
                if (i == 0) {
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);"
                            , element, "color: black; border: 3px solid orange; background: yellow");
                } else {
                    sleep(500);
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);"
                            , element, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String handleAlertMsg(WebDriver driver) {
        String alertMessage = "";
        try {
            Alert alert = driver.switchTo().alert();
            alertMessage = alert.getText();
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present after clicking the element.");
            e.printStackTrace();
        }
        return alertMessage;
    }

    public static void captureScreenshot() {
        try {
            File screenshot = takeScreenshot();
            File screenshotName = createScreenshotFile(screenshot);
            logScreenshot(screenshotName);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    private static File takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    private static File createScreenshotFile(File screenshot) throws IOException {
        String screenshotPath = "test-output/screenshots/screenshot_" + currentDateTime() + ".jpg";
        File screenshotFile = new File(screenshotPath);
        FileUtils.copyFile(screenshot, screenshotFile);
        return screenshotFile;
    }

    public static String currentDateTime() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("HH-mm-ss_dd_MM_yyyy");
        LocalDateTime now = LocalDateTime.now();
        return date.format(now);
    }

    private static void handleIOException(IOException e) {
        Reporter.log("Failed to capture screenshot: " + e.getMessage());
        e.printStackTrace();
    }

//  Formatting the output for testng report
    public static void reportLogGameResult(WebElement gameResultStr, Integer actualResult){
        Reporter.log("</br><font>_______________ Game Result _______________</font><br>");
        List<String> gameResultList = List.of(gameResultStr.getText().split("\\R"));
        for(String result: gameResultList){
            Reporter.log(result);
            Reporter.log("</br><font> </font><br>");
        }
        Reporter.log("</br><font>________________ The fake Bar ______________</font><br>");
        Reporter.log(" | The fake bar is: "+ actualResult + " |");
        Reporter.log("</br><font>____________________________________________</font><br>");
    }

    private static void logScreenshot(File screenshotFile) {
        String destFile = "/test-output/screenshots/" + screenshotFile.getName();
        Reporter.log("</br><font>_______________ Screenshot _______________</font><br>");
        Reporter.log("<br> <img src=\"" + destFile + "\" alt=\"\" style=\"width: 900px; height: 500px;\" /> <br>");
    }

    public static void reportLogAlertMsg(String alertMsg) {
        Reporter.log("</br><font>_______________ Alert Message _______________</font><br>");
        Reporter.log(alertMsg);
        Reporter.log("</br><font> </font><br>");
    }
}