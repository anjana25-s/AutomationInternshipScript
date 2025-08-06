package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

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

public class RegisteredUserShortListWithInvalidOtp extends Baseclass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
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

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobWithInvalidOtp(
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
        ExtentTest test = extent.createTest("Apply for Job as Registered User (Invalid OTP) | " + testCaseId);

        if (!(keyword.equalsIgnoreCase("JobShortList") || keyword.equalsIgnoreCase("JobShortListWithSignup"))) {
            test.info("Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        if (keyword.equalsIgnoreCase("JobShortListWithSignup")) {
            SignUpLogoutUtil signupUtil = new SignUpLogoutUtil();
            String[] generatedCreds = signupUtil.createAccountAndLoginFromExcel(
                    new ExcelUtil(Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString(), "PromiloTestData"),
                    rowIndex);
            inputvalue = generatedCreds[0];
            password = generatedCreds[1];

            // Logout after signup
            Page tempPage = initializePlaywright();
            tempPage.navigate(prop.getProperty("url"));
            tempPage.setViewportSize(1366, 768);
            tempPage.close();
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        LandingPage landingPage = new LandingPage(page);
        try { landingPage.getPopup().click(); } catch (Exception ignored) {}
        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        Thread.sleep(3000);
        homePage.jobShortlist1().click();
        Thread.sleep(3000);

        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']").fill(name);

        Random random = new Random();
        String randomMobile = "90000" + String.format("%05d", random.nextInt(100000));
        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']").fill(randomMobile);

        // Update mobile number in Excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        excel.setCellData(rowIndex, 8, randomMobile);

        Thread.sleep(4000);
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

            test.info("Actual Error Text captured: " + actualErrorText);
            test.pass("Error toaster displayed as expected for " + testCaseId + ". Message: '" + actualErrorText + "'");

        } catch (Exception e) {
            test.fail("Error toaster was not displayed for " + testCaseId + ". Expected OTP error.");
            e.printStackTrace();
            Assert.fail("Error toaster not displayed for " + testCaseId);
        }

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_invalidOtp.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "Screenshot after invalid OTP submit");

        byte[] fileContent = Files.readAllBytes(Paths.get(screenshotPath));
        String base64Screenshot = Base64.getEncoder().encodeToString(fileContent);
        test.addScreenCaptureFromBase64String(base64Screenshot, "Base64 Screenshot after invalid OTP");

        page.close();
        test.info("Browser closed for TestCaseID: " + testCaseId);

        extent.flush();
    }
}
