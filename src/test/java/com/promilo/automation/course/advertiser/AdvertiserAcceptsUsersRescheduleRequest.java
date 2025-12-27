package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.courses.intrestspages.FreeVideoCounsellingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AdvertiserAcceptsUsersRescheduleRequest extends BaseClass {

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
            "com.promilo.automation.guestuser.courses.interest.FreeVideoCounsellingReschedule.freeVideoCounsellingFlow"
        }
    )
    public void CallbackOrTalktoExpertApprovefunctionalityTest() throws InterruptedException, IOException {

        test = extent.createTest("📊 Callback/Talk to Expert Accept Functionality");

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

            if (!"CallbackOrTalkToExpertApprove".equalsIgnoreCase(keyword)) continue;

            try {
                Page page = initializePlaywright();
                test.info("🌐 Browser Launched");

                page.navigate(prop.getProperty("stageUrl"));
                test.info("🌍 Navigated to: " + prop.getProperty("stageUrl"));

                page.setViewportSize(1000, 768);
                Thread.sleep(5000);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                Assert.assertTrue(login.signInContent().isVisible(), "❌ SignIn text not visible");
                test.pass("✔ Sign-in content visible");

                Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk to Expert not visible");
                test.pass("✔ Talk To Expert text visible");

                // Login
                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                login.loginPasswordField().fill("Karthik@88");
                login.signInButton().click();
                test.pass("🔐 Login successful");

                // Navigate to My Account → Prospect
                AdvertiserHomepage home = new AdvertiserHomepage(page);
                home.hamburger().click();
                home.myAccount().click();

                AdverstiserMyaccount account = new AdverstiserMyaccount(page);
                account.myProspect().click();
                test.info("📂 Navigated to My Prospect");

                AdvertiserProspects prospect = new AdvertiserProspects(page);

                // Open Reschedule Request
                page.locator("//span[text()='Reschedule Request']").first().click();
                test.info("📩 Viewed Reschedule Request");
                
                
                String rescheduleMeeting=page.locator("[class='modal-content']").textContent().trim();
                System.out.println("reschedule Meeting pop up   "+rescheduleMeeting);
                
                String rescheduleSlotDetails= page.locator("[class='rescheduledSlots-time w-100 font-12 text-center mb-50 active']").textContent().trim();
                System.out.println("reschedule SlotDetails "+rescheduleSlotDetails);
                
                
                
                
                // Accept Request
                page.locator("//button[text()='Accept Request']").click();
                test.pass("✅ Accepted Reschedule Request");
                
                
                
                
                
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
                   loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
                   loginPage1.loginWithOtp().click();
                   loginPage1.otpField().fill("9999");
                   loginPage1.loginButton().click();
                   

                   
                   
                   
                   FreeVideoCounsellingPage freeVideoCounselling= new FreeVideoCounsellingPage(userPage);
                   userPage.waitForTimeout(3000);
                   freeVideoCounselling.myInterestTab().click();
                   
                   userPage.waitForTimeout(2000);
                   userPage.locator("//div[text()='Accepted']").nth(1).click();
                   
                   
                   userPage.waitForTimeout(2000);
                   String statusTagValidation=freeVideoCounselling.statusTag().textContent().trim();
                   System.out.println(statusTagValidation);
                   
                   
                   String rescheduleDate=freeVideoCounselling.availableDate().textContent().trim();
                   
                
                
                
                
                page.close();
            } catch (Exception e) {
                test.fail("❌ Exception: " + e.getMessage());
                throw e;
            }
        }
    }
}
