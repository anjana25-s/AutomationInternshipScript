package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.promilo.automation.pageobjects.signuplogin.*;
import com.promilo.automation.resources.*;

import java.nio.file.Paths;
import java.util.*;

public class MailLoginWithInvalidOtp extends BaseClass {

    @Test
    public void mailLoginWithInvalidOtp() throws Exception {
    	
    	
    	 Page page = initializePlaywright();
         page.navigate(prop.getProperty("url"));
         page.setViewportSize(1920, 1080);
         
         
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MailLoginWithInvalidOtp");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> loginKeywords = new HashSet<>(Collections.singletonList("MailInvalidOtpLogin"));

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String expectedResult = excel.getCellData(i, 4);
            String otp = excel.getCellData(i, 5);

            if (!loginKeywords.contains(keyword)) {
                continue;
            }


            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless", "false"))));
                BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1280, 800));
                Page page1 = context.newPage();

                page1.navigate(prop.getProperty("url"));
                page1.waitForLoadState(LoadState.NETWORKIDLE);

MayBeLaterPopUp landingpage= new MayBeLaterPopUp(page1);

             try {
            	 landingpage.getPopup().click();
                    test.info("✅ Closed popup if displayed.");
                } catch (Exception e) {
                    test.info("ℹ️ No popup to close.");
                }

             landingpage.getLoginButton().click();
                test.info("👉 Clicked Login button.");

                LoginPage loginPage = new LoginPage(page1);
                loginPage.loginMailPhone().fill(inputValue);
                test.info("Entered Email: " + inputValue);

                loginPage.loginWithOtp().click();
                test.info(" Clicked 'Login with OTP'.");

                loginPage.otpField().fill(otp);
                test.info("\uD83D\uDD22 Entered Invalid OTP: " + otp);

                loginPage.loginButton().click();
                test.info("🚀 Clicked Login button after OTP entry.");

                try {
                    Locator errorLocator = page1.locator("xpath=//div[@role='status'] | //p[contains(text(),'Invalid') or contains(text(),'invalid') or contains(text(),'OTP') or contains(text(),'otp')]");
                    errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                    if (expectedResult.trim().equalsIgnoreCase("Invalid OTP.")) {
                        test.pass("✅ Invalid OTP error message displayed as expected for " + testCaseId);
                    } else {
                        test.fail("❌ Unexpected invalid OTP error for " + testCaseId + ". Value in Excel: '" + expectedResult + "'");
                        Assert.fail("Unexpected invalid OTP error for " + testCaseId);
                    }
                } catch (Exception e) {
                    test.fail("❌ Expected invalid OTP error, but no error message appeared for " + testCaseId);
                    Assert.fail("Expected invalid OTP error, but no error message appeared for " + testCaseId);
                }
                
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_pass.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup");


                context.close();
                browser.close();
                test.info("Browser closed for TestCaseID: " + testCaseId);
            }
        }

        extent.flush();
    }
}
