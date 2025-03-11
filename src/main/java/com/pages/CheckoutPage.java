package com.pages;

import com.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends TestBase {
    WebDriver driver;

    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By deliveryAddress = By.xpath("//ul[@id='address_delivery']");

    public boolean verifyTheDeliveryAddressFilledAtTheTimeRegistration(
            String title, String first_name, String last_name, String company,
            String address, String address2, String country, String state, String city,
            String zipCode, String mobile_phone) {

        String full_name = title + " " + first_name + " " + last_name;
        String full_address3 = city + " " + state + " " + zipCode;

        List<WebElement> addressElements = findElements(driver, deliveryAddress);

        if (addressElements.isEmpty()) {
            System.out.println("Delivery address section not found!");
            return false;
        }

        List<WebElement> dataPerLine = addressElements.getFirst().findElements(By.tagName("li"));

        if (dataPerLine.size() < 8) {
            System.out.println("Not enough address fields found!");
            return false;
        }

        String[] expectedValues = {full_name, company, address, address2, full_address3, country, mobile_phone};

        for (int i = 1; i <= 7; i++) {
            if (!dataPerLine.get(i).getText().trim().contains(expectedValues[i - 1])) {
                System.out.println("Mismatch found in line " + i + ": Expected '" + expectedValues[i - 1] +
                        "', Found '" + dataPerLine.get(i).getText().trim() + "'");
                return false;
            }
        }
        return true;
    }


    public boolean verifyTheBillingAddressFilledAtTheTimeRegistration(String title, String first_name, String last_name, String company,
                                                                      String address, String address2, String country, String state, String city,
                                                                      String zipCode, String mobile_phone){

        String full_name = title + " " + first_name + " " + last_name;
        String full_address3 = city + " " + state + " " + zipCode;

        List<WebElement> addressElements = findElements(driver, deliveryAddress);

        if (addressElements.isEmpty()) {
            System.out.println("Delivery address section not found!");
            return false;
        }

        List<WebElement> dataPerLine = addressElements.getFirst().findElements(By.tagName("li"));

        if (dataPerLine.size() < 8) {
            System.out.println("Not enough address fields found!");
            return false;
        }

        String[] expectedValues = {full_name, company, address, address2, full_address3, country, mobile_phone};

        for (int i = 1; i <= 7; i++) {
            if (!dataPerLine.get(i).getText().trim().contains(expectedValues[i - 1])) {
                System.out.println("Mismatch found in line " + i + ": Expected '" + expectedValues[i - 1] +
                        "', Found '" + dataPerLine.get(i).getText().trim() + "'");
                return false;
            }
        }
        return true;
    }

}
