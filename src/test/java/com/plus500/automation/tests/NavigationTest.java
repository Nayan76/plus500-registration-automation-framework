package com.plus500.automation.tests;

import com.plus500.automation.base.BaseTest;
import com.plus500.automation.pages.HomePage;
import com.plus500.automation.utils.*;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    @Test
    public void verifyNavigationToRegistration() {

        HomePage home = new HomePage(driver);

        // Wait for page load
        WaitUtils.getWait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        ScreenshotUtils.capture(driver, "HomePage");

        home.acceptCookies();

        WaitUtils.waitForVisible(driver,
                driver.findElement(By.tagName("body")));

        ScreenshotUtils.capture(driver, "HomePage_AfterCookies");

        // Navigate to Registration
        home.clickRegistration();
        TabUtils.switchToNewTab(driver);

        WaitUtils.waitForVisible(driver,
                driver.findElement(By.id("Content_txtEmail")));

        ScreenshotUtils.capture(driver, "RegistrationPage");

        logPage("Navigation to Registration Page");

        ExcelUtils.writeData("Navigation Test", driver.getCurrentUrl(), "PASS");
    }

    private void logPage(String step) {
        System.out.println("📍 " + step + " URL → " + driver.getCurrentUrl());
    }
}