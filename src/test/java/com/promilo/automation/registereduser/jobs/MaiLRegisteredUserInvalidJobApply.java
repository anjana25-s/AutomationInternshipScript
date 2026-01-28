package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MaiLRegisteredUserInvalidJobApply extends BaseClass {

    private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test
    public void applyForJobWithInvalidData() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("❌ Apply for Job Invalid OTP | Hardcoded Test");

        // Hardcoded test data
        String name = "karthik123";
        String mailphone = "111";

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);

        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();
        jobPage.applyNow().click();
        Thread.sleep(2000);

        jobPage.applyNameField().fill(name);
        jobPage.applyNowMobileTextField().fill(mailphone);
        jobPage.selectIndustryDropdown().click();
        Thread.sleep(1000);

        Locator option = page.locator("//div[contains(text(),'Healthcare')]").nth(0);
        if (option.isVisible()) option.click();

        jobPage.applyNameField().click();
        Thread.sleep(1000);

        Locator applyBtn = page.locator("//button[contains(@class,'submit-btm-askUs')]");
        applyBtn.scrollIntoViewIfNeeded();
        applyBtn.click();
        Thread.sleep(2000);
        

        // Screenshot
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/InvalidJobApply.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "Screenshot - Invalid OTP");

        byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
        test.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(screenshotBytes), "📸 Base64 Screenshot");

        page.close();
        test.info("🛑 Browser closed");
        extent.flush();
    }
}
