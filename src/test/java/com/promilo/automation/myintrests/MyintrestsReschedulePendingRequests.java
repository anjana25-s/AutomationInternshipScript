package com.promilo.automation.myintrests;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MyintrestsReschedulePendingRequests extends BaseClass {

    @Test
    public void reschedulePendingRequestsTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📘 My Interests - Reschedule Pending Requests | Data-Driven");

        // Initialize Playwright and navigate
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        // Load Excel data
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
            String email = excel.getCellData(i, 7); // MailPhone
            String password = excel.getCellData(i, 6);

            if (!"ReschedulePendingRequests".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔁 Running test for TestCaseID: " + testCaseId);

            // Landing page interaction
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login page interaction
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // My Interest > Reschedule Requests
            page.locator("//span[text()='My Interest']").click();
            page.locator("//div[text()='Reschedule Requests']").click();

            Thread.sleep(5000); // Static wait – replace with smarter wait if needed

            test.pass("✅ Reschedule Pending Requests page accessed successfully for user: " + email);
            break; // Remove break if multiple rows are to be tested
        }
    }
}
