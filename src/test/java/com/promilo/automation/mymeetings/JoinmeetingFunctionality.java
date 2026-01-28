package com.promilo.automation.mymeetings;

import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.mymeetings.MymeetingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class JoinmeetingFunctionality extends BaseClass {

    @Test
    public void joinMeetingWithDataDrivenLogin() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📅 Join Meeting Functionality | Data Driven Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        SoftAssert softAssert = new SoftAssert();

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
            String keyword = excel.getCellData(i, 1);
            if (!"JoinMeeting".equalsIgnoreCase(keyword)) continue;

            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            try {
                // Landing page interaction
                LandingPage landingPage = new LandingPage(page);
                landingPage.getPopup().click();
                landingPage.clickLoginButton();

                // Login
                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(email);
                loginPage.passwordField().fill(password);
                loginPage.loginButton().click();

                // Navigate to My Meetings
                page.locator("//span[normalize-space()='My Meetings']").click();
                page.locator("//div[text()='All']").click();
                page.locator("//span[text()='Join Now']").click();
                page.locator("//button[text()='Join now']").click();

                // Handle Join popup
                page.onDialog((Dialog dialog) -> {
                    System.out.println("Dialog message: " + dialog.message());
                    dialog.accept();
                });

                // Meeting interactions
                MymeetingPage mymeeting = new MymeetingPage(page);
                mymeeting.audioMuteButton().click();
                mymeeting.videoTogglebutton().click();
                mymeeting.MeetingJoinbutton().click();

                mymeeting.RisehandButton().click();
                mymeeting.chatButton().click();
                mymeeting.chatTextfield().fill("Hi");
                mymeeting.chatSendbutton().click();
                mymeeting.chatExitbutton().click();
                mymeeting.audioMuteButton().click();

                test.pass("✅ Meeting joined and actions performed successfully for: " + email);

            } catch (Exception e) {
                test.fail("❌ Exception occurred while joining meeting for " + email + ": " + e.getMessage());
            }
        }

        softAssert.assertAll();
    }
}
