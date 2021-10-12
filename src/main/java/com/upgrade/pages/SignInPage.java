package com.upgrade.pages;

import com.upgrade.pojos.Borrower;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

@Log4j
public class SignInPage extends BasePage {

    @FindBy(css="[data-auto='username']")
    private WebElement username;

    @FindBy(css="[data-auto='password']")
    private WebElement password;

    @FindBy(css="[data-auto='login']")
    private WebElement submitBtn;

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public SelectOfferPage signIn(Borrower borrower) {
        type(username, borrower.getEmail());
        type(password, borrower.getPassword());
        click(submitBtn);
        waitForPage();
        return new SelectOfferPage(driver);
    }

    public SignInPage gotoSignInPage(String url) {
        String server = String.format("%s/portal/login", url);
        log.info("Navigate to - " + server);
        driver.get(server);
        waitForWebElements(Arrays.asList(username));
        return this;
    }
}