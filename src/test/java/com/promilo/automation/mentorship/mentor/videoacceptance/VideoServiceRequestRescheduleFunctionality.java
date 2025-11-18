package com.promilo.automation.mentorship.mentor.videoacceptance;

import java.nio.file.Paths;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class VideoServiceRequestRescheduleFunctionality extends Baseclass{
	
	
	
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
	            "com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipBookMeeting.mentorshipbook"
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
                
                MyAcceptance acceptVideoRequest= new MyAcceptance(page);
                acceptVideoRequest.myAcceptance().click();
                acceptVideoRequest.videorescheduleButton().click();
                acceptVideoRequest.enableDate().first().click();
                acceptVideoRequest.availablesSlots().first().click();
                acceptVideoRequest.availablesSlots().nth(1).click();
                acceptVideoRequest.continueButton().click();
                
                System.out.println(acceptVideoRequest.modalContent().textContent());                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
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
                 
                 
                 Thread.sleep(4000);
                 
                 
                 
                 advertiserPage.locator("//span[text()='My Interest']").click();
                 Thread.sleep(4000);

                 advertiserPage.locator("img.rescheduled-notification-icon").click();
                 Thread.sleep(4000);


                 advertiserPage.locator(
                         "//div[@class='rescheduledSlots-time w-100 font-12 text-center mb-50 ']")
                         .first().click();
                 Thread.sleep(4000);


                 advertiserPage.locator("//button[text()='Accept Request']").click();

                 System.out.println(advertiserPage.locator("//div[@class='modal-content']").textContent()); 
                 
                 Thread.sleep(2000);
                 
                 
                 
                 
                 



                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                
                
            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (page != null) {
                    page.close();
                    test.info("🧹 Browser closed for current test row.");
                }
            }
        }
    }

}
