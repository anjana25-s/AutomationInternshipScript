package com.promilo.automation.emailnotifications.user.gethrcall;

import java.nio.file.Paths;
import java.util.Random;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.emailnotifications.CancelMeeting;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ShortlistEmailnotificationValidation extends Baseclass{
ExtentReports extent = ExtentManager.getInstance();
	    ExtentTest test = extent.createTest("🚀 Advertiser Add Funds Test | Data-Driven");

	    @Test
	    public void runAddFundsTest() {
	        try {
	            // Step 1: Load Excel Data
	            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
	                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
	            test.info("📂 Loaded Excel data from: " + excelPath);

	            // Step 2: Admin Login
	            Page page = initializePlaywright();
	            page.navigate(prop.getProperty("url"));
	            page.setViewportSize(1000, 768);
	            test.info("✅ Navigated to: " + prop.getProperty("url"));
	            
	            LandingPage landingPage= new LandingPage(page);
	           
	                landingPage.getPopup().click();

	            landingPage.clickLoginButton();

	            LoginPage loginPage = new LoginPage(page);
	            loginPage.loginMailPhone().fill("warm-apart@ofuk8kzb.mailosaur.net");
	            loginPage.passwordField().fill("Karthik@88");
	            loginPage.loginButton().click();

	         
	            JobListingPage homePage = new JobListingPage(page);
	            homePage.homepageJobs().click();
	            page.waitForTimeout(2000);
	            homePage.jobShortlist1().first().click();
	            page.waitForTimeout(2000);

	            page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']").fill("karthik");

	            Random random = new Random();
	            String randomMobile = "90000" + String.format("%05d", random.nextInt(100000));
	            page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']").fill(randomMobile);

	            page.waitForTimeout(4000);
	            homePage.jobShortList().click();
	            
	            
	            

	            
	        
	        
	        }catch (Exception e) {
    test.fail("💥 Test failed due to exception: " + e.getMessage());
    e.printStackTrace();
} finally {
    extent.flush();
}
}
}




