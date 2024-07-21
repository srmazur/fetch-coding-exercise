package fetch.exercise.page;

import fetch.exercise.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageElements {

    private static final WebDriver driver = Driver.getDriver();

    public static final String PAGE_URL = "http://sdetchallenge.fetch.com/";

    public String leftPrefix = "left_", rightPrefix = "right_";

    public final String foundBarMessage = "Yay! You find it!";
    public final String noBarFoundMessage = "Fail to find the fake gold bar!";
    public PageElements(WebDriver driver) {
        PageFactory.initElements(PageElements.driver, this);
    }

    @FindBy(xpath = "//div[@class='game-info']")
    public WebElement gameResults;


    @FindBy(xpath = "//div[@class='result']//button[@id='reset']")
    public WebElement operator;

    @FindBy(id = "weigh")
    public WebElement weighButton;

    @FindBy(xpath = "//button[text()='Reset']")
    public WebElement resetButton;

    public WebElement getFakeBarButton(WebDriver driver, int fakeBar) {
        return driver.findElement(By.id("coin_" + fakeBar));
    }

    public WebElement getCell(WebDriver driver, String prefix, int cellId) {
        return driver.findElement(By.id(prefix + cellId));
    }
}
