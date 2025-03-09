package com.automation.tests.Cart;

import com.pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class RemoveProductsFromCartTest {
    HomePage homePage;
    ProductsPage productsPage;
    CartPage cartPage;

    static int PRODUCT_LIST_ORDER_1; // to generate random choice for product

    @BeforeMethod
    public void testSetUp(){
        setUp();
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);

        Random random = new Random();
        int totalProducts = productsPage.getProductOrderNumber();

        PRODUCT_LIST_ORDER_1 = random.nextInt(totalProducts) + 1;
    }

    @Test(description = "Test Case 17: Remove products from cart")
    public void removeProductsFromCartTest(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible");

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER_1);
        String addProductByProductIndex = productsPage.getProductIndex(productName);

        productsPage.addToCartButton(addProductByProductIndex); // add product

        productsPage.successfullyAddedModalButton();

        cartPage.goToCartPage();

        String actualShoppingCart = cartPage.verifyCartPage();
        Assert.assertEquals(actualShoppingCart, "Shopping Cart");

        cartPage.clickRemoveProductFromCart();

        if (cartPage.verifyDisplayedQuantityInCart() == 0){
            Assert.assertTrue(true, "Product removed from the cart");
        }else{
            Assert.assertFalse(false, "Product not removed from the cart");
        }

    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
