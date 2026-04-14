package com.plus500.automation.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import com.plus500.automation.utils.WaitUtils;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[text()='Accept']")
    WebElement acceptBtn;

    @FindBy(xpath = "//span[text()='Simulator Registration']")
    WebElement registerLink;

    public void acceptCookies() {
        WaitUtils.waitForClickable(driver, acceptBtn);
        acceptBtn.click();
    }

    public void clickRegistration() {
        WaitUtils.waitForClickable(driver, registerLink);
        registerLink.click();
    }
}