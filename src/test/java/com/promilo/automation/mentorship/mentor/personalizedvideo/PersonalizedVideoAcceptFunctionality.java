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
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
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
	            "com.promilo.automation.mentorship.mentee.intrests.RequestVideoTest.mentorshipShortListFunctionalityTest"
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
            loginPage.loginMailPhone().fill("92466825@qtvjnqv9.mailosaur.net");
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
               loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
               loginPage1.loginWithOtp().click();
               loginPage1.otpField().fill("9999");
               loginPage1.loginButton().click();
               
               
               
               MentorshipMyintrest validation= new MentorshipMyintrest(advertiserPage);
               validation.myInterestTab().click();
               validation.experianceString().isVisible();
               validation.experianceValue().isVisible();
               validation.serviceIcon().isVisible();
               validation.serviceName().isVisible();
               
               
               
                           // Locate the element
               Locator interestStatus = validation.statusTag();

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
               hamburger2.Mystuff().click();
               hamburger2.MyAccount().click();
               
                
               MentorMyBilling menteebillingValidation2= new MentorMyBilling(advertiserPage);
               menteebillingValidation2.myBillingButton().click();
               System.out.println(menteebillingValidation2.billingtableHead().textContent()); 
               System.out.println(menteebillingValidation2.billingData().textContent()); 
            

               Thread.sleep(3000);
               advertiserPage.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("In-App-Notification-Icon")).click();
             	System.out.println(advertiserPage.locator("//h6[contains(text(),'Great news! Your Personalized Video Message request has been accepted')]").textContent()); 
             	
             	
             	advertiserPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
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
                    "//p[contains(text(),'Hi mentor , Congratulations on completing a for Personalized Video request! You’ve earned ')]"
                ).first();
                congratsMessage.click();
                
                advertiserPage.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']//*[name()='path' and contains(@fill,'currentCol')]").click();

                String congratsText = congratsMessage.textContent().trim();
                System.out.println("🎉 Notification Message: " + congratsText);
                
                
                
        }
                
                
                

                        
        
        
        
        
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }
	    }}
