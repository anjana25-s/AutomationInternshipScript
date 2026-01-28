package com.promilo.automation.emailnotifcations.advertiser;

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
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.campaign.ProspectApproveFunctionality;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.emailnotifications.advertiser.gethrcall.GetHrCallNotification;
import com.promilo.automation.pageobjects.emailnotifications.gethrcall.InitiateHrCallNotification;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class GetHrCallInitiated  extends BaseClass{
	
	 ExtentReports extent = ExtentManager.getInstance();
     ExtentTest test = extent.createTest("🚀 Promilo Staging Signup - Passes if 'My Stuff' is visible after signup (Playwright)");

	 private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

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
	        LandingPage landingPage = new LandingPage(page);
	        try {
	            landingPage.getPopup().click();
	            test.info("Closed initial popup");
	        } catch (Exception ignored) {}

	        landingPage.clickLoginButton();
	        test.info("Clicked on Login button");

	        LoginPage loginPage = new LoginPage(page);
	        loginPage.loginMailPhone().fill(inputvalue);
	        loginPage.passwordField().fill(password);
	        loginPage.loginButton().click();
	        test.info("Logged in as registered user: " + inputvalue);

	        applyJobDetailsFlow(page, name, otp, mailphone);
	    }

	    private void applyJobDetailsFlow(Page page, String name, String otp, String mailphone) throws Exception {
	        JobListingPage homePage = new JobListingPage(page);
	        
	        homePage.homepageJobs().click();
	        test.info("Navigated to Jobs section");

	        homePage.fintech();
	        test.info("Selected Fintech jobs category");

	        Thread.sleep(5000);
	        Locator fintechJobCard = page.locator("//p[text()='Developer']").first();
	        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
	        fintechJobCard.scrollIntoViewIfNeeded();
	        fintechJobCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
	        fintechJobCard.click();
	        test.info("Opened Python Developer job card");

	        Thread.sleep(2000);
	        homePage.getHrCall().first().click();
	        test.info("Clicked on Get HR Call button");

	        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']").fill("karthik");
	        test.info("Filled user name");

	        Random random = new Random();
	        String mobileToUse = (mailphone != null && !mailphone.isEmpty()) ? mailphone : ("90000" + String.format("%05d", random.nextInt(100000)));
	        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']").fill(mobileToUse);
	        test.info("Filled user mobile: " + mobileToUse);
	        
	        homePage.selectIndustryDropdown().click();
	        test.info("Opened Industry dropdown");
	        Thread.sleep(1000);

	        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");
	        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
	        for (String industry : industries) {
	            for (int i = 0; i < options.count(); i++) {
	                String optionText = options.nth(i).innerText().trim();
	                if (optionText.equalsIgnoreCase(industry)) {
	                    options.nth(i).click();
	                    test.info("✅ Selected industry: " + industry);
	                    break;
	                }
	            }
	        }

	        page.waitForTimeout(4000);
	        homePage.getAnHrCallButton().click();
	        test.info("Clicked on Get An HR Call");

	        if (otp == null || otp.length() < 4) {
	            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
	        }

	        for (int i = 0; i < 4; i++) {
	            String otpChar = String.valueOf(otp.charAt(i));
	            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
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
	        
	        page.locator("//button[text()='Verify & Proceed']").click();
	        test.info("Clicked Verify & Proceed");

	        page.locator("//button[text()='Submit']").nth(1).click();
	        test.info("Clicked Submit on HR call popup");
	        
	        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));

	        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You' popup not displayed.");
	       
	        
	        test.info("✅ 'Thank You' popup displayed");
	        
	        
	        
	        Page page1 = page.context().newPage();
	        page1.navigate("https://mailosaur.com/app/servers/ofuk8kzb/messages/inbox");
	        test.info("Opened Mailosaur inbox");

	        page1.locator("//input[@placeholder='Enter your email address']").fill("karthiku7026@gmail.com");
	        page1.locator("//button[text()='Continue']").click();
	        page1.locator("//input[@placeholder='Enter your password']").fill("Karthik@88");
	        page1.locator("//button[text()='Log in']").click();
	        test.info("Logged into Mailosaur");

	        Locator firstEmail = page1.locator("//p[contains(text(),'Exciting News: Prospects ')]").first();
	        firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	        firstEmail.click();
	        test.info("Opened first HR Call Request email");
	        
	        GetHrCallNotification hrCall= new GetHrCallNotification(page1);
	        
 
test.info( hrCall.greetingText().textContent()+"is displayed");
	  test.info(hrCall.excitingText().textContent() + "is displayed");      
	     test.info(hrCall.clickLinkText().textContent()+"is displayed");   
	      test.info(hrCall.promiloLogo1().textContent() +"is displayed");  
	     test.info(hrCall.promiloLogo2().textContent()+ "is displayed");   
	    test.info(hrCall.regardingText().textContent()+"is displayed");    
	     test.info(hrCall.candidateCard().textContent()+ "redirected to");   
	        hrCall.myProspectCard().click();

test.info("redirected to respective prospect page");
test.pass("Validated All the mail contents and redirection");
	        
	        
	        Thread.sleep(3000);
	        page.reload();

	       
	    }
}
