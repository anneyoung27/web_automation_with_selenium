package com.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;


public class TestBase extends DriverManager{

    public static Logger log = LogManager.getLogger();

    public static boolean isElementPresent(WebElement element) {
        try {
            log.info("{} element is present", element.toString());

            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.error("{}", e.getMessage());
            return false;
        }
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(setUp.getProperty("explicit.wait"))));
            wait.until(ExpectedConditions.visibilityOf(element));

            log.info("{} element is displayed", element.toString());
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            log.error("Element not valid {}", e.getMessage());
            return false;
        }
    }

    public static String getElementText(WebElement element){
        if (element != null && isElementPresent(element)) {
            try {
                log.info("{} element is valid", element.toString());

                return element.getText();
            } catch (StaleElementReferenceException e) {
                log.error("{} element is not valid", element.toString());
                return "";
            }
        } else {
            log.error("{} element not found or null", element);
            return "";
        }
    }

    public static void type(WebElement element, String value) {
        if (element != null && isElementPresent(element)) {
            try {
                element.clear();
                element.sendKeys(value);
                log.info("{} element is valid - entered value {}", element.toString(), value);
            } catch (StaleElementReferenceException e) {
                log.error("Element not valid: {}", e.getMessage());
            }
        } else {
            log.error("{} not found", element);
        }
    }

    public static void click(WebElement element) {
        if (element != null) {
            // retry mechanism to get the element (to fix page changes or reloaded element)
            int attempts = 0;
            while (attempts < 3) {
                try {
                    wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(setUp.getProperty("explicit.wait"))));
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    element.click();
                    log.info("Element {} has been clicked", element.toString());
                    return; // if success, exit from method
                } catch (StaleElementReferenceException e) {
                    log.info("Element is stale, retrying... Attempt: {}", attempts + 1);
                } catch (Exception e) {
                    log.error("Failed to click the element: {}", e.getMessage());
                    return;
                }
                attempts++;
            }
        } else {
            log.error("Element not found or null");
        }
    }

    public static void selectRadioButton(WebElement radioButton) {
        click(radioButton);
        log.info("{} is selected", radioButton.toString());
    }

    public static void selectDropdown(WebElement dropDownLocator, String value) {
        try {
            Select select = new Select(dropDownLocator);
            select.selectByVisibleText(value);
            log.info("{} element is selected with {}", dropDownLocator, value);
        } catch (NoSuchElementException e) {
            log.error("Option '{}' not found in the dropdown", value);
        } catch (Exception e) {
            log.error("Failed to select value: {}", e.getMessage());
        }
    }

    public static WebElement visibilityOfElementLocated(By locator) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(setUp.getProperty("explicit.wait"))));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.info("{} element is visible", locator);
            return element;
        } catch (TimeoutException e) {
            log.error("Timeout: {} element not visible within the specified time", locator);
            return null;
        }
    }

    public static List<WebElement> findElements(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (elements.isEmpty()) {
            log.error("{} element is empty", locator);
        }
        log.info("{} element is found", locator);
        return elements;
    }

    public static WebElement findElement(WebDriver driver, By locator){
        log.info("{} element has been found ", locator);
        return driver.findElement(locator);
    }

    public void waitForPageToLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int timeout = 10;
        for (int i = 0; i < timeout; i++) {
            String readyState = Objects.requireNonNull(js.executeScript("return document.readyState")).toString();
            if (readyState.equals("complete")) break;
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}