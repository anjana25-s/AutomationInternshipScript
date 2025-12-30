package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
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

public class PhoneRegisteredApplyJob extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("Phone Registered Apply Job (NO DATA-DRIVEN)");

    @Test
    public void applyForJobAsRegisteredUser() throws Exception {

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        // 👉 If you want to use signup utility like yesterday:
        // SignUpLogoutUtil.doSignUpAndReturnSession(page);

        applyJobAsRegisteredUser(page);

        test.pass("Test completed successfully.");
        extent.flush();
    }

    public void applyJobAsRegisteredUser(Page page) throws Exception {
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}

        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill("7026268342");
        loginPage.passwordField().fill("Karthik@88");
        loginPage.loginButton().click();

        applyJobDetailsFlow(page);
    }

    private void applyJobDetailsFlow(Page page) throws Exception {

        JobListingPage jobPage = new JobListingPage(page);

        Thread.sleep(5000);
        jobPage.homepageJobs().click();

        // Search job manually
        page.locator("//input[@placeholder='Search Jobs']")
            .fill("decem");
        page.keyboard().press("Enter");

        page.locator("//button[text()='Apply Now']").first().click();


        

        page.locator("//li[@class='time-slot-box list-group-item']").first().click();

        page.locator("//button[text()='Submit']").nth(1).click();
        test.info("📨 Clicked Submit button");

        
        
        

                Locator thankYouPopup =
                page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        Assert.assertEquals(thankYouPopup.innerText().trim(), "Thank You!", "Popup validation failed");

        test.pass("🎉 Job applied successfully");

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/PhoneRegisteredApplyJob.png";
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(screenshotPath))
                .setFullPage(true));

        test.addScreenCaptureFromPath(screenshotPath);
        test.addScreenCaptureFromBase64String(Base64.getEncoder()
                .encodeToString(Files.readAllBytes(Paths.get(screenshotPath))));
    }
}
