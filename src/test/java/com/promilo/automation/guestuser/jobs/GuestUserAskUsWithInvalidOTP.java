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

public class GuestUserAskUsWithInvalidOTP extends BaseClass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount - 1][7];
        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 3); // InputValue (Email)
            data[i - 1][3] = excel.getCellData(i, 6); // Password
            data[i - 1][4] = excel.getCellData(i, 7); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
        }
        return data;
    }

    @Test
    public void applyForJobAsGuestUserWithInvalidOtp() throws Exception {
        String testCaseId = "GU_InvalidOTP_01";
        String otp = "1234"; // intentionally invalid OTP

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚫 Guest User Ask Us - Invalid OTP | " + testCaseId);

        // Initialize Playwright
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Pass 'page' to Page Objects
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();

        page.locator("//input[@placeholder='Search Jobs']").fill("December Campaign Automation");
        page.keyboard().press("Enter");
        // Fill details
        page.locator("input[name='userName']").nth(1).fill("karthik");
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

        page.locator("//textarea[@id='feedbackDetails']").first().fill("something");

        homePage.askUsSubmitButton().click();

        // OTP entry
        Locator otpFields = page.locator("//input[starts-with(@aria-label, 'Please enter OTP character')]");
        otpFields.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));

        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            int attempts = 0;
            boolean filled = false;
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
        
        page.locator("//button[text()='Verify & Proceed']").click();
        test.info("Clicked Verify & Proceed");


        // Validation: Thank You popup should NOT appear
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        try {
            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            test.fail("❌ Test failed: 'Thank You!' popup appeared with invalid OTP");
            Assert.fail("Negative test failed: 'Thank You!' popup appeared unexpectedly.");
        } catch (Exception e) {
            test.pass("✅ Negative validation passed: 'Thank You!' popup did not appear as expected.");
        }

       

        // Screenshot
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_invalidOtp.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath);

        page.close();
        page.context().close();
        extent.flush();
    }
}
