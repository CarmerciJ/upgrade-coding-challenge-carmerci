package com.upgrade.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

@Log4j
public class SelectOfferPage extends FunnelBasePage {

    @FindBy(css = "[data-auto='getDefaultLoan']")
    private WebElement continueBtn;

    public SelectOfferPage(WebDriver driver) {
        super(driver);
        waitForWebElements(Arrays.asList(continueBtn));
    }


}
