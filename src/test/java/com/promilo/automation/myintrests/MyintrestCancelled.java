package com.promilo.automation.myintrests;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MyintrestCancelled extends Baseclass {

    @Test
    public void cancelStatusVerificationTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 My Interest Cancellation Status | Data Driven");

        // Excel setup
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"CancelInterestStatus".equalsIgnoreCase(keyword)) {
                continue;
            }

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Landing page interaction
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login page interaction
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // Navigate to My Interest and check Cancelled status
            page.locator("//span[text()='My Interest']").click();
            page.locator("//div[text()='Cancelled'][1]").first().click();

            Locator cancelledStatus = page.locator("//div[contains(@class, 'btn-cancelled-outlined') and contains(text(), 'Cancelled')]");
            cancelledStatus.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            // Assert and report
            Assert.assertTrue(cancelledStatus.isVisible(), "❌ 'Cancelled' status is not visible.");
            test.pass("✅ 'Cancelled' status is displayed successfully for test case: " + testCaseId);

            // Close browser after each test case
            page.close();
        }
    }
}
