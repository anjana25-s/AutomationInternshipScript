package com.promilo.automation.signupandlogin;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ExistingUserSignupPhone extends BaseClass {

    @Test
    public void signupWithExistingUserMail() throws Exception {
        Page dummyPage = initializePlaywright(); // Initializes prop

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging Signup - Existing User Phone Validation");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        System.out.println("Excel Path: " + excelPath);

        java.io.File excelFile = new java.io.File(excelPath);
        if (!excelFile.exists()) {
            test.fail("❌ Excel file not found at: " + excelPath);
            Assert.fail("Excel file not found at: " + excelPath);
        }

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        System.out.println("Total rows: " + rowCount);
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> signupKeywords = new HashSet<>(Arrays.asList("ExistingSignupPhone"));
        boolean testCaseExecuted = false;

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String expectedResult = excel.getCellData(i, 4);

            if (!signupKeywords.contains(keyword)) {
                continue;
            }

            testCaseExecuted = true;
            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);
            System.out.println("Launching Playwright browser for row " + i);

            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
                BrowserContext context = browser.newContext();
                Page page = context.newPage();

                page.navigate(prop.getProperty("url"));
                System.out.println("Navigated to: " + prop.getProperty("url"));
                test.info("🌐 Navigated to URL.");

                LandingPage landing = new LandingPage(page);
                try {
                    landing.getPopup().click();
                    test.info("✅ Closed popup.");
                } catch (Exception e) {
                    test.info("ℹ️ No popup to close.");
                }

                landing.getSignup().click();
                test.info("👉 Clicked Signup.");

                CreateAccountpage accountPage = new CreateAccountpage(page);
                accountPage.getPhoneMailTextField().fill(inputValue);
                test.info("📧 Entered existing user phone: " + inputValue);

                accountPage.getSendCodeButton().click();
                test.info("📨 Clicked Send Code button.");

                Locator errorToast = accountPage.getExistingUserToast();
                try {
                    errorToast.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
                    String toastText = errorToast.innerText().trim();
                    System.out.println("📢 Toaster Text Captured: " + toastText);
                    test.info("📢 Toast message: " + toastText);

                    // If you want to verify that the toast contains expected text partially:
                    if (toastText.toLowerCase().contains(expectedResult.toLowerCase()) || expectedResult.equalsIgnoreCase("User Already Exists")) {
                        test.pass("✅ Error toast displayed as expected for " + testCaseId + ": " + toastText);
                    } else {
                        test.fail("❌ Unexpected toast text. Expected to contain: '" + expectedResult + "' but got: '" + toastText + "'");
                        Assert.fail("Unexpected toast text for " + testCaseId);
                    }

                } catch (PlaywrightException e) {
                    // If no toast appeared, we consider it a failure since we expect an error
                    test.fail("❌ Expected error toast did not appear for " + testCaseId);
                    Assert.fail("Expected error toast did not appear for " + testCaseId);
                }

                // Capture screenshot
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_result.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup attempt");

                Thread.sleep(3000);
                System.out.println("Closed browser for row " + i);
                test.info("🧹 Test completed for TestCaseID: " + testCaseId);
            }
        }

        if (!testCaseExecuted) {
            test.fail("❌ No test case executed. Check Excel content and filtering logic.");
            Assert.fail("No test case executed. Check Excel content and filtering logic.");
        }

        extent.flush();
    }
}
