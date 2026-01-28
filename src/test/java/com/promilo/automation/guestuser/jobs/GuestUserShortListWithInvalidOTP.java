package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import java.util.Optional;

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

public class GuestUserShortListWithInvalidOTP extends BaseClass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // ✅ Always pick ONLY the first valid row (ignores other 49 rows)
        if (excel.getCellData(1, 0) == null || excel.getCellData(1, 0).trim().isEmpty()) {
            throw new RuntimeException("No valid data found in Excel row 1.");
        }

        Object[][] data = new Object[1][8];
        data[0][0] = excel.getCellData(1, 0); // TestCaseID
        data[0][1] = excel.getCellData(1, 1); // Keyword
        data[0][2] = excel.getCellData(1, 3); // Email
        data[0][3] = excel.getCellData(1, 6); // Password
        data[0][4] = excel.getCellData(1, 7); // Name
        data[0][5] = excel.getCellData(1, 5); // OTP
        data[0][6] = excel.getCellData(1, 8); // MailPhone
        data[0][7] = excel.getCellData(1, 9); // ExpectedResult

        return data;
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(String testCaseId, String keyword, String email, String password,
            String name, String otp, String mailphone, String expectedResult) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        if (!keyword.equalsIgnoreCase("JobShortList")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        Thread.sleep(5000);
        homePage.jobShortlist1().click();
        Thread.sleep(4000);

        // Fill in valid name and phone
        page.locator("//input[@name='userName']").fill("karthik U");
        page.locator("//input[@placeholder='Mobile*']").fill("9000090780");

        homePage.jobShortList().click();

        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP provided is less than 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

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
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1) + " correctly after retries.");
            }
        }

        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();

        try {
            Locator errorLocator = page.locator("//div[@role='status' and @aria-live='polite']");
            errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            String actualErrorText = errorLocator.innerText().trim();
            test.info("⚡ Actual Error Text captured: " + actualErrorText);

            test.pass("✅ Error toaster displayed as expected for " + testCaseId + ". Message: '" + actualErrorText + "'");

        } catch (Exception e) {
            test.fail("❌ Error toaster was not displayed for " + testCaseId + ". Expected: '"
                    + Optional.ofNullable(expectedResult).orElse("").trim() + "'");
            e.printStackTrace();
            Assert.fail("Error toaster was not displayed for " + testCaseId);
        }

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup");

        page.close();
        test.info("Browser closed for TestCaseID: " + testCaseId);
    }
}
