package com.pages;

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
import java.util.stream.Collectors;

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

    By verifyShoppingCartPage = By.cssSelector(".active");

    @FindBy(xpath = "//a[normalize-space(text())='Proceed To Checkout']")
    static WebElement proceedToCheckout;

    By verifyCheckOutPopUp = By.xpath("//h4[@class='modal-title w-100']");

    @FindBy(xpath = "//u[normalize-space(text())='Register / Login']")
    static WebElement registerOrLogin;

    By verifyAddressDetail = By.xpath("(//h2[@class='heading'])[1]");

    By verifyReviewYourOrder = By.xpath("(//h2[@class='heading'])[2]");

    @FindBy(xpath = "//textarea[@class='form-control']")
    static WebElement typeCommentBeforeCheckout;

    @FindBy(css = ".btn.btn-default.check_out")
    static WebElement placeOrderButton;

    @FindBy(css = ".cart_quantity_delete")
    static WebElement deleteProductButton;

    @FindBy(xpath = "//table[@id='cart_info_table']//tbody/tr")
    static WebElement cartProductRow;

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
        Set<String> foundProducts = new HashSet<>();

        List<WebElement> cartTable = findElements(driver, cartTableData);

        if (cartTable.isEmpty()) {
            System.out.println("❌ Cart is empty! No products found.");
            return;
        }

        System.out.println("🔍 Checking products in the cart...");

        for (WebElement row : cartTable) {
            List<WebElement> rowData = row.findElements(By.tagName("td"));

            if (rowData.size() > 1) {
                String productDescription = rowData.get(1).getText().trim();
                for (String product : addedProduct) {
                    if (productDescription.contains(product)) {
                        foundProducts.add(product);
                    }
                }
            }
        }

        List<String> missingProducts = Arrays.stream(addedProduct)
                .filter(p -> !foundProducts.contains(p))
                .toList();

        if (missingProducts.isEmpty()) {
            System.out.println("✅ All added products are visible in the Cart: " + foundProducts);
        } else {
            System.out.println("❌ Some products are missing in the Cart: " + missingProducts);
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

    // PLACE ORDER REGISTER WHILE CHECKOUT
    public String verifyCartPage(){
        WebElement getVerifyMessageText = visibilityOfElementLocated(verifyShoppingCartPage);

        assert getVerifyMessageText != null;
        return getVerifyMessageText.getText();
    }

    public void proceedToCheckout(){
        click(proceedToCheckout);
    }

    public String verifyCheckoutPopUpIsVisible(){
        WebElement getVerifyMessageText = visibilityOfElementLocated(verifyCheckOutPopUp);

        assert getVerifyMessageText != null;
        return getVerifyMessageText.getText();
    }

    public void registerOrLogin(){
        click(registerOrLogin);
    }

    public String verifyAddressDetailIsVisible(){
        WebElement getVerifyMessageText = visibilityOfElementLocated(verifyAddressDetail);

        assert getVerifyMessageText != null;
        return getVerifyMessageText.getText();
    }

    public String verifyReviewYourOrderIsVisible(){
        WebElement getVerifyMessageText = visibilityOfElementLocated(verifyReviewYourOrder);

        assert getVerifyMessageText != null;
        return getVerifyMessageText.getText();
    }

    public void setCommentBeforeCheckout(String comment){
        type(typeCommentBeforeCheckout, comment);
    }

    public void placeOrderButton(){
        click(placeOrderButton);
    }

    // REMOVE PRODUCT FROM CART
    public void clickRemoveProductFromCart(){
        click(deleteProductButton);
    }

    // ADD ITEM FROM RECOMMENDED ITEM
    public boolean verifyIfAddedItemFromRecommendedItemsInCart(){
        return cartProductRow.isDisplayed();
    }


}
