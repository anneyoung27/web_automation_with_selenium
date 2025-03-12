package com.pages;

import com.base.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class HomePage extends TestBase {
    WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//img[@src='/static/images/home/logo.png']")
    static WebElement landingPageImg;

    @FindBy(id = "footer")
    static WebElement footer;

    By subscriptionLabel = By.cssSelector("div[class='single-widget'] h2");

    @FindBy(xpath = "//input[@id='susbscribe_email']")
    static WebElement subscribeEmail;

    @FindBy(xpath = "//button[@id='subscribe']")
    static WebElement subscribeButton;

    By successMessage = By.xpath("//div[@class='alert-success alert']");

    By categoryLabel = By.xpath("//h2[contains(text(),'Category')]");

    By categoryBy = By.xpath("//div[@class='panel-group category-products']/div//h4/a");

    @FindAll({
            @FindBy(xpath = "(//i[@class='fa fa-plus'])[1]"),
            @FindBy(xpath = "(//a[@data-parent='#accordian']//span)[1]")
    })
    static WebElement womenCategoryExtend;

    @FindAll({
            @FindBy(xpath = "(//i[@class='fa fa-plus'])[2]"),
            @FindBy(xpath = "(//span[@class='badge pull-right']//i)[2]")
    })
    static WebElement menCategoryExtend;

    @FindAll({
            @FindBy(xpath = "(//i[@class='fa fa-plus'])[3]"),
            @FindBy(xpath = "(//span[@class='badge pull-right']//i)[3]")
    })
    static WebElement kidsCategoryExtend;

    By clickedCategoryPageLabel = By.cssSelector(".title.text-center");

    By recommendedItemsIsVisible = By.xpath("//h2[normalize-space(text())='recommended items']");

    @FindBy(xpath = "(//div[@class='recommended_items']//a[contains(text(), 'Add to cart')])[1]")
    static WebElement addToCartButton;

    @FindBy(xpath = "//u[contains(text(),'View Cart')]")
    static WebElement viewCart;

    @FindBy(xpath = "//a[@id='scrollUp']")
    static WebElement clickScrollUpButton;

    By scrollUpLabel = By.xpath("//div[@class='item active']//div[@class='col-sm-6']//h2[contains(text(),'Full-Fledged practice website for Automation Engin')]");

    // VERIFY SUBSCRIPTION IN HOME PAGE TEST STEP
    public boolean verifyLandingPage(){
        return isElementDisplayed(landingPageImg);
    }

    public void scrollDownToFooter(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", footer);
    }

    public String verifySubscriptionIsVisible(){
        WebElement getLabel = visibilityOfElementLocated(subscriptionLabel);

        assert getLabel != null;
        return getLabel.getText();
    }

    public void enterEmail(String userEmail){
        type(subscribeEmail, userEmail);
    }

    public void subscribeButton(){
        click(subscribeButton);
    }

    public String verifySuccessMessage(){
        WebElement getLabel = visibilityOfElementLocated(successMessage);

        assert  getLabel != null;
        return getLabel.getText();
    }

    // VERIFY CATEGORY IN HOME PAGE TEST STEP
    public String verifyCategoryIsVisible(){
        WebElement getLabel = visibilityOfElementLocated(categoryLabel);

        assert  getLabel != null;
        return getLabel.getText();
    }

    public void clickWomenCategoryExtend(){
        click(womenCategoryExtend);
    }

    public void clickMenCategoryExtend(){
        click(menCategoryExtend);
    }

    public void clickKidsCategoryExtend(){
        click(kidsCategoryExtend);
    }

    public int getCategoryCount(){ // WOMEN, MEN, KIDS
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryBy));
        List<WebElement> category = findElements(driver, categoryBy);

        System.out.println("Category size: " + category.size());
        return category.size();
    }

    public String getCategoryName(int categoryCount) {
        List<WebElement> list = findElements(driver, categoryBy);

        if (categoryCount < 0 || categoryCount >= list.size()) {
            throw new IllegalArgumentException("Invalid category index: " + categoryCount);
        }
        String categoryByDetails = list.get(categoryCount).getText().trim();
        System.out.println("Category found: " + categoryByDetails);

        return categoryByDetails;
    }

    public void clickProductCategory(String byCategory, String productByCategory){
        String xpath = String.format("//div[@id='%s']//a[contains(text(),'%s')]", byCategory, productByCategory);

        WebElement getProduct = driver.findElement(By.xpath(xpath));
        System.out.println("Element: " + xpath +" was clicked!");
        click(getProduct);
    }

    public String verifyCategoryPageAccordingSelectedCategoryAndProduct(){

        WebElement getCategoryPageLabel = visibilityOfElementLocated(clickedCategoryPageLabel);

        assert getCategoryPageLabel != null;
        return getCategoryPageLabel.getText();
    }

    // ADD TO CART FROM RECOMMENDATIONS TEST STEP
    public String verifyRecommendationItemsIsVisible(){
        WebElement getLabel = visibilityOfElementLocated(recommendedItemsIsVisible);

        assert  getLabel != null;
        return getLabel.getText();
    }

    public void addRecommendedItemToCart() {
        click(addToCartButton);
    }

    public void clickViewCart(){
        click(viewCart);
    }

    //  SCROLL UP USING 'ARROW' BUTTON AND SCROLL DOWN FUNCTIONALITY
    public void clickScrollUpButton(){
        click(clickScrollUpButton);
    }

    public String verifyLabelWhenScrollUp(){
        WebElement getLabel = visibilityOfElementLocated(scrollUpLabel);

        assert  getLabel != null;
        return getLabel.getText();
    }

    public void scrollToTopOfAPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth'});", landingPageImg);
    }


    // ROUGH (METHOD HELPER)
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split(" ");
        StringBuilder capitalizedStr = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedStr.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" "); // Add space back
            }
        }
        return capitalizedStr.toString().trim(); // Trim trailing space
    }

}