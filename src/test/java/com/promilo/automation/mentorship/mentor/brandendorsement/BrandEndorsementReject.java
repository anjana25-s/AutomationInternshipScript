package com.promilo.automation.mentorship.mentor.brandendorsement;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementReject extends com.promilo.automation.resources.BaseClass{
	
	
	
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
	            "com.promilo.automation.mentorship.mentee.intrests.BrandEndorsement.mentorshipBrandEndorsement"
	        } 
	    )
public void AcceptVideoServiceRequestTest() throws Exception {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("✅ Accept video service - Positive Test");

   
 // LOAD EXCEL
    String excelPath = Paths.get(System.getProperty("user.dir"),
            "Testdata", "Mentorship Test Data.xlsx").toString();
    ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");


    int totalRows = excel.getRowCount();
    if (totalRows < 2) {
        test.fail("❌ No data found in Excel.");
        Assert.fail("No data in Excel");
    }

    // BUILD HEADER MAP
    int totalCols = excel.getColumnCount();
    Map<String, Integer> headerMap = new HashMap<>();
    for (int c = 0; c < totalCols; c++) {
        String header = excel.getCellData(0, c);
        if (header != null && !header.trim().isEmpty()) {
            headerMap.put(header.trim().replace("\u00A0", "").toLowerCase(), c);
        }
    }

    // FIND ROWS WITH KEYWORD = AddEmployment (or your relevant keyword)
    for (int i = 1; i < totalRows; i++) {
        String keyword = excel.getCellData(i, headerMap.get("keyword"));
        if (!"BrandEndorsementReject".equalsIgnoreCase(keyword)) continue;

        String InputValue = excel.getCellData(i, headerMap.get("inputvalue"));
        String password = excel.getCellData(i, headerMap.get("password"));

        test.info("📘 Executing Excel row " + i + " | InputValue: " + InputValue);

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
            loginPage.loginMailPhone().fill(InputValue);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();
            test.info("✅ Logged in with credentials: " + InputValue);

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
