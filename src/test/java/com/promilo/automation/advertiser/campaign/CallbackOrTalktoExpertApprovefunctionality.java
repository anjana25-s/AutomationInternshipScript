package com.promilo.automation.advertiser.campaign;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class CallbackOrTalktoExpertApprovefunctionality extends Baseclass {

    @Test
    public void verifyCallbackTalkToExpertApproveFromExcel() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📊 Data-Driven Callback/Talk to Expert Approve Functionality");

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

            if (!"CallbackOrTalkToExpertApprove".equalsIgnoreCase(keyword)) {
                continue;
            }

            try {
                Page page = initializePlaywright();
                page.navigate(prop.getProperty("stageUrl"));
                page.setViewportSize(1000, 768);
                Thread.sleep(5000);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                // UI assertions
                Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
                Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");

                // Login using Excel data
                login.loginMailField().fill(email);
                login.loginPasswordField().fill(password);
                login.signInButton().click();

                // Navigate to My Account
AdvertiserHomepage Hamburger= new AdvertiserHomepage(page);

Hamburger.hamburger().click();
AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
                myaccount.myAccount().click();

                // Go to My Prospect
                AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
                prospect.myProspect().click();

                // Approve Callback/Talk to Expert
                AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
                approveFunctionality.Jobs().click();
                Thread.sleep(3000);
                approveFunctionality.CallbackOrTalktoExpert().click();

                approveFunctionality.ApproveButton().first().click();

                test.pass("✅ Callback/Talk to Expert Approve action completed for: " + email);

            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + " | Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
