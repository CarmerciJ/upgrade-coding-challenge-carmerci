package com.upgrade.pages;

import com.google.common.hash.Funnel;
import com.upgrade.pojos.Borrower;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Log4j
public class SelectOfferPage extends FunnelBasePage {

    @FindBy(css = "[data-auto='getDefaultLoan']")
    private WebElement continueBtn;

    //Loan Offer data to be saved and validated after sign in
    @FindBy(css = "[data-auto='userLoanAmount']")
    private WebElement loanAmount;

    @FindBy(xpath = "//h3//following-sibling::div[@data-auto='defaultMonthlyPayment']")
    private WebElement monthlyPayment;

    @FindBy(xpath = ".//li[@data-auto='defaultLoanTerm']//following-sibling::div[contains(@class,'number')]")
    private WebElement termLength;

    @FindBy(xpath=".//li[@data-auto='defaultLoanInterestRate']//div[contains(@class,'number')]")
    private WebElement interestRate;

    @FindBy(xpath=".//div[@data-auto='defaultAPR']")
    private WebElement apr;


    //Setting these as variables so can be reused later on in the process
    private BigDecimal actualLoanAmount;
    private double actualMonthlyPayment;
    private String actualTermLength;
    private double actualInterestRate;
    private double actualAPR;

    public SelectOfferPage(WebDriver driver) {
        super(driver);
        if(continueBtn.isDisplayed()){
            waitForWebElements(Arrays.asList(continueBtn));
        }

    }
    public SelectOfferPage(){}

    /**
     * This method grabs the relevant  approved Loan Amount, Monthly Payment, Term, Interest Rate and APR from the default offer (first one) on top of the page.
     * It then saves it as part of the Borrower object in order to verify anywhere else.
     * @return the newly updated borrower/user
     */
    public Borrower saveLoanAmountDetails(Borrower borrower) {
        pause(2);
        getLoanValuesWhenNavigatingToPage();
        borrower.setApprovedLoanAmount(actualLoanAmount);
        log.info("Saving the loan amount internally to: " + actualLoanAmount);

        borrower.setMonthlyPayment(actualMonthlyPayment);
        log.info("Saving the monthly payment internally to: " + actualMonthlyPayment);

        borrower.setTermLength(actualTermLength);
        log.info("Saving the term length internally to: "+ actualTermLength);

        borrower.setInterestRate(actualInterestRate);
        log.info("Saving the interest rate internally to: " + actualInterestRate);

        borrower.setApr(actualAPR);
        log.info("Saving the APR internally to: "+ actualAPR);
        return borrower;
    }

    /**
     * This method is used to validate the values that were saved when the user first looked up loan information to what
     * they see when they sign into their accounts again
     * @param borrower the current user user the platform
     * @return
     */

    public FunnelBasePage validateURLAndLoanOfferDetailsAfterSignIn(Borrower borrower){
        Assert.assertTrue(driver.getCurrentUrl().contains("/offer-page"));
        getLoanValuesWhenNavigatingToPage();
        Assert.assertEquals(borrower.getApprovedLoanAmount(),actualLoanAmount);
        Assert.assertEquals(borrower.getMonthlyPayment(),actualMonthlyPayment);
        Assert.assertEquals(borrower.getTermLength(),actualTermLength);
        Assert.assertEquals(borrower.getInterestRate(),actualInterestRate);
        Assert.assertEquals(borrower.getApr(),actualAPR);
        return this;
    }

    /**
     * This is a helper method to grab the loan offers off the page everytime the user navigates to it
     */
    public void getLoanValuesWhenNavigatingToPage(){
        actualLoanAmount = new BigDecimal(removeSpecialCharactersFromText(loanAmount.getText()));
        actualMonthlyPayment = Double.parseDouble(removeSpecialCharactersFromText(monthlyPayment.getText()));
        actualTermLength = termLength.getText();
        actualInterestRate = Double.parseDouble(removeSpecialCharactersFromText(interestRate.getText()));
        actualAPR = Double.parseDouble(removeSpecialCharactersFromText(apr.getText()));
    }




}
