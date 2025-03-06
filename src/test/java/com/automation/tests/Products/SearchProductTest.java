package com.automation.tests.Products;

import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class SearchProductTest {
    ProductsPage productsPage;

    @BeforeMethod
    public void testSetUp(){
        setUp();

        productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.verifyLandingPage(), "Home page is visible");

        productsPage.goToProductsPage();
    }

    @Test(description = "Test Case 9: Search Product", dataProvider = "productToSearch")
    public void searchProductTest(String searchProduct){
        String actualProductsPage = productsPage.verifyNavigateToAllProductsPage();
        Assert.assertEquals(actualProductsPage, "ALL PRODUCTS");

        productsPage.findProduct(searchProduct);

        String actualSearchProduct = productsPage.verifyIfSearchProductIsVisible();
        Assert.assertEquals(actualSearchProduct, "SEARCHED PRODUCTS");

        productsPage.verifySearchProductIsRelatedToSearchAreVisible(searchProduct);
    }

    @DataProvider(name = "productToSearch")
    public Object[][] products(){
        return new Object[][] {
                {"Tshirt"}
        };
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
