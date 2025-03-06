package com.pages;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    // VERIFY SUBSCRIPTION IN HOME PAGE TEST STEP
    public boolean verifyLandingPage(){
        return isElementDisplayed(landingPageImg);
    }

    public void scrollDown(){
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




}
