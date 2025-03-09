package com.automation.tests.Products;

import com.automation.utils.Constant;
import com.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class AddReviewOnProductTest {
    ProductsPage productsPage;

    static int PRODUCT_LIST_ORDER; // to generate randomly product

    static String REVIEWER_NAME;
    static String REVIEWER_EMAIL;
    static String REVIEW_TEXT;

    @BeforeMethod
    public void testSetUp(){
        setUp();

        productsPage = new ProductsPage(driver);

        Random random = new Random();
        PRODUCT_LIST_ORDER = random.nextInt(productsPage.getProductOrderNumber()) + 1;

        REVIEWER_NAME = Constant.NAME;
        REVIEWER_EMAIL = Constant.EMAIL;
        REVIEW_TEXT = Constant.MESSAGES;
    }

    @Test(description = "Test Case 21: Add review on product")
    public void addReviewOnProductTest() throws InterruptedException {
        productsPage.goToProductsPage();

        String actualProductPageIsVisible = productsPage.verifyNavigateToAllProductsPage();
        Assert.assertEquals(actualProductPageIsVisible, "ALL PRODUCTS");

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER);

        String viewProductByProductIndex = productsPage.generateElementViewProductButton(productName);

        productsPage.viewProductButton(viewProductByProductIndex);

        String actualWriteYourReviewIsVisible = productsPage.verifyWriteYourReviewIsVisible();
        Assert.assertEquals(actualWriteYourReviewIsVisible, "WRITE YOUR REVIEW");

        productsPage.setReviewerName(REVIEWER_NAME);
        productsPage.setReviewerEmail(REVIEWER_EMAIL);
        productsPage.setReview(REVIEW_TEXT);

        productsPage.submitReviewButton();

        String actualSuccessReviewMessage = productsPage.verifySuccessfullySubmitReview();
        Assert.assertEquals(actualSuccessReviewMessage, "Thank you for your review.");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}