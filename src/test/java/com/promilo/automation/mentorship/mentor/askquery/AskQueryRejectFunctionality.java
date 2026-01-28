package com.promilo.automation.mentorship.mentor.askquery;

import static org.testng.Assert.assertEquals;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class AskQueryRejectFunctionality extends BaseClass {

	
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
	            "com.promilo.automation.mentorship.mentee.intrests.MentorshipAskQueryPaid.mentorshipShortListFunctionalityTest"
	        } 
	    )
public void AcceptVideoServiceRequestTest() throws Exception {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("✅ Accept video service - Positive Test");

   
    

    test.info("🌐 Navigated to application URL.");

 
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        try {
            // HANDLE OPTIONAL POPUP
            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            try { popup.getPopup().click(); test.info("✅ Popup closed"); } catch (Exception ignored) {}

            popup.clickLoginButton();

            // LOGIN
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("92466825@qtvjnqv9.mailosaur.net");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();
            test.info("✅ Logged in with credentials: " );

            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            
            
            MyAcceptance acceptRequest= new MyAcceptance(page);
            acceptRequest.myAcceptance().click();
            acceptRequest.askQueryReject().click();
            Thread.sleep(3000);
            acceptRequest.proceedButton().click();
            page.waitForTimeout(3000);
            Assert.assertTrue(page.locator("//div[text()='Rejected']").isVisible(),
                    "'Rejected' status should be visible on the page");
            
            
            
            
            
            
            page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
            mailsaurCredenatinals.MialsaurMail();
            mailsaurCredenatinals.MailsaurContinue();
            mailsaurCredenatinals.MailsaurPassword();
            mailsaurCredenatinals.MailsaurLogin();
            
            page.locator("//p[contains(text(),'You Declined December Query—That’s Perfectly Fine!')]").first().click();
            System.out.println(page.locator("//span[@class='tinyMce-placeholder']").textContent());
            System.out.println(page.locator("//p[contains(text(),'ve chosen to decline the')]").textContent());
            System.out.println(page.locator("//p[contains(text(),'There are always more queries')]").textContent()); 
            page.locator("//tbody/tr/td[@class='pad']/div[@class='alignment']/a[@class='button']/span[1]").click();
            
            
            
            
            
            
            
            
            
           
            
            
            

            
            
            
            
           
            
            
            
            
        
            
            
            
            
            
        }
        
        catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // No variable `i`, so no row failure reporting
        }

	
	
        
    }}

