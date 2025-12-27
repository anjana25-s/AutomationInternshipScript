package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.courses.intrestspages.FillApplication;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MailRegisteredFillApplicationOnline extends BaseClass {
	
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
        
        
        
        page.waitForTimeout(3000);
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);


        Thread.sleep(3000);
        test.info("⏱ Waited 3 seconds for page load.");

         Thread.sleep(3000);

        // Step 1: Click Courses
        page.locator("//a[text()='Courses']").click();
        test.info("📚 Clicked on 'Courses'.");

        // Step 2: Search course
        page.locator("//input[@placeholder='Search Colleges and Courses']").fill("Golden Institute of Technology");
        test.info("🔍 Entered course search text: Course auto");

        page.keyboard().press("Enter");
        test.info("↩️ Pressed Enter to search courses.");

        // Step 3: Click Talk to Experts
        page.locator("//span[text()='Fill Application']").click();
        test.info("👨‍🏫 Clicked 'Talk to Experts' button.");
        
        
        
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(page);
        
        
        
        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        String serverId = "qtvjnqv9";
        int randomNum = 10000 + new Random().nextInt(90000);
        
        page.waitForTimeout(3000);
                // ✅ Invalid inputs validation
        ErrorMessageValidation.nameTextField().first().fill("karthik");
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

        
        
        
        page.locator("//button[text()='Fill Application']").click();
        
        
        
        
        
        
        
        
        
        
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
        
        
        
        
        
        //Form filling first page
        page.locator("[id='lastName']").fill("U");
        page.locator("[id='dateOfBirth']").fill("1997-08-28");
        page.locator(".react-select__input-container").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Male").setExact(true)).click();
        page.locator("//button[text()='Send OTP']").first().click();
        
        
        
        Page page1 = page.context().newPage();
        page1.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

        
        page1.waitForTimeout(3000);
        MailsaurCredentials formOtp= new MailsaurCredentials(page1);       
        if (page1.locator("//input[@id='email']").isVisible()) {
            // Need to login
            formOtp.MialsaurMail().fill("karthikmailsaur@gmail.com");
            formOtp.MailsaurContinue().click();
            formOtp.MailsaurPassword().fill("Karthik@88");
            formOtp.MailsaurLogin();
        } else {
            System.out.println("Already logged in. Skipping login.");
        }
        
        
        Locator firstEmail = page1.locator("//p[text()='Verify Your Email Address to Complete Promilo Registration 🌐']").first();
        firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        firstEmail.click();
        page1.waitForTimeout(3000);
        String otpText = page1.locator("//p[contains(text(),'Verification Code')]").innerText().trim();
        String formOtp1 = otpText.replaceAll("\\D+", "");
        System.out.println("✅ OTP fetched from Mailosaur UI: " + otp);
        
        
        
        page1.close();        

        page.locator("[id='email_otp']").fill(formOtp1);
        page.locator("//button[text()='Verify OTP']").first().click();
        System.out.println("mail otp verified");

        page.locator("[id='aadhar']").fill("121113231322");

        
        page.locator("//button[text()='Send OTP']").first().click();
        page.locator("[id='mobile_otp']").fill("9999");
        
        page.waitForTimeout(4000);
        page.locator("//button[text()='Verify OTP']").first().click();
        System.out.println("Phone verified");



        
        
        
        
        page.locator("[class='next-btn']").click();
        
        
        //form filling second page
        page.locator("#boardOfExamination").click();
        page.getByText("Central Board of Secondary").click();
        page.getByText("Select Board of Examination").click();
        page.locator(".sub-option").first().click();
        page.locator(".react-select__input-container").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Band Score (Language Tests)")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Company Name")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Company Name")).fill("promilo");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Job Title")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Job Title")).fill("qa engineer");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Next next-arrow")).click();
        page.locator(".react-select__input-container").first().click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("JEE Main")).click();
        page.locator(".react-select__control.css-13cymwt-control > .react-select__value-container > .react-select__input-container").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("JEE Main")).click();
        
        
        
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Next next-arrow")).click();
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //Form fill 3rd page
        page.locator("[class='selected-values-display']").click();
        List<Locator> subOptions = page.locator("[class='sub-option']").all();

        for (int typeOfSport = 0; typeOfSport < subOptions.size(); typeOfSport++) {
            String text = subOptions.get(typeOfSport).textContent().trim();
            System.out.println("Option " + (typeOfSport + 1) + ": " + text);
        }

        
        page.locator("[class='sub-option']").first().click();
        
        
        //achievement drop down  
        page.waitForTimeout(3000);
        Locator level =page.locator(".react-select__input-container");
        level.scrollIntoViewIfNeeded();
        level.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("District Level")).click();
        
        
        
        
        
        page.waitForTimeout(4000);
        page.waitForFileChooser(() -> {
            page.locator("//span[text()='Upload file']").first().click();
        }).setFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
        
        
        page.getByText("I hereby declare that all the").click();

        page.getByText("Please complete all required").click();
        
        page.waitForFileChooser(() -> {
            page.locator("//span[text()='Upload file']").nth(1).click();
        }).setFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
        
        
        
        
        
        
        
        
        
        
        

        page.getByText("I hereby declare that all the").textContent().trim();
        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I hereby declare that all the")).check();

        
        page.getByText("Please complete all required").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Next next-arrow")).click();
        page.locator("//button[text()=' Pay']").first().click();
     // Locate the label that contains the payment text
        page.locator("label")
                .filter(new Locator.FilterOptions().setHasText("Pay Online")).click();
        
        
        
        
        Locator paymentLabel =  page.getByText("Pay Online ( ₹153.4 includes");
        String fullText = paymentLabel.textContent().trim(); 
        String amount = fullText.replaceAll("[^0-9.]", "");  
        
        System.out.println(fullText);
        System.out.println(amount);
        
        
        // Store in BaseClass
        BaseClass.courseFee = amount;


        test.info("💰 Extracted Course Fee: " + BaseClass.courseFee);
        System.err.println("Amount fetched");

        
        
        
        
        
        
        
        
        
        
     // Payment
        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
        paymentFunctionality.payOnline().click();
        System.out.println("pay online clicked");
        paymentFunctionality.payButton().nth(1).click();
        System.out.println("Pay button clicked");

        FrameLocator frame = page.frameLocator("iframe");
        frame.getByTestId("contactNumber").fill(contactNumber);

        Page popup2 = page.waitForPopup(() -> {
            frame.getByTestId("screen-container")
                    .locator("div")
                    .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                    .nth(2)
                    .click();
        });

        popup2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
        
        
        page.waitForTimeout(4000);
        String expectedMessage = "Success! You are all setWe’ve received your application and paymentView and track all your application details > My Application.";
        String successText = page.locator("[class='payment-success-main-cont']").textContent().replace("\n", " ").trim();

        Assert.assertEquals(successText, expectedMessage);
        
        
        
        page.locator("//a[text()='My Application.']").click();
        
        
        
        
        
        FillApplication fillFormValidation= new FillApplication(page);
        
        String campaignName=fillFormValidation.campaignName().textContent().trim();
        assertEquals(campaignName, "Golden Institute of Technology");
        
        
        String location= fillFormValidation.location().textContent().trim();
        assertEquals(location, "Nandyal");
        
        
        String courseName= fillFormValidation.courseName().textContent().trim();
        assertEquals(courseName, "MBA");
        
        String serviceName= fillFormValidation.serviceName().textContent().trim();
        assertEquals(serviceName, "Application");
        
        fillFormValidation.serviceIcon().isVisible();
        
        
        String statusTag= fillFormValidation.statusTag().textContent().trim();
        assertEquals(statusTag, "Submitted");
        System.out.println(statusTag+ "is displayed");
        
        String paidAmount = fillFormValidation.paidAmount().textContent().trim();
        String paidAmountValue = paidAmount.replaceAll("[^0-9.]", "");
        assertEquals(paidAmountValue, BaseClass.courseFee, 
             "❌ Mismatch: Paid amount does not match stored course fee.");

         
        
        
        
        String submittedDate = fillFormValidation.submittedDate().first().textContent().trim();
        System.out.println("Extracted Submitted Date: [" + submittedDate + "]");

        // Validate not empty
        if (submittedDate.isEmpty()) {
            Assert.fail("❌ Submitted date text is EMPTY. Locator might be wrong.");
        }

        Pattern p = Pattern.compile("(\\d{2}/\\d{2}/\\d{4})");
        Matcher m = p.matcher(submittedDate);

        String extractedDate = "";
        if (m.find()) {
            extractedDate = m.group(1);   
        } else {
            Assert.fail("❌ Could not extract a valid date from submitted text: " + submittedDate);
        }

        System.out.println("Extracted Date: " + extractedDate);

        // Build today's date for comparison
        String expectedToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim();

        // Validate date
        Assert.assertEquals(
                extractedDate,
                expectedToday,
                "❌ Meeting date does not match today's date"
        );

        System.out.println("✅ Current date validated: " + expectedToday);
        
        
        	
        
        
        
        
        
        
        
        
        


        
        
        
        
        
        
        
        
        
        
        

        
        
        
        
        



        }
    }
    
}
