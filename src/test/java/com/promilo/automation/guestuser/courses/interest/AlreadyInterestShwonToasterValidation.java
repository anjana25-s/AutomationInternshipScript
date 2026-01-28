package com.promilo.automation.guestuser.courses.interest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.courses.intrestspages.FreeVideoCounsellingPage;
import com.promilo.automation.mentorship.datavalidation.objects.MentorshipBookMeetingPageObjects;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class AlreadyInterestShwonToasterValidation extends BaseClass {

    
    
    
    @Test(
		      dependsOnMethods = {
		        "com.promilo.automation.guestuser.courses.interest.FreeVideoCounsellingTest.freeVideoCounsellingFlow"
		      } 
		    )
    public void AlreadyInterestShwonToasterValidationTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Prospect Reject Functionality");

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

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) continue;

            test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Playwright page initialized");

            page.navigate(prop.getProperty("stageUrl"));
            test.info("🌍 Navigated to URL: " + prop.getProperty("stageUrl"));

            page.setViewportSize(1000, 768);
            Thread.sleep(3000);
            test.info("🖥 Viewport set to 1000x768 and waited 3 seconds");

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            test.info("✅ Sign-in content is visible");

            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");
            test.info("✅ Talk To Expert is visible");

            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            test.info("✉ Entered email");

            login.loginPasswordField().fill("Karthik@88");
            test.info("🔑 Entered password");

            login.signInButton().click();
            test.info("🔓 Clicked Sign-in button");

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            test.info("☰ Clicked main menu toggle");

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();
            test.info("📁 Clicked My Account");

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();
            test.info("📋 Clicked My Prospect");

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            test.info("💼 Clicked Course tab");

            Thread.sleep(3000);
            approveFunctionality.RejectButton().first().click();
            test.info("❌ Clicked Reject button for the first prospect");
            
            
            page.locator("[class='btn done-btn w-100']").click();
            
            
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
               page.waitForTimeout(4000);
               freeVideoCounselling.myInterestTab().click();
               
               
               String campaignName=freeVideoCounselling.campaignName().textContent().trim();
               assertEquals(campaignName, "BTWIN");
               
               
               String location= freeVideoCounselling.location().textContent().trim();
               assertEquals(location, "Ahmedabad");
               
               String productTitle= freeVideoCounselling.productTitle().textContent().trim();
               assertEquals(productTitle, "Course promilo automation");
               
               
               
               
               
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
               
               
               
               
               userPage.locator("//a[@class='card-content-header-text fw-bolder mb-0 text-wrap pointer text-truncate']").click();
               
               
               
               
               
               MentorshipBookMeetingPageObjects po = new MentorshipBookMeetingPageObjects(userPage);

               freeVideoCounselling.freeVideoCounsellingBtn().click();


               

               Locator dateElement = po.availableDate();
               dateElement.click();
               BaseClass.selectedDate = dateElement.innerText().split("\\s+")[0].trim();

               Locator timeElement = po.firstTimeSlot();
               timeElement.click();
               BaseClass.selectedTime = timeElement.innerText();

              // page.locator("//button[text()='Next']").click();
               test.info("➡️ Clicked Next button");


               userPage.locator("//button[text()='Next']").click();
               test.info("➡️ Clicked Next button");

               userPage.locator("//button[text()='Submit']").nth(1).click();
               test.info("📨 Clicked Submit button");            
               

               
               String toasterValidation=userPage.locator("//div[text()='You already shown interest on this campaign.']").textContent().trim();
               assertEquals(toasterValidation, "You already shown interest on this campaign.");

            
            
            
            



        } 

        extent.flush();
        test.info("🧹 Extent report flushed and test completed.");
    }
}
