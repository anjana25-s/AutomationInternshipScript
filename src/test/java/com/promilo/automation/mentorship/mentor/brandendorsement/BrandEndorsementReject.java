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
import com.microsoft.playwright.Page.LocatorOptions;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementReject extends com.promilo.automation.resources.Baseclass{
	
	
	
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
	            "com.promilo.automation.mentorship.mentee.intrests.pagepbjects.BrandEndorsement.mentorshipBrandEndorsement"
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
            acceptRequest.brandEndorsementReject().click();
            acceptRequest.proceedButton().click();
            System.out.println(page.locator("//p[text()='Brand Endorsement']//following::span[text()='Rejected']").first().textContent()); 
            
          


            
          
            
            

        }
        
        
        catch (Exception e) {
            test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
            e.printStackTrace();
        }
	
    }}
	
	
	
}
