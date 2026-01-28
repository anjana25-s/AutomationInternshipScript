package com.promilo.automation.guestuser.courses.interest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.courses.intrestspages.FreeVideoCounsellingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CampusVisitRejectRescheduleRequest extends BaseClass {

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
            "com.promilo.automation.guestuser.courses.interest.GuestUserCampusVisit.GuestUserCampusVisitTest"
        }
    )
    public void CampusVisitRejectRescheduleRequestTest()
            throws InterruptedException, IOException {

        test = extent.createTest("📊 Callback / Talk To Expert Approve Functionality");

        String excelPath = Paths.get(
                System.getProperty("user.dir"),
                "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx"
        ).toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) {
                break;
            }
            rowCount++;
        }

        for (int i = 1; i <= rowCount; i++) {

            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 2);
            String password = excel.getCellData(i, 3);

            if (!"CallbackOrTalkToExpertApprove".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("Executing Row : " + i + " | TestCaseID : " + testCaseId);

            try {
                Page page = initializePlaywright();
                test.info("Browser launched successfully");

                page.navigate(prop.getProperty("stageUrl"));
                page.setViewportSize(1000, 768);
                Thread.sleep(5000);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                // UI Assertions
                Assert.assertTrue(login.signInContent().isVisible(),
                        "Sign-in content is not visible");
                Assert.assertTrue(login.talkToAnExpert().isVisible(),
                        "Talk To Expert content should be visible");

                // Login
                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                login.loginPasswordField().fill("Karthik@88");
                login.signInButton().click();

                test.info("Advertiser logged in successfully");

                // Hamburger → My Account
                AdvertiserHomepage home = new AdvertiserHomepage(page);
                home.hamburger().click();
                home.myAccount().click();
                page.locator("//span[text()='Visits Planned']").click();
                page.locator("//a[text()='All']").click();
                
                
                
                
                
                
                
                page.locator("//div[@class='pointer mx-1 d-flex my-auto cards-features-btn-alignment']//following::span[text()='Reschedule']").first().click();
                page.locator("[class='font-14 label-text text-dark-gray ms-1 ']").first().click();
                page.locator("//button[text()='Reschedule']").click();
                
                
                page.waitForTimeout(3000);
                page.locator("//span[@class='flatpickr-day']").first().click();
                test.info("📅 Selected date for reschedule");
                
               

                // Select two slots for reschedule
                page.locator("li.time-slot-box.list-group-item").first().click();
                page.locator("li.time-slot-box.list-group-item").nth(1).click();


                page.locator("//button[text()='Submit']").click();
                
                
                String popUpText=page.locator("[class='modal-content']").textContent().trim();
                System.out.println(popUpText);
                
                
                
                
                
                
                String successPopUp=page.locator("[class='font-14 text-center']").textContent().trim();
                assertEquals(successPopUp, "Once the user confirms the proposed timings, campus visit will be confirmed.");
                                
                String rescheduleStatus=page.locator("//span[text()='Reschedule Requested']").first().textContent().trim();
                assertEquals(rescheduleStatus, "Reschedule Requested");
                
                
                String text2= page.locator("//div[text()='Awaiting Acceptance']").first().textContent().trim();
                assertEquals(text2, "Awaiting Acceptance");
                
                
                
                
                
                
                
                
                
                
                
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
                   
                   
                   userPage.locator("//span[text()='My Visits']").click();
                   userPage.waitForTimeout(5000);
                   userPage.locator("//div[text()='Reschedule Requests']").click();
                   userPage.waitForTimeout(3000);
                   userPage.locator("img.rescheduled-notification-icon").click();
                   userPage.waitForTimeout(3000);

                   userPage.locator(
                           "//div[@class='rescheduledSlots-time w-100 font-12 text-center mb-50 ']")
                           .first().click();

                   
                   
                   
                   
                   userPage.waitForTimeout(3000);
                   userPage.locator("[class='cancel-btn-rescheduled']").click();
                   
                   
                   userPage.locator("[class='accept-btn-rescheduled']").click();
                   userPage.locator("[class='sub-heading pt-1']").click();
                   userPage.locator("//img[@alt='Close']").click();
                   
                   
                   
                   
                   
                   String meetingDate = freeVideoCounselling.meetingDate().first().textContent().trim();
                   Pattern p = Pattern.compile("(\\d{1,2})");
                   Matcher m = p.matcher(meetingDate);
                   String displayedDayStr = "";
                   if (m.find()) displayedDayStr = m.group(1);
                   else Assert.fail("❌ Could not extract day from Meeting Date: " + meetingDate);
                   int displayedDay = Integer.parseInt(displayedDayStr);
                   int storedDay = Integer.parseInt(BaseClass.selectedDate.trim());
                   Assert.assertEquals(displayedDay, storedDay);

                   String meetingTime = freeVideoCounselling.meetingTime().first().textContent().trim();
                   Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2}\\s?[AP]M)");
                   Matcher timeMatcher = timePattern.matcher(meetingTime);
                   String displayedTime = "";
                   if (timeMatcher.find()) displayedTime = timeMatcher.group(1).replaceAll("\\s+", " ").trim();
                   String storedTime = BaseClass.selectedTime.replaceAll("\\s+", " ").trim();
                   storedTime = storedTime.replaceFirst("^0", "");
                   displayedTime = displayedTime.replaceFirst("^0", "");
                   Assert.assertEquals(displayedTime, storedTime);


                   


                
                
                

            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
                
                
                
                
                
            }
        }
    }
}
