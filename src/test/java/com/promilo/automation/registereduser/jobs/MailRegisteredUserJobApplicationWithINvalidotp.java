package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

public class MailRegisteredUserJobApplicationWithINvalidotp extends Baseclass {

    static final String SHEET_NAME = "PromiloTestData";
    static ExcelUtil excel;

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = System.getProperty("user.dir") + "/Testdata/PromiloAutomationTestData_Updated_With_OTP (2).xlsx";
        excel = new ExcelUtil(excelPath, SHEET_NAME);

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty()) break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount][1];
        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = i;
        }

        return data;
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(int rowIndex) throws Exception {
        String testCaseId = excel.getCellData(rowIndex, 0);
        String keyword = excel.getCellData(rowIndex, 1);
        String fieldType = excel.getCellData(rowIndex, 2);
        String inputValue = excel.getCellData(rowIndex, 3);
        String expectedResult = excel.getCellData(rowIndex, 4);
        String otp = excel.getCellData(rowIndex, 5);
        String password = excel.getCellData(rowIndex, 6);
        String name = excel.getCellData(rowIndex, 7);
        String mailPhone = excel.getCellData(rowIndex, 8);

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✉️ Mail Registered User Job Application with Invalid OTP | " + testCaseId);

        if (!"MailJobInvalidOtp".equalsIgnoreCase(keyword)) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to unmatched keyword.");
            return;
        }

        // Handle dynamic signup if specified in keyword
        if (keyword.equalsIgnoreCase("PhoneRegisteredJobApplyWithSignup")) {
            SignUpLogoutUtil signupUtil = new SignUpLogoutUtil();
            String[] creds = signupUtil.createAccountAndLoginFromExcel(
                    new ExcelUtil(Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString(), "PromiloTestData"),
                    rowIndex
            );

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 1000);

        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click();
        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);

        loginPage.loginMailPhone().fill(inputValue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.applyNow().click();
        Thread.sleep(2000);

        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill(mailPhone);

        homePage.selectIndustryDropdown().click();
        Thread.sleep(1000);

        List<String> industries = List.of("Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");
        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            for (int i = 0; i < options.count(); i++) {
                if (options.nth(i).innerText().trim().equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    break;
                }
            }
        }

        homePage.applyNameField().click();
        Thread.sleep(1000);
        page.locator("//button[contains(@class,'submit-btm-askUs')]").click();
        Thread.sleep(2000);

        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("Invalid OTP: " + otp);
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
                if (currentValue.equals(otpChar)) filled = true;
                else page.waitForTimeout(500);
            }

            if (!filled) throw new RuntimeException("❌ Failed to enter OTP digit " + (i + 1));
        }

        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();

        try {
            Locator errorLocator = page.locator("//div[@role='status' and @aria-live='polite']");
            errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            String actualErrorText = errorLocator.innerText().trim();
            test.pass("✅ Error toaster displayed. Message: '" + actualErrorText + "'");
        } catch (Exception e) {
            test.fail("❌ Expected error toaster not displayed for TestCaseID: " + testCaseId);
            Assert.fail("Error toaster not shown");
        }

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_error_toaster.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath);

        byte[] fileContent = Files.readAllBytes(Paths.get(screenshotPath));
        test.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(fileContent));

        page.close();
        test.info("✅ Test completed and browser closed for TestCaseID: " + testCaseId);
    }
    }}
