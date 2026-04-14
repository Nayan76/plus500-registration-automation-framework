package com.plus500.automation.utils;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class WaitUtils {

    public static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver,
                Duration.ofSeconds(Integer.parseInt(ConfigReader.get("explicitWait"))));
    }

    public static void waitForClickable(WebDriver driver, WebElement element) {
        getWait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForVisible(WebDriver driver, WebElement element) {
        getWait(driver).until(ExpectedConditions.visibilityOf(element));
    }
}