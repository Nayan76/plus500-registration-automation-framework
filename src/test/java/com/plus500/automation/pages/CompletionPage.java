package com.plus500.automation.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import com.plus500.automation.utils.WaitUtils;

public class CompletionPage {

    WebDriver driver;

    public CompletionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText="here")
    WebElement hereLink;

    public void clickHere() {
        WaitUtils.waitForClickable(driver, hereLink);
        hereLink.click();
    }
}
