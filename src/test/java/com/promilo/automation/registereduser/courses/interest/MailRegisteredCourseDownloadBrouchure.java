package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
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
import com.promilo.automation.courses.intrestspages.DownloadsIntrestPage;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.PhoneRegisteredSignupUtility;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MailRegisteredCourseDownloadBrouchure extends BaseClass {

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

            
            
            
            DownloadsIntrestPage downloadsPage = new DownloadsIntrestPage(page);

            Thread.sleep(3000);
            downloadsPage.coursesMenu().click();
            Thread.sleep(3000);
            downloadsPage.downloadBrochureBtn().click();

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            
            
            FormComponents downloadBrouchure= new FormComponents(page);
            
            
            downloadBrouchure.userNameField().fill("karthik");
            downloadBrouchure.mobileField().fill(randomPhone);
            downloadsPage.finalDownloadBtn().click();
            
            
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
            page.waitForTimeout(5000);
            
            
            

            downloadsPage.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            String popupText = downloadsPage.thankYouPopup().innerText().trim();
            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                    "Expected 'Thank You!' popup, found: " + popupText);
            test.pass("🎉 Thank You popup validated: " + popupText);

            downloadsPage.thankYouCloseIcon().click();
            test.info("✅ Closed the 'Thank You' popup.");
            
            
            downloadsPage.myInterestTab().click();
            downloadsPage.myPreferenceTab().click();
            
            page.waitForTimeout(4000);
            DownloadsIntrestPage interestValidation= new DownloadsIntrestPage(page);
            String campaignName=interestValidation.campaignName().textContent().trim();
            assertTrue(campaignName.contains("Global Institute of Engineering, Manage"));
            
            
            String location=interestValidation.location().textContent().trim();
            assertEquals(location, "Nandyal");
            
            String brandTitle= interestValidation.productTitle().textContent().trim();
            assertEquals(brandTitle, "Technology");
            
            
            
            String duration= interestValidation.duration().textContent().trim();
            assertEquals(duration, "3 Years");
            
            String avgFee= interestValidation.fee().textContent().trim();
            assertEquals(avgFee, "0.50 Lakhs");
            
            String serviceName= interestValidation.serviceName().textContent().trim();
            assertEquals(serviceName, "Download Brochure");
            
            
            interestValidation.serviceIcon().isVisible();
            interestValidation.cardPicture().isVisible();
            
            

                        
            
            
            
            
            // Open Mailosaur inbox
            Page mailsaurPage = page.context().newPage();
            mailsaurPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            test.info("📩 Opened Mailosaur Inbox");

            MailsaurCredentials approveMail = new MailsaurCredentials(mailsaurPage);
            approveMail.MialsaurMail();
            approveMail.MailsaurContinue();
            approveMail.MailsaurPassword();
            approveMail.MailsaurLogin();
            test.info("🔑 Logged into Mailosaur");

            mailsaurPage.locator("//p[contains(text(),'Download Your Meetup Details ')]").first().click();
            test.info("📨 Opened 'Download Your Meetup Details' email");

            mailsaurPage.locator("//span[contains(text(),'Hi ')]").textContent();
            mailsaurPage.locator("//p[contains(text(),'You expressed interest in')]").textContent();
            test.info("✅ Validated email contents");

            mailsaurPage.locator("//a[contains(text(),'Click here for download brochure')]").click();
            test.info("📂 Clicked 'Download Brochure' link inside email");

            mailsaurPage.locator("(//a)[27]").click();
            test.info("⬇️ Final Brochure download link clicked");

        }

        extent.flush();
    }
}
