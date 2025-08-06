package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.*;
import com.promilo.automation.resources.*;

import java.nio.file.Paths;
import java.util.*;

public class SignupWithExistingPhoneNumberTest extends Baseclass {

    @Test
    public void signup() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging Signup - Keyword & TestCaseID Driven Execution (Playwright)");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");
        System.out.println("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> signupKeywords = new HashSet<>(Collections.singletonList("ValidSignup"));

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String expectedResult = excel.getCellData(i, 4);
            String otp = excel.getCellData(i, 5);
            String password = excel.getCellData(i, 6);

            if (!signupKeywords.contains(keyword)) {
                test.info("⏭️ Skipping TestCaseID: " + testCaseId + " with Keyword: " + keyword);
                continue;
            }

            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);
            System.out.println("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);

            Page page = initializePlaywright();
            page.setViewportSize(1280, 800);
            page.navigate(prop.getProperty("url"));
            System.out.println("🌐 Navigated to URL: " + prop.getProperty("url"));

            LandingPage landingPage = new LandingPage(page);

            try {
                landingPage.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup if present.");
                System.out.println("✅ Closed popup if present.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup present, continuing.");
                System.out.println("ℹ️ No popup present, continuing.");
            }

            landingPage.getSignup().click();
            test.info("👉 Clicked Signup.");
            System.out.println("👉 Clicked Signup.");

            CreateAccountpage accountPage = new CreateAccountpage(page);

            accountPage.getPhoneMailTextField().fill(inputValue);
            test.info("📱 Entered phone number for signup: " + inputValue);
            System.out.println("📱 Entered phone number for signup: " + inputValue);

            Assert.assertTrue(accountPage.getSendCodeButton().isEnabled(), "❌ Send Code should be enabled after input.");
            accountPage.getSendCodeButton().click();
            test.info("📨 Clicked Send Code.");
            System.out.println("📨 Clicked Send Code.");

            try {
                Locator toastLocator = accountPage.getExistingUserToast();
                toastLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                String toastText = toastLocator.innerText().trim();
                test.info("📢 Toast/Error message: " + toastText);
                System.out.println("📢 Toast/Error message: " + toastText);

                if (!expectedResult.isEmpty()) {
                    test.pass("✅ Existing phone number validation message displayed as expected for " + testCaseId);
                    System.out.println("✅ Existing phone number validation message displayed as expected for " + testCaseId);
                } else {
                    test.fail("❌ Expected signup success but received an error for " + testCaseId);
                    System.out.println("❌ Expected signup success but received an error for " + testCaseId);
                    Assert.fail("Expected signup success but received an error for " + testCaseId);
                }
            } catch (PlaywrightException e) {
                if (expectedResult.equalsIgnoreCase("SignupSuccessExpected")) {
                    test.pass("✅ Signup passed as expected for " + testCaseId);
                    System.out.println("✅ Signup passed as expected for " + testCaseId);
                } else {
                    test.fail("❌ Expected error for existing phone but signup proceeded for " + testCaseId);
                    System.out.println("❌ Expected error for existing phone but signup proceeded for " + testCaseId);
                    Assert.fail("Expected error for existing phone but signup proceeded for " + testCaseId);
                }
            }

            page.close();
            test.info("🧹 Closed browser for TestCaseID: " + testCaseId);
            System.out.println("🧹 Closed browser for TestCaseID: " + testCaseId);
        }

        extent.flush();
        System.out.println("✅ Test execution completed and Extent report flushed.");
    }
}
