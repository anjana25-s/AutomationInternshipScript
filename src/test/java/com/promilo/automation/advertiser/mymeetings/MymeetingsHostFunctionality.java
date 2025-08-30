package com.promilo.automation.advertiser.mymeetings;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.pageobjects.Mymeetings.MymeetingPage;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class MymeetingsHostFunctionality extends Baseclass {

    @Test
    public void verifyMyMeetingHostFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📌 MyMeetings Host Functionality | Data-Driven Test");

        // Setup Excel
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

            if (!"MyMeetingHost".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("▶️ Executing TestCaseID: " + testCaseId + " with Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);

            Thread.sleep(3000);

            // Login flow
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk To Expert should be visible.");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();


            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();

            AdverstiserMyaccount account = new AdverstiserMyaccount(page);
            account.myMeeting().click();

            com.promilo.automation.advertiser.AdvertiserMymeetingPage myMeeting = new com.promilo.automation.advertiser.AdvertiserMymeetingPage(page);

            myMeeting.jobs().click();
            myMeeting.hostButton().click();
            myMeeting.audioMuteButton().click();
            myMeeting.videoMuteButton().click();
            myMeeting.joinMeetingButton().click();
            myMeeting.riseHandButton().click();
            myMeeting.chatButton().click();
            myMeeting.chatTextfield().fill("Hi");
            myMeeting.chatSendButton().click();
            myMeeting.chatExitButton().click();
            myMeeting.audioMuteButton().click();

            test.pass("✅ MyMeeting Host flow executed successfully for email: " + email);
        }
    }
}
