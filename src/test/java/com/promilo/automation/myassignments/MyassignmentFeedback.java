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

public class MyassignmentFeedback extends Baseclass {

    @Test
    public void assignmentFeedbackDataDrivenTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 MyAssignment Feedback | Data-Driven Test");

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

            if (!"MyAssignmentFeedback".equalsIgnoreCase(keyword)) continue;

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Landing page
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // MyResume navigation
            MyResumePage resumePage = new MyResumePage(page);
            resumePage.Mystuff().click();
            Assert.assertTrue(resumePage.Mystuff().isVisible(), "Mystuff menu should be visible after login");
            resumePage.MyAccount().click();

            page.locator("//a[text()='My Assignment']").click();
            page.locator("//div[text()='Completed']").click();

            page.locator("img[class='assignment-query-image pointer']").click();
            Locator stars = page.locator("//span[contains(@style, 'color: rgb(239, 175, 65)')]");

            int count = stars.count();
            test.info("⭐ Rating Given: " + count);
            System.out.println("Rating Given: " + count);

            for (int j = 0; j < count; j++) {
                Locator star = stars.nth(j);
                System.out.println("Star " + (j + 1) + ": given = " + star.isVisible());
            }

            Locator comment = page.locator("div[class='assignment-feedback-comment']").first();
            String text = comment.textContent().trim();
            test.info("💬 Assignment Comment: " + text);
            System.out.println("Assignment Comment: " + text);
        }
    }
}
