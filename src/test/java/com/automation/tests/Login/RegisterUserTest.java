package com.automation.tests.Login;

import com.pages.LoginPage;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.utils.Constant;
import static com.base.DriverManager.*;

public class RegisterUserTest {
    private static final String GENERATED_NAME = Constant.NAME;
    LoginPage loginPage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        loginPage = new LoginPage(driver);
        loginPage.goToLogIn();
    }

    @Test(description = "Test Case 1: Register User")
    public void registerUserTest(){
        String actualNewUser = loginPage.verifyRegisterIsVisible();
        Assert.assertEquals(actualNewUser, "New User Signup!");

        loginPage.typeUserInformation(GENERATED_NAME, Constant.EMAIL);

        loginPage.clickSignUpButton();

        String actualAccountInformation = loginPage.verifyAccountInformationIsVisible();
        Assert.assertEquals(actualAccountInformation, "ENTER ACCOUNT INFORMATION");

        loginPage.fillAccountInformation(Constant.TITLE,
                                         Constant.PASSWORD,
                                         Constant.DATE,
                                         Constant.MONTH,
                                         Constant.YEAR);

        loginPage.selectCheckbox();

        loginPage.fillAddressInformation(Constant.FIRST_NAME, Constant.LAST_NAME, Constant.COMPANY,
                                         Constant.ADDRESS, Constant.ADDRESS2, Constant.COUNTRY,
                                         Constant.STATE, Constant.CITY, Constant.ZIPCODE, Constant.MOBILE_NUMBER);

        loginPage.createAccountButton();

        String actualAccountWasCreated = loginPage.verifyAccountWasCreatedIsVisible();
        Assert.assertEquals(actualAccountWasCreated, "ACCOUNT CREATED!");

        loginPage.clickContinueButton();

        String actualLoggedName = loginPage.verifyLoggedInAsUserNameIsVisible(GENERATED_NAME);
        Assert.assertEquals(actualLoggedName, GENERATED_NAME);

        loginPage.clickDeleteAccountButton();


    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}