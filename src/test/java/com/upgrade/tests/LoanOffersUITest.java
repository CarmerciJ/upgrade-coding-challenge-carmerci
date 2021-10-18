package com.upgrade.tests;

import com.github.javafaker.Faker;
import com.upgrade.pages.*;
import com.upgrade.pojos.Borrower;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Log4j
public class LoanOffersUITest extends AbstractTest {
    private static final String mainUrl = "https://www.credify.tech/phone/nonDMFunnel";
    private static final String loginUrl = "https://www.credify.tech/portal/login";

    /*
        Please refer README.md for more details on
        Case # 1 : Validate offers after re-login
    */

    @Test
    public void validateOffersTest() {
        Borrower testBorrower = getRandomTestBorrower(5000,10000,150000,200000,5000,10000);
        LandingPage landingPage = new LandingPage(getDriver());

        //Capture offer details in the Offers page
        landingPage
                .gotoLandingPage(mainUrl) // Navigate to https://www.credify.tech/phone/nonDMFunnel
                .enterLoanDetails(testBorrower) // Enter loan amount and purpose
                .enterContactDetails(testBorrower) //Enter Contact Details
                .enterIncomeDetails(testBorrower) //Enter Income Details
                .enterLoginDetails(testBorrower, new LoginInfoPage(getDriver()));//Create User

        SelectOfferPage offerPage = new SelectOfferPage(getDriver());
        testBorrower = offerPage.saveLoanAmountDetails(testBorrower);//save term details to user to validate after signing out and in again
        FunnelBasePage funnelBasePage =  new FunnelBasePage(getDriver());
        funnelBasePage.clickSignOut();//Sign Out


        //Above is missing capturing the Loan Amount Monthly Payment, Term, Interest Rate and APR from the default offer (first one) on top of the page.

        //Validate the offer details after login
        SignInPage signInPage = new SignInPage(getDriver());
        signInPage
                .gotoSignInPage(loginUrl)
                .signIn(testBorrower)
                .validateURLAndLoanOfferDetailsAfterSignIn(testBorrower)
                .clickSignOut();

    }

    /*
        Please refer README.md for more details on
        Case # 2  : Loan rejected for low annual income
    */

    //Verify that your loan application is rejected when annual income specified is lower than the requested loan amount
    @Test
    public void validateDeclineLoanTest() {
        // Implement Case # 2
        Borrower testBorrower2 = getRandomTestBorrower(5000,10000,100,1000,100,500);
        LandingPage landingPage = new LandingPage(getDriver());


        landingPage.gotoLandingPage(mainUrl)//Navigate to the initial page
                .enterLoanDetails(testBorrower2)//Enter Loan Amount and picklist value
                .enterContactDetails(testBorrower2)  //Enter Contact Details
                .enterIncomeDetails(testBorrower2) //Enter Income Details
                .enterLoginDetails(testBorrower2, new RejectedLoanPage(getDriver()))//Enter new User info
                .validateLoadWasRejected()//Validate the loan was rejected
                .validateRejectedLoanReasons();//Click on checkbox and Validate current page url and Validate link is on the page

    }

    //Test Data Creation
    private Borrower getRandomTestBorrower(int desiredLoanAmountRange1,int desiredLoanAmountRange2,
                                           int yearlyIncomeRange1, int yearlyIncomeRange2,
                                           int additionalIncomeRange1,int additionalIncomeRange2) {
        Borrower borrower = new Borrower();
        Faker faker = new Faker(new Locale("en-US"));

        borrower.setFirstName(faker.name().firstName());
        borrower.setLastName(faker.name().lastName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        borrower.setDob(simpleDateFormat.format(faker.date().birthday()));
        borrower.setCity(faker.address().city());
        borrower.setEmail(String.format("coding.%s@upgrade-challenge.com", generateRandomNumberFromRange(15000000, 20000000)));
        borrower.setPassword("System@987");
        borrower.setZipCode(faker.address().zipCode());
        borrower.setStreet(faker.address().streetAddress());
        borrower.setState("CA");
        borrower.setLoanPurpose("Home Improvement");
        borrower.setYearlyIncome(generateRandomNumberFromRange(yearlyIncomeRange1, yearlyIncomeRange2));
        borrower.setAdditionalIncome(generateRandomNumberFromRange(additionalIncomeRange1, additionalIncomeRange2));
        borrower.setDesiredLoanAmount(generateRandomNumberFromRange(desiredLoanAmountRange1, desiredLoanAmountRange2));
        return borrower;
    }

    private BigDecimal generateRandomNumberFromRange(int min, int max) {
        return BigDecimal.valueOf(Math.random() * (max - min + 1) + min).setScale(0, RoundingMode.DOWN);
    }

}
