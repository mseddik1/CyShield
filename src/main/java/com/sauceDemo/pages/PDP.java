package com.sauceDemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PDP extends BasePage{

    private final By addToCartButton = By.id("add-to-cart");
    private final By productName = By.xpath("//div[@data-test='inventory-item-name']");

    public PDP(WebDriver driver){
        super(driver);
    }

    @Step("Get product name from Product Details Page")
    public String getProductName(){
        return driver.findElement(productName).getText();
    }

    @Step("Add product to cart from Product Details Page")
    public PDP clickAddToCart(){
        clickElement(addToCartButton);
        return this;
    }
}
