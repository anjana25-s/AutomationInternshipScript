package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

public class MailLoginWithValidOtp extends Baseclass {

    @Test
    public void mailLoginWithValidOtp() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging - Mail Login with Valid OTP (Playwright)");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> loginKeywords = Collections.singleton("MailValidOtpLogin");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String otp = excel.getCellData(i, 5);

            if (!loginKeywords.contains(keyword)) {
                test.info("⏭️ Skipping TestCaseID: " + testCaseId + " with Keyword: " + keyword);
                continue;
            }

            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1920, 1080);

            try {
                LandingPage landingPage = new LandingPage(page);

                try {
                    landingPage.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup if present.");
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup to close.");
                }

                landingPage.getLoginButton().click(new Locator.ClickOptions().setTimeout(10000));
                test.info("👉 Clicked Login button.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(inputValue);
                test.info("✍️ Entered " + fieldType + ": " + inputValue);

                loginPage.loginWithOtp().click();
                test.info("📨 Clicked 'Login with OTP'.");

                if (otp == null || otp.trim().isEmpty()) {
                    test.fail("❌ OTP required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("OTP missing for login: " + testCaseId);
                }
                loginPage.otpField().fill(otp);
                test.info("🔢 Entered OTP: " + otp);

                loginPage.loginButton().click();
                test.info("🚀 Clicked Login button after OTP entry.");

                DashboardPage dashboardPage = new DashboardPage(page);

                // Explicit wait for 'My Stuff' to appear visibly
                dashboardPage.mystuff().waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000));

                // Playwright + TestNG Assertion
                PlaywrightAssertions.assertThat(dashboardPage.mystuff()).isVisible();
                Assert.assertTrue(dashboardPage.mystuff().isVisible(),
                        "'My Stuff' icon should be visible after login for " + testCaseId);
                test.pass("✅ 'My Stuff' icon is visible after login for " + testCaseId + ". Marking test as PASS.");

                // Capture screenshot after success
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_login_pass.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after login");

            } catch (Exception e) {
                test.fail("❌ TestCaseID: " + testCaseId + " failed: " + e.getMessage());
                throw e;
            } finally {
                closePlaywright();
                test.info("🧹 Closed browser for TestCaseID: " + testCaseId);
            }
        }

        extent.flush();
    }
}
