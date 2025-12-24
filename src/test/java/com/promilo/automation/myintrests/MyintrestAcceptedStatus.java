package com.promilo.automation.myintrests;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MyintrestAcceptedStatus extends Baseclass {

    @Test
    public void verifyAcceptedInterestTag() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📌 My Interest - Accepted Status | Data Driven Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        SoftAssert softAssert = new SoftAssert();

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
            String keyword = excel.getCellData(i, 1);
            if (!"InterestAccepted".equalsIgnoreCase(keyword)) continue;

            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            try {
                // Login
                LandingPage landingPage = new LandingPage(page);
                landingPage.getPopup().click();
                landingPage.clickLoginButton();

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(email);
                loginPage.passwordField().fill(password);
                loginPage.loginButton().click();

                // Navigate to My Interest
                page.locator("//span[text()='My Interest']").click();
                Thread.sleep(3000);

                // Click on Accepted tab
                page.locator("(//div[text()='Accepted'])[1]").first().click();
                Thread.sleep(3000);

                // Locate status tag
                Locator interestStatusTag = page.locator("//div[@class='btn-blue-outlined filled my-interest-status-tag']");
                interestStatusTag.waitFor(new Locator.WaitForOptions().setTimeout(5000));

                // Assertions
                softAssert.assertTrue(interestStatusTag.isVisible(), "❌ Interest status tag is not visible.");
                String tagText = interestStatusTag.textContent().trim();
                test.info("✅ Tag text found: " + tagText);

                softAssert.assertEquals(tagText, "Accepted", "❌ Tag text mismatch!");
                test.pass("✅ Interest accepted status verified for: " + email);

            } catch (Exception e) {
                test.fail("❌ Exception for email: " + email + " → " + e.getMessage());
            }
        }

        softAssert.assertAll();
    }
}
