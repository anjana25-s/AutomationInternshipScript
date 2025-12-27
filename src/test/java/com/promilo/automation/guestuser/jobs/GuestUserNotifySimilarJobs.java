package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserNotifySimilarJobs extends BaseClass {

    // Excel reference (not used for execution)
    private void readExcelForReference() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        String testCaseId = excel.getCellData(1, 0);
        String keyword = excel.getCellData(1, 1);
        String email = excel.getCellData(1, 3);
        String password = excel.getCellData(1, 6);
        String name = excel.getCellData(1, 7);
        String otp = excel.getCellData(1, 5);
        String mailphone = excel.getCellData(1, 8);
        String expectedResult = excel.getCellData(1, 4);

        System.out.println("Excel reference -> " + testCaseId + ", " + keyword + ", " + email);
    }

    @Test
    public void notifySimilarJobsAsGuestUser() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Guest User | Notify Similar Jobs | Single Run");

        // Hardcoded test data for single execution
        String name = "Guest User";
        String mailphone = "9000019014";
        String otp = "9999";

        // Launch Playwright
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Close landing popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        // Go to jobs and pick fintech
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.fintech();

        Locator fintechJobCard = page.locator(
                "//span[@class='font-12 additional-tags-text additional-cards-text-truncate jobs-brand-additional-title']");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        fintechJobCard.scrollIntoViewIfNeeded();
        fintechJobCard.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        fintechJobCard.click();

        // Notify similar jobs form
        homePage.notifySimilarJobs().click();
        homePage.applyNameField().nth(1).fill(name);
     // Generate random phone (90000 + 5 random digits)
        String randomPhone = "90000" + (10000 + new java.util.Random().nextInt(90000));

        // Generate random email (testuser + random 6 digits)
        String randomEmail = "testuser" + System.currentTimeMillis() % 1000000 + "@gmail.com";

        // Fill into fields
        page.locator("//input[@placeholder='Mobile*']").nth(1).fill(randomPhone);
        page.locator("input[placeholder='Email*']").nth(1).fill(randomEmail);

        // For debugging/logging
        System.out.println("Generated Phone: " + randomPhone);
        System.out.println("Generated Email: " + randomEmail);

        homePage.sendSimilarJobs().click();

        // OTP validation
        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be at least 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = page
                    .locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(
                    new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            int attempts = 0;
            while (!filled && attempts < 3) {
                attempts++;
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
            }
            test.info("Entered OTP digit: " + otpChar);
        }

        // Verify & proceed
        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();

        // Validate Thank You popup
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String popupText = thankYouPopup.innerText().trim();

        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, but found: " + popupText);
        test.pass("✅ 'Thank You!' popup validated successfully");

        // Screenshot
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/notifySimilarJobs_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after Notify Similar Jobs");

        page.close();
        extent.flush();
    }
}
