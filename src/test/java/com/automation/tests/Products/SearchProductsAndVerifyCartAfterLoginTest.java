package com.automation.tests.Products;

import com.pages.CartPage;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class SearchProductsAndVerifyCartAfterLoginTest {
    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;

    static int PRODUCT_LIST_ORDER; // to generate randomly product
    static String USER_EMAIL;
    static String USER_PASSWORD;

    @BeforeMethod
    public void testSetUp(){
        setUp();

        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        Random random = new Random();

        PRODUCT_LIST_ORDER = random.nextInt(productsPage.getProductOrderNumber()) + 1;
        USER_EMAIL = setUp.getProperty("registeredEmail");
        USER_PASSWORD = setUp.getProperty("registeredPassword");
    }

    @Test(description = "Test Case 20: Search Products and Verify Cart after Login")
    public void searchProductAndVerifyCartAfterLoginTest(){
        Assert.assertTrue(productsPage.verifyLandingPage(), "Home page is visible");

        productsPage.goToProductsPage();

        String actualProductPageIsVisible = productsPage.verifyNavigateToAllProductsPage();
        Assert.assertEquals(actualProductPageIsVisible, "ALL PRODUCTS");

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER);

        System.out.println("#DEBUG -> " + productName);
        productsPage.findProduct(productName);

        String setProductIndex = productsPage.getProductIndex(productName);

        productsPage.addToCartButton(setProductIndex);

        productsPage.viewCart();

        String [] products = new String[] {productName};

        cartPage.verifyAddedProductInCartIsVisible(products);

        loginPage.goToLogIn();

        loginPage.loginAccount(USER_EMAIL, USER_PASSWORD);

        loginPage.clickLoginButton();

        cartPage.goToCartPage();

        cartPage.verifyAddedProductInCartIsVisible(products);
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
