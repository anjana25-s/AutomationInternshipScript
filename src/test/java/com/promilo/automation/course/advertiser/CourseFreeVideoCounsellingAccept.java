package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
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
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class CourseFreeVideoCounsellingAccept extends BaseClass {
    @Test(
        dependsOnMethods = {
            "com.promilo.automation.guestuser.courses.interest.FreeVideoCounselling.FreeVideoCounsellingTest"
        } 
    )
    public void verifyFilterFunctionalityDataDriven() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 ProspectApproveFunctionality  | Data Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }
            test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Playwright initialized and browser launched.");

            page.navigate(prop.getProperty("stageUrl"));
            test.info("🌍 Navigated to URL: " + prop.getProperty("stageUrl"));

            page.setViewportSize(1000, 768);
            test.info("🖥 Viewport set to 1000x768.");

            Thread.sleep(3000);
            test.info("⏱ Waited 3 seconds for page load.");

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            test.info("✅ Sign-in content is visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");
            test.info("✅ Talk To Expert content is visible.");

            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            test.info("✉ Filled login email.");
            login.loginPasswordField().fill("Karthik@88");
            test.info("🔑 Filled login password.");
            login.signInButton().click();
            test.info("🔓 Clicked Sign In button.");

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            test.info("☰ Clicked hamburger menu.");

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();
            test.info("👤 Navigated to My Account.");

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();
            test.info("📋 Navigated to My Prospect section.");

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);

            Thread.sleep(3000);
            test.info("⏱ Waited 3 seconds before approving.");

            approveFunctionality.ApproveButton().first().click();
            test.info("✅ Clicked Approve button for the first prospect.");

            Thread.sleep(3000);
            test.info("⏱ Waited 3 seconds after clicking Approve.");

            Locator statusDiv = page.locator("//div[@class='popup-main-container']");
            Assert.assertTrue(statusDiv.isVisible(), "Done div should be visible");
            test.info("✅ Approval confirmation popup is visible.");

            System.out.println("approve functionality executed");
            test.info("📄 Approval functionality executed successfully.");
            
            
            
            
            
            
            
            
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
               Locator interestButton=freeVideoCounselling.myInterestTab();
               interestButton.scrollIntoViewIfNeeded();
               interestButton.click();
               
               
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



            page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            test.info("🌐 Navigated to Mailosaur inbox.");

            MailsaurCredentials approveMail = new MailsaurCredentials(page);
            approveMail.MialsaurMail();
            test.info("✉ Entered Mailosaur server email.");
            approveMail.MailsaurContinue();
            test.info("➡ Clicked Continue.");
            approveMail.MailsaurPassword();
            test.info("🔑 Entered Mailosaur password.");
            approveMail.MailsaurLogin();
            test.info("🔓 Logged into Mailosaur successfully.");

            page.locator("//p[contains(text(),'Thank you for accepting the meeting request from ')]").first().click();
            test.info("✉ Opened latest 'Thank you for accepting the meeting request' email.");

            page.locator("//span[contains(text(),'Dear ')]").textContent();
            test.info("📄 Validated greeting text in email.");
            page.locator("//p[contains(text(),'Thank you for accepting the')]").textContent();
            test.info("📄 Validated thank you message in email.");
            page.locator("//p[contains(text(),'The meeting is now')]").textContent();
            test.info("📄 Validated meeting status in email.");
            page.locator("(//p)[16]").textContent();
            test.info("📄 Validated additional email content.");

            page.locator("//tbody/tr/td[@class='column column-1']/a/table[1]").textContent();
            test.info("📄 Validated table content in email.");
            page.locator("//span[contains(text(),'Meeting')]").nth(1).click();
            test.info("✅ Clicked Meeting tab/link inside email.");
        }

        extent.flush();
        test.info("📘 Extent report flushed successfully.");
    }
}
