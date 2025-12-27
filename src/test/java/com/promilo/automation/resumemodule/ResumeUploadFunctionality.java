package com.promilo.automation.resumemodule;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.*;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class ResumeUploadFunctionality extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @Test
    public void resumeUploadTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📄 Resume Upload | Data-Driven Test");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // Pick metadata from Excel row 8
        String testCaseId   = excel.getCellData(8, 0); // TC_ResumeUploadFunctionality
        String description  = excel.getCellData(8, 10); // "A detailed overview of..."

        Page page = initializePlaywright();

        try {
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Popup Handling
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup to close.");
            }

            // Login
            mayBeLaterPopUp.clickLoginButton();

            if (registeredEmail == null || registeredPassword == null) {
                test.fail("❌ Signup credentials not available. Aborting test.");
                return;
            }

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(registeredEmail);
            loginPage.passwordField().fill(registeredPassword);
            loginPage.loginButton().click();
            test.info("🔐 Logged in with " + registeredEmail);

            // Resume Navigation
            Hamburger resumePage = new Hamburger(page);
            resumePage.Mystuff().click();
            Assert.assertTrue(resumePage.Mystuff().isVisible(), "'MyStuff' should be visible.");
            resumePage.MyAccount().click();
            resumePage.MyResume().click();
            test.info("📂 Navigated to My Resume section.");

            // Upload Resume
            resumePage.UploadCV("C:\\Users\\Admin\\Downloads\\Dummy_CV_Aarav_Sharma.pdf");
            page.waitForTimeout(3000);
            test.info("📄 Resume uploaded successfully.");

            // Assertion: AI text check
            String aiText = resumePage.letAIDoTheWorkText().innerText().trim();
            Assert.assertEquals(aiText, "Let AI do the work", "Validating displayed AI helper text");

            // Click "AutoFillWithAI"
            resumePage.AutoFillWithAI().click();
            page.waitForTimeout(2000);

            // Optional: Check if unwanted popup shows
            Locator unwantedPopup = page.locator("//span[@class='pointer truncate']");
            if (unwantedPopup.count() > 0 && unwantedPopup.first().isVisible()) {
                test.warning("⚠️ Unwanted element '//span[@class='pointer truncate']' is displayed.");
            }

            
            
            
            test.pass("✅ Resume uploaded and AI initiated successfully for " + testCaseId);

            // Screenshot
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" 
                                    + testCaseId + "_resumeupload_pass.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
            test.addScreenCaptureFromPath(screenshotPath, "📸 Screenshot");

        } catch (Exception e) {
            test.fail("❌ " + testCaseId + " failed: " + e.getMessage());
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" 
                                    + testCaseId + "_resumeupload_fail.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
            test.addScreenCaptureFromPath(screenshotPath, "📸 Failure Screenshot");
            throw e;

        } finally {
            closePlaywright();
            test.info("🧹 Closed browser for " + testCaseId);
        }
    }
}
