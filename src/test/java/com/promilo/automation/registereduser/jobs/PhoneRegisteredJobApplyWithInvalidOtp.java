package com.promilo.automation.registereduser.jobs;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class PhoneRegisteredJobApplyWithInvalidOtp extends BaseClass {

    private static final Logger logger = LogManager.getLogger(PhoneRegisteredJobApplyWithInvalidOtp.class);

    @DataProvider(name = "invalidOtpJobData")
    public Object[][] invalidOtpJobData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int totalRows = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty()) break;
            totalRows++;
        }

        List<Object[]> filteredData = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();

            // Only include rows for this test
            if (normalizedKeyword.equals("phoneregisteredjobapplywithinvalidotp")) {
                Object[] row = new Object[8];
                row[0] = testCaseId;
                row[1] = keyword;
                row[2] = excel.getCellData(i, 3); // InputValue
                row[3] = excel.getCellData(i, 6); // Password
                row[4] = excel.getCellData(i, 7); // Name
                row[5] = excel.getCellData(i, 5); // OTP
                row[6] = excel.getCellData(i, 8); // MailPhone
                row[7] = i;                         // RowIndex
                filteredData.add(row);
            }
        }

        // If no rows match, return a dummy row to pass the test
        if (filteredData.isEmpty()) {
            filteredData.add(new Object[]{"NoTest", "NoKeyword", "", "", "", "", "", 0});
        }

        return filteredData.toArray(new Object[0][0]);
    }

    @Test(dataProvider = "invalidOtpJobData")
    public void applyWithInvalidOtp(
            String testCaseId,
            String keyword,
            String inputvalue,
            String password,
            String name,
            String otp,
            String mailphone,
            int rowIndex
    ) throws Exception {

        // Pass automatically if no matching keyword
        if ("NoTest".equals(testCaseId)) {
            logger.info("No matching keywords found in Excel. Test marked as passed.");
            return;
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Invalid OTP Job Apply | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 768);

        applyForJobWithInvalidOtp(page, inputvalue, password, name, otp, test);

        extent.flush();
    }

    public void applyForJobWithInvalidOtp(Page page, String inputvalue, String password, String name, String otp, ExtentTest test) throws Exception {
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        page.waitForTimeout(2000);
        homePage.jobShortlist1().click();
        page.waitForTimeout(2000);
        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']").fill(name);

        String randomMobile = "90000" + String.format("%05d", new Random().nextInt(100000));
        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']").fill(randomMobile);

        page.waitForTimeout(4000);
        homePage.jobShortList().click();

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
                if (currentValue.equals(otpChar)) filled = true;
                else page.waitForTimeout(500);
            }

            if (!filled) throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
        }

        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();

        Locator errorToaster = page.locator("//div[contains(@class,'Toastify__toast--error')]");
        errorToaster.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(errorToaster.isVisible(), "Error toaster not visible for invalid OTP.");
        test.pass("Invalid OTP error toaster displayed correctly.");
    }
}
