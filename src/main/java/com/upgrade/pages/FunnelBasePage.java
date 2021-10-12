package com.upgrade.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class FunnelBasePage extends BasePage {

    @FindBy(linkText = "Sign Out")
    private WebElement signOut;

    @FindBy(css = "label.header-nav__toggle")
    private WebElement menu;

    public FunnelBasePage(WebDriver driver) {
        super(driver);
    }

    public SignOutPage clickSignOut() {
        click(menu);
        visibilityOfElementLocated(By.linkText("Sign Out"));
        click(signOut);
        textToBePresentInElement(By.tagName("body"), "You've been successfully logged out");
        return new SignOutPage(driver);
    }

}