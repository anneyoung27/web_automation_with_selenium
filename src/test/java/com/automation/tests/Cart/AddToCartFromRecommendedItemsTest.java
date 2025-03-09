package com.automation.tests.Cart;

import com.pages.CartPage;
import com.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class AddToCartFromRecommendedItemsTest {
    HomePage homePage;
    CartPage cartPage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);

    }

    @Test(description = "Test Case 22: Add to cart from Recommended items")
    public void addToCartFromRecommendedItemsTest() {
        homePage.scrollDownToFooter();

        String actualRecommendationItemsLabel = homePage.verifyRecommendationItemsIsVisible();
        Assert.assertEquals(actualRecommendationItemsLabel, "RECOMMENDED ITEMS");

        homePage.addRecommendedItemToCart();

        homePage.clickViewCart();

        Assert.assertTrue(cartPage.verifyIfAddedItemFromRecommendedItemsInCart(), "Added items is visible");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}

