package com.upgrade.tests;

import com.upgrade.pojos.lead.LeadSecretRequest;
import com.upgrade.pojos.lead.LeadSecretResponse;
import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;


@Log4j
public class LeadSecretApiTest extends AbstractTest {

    private UUID loanAppUuid = UUID.fromString("b8096ec7-2150-405f-84f5-ae99864b3e96");
    private UUID incorrectLoanAppUuID = UUID.fromString("b8096ec8-2450-405d-84f5-kd99864b3e92");
    private String url = "https://credapi.credify.tech/api/brfunnelorch/";

    /*
        Please refer README.md for more details on APT Test
        ##### Validations

        1. Validate that for correct loanAppUuid provided in the payload below, the API response code is a 200 (OK)
        2. For the above use case, validate the response and make sure productType attribute has value PERSONAL_LOAN.
        3. Validate firstName as Benjamin in borrowerResumptionInfo.
        4. Validate that in the initial POST request, if a different loanAppUuid is provided (that doesn't exist in our system) - the API response is a 404 (NOT_FOUND)

    */
    @Test
    public void leadSecretTestValidate200code() {

        apiRequest()
                .addHeader("x-cf-corr-id", UUID.randomUUID().toString())
                .addHeader("x-cf-source-id", "coding-challenge")
                .setContentType(ContentType.JSON)
                .setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret"))
                .post(testDataCreator(loanAppUuid), 200)
                .getResponse()
                .as(LeadSecretResponse.class);

        log.info("Starting Validations......");
        Assert.assertEquals(apiRequest().getResponse().getStatusCode(), 200,"The status code was NOT matched with 200");//Validate that for correct loanAppUuid provided in the payload, the API response code is a 200 (OK)
        log.info("Assertion for status code of 200 passed");
        Assert.assertEquals(apiRequest().getResponse().then().extract().path("loanAppResumptionInfo.productType"),"PERSONAL_LOAN", "productType did not equal PERSONAL_LOAN"); //make sure productType attribute has value PERSONAL_LOAN
        log.info("Assertion that loanAppResumptionInfo.productType equaled PERSONAL_LOAN passed");
        Assert.assertEquals(apiRequest().getResponse().then().extract().path("loanAppResumptionInfo.borrowerResumptionInfo.firstName"),"Benjamin", "firstName in borrowerResumptionInfo did not equal Benjamin");// Validate firstName as Benjamin in borrowerResumptionInfo
        log.info("Assertion that loanAppResumptionInfo.borrowerResumptionInfo.firstName equaled Benjamin passed");
        log.info("Finished Validtions....");
    }

    @Test
    public void validateResponseCodeForInvalidLoanAppID(){
        apiRequest()
                .addHeader("x-cf-corr-id", UUID.randomUUID().toString())
                .addHeader("x-cf-source-id", "coding-challenge")
                .setContentType(ContentType.JSON)
                .setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret"))
                .post(testDataCreator(incorrectLoanAppUuID), 404)
                .getResponse()
                .as(LeadSecretResponse.class);

        log.info("Starting Validations For Negative Test Case......");

        Assert.assertEquals(apiRequest().getResponse().getStatusCode(),404,"The response error code matched 400 for error Not-found");
        log.info("Assertion for 404 status code passed");

        Assert.assertEquals(apiRequest().getResponse().then().extract().path("message"),"Loan application does not exist.", "The message for this not found request was incorrect");
        log.info("Assertion for 404 message validation passed");

        Assert.assertEquals(apiRequest().getResponse().then().extract().path("httpStatus"),"NOT_FOUND", "The httpstatus for this not found request was incorrect");
        log.info("Assertion for 404 http status validation passed");

        log.info("Finished negative test case validations.......");
    }


    //Test Data creator for the headers
    public LeadSecretRequest testDataCreator(UUID loanAppUuid) {
        LeadSecretRequest leadSecretRequest = LeadSecretRequest.builder()
                .loanAppUuid(loanAppUuid)
                .skipSideEffects(true)
                .build();
       return leadSecretRequest;
    }
}
