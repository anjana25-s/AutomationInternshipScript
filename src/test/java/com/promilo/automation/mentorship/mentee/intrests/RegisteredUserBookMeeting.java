package com.promilo.automation.mentorship.mentee.intrests;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.promilo.automation.mentorship.datavalidation.objects.MentorshipBookMeetingPageObjects;
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

public class RegisteredUserBookMeeting extends BaseClass{
	
	
	
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

        // ======== Count data rows (skip header) ========
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, colMap.get("Tc_Id"));
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        if (rowCount == 0) {
            test.fail("No data rows in Excel");
            Assert.fail("No data rows in Excel");
            return;
        }

        Set<String> targetKeywords = Collections.singleton("MentorshipBookMeeting");

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch data
            String mentorName = excel.getCellData(i, colMap.get("MentorName"));
            String otp = excel.getCellData(i, colMap.get("otp"));
            String invoiceName = excel.getCellData(i, colMap.get("invoiceName"));
            String name = excel.getCellData(i, colMap.get("MentorName"));
            String street1 = excel.getCellData(i, colMap.get("street1"));
            String street2 = excel.getCellData(i, colMap.get("street2"));
            String pincode = excel.getCellData(i, colMap.get("pincode"));
            String gst = excel.getCellData(i, colMap.get("gst"));
            String pan = excel.getCellData(i, colMap.get("pan"));
            String contactNumber = excel.getCellData(i, colMap.get("contactNumber"));
        
        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

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
	        
	        
	        
	        
	        DescriptionPage GetMentorCall= new DescriptionPage(page);
	        GetMentorCall.allLink().click();
	        GetMentorCall.videoCallLink().click();
	        Thread.sleep(3000);
	        
	        Locator bookMeeting =page.locator("//button[text()='Book Online Meeting']").nth(1);
	        bookMeeting.scrollIntoViewIfNeeded();
	        bookMeeting.click();
	        
	        MentorshipFormComponents fillForm= new MentorshipFormComponents(page);
	        fillForm.registeredUserName().fill(name);	        
	        
	        // random phone + email
	        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
	        
	        fillForm.registeredUserMobile().fill(randomPhone);
	        BaseClass.generatedPhone = randomPhone;
	        fillForm.getMentorCall().click();
	        
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
	        fillForm.verifyAndProceed().click();

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
	        
	        fillForm.nextButton().click();
	        
	       DescriptionPage clickGetHr= new DescriptionPage(page);
	        
	    // -------------------- Invoice Section --------------------
	       fillForm.InvoiceNameField().fill(invoiceName);
	       fillForm.StreetAdress1().fill(street1);
	       fillForm.StreetAdress2().fill(street2);
	       fillForm.pinCode().fill(pincode);
	       fillForm.yesRadrioBox().click();
	       fillForm.gstNumber().fill(gst);
	       fillForm.panNumber().fill(pan);
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
	           
	           
	           page.waitForTimeout(8000);
	           

	           // -------------------- Validation --------------------
	           Locator thankYouPopup = page.locator(
	                   "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	           thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
	           Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");

	           page.waitForTimeout(3000);
	           

	          

	           // -------------------- Validate My Interest Card --------------------
	              MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
	              page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("My Interest")).nth(1).click();
	              page.waitForTimeout(5000);

	              String meetingDate = myintrest.meetingDate().first().textContent().trim();
	              Pattern p = Pattern.compile("(\\d{1,2})");
	              Matcher m = p.matcher(meetingDate);
	              String displayedDayStr = "";
	              if (m.find()) displayedDayStr = m.group(1);
	              else Assert.fail("❌ Could not extract day from Meeting Date: " + meetingDate);
	              int displayedDay = Integer.parseInt(displayedDayStr);
	              int storedDay = Integer.parseInt(BaseClass.selectedDate.trim());
	              Assert.assertEquals(displayedDay, storedDay);

	              String meetingTime = myintrest.meetingTime().first().textContent().trim();
	              Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2}\\s?[AP]M)");
	              Matcher timeMatcher = timePattern.matcher(meetingTime);
	              String displayedTime = "";
	              if (timeMatcher.find()) displayedTime = timeMatcher.group(1).replaceAll("\\s+", " ").trim();
	              String storedTime = BaseClass.selectedTime.replaceAll("\\s+", " ").trim();
	              storedTime = storedTime.replaceFirst("^0", "");
	              displayedTime = displayedTime.replaceFirst("^0", "");
	              Assert.assertEquals(displayedTime, storedTime);

	              Assert.assertEquals(myintrest.bookMeetingMentorName().innerText().trim(), "December Automation");
	              Assert.assertEquals(myintrest.bookMeetingMentorData().innerText().trim(), "dxgfchvjbng vbnm");
	              Assert.assertEquals(myintrest.bookMeetingServiceName().innerText().trim(), "Video Call");
	         

}
    
}}