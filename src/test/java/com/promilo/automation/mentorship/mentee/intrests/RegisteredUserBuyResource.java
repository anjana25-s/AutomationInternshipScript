package com.promilo.automation.mentorship.mentee.intrests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserBuyResource extends BaseClass {

	
	
	
	
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
        
        
        
        
        
        
        
        
        
        
        
	        // -------------------- Mentorship Module --------------------
	        HomePage dashboard = new HomePage(page);
	        Thread.sleep(2000);
	        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

	        // Search for mentor
            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");
	        page.waitForTimeout(2000);
            page.waitForTimeout(14000);

	        
	        
	        
	        DescriptionPage descriptionValidation = new DescriptionPage(page);
	        Locator buyResources = descriptionValidation.buyResources().first();
	        descriptionValidation.allLink().click();
	        buyResources.scrollIntoViewIfNeeded();
	        buyResources.click();

	        // -------------------- Fill form details --------------------
	        MentorshipFormComponents form = new MentorshipFormComponents(page);
	        form.registeredUserName().fill(name);
	        // Generate phone number starting with 90000 + 5 random digits
	        String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
	        form.registeredUserMobile().fill(randomPhone);

	        

	        form.downloadResource().click();
	        

	        if (otp == null || otp.length() < 4) {
	            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
	        }

	        for (int i1 = 0; i1 < 4; i1++) {
	            String otpChar = String.valueOf(otp.charAt(i1));
	            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
	            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

	            int attempts = 0;
	            boolean filled = false;
	            while (!filled && attempts < 3) {
	                attempts++;
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
	                throw new RuntimeException("Failed to enter OTP digit " + (i1 + 1));
	            }
	            test.info("Entered OTP digit: " + otpChar);
	        }
	        
	        page.locator("//button[text()='Verify & Proceed']").click();
	        form.nextButton().click();

	     // -------------------- Invoice Section --------------------
	        form.InvoiceNameField().fill(invoiceName);
	        form.StreetAdress1().fill(street1);
	        form.StreetAdress2().fill(street2);
	        form.pinCode().fill(pincode);
	        form.yesRadrioBox().click();
	        form.gstNumber().fill(gst);
	        form.panNumber().fill(pan);

	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

	        // -------------------- Payment --------------------
	        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
	        paymentFunctionality.payOnline().click();
	        paymentFunctionality.payButton().click();

	        page.waitForTimeout(5000);

	        // Switch to iframe for payment
	        FrameLocator frame = page.frameLocator("iframe");
	        frame.getByTestId("contactNumber").fill(contactNumber);

	        // Select "Wallet" option
	        frame.getByTestId("nav-sidebar").locator("div")
	                .filter(new Locator.FilterOptions().setHasText("Wallet"))
	                .nth(2)
	                .click();

	        // -------------------- Handle PhonePe Popup --------------------
	        Page page8 = page.waitForPopup(() -> {
	            page.locator("iframe").contentFrame()
	                .getByTestId("screen-container")
	                .locator("div")
	                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
	                .nth(2)
	                .click();
	        });

	        // Click Success in popup
	        page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();

	     // -------------------- Handle Download Tab --------------------
	        Page downloadTab = page.waitForPopup(() -> {
	            // This will auto-trigger when success is clicked
	        });
	        downloadTab.waitForLoadState();
	        logger.info("Download tab opened with URL: " + downloadTab.url());
	        downloadTab.close(); // ✅ close the tab safely

	        // Switch back to main page
	        page.bringToFront();

	        // -------------------- Validation --------------------
	        Locator thankYouPopup = page.locator(
	                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
	        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
	        

	        ThankYouPopup popupValidation = new ThankYouPopup(page);
	        popupValidation.downloadManually().click();
	        page.bringToFront();
	        popupValidation.myDownloadsButton().click();



	     // ========================= SECTION: Validate Mentor Interest Card =========================
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);

         
            assertEquals(myintrest.mentorName().innerText().trim(), "karthik U");
            assertEquals(myintrest.mentorData().innerText().trim(), "dxgfchvjbng vbnm");
            assertTrue(myintrest.experianceString().isVisible());
            assertEquals(myintrest.experianceValue().innerText().trim(), "2 Years");
            assertEquals(myintrest.locationValue().innerText().trim(), "Anantapur");
            assertEquals(myintrest.serviceName().innerText().trim(), "Resources");
	        

	    
}
}

}