package com.plus500.automation.tests;

import com.plus500.automation.base.BaseTest;
import com.plus500.automation.pages.*;
import com.plus500.automation.utils.*;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class RegistrationFlowTest extends BaseTest {

    @Test
    public void completeRegistrationFlow() {

        HomePage home = new HomePage(driver);
        RegistrationPage reg = new RegistrationPage(driver);
        CompletionPage comp = new CompletionPage(driver);

        // Step 1 → Navigate first
        home.acceptCookies();
        home.clickRegistration();
        TabUtils.switchToNewTab(driver);

        WaitUtils.waitForVisible(driver,
                driver.findElement(By.id("Content_txtEmail")));

        // Step 2 → Fill Form
        ScreenshotUtils.capture(driver, "BeforeFillingForm");

        reg.fillForm();

        ScreenshotUtils.capture(driver, "FormFilled");

        reg.selectCountry();
        reg.selectNonPro();
        reg.handleAgreements();

        reg.clickRegister();

        // Step 3 → After Register
        WaitUtils.getWait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        ScreenshotUtils.capture(driver, "AfterRegister");

        logPage("After Register");

        // Step 4 → Completion Page
        comp.clickHere();

        WaitUtils.getWait(driver).until(d ->
                d.getCurrentUrl().contains("login.aspx")
        );

        ScreenshotUtils.capture(driver, "LoginPage");

        logPage("Login Page");

        // Step 5 → iframe handling
        WaitUtils.getWait(driver).until(d ->
                d.findElements(By.tagName("iframe")).size() > 0
        );

        driver.switchTo().frame(0);

        WebElement adminLink = driver.findElement(By.id("lnkAdminPortal"));
        String url = adminLink.getAttribute("href");

        System.out.println("Admin URL → " + url);

        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0], '_blank');", url);

        driver.switchTo().defaultContent();

        TabUtils.switchToNewTab(driver);

        WaitUtils.getWait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        ScreenshotUtils.capture(driver, "AdminPortal");

        logPage("Final Admin Portal Page");

        ExcelUtils.writeData("Registration Flow", driver.getCurrentUrl(), "PASS");
    }

    private void logPage(String step) {
        System.out.println("📍 " + step + " URL → " + driver.getCurrentUrl());
    }
}