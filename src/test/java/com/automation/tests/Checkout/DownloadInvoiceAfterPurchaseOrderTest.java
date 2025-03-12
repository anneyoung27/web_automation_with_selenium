package com.automation.tests.Checkout;

import com.automation.utils.Constant;
import com.pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Month;
import java.util.Random;

import static com.base.DriverManager.*;

public class DownloadInvoiceAfterPurchaseOrderTest {
    LoginPage loginPage;
    HomePage homePage;
    ProductsPage productsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;

    private static int PRODUCT_LIST_ORDER; // to generate random choice for product
    private static final String GENERATED_NAME = Constant.NAME; // to generate name
    private static final String MONTH = Constant.MONTH; // to generate month for payment credit card
    private static final String YEAR = Constant.YEAR; // to generate month for payment credit card

    @BeforeMethod
    public void testSetUp() {
        setUp();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        paymentPage = new PaymentPage(driver);

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

        cartPage.registerOrLogin();

        loginPage.typeUserInformation(GENERATED_NAME, Constant.EMAIL);

        loginPage.clickSignUpButton();

        loginPage.fillAccountInformation(Constant.TITLE,
                                         Constant.PASSWORD,
                                         Constant.DATE,
                                         MONTH,
                                         YEAR);

        loginPage.selectCheckbox();

        loginPage.fillAddressInformation(Constant.FIRST_NAME, Constant.LAST_NAME, Constant.COMPANY,
                                         Constant.ADDRESS, Constant.ADDRESS2, Constant.COUNTRY,
                                         Constant.STATE, Constant.CITY, Constant.ZIPCODE, Constant.MOBILE_NUMBER);

        loginPage.createAccountButton();

        String actualAccountWasCreated = loginPage.verifyAccountWasCreatedIsVisible();
        Assert.assertEquals(actualAccountWasCreated, "ACCOUNT CREATED!");

        loginPage.clickContinueButton();

        String actualLoggedName = loginPage.verifyLoggedInAsUserNameIsVisible(GENERATED_NAME);
        Assert.assertEquals(actualLoggedName, GENERATED_NAME);

        cartPage.goToCartPage();

        cartPage.proceedToCheckout();

        String actualAddressDetail = cartPage.verifyAddressDetailIsVisible();
        Assert.assertEquals(actualAddressDetail, "Address Details");

        String actualReviewYourOrder = cartPage.verifyReviewYourOrderIsVisible();
        Assert.assertEquals(actualReviewYourOrder, "Review Your Order");

        cartPage.setCommentBeforeCheckout(Constant.MESSAGES);

        cartPage.placeOrderButton();

        paymentPage.setNameOnCard(GENERATED_NAME.toUpperCase());

        paymentPage.setCardNumber(Constant.CREDIT_NUMBER);

        paymentPage.setCVC(Constant.CVC_NUMBER);

        int getMonth = Month.valueOf(MONTH.toUpperCase()).getValue();
        String formattedMonth = String.format("%02d", getMonth);

        paymentPage.setExpiryMonth(formattedMonth);

        int getYear = Integer.parseInt(YEAR);
        int expiryYear = getYear + 10;

        paymentPage.setExpiryYear(String.valueOf(expiryYear));

        paymentPage.payAndConfirmButton();

        String actualOrderPlaceLabel = paymentPage.verifyOrderedPlaceLabel();
        Assert.assertEquals(actualOrderPlaceLabel, "Congratulations! Your order has been confirmed!");

        paymentPage.clickDownloadInvoice();

        paymentPage.clickContinueButton();

        loginPage.deleteAccountButton();

        String actualAccountDeleteLabel = loginPage.verifyAccountDelete();
        Assert.assertEquals(actualAccountDeleteLabel, "ACCOUNT DELETED!");

        loginPage.clickContinueButton();

    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
