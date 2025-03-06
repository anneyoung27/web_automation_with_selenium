package com.automation.tests.Home;

import com.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class VerifySubscriptionInHomePageTest {

    HomePage homePage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        homePage = new HomePage(driver);
    }

    @Test(description = "Test Case 10: Verify Subscription in home page")
    public void verifySubscriptionInHomePage(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible");

        String actualLabel = homePage.verifySubscriptionIsVisible();
        Assert.assertEquals(actualLabel, "SUBSCRIPTION");

        homePage.scrollDown();

        String registeredEmail = setUp.getProperty("registeredEmail");
        homePage.enterEmail(registeredEmail);

        homePage.subscribeButton();

        String actualSuccessMessage = homePage.verifySuccessMessage();
        Assert.assertEquals(actualSuccessMessage, "You have been successfully subscribed!");
    }


    @AfterMethod
    public void quit(){
        tearDown();
    }
}