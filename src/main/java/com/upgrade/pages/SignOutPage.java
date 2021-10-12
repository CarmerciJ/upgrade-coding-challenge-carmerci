package com.upgrade.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

public class SignOutPage extends BasePage {

    @FindBy(css = "[data-auto='logoutMessage']")
    private WebElement signOutText;

    public SignOutPage(WebDriver driver) {
        super(driver);
        waitForWebElements(Arrays.asList(signOutText));
    }
}
