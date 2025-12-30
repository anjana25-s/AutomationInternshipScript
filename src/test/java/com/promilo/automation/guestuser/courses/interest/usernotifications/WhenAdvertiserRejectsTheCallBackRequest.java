package com.promilo.automation.guestuser.courses.interest.usernotifications;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class WhenAdvertiserRejectsTheCallBackRequest extends BaseClass {

    @Test(
		      dependsOnMethods = {
				        "com.promilo.automation.guestuser.courses.interest.TalkToExperts.TaltoExpertIntrest"
				      } 
				    )
    public void CallbackOrTalktoExpertApprovefunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📊 Callback/Talk to Expert Approve Functionality");

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

                // Approve Callback/Talk to Expert
                AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
                test.info("💼 Clicked Course tab.");
                Thread.sleep(3000);
                approveFunctionality.CallbackOrTalktoExpert().click();
                test.info("📞 Selected Callback/Talk to Expert requests.");

                approveFunctionality.RejectButton().first().click();
                test.info("✅ Clicked Approve button for first request.");
                

                page.locator("//button[contains(text(),'Reject')]").click();

                test.pass("✅ Callback/Talk to Expert Approve action completed for: " + email);
                
                
                
                
    	        page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");


                MailsaurCredentials approveMail = new MailsaurCredentials(page);
                approveMail.MialsaurMail();
                approveMail.MailsaurContinue();
                approveMail.MailsaurPassword();
                approveMail.MailsaurLogin();
                
                Thread.sleep(2000);
                
                page.locator("//p[contains(text(),'Update on Your Course ')]").first().click();
                
                page.locator("//span[contains(text(),'Hi')]").textContent();
                page.locator("//span[contains(text(),'Hi')]").textContent();
                page.locator("//p[contains(text(),'this message finds you in good health. Thank you')]").textContent();
                page.locator("//p[contains(text(),'Unfortunately, ')]").textContent();
                
                page.locator("//p[contains(text(),'However, numerous other ')]").textContent();
                
               
               page.locator("//tbody/tr/td[@align='center']/table[1]").textContent();
               
            


            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + " | Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
