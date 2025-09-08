package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.*;

public class Signup extends Baseclass {

    @Test
    public void signup() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        //Test Description
        ExtentTest test = extent.createTest("🚀 Promilo Staging Signup - Passes if 'My Stuff' is visible after signup (Playwright)");
  
        //path of the excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> signupKeywords = Collections.singleton("ValidSignup");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String otp = excel.getCellData(i, 5);
            String password = excel.getCellData(i, 6);
            
            //fetch the testcaseID and keword from the excel

            if (!signupKeywords.contains(keyword)) {
                continue;
            }


            //launch and maximize  the browser
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            
            Thread.sleep(5000);
            
            
            //click on pop-up present in the landing page

            try {
                LandingPage landingPage = new LandingPage(page);

                // Close popup if present
                try {
                    landingPage.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup if present.");
                    Thread.sleep(5000);
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup to close.");
                }
                

                landingPage.clickSignup();

                
                //enter the mail which fetched from the excel
                CreateAccountpage accountPage = new CreateAccountpage(page);

             // Generate a new random email starting with 'testpromilo'
                String randomEmail = "testpromilo" + System.currentTimeMillis() + (int)(Math.random() * 1000) + "@mailinator.com";
                accountPage.getPhoneMailTextField().fill(randomEmail);
                test.info("✍️ Entered Email: " + randomEmail);

                

                //click on send verification code button
                accountPage.getSendCodeButton().click();
                test.info("📨 Clicked Send Code.");

                
                //enter the otp which fetch from the excel
                if (otp == null || otp.trim().isEmpty()) {
                    test.fail("❌ OTP required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("OTP missing for signup: " + testCaseId);
                }
                accountPage.getOtpField().fill(otp);
                test.info("🔢 Entered OTP: " + otp);
                
                

                //enter the password
                if (password == null || password.trim().isEmpty()) {
                    test.fail("❌ Password required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("Password missing for signup: " + testCaseId);
                }
                accountPage.getPasswordField().fill(password);
                test.info("🔑 Entered Password.");

                accountPage.getSubmitButton().click();
                test.info("🚀 Clicked Submit.");

                DashboardPage dashboardPage = new DashboardPage(page);

                // ✅ Pass test purely if 'mystuff' is visible
                PlaywrightAssertions.assertThat(dashboardPage.mystuff()).isVisible();
                test.pass("✅ 'My Stuff' icon is visible after signup for " + testCaseId + ". Marking test as PASS.");

                //capture the screenshot after completion of the test
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_pass.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup");

                
                
            } catch (Exception e) {
                test.fail("❌ TestCaseID: " + testCaseId + " failed: " + e.getMessage());
                throw e;
            } finally {
                closePlaywright();
                test.info("🧹 Closed browser for TestCaseID: " + testCaseId);
            }
        }

        //closes the excel workbook and prepare the test report. 
        extent.flush();
    }
}
