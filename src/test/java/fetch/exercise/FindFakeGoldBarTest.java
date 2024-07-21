package fetch.exercise;

import fetch.exercise.page.PageElements;
import fetch.exercise.utils.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static fetch.exercise.utils.SeleniumUtils.*;

public class FindFakeGoldBarTest {

    private static WebDriver driver;
    private static PageElements pageElements;
    private static final List<Integer> BARS = IntStream.range(0, 9)
            .boxed()
            .toList();

    @BeforeClass
    public void setUp() {
        driver = Driver.getDriver();
        driver.get(pageElements.PAGE_URL);
        pageElements = new PageElements(driver);
    }

    @Test
    public void testFindFakeGoldBar() {
        List<Integer> fakeBar = recursiveBarRefiner(BARS);
        click(pageElements.getFakeBarButton(driver, fakeBar.getFirst()));
        String alertMessage = handleAlertMsg(driver);
        reportLogAlertMsg(alertMessage);
        reportLogGameResult(pageElements.gameResults, fakeBar.getFirst());
        Assert.assertTrue(alertMessage.contains(pageElements.foundBarMessage), pageElements.noBarFoundMessage);
    }

    @AfterClass
    public void tearDown() {
        Driver.quitDriver();
    }

    private List<Integer> recursiveBarRefiner(List<Integer> list) {
        if (list.size() == 1) {
            return list;
        }

        List<List<Integer>> dividedBars = divideListIntoThree(list);
        List<Integer> refineBars = weighBars(dividedBars);

        return recursiveBarRefiner(refineBars);
    }

    private List<List<Integer>> divideListIntoThree(List<Integer> list) {
        int size = list.size() / 3;
        List<List<Integer>> result = new ArrayList<>();

        result.add(List.copyOf(list.subList(0, size)));
        result.add(List.copyOf(list.subList(size, size * 2)));
        result.add(List.copyOf(list.subList(size * 2, list.size())));

        return result;
    }

    private List<Integer> weighBars(List<List<Integer>> dividedBars) {
        click(pageElements.resetButton);

        fillWithBars(dividedBars.get(0), pageElements.leftPrefix);
        fillWithBars(dividedBars.get(1), pageElements.rightPrefix);

        click(pageElements.weighButton);

        String operator = getOperator();

        return determineOutcome(operator, dividedBars);
    }

    private void fillWithBars(List<Integer> bars, String prefix) {
        for (int i = 0; i < bars.size(); i++) {
            sendKeys(pageElements.getCell(driver, prefix, i), bars.get(i).toString());
        }
    }

    private String getOperator() {
        waitForOperatorToLoad(pageElements.operator);
        return getText(pageElements.operator);
    }

    private List<Integer> determineOutcome(String operator, List<List<Integer>> dividedBars) {
        return switch (operator) {
            case "=" -> new ArrayList<>(dividedBars.get(2));
            case "<" -> new ArrayList<>(dividedBars.get(0));
            case ">" -> new ArrayList<>(dividedBars.get(1));
            default -> null;
        };
    }
}
