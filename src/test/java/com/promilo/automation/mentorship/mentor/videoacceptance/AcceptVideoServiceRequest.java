package com.promilo.automation.mentorship.mentor.videoacceptance;

import java.io.IOException;
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
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AcceptVideoServiceRequest extends BaseClass {
	
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
	            "com.promilo.automation.mentorship.mentee.intrests.MentorshipBookMeeting.mentorshipbook"
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
                
                MyAcceptance acceptVideoRequest= new MyAcceptance(page);
                acceptVideoRequest.myAcceptance().click();
                acceptVideoRequest.videocallAcceptButton().click();
                 System.out.println(acceptVideoRequest.modalContent().textContent()); 
                   
                 page.locator("img[alt='Add New']").click();

                 MentorMyBilling billingValidation = new MentorMyBilling(page);
                 hamburger.Mystuff().click();
                 hamburger.MyAccount().click();

                 billingValidation.myBillingButton().click();
                 
                 
                 
                 

                 // Get text values
                 String tableHeadText = billingValidation.billingtableHead().textContent().trim();
                 String billingDataText = billingValidation.billingData().textContent().trim();

                 // Print values (as you already do)
                 System.out.println(tableHeadText);
                 System.out.println(billingDataText);

                 // ================== ADD ASSERTIONS ==================

                 Assert.assertFalse(
                         tableHeadText.isEmpty(),
                         "❌ Billing table header text is empty!"
                 );

                 Assert.assertFalse(
                         billingDataText.isEmpty(),
                         "❌ Billing data text is empty!"
                 );

                 // Example FIXED values (if you expect exact header values)
                 Assert.assertTrue(
                         tableHeadText.contains("Invoice") 
                         || tableHeadText.contains("Billing") 
                         || tableHeadText.contains("Amount"),
                         "❌ Billing table header does not contain expected keywords!"
                 );

                 // If billing data must contain a number or amount
                 Assert.assertTrue(
                         billingDataText.length() > 0,
                         "❌ Billing data section returned empty!"
                 );

                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
              // Create a new context
              Browser actualBrowser = browser.get();
              BrowserContext advertiserContext = actualBrowser.newContext();
              Page advertiserPage = advertiserContext.newPage();

              
              page.waitForTimeout(3000);
              // Now use mentee as usual
              advertiserPage.navigate(prop.getProperty("url"));
              
              advertiserPage.waitForTimeout(5000);
              
              MayBeLaterPopUp mayBeLaterPopUp2 = new MayBeLaterPopUp(advertiserPage);
              try {
            	  mayBeLaterPopUp2.getPopup().click();
                  test.info("✅ Popup closed.");
              } catch (Exception ignored) {
                  test.info("ℹ️ No popup found.");
              }

              mayBeLaterPopUp2.clickLoginButton();              

              
                 // Login as mentee (different MailSaur email)
                 LoginPage loginPage1 = new LoginPage(advertiserPage);
                 loginPage1.loginMailPhone().fill(BaseClass.generatedPhone); // use the generated email
                 loginPage1.loginWithOtp().click();
                 loginPage1.otpField().fill("9999");
                 loginPage1.loginButton().click();
                 
                 
                 
                 
                 MentorshipMyintrest validation= new MentorshipMyintrest(advertiserPage);
                 Thread.sleep(3000);

                 validation.myInterestTab().click();
                 Locator interestStatus = validation.statusTag();
                 interestStatus.waitFor();
                 Assert.assertTrue(interestStatus.isVisible(), "❌ Interest Status tag is not displayed");

                 // Print result if visible
                 if (interestStatus.isVisible()) {
                     System.out.println("✅ Interest Status tag is displayed: " + interestStatus.textContent());
                 } else {
                     System.out.println("❌ Interest Status tag is not displayed");
                 }
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                 
                
                
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
