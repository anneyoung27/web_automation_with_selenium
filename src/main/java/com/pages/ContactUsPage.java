package com.pages;

import com.base.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactUsPage extends TestBase {
    WebDriver driver;

    public ContactUsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(),'Contact us')]")
    static WebElement contactUsButton;

    @FindBy(xpath = "//h2[contains(text(),'Get In Touch')]")
    static WebElement getInTouchLabelIsVisible;

    @FindBy(xpath = "//div[@class='form-group col-md-6']//input[@type='text']")
    static WebElement customerName;

    @FindBy(xpath = "//div[@class='form-group col-md-6']//input[@type='email']")
    static WebElement customerEmail;

    @FindBy(xpath = "//div[@class='form-group col-md-12']//input[@type='text']")
    static WebElement customerSubject;

    @FindBy(css = "#message")
    static WebElement customerMessage;

    @FindBy(xpath = "//input[@type='file']")
    static WebElement browseFileButton;

    @FindBy(xpath = "//input[@type='submit']")
    static WebElement submitButton;

    @FindBy(xpath = "//div[@class='status alert alert-success']")
    static WebElement successfulMessage;

    @FindBy(xpath = "//span[contains(text(),'Home')]")
    static WebElement homeButton;

    @FindBy(xpath = "//img[@src='/static/images/home/logo.png']")
    static WebElement landingPageImg;

    // TEST METHOD
    public void goToContactUs(){
        click(contactUsButton);
    }

    public String verifyGetInTouchIsVisible(){
        if(isElementDisplayed(getInTouchLabelIsVisible)){
            return getElementText(getInTouchLabelIsVisible);
        }else{
            return "GET IN TOUCH is not visible";
        }
    }

    public void setCustomerName(String name){
        type(customerName, name);
    }

    public void setCustomerEmail(String email){
        type(customerEmail, email);
    }

    public void setContactSubject(String subject){
        type(customerSubject, subject);
    }

    public void setCustomerMessage(String messages){
        type(customerMessage, messages);
    }

    public void uploadFile(String filePath){
        type(browseFileButton, filePath);
    }

    public void submitContactButton(){
        click(submitButton);
    }

    public String verifySuccessfulMessage(){
        if(isElementDisplayed(successfulMessage)){
            return getElementText(successfulMessage);
        }else{
            return "Press OK to proceed!";
        }
    }

    public void homeButton(){
        click(homeButton);
    }

    public boolean verifyLandingPage(){
        return isElementDisplayed(landingPageImg);
    }

}