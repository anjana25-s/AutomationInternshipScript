package com.promilo.automation.advertiser.mymeetings;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class DelegateFunctionality extends Baseclass {

    @Test
    public void verifyDelegateMeetingFlow() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Delegate Functionality - Data Driven");

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

            if (!"AdvertiserDelegate".equalsIgnoreCase(keyword)) {
                continue;
            }

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            // Assertions
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk To expert content is not visible.");

            // Perform Login
            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            // Navigate to My Account > My Meetings > Jobs

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.hamburger().click();
            
            myaccount.myAccount().click();

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myMeeting().click();

            com.promilo.automation.advertiser.AdvertiserMymeetingPage Mymeeting = new com.promilo.automation.advertiser.AdvertiserMymeetingPage(page);
            Mymeeting.jobs().click();

            test.pass("✅ Delegate functionality flow executed for: " + email);
        }
    }
}
