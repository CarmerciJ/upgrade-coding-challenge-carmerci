package com.upgrade.pages;

import com.upgrade.pojos.Borrower;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class LoginInfoPage extends BasePage {

    @FindBy(name = "username")
    private WebElement email;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(linkText = "Terms of Use")
    private WebElement termsOfUse;

    @FindBy(linkText = "ESIGN Act Consent")
    private WebElement eSIGNActConsent;

    @FindBy(linkText = "Credit Profile Authorization")
    private WebElement creditProfileAuthorization;

    @FindBy(linkText = "Privacy Policy")
    private WebElement privacyPolicy;

    @FindBy(css = "[data-auto='submitPersonalInfo']")
    private WebElement checkYourRateBtn;

    @FindBy(name = "agreements")
    private WebElement agreements;


    public LoginInfoPage(WebDriver driver) {
        super(driver);
        waitForWebElements(Arrays.asList(email));
    }

    // Note : Use java generics to return a different page
    //updated method to include generics so that this method is
    // accessible from multiple pages but returns the instance of same page object on which it is called
    public <T extends BasePage> T enterLoginDetails(Borrower randomPerson, T page) {
        type(email, randomPerson.getEmail());
        pause(4);
        type(password, randomPerson.getPassword());
        pause(4);
        selectTermsOfUse();
        pause(4);
        click(checkYourRateBtn);
        waitForPage();
        return page;

    }

    public LoginInfoPage selectTermsOfUse() {
        javaScriptClick(agreements);
        return this;
    }
}
