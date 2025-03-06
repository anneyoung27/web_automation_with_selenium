package com.automation.tests.Login;

import com.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class LoginUserWithValidCredentialsTest {
    LoginPage loginPage;
    private static String USER_NAME;
    private static String USER_EMAIL;
    private static String USER_PASSWORD;


    @BeforeMethod
    public void testSetUp(){
        setUp();

        loginPage = new LoginPage(driver);

        USER_NAME = setUp.getProperty("registeredName");
        USER_EMAIL = setUp.getProperty("registeredEmail");
        USER_PASSWORD = setUp.getProperty("registeredPassword");

        loginPage.goToLogIn();
    }

    @Test(description = "Test Case 2: Login user with correct email and password")
    public void userLoginWithValidCredentials(){
        String actualLoginLabel = loginPage.verifyLoginLabel();
        Assert.assertEquals(actualLoginLabel, "Login to your account");

        loginPage.loginAccount(USER_EMAIL, USER_PASSWORD);

        loginPage.clickLoginButton();

        String actualLoggedName = loginPage.verifyLoggedInAsUserNameIsVisible(USER_NAME);
        Assert.assertEquals(actualLoggedName, USER_NAME);
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
