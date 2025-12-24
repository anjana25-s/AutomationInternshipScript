package com.promilo.automation.mentorship.mentor.brandendorsement;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
<<<<<<< HEAD
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.LocatorOptions;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementAccept extends com.promilo.automation.resources.Baseclass{
	
	
	
	// ✅ Use generated email from previous test
    String emailToLogin = Baseclass.generatedEmail;
    String phoneToLogin = Baseclass.generatedPhone;

	 ExtentReports extent;
	    ExtentTest test;

	    @BeforeClass
	    public void setUpExtent() {
	        extent = ExtentManager.getInstance(); // Initialize ExtentReports
	    }

	    @AfterClass
	    public void tearDownExtent() {
	        if (extent != null) {
	            extent.flush();
	        }
	    }

	    @Test(
	        dependsOnMethods = {
	            "com.promilo.automation.mentorship.mentee.pagepbjects.BrandEndorsement.mentorshipBrandEndorsement"
	        } 
	    )
public void AcceptVideoServiceRequestTest() throws Exception {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("✅ Accept video service - Positive Test");

   
    Page page = initializePlaywright();
    page.navigate(prop.getProperty("url"));
    page.setViewportSize(1080, 720);

    test.info("🌐 Navigated to application URL.");

    String excelPath = Paths.get(System.getProperty("user.dir"),
            "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
    ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

    int rowCount = 0;
    for (int i = 1; i <= 1000; i++) {
        String testCaseId = excel.getCellData(i, 0);
        if (testCaseId == null || testCaseId.trim().isEmpty()) break;
        rowCount++;
    }
    test.info("📘 Loaded " + rowCount + " rows from Excel.");

    for (int i = 1; i < rowCount; i++) {
        String keyword = excel.getCellData(i, 1);
        if (!"AddEmployment".equalsIgnoreCase(keyword)) continue;

        String inputValue = excel.getCellData(i, 3);
        String description = excel.getCellData(i, 10);

        try {
            test.info("➡️ Starting execution for row " + i + " with input: " + inputValue);

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click();
                test.info("✅ Popup closed.");
            } catch (Exception ignored) {
                test.info("ℹ️ No popup found.");
            }

            mayBeLaterPopUp.clickLoginButton();
            test.info("🔑 Navigating to Login Page.");

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("rest-missing@8mgfvj1x.mailosaur.net");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();
            test.info("✅ Logged in with registered credentials: " );

            HomePage myStuff= new HomePage(page);
            myStuff.mystuff().click();

            Hamburger myaccount= new Hamburger(page);
            myaccount.MyAccount().click();
            
            
            
            
            //click on my-acceptance button
            MyAcceptance acceptRequest= new MyAcceptance(page);
            acceptRequest.myAcceptance().click();
            
            //click on brand Endorsement
            acceptRequest.brandEndorsementAccept().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            
            //close the modal's exit icon
            page.locator("img[alt='Add New']").click();
            
            
            
         // Click the copy icon for email
            Locator mailcopyIcon = page.locator("(//p[text()='Brand Endorsement']//following::img[@alt='copyIcon'])[1]");
            mailcopyIcon.click();

            // Get clipboard content for email
            Clipboard clipboardMail = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copiedMail = (String) clipboardMail.getData(DataFlavor.stringFlavor);
            System.out.println("Copied mail: " + copiedMail);

            // Click the copy icon for phone number
            Locator phonecopyIcon = page.locator("(//p[text()='Brand Endorsement']//following::img[@alt='copyIcon'])[2]");
            phonecopyIcon.click();

            // Get clipboard content for phone number
            Clipboard clipboardPhone = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copiedNumber = (String) clipboardPhone.getData(DataFlavor.stringFlavor);
            System.out.println("Copied number: " + copiedNumber);

            // Expected dynamic values from signup utility
            String expectedEmail = Baseclass.generatedEmail.trim().toLowerCase();
            String expectedPhone = Baseclass.generatedPhone.trim();

            // Normalize copied values
            String copiedMailNormalized = copiedMail.trim().toLowerCase();
            String copiedNumberNormalized = copiedNumber.trim().replaceAll("\\D", "");
            String expectedPhoneDigits = expectedPhone.replaceAll("\\D", "");

            // ---- 🧠 MAIL VALIDATION ----
            if (copiedMailNormalized.equals(expectedEmail)) {
                System.out.println("✅ Copied mail matches generated mail: " + copiedMail);
            } else {
                System.out.println("❌ Copied mail does NOT match generated mail.");
                System.out.println("Expected: " + expectedEmail);
                System.out.println("Found:    " + copiedMailNormalized);
            }

            // ---- 📱 PHONE VALIDATION ----
            if (copiedNumberNormalized.equals(expectedPhoneDigits)) {
                System.out.println("✅ Copied phone number matches generated phone: " + copiedNumber);
            } else {
                System.out.println("❌ Copied phone number does NOT match generated phone.");
                System.out.println("Expected: " + expectedPhoneDigits);
                System.out.println("Found:    " + copiedNumberNormalized);
            }

            // Continue test flow
            acceptRequest.brandEnodorsementViewMessage().click();
            String modalText = acceptRequest.modalContent().textContent();
            System.out.println("Modal text: " + modalText);

            
            
            // Create a new browser or page context from mentee perspective
            Browser actualBrowser = browser.get();
            BrowserContext advertiserContext = actualBrowser.newContext();
            Page mentorPage = advertiserContext.newPage();

            // Now use Mentor Page as usual
            mentorPage.navigate(prop.getProperty("url"));

            
            MayBeLaterPopUp login= new MayBeLaterPopUp(mentorPage);
            login.dismissPopup();
            login.clickLoginButton();
            
         
               LoginPage loginPage1 = new LoginPage(mentorPage);
               loginPage1.loginMailPhone().fill(Baseclass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
         
               
               //click on My-Intrest 
               mentorPage.locator("//span[text()='My Interest']").click();
               
               //click on my preference
               mentorPage.locator("//div[@class='tab text-center w-50 ms-1 pointer ']").click();
=======
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementAccept extends BaseClass{
	
	
	
	// ✅ Use generated email from previous test
    String emailToLogin = BaseClass.generatedEmail;
    String phoneToLogin = BaseClass.generatedPhone;

	 ExtentReports extent;
	    ExtentTest test;

	    @BeforeClass
	    public void setUpExtent() {
	        extent = ExtentManager.getInstance(); // Initialize ExtentReports
	    }

	    @AfterClass
	    public void tearDownExtent() {
	        if (extent != null) {
	            extent.flush();
	        }
	    }

	    @Test(
	        dependsOnMethods = {
	            "com.promilo.automation.mentorship.mentee.BrandEndorsement.mentorshipBrandEndorsement"
	        } 
	    )
public void AcceptVideoServiceRequestTest() throws Exception {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("✅ Accept video service - Positive Test");

   
    Page page = initializePlaywright();
    page.navigate(prop.getProperty("url"));
    page.setViewportSize(1080, 720);

    test.info("🌐 Navigated to application URL.");

    String excelPath = Paths.get(System.getProperty("user.dir"),
            "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
    ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

    int rowCount = 0;
    for (int i = 1; i <= 1000; i++) {
        String testCaseId = excel.getCellData(i, 0);
        if (testCaseId == null || testCaseId.trim().isEmpty()) break;
        rowCount++;
    }
    test.info("📘 Loaded " + rowCount + " rows from Excel.");

    for (int i = 1; i < rowCount; i++) {
        String keyword = excel.getCellData(i, 1);
        if (!"AddEmployment".equalsIgnoreCase(keyword)) continue;

        String inputValue = excel.getCellData(i, 3);
        String description = excel.getCellData(i, 10);

        try {
            test.info("➡️ Starting execution for row " + i + " with input: " + inputValue);

            LandingPage landingPage = new LandingPage(page);
            try {
                landingPage.getPopup().click();
                test.info("✅ Popup closed.");
            } catch (Exception ignored) {
                test.info("ℹ️ No popup found.");
            }

            landingPage.clickLoginButton();
            test.info("🔑 Navigating to Login Page.");

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("rest-missing@8mgfvj1x.mailosaur.net");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();
            test.info("✅ Logged in with registered credentials: " );
            
            

            //click on my stuff button
            DashboardPage myStuff= new DashboardPage(page);
            myStuff.mystuff().click();
            
            
            //click on my acceptance button
            MyAcceptance acceptRequest= new MyAcceptance(page);
            acceptRequest.myAcceptance().click();
            acceptRequest.brandEndorsementAccept().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            
            
            //click on exit icon of the request accepted  modal
            page.locator("img[alt='Add New']").click();
            
            
            
            
         // Click the copy icon of the mail
            Locator mailcopyIcon = page.locator("(//p[text()='Brand Endorsement']//following::img[@alt='copyIcon'])[1]");
            mailcopyIcon.click();
            
            // Get clipboard content
            Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copiedMail = (String) clipboard2.getData(DataFlavor.stringFlavor);
            
            System.out.println("Copied mail: "+ copiedMail );

            
            //click the copy icon of the phone Number
            Locator phonecopyIcon = page.locator("(//p[text()='Brand Endorsement']//following::img[@alt='copyIcon'])[2]");
            phonecopyIcon.click();
            
            
            // Get clipboard content
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String copiedNumber = (String) clipboard.getData(DataFlavor.stringFlavor);

            System.out.println("Copied number: " + copiedNumber);
            
            
            

            // Assertion: check if the copied value is digits only (valid number)
            Assert.assertTrue(copiedNumber.matches("\\d+"), "Copied value is not a valid number: " + copiedNumber);
            acceptRequest.brandEnodorsementViewMessage().click();
            String modalText = acceptRequest.modalContent().textContent();
            
            
            // Now use mentor page as usual
            page.navigate(prop.getProperty("url"));

            
            LandingPage login= new LandingPage(page);
            login.dismissPopup();
            login.clickLoginButton();
            
         
               LoginPage loginPage1 = new LoginPage(page);
               loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
         
               
               //click on my interest tab and get the card details
               page.locator("//span[text()='My Interest']").click();
               page.locator("//div[@class='tab text-center w-50 ms-1 pointer ']").click();
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
               
               
               
               
               
               
              
            
            
            


            
          
            
            

        }
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }}
	
	
	
}
