package com.promilo.automation.registereduser.jobs;

import java.nio.file.Paths;
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
import com.promilo.automation.pageobjects.signuplogin.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignUpLogoutUtil;

public class PhoneRegisteredJobApplyWithInvalidOtp extends Baseclass {

    private static final Logger logger = LogManager.getLogger(PhoneRegisteredJobApplyWithInvalidOtp.class);

    @DataProvider(name = "invalidOtpJobData")
    public Object[][] invalidOtpJobData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty()) break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount][8];
        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 3); // InputValue
            data[i - 1][3] = excel.getCellData(i, 6); // Password
            data[i - 1][4] = excel.getCellData(i, 7); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
            data[i - 1][7] = i;                       // RowIndex
        }
        return data;
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

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Invalid OTP Job Apply | " + testCaseId);

        if (!keyword.equalsIgnoreCase("PhoneRegisteredJobApplyWithInvalidOtp")) {
            logger.info("[{}] Skipped due to keyword mismatch.", testCaseId);
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 768);

        applyForJobWithInvalidOtp(page, inputvalue, password, name, otp);
        extent.flush();
    }

    public void applyForJobWithInvalidOtp(Page page, String inputvalue, String password, String name, String otp) throws Exception {
        LandingPage landingPage = new LandingPage(page);
        try { landingPage.getPopup().click(); } catch (Exception ignored) {}
        landingPage.clickLoginButton();

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

        Thread.sleep(4000);
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
    }
}
