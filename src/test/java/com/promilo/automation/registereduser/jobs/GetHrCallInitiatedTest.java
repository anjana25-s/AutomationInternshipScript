package com.promilo.automation.registereduser.jobs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.campaign.ProspectApproveFunctionality;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.job.pageobjects.GetHrCallPageObjects;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.job.pageobjects.JobsMyInterestPage;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.pageobjects.emailnotifications.gethrcall.InitiateHrCallNotification;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class GetHrCallInitiatedTest  extends BaseClass{
	
	 ExtentReports extent = ExtentManager.getInstance();
     ExtentTest test = extent.createTest("🚀 Promilo Staging Signup - Passes if 'My Stuff' is visible after signup (Playwright)");

	 private static final Logger logger = LogManager.getLogger(GetHrCallInitiatedTest.class);

	    private static String registeredEmail = null;
	    private static String registeredPassword = null;

	    @BeforeSuite
	    public void performSignupOnce() throws Exception {
	        System.out.println("⚙️ Performing signup ONCE before entire suite using Mailosaur UI signup...");

	        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
	        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

	        registeredEmail = creds[0];
	        registeredPassword = creds[1];

	        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
	    }

	    @DataProvider(name = "jobApplicationData")
	    public Object[][] jobApplicationData() throws Exception {
	        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloJob");

	        int rowCount = 0;
	        for (int i = 1; i <= 1; i++) {  
	            String testCaseId = excel.getCellData(i, 0);
	            if (testCaseId == null || testCaseId.isEmpty()) break;
	            rowCount++;
	        }

	        Object[][] data = new Object[rowCount][8];
	        for (int i = 1; i <= rowCount; i++) {
	            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
	            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
	            data[i - 1][2] = excel.getCellData(i, 4); // InputValue (ignored)
	            data[i - 1][3] = excel.getCellData(i, 6); // Password (ignored)
	            data[i - 1][4] = excel.getCellData(i, 7); // Name
	            data[i - 1][5] = excel.getCellData(i, 5); // OTP
	            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
	            data[i - 1][7] = i;                       // RowIndex
	        }
	        return data;
	    }

	    @Test(dataProvider = "jobApplicationData")
	    public void applyForJobTestFromExcel(
	            String testCaseId,
	            String keyword,
	            String inputvalue,
	            String password,
	            String name,
	            String otp,
	            String mailphone,
	            int rowIndex
	    ) throws Exception {

	        ExtentReports extent = ExtentManager.getInstance();
	        ExtentTest test = extent.createTest("Apply for Job as Registered User | " + testCaseId);

	        if (registeredEmail == null || registeredPassword == null) {
	            test.fail("❌ Signup credentials not found.");
	            Assert.fail("Signup not completed.");
	            return;
	        }

	        // Override input credentials with signed up ones
	        inputvalue = registeredEmail;
	        password = registeredPassword;

	        Page page = initializePlaywright();
	        page.navigate(prop.getProperty("url"));
	        page.setViewportSize(1000, 768);
	        test.info("🌐 Navigated to Promilo staging site");

	        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

	        test.pass("✅ Job application test passed for TestCase: " + testCaseId);
	        extent.flush();
	    }

	    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name, String otp, String mailphone) throws Exception {
	        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
	        try {
	            mayBeLaterPopUp.getPopup().click();
	            test.info("Closed initial popup");
	        } catch (Exception ignored) {}

	        mayBeLaterPopUp.clickLoginButton();
	        test.info("Clicked on Login button");

	        LoginPage loginPage = new LoginPage(page);
	        loginPage.loginMailPhone().fill(inputvalue);
	        loginPage.passwordField().fill(password);
	        loginPage.loginButton().click();
	        test.info("Logged in as registered user: " + inputvalue);

	        applyJobDetailsFlow(page, name, otp, mailphone);
	    }

	    private void applyJobDetailsFlow(Page page, String name, String otp, String mailphone) throws Exception {
	        JobListingPage listingPage = new JobListingPage(page);
	        FormComponents askUsPage = new FormComponents(page);
            GetHrCallPageObjects obj = new GetHrCallPageObjects(page);

	        // Navigate to Jobs
	        listingPage.homepageJobs().click();
	        test.info("Navigated to Jobs section");

	        Thread.sleep(5000);
	        listingPage.searchJob().fill("December Campaign Automation");
	        page.keyboard().press("Enter");

	        Thread.sleep(2000);
	        listingPage.getHrCall().first().click();
	        test.info("Clicked on Get HR Call button");

	        
        
        String getHrCallPopUpDescription = obj.getHrCallPopupDescription().textContent().trim();
        String expectedGetHrCallPopUpDescription = "Why Register to Get an HR Callback for Your Dream Job?Take Charge of Your Career: Connect with recruiters and apply for roles that match your aspirations.Stay Updated: You can receive real-time notifications about job openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt callback opportunities.Personalized Opportunities: Tailored job alerts ensure you get all the suitable openings.Exclusive Resources: Unlock premium tools and tips for interviewing and securing your dream job.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.Take Charge of Your Career: Connect with recruiters and apply for roles that match your aspirations.Stay Updated: You can receive real-time notifications about job openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt callback opportunities.Personalized Opportunities: Tailored job alerts ensure you get all the suitable openings.Exclusive Resources: Unlock premium tools and tips for interviewing and securing your dream job.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.PreviousNext";
        assertEquals(getHrCallPopUpDescription, expectedGetHrCallPopUpDescription);
        
        
        String getHrCallHeaderText = obj.getHrCallHeaderText().textContent().trim();
        String expectedGetHrCallHeaderText="Get an HR Call from December Campaign Automation!";
        assertEquals(getHrCallHeaderText, expectedGetHrCallHeaderText);
        
        
        String enableWhatssappNotification = obj.enableWhatsappNotification().textContent().trim();
        String expectedEnableWhatssappNotification="Enable updates & important information on Whatsapp";
        assertEquals(enableWhatssappNotification, expectedEnableWhatssappNotification);
        
        String getHrCallfooterText = obj.getHrCallFooterText().textContent().trim();
        String expectedGetHrCallfooterText="By proceeding ahead you expressly agree to the PromiloTerms & Conditions";
        assertEquals(getHrCallfooterText, expectedGetHrCallfooterText);
        
        
        
        
        askUsPage.userNameField().fill("karthik");
        test.info("Filled user name");

        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty()) ? mailphone : ("90000" + String.format("%05d", random.nextInt(100000)));
        askUsPage.mobileField().fill(mobileToUse);
        test.info("Filled user mobile: " + mobileToUse);

        listingPage.selectIndustryDropdown().click();
        test.info("Opened Industry dropdown");
        Thread.sleep(1000);

        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");
        Locator options = obj.industryOptions(); 
        for (String industry : industries) { for (int i = 0; i < options.count(); i++) { String optionText = options.nth(i).innerText().trim(); if (optionText.equalsIgnoreCase(industry)) { options.nth(i).click(); test.info("✅ Selected industry: " + industry); break; } } }

        askUsPage.userNameField().click();
        page.waitForTimeout(4000);
        listingPage.getAnHrCallButton().click();
        test.info("Clicked on Get An HR Call");

        
        page.waitForTimeout(2000);
        String otpPageDescription = obj.otpPageDescription().textContent().trim();
        String expectedOtpPageDescription = "Accelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextAccelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.";
        assertEquals(otpPageDescription, expectedOtpPageDescription);
        
        
        
        
        
        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = obj.otpDigitField(i + 1);
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
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
            }
            test.info("Entered OTP digit: " + otpChar);
        }

        
        
        
        String text3 = obj.otpThanksForInformation().textContent().trim();
        String expectedResult3="Thanks for giving your Information!";
        assertEquals(text3, expectedResult3);
      


   
        String text4 = obj.otpVerificationHeader().textContent().trim();
        String expectedResult4="OTP Verification";
  

        assertEquals(text4, expectedResult4);
        
        
        
        String text5 = obj.otpInstructionText().textContent().trim();
        
        
        String expectedResult5="Enter the 4-digit verification code we just sent you to";
        
        assertTrue(text5.contains(expectedResult5));
   

 
        String text6 = obj.otpStillCantFind().textContent().trim();
        
        String expectedResult6="Still can’t find the OTP";
        assertTrue(text6.contains(expectedResult6));
        askUsPage.verifyProceedButton().click();
        test.info("Clicked Verify & Proceed");

        askUsPage.nextButton().click();
        
        String nextPageText = obj.nextPageInfoText().first().textContent().trim();
        String expectedNextPageText="HR CallYou can enjoy an HR Call from your applied company to schedule your interview based on your preferred language.";
        assertEquals(nextPageText, expectedNextPageText);
        
        
        page.waitForTimeout(2000);
        String  chooseLangaugeText = obj.chooseLanguageText().textContent().trim();
        String expectedChooseLangaugeText="Please Select your preferred language with December Campaign Automation. This will make it easier for you and HR to connect as you choose. ";
        assertEquals(chooseLangaugeText, expectedChooseLangaugeText);
        
        
        
    
        
        askUsPage.submitButton().nth(1).click();	       
        String submitPageText = obj.submitPageDescription().nth(1).textContent().trim();
        String expectedSubmitPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
        assertEquals(submitPageText, expectedSubmitPageText);
        
        
        String takeMomentText = obj.takeMomentText().textContent().trim();
        String expectedTakeMomentText="Please take a moment to answer the below questions.";
        assertEquals(takeMomentText, expectedTakeMomentText);
        
        
        
        
        
        test.info("Clicked Submit on HR call popup");

        askUsPage.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(askUsPage.thankYouPopup().isVisible(), "'Thank You' popup not displayed.");
        
        
        
        String thankYouText = obj.thankYouText().textContent().trim();
        String expectedThankYouText = "Thank You!Thank you for requesting an HR Call from December Campaign Automation. Check your email, notifications, and WhatsApp for details on exclusive access.Help Recruiter to know you better by building, Updating or Uploading your resume to get an interview approved.Build ResumeUpload Resume";
        assertEquals(thankYouText, expectedThankYouText);
        
        
        
     // ======================================================================
        // VALIDATE "JOBS MY INTEREST" CARD
        // ======================================================================
        JobsMyInterestPage myintrest = new JobsMyInterestPage(page);

            new ThankYouPopup(page).myPreference().click();

        String expectedJobRole = "Software Tester";
        String expectedBrandName = "December Campaign Automation";
        String expectedExperience = "0-1 Yrs";
        String expectedLocation = "Bengaluru/Bangalore";
        String expectedServiceName = "Get HR Call";
        String expectedSalary = "5.6L - 9.9L";

        // Job Role
        String actualJobRole = myintrest.jobRole().innerText().trim();
        Assert.assertEquals(actualJobRole, expectedJobRole);

        // Experience (contain match as requested)
        String actualExperiance = myintrest.experiance().first().innerText().trim();
        Assert.assertTrue(actualExperiance.contains(expectedExperience),
                "Expected value not found! Actual: " + actualExperiance);

        // Brand Name
        String actualBrandName = myintrest.brandName().innerText().trim();
        Assert.assertEquals(actualBrandName, expectedBrandName);

        // Service Name
        String actualServiceName = myintrest.serviceName().innerText().trim();
        Assert.assertEquals(actualServiceName, expectedServiceName);

        // Salary
        String actualSalary = myintrest.salary().nth(1).innerText().trim();
        assertEquals(actualSalary, expectedSalary);

        // Location
        String actualServiceLocation = myintrest.location().innerText().trim();
        Assert.assertEquals(actualServiceLocation, expectedLocation);
        
        test.info("✅ 'Thank You' popup displayed");
        
        
    }
}
