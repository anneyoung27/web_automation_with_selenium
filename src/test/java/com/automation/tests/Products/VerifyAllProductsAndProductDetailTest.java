package com.automation.tests.Products;

import com.base.TestBase;
import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;


public class VerifyAllProductsAndProductDetailTest extends TestBase {
    ProductsPage productsPage;
    static int PRODUCT_LIST_ORDER;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        productsPage = new ProductsPage(driver);
        Random random = new Random();

        Assert.assertTrue(productsPage.verifyLandingPage(), "Home page is visible");
        productsPage.goToProductsPage();

        PRODUCT_LIST_ORDER = random.nextInt(productsPage.getProductOrderNumber()) + 1;
    }

    @Test(description = "Test Case 8: Verify All Products and product detail page")
    public void verifyAllProductsAndProductDetailTest() throws InterruptedException {
        String actualProductsPage = productsPage.verifyNavigateToAllProductsPage();
        Assert.assertEquals(actualProductsPage, "ALL PRODUCTS");

        productsPage.verifyProductListIsVisible();

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER);

        String viewProductByProductIndex = productsPage.generateElementViewProductButton(productName);

        productsPage.viewProductButton(viewProductByProductIndex);

        Assert.assertTrue(productsPage.verifyProductInformationIsVisible(), "Product information is visible");



    }


    @AfterMethod
    public void quit(){
        tearDown();
    }
}
