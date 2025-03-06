package com.automation.tests.Login;

import com.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class RegisterUserWithExistingEmailTest {
    LoginPage loginPage;
    private static String USER_NAME;
    private static String USER_EMAIL;

    @BeforeMethod
    public void testSetUp(){
        setUp();

        loginPage = new LoginPage(driver);

        USER_NAME = setUp.getProperty("registeredName");
        USER_EMAIL = setUp.getProperty("registeredEmail");

        loginPage.goToLogIn();
    }

    @Test(description = "Test Case 5: Register User with existing email")
    public void userLoginWithValidCredentials(){
        String actualNewUser = loginPage.verifyRegisterIsVisible();
        Assert.assertEquals(actualNewUser, "New User Signup!");

        loginPage.typeUserInformation(USER_NAME, USER_EMAIL);

        loginPage.clickSignUpButton();

        String actualInlineErrorMessage = loginPage.verifyInlineErrorMessage();
        Assert.assertEquals(actualInlineErrorMessage, "Email Address already exist!");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
