package com.plus500.automation.utils;

import org.openqa.selenium.WebDriver;
import java.util.Set;

public class TabUtils {

    public static void switchToNewTab(WebDriver driver) {

        String current = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {
            if (!handle.equals(current)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }
}