## Upgrade - Coding Challenge

### Summary
Upgrade is a fintech unicorn providing credit products (like personal loans) to individual customers. Once a borrower applies for a loan, they can manage their loan application by signing in to their Borrower Dashboard.

The subject of this challenge to enhance existing automation tests (provided), and add new tests to test some core functionality of the Upgrade loan's platform. The use cases are twofold (web and API).
Please follow instructions closely, and let your recruiter know in case you have any questions.

### Requirements:

#### For Web Automation:
Project has already the first test case automated, but it is not a complete test. 
The goal is to get you familiar with the framework, and you should be able to make any correction/enhancements you can think of on the first testcase 
(For example validations are missing from the test, consider design patterns, coding conventions,.... ). 
In this case you will be evaluated based on changes/improvements that you make on the code.

Second web test case is not automated, and you should write the code on your own using the framework. 
You will be evaluated based on the code changes (page objects, assertions, ...)

#### For Api automation:
Again you are given a test which needs improvements. 
You can improve the code as much as you think its necessary and reflects your java knowledge. 
(For example validations are missing from the test, consider design patterns, coding conventions,.... )

### Preconditions
  - Maven 3.6.0+
  - JDK 11
  - Latest Chrome browser installed
  - Lombok in IDE
      * IntelliJ IDEA 
        - [Lombok](https://projectlombok.org/setup/intellij)  
      * Eclipse 
        - [Lombok](https://projectlombok.org/setup/eclipse) 
        - [TestNG](https://marketplace.eclipse.org/content/testng-eclipse)   

### Installing dependencies with Maven
1. Navigate to the project folder:
```sh
cd path/to/coding-challenge
```
2. Install all the dependencies with the following command:
```sh
mvn install -DskipTests
```
The previous command will install all the necessary dependencies without running any tests. 
(If `-DskipTests` is removed from the previous command, the API and web tests will be executed).

#### More details on automation tasks

##### Web UI Testcase # 1
Using the UI, verify that after filling out the loan application form (with valid inputs), you are seeing loan offers after signing out and back in again.

1. Navigate to https://www.credify.tech/phone/nonDMFunnel
2. On this page:
    - Enter loan amount between 5000 and 10000, and select any loan purpose
    - Click "Check your Rate"
3. On the "Let's get started with some basic information" page, enter
    - First name: <distinct-random-name>
    - Last name: <distinct-random-name>
    - Home Address: <any valid, distinct US Address>
    - DOB: <any valid date after 01/01/1930 and before 01/01/2000>
    - Click on the "Continue" button
4. On the "How much money do you make in a year?" page, enter
    - Annual Income: Between 150000 and 200000
    - Additional Income : Between 5000 and 10000
    - Click on the "Continue" button
5. On the "Last step before you get your rate" page, enter
    - Email address format: coding.<random-number>@upgrade-challenge.com
    - Strong password: 8 characters long, contain at least one number, one uppercase letter, and one lower case letter.
    - Click the checkbox for Terms of Use
    - Click on the "Check Your Rate" button
6. From the `/offer-page`, capture the approved Loan Amount, Monthly Payment, Term, Interest Rate and APR from the default offer (first one) on top of the page.
    - Now click on "Sign Out" from the Menu option in the top right corner
7. Now navigate to https://www.credify.tech/portal/login
    - Enter the previously entered email and password
    - Click "Sign In to your account"
8. Validate that current page is `/offer-page`
    - Validate that for default offer (first one on the page) Loan Amount, Monthly Payment, Term, Interest Rate and APR matches with the info captured previously 

##### Web UI Testcase # 2
Verify that your loan application is rejected when annual income specified is lower than the requested loan amount

1. Navigate to https://www.credify.tech/phone/nonDMFunnel
2. On this page:
    - Enter loan amount between 5000 and 10000, and select any loan purpose
    - Click "Check your Rate"
3. On the "Let's get started with some basic information" page, enter
    - First name: <distinct-random-name>
    - Last name: <distinct-random-name>
    - Home Address: <any valid, distinct US Address>
    - DOB: <any valid date after 01/01/1930 and before 01/01/2000>
    - Click on the "Continue" button
4. On the "How much money do you make in a year?" page, enter
    - Annual Income: Between 100 and 1000 (click on any UI dialogs that may pop-up to confirm amount)
    - Additional Income : Between 100 and 500
    - Click on the "Continue" button
5. On the "Last step before you get your rate" page, enter
    - Email address format: coding.<random-number>@upgrade-challenge.com
    - Strong password: 8 characters long, contain at least one number, one uppercase letter, and one lower case letter.
    - Click the checkbox for Terms of Use
    - Click on the "Check Your Rate" button
6. Validate loan is rejected with "We´re sorry, you were not approved." page
7. From the `funnel/adverse-page`
    - Click on "If you would like to learn more about why you were not approved, please click here".
8. Validate that current page is `/portal/product/<loanId>/documents`
    - Validate that a link to "Adverse Action Notice.pdf" exists on the page.

##### API Testcase # 3

Upgrade exposes resume API for borrowers, that should provide a 200 (OK) response if a loan application exists. And a 404 response if the loan application does not exist. 
The API endpoint is available at: https://credapi.credify.tech/api/brfunnelorch/v2/resume/byLeadSecret

Write an automated test that makes a POST request to this API.
Sample request and a valid payload below (make sure to use the headers and payload as specified, otherwise the server will respond with a 500). 

POST Request
https://credapi.credify.tech/api/brfunnelorch/v2/resume/byLeadSecret
 
Headers:
`x-cf-source-id: coding-challenge`
`x-cf-corr-id: Any random UUID`
`Content-Type: application/json`
 
Request Body:
{
    "loanAppUuid":"b8096ec7-2150-405f-84f5-ae99864b3e96",
    "skipSideEffects":true
}
 
Expected Response:
```
{
    "loanAppResumptionInfo": {
        "loanAppId": 101398314,
        "loanAppUuid": "32aebe0e-3a09-441b-aaaa-3e80858818fd",
        "referrer": "LENDING_TREE",
        "status": "NEW",
        "productType": "PERSONAL_LOAN",
        "sourceSystem": "PARTNER_FUNNEL_V2",
        "desiredAmount": 25000.00,
        "borrowerResumptionInfo": {
            "firstName": "Benjamin",
            "maskedEmail": "q********@upgrade.com",
            "ssnRequired": false,
            "state": "OH",
            "email": "qa.preprod210618211325366@upgrade.com"
        },
        "coBorrowerResumptionInfo": null,
        "turnDown": false,
        "hasLogin": false,
        "availableAppImprovements": null,
        "cashOutAmount": null,
        "canAddCollateral": false,
        "rewardProgramId": null,
        "rewardProgramCode": null,
        "addon": null,
        "isMobileDiscountApplied": null,
        "checkingDiscountAvailable": false
    },
    "offers": [],
    "selectedOffer": null,
    "requiredAgreements": [],
    "resetOptions": [
        "LEAD_SECRET",
        "WIPE",
        "DEACTIVATE_USER"
    ],
    "originalLoanApp": null
}
```

In your automated tests - validate atleast the following, and feel free to include any other validations that you deem necessary:

##### Validations

1. Validate that for correct loanAppUuid provided in the payload below, the API response code is a 200 (OK)
2. For the above use case, validate the response and make sure productType attribute has value PERSONAL_LOAN.
3. Validate firstName as Benjamin in borrowerResumptionInfo.
4. Validate that in the initial POST request, if a different loanAppUuid is provided (that doesn't exist in our system) - the API response is a 404 (NOT_FOUND)

##### Code Submission:
Please run a `mvn clean` before archiving the project contents. Send us a zip file with entire project - only include source files (no class files or driver binaries).

##### Things to consider:
1. Coding best practices and design of any classes/test methods you come up with
2. Maintainability and reusability of code
3. Application of OOD principles
4. Extensibility of your solution to other areas of the Upgrade application.
5. Include reliable web locators in the page objects utilizing any special attributes available in the UI code.
6. Accuracy and robustness of Assertions
7. Good use of Exception handling, and Logging
8. Add pom.xml dependencies if you need any
9. Please don't include any of your personal information, in the UI test automation task.
