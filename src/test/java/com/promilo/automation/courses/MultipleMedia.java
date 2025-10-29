package com.promilo.automation.courses;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExcelReadUtil;  // ✅ added import
import com.promilo.automation.resources.ExtentManager;

public class MultipleMedia extends Baseclass {

    @Test
    public void TaltoExpert() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Comment Functionality | Data-Driven");

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

        System.out.println("📘 Loaded " + rowCount + " rows from Excel.");
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7); // MailPhone
            String password = excel.getCellData(i, 6); // Password
            String comment = excel.getCellData(i, 10); // Comment text

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            System.out.println("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);
            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            LandingPage landingPage = new LandingPage(page);
            try {
                landingPage.getPopup().click();
            } catch (Exception ignored) {
            }

            Thread.sleep(3000);
            page.locator("//a[text()='Courses']").click();
            Thread.sleep(3000);

            page.locator("//div[text()='Vinayaka Institute of technology -BN -BE -Ben -10L']").first().click();
            
            

         // Get all swipe icons
            Locator swipeIcons = page.locator("//span[@class='swiper-bullet-description swiper-pagination-bullet']");

            // Count how many are there
            int count = swipeIcons.count();

            // Loop through each and hover + click
            for (int h = 0; i < count; i++) {
                swipeIcons.nth(h).hover();
                page.waitForTimeout(500); // small wait to see hover effect
                swipeIcons.nth(h).click();
                page.waitForTimeout(1000); // wait to observe slide change
            }


            


            
        }
    }
}
