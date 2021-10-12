package com.upgrade.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;

@Log4j
public class ApiRequest {

    private String requestUrl;
    private Response response;
    private ContentType contentType;
    private Map<String, String> headers = new HashMap<>();
    private RequestSpecification requestSpecification = RestAssured.given();

    public ApiRequest post(Object body, Integer responseCode) {
        log.info("Calling POST: " + requestUrl);

        response = this.requestSpecification
                .contentType(contentType)
                .headers(headers)
                .body(body)
                .log()
                .body(true)
                .log()
                .headers()
                .when()
                .post(requestUrl);

        try {
            response
                    .then()
                    .statusCode(responseCode);
        } catch (AssertionError e) {
            throw new RuntimeException("Expected response code did not match for a Post request to: " + requestUrl +
                    "\n with status : \n" + response.statusLine(), e);
        }

        response
                .then()
                .log()
                .body(true);

        return this;
    }

    public ApiRequest setRequestUrl(String url) {
        this.requestUrl = url;
        return this;
    }

    public ApiRequest setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public ApiRequest addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }
}
