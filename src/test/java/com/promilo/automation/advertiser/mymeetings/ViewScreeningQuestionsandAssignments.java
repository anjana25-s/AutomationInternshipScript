package com.promilo.automation.advertiser.mymeetings;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserMymeetingPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class ViewScreeningQuestionsandAssignments extends Baseclass {

    @Test
    public void verifyBasicDetailsFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📄 View Screening Questions and Assignments | Data-Driven Test");

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
            String email = excel.getCellData(i, 7);      // Email
            String password = excel.getCellData(i, 6);   // Password

            if (!"ViewScreeningAssignment".equalsIgnoreCase(keyword)) continue;

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

            // Navigate to My Account > My Meetings
            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myMeeting().click();

            AdvertiserProspects prospect1 = new AdvertiserProspects(page);
            AdvertiserMymeetingPage Mymeeting = new AdvertiserMymeetingPage(page);

            Thread.sleep(3000);
            Mymeeting.jobs().click();
            prospect1.ThreedDotOption().first().click();
            Mymeeting.screeningQuestions().click();

            page.locator("//img[@alt='Add New']").click();
            Thread.sleep(2000);

            prospect1.ThreedDotOption().first().click();
            Mymeeting.assignment().click();

            Thread.sleep(2000);
            Locator submittedAssignment = page.locator("//span[text()='Submitted Assignment']");
            Assert.assertTrue(submittedAssignment.isVisible(), "❌ 'Submitted Assignment' element is not visible.");

            test.pass("✅ Test passed for user: " + email);

            page.close();
        }
    }
}
