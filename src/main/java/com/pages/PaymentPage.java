package com.pages;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage extends TestBase {
    WebDriver driver;

    public PaymentPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@data-qa='name-on-card']")
    static WebElement nameOnCard;

    @FindBy(xpath = "//input[@data-qa='card-number']")
    static WebElement cardNumber;

    @FindBy(xpath = "//input[@data-qa='cvc']")
    static WebElement cvc;

    @FindBy(xpath = "//input[@data-qa='expiry-month']")
    static WebElement expiryMonth;

    @FindBy(xpath = "//input[@data-qa='expiry-year']")
    static WebElement expiryYear;

    @FindBy(xpath = "//button[@data-qa='pay-button']")
    static WebElement payAndConfirmButton;

    By verifyOrderPlacedLabel = By.cssSelector("div[class='col-sm-9 col-sm-offset-1'] p");

    @FindBy(css = ".btn.btn-primary")
    static WebElement continueAfterSuccessOrderButton;

    public void setNameOnCard(String name){
        type(nameOnCard, name);
    }

    public void setCardNumber(String number){
        type(cardNumber, number);
    }

    public void setCVC(String cvcNumber){
        type(cvc, cvcNumber);
    }

    public void setExpiryMonth(String month){
        type(expiryMonth, month);
    }

    public void setExpiryYear(String year){
        type(expiryYear, year);
    }

    public void payAndConfirmButton(){
        click(payAndConfirmButton);
    }

    public String verifyOrderedPlaceLabel(){
        WebElement element = visibilityOfElementLocated(verifyOrderPlacedLabel);

        assert element != null;
        return element.getText();
    }

    public void clickContinueButton(){
        click(continueAfterSuccessOrderButton);
    }
}
