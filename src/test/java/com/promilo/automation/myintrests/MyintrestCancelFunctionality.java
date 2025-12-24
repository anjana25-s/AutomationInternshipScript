package com.promilo.automation.myintrests;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.*;

public class MyintrestCancelFunctionality extends Baseclass {

    @Test
    public void cancelMyInterestMeetingTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Cancel Meeting | Data Driven Test");

        // Load Excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"CancelMeeting".equalsIgnoreCase(keyword)) continue;

            test.info("▶️ Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Landing Page
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login Page
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // Navigate to "My Interest" and cancel
            MyResumePage resumePage = new MyResumePage(page);
            page.locator("//span[text()='My Interest']").click();
            page.locator("//span[text()='Cancel']").click();
            page.locator("//span[text()='Yes']").click();

            Locator successMessage = page.locator("//p[text()='Your meeting has been successfully cancelled.']");
            successMessage.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            Assert.assertTrue(successMessage.isVisible(), "✅ Success message should be visible after cancellation");
            String actualText = successMessage.textContent().trim();
            Assert.assertEquals(actualText, "Your meeting has been successfully cancelled.", "✅ Confirmation text mismatch");

            test.pass("✅ Meeting cancelled successfully for TestCaseID: " + testCaseId);
            page.close();
        }
    }
}
