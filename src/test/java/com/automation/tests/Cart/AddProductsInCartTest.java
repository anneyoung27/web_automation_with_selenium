package com.automation.tests.Cart;

import com.pages.CartPage;
import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;


public class AddProductsInCartTest {
    CartPage cartPage;
    ProductsPage productsPage;

    static int PRODUCT_LIST_ORDER_1; // to generate random choice for product
    static int PRODUCT_LIST_ORDER_2; // to generate random choice for product

    @BeforeMethod
    public void testSetUp(){
        setUp();
        cartPage = new CartPage(driver);
        productsPage = new ProductsPage(driver);

        Random random = new Random();

        // Generate product
        int totalProducts = productsPage.getProductOrderNumber();

        int firstProduct = random.nextInt(totalProducts) + 1;
        int secondProduct;

        // Ensure the second product is different
        do{
            secondProduct = random.nextInt(totalProducts) + 1;
        }while(firstProduct == secondProduct);

        PRODUCT_LIST_ORDER_1 = firstProduct;
        PRODUCT_LIST_ORDER_2 = secondProduct;
    }

    @Test(description = "Test Case 12: Add Products in Cart")
    public void addProductInCartTest(){
        Assert.assertTrue(productsPage.verifyLandingPage(), "Home Page is visible");

        productsPage.goToProductsPage();

        String productName1 = productsPage.getProductName(PRODUCT_LIST_ORDER_1);
        String productName2 = productsPage.getProductName(PRODUCT_LIST_ORDER_2);

        String addProductByProductIndex1 = productsPage.getProductIndex(productName1);
        String addProductByProductIndex2 = productsPage.getProductIndex(productName2);

        productsPage.addToCartButton(addProductByProductIndex1); // add first product

        productsPage.successfullyAddedModalButton();

        productsPage.addToCartButton(addProductByProductIndex2); // add second product

        productsPage.viewCart();

        String[] addedProductInCart = new String[] {productName1, productName2};

        cartPage.verifyAddedProductInCartIsVisible(addedProductInCart);

        cartPage.verifyPriceQuantityTotalProduct();
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
