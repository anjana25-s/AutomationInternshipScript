package com.promilo.automation.registereduser.jobs;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;

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

public class AskUsJobTestWithInvalidOtp extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur UI signup...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() {
        return new Object[][] {
            // TestCaseID , Keyword , Name , OTP , Mobile , TextArea
            { "TC01", "Askus?", "Test User", "1234", "", "Feedback from automation" }
        };
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId, String keyword, String name, String otp,
            String mailphone, String textArea) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job (AskUs) | " + testCaseId);

        // Only run Askus? keyword
        if (!keyword.equalsIgnoreCase("Askus?")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            throw new SkipException("Skipping as keyword is " + keyword);
        }

        if (registeredEmail == null || registeredPassword == null) {
            test.fail("❌ Signup credentials not found.");
            Assert.fail("Signup not completed.");
            return;
        }

        // 🔑 Use dynamic signup creds
        String email = registeredEmail;
        String password = registeredPassword;

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // --- Login Flow ---
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
        } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(email);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        // --- Apply Job Flow (AskUs Form) ---
        JobListingPage homePage = new JobListingPage(page);
        page.waitForTimeout(3000);
        homePage.homepageJobs().click();
        
        page.locator("//h3[text()='Crime Scene Cleaner (Trauma & Biohazard Technician']").first().click();
        page.waitForTimeout(2000);
        page.locator("//button[text()='Ask us?']").first().click();

        page.locator("//input[@name='userName']").fill(name);

        // ✅ Generate random 10-digit number (always starts with 90000)
        int randomSuffix = (int)(Math.random() * 100000); // 0–99999
        String phoneNumber = "90000" + String.format("%05d", randomSuffix);

        // Fill the number
        page.locator("//input[@placeholder='Mobile*']").fill(phoneNumber);
        page.locator("//textarea[@id='feedbackDetails']").fill(textArea);

        homePage.askUsSubmitButton().click();
        page.waitForTimeout(3000);

        // OTP input logic
        if (otp == null || otp.length() < 4)
            throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

        for (int i = 0; i < 4; i++) {
            String digit = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            for (int retry = 0; retry < 3; retry++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(digit);

                if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                    break;
                page.waitForTimeout(500);
            }
        }

        page.locator("//button[text()='Verify & Proceed']").click();

      Thread.sleep(5000);

        extent.flush();
    }
}
