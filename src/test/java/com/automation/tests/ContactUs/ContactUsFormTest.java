package com.automation.tests.ContactUs;

import com.automation.utils.Constant;
import com.pages.ContactUsPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.base.DriverManager.*;

public class ContactUsFormTest {
    ContactUsPage contactUsPage;

    @BeforeMethod
    public void testSetUp(){
        setUp();
        contactUsPage = new ContactUsPage(driver);
        contactUsPage.goToContactUs();
    }

    @Test(description = "Test Case 6: Contact Us Form")
    public void contactUsFormTest(){
        String actualLabel = contactUsPage.verifyGetInTouchIsVisible();
        Assert.assertEquals(actualLabel, "GET IN TOUCH");

        contactUsPage.setCustomerName(Constant.NAME);
        contactUsPage.setCustomerEmail(Constant.EMAIL);
        contactUsPage.setContactSubject(Constant.SUBJECT);
        contactUsPage.setCustomerMessage(Constant.MESSAGES);
        contactUsPage.uploadFile(System.getProperty("user.dir")+"\\src\\test\\resources\\files\\upload_img.jpg");

        contactUsPage.submitContactButton();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        String actualSuccessMessage = contactUsPage.verifySuccessfulMessage();
        Assert.assertEquals(actualSuccessMessage, "Success! Your details have been submitted successfully.");

        contactUsPage.homeButton();

        Assert.assertTrue(contactUsPage.verifyLandingPage(), "Landed to home page successfully!");
    }

    @AfterMethod
    public void quit(){
        tearDown();
    }
}
