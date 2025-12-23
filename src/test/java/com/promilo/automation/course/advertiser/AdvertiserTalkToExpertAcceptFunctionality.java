package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class AdvertiserTalkToExpertAcceptFunctionality extends Baseclass {

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpExtent() {
        extent = ExtentManager.getInstance(); // Using your existing ExtentManager
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
        test = extent.createTest("📊 Callback/Talk to Expert Approve Functionality");

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
                AdvertiserHomepage Hamburger = new AdvertiserHomepage(page);
                Hamburger.hamburger().click();
                test.info("☰ Clicked Hamburger menu.");

                AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
                myaccount.myAccount().click();
                test.info("👤 Navigated to My Account.");

                // Go to My Prospect
                AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
                prospect.myProspect().click();
                test.info("📋 Navigated to My Prospect section.");

                // Approve Callback/Talk to Expert
                AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
                test.info("💼 Clicked Course tab.");
                Thread.sleep(3000);
                approveFunctionality.CallbackOrTalktoExpert().click();
                test.info("📞 Selected Callback/Talk to Expert requests.");

                approveFunctionality.ApproveButton().first().click();
                test.info("✅ Clicked Approve button for first request.");

                page.locator("//button[text()='Proceed']").click();
                test.info("▶ Clicked Proceed button to confirm approval.");
                
                
                myaccount.hamburger().click();
                myaccount.myAccount().click();
                myaccount.myBilling().click();
                
                
                String rowText = page.locator("//tbody/tr[1]").textContent().trim();
                System.out.println("Row: " + rowText);

                // Assertions based on the ACTUAL data printed
                Assert.assertTrue(rowText.contains("Call back"), 
                        "Row does not contain: Call back");

                Assert.assertTrue(rowText.contains("course automation december"), 
                        "Row does not contain: course automation december");

                Assert.assertTrue(rowText.contains("karthik"), 
                        "Row does not contain: karthik");

                Assert.assertTrue(rowText.contains("Completed"), 
                        "Row does not contain: Completed");

                Assert.assertTrue(rowText.contains("Prepaid"), 
                        "Row does not contain: Prepaid");

                // --- DATE VALIDATION ---
                // Convert "12 Dec 2025" format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                String today = LocalDate.now().format(formatter);

                Assert.assertTrue(rowText.contains(today),
                        "Row does not contain today's date: " + today);

                // FINAL PRINT CONFIRMATION ASSERTION
                Assert.assertTrue(!rowText.isEmpty(), "Row text is empty!");


                

                test.pass("✅ Callback/Talk to Expert Approve action completed for: " + email);

                // Open Mailsaur inbox
                page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

                MailsaurCredentials approveMail = new MailsaurCredentials(page);
                approveMail.MialsaurMail();
                approveMail.MailsaurContinue();
                approveMail.MailsaurPassword();
                approveMail.MailsaurLogin();

                page.locator("//p[contains(text(),'Talk to Expert Request Accepted for ')]").first().click();

                page.locator("//span[contains(text(),'Dear ')]").textContent();
                page.locator("//p[contains(text(),'This is regarding your')]").textContent();
                page.locator("//span[contains(text(),'been deducted from your ')]").textContent();
                page.locator("//p[contains(text(),'Click the link below to view')]").textContent();
                Locator table = page.locator("(//table)[15]");
                table.textContent();

                page.locator("//span[contains(text(),'Prospect')]").click();
                
                
                
                //User side validation after accepting
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
                   loginPage1.loginMailPhone().fill(Baseclass.generatedPhone); // use the generated email
                   loginPage1.loginWithOtp().click();
                   loginPage1.otpField().fill("9999");
                   loginPage1.loginButton().click();
                   
                   
                   TalkToExpertPage notificationValidation= new TalkToExpertPage(userPage);
                   notificationValidation.notificationIcon().nth(1).click();
                   String expectedNotification=notificationValidation.talktoExpertAcceptNotification().textContent().trim();
                   assertEquals(expectedNotification, "It's Official! Your Talk with an Expert at BTWIN is Confirmed");
                   
                
                
                
                
                
                
                
                
                
                
                
                
                

            } catch (Exception e) {
                try {
                    // Capture screenshot
                    String screenshotPath = Paths.get(System.getProperty("user.dir"), "ExtentReports", "Screenshots", "CallbackApproveError_" + i + ".png").toString();
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
