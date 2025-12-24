package com.promilo.automation.signupandlogin;



import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CorrectEmailWrongPass extends BaseClass {

    @Test
    public void validLogin() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging Login - Passes if 'My Stuff' is visible after login (Playwright)");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> loginKeywords = Collections.singleton("CorrectEmailWrongPass");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String username = excel.getCellData(i, 3);
            String password = excel.getCellData(i, 6);

            if (!loginKeywords.contains(keyword)) {
                test.info("⏭️ Skipping TestCaseID: " + testCaseId + " with Keyword: " + keyword);
                continue;
            }

            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);

            // Launch and maximize browser
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1920, 1080);

            try {
                LandingPage landingPage = new LandingPage(page);

                // Close popup if present
                try {
                    landingPage.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup if present.");
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup to close.");
                }

                // Click on Login button
                landingPage.clickLoginButton(); 
                test.info("👉 Clicked Login button on landing page.");

                LoginPage loginPage = new LoginPage(page);

                // Fill username (email/phone)
                if (username == null || username.trim().isEmpty()) {
                    test.fail("❌ Username required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("Username missing for login: " + testCaseId);
                }
                loginPage.loginMailPhone().fill(username);
                test.info("✍️ Entered Username: " + username);

                // Fill password
                if (password == null || password.trim().isEmpty()) {
                    test.fail("❌ Password required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("Password missing for login: " + testCaseId);
                }
                loginPage.passwordField().fill(password);
                test.info("🔑 Entered Password.");

                // Click login
                loginPage.loginButton().click();
                test.info("🚀 Clicked Login button.");

             // Error toast validation
                Locator errorToast = page.locator("//div[@role='status'] | //p[contains(text(),'Invalid') or contains(text(),'invalid') or contains(text(),'wrong')]");

                // Wait for the toast to become visible
                errorToast.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

                // Fetch toast text
                String toastText = errorToast.innerText();

                // Get expected text from Excel
                String expectedResult = excel.getCellData(i, 4); // ensure this line is before this block

                // Debug logs
                System.out.println("Toast Text captured: '" + toastText + "'");
                System.out.println("Expected Text from Excel: '" + expectedResult + "'");

                // Assertion using TestNG
                Assert.assertTrue(
                    toastText.equalsIgnoreCase(expectedResult),
                    "❌ Expected toast message: '" + expectedResult + "' but found: '" + toastText + "'"
                );

                test.pass("✅ Error toaster displayed with correct message for " + testCaseId + ": " + toastText);

              
                // Capture screenshot after successful login
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
