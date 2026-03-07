package com.sauceDemo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.sauceDemo.base.BaseTests;
import com.sauceDemo.pages.HomePage;
import com.sauceDemo.pages.PDP;
import com.utils.RetryAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.Utils;

public class LoginTests extends BaseTests {
    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);


    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void validLogin(){
        JsonNode validUser = testDataFile.path("login").path("validUser");
        String userName = validUser.path("username").asText();
        String password = validUser.path("password").asText();

        boolean isLogin = loginPage.login(userName, password)
                .returnHome().isLoginSuccessful();
        softly().assertTrue(isLogin, "Not logged in!" );

        softly().assertAll();

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void invalidLogin(){
        JsonNode validUser = testDataFile.path("login").path("invalidUser");
        String userName = validUser.path("username").asText();
        String password = validUser.path("password").asText();

        boolean isLogin = loginPage.login(userName, password)
                .returnHome().isLoginSuccessful();
        softly().assertFalse(isLogin, "User logged in!" );

        softly().assertAll();

    }


    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void lockedOutLogin(){
        JsonNode validUser = testDataFile.path("login").path("lockedOutUser");
        String userName = validUser.path("username").asText();
        String password = validUser.path("password").asText();

        String errorMessage= loginPage.login(userName, password)
                .getErrorMessage();
        log.debug("Error message: {}", errorMessage);

        softly().assertTrue(errorMessage.contains("locked out"), "Check Error Message! " +errorMessage);

        softly().assertAll();
    }



}
