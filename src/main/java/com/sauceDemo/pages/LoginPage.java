package com.sauceDemo.pages;

import com.fasterxml.jackson.databind.JsonNode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{
    private final By usernameInput= By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");


    public LoginPage(WebDriver driver){
        super(driver);
    }


    public LoginPage login(String userName, String password){
        typeElement(usernameInput, userName);
        typeElement(passwordInput, password);
        clickElement(loginButton);
        return this;
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }


    public HomePage returnHome(){
        return new HomePage(driver);
    }

    public HomePage successfulLogin(){
        JsonNode validUser = testDataFile.path("login").path("validUser");
        String userName = validUser.path("username").asText();
        String password = validUser.path("password").asText();
        boolean isLogin = login(userName, password)
                .returnHome().isLoginSuccessful();
        softly().assertTrue(isLogin, "Not logged in!" );

        return new HomePage(driver);
    }



}
