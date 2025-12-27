package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;

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
import com.promilo.automation.courses.intrestspages.TalkToExpertPage;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MailRegisteredTalkToExpert   extends BaseClass{
	
	
	
	
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
        
        
        
        // Step 1: Click Courses
        page.locator("//a[text()='Courses']").click();
        test.info("📚 Clicked on 'Courses'.");

        // Step 2: Search course
        page.locator("//input[@placeholder='Search Colleges and Courses']").fill("btwin");
        test.info("🔍 Entered course search text: Course auto");

        page.keyboard().press("Enter");
        test.info("↩️ Pressed Enter to search courses.");
        
        
           page.locator("//span[normalize-space()='Talk to Experts']").first().click();

        
        
        
        
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(page);

        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        String serverId = "qtvjnqv9";
        int randomNum = 10000 + new Random().nextInt(90000);
        String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";
        
        
     // ✅ Invalid inputs validation
        ErrorMessageValidation.nameTextField().first().fill("Karthik");
        ErrorMessageValidation.mobileTextField().fill(randomPhone);

        
        page.waitForTimeout(3000);
        page.locator("[id='preferredLocation']").click();
        test.info("📍 Clicked Preferred Location dropdown");

        Thread.sleep(1000);
        List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
        Locator options = page.locator("//div[@class='option w-100']");
        for (String industry : industries) {
            boolean found = false;
            for (int i1 = 0; i1 < options.count(); i1++) {
                String optionText = options.nth(i1).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i1).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found) {
                test.warning("⚠️ Industry not found: " + industry);
            }
        }

        page.locator("//div[@class='text-content']").textContent();
        test.info("📋 Captured confirmation text content after industry selection");
        
        page.locator("//input[@name='userName']").click();


        test.info("📍 Re-clicked Preferred Location dropdown to close it");

        Thread.sleep(2000);
        
        

        page.locator("[class='submit-btm-askUs btn btn-primary']").click();
        
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

        
        
        page.waitForTimeout(3000);
        page.locator("[class='fw-bold w-100 font-16 calendar-modal-custom-btn mt-2 btn btn-primary']").click();
        test.info("📨 Clicked Submit button");

        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, found: " + popupText);
        test.pass("🎉 Thank You popup validated: " + popupText);

        page.locator("//img[@alt='closeIcon Ask us']").click();
        test.info("❌ Closed Thank You popup");
        
        
        TalkToExpertPage cardValidation= new TalkToExpertPage(page);
        cardValidation.myInterestTab().click();
        page.waitForTimeout(3000);
        cardValidation.myPreferenceButton().click();
        
        
        
        String campaignName=cardValidation.campaignName().textContent().trim();
        assertEquals(campaignName, "Application form fill mobile brand name");
        
        String location=cardValidation.location().textContent().trim();
        assertEquals(location, "Kerala - Other");
        
        String productTitle=cardValidation.productTitle().textContent().trim();
        assertEquals(productTitle, "Application form fill mobile title");
        
        String duration=cardValidation.duration().textContent().trim();
        assertEquals(duration, "6 Months");
        
        String fee=cardValidation.fee().textContent().trim();
        assertEquals(fee, "0.01 Lakhs");
        
        
        String serviceName=cardValidation.serviceName().textContent().trim();
        assertEquals(serviceName, "Talk to Experts");
        
        
        cardValidation.serviceIcon().isVisible();
        
        cardValidation.cardPicture().isVisible();
        
        
        
        
        
        
        
        

        
        
        
        
        
        
        
        }}
	
	
	
	
	
}