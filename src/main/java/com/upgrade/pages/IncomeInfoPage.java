package com.upgrade.pages;

import com.upgrade.pojos.Borrower;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;
import java.util.Arrays;

public class IncomeInfoPage extends BasePage {

    @FindBy(name = "borrowerIncome")
    private WebElement yearlyIncome;

    @FindBy(name = "borrowerAdditionalIncome")
    private WebElement additionalIncome;

    @FindBy(css = "[data-auto='continuePersonalInfo']")
    private WebElement continueIncomeInfo;

    public IncomeInfoPage(WebDriver driver) {
        super(driver);
        waitForWebElements(Arrays.asList(yearlyIncome, additionalIncome));
    }

    public LoginInfoPage enterIncomeDetails(Borrower randomPerson) {
        type(yearlyIncome, randomPerson.getYearlyIncome().toString());
        javaScriptFocusOnElement(additionalIncome);
        confirmIncomeModal(randomPerson);
        type(additionalIncome, randomPerson.getAdditionalIncome().toString());
        blur();
        click(continueIncomeInfo);
        return new LoginInfoPage(driver);
    }

    public IncomeInfoPage confirmIncomeModal(Borrower randomPerson) {
        BigDecimal yearlyIncome = randomPerson.getYearlyIncome();
        if (yearlyIncome.compareTo(BigDecimal.valueOf(10000)) == -1
                || yearlyIncome.compareTo(BigDecimal.valueOf(300000)) == 1) {
            By by = By.cssSelector("div.ReactModalPortal button[data-auto='confirmIncome']");
            waitForElementToBeDisplayed(by, 10, 1);
            click(By.cssSelector("div.ReactModalPortal button[data-auto='confirmIncome']"));
        }
        return this;
    }
}
