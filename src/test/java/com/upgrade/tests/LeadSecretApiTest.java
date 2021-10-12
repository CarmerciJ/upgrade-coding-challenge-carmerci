package com.upgrade.tests;

import com.upgrade.pojos.lead.LeadSecretRequest;
import com.upgrade.pojos.lead.LeadSecretResponse;
import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;

import java.util.UUID;


@Log4j
public class LeadSecretApiTest extends AbstractTest {

    private UUID loanAppUuid = UUID.fromString("b8096ec7-2150-405f-84f5-ae99864b3e96");
    private String url = "https://credapi.credify.tech/api/brfunnelorch/";

    /*
        Please refer README.md for more details on APT Test
    */
    @Test
    public void leadSecretTest() {
        LeadSecretRequest leadSecretRequest = LeadSecretRequest.builder()
                .loanAppUuid(loanAppUuid)
                .skipSideEffects(true)
                .build();

        apiRequest()
                .addHeader("x-cf-corr-id", UUID.randomUUID().toString())
                .addHeader("x-cf-source-id", "coding-challenge")
                .setContentType(ContentType.JSON)
                .setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret"))
                .post(leadSecretRequest, 200)
                .getResponse()
                .as(LeadSecretResponse.class);

    }

}
