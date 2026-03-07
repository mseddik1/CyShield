package com.sauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.VisualTestUtil;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage extends BasePage{
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By errorContainer = By.xpath("//h3[@data-test='error']");
    private final By itemPrice = By.xpath("//div[@data-test='inventory-item-price']");
    private final By itemTotal = By.xpath("//div[@data-test='subtotal-label']");
    private final By successMessage = By.xpath("//h2[@data-test='complete-header']");
    private final By checkoutCompleteContainer = By.id("checkout_complete_container");


    public CheckoutPage(WebDriver driver){
        super(driver);
    }

    public CheckoutPage continueCheckout(String firstName, String lastName, String postalCode){
        typeElement(firstNameInput, firstName);
        typeElement(lastNameInput, lastName);
        typeElement(postalCodeInput,postalCode);
        clickElement(continueButton);
        return this;
    }



    public String getErrorMessage(){
       return  myWait().until(ExpectedConditions
                .visibilityOf(driver.findElement(errorContainer))).getText();
    }



    public Double sumItemPrice(){

        Double totalPrice=0.0;
        for(WebElement e : driver.findElements(itemPrice)){
            totalPrice += Double.parseDouble(e.getText().split("\\$")[1]);
        }

        return totalPrice;
    }

    public Double getItemTotal(){
        String subtotalText = driver.findElement(itemTotal).getText();
        String amount = subtotalText.split("\\$")[1];

        return Double.valueOf(amount);
    }


    public CheckoutPage clickFinish(){
        clickElement(finishButton);
        return this;
    }

    public CheckoutPage getSuccessMessage(){
         String successMessageString = myWait().until(ExpectedConditions
                .visibilityOf(driver.findElement(successMessage))).getText();
         softly().assertEquals(successMessageString,"Thank you for your order!",
                 "Check error message!");

         return this;
    }

    public boolean isValidCheckoutVisual(){
        return visualTestUtil.takeAndCompareScreenshot(checkoutCompleteContainer,"test",null);
    }
}
