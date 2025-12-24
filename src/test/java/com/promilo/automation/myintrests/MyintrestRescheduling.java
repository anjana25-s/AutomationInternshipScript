package com.promilo.automation.myintrests;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MyintrestRescheduling extends BaseClass {

    @Test
    public void rescheduleInterestTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📅 My Interest Rescheduling | Data-Driven Test");

        // Excel Setup
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

            if (!"RescheduleMyInterest".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔄 Executing Row " + i + " | TestCaseID: " + testCaseId);

            // Launch Browser
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

            // Navigate to My Interest
            page.locator("//span[text()='My Interest']").click();

            // Click Reschedule
            page.locator("img[alt='Reschedule']").click();
            Thread.sleep(3000);

            // Select a date
            page.locator("span[aria-label='July 29, 2025']").click();
            Thread.sleep(3000);

            // Select time slot list
            page.locator("//ul[@class='w-100 mt-1 list-group']").click();
            Thread.sleep(3000);

            // Get and print selected slot
            Locator chipButton = page.locator("div[class='flex flex-wrap gap-[8px] text-white btn-primary border-chip font-12 width-fit']");
            String buttonText = chipButton.textContent().trim();
            System.out.println("⏰ Selected Slot: " + buttonText);
            Thread.sleep(3000);

            // Click Continue
            page.locator("//button[text()='Continue']").click();
            Thread.sleep(3000);

            test.pass("✅ Rescheduling flow completed for: " + email);
        }
    }
}
