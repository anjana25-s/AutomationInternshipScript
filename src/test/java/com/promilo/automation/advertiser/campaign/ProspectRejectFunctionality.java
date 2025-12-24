package com.promilo.automation.advertiser.campaign;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ProspectRejectFunctionality extends BaseClass {

    @Test
    public void verifyProspectRejectFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Prospect Reject Functionality | Data-Driven Test");

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

            if (!"ProspectReject".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("▶ Running row " + i + " | TestCaseID: " + testCaseId);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);

            Thread.sleep(5000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            // Navigate to My Account
            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            approveFunctionality.Jobs().click();

            Thread.sleep(3000);
            approveFunctionality.RejectButton().first().click();

            test.pass("✅ Reject action completed successfully for row " + i);
        }
    }
}
