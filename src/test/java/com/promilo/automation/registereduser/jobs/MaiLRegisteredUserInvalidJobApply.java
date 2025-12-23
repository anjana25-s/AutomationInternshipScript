package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignUpLogoutUtil;

public class MaiLRegisteredUserInvalidJobApply extends BaseClass {

    private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            if (excel.getCellData(i, 0) == null || excel.getCellData(i, 0).isEmpty()) break;
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

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUserWithInvalidOTP(
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
        ExtentTest test = extent.createTest("❌ Apply for Job Invalid OTP | " + testCaseId);

        if (!keyword.equalsIgnoreCase("RegisteredUserjobApplyInvalidWithSignup")) {
            logger.info("[{}] ⏭️ Skipped due to unmatched keyword.", testCaseId);
            return;
        }

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        SignUpLogoutUtil signupUtil = new SignUpLogoutUtil();
        String[] generatedCreds = signupUtil.createAccountAndLoginFromExcel(excel, rowIndex);
        inputvalue = generatedCreds[0];
        password = generatedCreds[1];

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        LandingPage landingPage = new LandingPage(page);
        try {
            landingPage.getPopup().click();
        } catch (Exception ignored) {}

        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();
        jobPage.applyNow().click();
        Thread.sleep(2000);

        jobPage.applyNameField().fill(name);
        jobPage.applyNowMobileTextField().fill(mailphone);
        jobPage.selectIndustryDropdown().click();
        Thread.sleep(1000);

        for (int i = 0; i < 3; i++) {
            Locator option = page.locator("//div[contains(text(),'Healthcare')]").nth(0);
            if (option.isVisible()) {
                option.click();
                break;
            }
        }

        jobPage.applyNameField().click();
        Thread.sleep(1000);

        Locator applyBtn = page.locator("//button[contains(@class,'submit-btm-askUs')]");
        applyBtn.scrollIntoViewIfNeeded();
        applyBtn.click();
        Thread.sleep(2000);

        Locator errorLocator = page.locator("//div[@role='status' and text()='This mobile number is already registered. Please try login']");
        try {
            errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            String errorText = errorLocator.innerText().trim();
            Assert.assertTrue(errorText.contains("already registered"), "Unexpected error text: " + errorText);
            test.pass("✅ Error displayed as expected: " + errorText);
        } catch (Exception e) {
            test.fail("❌ Expected error toaster not shown for testCase: " + testCaseId);
            Assert.fail("Error toaster not displayed.");
        }

        // Screenshot
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_invalid_apply.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "Screenshot - Invalid OTP");

        byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
        test.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(screenshotBytes), "📸 Base64 Screenshot");

        page.close();
        test.info("🛑 Browser closed for: " + testCaseId);
        extent.flush();
    }
}
