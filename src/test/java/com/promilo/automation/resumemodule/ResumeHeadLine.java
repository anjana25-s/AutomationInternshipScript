package com.promilo.automation.resumemodule;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.*;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.myresume.ResumeHeadLinepage;
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

public class ResumeHeadLine extends BaseClass {

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
    public void addResumeHeadlineTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📝 Add Resume Headline | Data-Driven Test");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // 🔑 Pick headline data from Excel row 18
        String testCaseId = excel.getCellData(18, 0);
        String headline = excel.getCellData(18, 11); // Column L (Resume headline)

        Page page = initializePlaywright();

        try {
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            test.info("🌐 Navigated to application URL: " + prop.getProperty("url"));

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup successfully.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup appeared on landing page.");
            }

            mayBeLaterPopUp.clickLoginButton();
            test.info("🔑 Clicked on Login button.");

            // 🔑 Use Mailosaur signup credentials
            if (registeredEmail == null || registeredPassword == null) {
                test.fail("❌ Signup credentials not available. Aborting test.");
                return;
            }

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(registeredEmail);
            test.info("📧 Entered registered email: " + registeredEmail);

            loginPage.passwordField().fill(registeredPassword);
            test.info("🔑 Entered registered password.");

            loginPage.loginButton().click();
            test.info("✅ Logged in successfully with: " + registeredEmail);

            Hamburger resumePage = new Hamburger(page);
            resumePage.Mystuff().click();
            test.info("📂 Clicked on 'MyStuff'.");

            Assert.assertTrue(resumePage.Mystuff().isVisible(), "'MyStuff' should be visible.");
            test.info("✅ Verified 'MyStuff' is visible.");

            resumePage.MyAccount().click();
            test.info("👤 Clicked on 'MyAccount'.");

            resumePage.MyResume().click();
            test.info("📄 Opened 'My Resume' section.");

            resumePage.AddHeadline().click();
            test.info("🖊️ Clicked 'Add Headline' button.");

            ResumeHeadLinepage resumeheadline = new ResumeHeadLinepage(page);
            resumeheadline.getResumeHeadlineTextArea().fill(
                headline != null ? headline : "Default Headline"
            );
            test.info("📝 Filled resume headline: " + (headline != null ? headline : "Default Headline"));

            resumeheadline.getResumeHeadlineSaveButton().click();
            test.info("💾 Clicked 'Save' button for resume headline.");

            Locator toaster = page.locator("//div[@role='status']");
            toaster.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            Assert.assertTrue(toaster.isVisible(), "✅ Toaster is displayed — Test Passed");
            test.info("🎉 Success toaster displayed after saving headline.");

            test.pass("✅ Resume headline added successfully for " + testCaseId);

            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" 
                                    + testCaseId + "_resumeheadline_pass.png";
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotPath))
                    .setFullPage(true));
            test.addScreenCaptureFromPath(screenshotPath, "📸 Screenshot");

        } catch (Exception e) {
            test.fail("❌ " + testCaseId + " failed: " + e.getMessage());
            throw e;
        } finally {
            closePlaywright();
            test.info("🧹 Closed browser for " + testCaseId);
        }
    }
}
