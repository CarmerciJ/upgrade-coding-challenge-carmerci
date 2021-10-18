package com.upgrade.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
public class RejectedLoanPage extends BasePage{

    @FindBy(xpath = "//h2[contains(text(),\"We can't find you a loan offer yet, but you still \")]")
    private WebElement loanRejection;

    @FindBy(css="[data-auto='adverse-learn-more-link']")
    private WebElement notApprovedLoanReasons;

    @FindBy(xpath=".//td[text()='Adverse Action Notice.pdf']//following-sibling::td//a[@data-auto='downloadDocument']")
    private WebElement pdfLink;

    public RejectedLoanPage (WebDriver driver) {
        super(driver);

    }
    public RejectedLoanPage(){}

    /**
     * This page checks to make sure the loan rejection exists on the page and that
     * the current url contains /funnel/adverse-page
     * @return RejectedLoanPage
     */
    public RejectedLoanPage validateLoadWasRejected(){
        Assert.assertTrue(loanRejection.isDisplayed());
        log.info("current url: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("/funnel/adverse-page"));
        return this;
    }

    /**
     * This method clicks on the "If you would like to learn more about why you were not approved, please click here" link
     * and then verifies that the current page url has the following format /portal/product/" + loanId +"/documents.
     * Finally, it verifies that the link to Adverse Action Notice.pdf exists on the page
     * --Note: for future extensibility for finding pdf link, can make above webelement into string, pass pdf name as method parameter
     * use replaceAll to replace variable in xpath string with variable--
     * @return instance of class
     */
    public RejectedLoanPage validateRejectedLoanReasons(){
        click(notApprovedLoanReasons);
        waitForPage();
        Pattern pattern = Pattern.compile("/(\\d{9})/");
        Matcher matcher = pattern.matcher(driver.getCurrentUrl());
        String loanId = "";
        if (matcher.find()) {
            log.info("the loan id in the url is: " + matcher.group(1));
            loanId = matcher.group(1);
        }

        Assert.assertTrue(driver.getCurrentUrl().contains("/portal/product/" + loanId +"/documents"),"The url does not contain /portal/product/" + loanId + "/documents");
        Assert.assertTrue(pdfLink.isDisplayed(),"Could not find the link to the pdf on the page");
        return this;
    }
}
