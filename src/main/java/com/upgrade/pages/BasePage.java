package com.upgrade.pages;

import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@Log4j
public class BasePage {
    private static final long TIMEOUT = 60;
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void type(@NonNull WebElement element, @NonNull String value) {
        log.info(String.format("Setting %s to value %s", element.getAttribute("name"), value));
        element.sendKeys(value);
    }

    protected void click(@NonNull WebElement element) {
        log(element);
        element.click();
    }

    protected void click(@NonNull By by) {
        click(driver.findElement(by));
    }

    public void javaScriptClick(@NonNull WebElement element) {
        log(element);
        JavascriptExecutor executor = ((JavascriptExecutor) driver);
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
        executor.executeScript("arguments[0].click();", element);
    }

    public void selectDropDownByTextIgnoringCase(@NonNull WebElement selectElement, @NonNull String text) {
        Select dropdown = new Select(selectElement);
        for (WebElement option : dropdown.getOptions()) {
            if (option.getText().equalsIgnoreCase(text)) {
                dropdown.selectByVisibleText(option.getText());
                break;
            }
        }
        log.info(String.format("Selected %s from the dropdown", text));
    }

    protected void waitForWebElements(List<WebElement> mandatoryElements) {
        log.info("Looking for mandatory elements on the page");
        //Wait for dom to go to ready state
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Wait fo mandatory elements to be clickable
        for (WebElement elm : mandatoryElements) {
            try {
                wait
                        .ignoring(StaleElementReferenceException.class)
                        .withTimeout(Duration.ofSeconds(60))
                        .pollingEvery(Duration.ofSeconds(1))
                        .until(visibilityOf(elm));
            } catch (TimeoutException e) {
                log.info("Couldnt verify visibility of the element: " + elm);
                throw new RuntimeException(e);
            }
        }
    }

    public void javaScriptFocusOnElement(@NonNull WebElement element) {
        log(element);
        JavascriptExecutor executor = ((JavascriptExecutor) driver);
        executor.executeScript("arguments[0].focus();", element);
    }

    public void waitForElementToBeDisplayed(@NonNull By by, int timeout, int pollInterval) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollInterval))
                .ignoring(NoSuchElementException.class)
                .until(driver -> driver.findElement(by).isDisplayed());
    }

    public void waitForPage() {
        // Method to wait for page spinner to be gone
        By spinnerBy = By.cssSelector("[data-fetching]");
        try {
            log.info("Waiting for spinner to appear");
            presenceOfElementLocated(spinnerBy, 5);
            waitForAttributeValueToChange(spinnerBy, "data-fetching", "false");
        } catch (TimeoutException te) {
            log.info("Spinner was not found. Continuing");
        }
    }

    public void presenceOfElementLocated(@NonNull By by, int timeoutInSeconds) {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.presenceOfElementLocated(by));
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    }

    public void waitForAttributeValueToChange(@NonNull By by, @NonNull String attribute, @NonNull String expectedAttributeValue) {
        new WebDriverWait(driver, TIMEOUT)
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.attributeToBe(by, attribute, expectedAttributeValue));
    }

    public void blur() {
        ((JavascriptExecutor) driver).executeScript("!!document.activeElement ? document.activeElement.blur() : 0");
    }

    public void textToBePresentInElement(@NonNull By element, @NonNull String text) {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElementLocated(element, text));
    }

    public static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error(e.toString());
            Thread.currentThread().interrupt();
        }
    }

    private void log(@NonNull WebElement element) {
        String display = "link";
        if (element.getAttribute("text") != null)
            display = element.getAttribute("text");
        else if (element.getAttribute("data-auto") != null)
            display = element.getAttribute("data-auto");
        else if (element.getAttribute("name") != null)
            display = element.getAttribute("name");
        else if (element.getAttribute("class") != null)
            display = element.getAttribute("class");
        log.info(String.format("Clicking On: %s", display));
    }

}
