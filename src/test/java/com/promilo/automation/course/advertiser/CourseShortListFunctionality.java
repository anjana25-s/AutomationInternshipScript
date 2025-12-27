package com.promilo.automation.course.advertiser;

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
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class CourseShortListFunctionality extends BaseClass {

    @Test
    public void ShortListFunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Course Shortlist Functionality | Data Driven");

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
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);
            test.info("🌍 Navigated to Stage URL and viewport set");

            try {
                // Step 1: Login
                AdvertiserLoginPage login = new AdvertiserLoginPage(page);
                Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
                Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");

                login.loginMailField().fill("adv@yopmail.com");
                login.loginPasswordField().fill("devuttan2023");
                login.signInButton().click();
                test.pass("✅ Advertiser logged in successfully");

                // Step 2: Navigate to My Account > Prospects
                AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
                myaccount.hamburger().click();
                myaccount.myAccount().click();
                test.info("➡️ Navigated to My Account section");

                AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
                prospect.myProspect().click();
                test.info("📌 Clicked on My Prospects");

                AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
                Thread.sleep(2000);
                test.info("📝 Navigated to Course tab");

                // Step 3: Click Shortlist
                approveFunctionality.ShortlistButton().first().click();
                test.pass("✅ Shortlisted first candidate successfully for TestCaseID: " + testCaseId);

            } catch (Exception e) {
                test.fail("❌ Test failed for TestCaseID: " + testCaseId + " | Error: " + e.getMessage());
                throw e;
            } finally {
                page.close();
                test.info("🔒 Browser closed for TestCaseID: " + testCaseId);
            }
        }

        extent.flush(); // Important: flush extent report at the end
    }
}
