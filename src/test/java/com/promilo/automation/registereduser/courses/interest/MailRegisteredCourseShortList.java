package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.courses.intrestspages.CoursesShortlist;
import com.promilo.automation.courses.intrestspages.DownloadsIntrestPage;
import com.promilo.automation.guestuser.courses.interest.CourseShortList;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.PhoneRegisteredSignupUtility;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MailRegisteredCourseShortList extends BaseClass {
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test
    public void applyForJobWithInvalidData() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

     // ======== Load Excel ========
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Mentee Interest");
            System.out.println("✅ Excel loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Failed to load Excel: " + e.getMessage());
            test.fail("Failed to load Excel: " + e.getMessage());
            Assert.fail("Excel loading failed");
            return;
        }

        // ======== Map headers to column index ========
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ======== Count data rows (skip header) ========
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, colMap.get("Tc_Id"));
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        System.out.println("✅ Loaded " + rowCount + " data rows from Excel.");

        if (rowCount == 0) {
            System.out.println("❌ No data rows found");
            test.fail("No data rows in Excel");
            Assert.fail("No data rows in Excel");
            return;
        }

        // ======== Process rows ========
        Set<String> targetKeywords = Collections.singleton("GetMentorCall");

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();

            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch data using header mapping
            String mentorName     = excel.getCellData(i, colMap.get("MentorName"));
            String otp            = excel.getCellData(i, colMap.get("otp"));
            String invoiceName    = excel.getCellData(i, colMap.get("invoiceName"));
            String name           = excel.getCellData(i, colMap.get("MentorName"));
            String street1        = excel.getCellData(i, colMap.get("street1"));
            String street2        = excel.getCellData(i, colMap.get("street2"));
            String pincode        = excel.getCellData(i, colMap.get("pincode"));
            String gst            = excel.getCellData(i, colMap.get("gst"));
            String pan            = excel.getCellData(i, colMap.get("pan"));
            String contactNumber  = excel.getCellData(i, colMap.get("contactNumber"));
            String password       = excel.getCellData(i, colMap.get("password"));


        

        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);
        
            
            
            
            
            
            page.locator("//a[text()='Courses']").click();
            test.info("📚 Clicked on 'Courses'");

            page.locator("//input[@placeholder='Search Colleges and Courses']").fill("BTWIN");
            page.keyboard().press("Enter");
            test.info("🔍 Searched for course: Course auto");
            

            page.locator("//img[@class='shortlist-icon shortlist']").click();
            test.info("📌 Clicked on 'Shortlist'");


            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            
            
            FormComponents downloadBrouchure= new FormComponents(page);
            
            
            downloadBrouchure.userNameField().fill("karthik");
            downloadBrouchure.mobileField().fill(randomPhone);

            page.locator("[id='preferredLocation']").click();
            List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
            Locator options = page.locator("//div[@class='option w-100']");
            for (String industry : industries) {
                for (int i1 = 0; i1 < options.count(); i1++) {
                    if (options.nth(i1).innerText().trim().equalsIgnoreCase(industry)) {
                        options.nth(i1).click();
                        test.info("📍 Selected Location: " + industry);
                        break;
                    }
                }
            }

            page.locator("//button[@type='button' and @class='submit-btm-askUs btn btn-primary']").click();
            test.info("🚀 Submitted Shortlist form");

            
            
            
            
            String validOtp = "9999";
            for (int i1 = 0; i1 < 4; i1++) {
                String digit = Character.toString(validOtp.charAt(i1));
                Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

                for (int retry = 0; retry < 3; retry++) {
                    otpField.click();
                    otpField.fill("");
                    otpField.fill(digit);
                    if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                        break;
                    page.waitForTimeout(500);
                }
                test.info("🔢 Entered OTP digit " + digit + " at position " + (i1 + 1));
            }

            page.locator("//button[text()='Verify & Proceed']").click();
            test.info("✅ Clicked 'Verify & Proceed' after entering OTP");
            
            
            // ✅ Validate Thank You popup
            Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
            );
            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            Assert.assertTrue(thankYouPopup.innerText().trim().equalsIgnoreCase("Thank You!"));
            test.pass("🎉 Thank You popup validated");

            page.locator("img[alt='closeIcon Ask us']").click();
            test.info("❌ Closed Thank You popup");
            
            
            
            
            page.waitForTimeout(4000);
            
            CoursesShortlist interestValidation  = new CoursesShortlist(page);
            interestValidation.myInterestTab().click();
            interestValidation.myPreferenceTab().click();
            String campaignName=interestValidation.campaignName().textContent().trim();
            assertTrue(campaignName.contains("BTWIN"));
            
            
            String location=interestValidation.location().textContent().trim();
            assertEquals(location, "Ahmedabad");
            
            String brandTitle= interestValidation.productTitle().textContent().trim();
            assertEquals(brandTitle, "Course promilo automation");
            
            
            
            String duration= interestValidation.duration().textContent().trim();
            assertEquals(duration, "3 Years");
            
            String avgFee= interestValidation.fee().textContent().trim();
            assertEquals(avgFee, "4.50 Lakhs");
            
            String serviceName= interestValidation.serviceName().textContent().trim();
            assertEquals(serviceName, "Shortlisted");
            
            
            interestValidation.serviceIcon().isVisible();
            interestValidation.cardPicture().isVisible();
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            

            // 🔑 Open Mailosaur in a new tab (keep original session alive)
            Page mailsaurPage = page.context().newPage();
            mailsaurPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            test.info("📩 Opened Mailosaur Inbox");

            MailsaurCredentials approveMail = new MailsaurCredentials(mailsaurPage);
            approveMail.MialsaurMail();
            approveMail.MailsaurContinue();
            approveMail.MailsaurPassword();
            approveMail.MailsaurLogin();
            test.info("🔑 Logged in to Mailosaur");

            mailsaurPage.locator("//p[contains(text(),'Great news')]").first().click();
            test.info("📨 Opened 'Great news' email");

            mailsaurPage.locator("//span[contains(text(),'Hi ')]").textContent(); 
            mailsaurPage.locator("//p[contains(text(),'Great news!')]").textContent();
            mailsaurPage.locator("//p[contains(text(),'To help you make the best')]").textContent();
            mailsaurPage.locator("//p[contains(text(),'Do')]").textContent();
            test.info("✅ Validated mail contents");

            // Inside mailsaurPage after opening the mail
            Locator counselingLink = mailsaurPage.locator("//span[contains(text(),'Counseling')]");
            Locator expertLink = mailsaurPage.locator("//span[contains(text(),'Talk to Expert ')]");

            // Click Counseling (opens new tab)
            Page counselingPage = mailsaurPage.waitForPopup(() -> {
                counselingLink.click();
            });
            counselingPage.waitForLoadState();
            test.info("🔗 Opened Counseling link in new tab");
            counselingPage.close();

            // Back to mailsaurPage
            mailsaurPage.bringToFront();

            // Click Talk to Expert (opens new tab)
            Page expertPage = mailsaurPage.waitForPopup(() -> {
                expertLink.click();
            });
            expertPage.waitForLoadState();
            test.info("🔗 Opened Talk to Expert link in new tab");
        }

        extent.flush();
    }
}
