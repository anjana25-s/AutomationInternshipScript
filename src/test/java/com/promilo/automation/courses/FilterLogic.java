package com.promilo.automation.courses;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class FilterLogic extends Baseclass {

    @Test
    public void downloadBrochure() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Comment Functionality | Simplified");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
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
            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            String email = excel.getCellData(i, 7); // MailPhone
            String password = excel.getCellData(i, 6); // Password

            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            // Close popup if present
            try {
                new LandingPage(page).getPopup().click();
            } catch (Exception ignored) {}

            Thread.sleep(3000);

            // Navigate to Courses
            page.locator("//a[text()='Courses']").click();
            Thread.sleep(5000);

         // Apply Filters directly without methods

         // Location filter
         page.locator("//p[text()='Location']").click();
         Thread.sleep(2000);
         page.locator("//span[text()='Ahmedabad']").click();
         page.waitForLoadState(LoadState.NETWORKIDLE);
         Thread.sleep(2000);
         System.out.println("After Location filter: " + page.url());

         // Stream filter
         page.locator("//p[text()='Stream']").first().click();
         page.locator("//span[text()='Engineering']").first().click();
         page.waitForLoadState(LoadState.NETWORKIDLE);
         Thread.sleep(2000);
         System.out.println("After Stream filter: " + page.url());

         // Course Level filter
         page.locator("//p[text()='Course Level']").click();
         page.locator("//span[text()='B.E. / B.Tech']").first().click();
         page.waitForLoadState(LoadState.NETWORKIDLE);
         Thread.sleep(2000);
         System.out.println("After Course Level filter: " + page.url());



            
            page.close(); 
        }

        extent.flush(); 
    }
}
