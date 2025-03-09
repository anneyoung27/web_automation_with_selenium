package com.automation.tests.Products;

import com.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;
import static com.pages.HomePage.capitalizeFirstLetter;

public class ViewCategoryProductTest {
    HomePage homePage;

    static int CATEGORY; // to generate random choice for category [WOMEN, MEN, KIDS]
    static String CATEGORY_NAME;
    Random random;

    @BeforeMethod
    public void testSetUp(){
        setUp();

        homePage = new HomePage(driver);
        random = new Random();

        CATEGORY = random.nextInt(homePage.getCategoryCount());
    }

    @Test(description = "Test Case 18: View Category Products")
    public void viewCategoryProductTest(){
        Assert.assertTrue(homePage.verifyLandingPage(), "Home page is visible");

        String actualCategoryLabel = homePage.verifyCategoryIsVisible();
        Assert.assertEquals(actualCategoryLabel, "CATEGORY");

        CATEGORY_NAME = homePage.getCategoryName(CATEGORY);

        String byCategory = CATEGORY_NAME;
        String productByCategory = "";

        if (CATEGORY_NAME.equalsIgnoreCase("WOMEN")){
            homePage.clickWomenCategoryExtend();
            String [] productCategoryForWomen = new String[] {"DRESS", "TOPS", "SAREE"};
            // get product by random
            int randomIndex = random.nextInt(productCategoryForWomen.length);
            productByCategory = productCategoryForWomen[randomIndex];

            homePage.clickProductCategory(capitalizeFirstLetter(byCategory), capitalizeFirstLetter(productByCategory)); // click product by category
        }else if (CATEGORY_NAME.equalsIgnoreCase("MEN")){
            homePage.clickMenCategoryExtend();
            String [] productCategoryForMen = new String[] {"TSHIRTS", "JEANS"};
            // get product by random
            int randomIndex = random.nextInt(productCategoryForMen.length);
            productByCategory = productCategoryForMen[randomIndex];

            homePage.clickProductCategory(capitalizeFirstLetter(byCategory), capitalizeFirstLetter(productByCategory)); // click product by category
        }else{
            homePage.clickKidsCategoryExtend();
            String [] productCategoryForKids = new String[] {"DRESS", "TOPS & SHIRTS"};
            // get product by random
            int randomIndex = random.nextInt(productCategoryForKids.length);
            productByCategory = productCategoryForKids[randomIndex];

            homePage.clickProductCategory(capitalizeFirstLetter(byCategory), capitalizeFirstLetter(productByCategory)); // click product by category
        }

        String actualCategoryLabelDisplayed = homePage.verifyCategoryPageAccordingSelectedCategoryAndProduct();
        Assert.assertEquals(actualCategoryLabelDisplayed, String.format("%s - %s PRODUCTS",byCategory, productByCategory));
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}