package com.promilo.automation.mentorship.mentor.personalizedvideo;

import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentorship.menteenotifications.PersonalizedVideoEmailAndInAppNotifications;
import com.promilo.automation.mentorship.mentornotifications.PersonalizedVideoNotifications;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class PersonalizedVideoRejectFunctionality extends BaseClass{
	
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
	            "com.promilo.automation.mentorship.mentee.RequestVideoTest.mentorshipShortListFunctionalityTest"
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
            acceptRequest.myAcceptance().click();
            acceptRequest.persoanlizedVideoMessageReject().click();
            acceptRequest.proceedButton().click();
            
            System.out.println(page.locator("//p[text()='Personalized Video Message']//following::span[text()='Rejected']").first().textContent()); 
            Thread.sleep(2000);
            
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();
            PersonalizedVideoNotifications inNotification= new PersonalizedVideoNotifications(page);
            System.out.println(inNotification.declinedInappNotifications().first().textContent()); 

            
            
            
            
            
         // Create a new context
            Browser actualBrowser = browser.get();
            BrowserContext advertiserContext = actualBrowser.newContext();
            Page advertiserPage = advertiserContext.newPage();

            // Now use advertiserPage as usual
            advertiserPage.navigate(prop.getProperty("url"));

            
            LandingPage Menteelogin= new LandingPage(advertiserPage);
            Menteelogin.dismissPopup();
            Menteelogin.clickLoginButton();
            
         
               // Login as advertiser (different MailSaur email)
               LoginPage loginPage1 = new LoginPage(advertiserPage);
               loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
               
               
               
               
               
               advertiserPage.locator("//span[text()='My Interest']").click();
               
               
               
               advertiserPage.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();
               PersonalizedVideoEmailAndInAppNotifications menteeInNotification= new PersonalizedVideoEmailAndInAppNotifications(advertiserPage);
               System.out.println(menteeInNotification.RejectedInAppNotification().first().textContent()); 

            
            
               
               advertiserPage.navigate("https://mailosaur.com/app/servers/8mgfvj1x/messages/inbox");
               
               MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(advertiserPage);
               mailsaurCredenatinals.MialsaurMail();
               mailsaurCredenatinals.MailsaurContinue();
               mailsaurCredenatinals.MailsaurPassword();
               mailsaurCredenatinals.MailsaurLogin();
               
               
               
               PersonalizedVideoEmailAndInAppNotifications MailValidations= new PersonalizedVideoEmailAndInAppNotifications(advertiserPage);
               MailValidations.rejectedNotification().first().click();
               System.out.println(MailValidations.greetingText().textContent()); 
               System.out.println(MailValidations.regretText().textContent()); 
               System.out.println(MailValidations.refundNote().textContent()); 
               
               
               MailValidations.mentorshipsButton().click();
               advertiserPage.waitForLoadState();
               
               
               advertiserPage.bringToFront();
               advertiserPage.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']//*[name()='path' and contains(@fill,'currentCol')]").click();
               

               PersonalizedVideoNotifications mentorMailValidation= new PersonalizedVideoNotifications(advertiserPage);
               mentorMailValidation.RejectedNotification().first().click();
               System.out.println(mentorMailValidation.dearText().textContent()); 
               System.out.println(mentorMailValidation.declinedMessage().textContent()); 
               mentorMailValidation.rejectedButton().isVisible();
               
               
               mentorMailValidation.exploreOpportunities().click();
               advertiserPage.waitForLoadState();
               
               
            
        }
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }}
}
