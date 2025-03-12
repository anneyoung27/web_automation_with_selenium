package com.automation.tests.Home;

import com.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class VerifyScrollUpWithoutArrowButtonAndScrollDownFunctionalityTest {
    HomePage homePage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        homePage = new HomePage(driver);
    }

    @Test(description = "Test Case 26: Verify Scroll up without 'Arrow' button and Scroll Down functionality")
    public void verifyScrollUpSingleArrowAndScrollDown(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible!");

        homePage.scrollDownToFooter();

        String actualSubsLabel = homePage.verifySubscriptionIsVisible();
        Assert.assertEquals(actualSubsLabel, "SUBSCRIPTION");

        homePage.scrollToTopOfAPage();

        String actualLabelAfterScrollUp = homePage.verifyLabelWhenScrollUp();
        Assert.assertEquals(actualLabelAfterScrollUp, "Full-Fledged practice website for Automation Engineers");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
