package com.sauceDemo.tests;

import com.sauceDemo.base.BaseTests;
import com.sauceDemo.pages.HomePage;
import com.sauceDemo.pages.PDP;
import com.utils.RetryAnalyzer;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTests {


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
        loginPage.successfulLogin().clickAddToCart(0).clickAddToCart(3)
                .openCart().removeFirstProduct().clickCheckout();

        softly().assertAll();

    }
}
