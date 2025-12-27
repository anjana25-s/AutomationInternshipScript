package com.promilo.automation.mentorship.mentee.intrests;

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
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.mentorship.menteenotifications.BrandEndorsementEmailAndInAppNotifications;
import com.promilo.automation.mentorship.mentornotifications.MentorBrandEndorsementsNotifications;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserBrandEndorsement extends BaseClass{

	
	
	
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
        ExtentTest test = extent.createTest("❌ Apply for Job Invalid OTP | Hardcoded Test");

        
        
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
            String Description       = excel.getCellData(i, colMap.get("feedbackText"));


        // INITIALIZE PLAYWRIGHT
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);
        
        
        
        
     // -------------------- Mentorship Module --------------------
        HomePage dashboard = new HomePage(page);
        Thread.sleep(2000);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill(mentorName);
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);
        

        // Navigate to mentor details
        DescriptionPage GetMentorCall = new DescriptionPage(page);
        GetMentorCall.allLink().click();
        GetMentorCall.brandEndorsement().click();
        GetMentorCall.bookEnquiry().nth(1).click();
        

        // -------------------- Fill Brand Endorsement Form --------------------
        MentorshipFormComponents fillForm = new MentorshipFormComponents(page);
        fillForm.registeredUserName().fill(name);
        // Generate random 5-digit number
        int randomNum = 10000 + new Random().nextInt(90000);

        // Generate random phone number
        String randomPhone = "90000" + randomNum;
        fillForm.registeredUserMobile().fill(randomPhone);
        // Store in BaseClass
        BaseClass.generatedPhone = randomPhone;

        // Submit enquiry
        GetMentorCall.bookAnEnquiry().click();
        
        page.waitForTimeout(3000);

        // -------------------- OTP Handling --------------------
        if (otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 digits: " + otp);
        }

        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            for (int attempt = 1; attempt <= 3 && !filled; attempt++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
            }
        }

        
        fillForm.verifyAndProceed().click();

        // -------------------- Brand Endorsement Details --------------------
        fillForm.typeofBrand().click();
        fillForm.brandOptions().first().click();
        fillForm.typeYourMessage().fill("Hello");
        fillForm.brandEndorsementSubmit().click();
        page.waitForTimeout(4000);        

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");

        ThankYouPopup thankYou = new ThankYouPopup(page);


        thankYou.myPreference().click();


     // ========================= SECTION: Validate Mentor Interest Card =========================
        MentorshipMyintrest myintrest = new MentorshipMyintrest(page);

        // Expected Values – Temporary hardcoded
        String expectedMentorName = "December Automation";
        String expectedMentorData = "dxgfchvjbng vbnm";
        String expectedExperience = "2 Years";
        String expectedLocation = "Anantapur";
        String expectedServiceName = "Brand Endorsement";


        // 2. Mentor name
        String actualMentorName = myintrest.mentorName().innerText().trim();
        Assert.assertEquals(actualMentorName, expectedMentorName);

        // 3. Mentor Data
        String actualMentorData = myintrest.mentorData().innerText().trim();
        Assert.assertEquals(actualMentorData, expectedMentorData);

        // 4. Experience label
        Assert.assertTrue(myintrest.experianceString().isVisible());

        // 5. Experience value
        String actualExperience = myintrest.experianceValue().innerText().trim();
        Assert.assertEquals(actualExperience, expectedExperience);

        // 6. Location value
        String actualLocation = myintrest.locationValue().innerText().trim();
        Assert.assertEquals(actualLocation, expectedLocation);

        // 7. Service Name
        String actualServiceName = myintrest.serviceName().innerText().trim();
        Assert.assertEquals(actualServiceName, expectedServiceName);
        
        
        
        page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
        
        MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
        mailsaurCredenatinals.MialsaurMail();
        mailsaurCredenatinals.MailsaurContinue();
        mailsaurCredenatinals.MailsaurPassword();
        mailsaurCredenatinals.MailsaurLogin();
        
        
        MentorBrandEndorsementsNotifications  mentorNotification= new MentorBrandEndorsementsNotifications(page);
        mentorNotification.intrestShownMailNotification().first().click();
        mentorNotification.dearText().isVisible();
        mentorNotification.goodnewsText().isVisible();
        mentorNotification.pendingCard().isVisible();
        mentorNotification.acceptButton().click();
        
        page.bringToFront();

        mentorNotification.backButton().click();
        
        page.waitForTimeout(4000);
        
        
        
        
        
        BrandEndorsementEmailAndInAppNotifications menteeNotification= new BrandEndorsementEmailAndInAppNotifications(page);
        menteeNotification.emailNotification().first().click();
        menteeNotification.hiText();
        menteeNotification.thankYoufortakingText();
        menteeNotification.viewCard().click();
               
       

}
    }
    
}
