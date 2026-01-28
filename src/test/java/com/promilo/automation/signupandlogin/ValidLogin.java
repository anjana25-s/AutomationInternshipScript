package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

public class ValidLogin extends BaseClass {

    @Test
    public void validLogin() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("ValidLogin");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> loginKeywords = Collections.singleton("ValidLogin");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String InputValue = excel.getCellData(i, 3);
            String password = excel.getCellData(i, 6);

            if (!loginKeywords.contains(keyword)) {
                test.info("⏭️ Skipping TestCaseID: " + testCaseId + " with Keyword: " + keyword);
                continue;
            }

            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);

            // Launch and maximize browser
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            try {
                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);

                // Close popup if present
                try {
                    mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup if present.");
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup to close.");
                }

                // Click on Login button
                mayBeLaterPopUp.clickLoginButton();
                test.info("👉 Clicked Login button on landing page.");

                LoginPage loginPage = new LoginPage(page);

                // Fill username (email/phone)
                if (InputValue
                		== null || InputValue
                		.trim().isEmpty()) {
                    test.fail("❌ Username required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("Username missing for login: " + testCaseId);
                }
                loginPage.loginMailPhone().fill("ea88d8b0@ofuk8kzb.mailosaur.net");
                test.info("✍️ Entered Username: " + InputValue);

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
                
                Thread.sleep(2000);

                // Validate 'My Stuff' visibility
                HomePage homePage = new HomePage(page);
                PlaywrightAssertions.assertThat(homePage.mystuff()).isVisible();
                test.pass("✅ 'My Stuff' icon is visible after login for " + testCaseId + ". Marking test as PASS.");

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
