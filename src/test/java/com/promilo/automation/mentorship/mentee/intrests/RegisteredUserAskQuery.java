package com.promilo.automation.mentorship.mentee.intrests;

import static org.testng.Assert.assertEquals;

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

public class RegisteredUserAskQuery extends BaseClass{
	
	
	
	
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
        
        

        // LOAD EXCEL
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
        Set<String> targetKeywords = Collections.singleton("RegisteredUserAskQuery");

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

	        
	        // -------------------- Mentor Description --------------------
	        DescriptionPage descriptionValidation = new DescriptionPage(page);
	        page.waitForTimeout(3000);
	        descriptionValidation.askQuery().click();
	        System.out.println("Clicked on 'Ask Query'");
	        page.waitForTimeout(2000);
	        
	       

	        descriptionValidation.askYourQuery().nth(1).click();
	        System.out.println("Selected 'Ask Your Query' option");
	        page.waitForTimeout(2000);

	        // -------------------- Ask Query Form --------------------
	        MentorshipFormComponents askQuery = new MentorshipFormComponents(page);
	        page.waitForTimeout(2000);
	        askQuery.registeredUserName().fill(name);
	        
	        
	        
	        System.out.println("Filled name: karthik");

	     // Generate phone number starting with 90000 + 5 random digits
	        String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
	        
	        int randomNum = 10000 + new Random().nextInt(90000);
	        
	        askQuery.registeredUserMobile().fill(randomPhone);


	        BaseClass.generatedPhone = randomPhone;
	        

	        page.locator("//button[normalize-space()='Ask Your Query']").nth(3)
	            .click(new Locator.ClickOptions().setForce(true));
	        System.out.println("Clicked 'Ask Your Query' button");

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

	        page.waitForTimeout(2000);
	        page.locator("//button[text()='Verify & Proceed']").click();
	            
	        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Type your questions here..."))
	            .fill("Ask Your Questions Here");
	        System.out.println("Filled question text box");

	        askQuery.nextButton().click(new Locator.ClickOptions().setForce(true));
	        System.out.println("Clicked 'Next' button");

	        // -------------------- Invoice Form --------------------
	     // -------------------- Invoice Section --------------------
	        page.waitForTimeout(4000);
	        askQuery.InvoiceNameField().fill(invoiceName);
	        askQuery.StreetAdress1().fill(street1);
	        askQuery.StreetAdress2().fill(street2);
	        askQuery.pinCode().fill(pincode);
	        askQuery.yesRadrioBox().click();
	        askQuery.gstNumber().fill(gst);
	        askQuery.panNumber().fill(pan);

	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
	        System.out.println("Checked terms checkbox");
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"))
	            .click(new Locator.ClickOptions().setForce(true));
	        	        System.out.println("Clicked Save button");

	        // -------------------- Payment --------------------
	        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
            paymentFunctionality.payOnline().click();
            paymentFunctionality.payButton().click();

	        // Switch to iframe for payment
	        FrameLocator frame = page.frameLocator("iframe");
	        frame.getByTestId("contactNumber").fill(contactNumber);
	        System.out.println("Filled contact number in payment iframe");

	        frame.getByTestId("nav-sidebar").locator("div")
	            .filter(new Locator.FilterOptions().setHasText("Wallet"))
	            .nth(2).click(new Locator.ClickOptions().setForce(true));
	        System.out.println("Clicked Wallet option in payment iframe");

	        page.waitForTimeout(3000);

	        // Handle new popup (PhonePe)
	        Page phonePePage = page.waitForPopup(() -> {
	            frame.getByTestId("screen-container")
	                .locator("div")
	                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
	                .nth(2).click(new Locator.ClickOptions().setForce(true));
	        });
	        System.out.println("Switched to PhonePe popup window");

	        phonePePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
	        System.out.println("Clicked Success on PhonePe page");

	        page.waitForTimeout(8000);

	        // -------------------- Validation --------------------
	        Locator thankYouPopup = page.locator(
	            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));
	        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
	        System.out.println("✅ Thank You popup displayed successfully");

	        
	        page.getByRole(AriaRole.DIALOG)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
            .click();





           

         // --------------------  Card Validation --------------------
            MentorshipMyintrest myintrest1 = new MentorshipMyintrest(page);
            page.waitForTimeout(5000);
            assertEquals(myintrest1.askQueryMentorName().innerText().trim(), "karthik U");
            assertEquals(myintrest1.askQueryMentorData().innerText().trim(), "dxgfchvjbng vbnm");
            assertEquals(myintrest1.askQueryDuration().innerText().trim(), "22 Days");
            assertEquals(myintrest1.askQueryServiceName().innerText().trim(), "Ask Query");
    
    }
	       
}
    
}
    
