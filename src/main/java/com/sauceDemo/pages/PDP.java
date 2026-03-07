package com.sauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PDP extends BasePage{

    private final By addToCartButton = By.id("add-to-cart");
    private final By productName = By.xpath("//div[@data-test='inventory-item-name']");

    public PDP(WebDriver driver){
        super(driver);
    }


    public String getProductName(){
        return driver.findElement(productName).getText();
    }

    public PDP clickAddToCart(){
        clickElement(addToCartButton);
        return this;
    }
}
