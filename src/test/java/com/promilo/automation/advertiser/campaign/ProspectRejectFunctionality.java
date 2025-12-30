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
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class ProspectRejectFunctionality extends BaseClass {

    @Test
    public void ProspectRejectFunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Prospect Reject Functionality | Data Driven");

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

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) continue;

            test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Playwright page initialized");

            page.navigate(prop.getProperty("stageUrl"));
            test.info("🌍 Navigated to URL: " + prop.getProperty("stageUrl"));

            page.setViewportSize(1000, 768);
            Thread.sleep(3000);
            test.info("🖥 Viewport set to 1000x768 and waited 3 seconds");

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            test.info("✅ Sign-in content is visible");

            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");
            test.info("✅ Talk To Expert is visible");

            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            test.info("✉ Entered email");

            login.loginPasswordField().fill("Karthik@88");
            test.info("🔑 Entered password");

            login.signInButton().click();
            test.info("🔓 Clicked Sign-in button");

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            test.info("☰ Clicked main menu toggle");

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();
            test.info("📁 Clicked My Account");

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();
            test.info("📋 Clicked My Prospect");

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            approveFunctionality.Jobs().click();
            test.info("💼 Clicked Jobs tab");

            Thread.sleep(3000);
            approveFunctionality.RejectButton().first().click();
            test.info("❌ Clicked Reject button for the first prospect");

            test.pass("✅ Reject action completed successfully for row " + i);
        }

        extent.flush();
        test.info("🧹 Extent report flushed and test completed.");
    }
}
