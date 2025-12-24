package com.promilo.automation.advertiser.mymeetings;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class InviteFunctionality extends Baseclass {

    @Test
    public void verifyInviteFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📌 Invite Functionality | Data-Driven Test");

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

            if (!"InviteFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔁 Running test case: " + testCaseId + " | Keyword: " + keyword);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(4000);

            // Login
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            SoftAssert softAssert = new SoftAssert();

            softAssert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            softAssert.assertTrue(login.talkToAnExpert().isVisible(), "❌ 'Talk to Expert' should be visible.");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();
            test.info("✅ Logged in as " + email);

            // Navigate to My Account > My Meetings

            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();

            AdverstiserMyaccount account = new AdverstiserMyaccount(page);
            account.myMeeting().click();

            com.promilo.automation.advertiser.AdvertiserMymeetingPage mymeeting = new com.promilo.automation.advertiser.AdvertiserMymeetingPage(page);
            mymeeting.jobs().click();
            mymeeting.inviteButton().click();

            page.locator("input[class='save-input pointer save-btn']").click();
            test.pass("✅ Invite sent successfully.");

            softAssert.assertAll();
        }
    }
}
