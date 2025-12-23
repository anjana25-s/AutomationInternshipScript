package com.promilo.automation.mentorship.mentor.downloadresources;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
import com.promilo.automation.mentorship.mentornotifications.MentorDownloadResourceNotifications;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class MentorDownloadResourceValidation extends BaseClass{
	
	
	
	
	
	
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
	            "com.promilo.automation.mentorship.mentee.BuyResourcesFunctionality.mentorshipShortListFunctionalityTest"
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
            
            
            MyAcceptance acceptRequest = new MyAcceptance(page);

         // Click on 'My Acceptance' section
            acceptRequest.myAcceptance().click();

            // Wait for and assert visible for each locator
            acceptRequest.menteeName().waitFor(); // waits until attached (default)
            Assert.assertTrue(acceptRequest.menteeName().isVisible(), "❌ Mentee Name is not displayed");
            System.out.println("Mentee Name: " + acceptRequest.menteeName().textContent());

            acceptRequest.resourcesCampaginname().waitFor();
            Assert.assertTrue(acceptRequest.resourcesCampaginname().isVisible(), "❌ Campaign Name is not displayed");
            System.out.println("Campaign Name: " + acceptRequest.resourcesCampaginname().textContent());

            acceptRequest.resourcesCampaign().waitFor();
            Assert.assertTrue(acceptRequest.resourcesCampaign().isVisible(), "❌ Campaign Resource is not displayed");
            System.out.println("Campaign Resource: " + acceptRequest.resourcesCampaign().textContent());

            acceptRequest.resourceMoney().waitFor();
            Assert.assertTrue(acceptRequest.resourceMoney().isVisible(), "❌ Resource Money is not displayed");
            System.out.println("Resource Money: " + acceptRequest.resourceMoney().textContent());
            
            
            
            
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();
            MentorMyBilling billingValidation= new MentorMyBilling(page);
            billingValidation.myBillingButton().click();
            System.out.println(billingValidation.billingtableHead().textContent()); 
            System.out.println(billingValidation.billingData().textContent()); 
            
            
            page.navigate("https://mailosaur.com/app/servers/8mgfvj1x/messages/inbox");
            
            MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
            mailsaurCredenatinals.MialsaurMail();
            mailsaurCredenatinals.MailsaurContinue();
            mailsaurCredenatinals.MailsaurPassword();
            mailsaurCredenatinals.MailsaurLogin(); 
            
            
            MentorDownloadResourceNotifications mailValidation= new MentorDownloadResourceNotifications(page);
            mailValidation.someoneDownloadedNotification().first().click();
            System.out.println(mailValidation.dearText().textContent()); 
            System.out.println(mailValidation.resourceDownloadedText().textContent()); 
            System.out.println(mailValidation.appreciatedText().textContent()); 
            mailValidation.viewResouceButton().click();
            
            page.waitForTimeout(3000);
            
            page.bringToFront();
            page.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']//*[name()='path' and contains(@fill,'currentCol')]").click();

            
            mailValidation.paymentNotification().first().click();
            System.out.println(mailValidation.hiText().textContent()); 
            System.out.println(mailValidation.congratulationsText().textContent()); 
            System.out.println(mailValidation.greateFullText().textContent()); 
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        }
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	

	
    }}
	
	
}
