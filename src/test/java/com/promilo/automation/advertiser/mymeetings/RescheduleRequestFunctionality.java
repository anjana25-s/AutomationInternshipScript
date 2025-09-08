package com.promilo.automation.advertiser.mymeetings;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.pageobjects.mymeetings.MymeetingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class RescheduleRequestFunctionality extends Baseclass {

    @Test
    public void verifyRescheduleRequestFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📅 Reschedule Advertiser Meeting | Data Driven");

        // Initialize Excel
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

            if (!"RescheduleAdvertiserMeeting".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔑 Executing Test Case ID: " + testCaseId + " | Keyword: " + keyword);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);

            Thread.sleep(5000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            // UI assertions
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk To expert content should be visible");

            // Login actions
            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            // Navigate to My Account → My Meeting
            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            AdvertiserHomepage home = new AdvertiserHomepage(page);
            home.myAccount().click();

            AdverstiserMyaccount account = new AdverstiserMyaccount(page);
            account.myMeeting().click();

            com.promilo.automation.advertiser.AdvertiserMymeetingPage myMeetingPage = new com.promilo.automation.advertiser.AdvertiserMymeetingPage(page);
            myMeetingPage.jobs().click();
            myMeetingPage.reschedule().click();
            myMeetingPage.acceptRequest().click();

            test.pass("✅ Reschedule request successfully processed for " + email);
            break; // Run only first matching row; remove `break` if all rows should be tested
        }
    }
}
