package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
<<<<<<< HEAD
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorCampaignListSearchAndFilter extends Baseclass{
	
	
	  @Test
	    public void addEmploymentPositiveTest() throws Exception {

	        ExtentReports extent = ExtentManager.getInstance();
	        ExtentTest test = extent.createTest("✅ Add Employment - Positive Test");

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
	                loginPage.loginMailPhone().fill("testradha68@yopmail.com");
	                loginPage.passwordField().fill("Karthik@88");
	                loginPage.loginButton().click();
	                test.info("✅ Logged in with registered credentials.");

	                // Navigate to My Resume
	                Hamburger resumePage = new Hamburger(page);
=======
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorCampaignListSearchAndFilter extends BaseClass{
	
	
	  @Test
	    public void addEmploymentPositiveTest() throws Exception {

	        ExtentReports extent = ExtentManager.getInstance();
	        ExtentTest test = extent.createTest("✅ Add Employment - Positive Test");

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
	                loginPage.loginMailPhone().fill("testradha68@yopmail.com");
	                loginPage.passwordField().fill("Karthik@88");
	                loginPage.loginButton().click();
	                test.info("✅ Logged in with registered credentials.");

	                // Navigate to My Resume
	                MyResumePage resumePage = new MyResumePage(page);
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
	                resumePage.Mystuff().click();

	                // Become a Mentor actions
	                BecomeMentor becomeMentor = new BecomeMentor(page);
	                becomeMentor.becomeMentorButton().click();
	                becomeMentor.campaignList().click();
	                
	                
	                CampaignlistPage SearchAndFilter= new CampaignlistPage(page);
	                SearchAndFilter.searchField().fill("Health");
	                SearchAndFilter.searchIcon().click();
	                System.out.println(SearchAndFilter.campaignName().first().textContent()); 	    
	                
	                
	                CampaignlistPage dataValidation= new CampaignlistPage(page);
	                System.out.println(dataValidation.createdDate().first().textContent()); 
	                System.out.println(dataValidation.createdDate().nth(1).textContent()); 
	                System.out.println(dataValidation.createdDate().nth(2).textContent()); 


	                dataValidation.campaignStatus().first().textContent();
	             // First row
	                String firstRowText = page.locator("//tr").first().textContent();

	                // Split by spaces
	                String[] sections = firstRowText.split("\\s+");

	                // Print each section with a gap
	                for (String section : sections) {
	                    System.out.print(section + "   ");  // 3 spaces gap between each word
	                }

	                System.out.println(); // new line after the row

	                // Second row
	                String secondRowText = page.locator("//tr").nth(1).textContent();
	                String[] sections2 = secondRowText.split("\\s+");

	                for (String section : sections2) {
	                    System.out.print(section + "   ");  // same gap
	                }

	                System.out.println();
	                
	                
	                
	                
	                //Filter Section
	                
	                CampaignlistPage filter= new CampaignlistPage(page);
	                page.locator("//button[text()='Start Date - End Date']").click();
	                filter.fromTextfield().click();
	                page.locator("//span[text()='1']").first().click();
	                filter.toTextfield().click();
	                page.locator("//span[text()='1']").first().click();
	                
	                
	                
	                
	                
	                
	                
	                
	                
	                
	             // Get all elements
	                int count = page.locator("//span[@class='rdrStaticRangeLabel']").count();

	                // Iterate and click each
	                for (int range = 0; range < count; range++) {
	                    // Get the range label text
	                    String rangeLabel = page.locator("//span[@class='rdrStaticRangeLabel']").nth(range).textContent().trim();

	                    // Click the range button
	                    page.locator("//span[@class='rdrStaticRangeLabel']").nth(range).click();
	                    
	                    // Optional wait if UI updates dynamically
	                    page.waitForTimeout(500); // 0.5 second

	                    // Validation: count rows starting from nth(1)
	                    int totalRows = page.locator("//tr").count();
	                    int rowsFromSecond = totalRows - 1; // include nth(1) itself

	                    System.out.println("After clicking \"" + rangeLabel + "\": Rows from nth(1) = " + rowsFromSecond);
	                }


	                
	                
	                
	                
	                
	                
	                
	                
	                
	            

	                
	                

	                
	                
	                
	                
	                
	                
	            }
	            
	            
	            
	            catch (Exception e) {
	                test.fail("❌ Error in row " + i + ": " + e.getMessage());
	                throw e;
	            }
	
	        }}
}
