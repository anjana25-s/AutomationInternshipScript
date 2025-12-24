package com.promilo.automation.mentorship.mentor.oneononecallacceptance;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
<<<<<<< HEAD
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
import com.promilo.automation.mentorship.mentornotifications.MentroOneOnOneCall;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class OneOnOneCallAcceptFunctionality extends Baseclass {

	
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
	            "com.promilo.automation.mentorship.mentee.pagepbjects.GetMentorCall.mentorshipShortListFunctionalityTest"
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

            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            
            MyAcceptance acceptRequest= new MyAcceptance(page);
            acceptRequest.oneOnoneCallAcceptButton().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            


            page.locator("img[alt='Add New']").click();
            
            MentorMyBilling billingValidation= new MentorMyBilling(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            billingValidation.myBillingButton().click();
            System.out.println(billingValidation.billingtableHead().textContent()); 
          System.out.println(billingValidation.billingData().textContent()); 
            
            
            page.waitForTimeout(3000);
            
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();
           
            
            MentroOneOnOneCall notification= new MentroOneOnOneCall(page);
            System.out.println(notification.contactDetailsUnlocked().first().textContent()); 
            //System.out.println(notification.earnedNotifications().first().textContent()); 

            
            page.navigate("https://mailosaur.com/app/servers/8mgfvj1x/messages/inbox");
            
            MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
            mailsaurCredenatinals.MialsaurMail();
            mailsaurCredenatinals.MailsaurContinue();
            mailsaurCredenatinals.MailsaurPassword();
            mailsaurCredenatinals.MailsaurLogin();
            
            
            MentroOneOnOneCall mailNotification=  new MentroOneOnOneCall(page);
            Thread.sleep(3000);
            mailNotification.detailUnlockedNotification().first().click();
            System.out.println(mailNotification.dearText().textContent()); 
            System.out.println(mailNotification.acceptedText().textContent());
            System.out.println(mailNotification.whatNextText().textContent()); 
            System.out.println(mailNotification.reachoutText().textContent()); 
            mailNotification.viewContactsButton().click();
            

            
            
            
            

            

            
            
            
            
            // Create a new context
            Browser actualBrowser = browser.get();
            BrowserContext advertiserContext = actualBrowser.newContext();
            Page advertiserPage = advertiserContext.newPage();

            // Now use advertiserPage as usual
            advertiserPage.navigate(prop.getProperty("url"));

            
            MayBeLaterPopUp login= new MayBeLaterPopUp(advertiserPage);
            login.dismissPopup();
            login.clickLoginButton();
            
         
               // Login as advertiser (different MailSaur email)
               LoginPage loginPage1 = new LoginPage(advertiserPage);
               loginPage1.loginMailPhone().fill(Baseclass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
         
               
               
               advertiserPage.locator("//span[text()='My Interest']").click();
               
               
               
               
               Hamburger hamburger2 = new Hamburger(advertiserPage);
               hamburger2.Mystuff().click();
               hamburger2.MyAccount().click();

               
               
               
               MentorMyBilling menteebillingValidation= new MentorMyBilling(advertiserPage);
=======
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
import com.promilo.automation.mentorship.mentornotifications.MentroOneOnOneCall;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class OneOnOneCallAcceptFunctionality extends BaseClass {

	
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
	            "com.promilo.automation.mentorship.mentee.GetMentorCall.mentorshipShortListFunctionalityTest"
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

            MyResumePage hamburger = new MyResumePage(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            
            MyAcceptance acceptRequest= new MyAcceptance(page);
            acceptRequest.oneOnoneCallAcceptButton().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            


            page.locator("img[alt='Add New']").click();
            
            MentorMyBilling billingValidation= new MentorMyBilling(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            billingValidation.myBillingButton().click();
            System.out.println(billingValidation.billingtableHead().textContent()); 
          System.out.println(billingValidation.billingData().textContent()); 
            
            
            page.waitForTimeout(3000);
            
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();
           
            
            MentroOneOnOneCall notification= new MentroOneOnOneCall(page);
            System.out.println(notification.contactDetailsUnlocked().first().textContent()); 
            //System.out.println(notification.earnedNotifications().first().textContent()); 

            
            page.navigate("https://mailosaur.com/app/servers/8mgfvj1x/messages/inbox");
            
            MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
            mailsaurCredenatinals.MialsaurMail();
            mailsaurCredenatinals.MailsaurContinue();
            mailsaurCredenatinals.MailsaurPassword();
            mailsaurCredenatinals.MailsaurLogin();
            
            
            
            MentroOneOnOneCall mailNotification=  new MentroOneOnOneCall(page);
            Thread.sleep(3000);
            mailNotification.detailUnlockedNotification().first().click();
            System.out.println(mailNotification.dearText().textContent()); 
            System.out.println(mailNotification.acceptedText().textContent());
            System.out.println(mailNotification.whatNextText().textContent()); 
            System.out.println(mailNotification.reachoutText().textContent()); 
            mailNotification.viewContactsButton().click();
            

            
            
            
            

            

            
            
            
            
                        // Now use page as usual
            page.navigate(prop.getProperty("url"));

            
            LandingPage login= new LandingPage(page);
            login.dismissPopup();
            login.clickLoginButton();
            
         
               // Login as advertiser (different MailSaur email)
               LoginPage loginPage1 = new LoginPage(page);
               loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
         
               
               
               page.locator("//span[text()='My Interest']").click();
               
               
               
               
               MyResumePage hamburger2 = new MyResumePage(page);
               hamburger2.Mystuff().click();
               hamburger2.MyAccount().click();

               
               
               
               MentorMyBilling menteebillingValidation= new MentorMyBilling(page);
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
               menteebillingValidation.myBillingButton().click();
               System.out.println(menteebillingValidation.billingtableHead().textContent()); 
               String billingText = menteebillingValidation.billingData().textContent();
               System.out.println("Billing Text: " + billingText);

               Assert.assertTrue(billingText.contains("One On One Call"),
                       "❌ Validation failed: Expected text 'One On One Call' not found in billing data. Actual text: " + billingText);

               System.out.println("✅ Validation passed: 'One On One Call' is present in billing data.");
               
               

              
              
              
               
            
            
            
            
            
        }
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
	
        
    }}
}
