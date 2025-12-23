package com.promilo.automation.signupandlogin;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ResetPasswordwith_Existingmail extends BaseClass {

    @Test
    public void resetPasswordWithExistingMail() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging - Reset Password with Existing Mail (Playwright)");

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

        Set<String> resetKeywords = new HashSet<>(Collections.singletonList("ResetPasswordExistingMail"));

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String expectedResult = excel.getCellData(i, 4);
            String otp = excel.getCellData(i, 5);
            String password = excel.getCellData(i, 6);

            if (!resetKeywords.contains(keyword)) {
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
                test.info("✅ Popup closed.");
                System.out.println("✅ Popup closed.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup present, continuing.");
                System.out.println("ℹ️ No popup present, continuing.");
            }

            landingPage.clickLoginButton();
            test.info("🔷 Login page opened.");
            System.out.println("🔷 Login page opened.");

            LoginPage loginPage = new LoginPage(page);

            loginPage.loginMailPhone().fill(inputValue);
            test.info("📧 Entered email for reset: " + inputValue);
            System.out.println("📧 Entered email for reset: " + inputValue);

            loginPage.forgotPassword().click();
            test.info("🔄 Clicked 'Forgot Password'.");
            System.out.println("🔄 Clicked 'Forgot Password'.");

            loginPage.otpField().fill(otp);
            test.info("🔑 Entered OTP: " + otp);
            System.out.println("🔑 Entered OTP: " + otp);

            loginPage.passwordField().fill(password);
            test.info("🔒 Entered new password.");
            System.out.println("🔒 Entered new password.");

            loginPage.loginButton().click();
            test.info("🔓 Clicked 'Login' after password reset.");
            System.out.println("🔓 Clicked 'Login' after password reset.");

          

            // ✅ Post-reset login validation
            test.info("🔄 Performing login validation after password reset for " + testCaseId);
            System.out.println("🔄 Performing login validation after password reset for " + testCaseId);

            DashboardPage dashboardPage = new DashboardPage(page);
            Locator mystuff = dashboardPage.mystuff();

            mystuff.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            Assert.assertTrue(mystuff.isVisible(), "❌ Profile image not displayed, login may have failed for " + testCaseId);

            test.pass("✅ Login successful; profile image displayed for " + testCaseId);
            System.out.println("✅ Login successful; profile image displayed for " + testCaseId);

            page.close();
            test.info("🧹 Browser closed for TestCaseID: " + testCaseId);
            System.out.println("🧹 Browser closed for TestCaseID: " + testCaseId);
        }

        extent.flush();
        System.out.println("✅ Test execution completed and Extent report flushed.");
    }
}
