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

public class DelegateFunctionality extends BaseClass {

    @Test
    public void verifyDelegateFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Delegate Functionality | Data Driven");

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

            if (!"DelegateFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing test case: " + testCaseId + " with email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Initialized Playwright and launched browser.");

            page.navigate(prop.getProperty("stageUrl"));
            test.info("🌍 Navigated to Stage URL: " + prop.getProperty("stageUrl"));

            page.setViewportSize(1000, 768);
            test.info("🖥 Set viewport size to 1000x768.");

            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            // Login validations
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            test.info("✅ Sign-in content is visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");
            test.info("✅ Talk To Expert content is visible.");

            // Login actions
            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            test.info("✉ Entered email for login.");

            login.loginPasswordField().fill("Karthik@88");
            test.info("🔑 Entered password for login.");

            login.signInButton().click();
            test.info("🔓 Clicked Sign In button.");

            // Navigate to My Account > My Prospects
            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.hamburger().click();
            test.info("☰ Clicked Hamburger menu.");

            myaccount.myAccount().click();
            test.info("📁 Navigated to My Account.");

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();
            test.info("📋 Opened My Prospects section.");

            // Delegate process
            AdvertiserProspects delegate = new AdvertiserProspects(page);
            delegate.Jobs().click();
            test.info("💼 Clicked Jobs tab.");

            delegate.DelegateButton().first().click();
            test.info("👤 Clicked Delegate button on first job.");

            delegate.DelegateSaveButton().first().click();
            test.info("💾 Clicked Delegate Save button.");

            test.pass("✅ Delegate functionality completed for: " + email);
        }

        extent.flush();
        test.info("🧹 Flushed Extent report and test completed.");
    }
}
