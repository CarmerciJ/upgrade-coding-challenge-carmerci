package com.upgrade.tests;

import com.upgrade.api.ApiRequest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

public class AbstractTest {
    private WebDriver driver;
    private ApiRequest apiRequest;

    @BeforeTest
    public void beforeTest() {
        PropertyConfigurator.configure(AbstractTest.class.getClassLoader().getResourceAsStream("log4j.properties"));
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        return driver;
    }

    public ApiRequest apiRequest() {
        if (apiRequest == null) {
            apiRequest = new ApiRequest();
        }
        return apiRequest;
    }

}