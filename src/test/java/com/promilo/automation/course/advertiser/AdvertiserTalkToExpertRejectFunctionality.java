package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.courses.intrestspages.TalkToExpertPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class AdvertiserTalkToExpertRejectFunctionality extends BaseClass {

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpExtent() {
        extent = ExtentManager.getInstance(); // Initialize ExtentReports
    }

    @AfterClass
    public void tearDownExtent() {
        if (extent != null) {
            extent.flush();
        }
    }

    @Test(
        dependsOnMethods = {
            "com.promilo.automation.guestuser.courses.interest.TalkToExperts.TaltoExpertIntrest"
        } 
    )
    public void CallbackOrTalktoExpertApprovefunctionalityTest() throws InterruptedException, IOException {
        test = extent.createTest("📊 Callback/Talk to Expert Reject Functionality");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 2);
            String password = excel.getCellData(i, 3);

            if (!"CallbackOrTalkToExpertApprove".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔹 Executing row " + i + " | TestCaseID: " + testCaseId + " | Email: " + email);

            try {
                Page page = initializePlaywright();
                test.info("🌐 Playwright initialized and browser launched.");

                page.navigate(prop.getProperty("stageUrl"));
                test.info("🌍 Navigated to URL: " + prop.getProperty("stageUrl"));

                page.setViewportSize(1000, 768);
                test.info("🖥 Set viewport size to 1000x768.");

                Thread.sleep(5000);
                test.info("⏱ Waiting for 5 seconds for page load.");

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                // UI assertions
                Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
                test.info("✅ Sign-in content is visible.");
                Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");
                test.info("✅ Talk To Expert content is visible.");

                // Login using Excel data
                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                test.info("✉ Filled login email.");
                login.loginPasswordField().fill("Karthik@88");
                test.info("🔑 Filled login password.");
                login.signInButton().click();
                test.info("🔓 Clicked Sign In button.");

                // Navigate to My Account
                AdvertiserHomepage Hamburger= new AdvertiserHomepage(page);
                Hamburger.hamburger().click();
                test.info("☰ Clicked Hamburger menu.");

                AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
                myaccount.myAccount().click();
                test.info("👤 Navigated to My Account.");

                // Go to My Prospect
                AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
                prospect.myProspect().click();
                test.info("📋 Navigated to My Prospect section.");

                // Reject Callback/Talk to Expert
                AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
                test.info("💼 Clicked Course tab.");
                Thread.sleep(3000);
                approveFunctionality.CallbackOrTalktoExpert().click();
                test.info("📞 Selected Callback/Talk to Expert requests.");

                approveFunctionality.RejectButton().first().click();
                test.info("✅ Clicked Reject button for first request.");

                page.locator("//button[contains(text(),'Reject')]").click();
                
                
                
                
              //User side validation after Rejecting
                Browser actualBrowser = browser.get();
                BrowserContext advertiserContext = actualBrowser.newContext();
                Page userPage = advertiserContext.newPage();

                // Now use Mentor Page as usual
                userPage.navigate(prop.getProperty("url"));

                
                MayBeLaterPopUp mayBeLaterPopUp2 = new MayBeLaterPopUp(userPage);
                try {
              	  mayBeLaterPopUp2.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                mayBeLaterPopUp2.clickLoginButton();              


                
             
                   LoginPage loginPage1 = new LoginPage(userPage);
                   loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
                   loginPage1.loginWithOtp().click();
                   loginPage1.otpField().fill("9999");
                   loginPage1.loginButton().click();
                   

                   TalkToExpertPage notificationValidation= new TalkToExpertPage(userPage);
                   notificationValidation.notificationIcon().nth(1).click();
                   String expectedNotification=notificationValidation.talkToExpertRejectNotification().textContent().trim();
                   assertEquals(expectedNotification, "Update on Your Course promilo automation Course Application - BTWIN, Ahmedabad");
                   
                
                
                
                

                test.pass("✅ Callback/Talk to Expert Reject action completed for: " + email);

                page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

                MailsaurCredentials approveMail = new MailsaurCredentials(page);
                approveMail.MialsaurMail();
                approveMail.MailsaurContinue();
                approveMail.MailsaurPassword();
                approveMail.MailsaurLogin();

                Thread.sleep(2000);

                page.locator("//p[contains(text(),'Rejected for ')]").first().click();

                page.locator("//span[contains(text(),'Dear ')]").textContent();
                test.info("validated the Dear User 'name'  Text");

                
                page.locator("//p[contains(text(),'This is regarding your')]").textContent();
                test.info("validated the This is regarding your Text");

                page.locator("//p[contains(text(),'We wanted to inform you that')]").textContent();
                test.info("We wanted to inform you that Text");

                page.locator("//p[contains(text(),'Please click the link below to')]").textContent();
                test.info("Validated the Please click the link below Text");

                Locator table= page.locator("(//table)[15]");
                table.textContent();      
                test.info("Fetched the Card  Info ");


                Thread.sleep(2000);

                page.locator("//span[contains(text(),'Prospect')]").click();
                
                test.info("Clicked on Prospect in advertiser mail");
                
                test.pass("✅ Callback/Talk to Expert Reject action completed for: " + email);


            } catch (Exception e) {
                try {
                    // Capture screenshot on failure
                    String screenshotPath = Paths.get(System.getProperty("user.dir"), "ExtentReports", "Screenshots", "CallbackRejectError_" + i + ".png").toString();
                    Files.createDirectories(Paths.get(System.getProperty("user.dir"), "ExtentReports", "Screenshots"));
                    initializePlaywright().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));

                    test.fail("❌ Test failed for row " + i + " | Error: " + e.getMessage())
                        .addScreenCaptureFromPath(screenshotPath);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                e.printStackTrace();
            }
        }
    }
}
