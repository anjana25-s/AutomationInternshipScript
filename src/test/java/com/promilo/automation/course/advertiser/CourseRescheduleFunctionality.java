package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class CourseRescheduleFunctionality extends BaseClass {
    
    @Test(
		      dependsOnMethods = {
				        "com.promilo.automation.guestuser.courses.interest.FreeVideoCounsellingReschedule.FreeVideoCounsellingRescheduleTest"
				      } 
				    )
			
    public void ProspectRejectFunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", 
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        System.out.println("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) continue;

            // ✅ Create separate node for each test case
            ExtentTest test = extent.createTest("🚀 Prospect Reschedule Functionality | TestCase: " + testCaseId);

            try {
                test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

                Page page = initializePlaywright();
                test.info("🌐 Playwright page initialized");

                page.navigate(prop.getProperty("stageUrl"));
                test.info("🌍 Navigated to URL: " + prop.getProperty("stageUrl"));

                page.setViewportSize(1000, 768);
                Thread.sleep(3000);
                test.info("🖥 Viewport set to 1000x768 and waited 3 seconds");

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);
                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                login.loginPasswordField().fill("Karthik@88");
                login.signInButton().click();
                test.pass("✅ Advertiser logged in");

                AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
                myaccount.hamburger().click();
                myaccount.myAccount().click();
                test.info("📂 Opened My Account section");

                AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
                prospect.myProspect().click();
                test.info("📑 Opened Prospects section");

                AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
                Thread.sleep(5000);

                page.locator("[class='approve-btn content-nowrap maxbtnwidth mb-50 btn btn-']").first()
                        .click(new Locator.ClickOptions().setForce(true));
                test.info("⏳ Clicked on Reschedule accept button");
                
                
                String rescheduleMeeting= page.locator("[class='heading text-primary text-center mb-1']").textContent().trim();
                assertEquals(rescheduleMeeting, "Reschedule the Meeting");
                
                String subHeading= page.locator("[class='sub-heading']").textContent().trim();
                assertEquals(subHeading, "User has requested reschedule . Please accept the request for this slot.");
                
                String noteText= page.locator("[class='note text-center']").textContent().trim();
                assertEquals(noteText, "Accepting or cancelling the reschedule request will also result in the acceptance or cancellation of the original meeting.");

               System.out.println(page.locator("[class='rescheduledSlots-time w-100 font-12 text-center mb-50 active']").textContent().trim());                 
                

                page.locator("//button[text()='Accept Request']").click();
                page.waitForTimeout(3000);
                               



                
                                
                String succespopUp=page.locator("[class='font-14 text-center']").textContent().trim();
                assertEquals(succespopUp, "Thanks for Accepting this reschedule request. Your meeting is confirmed.");

                
         
                
             // Step 1: Open new page (tab) in same browser context
                Page mailPage = page.context().newPage();
                mailPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
                test.info("🌐 Navigated to Mailosaur inbox.");

                // Step 2: Login
                MailsaurCredentials approveMail = new MailsaurCredentials(mailPage);
                approveMail.MialsaurMail();
                approveMail.MailsaurContinue();
                approveMail.MailsaurPassword();
                approveMail.MailsaurLogin();
                test.info("🔑 Logged into Mailosaur successfully.");

                // Step 3: Click the latest "Reschedule Request Sent" mail
                mailPage.locator("//p[contains(text(),'Thank you for accepting the meeting request from ')]")
                       .first()
                       .click();
                test.info("✉ Opened the latest 'Reschedule Request Sent' email.");

                // Step 4: Validate mail contents
                String hiText = mailPage.locator("//span[contains(text(),'Dear ')]").textContent();
                String campaignText = mailPage.locator("//p[contains(text(),'Thank you for accepting the')]").textContent();
                String requestText = mailPage.locator("//p[contains(text(),'The meeting is now')]").textContent();
                   
                test.info("📄 Email Content Validation Results:");
                test.info("Hi Text: " + hiText);
                test.info("Campaign Text: " + campaignText);
                test.info("Request Text: " + requestText);
                

                System.out.println("Validation Results:");
                System.out.println(hiText);
                System.out.println(campaignText);
                System.out.println(requestText);
                
                
                test.pass("✅ Reschedule request submitted successfully");

                
                
                
            
            } catch (Exception e) {
                test.fail("❌ Test failed due to exception: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // ✅ Flush Extent Report after all iterations
        extent.flush();
    }
}
