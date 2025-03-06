package com.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestBase extends DriverManager{

    public static boolean isElementPresent(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(setUp.getProperty("explicit.wait"))));
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static String getElementText(WebElement element){
        if (element != null && isElementPresent(element)) {
            try {
                return element.getText();
            } catch (StaleElementReferenceException e) {
                System.out.println("Element not valid: " + e.getMessage());
                return "";
            }
        } else {
            System.out.println("Element not found or null.");
            return "";
        }
    }

    public static void type(WebElement element, String value) {
        if (element != null && isElementPresent(element)) {
            try {
                element.clear();
                element.sendKeys(value);
            } catch (StaleElementReferenceException e) {
                System.out.println("Element not valid: " + e.getMessage());
            }
        } else {
            System.out.println("Element not found or null.");
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
                    return; // if success, exit from method
                } catch (StaleElementReferenceException e) {
                    System.out.println("Element is stale, retrying... Attempt: " + (attempts + 1));
                } catch (Exception e) {
                    System.out.println("Failed to click the element: " + e.getMessage());
                    return;
                }
                attempts++;
            }
        } else {
            System.out.println("Element not found or null.");
        }
    }

    public static void selectRadioButton(List<WebElement> radioLocator, String value) {
        radioLocator.stream()
                .filter(radio -> value.equalsIgnoreCase(radio.getAttribute("value")) && !radio.isSelected())
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public static void selectDropdown(WebElement dropDownLocator, String value) {
        try {
            Select select = new Select(dropDownLocator);
            select.selectByVisibleText(value);
            System.out.println("Selected: " + value);
        } catch (NoSuchElementException e) {
            System.out.println("Option '" + value + "' not found in the dropdown.");
        } catch (Exception e) {
            System.out.println("Failed to select value: " + e.getMessage());
        }
    }

    public static WebElement visibilityOfElementLocated(By locator) {
        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(setUp.getProperty("explicit.wait"))));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("✔ Element is visible: " + locator);
            return element;
        } catch (TimeoutException e) {
            System.out.println("❌ Timeout: Element not visible within the specified time: " + locator);
            return null;
        }
    }

    public static List<WebElement> findElements(WebDriver driver, By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (elements.isEmpty()) {
            System.out.println("Element not found: " + locator);
        }
        return elements;
    }

    public static WebElement findElement(WebDriver driver, By locator){
        return driver.findElement(locator);
    }
}