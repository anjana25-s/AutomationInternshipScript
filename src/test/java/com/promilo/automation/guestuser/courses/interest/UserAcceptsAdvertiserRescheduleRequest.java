package com.promilo.automation.guestuser.courses.interest;

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
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
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

public class UserAcceptsAdvertiserRescheduleRequest extends BaseClass {

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
            "com.promilo.automation.guestuser.courses.interest.FreeVideoCounsellingTest.freeVideoCounsellingFlow"
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
                page.navigate(prop.getProperty("stageUrl"));
                page.setViewportSize(1000, 768);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                page.waitForTimeout(3000);
                // Assertions
                Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
                Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk To Expert content should be visible");

                // Login using Excel credentials
                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                login.loginPasswordField().fill("Karthik@88");
                login.signInButton().click();

                AdvertiserHomepage homepage = new AdvertiserHomepage(page);
                homepage.hamburger().click();
                homepage.myAccount().click();

                AdverstiserMyaccount myAccount = new AdverstiserMyaccount(page);
                myAccount.myProspect().click();

                

                // Reschedule first request
                Locator rescheduleBtn = page.locator("//span[normalize-space(text())='Reschedule']").first();
                rescheduleBtn.click(new Locator.ClickOptions().setForce(true));

                // Select today's date dynamically
                page.locator("//span[contains(@class,'flatpickr-day') and not(contains(@class,'prevMonthDay'))]").first().click();

                // Select first two available time slots
                page.locator("li.time-slot-box.list-group-item").first().click();
                page.locator("li.time-slot-box.list-group-item").nth(1).click();

                page.locator("//button[text()='Submit']").click();

                /*// Validate success toaster
                Locator toaster = page.getByText("Your rescheduled request has been sent to the user.Once the user confirms the");
                Assert.assertTrue(toaster.isVisible(), "❌ Reschedule success message not visible");

                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Done")).click();

                test.pass("✅ Reschedule request sent successfully for row " + i);
                
                */
                
                
                
                
                
                
                
                
                
                
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
                   userPage.pause();
                   
                   
                   
                   userPage.waitForTimeout(3000);
                   userPage.locator("img.rescheduled-notification-icon").click();
                   userPage.waitForTimeout(3000);

                   userPage.locator(
                           "//div[@class='rescheduledSlots-time w-100 font-12 text-center mb-50 ']")
                           .first().click();

                   
                   userPage.waitForTimeout(3000);
                   userPage.locator("//button[text()='Accept Request']").click();
                   

            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + " due to: " + e.getMessage());
            }
        }
    }
}
