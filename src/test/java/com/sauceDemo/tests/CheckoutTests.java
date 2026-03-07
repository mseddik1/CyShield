package com.sauceDemo.tests;

import com.dataProviders.DataProviders;
import com.fasterxml.jackson.databind.JsonNode;
import com.sauceDemo.base.BaseTests;
import com.sauceDemo.pages.CheckoutPage;
import com.sauceDemo.pages.HomePage;
import com.sauceDemo.pages.PDP;
import com.utils.RetryAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTests {

    private static final Logger log = LoggerFactory.getLogger(CheckoutTests.class);
    private JsonNode checkoutNode = testDataFile.path("checkout");



    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void a(){
        loginPage.successfulLogin();

        softly().assertAll();

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void addToCart(){
        HomePage homePage = loginPage.successfulLogin();
        String firstItem = homePage.getFirstItemName();
        PDP pdp = homePage.openFirstItem();
        softly().assertEquals(pdp.getProductName(),firstItem,"PDP Item name mismatch!");
        softly().assertEquals(pdp.clickAddToCart().openCart().getFirstItemName(), firstItem, "Cart Item name mismatch!");
        softly().assertAll();


    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void successfulCheckout(){
        JsonNode validCheckout = checkoutNode.path("validCheckout");
        String firstName = validCheckout.path("firstName").asText();
        String lastName = validCheckout.path("lastName").asText();
        String postalCode = validCheckout.path("postalCode").asText();
        CheckoutPage checkoutPage = loginPage.successfulLogin().clickAddToCart(0).clickAddToCart(0).clickAddToCart(3)
                .openCart().removeFirstProduct()
                .clickCheckout().continueCheckout(firstName,lastName,postalCode);

        softly().assertEquals(checkoutPage.getItemTotal(), checkoutPage.sumItemPrice(), "Check item prices!");

        log.info(checkoutPage.getItemTotal().toString());
        log.info(checkoutPage.sumItemPrice().toString());


        checkoutPage.clickFinish().getSuccessMessage();
        softly().assertTrue(checkoutPage.isValidCheckoutVisual(), "Visual Mismatch Detected!");
        softly().assertAll();

    }



    @Test(retryAnalyzer = RetryAnalyzer.class, dataProvider = "emptyCheckoutData", dataProviderClass = DataProviders.class)
    public void emptyCheckout(JsonNode data){

        String firstName = data.path("firstName").asText();
        String lastName = data.path("lastName").asText();
        String postalCode = data.path("postalCode").asText();
        String expectedErrorMessage = data.path("error").asText();

        String errorMessage = loginPage.successfulLogin().clickAddToCart(0)
                .openCart()
                .clickCheckout().continueCheckout(firstName,lastName,postalCode).getErrorMessage();

        softly().assertEquals(errorMessage, expectedErrorMessage, "Check error message! -> "+errorMessage);
        softly().assertAll();

    }



    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void invalidCheckout(){
        JsonNode invalidCheckout = checkoutNode.path("invalidCheckout");
        String firstName = invalidCheckout.path("firstName").asText();
        String lastName = invalidCheckout.path("lastName").asText();
        String postalCode = invalidCheckout.path("postalCode").asText();
        String expectedErrorMessage = invalidCheckout.path("error").asText();

        String errorMessage = loginPage.successfulLogin().clickAddToCart(0)
                .openCart()
                .clickCheckout().continueCheckout(firstName,lastName,postalCode).getErrorMessage();


        softly().assertEquals(errorMessage, expectedErrorMessage, "Check Error Message!");

        softly().assertAll();


    }

}
