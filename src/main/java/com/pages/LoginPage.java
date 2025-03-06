package com.pages;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class LoginPage extends TestBase {
    WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ELEMENTS
    @FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
    static WebElement goToLoginBtn;

    @FindBy(css = "div[class='signup-form'] h2")
    static WebElement registerLabel;

    @FindBy(xpath = "//button[contains(text(),'Signup')]")
    static WebElement signUpBtn;

    @FindBy(xpath = "//form[@action='/signup']/input[@placeholder='Name']")
    static WebElement name;

    @FindBy(xpath = "//form[@action='/signup']//input[@type='email']")
    static WebElement emailAddress;

    @FindBy(xpath = "//b[contains(text(),'Enter Account Information')]")
    static WebElement accountInformationLabel;

    @FindBy(xpath = "//button[contains(text(),'Create Account')]")
    static WebElement createAccountBtn;

    // ACCOUNT INFORMATION
    @FindBy(xpath = "//input[@name='title']")
    static List<WebElement> title;

    @FindBy(xpath = "//input[@id='password']")
    static WebElement password;

    @FindBy(xpath = "//select[@id='days']")
    static WebElement days;

    @FindBy(xpath = "//select[@id='months']")
    static WebElement months;

    @FindBy(xpath = "//select[@id='years']")
    static WebElement years;

    @FindBy(css = "#newsletter")
    static WebElement checkBoxSignUp;

    @FindBy(css = "#optin")
    static WebElement checkBoxReceiveSpecialOffers;

    // ADDRESS INFORMATION
    @FindBy(xpath = "//input[@id='first_name']")
    static WebElement userFirstName;

    @FindBy(css = "#last_name")
    static WebElement userLastName;

    @FindBy(css = "#company")
    static WebElement userCompany;

    @FindBy(css = "#address1")
    static WebElement userAddress1;

    @FindBy(css = "#address2")
    static WebElement userAddress2;

    @FindBy(xpath = "//select[@id='country']")
    static WebElement userCountry;

    @FindBy(xpath = "//input[@id='state']")
    static WebElement userState;

    @FindBy(xpath = "//input[@id='city']")
    static WebElement userCity;

    @FindBy(xpath = "//input[@id='zipcode']")
    static WebElement userZipCode;

    @FindBy(css = "#mobile_number")
    static WebElement userMobileNumber;

    @FindBy(xpath = "//b[contains(text(),'Account Created!')]")
    static WebElement accountCreatedLabel;

    @FindBy(xpath = "//a[@class='btn btn-primary']")
    static WebElement continueBtn;

    @FindBy(xpath = "//a[contains(text(),'Delete Account')]")
    static WebElement deleteBtn;

    // USER LOGIN
    @FindBy(xpath = "//h2[contains(text(),'Login to your account')]")
    static WebElement loginLabel;

    @FindBy(xpath = "//form[@action='/login']//input[@type='email']")
    static WebElement createdUserEmail;

    @FindBy(xpath = "//input[@type='password']")
    static WebElement createdUserPassword;

    @FindBy(xpath = "//button[contains(text(),'Login')]")
    static WebElement loginButton;

    @FindBy(xpath = "//p[@style='color: red;']")
    static WebElement inlineErrorMessage;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    static WebElement logoutButton;


    // LOGIN | LOGOUT | REGISTER TEST STEPS
    public void goToLogIn(){
        click(goToLoginBtn);
    }

    public String verifyRegisterIsVisible(){
        if (isElementDisplayed(registerLabel)) {
            return getElementText(registerLabel);
        }else{
            return "Register label is not visible";
        }
    }

    public void typeUserInformation(String userName, String userEmail){
        type(name, userName);
        type(emailAddress, userEmail);
    }

    public String verifyAccountInformationIsVisible(){
        if(isElementDisplayed(accountInformationLabel)){
            return getElementText(accountInformationLabel);
        }else{
            return "Account Information is not visible";
        }
    }

    public void clickSignUpButton(){
        click(signUpBtn);
    }

    private void selectGender(String gender) {
        if(gender.equalsIgnoreCase("Mr.")){
            selectRadioButton(title, gender);
        }else if(gender.equalsIgnoreCase("Mrs.")){
            selectRadioButton(title, gender);
        }
    }

    public void fillAccountInformation(String title, String userPassword, String date, String month, String year){
        selectGender(title);
        type(password, userPassword);
        selectDropdown(days, date);
        selectDropdown(months, month);
        selectDropdown(years, year);
    }

    public void selectCheckbox(){
        click(checkBoxSignUp);
        click(checkBoxReceiveSpecialOffers);
    }

    public void fillAddressInformation(String firstName, String lastName, String company,
                                       String address, String address2, String country,
                                       String state, String city, String zipCode, String mobileNumber){

        type(userFirstName, firstName);
        type(userLastName, lastName);
        type(userCompany, company);
        type(userAddress1, address);
        type(userAddress2, address2);
        selectDropdown(userCountry, country);
        type(userState, state);
        type(userCity, city);
        type(userZipCode, zipCode);
        type(userMobileNumber, mobileNumber);
    }

    public void createAccountButton(){
        click(createAccountBtn);
    }

    public String verifyAccountWasCreatedIsVisible(){
        if(isElementDisplayed(accountCreatedLabel)){
            return getElementText(accountCreatedLabel);
        }else{
            return "Account Created! is not visible";
        }
    }

    public void clickContinueButton(){
        click(continueBtn);
    }

    public String verifyLoggedInAsUserNameIsVisible(String userName){
        String xpath = String.format("//b[contains(text(), '%s')]", userName);
        WebElement element = visibilityOfElementLocated(By.xpath(xpath));

        assert element != null;
        return element.getText();
    }

    public void clickDeleteAccountButton(){
        click(deleteBtn);
    }

    public void loginAccount(String userEmailAddress, String password){
        type(createdUserEmail, userEmailAddress);
        type(createdUserPassword, password);
    }

    public void clickLoginButton(){
        click(loginButton);
    }

    public String verifyInlineErrorMessage(){
        if(isElementDisplayed(inlineErrorMessage)){
            return getElementText(inlineErrorMessage);
        }else{
            return "Your email or password is incorrect! is not visible";
        }
    }

    public String verifyLoginLabel(){
        if(isElementDisplayed(loginLabel)){
            return getElementText(loginLabel);
        }else{
            return "Login to your account is not visible";
        }
    }

    public void clickLogoutButton(){
        click(logoutButton);
    }

}