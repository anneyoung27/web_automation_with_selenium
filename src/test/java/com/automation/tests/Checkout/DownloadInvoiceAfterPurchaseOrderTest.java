package com.automation.tests.Checkout;

import com.pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class DownloadInvoiceAfterPurchaseOrderTest {
    LoginPage loginPage;
    HomePage homePage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    private static int PRODUCT_LIST_ORDER;

    @BeforeMethod
    public void testSetUp() {
        setUp();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        Random random = new Random();
        PRODUCT_LIST_ORDER = random.nextInt(productsPage.getProductOrderNumber()) + 1;

    }

    @Test(description = "Test Case 24: Download invoice after purchase order")
    public void downloadInvoiceAfterPurchaseOrderTest(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible");

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER);

        String addProductByProductIndex = productsPage.getProductIndex(productName);

        productsPage.addToCartButton(addProductByProductIndex);

        productsPage.viewCart();

        String[] addedProductInCart = new String[] {productName};

        cartPage.verifyAddedProductInCartIsVisible(addedProductInCart);

        cartPage.proceedToCheckout();



    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
