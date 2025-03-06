package com.automation.rough;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class VerifyProductDetails {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            // Open product details page
            driver.get("https://automationexercise.com/product_details/1");

            // Locate product information section
            WebElement productInfo = driver.findElement(By.xpath("//div[@class='product-information']"));

            // Verify each detail is displayed
            verifyElementVisibility(driver, "//h2", "Product Name");
            verifyElementVisibility(driver, "//p[contains(text(),'Category:')]", "Category");
            verifyElementVisibility(driver, "//span[contains(text(),'Rs.')]", "Price");
            verifyElementVisibility(driver, "//b[contains(text(),'Availability:')]", "Availability");
            verifyElementVisibility(driver, "//b[contains(text(),'Condition:')]", "Condition");
            verifyElementVisibility(driver, "//b[contains(text(),'Brand:')]", "Brand");

            System.out.println("✅ All product details are visible!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // Helper method to verify element visibility
    public static void verifyElementVisibility(WebDriver driver, String xpath, String elementName) {
        List<WebElement> elements = driver.findElements(By.xpath(xpath));
        if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
            System.out.println("✔ " + elementName + " is visible.");
        } else {
            System.out.println("❌ " + elementName + " is NOT visible!");
        }
    }
}
