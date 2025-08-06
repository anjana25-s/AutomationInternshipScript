package com.promilo.automation.myassignments;

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
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AssignmentWithoutProvidingData extends Baseclass {

    @Test
    public void assignmentSubmissionWithoutDataTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📌 Assignment | Submit without providing data (Data-driven)");

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

            if (!"AssignmentWithoutProvidingData".equalsIgnoreCase(keyword)) continue;

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Landing Page
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // Navigate to My Assignment
            MyResumePage resumePage = new MyResumePage(page);
            resumePage.Mystuff().click();
            Assert.assertTrue(resumePage.Mystuff().isVisible(), "Mystuff menu should be visible after login");
            resumePage.MyAccount().click();

            page.locator("//a[text()='My Assignment']").click();
            Thread.sleep(2000);

            page.locator("//span[text()='Start Assignment' and //div[@class='preferance-functional-button']]").click();
            page.locator("//span[text()='Submit Assignment']").click();

            Locator assignmentTextarea = page.locator("div[aria-label='Editor editing area: main']");
            assignmentTextarea.click();
            page.keyboard().type(""); // No content
            page.keyboard().press("Enter");

            page.locator("//span[text()='Submit']").click();

            Locator errorLocator = page.locator("//p[text()='Minimum 3 characters required']");
            errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            if (!errorLocator.isVisible()) {
                test.fail("❌ Expected error message not shown for empty assignment submission.");
                throw new AssertionError("❌ Error message was not displayed.");
            } else {
                test.pass("✅ Correct error message displayed for empty assignment submission.");
            }

            break; // remove if multiple executions are expected
        }
    }
}
