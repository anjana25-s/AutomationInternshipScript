package com.promilo.automation.mentorship.mentor.personalizedvideo;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
<<<<<<< HEAD
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class PersonalizedVideoAcceptFunctionality extends Baseclass{
	
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
	            "com.promilo.automation.mentorship.mentee.pagepbjects.RequestVideoTest.mentorshipShortListFunctionalityTest"
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
            acceptRequest.myAcceptance().click();
            acceptRequest.personalizedVideoMessageAccept().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            page.locator("//img[@class='pointer close-icon']").click();
            page.locator("(//p[text()='Personalized Video Message']//following::span[text()='Upload Video'])[1]").click();
            page.waitForTimeout(3000);
            Locator upload=page.locator("//img[@alt='attachment']");
            upload.click();
            upload.setInputFiles(Paths.get("C:\\Users\\Admin\\Documents\\Bandicam\\Assignment-issue.mp4"));
            
            acceptRequest.messageSendbutton().first().click();
            acceptRequest.chatExitIcon().click();
            page.locator("//span[text()='View Content']").first().click();
            System.out.println(acceptRequest.modalContent().textContent());
            
            

            
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("close chat popup")).first().click();

            
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            
            MentorMyBilling billingValidation= new MentorMyBilling(page);
            billingValidation.myBillingButton().click();
            System.out.println(billingValidation.billingtableHead().textContent()); 
            System.out.println(billingValidation.billingData().textContent()); 
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();

            
            

            
            
            
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
               Thread.sleep(3000);
            // Locate the element
               Locator interestStatus = advertiserPage.locator("[class='btn-blue-outlined filled my-interest-status-tag']");

               // Wait until it's visible (ensures page is ready)
               interestStatus.waitFor();

               // Assert visibility and print message
               Assert.assertTrue(interestStatus.isVisible(), "❌ Interest Status tag is not displayed");

               // Print result if visible
               if (interestStatus.isVisible()) {
                   System.out.println("✅ Interest Status tag is displayed: " + interestStatus.textContent());
               } else {
                   System.out.println("❌ Interest Status tag is not displayed");
               }
               
               
               
               
               
               
               
               Hamburger hamburger2 = new Hamburger(advertiserPage);
=======
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class PersonalizedVideoAcceptFunctionality extends BaseClass{
	
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
            acceptRequest.personalizedVideoMessageAccept().click();
            System.out.println(acceptRequest.modalContent().textContent()); 
            page.locator("//img[@class='pointer close-icon']").click();
            page.locator("(//p[text()='Personalized Video Message']//following::span[text()='Upload Video'])[1]").click();
            page.waitForTimeout(3000);
            Locator upload=page.locator("//img[@alt='attachment']");
            upload.click();
            upload.setInputFiles(Paths.get("C:\\Users\\Admin\\Documents\\Bandicam\\Assignment-issue.mp4"));
            
            acceptRequest.messageSendbutton().first().click();
            acceptRequest.chatExitIcon().click();
            page.locator("//span[text()='View Content']").first().click();
            System.out.println(acceptRequest.modalContent().textContent());
            
            

            
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("close chat popup")).first().click();

            
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            
            MentorMyBilling billingValidation= new MentorMyBilling(page);
            billingValidation.myBillingButton().click();
            System.out.println(billingValidation.billingtableHead().textContent()); 
            System.out.println(billingValidation.billingData().textContent()); 
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();

            
            

            
            
            
            // Create a new context
            Browser actualBrowser = browser.get();
            BrowserContext advertiserContext = actualBrowser.newContext();
            Page advertiserPage = advertiserContext.newPage();

            // Now use advertiserPage as usual
            advertiserPage.navigate(prop.getProperty("url"));

            
            LandingPage login= new LandingPage(advertiserPage);
            login.dismissPopup();
            login.clickLoginButton();
            
         
               // Login as advertiser (different MailSaur email)
               LoginPage loginPage1 = new LoginPage(advertiserPage);
               loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
               
               
               
               
               advertiserPage.locator("//span[text()='My Interest']").click();              
               Thread.sleep(3000);
            // Locate the element
               Locator interestStatus = advertiserPage.locator("[class='btn-blue-outlined filled my-interest-status-tag']");

               // Wait until it's visible (ensures page is ready)
               interestStatus.waitFor();

               // Assert visibility and print message
               Assert.assertTrue(interestStatus.isVisible(), "❌ Interest Status tag is not displayed");

               // Print result if visible
               if (interestStatus.isVisible()) {
                   System.out.println("✅ Interest Status tag is displayed: " + interestStatus.textContent());
               } else {
                   System.out.println("❌ Interest Status tag is not displayed");
               }
               
               
               
               
               
               
               
               MyResumePage hamburger2 = new MyResumePage(advertiserPage);
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
               hamburger2.Mystuff().click();
               hamburger2.MyAccount().click();
               
                
               MentorMyBilling menteebillingValidation2= new MentorMyBilling(advertiserPage);
               menteebillingValidation2.myBillingButton().click();
               System.out.println(menteebillingValidation2.billingtableHead().textContent()); 
               System.out.println(menteebillingValidation2.billingData().textContent()); 
            

               Thread.sleep(3000);
               advertiserPage.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();
             	System.out.println(advertiserPage.locator("//h6[contains(text(),'Great news! Your Personalized Video Message request has been accepted')]").textContent()); 
             	
             	
             	advertiserPage.navigate("https://mailosaur.com/app/servers/8mgfvj1x/messages/inbox");
                MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(advertiserPage);
                mailsaurCredenatinals.MialsaurMail();
                mailsaurCredenatinals.MailsaurContinue();
                mailsaurCredenatinals.MailsaurPassword();
                mailsaurCredenatinals.MailsaurLogin();
                
                
                
                advertiserPage.locator("//p[contains(text(),'Your Personalized Video Message Request')]").first().click();
                System.out.println(advertiserPage.locator("//span[contains(text(),'Greetings')]").textContent()); 
                System.out.println(advertiserPage.locator("//p[contains(text(),'We are happy to inform you')]").textContent());
                System.out.println(advertiserPage.locator("//p[contains(text(),'Please feel free to share any')]").textContent());
                System.out.println(advertiserPage.locator("//button[contains(text(),'Accepted')]").isVisible());
                advertiserPage.locator("//span[contains(text(),'Interest')]").click();
                advertiserPage.waitForTimeout(1000);
                advertiserPage.bringToFront();
                
                
                
                
                advertiserPage.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']//*[name()='path' and contains(@fill,'currentCol')]").click();
             // ✅ Validate "Congratulations" notification
                Locator congratsMessage = advertiserPage.locator(
                    "//p[contains(text(),'Congratulations on completing a for service name request! You’ve earned ')]"
                ).first();
                congratsMessage.waitFor();
                String congratsText = congratsMessage.textContent().trim();
                System.out.println("🎉 Notification Message: " + congratsText);
                
                
                

                // Assertion
                Assert.assertTrue(congratsText.contains("Congratulations on completing"),
                        "❌ Expected 'Congratulations' message not found. Actual: " + congratsText);
                System.out.println("✅ 'Congratulations' message validation passed.");
                
                
                
                

                // ✅ Get corresponding timestamp for "Congratulations" message
                Locator congratsTime = advertiserPage.locator(
                    "(//p[contains(text(),'Hi mentor , Congratulations on completing a for service name request! You’ve earned ')]" +
                    "/following::p[@class='font-normal whitespace-nowrap'])[1]"
                );
                if (congratsTime.count() > 0) {
                    String timeText = congratsTime.first().textContent().trim();
                    System.out.println("⏱️ Timestamp for Congratulations message: " + timeText);
                } else {
                    System.out.println("ℹ️ No timestamp found for Congratulations message.");
                }



                // ✅ Validate "Waiting! Time to Record Your Video" notification
                Locator waitingMessage = advertiserPage.locator(
                    "//p[contains(text(),'Waiting! Time to Record Your Video')]"
                ).first();
                waitingMessage.waitFor();
                String waitingText = waitingMessage.textContent().trim();
                System.out.println("🎬 Notification Message: " + waitingText);

                // Assertion
                Assert.assertTrue(waitingText.contains("Waiting! Time to Record Your Video"),
                        "❌ Expected 'Waiting! Time to Record Your Video' message not found. Actual: " + waitingText);
                System.out.println("✅ 'Waiting! Time to Record Your Video' message validation passed.");
                
                
                

                // ✅ Get corresponding timestamp for "Waiting" message
                Locator waitingTime = advertiserPage.locator(
                    "(//p[contains(text(),'Waiting! Time to Record Your Video')]" +
                    "/following::p[@class='font-normal whitespace-nowrap'])[1]"
                );
                if (waitingTime.count() > 0) {
                    String timeText = waitingTime.first().textContent().trim();
                    System.out.println("⏱️ Timestamp for Waiting message: " + timeText);
                } else {
                    System.out.println("ℹ️ No timestamp found for Waiting message.");
                }
        }
        
        
        
        
        
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }}
}
