package com.promilo.automation.advertiser.campaign;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ProspectRescheduleFunctionality extends BaseClass {

    @Test
    public void verifyProspectRescheduleWithExcel() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📊 Data-Driven: Prospect Reschedule Functionality");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("✅ Total Rows Loaded from Excel: " + rowCount);

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"ProspectReschedule".equalsIgnoreCase(keyword)) continue;

            test.info("▶️ Running TestCaseID: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);

            Thread.sleep(4000);

            // Login
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            // Navigate to My Account > My Prospect

            AdvertiserHomepage homePage = new AdvertiserHomepage(page);
            homePage.hamburger().click();
            homePage.myAccount().click();

            AdverstiserMyaccount myAccount = new AdverstiserMyaccount(page);
            myAccount.myProspect().click();

            AdvertiserProspects prospects = new AdvertiserProspects(page);
            prospects.Jobs().click();
            Thread.sleep(3000);

            prospects.Reschedule().click();
            Thread.sleep(1000);

            page.locator("//span[@class='custom-next-month']").nth(1).click();
            page.locator("//span[@aria-label='August 6, 2025']").click();

            page.locator("//ul[@class='fixed d-flex justify-content-center mt-1 list-group']").click();
            Locator firstRow = page.locator("//div[@class='row']").first();

            Assert.assertTrue(firstRow.isVisible(), "❌ First row is not visible after rescheduling.");
            test.pass("✅ Prospect Reschedule completed successfully for: " + email);

            page.close();
        }
    }
}
