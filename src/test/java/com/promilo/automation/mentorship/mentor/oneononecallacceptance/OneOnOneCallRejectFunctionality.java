package com.promilo.automation.mentorship.mentor.oneononecallacceptance;

import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentorship.mentornotifications.MentroOneOnOneCall;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class OneOnOneCallRejectFunctionality extends BaseClass {

    String emailToLogin = BaseClass.generatedEmail;
    String phoneToLogin = BaseClass.generatedPhone;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpExtent() {
        extent = ExtentManager.getInstance();
    }

    @AfterClass
    public void tearDownExtent() {
        if (extent != null) {
            extent.flush();
        }
    }

    @Test(
        dependsOnMethods = {
            "com.promilo.automation.mentorship.mentee.intrests.GetMentorCall.mentorshipShortListFunctionalityTest"
        }
    )
    public void RejectVideoServiceRequestTest() throws Exception {

        test = extent.createTest("❌ Reject video service - Negative Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);
        test.info("🌐 Navigated to application URL.");

        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
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
            if (!"AddEmployment".equalsIgnoreCase(keyword)) continue;

            try {
                test.info("➡️ Starting execution for row " + i);

                // Mentor login
                LandingPage landingPage = new LandingPage(page);
                try {
                    landingPage.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                landingPage.clickLoginButton();
                test.info("🔑 Navigating to Login Page.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill("92466825@qtvjnqv9.mailosaur.net");
                loginPage.passwordField().fill("Karthik@88");
                loginPage.loginButton().click();
                test.info("✅ Logged in with registered credentials.");

                MyResumePage mentorMenu = new MyResumePage(page);
                mentorMenu.Mystuff().click();
                mentorMenu.MyAccount().click();

                // Reject One-on-One Call request
                MyAcceptance acceptRequest = new MyAcceptance(page);
                acceptRequest.myAcceptance().click();
                Thread.sleep(2000); // temporary wait for modal
                Locator cancelButton = acceptRequest.oneOnoneCallRejectButton();
                cancelButton.scrollIntoViewIfNeeded();
                cancelButton.click(new Locator.ClickOptions().setForce(true));
                acceptRequest.proceedButton().click();

                // Advertiser login (new context)
                Browser actualBrowser = browser.get();
                BrowserContext advertiserContext = actualBrowser.newContext();
                Page advertiserPage = advertiserContext.newPage();
                advertiserPage.navigate(prop.getProperty("url"));

                Thread.sleep(3000);

                LandingPage advertiserLanding = new LandingPage(advertiserPage);
                try {
                    advertiserLanding.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }
                advertiserLanding.clickLoginButton();

                LoginPage advertiserLogin = new LoginPage(advertiserPage);
                if (BaseClass.generatedPhone == null || BaseClass.generatedPhone.isEmpty()) {
                    throw new RuntimeException("Generated phone/email is null or empty.");
                }
                advertiserLogin.loginMailPhone().fill(BaseClass.generatedPhone);
                advertiserLogin.loginWithOtp().click();
                advertiserLogin.otpField().fill("9999");
                advertiserLogin.loginButton().click();

                advertiserPage.locator("//span[text()='My Interest']").click();
                advertiserPage.locator("//div[text()='My Preference']").click();

                // Mailosaur validation
                page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

                MailsaurCredentials mailsaur = new MailsaurCredentials(page);
                mailsaur.MialsaurMail();
                mailsaur.MailsaurContinue();
                mailsaur.MailsaurPassword();
                mailsaur.MailsaurLogin();

                MentroOneOnOneCall mailValidation = new MentroOneOnOneCall(page);
                mailValidation.declinedNotification().first().click();
                System.out.println(mailValidation.dearText().textContent());
                System.out.println(mailValidation.rejectedDeclineText().textContent());
                mailValidation.rejectedButton().isVisible();
                mailValidation.reviewRequestButton().click();

            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
