package com.automation.tests.Cart;

import com.pages.CartPage;
import com.pages.HomePage;
import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class VerifyProductQuantityInCartTest {
    HomePage homePage;
    CartPage cartPage;
    ProductsPage productsPage;

    static int PRODUCT_LIST_ORDER;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        cartPage = new CartPage(driver);
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        Random random = new Random();

        PRODUCT_LIST_ORDER = random.nextInt(productsPage.getProductOrderNumber()) + 1;
    }

    @Test(description = "Test Case 13: Verify Product quantity in Cart")
    public void verifyProductQuantityInCartTest(){
        Assert.assertTrue(productsPage.verifyLandingPage(), "Home Page is visible");

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER);

        String viewProductByProductIndex = productsPage.generateElementViewProductButton(productName);

        productsPage.viewProductButton(viewProductByProductIndex);

        String value = "4";
        productsPage.addQuantityProduct(String.valueOf(Integer.parseInt(value)));

        productsPage.addToCartButton();

        productsPage.viewCart();

        int actualIncreasedQuantity = cartPage.verifyDisplayedQuantityInCart();
        Assert.assertEquals(actualIncreasedQuantity, Integer.parseInt(value), "Displayed with exact quantity");
    }


    @AfterMethod
    public void quit(){
        tearDown();
    }
}
