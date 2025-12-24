package com.promilo.automation.mentorship.mentor.campaign;

<<<<<<< HEAD
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorDeleteCampaign extends Baseclass {
	
	
	
	 @Test
	    public void duplication() throws Exception {

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
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorDeleteCampaign extends BaseClass {
	
	
	
	 @Test
	    public void duplication() throws Exception {

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
	                
	                
	                //duplicating or copy the campaign
	                CampaignlistPage duplication= new CampaignlistPage(page);
	                duplication.checkBox().first().click();
	                duplication.deletedButton().click();
	                
	                
	                Locator codeElement = page.locator("//span[contains(text(), 'Please enter this code')]");
	                String fullText = codeElement.textContent();
	                String otpCode = fullText.replaceAll("[^0-9]", "");
	                test.info("🔢 Extracted OTP code: " + otpCode);
	                System.out.println("Extracted OTP code: " + otpCode);

	                for (int j = 0; j < otpCode.length(); j++) {
	                    char digit = otpCode.charAt(j);
	                    String locatorXpath = String.format("//input[@aria-label='Please enter OTP character %d']", j + 1);
	                    Locator otpInput = page.locator(locatorXpath);
	                    otpInput.fill(String.valueOf(digit));
	                }
	                
	                
	                test.info("🔑 Entered OTP digits for folder deletion");
	                System.out.println("Entered OTP digits");
	                
	                
	                duplication.popupDeleteButton().click();
	                System.out.println(duplication.deletedToaster().textContent()); 
	                boolean isToasterVisible = duplication.deletedToaster().isVisible();
	                System.out.println("🔥 Toaster displayed: " + isToasterVisible);

	                // ✅ Assertion to check toaster is displayed
	                Assert.assertTrue(isToasterVisible, "❌ Toaster was not displayed after action.");
	                
	                
	                
	                
	                
	            }
	            
	            
	                
	                
	                catch (Exception e) {
	                    test.fail("❌ Error in row " + i + ": " + e.getMessage());
	                    throw e;
	                }      
	        }}}
