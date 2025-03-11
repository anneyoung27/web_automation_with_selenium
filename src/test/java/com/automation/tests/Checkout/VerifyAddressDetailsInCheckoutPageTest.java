package com.automation.tests.Checkout;

import com.automation.utils.Constant;
import com.pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.base.DriverManager.*;

public class VerifyAddressDetailsInCheckoutPageTest {
    LoginPage loginPage;
    HomePage homePage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    private static String GENERATED_NAME;
    private static int PRODUCT_LIST_ORDER;

    private static String TITLE;
    private static String FIRST_NAME;
    private static String LAST_NAME;
    private static String COMPANY;
    private static String ADDRESS;
    private static String ADDRESS2;
    private static String COUNTRY;
    private static String STATE;
    private static String CITY;
    private static String ZIPCODE;
    private static String MOBILE_NUMBER;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        Random random = new Random();
        PRODUCT_LIST_ORDER = random.nextInt(productsPage.getProductOrderNumber()) + 1;

        GENERATED_NAME = Constant.NAME;

        TITLE = Constant.TITLE;
        FIRST_NAME = Constant.FIRST_NAME;
        LAST_NAME = Constant.LAST_NAME;
        COMPANY = Constant.COMPANY;
        ADDRESS = Constant.ADDRESS;
        ADDRESS2 = Constant.ADDRESS2;
        COUNTRY = Constant.COUNTRY;
        STATE = Constant.STATE;
        CITY = Constant.CITY;
        ZIPCODE = Constant.ZIPCODE;
        MOBILE_NUMBER = Constant.MOBILE_NUMBER;
    }

    @Test(description = "Test Case 23: Verify address details in checkout page")
    public void verifyAddressDetailsINCheckoutPageTest(){
        loginPage.goToLogIn();

        loginPage.typeUserInformation(GENERATED_NAME, Constant.EMAIL);

        loginPage.clickSignUpButton();

        String actualAccountInformation = loginPage.verifyAccountInformationIsVisible();
        Assert.assertEquals(actualAccountInformation, "ENTER ACCOUNT INFORMATION");

        loginPage.fillAccountInformation(TITLE,
                                         Constant.PASSWORD,
                                         Constant.DATE,
                                         Constant.MONTH,
                                         Constant.YEAR);

        loginPage.selectCheckbox();

        loginPage.fillAddressInformation(FIRST_NAME, LAST_NAME, COMPANY,
                                         ADDRESS, ADDRESS2, COUNTRY,
                                         STATE, CITY, ZIPCODE, MOBILE_NUMBER);

        loginPage.createAccountButton();

        String actualAccountWasCreated = loginPage.verifyAccountWasCreatedIsVisible();
        Assert.assertEquals(actualAccountWasCreated, "ACCOUNT CREATED!");

        loginPage.clickContinueButton();

        String actualLoggedName = loginPage.verifyLoggedInAsUserNameIsVisible(GENERATED_NAME);
        Assert.assertEquals(actualLoggedName, GENERATED_NAME);

        String productName = productsPage.getProductName(PRODUCT_LIST_ORDER);

        String addProductByProductIndex = productsPage.getProductIndex(productName);

        productsPage.addToCartButton(addProductByProductIndex);

        productsPage.viewCart();

        String[] addedProductInCart = new String[] {productName};

        cartPage.verifyAddedProductInCartIsVisible(addedProductInCart);

        cartPage.proceedToCheckout();

        Assert.assertTrue(checkoutPage.verifyTheDeliveryAddressFilledAtTheTimeRegistration(TITLE, FIRST_NAME, LAST_NAME, COMPANY,
                                                                                           ADDRESS, ADDRESS2, COUNTRY, STATE, CITY,
                                                                                           ZIPCODE, MOBILE_NUMBER), "Delivery address is same at the time registration");


        Assert.assertTrue(checkoutPage.verifyTheBillingAddressFilledAtTheTimeRegistration(TITLE, FIRST_NAME, LAST_NAME, COMPANY,
                                                                                          ADDRESS, ADDRESS2, COUNTRY, STATE, CITY,
                                                                                          ZIPCODE, MOBILE_NUMBER), "Billing address is same at the time registration");

        loginPage.clickDeleteAccountButton();

        String actualDeleteAccountLabel = loginPage.verifyAccountDelete();
        Assert.assertEquals(actualDeleteAccountLabel, "ACCOUNT DELETED!");




    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
