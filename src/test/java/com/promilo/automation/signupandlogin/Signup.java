package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

import java.nio.file.Paths;
import java.util.*;

public class Signup extends BaseClass {

    private static ExtentReports extent = ExtentManager.getInstance(); // Initialize once

    @Test
    public void signup() throws Exception {

        //path of the excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        System.out.println("✅ Loaded " + rowCount + " rows from Excel.");

        Set<String> signupKeywords = Collections.singleton("ValidSignup");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String otp = excel.getCellData(i, 5);
            String password = excel.getCellData(i, 6);

            if (!signupKeywords.contains(keyword)) {
                continue;
            }

            ExtentTest test = extent.createTest("🚀 Signup Test | TestCaseID: " + testCaseId);

            Page page = initializePlaywright();
            try {
                page.navigate(prop.getProperty("url"));
                page.setViewportSize(1000, 768);
                Thread.sleep(5000);

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);

                // Close popup if present
                try {
                    mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup if present.");
                    Thread.sleep(5000);
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup to close.");
                }

                mayBeLaterPopUp.clickSignup();
                test.info("📝 Clicked Signup button.");

                CreateAccountpage accountPage = new CreateAccountpage(page);

                // Generate a new random email
                String randomEmail = "testpromilo" + System.currentTimeMillis() + (int)(Math.random() * 1000) + "@mailinator.com";
                accountPage.getPhoneMailTextField().fill(randomEmail);
                test.info("✍️ Entered Email: " + randomEmail);

                accountPage.getSendCodeButton().click();
                test.info("📨 Clicked Send Code.");

                if (otp == null || otp.trim().isEmpty()) {
                    test.fail("❌ OTP required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("OTP missing for signup: " + testCaseId);
                }
                accountPage.getOtpField().fill(otp);
                test.info("🔢 Entered OTP: " + otp);

                if (password == null || password.trim().isEmpty()) {
                    test.fail("❌ Password required for " + testCaseId + " but missing in Excel.");
                    throw new RuntimeException("Password missing for signup: " + testCaseId);
                }
                accountPage.getPasswordField().fill(password);
                test.info("🔑 Entered Password.");

                accountPage.getSubmitButton().click();
                test.info("🚀 Clicked Submit.");

                HomePage homePage = new HomePage(page);

               PlaywrightAssertions.assertThat(homePage.mystuff()).isVisible();
                test.pass("✅ 'My Stuff' icon is visible after signup for " + testCaseId);
                
                
                
                
            } catch (Exception e) {
                test.fail("❌ TestCaseID: " + testCaseId + " failed: " + e.getMessage());
            } finally {
                closePlaywright();
                test.info("🧹 Closed browser for TestCaseID: " + testCaseId);
            }
        }

        extent.flush();
    }
}
