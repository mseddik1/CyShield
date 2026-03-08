package com.sauceDemo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.sauceDemo.base.BaseTests;
import com.sauceDemo.pages.HomePage;
import com.sauceDemo.pages.PDP;
import com.utils.AllureUtils;
import com.utils.RetryAnalyzer;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.Utils;


@Feature("Login")
public class LoginTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);


    @Test(retryAnalyzer = RetryAnalyzer.class,description = "Valid user should login successfully")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void validLogin(){
        JsonNode validUser = testDataFile.path("login").path("validUser");
        String username = validUser.path("username").asText();
        String password = validUser.path("password").asText();

        HomePage homePage = loginPage.login(username, password)
                .returnHome();

        homePage.waitUntilLoaded();

        boolean isLogin = homePage.isLoginSuccessful();
        softly().assertTrue(isLogin, "Not logged in!" );
        AllureUtils.attachScreenshot("Products page displayed");

        softly().assertAll();

    }

    @Test(retryAnalyzer = RetryAnalyzer.class,description = "Invalid user should not login and should get an error message.")
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidLogin(){
        JsonNode validUser = testDataFile.path("login").path("invalidUser");
        String username = validUser.path("username").asText();
        String password = validUser.path("password").asText();


        boolean isLogin = loginPage.login(username, password)
                .returnHome().isLoginSuccessful();
        softly().assertFalse(isLogin, "User logged in!" );
        AllureUtils.attachScreenshot("User is not logged in");

        softly().assertAll();

    }


    @Test(retryAnalyzer = RetryAnalyzer.class,description = "Locked out user should get an error message" )
    @Story("Valid Login")
    @Severity(SeverityLevel.NORMAL)
    public void lockedOutLogin(){
        JsonNode validUser = testDataFile.path("login").path("lockedOutUser");
        String userName = validUser.path("username").asText();
        String password = validUser.path("password").asText();

        String errorMessage= loginPage.login(userName, password)
                .getErrorMessage();
        log.debug("Error message: {}", errorMessage);

        softly().assertTrue(errorMessage.contains("locked out"), "Check Error Message! " +errorMessage);
        AllureUtils.attachScreenshot("Locked user gets error message");

        softly().assertAll();
    }



}
