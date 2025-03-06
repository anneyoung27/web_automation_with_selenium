package com.pages;

import com.aventstack.extentreports.util.Assert;
import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartPage extends TestBase {
    WebDriver driver;

    public CartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // VERIFY SUBSCRIPTION
    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    static WebElement cartPage;

    @FindBy(css = "#footer")
    static WebElement footer;

    By subscriptionLabel = By.xpath("//h2[contains(text(),'Subscription')]");

    @FindBy(xpath = "//input[@id='susbscribe_email']")
    static WebElement inputSubscribeEmail;

    @FindBy(xpath = "//button[@id='subscribe']")
    static WebElement subscribeButton;

    By successAlertMessage = By.xpath("//div[@class='alert-success alert']");

    By cartTableData = By.xpath("//table/tbody/tr");

    // VERIFY SUBSCRIPTION TEST STEP
    public void goToCartPage(){
        click(cartPage);
    }

    public void scrollDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", footer);
    }

    public String verifySubscriptionLabelIsVisible(){
        WebElement getLabelText = visibilityOfElementLocated(subscriptionLabel);

        assert getLabelText != null;
        return getLabelText.getText();
    }

    public void enterEmail(String userEmail){
        type(inputSubscribeEmail, userEmail);
    }

    public void subscribeButton(){
        click(subscribeButton);
    }

    public String verifySuccessMessage(){
        WebElement getVerifyMessageText = visibilityOfElementLocated(successAlertMessage);

        assert getVerifyMessageText != null;
        return getVerifyMessageText.getText();
    }

    // VERIFY ADDED PRODUCT IN CART
    public void verifyAddedProductInCartIsVisible(String[] addedProduct) {
        Set<String> foundProduct = new HashSet<>(); // Avoid duplicates

        List<WebElement> cartTable = findElements(driver, cartTableData);

        for (WebElement data : cartTable) {
            List<WebElement> dataPerRows = data.findElements(By.tagName("td")); // Get row data

            String columnDescription = dataPerRows.get(1).getText().trim(); // Get description column

            for (String product : addedProduct) {
                if (columnDescription.contains(product)) { // get product name not product category
                    foundProduct.add(product);
                }
            }
        }

        // Ensure all added products are in the cart
        if (foundProduct.containsAll(Arrays.asList(addedProduct))) {
            System.out.println("✅ All added products are visible in the Cart");
        } else {
            System.out.println("❌ Some products are missing in the Cart");
        }
    }

    // VERIFY PRICES, QUANTITY, and TOTAL PRICE ADDED PRODUCT
    // (PRICES * QUANTITY = TOTAL)
    public void verifyPriceQuantityTotalProduct(){
        List<WebElement> cartTable = findElements(driver, cartTableData);

        for (WebElement data : cartTable){
            List<WebElement> dataPerRows = data.findElements(By.tagName("td")); // get row data

            String columnPrice = dataPerRows.get(2).getText().trim().replace("Rs. ", "");
            String columnQuantity = dataPerRows.get(3).getText().trim();
            String columnTotal = dataPerRows.get(4).getText().trim().replace("Rs. ", "");

            int expectedTotal = Integer.parseInt(columnPrice) * Integer.parseInt(columnQuantity);

            assert Integer.parseInt(columnTotal) == expectedTotal;
            System.out.println("Verified: " + columnPrice + "x" + columnQuantity + "=" + expectedTotal);
        }
    }

    // VERIFY THE PRODUCT DISPLAYED IN CART WITH EXACT QUANTITY
    public int verifyDisplayedQuantityInCart(){
        String columnQuantity = "";
        List<WebElement> cartTable = findElements(driver, cartTableData);

        for (WebElement data : cartTable){
            List<WebElement> dataPerRows = data.findElements(By.tagName("td")); // get row data

            columnQuantity = dataPerRows.get(3).getText().trim();
        }

        return Integer.parseInt(columnQuantity);
    }
}
