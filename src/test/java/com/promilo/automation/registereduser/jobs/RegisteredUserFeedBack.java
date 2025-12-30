package com.promilo.automation.registereduser.jobs;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserFeedBack extends BaseClass {

    private static final Logger logger = LogManager.getLogger(RegisteredUserFeedBack.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @Test
    public void registeredUserFeedbackTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Registered User -> Submit Feedback (No Data Driven)");

        // 🔵 Perform signup ONCE per execution
        if (registeredEmail == null || registeredPassword == null) {
            SignupWithMailosaurUI mailosaurSignup = new SignupWithMailosaurUI();
            String[] creds = mailosaurSignup.performSignupAndReturnCredentials();

            registeredEmail = creds[0];
            registeredPassword = creds[1];

            logger.info("👉 Mailosaur User Created: " + registeredEmail);
            test.info("Signed up using Mailosaur user: " + registeredEmail);
        }

        // 🔵 Initialize Playwright
        Page page = initializePlaywright();
        if (page == null) {
            throw new RuntimeException("❌ Browser launch failed. Page is null.");
        }

        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 678);
        test.info("Navigated to: " + prop.getProperty("url"));

        // 🔵 Handle initial popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
        } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        // 🔵 Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();

        test.info("Logged in as: " + registeredEmail);

        // 🔵 Feedback Flow
        JobListingPage jobListing = new JobListingPage(page);
        jobListing.homepageJobs().click();
        page.waitForTimeout(2000);
        
        com.promilo.automation.job.pageobjects.RegisteredUserShortListPageObjects objects = new com.promilo.automation.job.pageobjects.RegisteredUserShortListPageObjects(page);


        JobListingPage homePage = new JobListingPage(page);

        Thread.sleep(5000);
        homePage.homepageJobs().click();
        page.waitForTimeout(5000);

        homePage.searchJob().fill("December");
        page.keyboard().press("Enter");
        page.waitForTimeout(15000);


        page.locator("//button[@class='feedback-save-btn']").click();
        
        
        homePage.applyNameField().fill("Karthik");
        test.info("👤 Entered user name: karthik");

        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        homePage.applyNowMobileTextField().fill(randomPhone);
        test.info("📱 Entered random phone: " + randomPhone);
        
        
        
        page.locator("//button[text()='Submit']").nth(1).click();
        test.info("📨 Clicked Submit button");
        
        
        String otp = "9999";
        for (int i1 = 0; i1 < 4; i1++) {
            String digit = Character.toString(otp.charAt(i1));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            for (int retry = 0; retry < 3; retry++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(digit);
                if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                    break;
                page.waitForTimeout(500);
            }
            test.info("🔢 Entered OTP digit " + digit + " at position " + (i1 + 1));
        }

        page.locator("//button[text()='Verify & Proceed']").click();


                // 🔵 Validate popup
        Locator thankYou = page.locator("//*[contains(text(),'Thank You!')]");
        thankYou.waitFor();
        


        String actualText = thankYou.innerText().trim();
        String expectedText = "Thank You!";

        Assert.assertTrue(
                actualText.contains(expectedText),
                "❌ Expected: '" + expectedText + "' but found: '" + actualText + "'"
        );

        test.pass("🎉 Feedback Thank You popup displayed successfully!");

        page.close();
        extent.flush();
    }
}
