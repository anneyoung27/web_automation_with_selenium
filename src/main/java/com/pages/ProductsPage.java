package com.pages;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class ProductsPage extends TestBase {
    WebDriver driver;

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // PRODUCT DETAILS
    @FindBy(xpath = "//img[@src='/static/images/home/logo.png']")
    static WebElement landingPageImg;

    @FindBy(xpath = "//a[contains(text(),'Products')]")
    static WebElement productButton;

    @FindBy(xpath = "//h2[@class='title text-center']")
    static WebElement allProductsPage;

    By productList = By.cssSelector(".col-sm-4");

    By productInformation = By.xpath("//div[@class='product-information']");

    // SEARCH PRODUCT
    @FindBy(xpath = "//input[@id='search_product']")
    static WebElement productNameToSearch;

    @FindBy(css = "#submit_search")
    static WebElement productSearchButton;

    By searchProductLabel = By.cssSelector(".title.text-center");

    By verifySearchedProductIsVisible = By.xpath("//div[@class='productinfo text-center']/p");

    // ADD PRODUCT TO CART
    @FindBy(xpath = "//button[contains(@class,'btn btn-success')]")
    static WebElement continueShoppingButton;

    // ADD QUANTITY IN PRODUCT DETAIL
    @FindBy(id = "quantity")
    static WebElement addQuantity;

    @FindBy(xpath = "//button[@type='button']")
    static WebElement addToCartButton;

    @FindBy(xpath = "//u[contains(text(),'View Cart')]")
    static WebElement viewCart;

    // PRODUCT DETAILS TEST STEP
    public void goToProductsPage(){
        click(productButton);
    }

    public boolean verifyLandingPage(){
        return isElementDisplayed(landingPageImg);
    }

    public String verifyNavigateToAllProductsPage(){
        if(isElementDisplayed(allProductsPage)){
            return getElementText(allProductsPage);
        }else{
            return "ALL PRODUCTS is not visible";
        }
    }

    public void verifyProductListIsVisible(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productList));
        List<WebElement> products = findElements(driver, productList);

        if (!products.isEmpty()){
            System.out.println("Products list is visible, total of products: " + products.size());
        }else{
            System.out.println("Product list is not visible");
            throw new AssertionError("Product list is not visible.");
        }
    }

    public int getProductOrderNumber(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productList));
        List<WebElement> products = findElements(driver, productList);

        return products.size();
    }

    public String getProductName(int productId) {
        List<WebElement> list = findElements(driver, productList);

        for(int i = 0; i < list.size(); i++){
            if(i == productId){
                String productDetails = list.get(i).getText();
                String [] lines = productDetails.split("\n");

                if(lines.length > 1){
                    System.out.println("ProductID order: " + i);
                    return lines[1]; // take product's name
                }
                //System.out.println(i + ". " + list.get(i).getText());
            }
        }
        return "Product not found";
    }

    public String generateElementViewProductButton(String productName) {
        String hrefValue = "";
        List<WebElement> products = findElements(driver, productList);

        for (int i = 0; i < products.size(); i++) {
            String productDetails = products.get(i).getText(); // try to get product name
            String[] lines = productDetails.split("\n"); // split data

            if (lines.length > 1 && lines[1].equalsIgnoreCase(productName)) {
                // Try to get element <a> directly from element product to get View Product button
                List<WebElement> linkElements = products.get(i).findElements(By.cssSelector(".choose a"));

                if (linkElements.isEmpty()) {
                    System.out.println("No link found for product: " + lines[1]);
                    continue;
                }

                hrefValue = linkElements.getFirst().getAttribute("href"); // get view product element

                // get element (product_details/product index)
                assert hrefValue != null;
                String [] parts = hrefValue.split("product_details/");
                if (parts.length > 1){
                    hrefValue = "product_details/" + parts[1];
                }

                System.out.println("{Product number: " + i + " - Product name: " + lines[1] + " - Extracted href: " + hrefValue + "}\n");
                break;
            }
        }
        return hrefValue;
    }

    public void viewProductButton(String productIndex){
        String css = String.format("[href='/%s']", productIndex); // dynamically find element view product based on product index (extracted href)
        System.out.println("css generated: " + css);
        WebElement element = visibilityOfElementLocated(By.cssSelector(css));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        assert element != null;
        click(element);
    }

    // SEARCH PRODUCT TEST STEP
    public boolean verifyProductInformationIsVisible(){
        WebElement productInfo = findElement(driver, productInformation);
        return productInfo.isDisplayed();
    }

    public void findProduct(String productName){
        type(productNameToSearch, productName);

        click(productSearchButton);
    }

    public String verifyIfSearchProductIsVisible(){
        WebElement getLabel = visibilityOfElementLocated(searchProductLabel);
        assert getLabel != null;
        return getLabel.getText();
    }

    public void verifySearchProductIsRelatedToSearchAreVisible(String searchKeyword){
        List<WebElement> products = findElements(driver, verifySearchedProductIsVisible);
        String message = "";
        boolean isValid = true;

        if(products.isEmpty()){
            message = "❌ There are no product found";
            System.out.println(message);
            return;
        }

        String normalizeSearchKeyword = searchKeyword.toLowerCase().replaceAll("[-\\s]", ""); //t-shirt, t shirt, tshirt

        for (WebElement product :products){
            String productText = product.getText().toLowerCase();
            System.out.println(productText);

            // delete "-" and space
            String normalizeProductText = productText.replaceAll("[-\\s]", "");

            if(!normalizeProductText.contains(normalizeSearchKeyword)){
                isValid = false;
            }
        }

        if(isValid){
            message = "✔ All products are displayed according to the keywords: " + searchKeyword;
        }else{
            message = "❌ The products that appear do not match the search keywords!";
        }

        System.out.println(message);
    }

    // ADD PRODUCT TO CART TEST STEP
    public String getProductIndex(String productName){
        // get product index / id by product name
        String productIndex = "";
        List<WebElement> products = findElements(driver, productList);

        for (int i = 0; i < products.size(); i++) {
            String productDetails = products.get(i).getText(); // try to get product name
            String[] lines = productDetails.split("\n"); // split data

            if (lines.length > 1 && lines[1].equalsIgnoreCase(productName)) {
                // Try to get element <a> directly from element product to get Add to Cart button
                List<WebElement> linkElements = products.get(i).findElements(By.cssSelector(".single-products a"));

                if (linkElements.isEmpty()) {
                    System.out.println("No link found for product: " + lines[1]);
                    continue;
                }

                productIndex = linkElements.getFirst().getAttribute("data-product-id"); // get data-product-id

                assert productIndex != null;

                System.out.println("{Product number: " + i + " - Product name: " + lines[1] + " - Extracted data-product-id: " + productIndex + "}\n");
                break;
            }
        }
        return productIndex;
    }

    public void addToCartButton(String productIndex){
        String xpath = String.format("(//a[@data-product-id='%s'])[1]", productIndex);
        System.out.println("xpath generated: " + xpath);

        WebElement element = visibilityOfElementLocated(By.xpath(xpath));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        assert element != null;
        click(element);
    }

    public void successfullyAddedModalButton(){
        click(continueShoppingButton);
    }

    // ADD QUANTITY IN PRODUCT DETAIL
    public void addQuantityProduct(String value){
        type(addQuantity, value);
    }

    public void addToCartButton(){
        click(addToCartButton);
    }

    public void viewCart(){
        click(viewCart);
    }


}