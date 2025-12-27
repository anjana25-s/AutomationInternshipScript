package com.promilo.automation.mentorship.mentor.videoacceptance;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xmlbeans.impl.soap.Text;
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
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipVideoServiceMyAcceptanceCardValidation extends BaseClass{
	
	
	
	
	    @Test(dependsOnMethods = {
	            "com.promilo.automation.mentorship.mentee.intrests.MentorshipBookMeeting.mentorshipbook"
	        } 
	    )
    public void AcceptVideoServiceRequestTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✅ Accept video service - Positive Test");

       
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

            String inputValue = excel.getCellData(i, 3);
            String description = excel.getCellData(i, 10);

            try {
                test.info("➡️ Starting execution for row " + i + " with input: " + inputValue);

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try {
                    mayBeLaterPopUp.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                mayBeLaterPopUp.clickLoginButton();
                test.info("🔑 Navigating to Login Page.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill("812de0aa@qtvjnqv9.mailosaur.net");
                loginPage.passwordField().fill("Karthik@88");
                loginPage.loginButton().click();
                test.info("✅ Logged in with registered credentials: " );

                Hamburger hamburger = new Hamburger(page);
                hamburger.Mystuff().click();
                hamburger.MyAccount().click();
                
                MyAcceptance acceptVideoRequest= new MyAcceptance(page);
                acceptVideoRequest.myAcceptance().click();
                
                
                
                String statusTag=acceptVideoRequest.videoCallstatusTag().first().textContent().trim();
                assertEquals(statusTag, "Pending");
                
                String  menteeName= acceptVideoRequest.videoServiceMenteeName().first().textContent().trim();
                assertEquals(menteeName, "December");
                
                String campaignName=acceptVideoRequest.videoServiceCampaignName().first().textContent().trim();
                
                assertTrue(campaignName.contains("December Automation"));                
                String highlightText=acceptVideoRequest.videoServiceHighlight().first().textContent().trim();
                assertEquals(highlightText, "dxgfchvjbng vbnm");
                
                
                
                
                
                
                
                MentorshipMyintrest myintrest= new MentorshipMyintrest(page);
                
                
                String amountValidation=myintrest.totalAmount().textContent().trim();
                assertEquals(amountValidation, "₹ 2,600");
                
                String languageValidation=myintrest.languageChosen().textContent().trim(); 
                assertEquals(languageValidation, "English");
                
                
             // Meeting Date - dynamically fetched
                String meetingDate = acceptVideoRequest.meetingDate().first().textContent().trim();
                System.out.println("🔹 Displayed Meeting Date Text: " + meetingDate);

                // Extract the day from any dd/MM/yyyy or d/M/yyyy format
                Pattern pattern = Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");
                Matcher matcher = pattern.matcher(meetingDate);

                if (!matcher.find()) {
                    Assert.fail("❌ Invalid date format. Could not extract day from: " + meetingDate);
                }

                // Extract displayed day
                int displayedDay = Integer.parseInt(matcher.group(1));
                System.out.println("🔹 Extracted Displayed Day: " + displayedDay);

                // Extract stored day (from BaseClass)
                int storedDay = Integer.parseInt(BaseClass.selectedDate.trim());
                System.out.println("🔹 Stored Day from BaseClass: " + storedDay);

                // Assertion
                Assert.assertEquals(
                        displayedDay,
                        storedDay,
                        "❌ Meeting Date mismatch! Expected (stored): " + storedDay + " | Displayed: " + displayedDay
                );

                System.out.println("✅ Meeting Date matched successfully!");

             // Meeting Time
                String meetingTime = acceptVideoRequest.meetingTime().first().textContent().trim();
                Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2}\\s?[AP]M)");
                Matcher timeMatcher = timePattern.matcher(meetingTime);

                String displayedTime = "";
                if (timeMatcher.find()) {
                    displayedTime = timeMatcher.group(1).replaceAll("\\s+", " ").trim();
                } else {
                    Assert.fail("❌ Could not extract time from Meeting Time: " + meetingTime);
                }

                // Normalize both times (remove leading zero if present)
                String storedTime = BaseClass.selectedTime.replaceAll("\\s+", " ").trim();
                storedTime = storedTime.replaceFirst("^0", "");      // ← removes leading 0
                displayedTime = displayedTime.replaceFirst("^0", ""); // ← removes leading 0 (safe)

                // Final Assertion
                Assert.assertEquals(displayedTime, storedTime,
                        "❌ Meeting Time mismatch! Stored Time: " + storedTime + " | Displayed: " + displayedTime);

                

                
                
                
                
                
                
                
                
                
                
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
              
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                
                
            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (page != null) {
                    page.close();
                    test.info("🧹 Browser closed for current test row.");
                }
            }
        }
    }
	
	
	

}
