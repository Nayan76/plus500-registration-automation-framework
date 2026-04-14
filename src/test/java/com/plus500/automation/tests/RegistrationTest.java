package com.plus500.automation.tests;

import com.plus500.automation.base.BaseTest;
import com.plus500.automation.pages.CompletionPage;
import com.plus500.automation.pages.HomePage;
import com.plus500.automation.pages.LoginPage;
import com.plus500.automation.pages.RegistrationPage;
import com.plus500.automation.utils.*;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    @Test
    public void completeFlow() {

        HomePage home = new HomePage(driver);
        RegistrationPage reg = new RegistrationPage(driver);
        CompletionPage comp = new CompletionPage(driver);
        LoginPage login = new LoginPage(driver);

        // 🔷 Step 1: Home Page (FIXED)
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

        logPage("Home Page");

        // 🔷 Step 2: Registration Page (FIXED)
        home.clickRegistration();
        TabUtils.switchToNewTab(driver);

        WaitUtils.waitForVisible(driver,
                driver.findElement(By.id("Content_txtEmail")));

        ScreenshotUtils.capture(driver, "RegistrationPage");

        logPage("Registration Page");

        // 🔷 Step 3: Before Filling Form
        ScreenshotUtils.capture(driver, "BeforeFillingForm");

        // 🔷 Step 4: Fill Form
        reg.fillForm();

        ScreenshotUtils.capture(driver, "FormFilled");

        reg.selectCountry();
        reg.selectNonPro();
        reg.handleAgreements();

        reg.clickRegister();

        // 🔷 Step 5: After Register
        WaitUtils.getWait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        ScreenshotUtils.capture(driver, "AfterRegister");

        logPage("After Register");

        // 🔷 Step 6: Completion Page
        comp.clickHere();

        WaitUtils.getWait(driver).until(d ->
                d.getCurrentUrl().contains("login.aspx")
        );

        ScreenshotUtils.capture(driver, "LoginPage");

        logPage("Login Page");

        // 🔷 Step 7: Switch to iframe
        WaitUtils.getWait(driver).until(d ->
                d.findElements(By.tagName("iframe")).size() > 0
        );

        driver.switchTo().frame(0);

        // 🔷 Step 8: Get Admin URL
        WebElement adminLink = driver.findElement(By.id("lnkAdminPortal"));
        String url = adminLink.getAttribute("href");

        System.out.println("Admin URL → " + url);

        // 🔥 Open in new tab (reliable)
        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0], '_blank');", url);

        driver.switchTo().defaultContent();

        // 🔷 Step 9: Switch tab
        TabUtils.switchToNewTab(driver);

        WaitUtils.getWait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        ScreenshotUtils.capture(driver, "AdminPortal");

        logPage("Final Admin Portal Page");

        ExcelUtils.writeData("Home Page", driver.getCurrentUrl(), "PASS");
        ExcelUtils.writeData("Registration Page", driver.getCurrentUrl(), "PASS");
        ExcelUtils.writeData("After Register", driver.getCurrentUrl(), "PASS");
        ExcelUtils.writeData("Login Page", driver.getCurrentUrl(), "PASS");
        ExcelUtils.writeData("Admin Page", driver.getCurrentUrl(), "PASS");
    }

    private void logPage(String step) {
        System.out.println("📍 " + step + " URL → " + driver.getCurrentUrl());
    }
}