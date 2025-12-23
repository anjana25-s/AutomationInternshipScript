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
               
               
               
               
               
               
              
            
            
            


            
          
            
            

        }
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }}
	
	
	
}
