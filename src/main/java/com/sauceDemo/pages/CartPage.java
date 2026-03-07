package com.sauceDemo.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    private final By cartHeader = By.xpath("//span[@data-test='title']");
    private final By itemNames = By.xpath("//div[@data-test='inventory-item-name']");
    private final By removeButton = By.xpath("//button[text()='Remove']"); //this returns a list
    private final By checkoutButton = By.id("checkout");
    public CartPage(WebDriver driver){
        super(driver);
        myWait().until(d -> {
            WebElement header = d.findElement(cartHeader);
            String text = header.getText();
            return text != null && !text.trim().isEmpty();
        });
    }


    public String getFirstItemName(){
        return driver.findElements(itemNames).getFirst().getText();
    }


    public CartPage removeFirstProduct(){
        myWait().until(ExpectedConditions.elementToBeClickable(driver.findElements(removeButton).getFirst())).click();
        return this;
    }


    public CheckoutPage clickCheckout(){
        clickElement(checkoutButton);
        return new CheckoutPage(driver);
    }




}
