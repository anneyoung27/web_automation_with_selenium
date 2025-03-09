package com.automation.tests.Cart;

import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class ViewAndCartBrandProductsTest {
    ProductsPage productsPage;
    static int BRANDS_LIST_ORDER_1;
    static int BRANDS_LIST_ORDER_2;

    @BeforeMethod
    public void testSetUp(){
        setUp();

        productsPage = new ProductsPage(driver);
        Random random = new Random();

        int firstBrand = random.nextInt(productsPage.getBrandProductCountNumber()) + 1;
        int secondBrand;

        // Ensure the second product is different
        do{
            secondBrand = random.nextInt(productsPage.getBrandProductCountNumber()) + 1;
        }while(firstBrand == secondBrand);

        BRANDS_LIST_ORDER_1 = firstBrand;
        BRANDS_LIST_ORDER_2 = secondBrand;
    }

    @Test(description = "Test Case 19: View & Cart Brand Products")
    public void viewAndCartBrandProductTest(){

        productsPage.goToProductsPage();

        String actualBrandLabel = productsPage.verifyIfBrandLabelIsVisible();
        Assert.assertEquals(actualBrandLabel, "BRANDS");

        String productBrandName = productsPage.getProductBrandsName(BRANDS_LIST_ORDER_1);

        String brandProduct = productsPage.generateElementClickBrandProduct(productBrandName);

        productsPage.clickBrandProduct(brandProduct);

        String actualBrandPageAndProductLabel = productsPage.verifyBrandPageAndProductsIsVisible();
        String expectedBrandPageAndProductLabel = brandProduct.substring(brandProduct.lastIndexOf("/") + 1);
        Assert.assertEquals(actualBrandPageAndProductLabel, String.format("BRAND - %s PRODUCTS", expectedBrandPageAndProductLabel.toUpperCase()));

        String productBrandName2 = productsPage.getProductBrandsName(BRANDS_LIST_ORDER_2);

        String brandProduct2 = productsPage.generateElementClickBrandProduct(productBrandName2);

        productsPage.clickBrandProduct(brandProduct2);

        String brandName = brandProduct2.substring(brandProduct2.lastIndexOf("/") + 1);
        productsPage.verifyThatUserIsNavigatedToBrandPageAndCanSeeProducts(brandName);
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
