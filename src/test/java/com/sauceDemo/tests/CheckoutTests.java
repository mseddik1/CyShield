package com.sauceDemo.tests;

import com.dataProviders.DataProviders;
import com.fasterxml.jackson.databind.JsonNode;
import com.sauceDemo.base.BaseTests;
import com.sauceDemo.pages.CheckoutPage;
import com.sauceDemo.pages.HomePage;
import com.sauceDemo.pages.PDP;
import com.utils.AllureUtils;
import com.utils.RetryAnalyzer;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTests {

    private static final Logger log = LoggerFactory.getLogger(CheckoutTests.class);
    private JsonNode checkoutNode = testDataFile.path("checkout");




    @Test(groups = {"smoke","regression"},retryAnalyzer = RetryAnalyzer.class, description = "User should be able to add items to cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void addToCart(){
        HomePage homePage = loginPage.successfulLogin();
        String firstItem = homePage.getFirstItemName();
        PDP pdp = homePage.openFirstItem();
        Allure.step("Verify PDP item name matches selected item", () -> {
            softly().assertEquals(pdp.getProductName(), firstItem, "PDP Item name mismatch!");
            AllureUtils.attachScreenshot("PDP item");
        });
        Allure.step("Verify cart item name matches selected item", () ->{
            softly().assertEquals(pdp.clickAddToCart().openCart().getFirstItemName(), firstItem, "Cart Item name mismatch!");
            AllureUtils.attachScreenshot("Cart item");
        });

        softly().assertAll();


    }

    @Test(groups = {"smoke"},retryAnalyzer = RetryAnalyzer.class, description = "User should be able to complete checkout successfully")
    @Story("Successful Checkout")
    @Severity(SeverityLevel.CRITICAL)
    public void successfulCheckout(){
        JsonNode validCheckout = checkoutNode.path("validCheckout");
        String firstName = validCheckout.path("firstName").asText();
        String lastName = validCheckout.path("lastName").asText();
        String postalCode = validCheckout.path("postalCode").asText();
        CheckoutPage checkoutPage = loginPage.successfulLogin().clickAddToCart(0).clickAddToCart(0).clickAddToCart(3)
                .openCart().removeFirstProduct()
                .proceedToCheckout().continueCheckout(firstName,lastName,postalCode);

        Allure.step("Check item prices sum and total sum are equal",()->{
            softly().assertEquals(checkoutPage.getItemTotal(), checkoutPage.sumItemPrice(), "Check item prices!");
            AllureUtils.attachScreenshot("Item prices and total price");
        });

        log.info(checkoutPage.getItemTotal().toString());
        log.info(checkoutPage.sumItemPrice().toString());


        Allure.step("Complete checkout and verify success message", () -> {
            checkoutPage.clickFinish().getSuccessMessage();
            AllureUtils.attachScreenshot("Checkout success page");
        });


        Allure.step("Visually compare success page", () -> {
            boolean matched = checkoutPage.isValidCheckoutVisual();
            softly().assertTrue(matched, "Visual Mismatch Detected!");
            AllureUtils.attachText("Visual comparison result", "Matched = " + matched);
        });

        softly().assertAll();

    }



    @Test(groups = {"smoke"},retryAnalyzer = RetryAnalyzer.class, dataProvider = "emptyCheckoutData", dataProviderClass = DataProviders.class,
            description = "User should see validation error when checkout fields are empty")
    @Story("Empty Checkout Fields")
    @Severity(SeverityLevel.NORMAL)
    public void emptyCheckout(JsonNode data){

        String firstName = data.path("firstName").asText();
        String lastName = data.path("lastName").asText();
        String postalCode = data.path("postalCode").asText();
        String expectedErrorMessage = data.path("error").asText();

        String errorMessage = loginPage.successfulLogin().clickAddToCart(0)
                .openCart()
                .proceedToCheckout().continueCheckout(firstName,lastName,postalCode).getErrorMessage();

        Allure.step("Verify checkout validation error message", () -> {
            softly().assertEquals(errorMessage, expectedErrorMessage, "Check error message! -> " + errorMessage);
            AllureUtils.attachScreenshot("Checkout validation error");
        });
        softly().assertAll();

    }



    @Test(groups = {"regression"},retryAnalyzer = RetryAnalyzer.class, description = "User should not be able to checkout with invalid data")
    @Story("Invalid Checkout")
    @Severity(SeverityLevel.NORMAL)
    public void invalidCheckout(){
        JsonNode invalidCheckout = checkoutNode.path("invalidCheckout");
        String firstName = invalidCheckout.path("firstName").asText();
        String lastName = invalidCheckout.path("lastName").asText();
        String postalCode = invalidCheckout.path("postalCode").asText();
        String expectedErrorMessage = invalidCheckout.path("error").asText();

        String errorMessage = loginPage.successfulLogin().clickAddToCart(0)
                .openCart()
                .proceedToCheckout().continueCheckout(firstName,lastName,postalCode).getErrorMessage();



        Allure.step("Verify checkout validation error message", () -> {
            softly().assertEquals(errorMessage, expectedErrorMessage, "Check error message! -> " + errorMessage);
            AllureUtils.attachScreenshot("Checkout validation error");
        });

        softly().assertAll();


    }

}
