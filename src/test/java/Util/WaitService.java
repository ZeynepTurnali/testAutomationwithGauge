package Util;

import base.BaseUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class WaitService {

    public Wait<WebDriver> wait;


    public WaitService() {
        wait = new FluentWait<WebDriver>(BaseUtil.getWebDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
    }


    public WebElement waitUntilElementToBePresence(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }

    public List<WebElement> waitUntilElementsToBePresence(By by) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

    }

    public WebElement waitUntilElementToBeVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));

    }

    public Boolean waitUntilElementToLoadPage() {
        Boolean state = wait.until((ExpectedCondition<Boolean>) webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        return state;
    }


}