package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserShortList extends BaseClass {

    // ❌ DataProvider IGNORED (kept but not used)
    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        return new Object[][] {
            { "TC01", "JobShortList", "dummy@gmail.com", "dummyPass", "GuestUser", "9999", "9999999999" }
        };
    }

    // ❌ Removed DataProvider from test annotation
    @Test
    public void applyForJobAsRegisteredUser() throws Exception {

        // 🔵 Hardcoded sample inputs for NON-data-driven execution
        String testCaseId = "TC01";
        String keyword = "JobShortList";
        String email = "guestuser@gmail.com";
        String password = "Guest@123";
        String name = "Guest User";
        String otp = "9999";
        String mailphone = "9000012345";

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Guest User | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        page.waitForTimeout(15000);

        // Click shortlist
        homePage.jobShortlist1().click();
        page.waitForTimeout(4000);

        
        // Fill user data
        page.locator("//input[@name='userName']").nth(1).fill(name);

     // Generate random phone (90000 + 5 random digits)
        String randomPhone = "90000" + (10000 + new java.util.Random().nextInt(90000));

        // Generate random email (testuser + random 6 digits)
        String randomEmail = "testuser" + System.currentTimeMillis() % 1000000 + "@gmail.com";

        // Fill into fields
        page.locator("//input[@placeholder='Mobile*']").nth(1).fill(randomPhone);
        page.locator("input[placeholder='Email*']").nth(1).fill(randomEmail);

        
        homePage.jobShortList().click();

        // OTP Entry
        for (int i = 0; i < 4; i++) {
            String otpChar = Character.toString(otp.charAt(i));
            Locator otpField = page
                    .locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
            otpField.fill(otpChar);
        }

        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.click();

        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "❌ Expected 'Thank You!' popup, but got: " + popupText);

        test.pass("✅ Thank You popup validated!");

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_guest_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath);

        page.context().close();
    }
}
