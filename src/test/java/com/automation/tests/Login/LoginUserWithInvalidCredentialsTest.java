package com.automation.tests.Login;

import com.automation.utils.Constant;
import com.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class LoginUserWithInvalidCredentialsTest {
    LoginPage loginPage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        loginPage = new LoginPage(driver);
        loginPage.goToLogIn();
    }

    @Test(description = "Test Case 3: Login user with incorrect email and password")
    public void userLoginWithInValidCredentials(){
        loginPage.loginAccount(Constant.EMAIL, Constant.PASSWORD);

        loginPage.clickLoginButton();

        String actualInlineErrorMessage = loginPage.verifyInlineErrorMessage();
        Assert.assertEquals(actualInlineErrorMessage, "Your email or password is incorrect!");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
