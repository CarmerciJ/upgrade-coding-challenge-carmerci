package com.upgrade.tests;

import com.github.javafaker.Faker;
import com.upgrade.pages.LandingPage;
import com.upgrade.pages.SignInPage;
import com.upgrade.pojos.Borrower;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Log4j
public class LoanOffersUITest extends AbstractTest {
    private static final String url = "https://www.credify.tech";

    /*
        Please refer README.md for more details on
        Case # 1 : Validate offers after re-login
    */

    @Test
    public void validateOffersTest() {
        Borrower borrower = getRandomTestBorrower();
        LandingPage landingPage = new LandingPage(getDriver());

        //Capture offer details in the Offers page
        landingPage
                .gotoLandingPage(url)
                .enterLoanDetails(borrower)
                .enterContactDetails(borrower)
                .enterIncomeDetails(borrower)
                .enterLoginDetails(borrower)
                .clickSignOut();


        //Validate the offer details after login
        SignInPage signInPage = new SignInPage(getDriver());
        signInPage
                .gotoSignInPage(url)
                .signIn(borrower)
                .clickSignOut();
    }

    /*
        Please refer README.md for more details on
        Case # 2  : Loan rejected for low annual income
    */

    @Test
    public void validateDeclineLoanTest() {
        // Implement Case # 2
    }

    private Borrower getRandomTestBorrower() {
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
        borrower.setYearlyIncome(generateRandomNumberFromRange(150000, 200000));
        borrower.setAdditionalIncome(generateRandomNumberFromRange(1000, 10000));
        borrower.setDesiredLoanAmount(generateRandomNumberFromRange(5000, 10000));
        return borrower;
    }

    private BigDecimal generateRandomNumberFromRange(int min, int max) {
        return BigDecimal.valueOf(Math.random() * (max - min + 1) + min).setScale(0, RoundingMode.DOWN);
    }

}
