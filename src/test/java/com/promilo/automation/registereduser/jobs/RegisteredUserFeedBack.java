package com.promilo.automation.registereduser.jobs;

import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignUpLogoutUtil;

public class RegisteredUserFeedBack extends Baseclass {

    private static final Logger logger = LogManager.getLogger(RegisteredUserFeedBack.class);

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String inputvalue,
            String password,
            String name,
            String otp,
            String mailphone,
            int rowIndex
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Apply for Job as Registered User | " + testCaseId);

        // DEBUG LOG: Always log the keyword to help debug skip logic
        logger.info("[{}] Running test with keyword: '{}'", testCaseId, keyword);
        System.out.println("Running test with keyword: " + keyword + " | TestCaseID: " + testCaseId);

        // Normalize keyword to avoid accidental skip due to spaces or case mismatch
        String normalizedKeyword = keyword.trim().toLowerCase();

        // Only run if keyword matches expected types
        if (!(normalizedKeyword.equals("registereduserjobshortlist") ||
              normalizedKeyword.equals("registereduserjobshortlistwithsignup") ||
              normalizedKeyword.equals("registereduserfeedbackwithsignup"))) {
            logger.warn("[{}] Skipping test because keyword '{}' does not match expected patterns.", testCaseId, keyword);
            test.skip("Skipped: Keyword doesn't match required types for test execution.");
            return;
        }

        // Initialize Playwright page
        Page page = initializePlaywright();
        if (page == null) {
            throw new RuntimeException("Playwright page is not initialized. Browser failed to launch.");
        }

        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 678);
        test.info("Navigated to URL: " + prop.getProperty("url"));

        LandingPage landingPage = new LandingPage(page);
        try {
            landingPage.getPopup().click();
            logger.info("[{}] Closed initial popup if present.", testCaseId);
        } catch (Exception ignored) {
            logger.info("[{}] No popup to close.", testCaseId);
        }

        landingPage.clickLoginButton();
        test.info("Clicked login button on landing page.");

        LoginPage loginPage = new LoginPage(page);

        // Perform signup if keyword requires it
        if (normalizedKeyword.equals("registereduserfeedbackwithsignup") ||
            normalizedKeyword.equals("registereduserjobshortlistwithsignup")) {

            logger.info("[{}] Performing dynamic signup for fresh user.", testCaseId);
            SignUpLogoutUtil signupUtil = new SignUpLogoutUtil();

            String[] generatedCreds = signupUtil.createAccountAndLoginFromExcel(
                    new ExcelUtil(
                            Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString(),
                            "PromiloTestData"
                    ),
                    rowIndex
            );

            inputvalue = generatedCreds[0];
            password = generatedCreds[1];

            logger.info("[{}] Dynamic signup completed with Email: {}, Password: {}", testCaseId, inputvalue, password);
            test.info("Dynamic signup completed with email: " + inputvalue);
        }

        // Perform login
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();
        test.info("Logged in successfully with user: " + inputvalue);

        // Job Shortlist Feedback Flow
        JobListingPage jobListing = new JobListingPage(page);
        jobListing.homepageJobs().click();
        page.waitForTimeout(2000);
        page.locator("//span[text()='Hybrid  / Online & offline']").click(); // Select job category
        page.locator("//textarea[@placeholder='Write a feedback']").fill("something"); // Enter feedback
        page.locator("//button[@class='feedback-save-btn']").click(); // Save feedback

        // Validate Thank You Message
        String expectedMessage = "Thank You! Congratulations! You did it. Our expert will take it from here, and we’ll keep you updated on the progress";
        Locator thankYouMessage = page.locator("//*[contains(text(),'Thank You!')]");
        String actualMessage = thankYouMessage.innerText().trim();

        Assert.assertTrue(
            actualMessage.contains(expectedMessage),
            "Confirmation message not found or does not match. Expected to contain: '" + expectedMessage + "' but found: '" + actualMessage + "'."
        );

        test.pass("Thank You confirmation message is displayed correctly after job shortlisting.");
        logger.info("[{}] Thank You confirmation message displayed: {}", testCaseId, actualMessage);

        // Cleanup
        page.close();
    }

}
