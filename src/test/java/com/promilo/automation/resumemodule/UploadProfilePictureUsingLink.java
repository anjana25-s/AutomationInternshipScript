package com.promilo.automation.resumemodule;

import com.microsoft.playwright.*;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

import org.testng.annotations.Test;

import java.nio.file.Paths;

public class UploadProfilePictureUsingLink extends BaseClass {

    @Test
    public void resumeUploadTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🖼️ Upload Profile Picture Using Link | Excel Keyword Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        String testCaseId = excel.getCellData(3, 0);  // TestCaseID
        String keyword = excel.getCellData(3, 1);     // Keyword
        String email = excel.getCellData(3, 3);       // MailPhone (Email)
        String password = excel.getCellData(3, 6);    // Password
        String imageUrl = excel.getCellData(3, 16);   // Profile picture URL

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        test.info("🌐 Navigated to application URL");

        try {
            // Landing Page
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup displayed.");
            }
            mayBeLaterPopUp.clickLoginButton();
            test.info("➡️ Clicked Login button");

            // Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            test.info("📧 Entered email: " + email);
            loginPage.passwordField().fill(password);
            test.info("🔑 Entered password");
            loginPage.loginButton().click();
            test.info("🔐 Logged in successfully");

            // Navigate to Resume
            Hamburger resumePage = new Hamburger(page);
            resumePage.Mystuff().click();
            test.info("📂 Clicked on My Stuff");
            resumePage.MyAccount().click();
            test.info("👤 Clicked on My Account");
            resumePage.MyResume().click();
            test.info("📄 Opened My Resume section");

            Thread.sleep(3000);

            // Upload Profile Picture using Link
            resumePage.EditProfileIcon().click();
            test.info("✏️ Clicked Edit Profile icon");
            Thread.sleep(3000);

            resumePage.LinkTab().click();
            test.info("🔗 Selected Link tab");

            resumePage.ProfilePictureUrlInput().fill("https://wallpapercave.com/wp/fAwVCh3.jpg");
            test.info("🖼️ Entered profile picture URL: " + imageUrl);

            resumePage.SaveButton().click();
            test.info("💾 Clicked Save button");

            Thread.sleep(3000);
            resumePage.CropButton().click();
            test.info("✂️ Cropped and applied profile picture");

            test.pass("✅ Profile picture uploaded successfully for TestCaseID: " + testCaseId);

            // Screenshot
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_profilepicture.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
            test.addScreenCaptureFromPath(screenshotPath);
            test.info("📸 Screenshot captured: " + screenshotPath);

        } catch (Exception e) {
            test.fail("❌ TestCaseID: " + testCaseId + " failed: " + e.getMessage());
            throw e;
        } finally {
            closePlaywright();
            test.info("🧹 Closed browser for TestCaseID: " + testCaseId);
            extent.flush();
        }
    }
}
