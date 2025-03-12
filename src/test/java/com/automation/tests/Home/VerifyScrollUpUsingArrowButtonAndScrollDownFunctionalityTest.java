package com.automation.tests.Home;

import com.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class VerifyScrollUpUsingArrowButtonAndScrollDownFunctionalityTest {
    HomePage homePage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        homePage = new HomePage(driver);
    }

    @Test(description = "Test Case 25: Verify scroll up using 'Arrow' button and scroll down functionality")
    public void verifyScrollUpSingleArrowAndScrollDown(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible!");

        homePage.scrollDownToFooter();

        String actualSubsLabel = homePage.verifySubscriptionIsVisible();
        Assert.assertEquals(actualSubsLabel, "SUBSCRIPTION");

        homePage.clickScrollUpButton();

        String actualLabelAfterScrollUp = homePage.verifyLabelWhenScrollUp();
        Assert.assertEquals(actualLabelAfterScrollUp, "Full-Fledged practice website for Automation Engineers");
    }


    @AfterMethod
    public void quit(){
        tearDown();
    }

}
