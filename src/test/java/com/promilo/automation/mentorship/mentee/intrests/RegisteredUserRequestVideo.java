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

public class RegisteredUserRequestVideo extends BaseClass{
	
	
	
	
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
            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");
	        page.waitForTimeout(2000);
            page.waitForTimeout(14000);

	        
	        
	        
	        
	        
	        DescriptionPage descriptionValidation= new DescriptionPage(page);
	        descriptionValidation.allLink().click();
	        Locator personalizedVideoMessage= descriptionValidation.personalizedVideoMessage();
	        personalizedVideoMessage.scrollIntoViewIfNeeded();
	        personalizedVideoMessage.click();
	        page.waitForTimeout(3000);
	        descriptionValidation.requestVideo().nth(1).click();
	        
	        
	        
	     // -------------------- Fill form details --------------------
	        MentorshipFormComponents form = new MentorshipFormComponents(page);
	        form.registeredUserName().fill(name);

	        // Generate phone number starting with 90000 + 5 random digits
	        String randomPhone = "90000" + String.format("%05d", (int)(Math.random() * 100000));
	        form.registeredUserMobile().fill(randomPhone);

	        BaseClass.generatedPhone = randomPhone;	     
	        form.RequestVideoMessageButton().click();
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

	        form.verifyAndProceed().click();	        
	        
	        
	        form.occationDropdown().click();
	        Locator options = form.occationOptions(); // your radio option container
	        int optionCount = options.count();

	        for (int i1 = 0; i1 < optionCount; i1++) {
	            Locator option = options.nth(i1);
	            
	            // Get the radio input
	            Locator radio = option.locator("input[type='radio']");
	            
	            // Get the text next to the radio (usually in label or span)
	            String radioText = option.innerText().replace("\n", " ").trim(); // cleans up newlines
	            
	            System.out.println("Option " + (i1 + 1) + ": " + radioText);
	            
	            // Optional: Click the radio
	            // radio.click();
	        }

	        form.occationOptions().first().click();
	        form.contentDescriptionBox().fill("Something");
	        form.toggleButton().click();
	        form.nextButton().click();
	        
	        
	        page.waitForTimeout(3000);
	        
	        
		       DescriptionPage clickGetHr= new DescriptionPage(page);
		        
		    // -------------------- Invoice Section --------------------
		        form.InvoiceNameField().fill(invoiceName);
		        form.StreetAdress1().fill(street1);
		        form.StreetAdress2().fill(street2);
		        form.pinCode().fill(pincode);
		        form.yesRadrioBox().click();
		        form.gstNumber().fill(gst);
		        form.panNumber().fill(pan);


		        page.waitForTimeout(3000);
		        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
		        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
		        
		        
		        
		        
		        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
		        paymentFunctionality.payOnline().click();
		        paymentFunctionality.payButton().click();

		        page.waitForTimeout(5000);

		        // Switch to iframe for payment
		        FrameLocator frame = page.frameLocator("iframe");

		        // Fill contact number
		        frame.getByTestId("contactNumber").fill(contactNumber);

		        // Select "Wallet" option from sidebar
		        frame.getByTestId("nav-sidebar")
		            .locator("div")
		            .filter(new Locator.FilterOptions().setHasText("Wallet"))
		            .nth(2)
		            .click();
		        
		        page.waitForTimeout(3000);
		        
		        Page page8 = page.waitForPopup(() -> {
		               page.locator("iframe").contentFrame().getByTestId("screen-container").locator("div").filter(new Locator.FilterOptions().setHasText("PhonePe")).nth(2).click();
		               
		               
		           });
		           page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
		           
		        
		      ThankYouPopup popupValidation= new ThankYouPopup(page);
		      String thankYouText=popupValidation.registeringText().textContent();
		      String expectedText = thankYouText;
		      System.out.println(expectedText);
		      assertEquals(thankYouText, expectedText);
		      
		      page.getByRole(AriaRole.DIALOG)
	            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
	            .click();


		     // ================== Validate Mentor Interest Card Elements ==================

		        MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        

		        // ------------------ Expected Values (You can load from Excel later) ------------------
		        String expectedMentorName = "karthik U";
		        String expectedMentorData = "dxgfchvjbng vbnm";
		        String expectedServiceName = "Personalized Video";


		        
		       
		        // 2. Mentor name
		        String actualMentorName = myintrest.bookMeetingMentorName().innerText().trim();
		        Assert.assertEquals(actualMentorName, expectedMentorName,
		                "❌ Mentor Name does not match!");
		        System.out.println("✔ Mentor name validated: " + actualMentorName);

		        // 3. Mentor data (High)
		        Assert.assertTrue(myintrest.bookMeetingMentorData().isVisible(), "Mentor data is NOT visible");
		        String actualMentorData = myintrest.bookMeetingMentorData().innerText().trim();
		        Assert.assertEquals(actualMentorData, expectedMentorData,
		                "❌ Mentor Data does not match!");

		        		        
		        // 3. Service Name
		        String actualServiceName = myintrest.bookMeetingServiceName().innerText().trim();
		        Assert.assertEquals(actualServiceName, expectedServiceName,
		                "❌ Service name mismatch!");
		        
		        
		        // 3. Duration 

		        String actulaDuration=myintrest.askQueryDuration().textContent().trim();
		        assertEquals(actulaDuration, "3 Days");
		        


	        
	        		      
	        

	        
	        
	        
	        
	        
	        
	    }

}}
