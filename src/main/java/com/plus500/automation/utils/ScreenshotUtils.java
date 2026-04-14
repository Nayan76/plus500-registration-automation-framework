package com.plus500.automation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {

    public static void capture(WebDriver driver, String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Use "target/screenshots" - this is inside your project and usually has write permissions
            String dest = System.getProperty("user.dir") + "/target/screenshots/" + fileName + ".png";
            File destination = new File(dest);

            // ✅ Create parent folders if they don't exist
            if (!destination.getParentFile().exists()) {
                destination.getParentFile().mkdirs();
            }

            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            System.out.println("⚠️ Could not save screenshot: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Screenshot failed: " + e.getMessage());
        }
    }
}