package com.plus500.automation.pages;

import com.plus500.automation.utils.ExcelUtils;
import com.plus500.automation.utils.ScreenshotUtils;
import com.plus500.automation.utils.TabUtils;
import com.plus500.automation.utils.WaitUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class RegistrationPage {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="Content_txtEmail")
    WebElement email;

    @FindBy(id="Content_txtFirstname")
    WebElement firstName;

    @FindBy(id="Content_txtLastname")
    WebElement lastName;

    @FindBy(id="Content_txtPhonenumber")
    WebElement phone;

    // ✅ Country Dropdown
    @FindBy(id="Content_cboCountry")
    WebElement countryDropdown;

    // ✅ NEW REQUIRED FIELDS
    @FindBy(id="Content_txtState")
    WebElement state;

    @FindBy(id="Content_txtStreet")
    WebElement street;

    @FindBy(id="Content_txtCity")
    WebElement city;

    @FindBy(id="Content_txtCounty")
    WebElement county;

    @FindBy(id="Content_txtPostCode")
    WebElement zipCode;

    @FindBy(id="Content_rdoTypeNonPro")
    WebElement nonPro;

    @FindBy(xpath="//div[@class='agreementcheckboxes']//a")
    List<WebElement> links;

    @FindBy(xpath="//div[@class='agreementcheckboxes']//input[@type='checkbox']")
    List<WebElement> checkboxes;

    @FindBy(id="Content_cmdRegister")
    WebElement registerBtn;

    // ✅ Fill Form (UPDATED)
    public void fillForm() {

        email.sendKeys("test@mail.com");
        firstName.sendKeys("Test");
        lastName.sendKeys("User");
        phone.sendKeys("9999999999");

        // 🔥 REQUIRED FIELDS
        state.sendKeys("Tamil Nadu");
        street.sendKeys("Anna Street");
        city.sendKeys("Chennai");
        county.sendKeys("Chennai");
        zipCode.sendKeys("600001");
    }

    // ✅ Select Country (Robust Version)
    public void selectCountry() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement dropdown = driver.findElement(By.id("Content_cboCountry"));

        // ✅ Step 1: Set value
        js.executeScript("arguments[0].value='IN';", dropdown);

        // ✅ Step 2: Trigger ASP.NET postback manually
        js.executeScript("__doPostBack('ctl00$Content$cboCountry','')");

        // ✅ Step 3: WAIT for page reload (VERY IMPORTANT)
        WaitUtils.getWait(driver).until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // ✅ Step 4: VERIFY VALUE
        String selectedValue = (String) js.executeScript(
                "return document.getElementById('Content_cboCountry').value;"
        );

        System.out.println("Selected country value: " + selectedValue);
    }

    public void selectNonPro() {
        nonPro.click();
    }

    // ✅ Agreement Handling
    public void handleAgreements() {

        for (int i = 0; i < links.size(); i++) {

            String parent = driver.getWindowHandle();

            WaitUtils.waitForClickable(driver, links.get(i));
            links.get(i).click();

            TabUtils.switchToNewTab(driver);

            String url = driver.getCurrentUrl();
            ExcelUtils.writeData("Agreement " + i, url, "PASS");

            ScreenshotUtils.capture(driver, "Agreement_" + i);

            driver.close();
            driver.switchTo().window(parent);

            WaitUtils.waitForClickable(driver, checkboxes.get(i));
            checkboxes.get(i).click();
        }
    }

    public void clickRegister() {
        WaitUtils.waitForClickable(driver, registerBtn);
        registerBtn.click();
    }
}