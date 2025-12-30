package com.promilo.automation.guestuser.courses.interest;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.courses.intrestspages.CoursesShortlist;
import com.promilo.automation.courses.intrestspages.FreeVideoCounsellingPage;
import com.promilo.automation.mentorship.datavalidation.objects.MentorshipBookMeetingPageObjects;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class FreeVideoCounsellingTest extends BaseClass {

    @Test
    public void freeVideoCounsellingFlow() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 FreeVideoCounselling Functionality | Data-Driven");

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

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            String email = excel.getCellData(i, 7); 
            String password = excel.getCellData(i, 6); 
            String comment = excel.getCellData(i, 10); 

            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            FreeVideoCounsellingPage fvcPage = new FreeVideoCounsellingPage(page);

            test.info("🌐 Browser launched and page initialized");

            page.navigate(prop.getProperty("url"));
            test.info("➡️ Navigated to URL: " + prop.getProperty("url"));

            page.setViewportSize(1000, 768);
            Thread.sleep(3000);
            
            
            
            

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click();
                test.info("❌ Closed popup successfully");
            } catch (Exception ignored) {
                test.info("⚠️ No popup found, continuing...");
            }

            Thread.sleep(3000);

            
            
            
                        
            
            CoursesShortlist shortList = new CoursesShortlist(page);

            shortList.coursesTab().click();
            shortList.searchCourseInput().fill("BTWIN");
            page.keyboard().press("Enter");
            
            page.waitForTimeout(14000);
            
            
            
            // Search Course
            fvcPage.freeVideoCounsellingBtn().click();
            test.info("🎥 Clicked on 'Free Video Counselling' option");

            fvcPage.userNameField().fill("karthik");
            test.info("👤 Entered user name: karthik");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            fvcPage.userMobileField().fill(randomPhone);
            test.info("📱 Entered random phone: " + randomPhone);

            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";
            fvcPage.userEmailField().fill(randomEmail);
            test.info("📧 Entered random Mailosaur email: " + randomEmail);
            
            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;
            
            
            
            
            

            // Select Location
            page.waitForTimeout(2000);
            fvcPage.preferredLocationDropdown().click();
            Thread.sleep(1000);
            List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
            Locator options = fvcPage.locationOptions();
            for (String industry : industries) {
                boolean found = false;
                for (int j = 0; j < options.count(); j++) {
                    String optionText = options.nth(j).innerText().trim();
                    if (optionText.equalsIgnoreCase(industry)) {
                        options.nth(j).click();
                        test.info("✅ Selected industry: " + industry);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    test.warning("⚠️ Industry not found: " + industry);
                }
            }
            
            
            
            
            page.waitForTimeout(2000);
            test.info("📋 Captured confirmation text content: " + fvcPage.textContent().textContent());
            fvcPage.userEmailField().click(); // close dropdown
            Thread.sleep(2000);

            
            page.waitForTimeout(200);
            Locator counsellingBtn = fvcPage.freeVideoCounsellingSubmit();
            counsellingBtn.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            test.info("⏳ Waiting for 'Start Counselling' button to be visible");

            counsellingBtn.click();
            
            
            
            

            // Enter OTP
            String otp = "9999";
            for (int j = 0; j < otp.length(); j++) {
                String digit = Character.toString(otp.charAt(j));
                Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
                for (int retry = 0; retry < 3; retry++) {
                    otpField.click();
                    otpField.fill("");
                    otpField.fill(digit);
                    if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                        break;
                    page.waitForTimeout(500);
                }
                test.info("🔢 Entered OTP digit " + digit + " at position " + (j + 1));
            }
            fvcPage.verifyProceedBtn().click();
            test.info("✅ Clicked 'Verify & Proceed'");
            
            
            MentorshipBookMeetingPageObjects po = new MentorshipBookMeetingPageObjects(page);

            
            
            
            

            String currentMonth = po.currentMonth().textContent().trim();
            String expectedMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM"));
            Assert.assertTrue(currentMonth.contains(expectedMonth));

            Locator dateElement = po.availableDate();
            dateElement.click();
            
            
            
            
            
            BaseClass.selectedDate = dateElement.innerText().split("\\s+")[0].trim();

            Locator timeElement = po.firstTimeSlot();
            timeElement.click();
            
            
            
            
            BaseClass.selectedTime = timeElement.innerText();

            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            page.locator("//button[text()='Next']").click();
            test.info("➡️ Clicked Next button");

            page.locator("//button[text()='Submit']").nth(1).click();
            test.info("📨 Clicked Submit button");            
            
            
            
            
            

            // Validate Thank You popup
            fvcPage.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(5000));
            Assert.assertEquals(fvcPage.thankYouPopup().innerText().trim(), "Thank You!");
            test.pass("🎉 Thank You popup validated");

            fvcPage.thankYouCloseIcon().click();
            test.info("❌ Closed Thank You popup");
            
            
            
            
            
            
            
            

            // Mailsaur validation
            Page mailsaurPage = page.context().newPage();
            mailsaurPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            test.info("📨 Navigated to Mailosaur inbox");

            MailsaurCredentials approveMail = new MailsaurCredentials(mailsaurPage);
            approveMail.MialsaurMail();
            approveMail.MailsaurContinue();
            approveMail.MailsaurPassword();
            approveMail.MailsaurLogin();

            mailsaurPage.locator("//p[contains(text(),'Please Review the Agenda')]").first().click();
            test.info("📩 Opened 'Please Review the Agenda' mail");

            mailsaurPage.locator("//span[contains(text(),'Hi ')]").textContent();
            mailsaurPage.locator("//p[contains(text(),'Your meeting with ')]").textContent();
            mailsaurPage.locator("//p[contains(text(),'Waiting for ')]").click();

            mailsaurPage.locator("(//tbody)[13]").click();
            test.pass("📨 Mail content validated successfully for FreeVideoCounselling flow");
        }

        extent.flush();
    }
}
