package com.upgrade.pages;

import com.upgrade.pojos.Borrower;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

public class ContactInfoPage extends BasePage {

    @FindBy(name = "borrowerFirstName")
    private WebElement firstName;

    @FindBy(name = "borrowerLastName")
    private WebElement lastName;

    @FindBy(css = "div.geosuggest input")
    private WebElement street;

    @FindBy(name = "borrowerCity")
    private WebElement city;

    @FindBy(name = "borrowerState")
    private WebElement state;

    @FindBy(name = "borrowerZipCode")
    private WebElement zipCode;

    @FindBy(name = "borrowerDateOfBirth")
    private WebElement dateOfBirth;

    @FindBy(xpath = "//h3[contains(.,'Need help?')]")
    private WebElement needHelp;

    @FindBy(css = "[data-auto='continuePersonalInfo']")
    private WebElement continueContactInfo;

    public ContactInfoPage(WebDriver driver) {
        super(driver);
        waitForWebElements(Arrays.asList(firstName, lastName));
    }

    public IncomeInfoPage enterContactDetails(Borrower borrower) {
        type(firstName, borrower.getFirstName());
        type(lastName, borrower.getLastName());
        type(street, borrower.getStreet());
        pause(1);
        click(firstName);
        type(city, borrower.getCity());
        type(state, borrower.getState());
        type(zipCode, borrower.getZipCode());
        type(dateOfBirth, convertDOBFormat(borrower.getDob()));
        click(continueContactInfo);
        return new IncomeInfoPage(driver);
    }

    private String convertDOBFormat(String dateOfBirth) {
        DateTime dt = new DateTime(dateOfBirth, DateTimeZone.UTC);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMddyyyy");
        return fmt.print(dt);
    }
}
