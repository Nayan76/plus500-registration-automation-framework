package com.plus500.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "lnkAdminPortal")
    WebElement adminPortal;

    public String getAdminPortalUrl() {
        return adminPortal.getAttribute("href");
    }
}