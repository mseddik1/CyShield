package com.sauceDemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.lang.model.element.Element;
import java.util.List;

public class HomePage extends BasePage {
    private final By inventoryItems = By.xpath("//div[@class='inventory_item']//img");
    private final By inventoryItemNames = By.xpath("//div[@data-test='inventory-item-name']");
    private final By homepageTitle = By.xpath("//span[@data-test='title']");
    private final By addToCartButton = By.xpath("//button[text()='Add to cart']"); //this returns a list
    private final By removeButton = By.xpath("//button[text()='Remove']"); //this returns a list




    public HomePage(WebDriver driver){
        super(driver);
    }


    @Step("Verify whether user is on the inventory page")
    public boolean isLoginSuccessful(){

        return driver.getCurrentUrl().contains("inventory.html");

    }

    public HomePage waitUntilLoaded(){
        myWait().until(ExpectedConditions
                .visibilityOfAllElements(driver.findElements(inventoryItems)));
        return this;
    }



    @Step("Get the name of the first inventory item")
    public String getFirstItemName(){
        return driver.findElements(inventoryItemNames).getFirst().getText();
    }

    @Step("Open the first inventory item")
    public PDP openFirstItem(){
        myWait().until(ExpectedConditions.elementToBeClickable(driver.findElements(inventoryItems).getFirst())).click();
//        driver.findElements(inventoryItems).getFirst().click();
        return new PDP(driver);
    }
    @Step("Add inventory item at index {idx} to cart")
    public HomePage clickAddToCart(int idx){
        List<WebElement> buttons = driver.findElements(addToCartButton);
        myWait().until(ExpectedConditions.elementToBeClickable(buttons.get(idx))).click();
        return this;
    }


}
