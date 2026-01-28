package com.promilo.automation.resumemodule;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.pageobjects.myresume.AccomplishmentsPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.ToasterUtil;

import org.testng.annotations.Test;

import java.nio.file.Paths;

public class SocialProfile extends BaseClass {

    @Test
    public void addSocialProfileTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📎 Add Social Profile | TC_AddSocialProfile");

        Page page = null;
        String testCaseId = null;

        try {
            // Load Excel test data
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", 
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            // Row 4 (index starts from 0, this is the 5th row in Excel)
            testCaseId = excel.getCellData(4, 0);  
            String keyword = excel.getCellData(4, 1);     
            String email = excel.getCellData(4, 3);      
            String password = excel.getCellData(4, 6);   
            String description = excel.getCellData(4, 10);
            String profileType = excel.getCellData(4, 11);
            String profileUrl = excel.getCellData(4, 12);

            test.info("📊 Loaded test data → TCID: " + testCaseId + 
                      ", Email: " + email + ", ProfileType: " + profileType);

            // Playwright Setup
            page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            test.info("🌐 Navigated to URL: " + prop.getProperty("url"));

            // Landing Page
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup if present.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup to close.");
            }

            mayBeLaterPopUp.clickLoginButton();
            test.info("🔑 Clicked on Login button.");

            // Login Page
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            test.info("📧 Entered email: " + email);

            loginPage.passwordField().fill(password);
            test.info("🔒 Entered password.");

            loginPage.loginButton().click();
            test.info("🔐 Logged in with " + email);

            // Navigate to My Resume → Social Profile
            Hamburger resumePage = new Hamburger(page);
            resumePage.Mystuff().click();
            test.info("📂 Clicked on 'MyStuff'.");

            resumePage.MyAccount().click();
            test.info("👤 Clicked on 'MyAccount'.");

            resumePage.MyResume().click();
            test.info("📄 Opened 'My Resume' section.");

            resumePage.AddSocialProfile().click();
            test.info("➕ Clicked 'Add Social Profile'.");

            // Fill Social Profile form
            AccomplishmentsPage socialPage = new AccomplishmentsPage(page);
            socialPage.addSocialProfileField().fill("linkedIn");
            test.info("📎 Entered Profile Type: " + profileType);

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Paste profile link"))
                .fill("https://www.linkedin.com/");
            test.info("🔗 Entered Profile URL: " + profileUrl);

            socialPage.socialDescriptionBox().fill("something");
            test.info("📝 Entered Description: " + description);

            socialPage.saveButton().click();
            test.info("💾 Clicked Save button.");

            // Toast verification
            ToasterUtil toaster = new ToasterUtil(page);
            Locator toast = toaster.getSuccessToaster();
            toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            test.info("⏳ Waiting for success toaster...");

            if (!toast.isVisible()) {
                throw new AssertionError("❌ Toast was not displayed.");
            }

            test.pass("✅ Social profile added successfully for " + testCaseId);

            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" 
                                  + testCaseId + "_socialprofile_pass.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
            test.addScreenCaptureFromPath(screenshotPath, "📸 Screenshot");
            test.info("📸 Screenshot captured at: " + screenshotPath);

        } catch (Exception e) {
            test.fail("❌ " + (testCaseId != null ? testCaseId : "Test") + " failed: " + e.getMessage());
            throw e;
        } finally {
            closePlaywright();
            test.info("🧹 Closed browser for " + (testCaseId != null ? testCaseId : "Test"));
            extent.flush();
        }
    }
}
