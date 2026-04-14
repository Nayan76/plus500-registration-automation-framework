package com.plus500.automation.utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver driver;

    public static WebDriver initDriver() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Integer.parseInt(ConfigReader.get("implicitWait")))
        );

        driver.get(ConfigReader.get("url"));

        return driver;
    }
}