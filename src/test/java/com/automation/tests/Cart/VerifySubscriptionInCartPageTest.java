package com.automation.tests.Cart;

import com.pages.CartPage;
import com.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class VerifySubscriptionInCartPageTest {
    HomePage homePage;
    CartPage cartPage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        cartPage = new CartPage(driver);
        homePage = new HomePage(driver);
    }

    @Test(description = "Test Case 11: Verify Subscription in Cart Page")
    public void verifySubscriptionInCartPageTest(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible");

        cartPage.goToCartPage();

        cartPage.scrollDown();

        String actualSubsLabel = cartPage.verifySubscriptionLabelIsVisible();
        Assert.assertEquals(actualSubsLabel, "SUBSCRIPTION");

        String registeredEmail = setUp.getProperty("registeredEmail");
        cartPage.enterEmail(registeredEmail);

        cartPage.subscribeButton();

        String actualSuccessMessage = cartPage.verifySuccessMessage();
        Assert.assertEquals(actualSuccessMessage, "You have been successfully subscribed!");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
