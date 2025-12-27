package com.promilo.automation.resumemodule;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.aventstack.extentreports.*;

import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.*;

public class UploadProfilePictureUsingCamera extends BaseClass {

    @Test
    public void resumeUploadWithCamera() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📸 Upload Profile Picture Using Camera | Excel Keyword Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        String testCaseId = excel.getCellData(4, 0);
        String keyword = excel.getCellData(4, 1);
        String email = excel.getCellData(4, 3);
        String password = excel.getCellData(4, 6);

        Page page = initializePlaywright();
        BrowserContext context = getContext();

        // 🔹 Grant camera permissions
        context.grantPermissions(
            List.of("camera"),
            new BrowserContext.GrantPermissionsOptions().setOrigin("https://promilo.com")
        );
        test.info("🎥 Camera permission granted for https://promilo.com");

        // Handle dialogs automatically
        page.onDialog(dialog -> {
            test.info("📢 Permission dialog detected: " + dialog.message());
            dialog.accept();
        });

        try {
            page.setViewportSize(1100, 780);
            page.navigate(prop.getProperty("url"));
            test.info("🌐 Navigated to application URL");

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

            // Open camera upload
            resumePage.EditProfileIcon().click();
            test.info("✏️ Clicked Edit Profile icon");
            Thread.sleep(3000);

            resumePage.cameraOption().click();
            test.info("📸 Selected Camera option");

            Thread.sleep(3000);
            
            context.grantPermissions(
                    Arrays.asList("camera", "microphone"),
                    new BrowserContext.GrantPermissionsOptions()
                        .setOrigin("https://stage.promilo.com")
            );


            page.getByText("Take Photo", new Page.GetByTextOptions().setExact(true)).click();
            test.info("📷 Captured photo using camera");

            Thread.sleep(2000);

            // Crop & Save
            page.locator("//button[text()='Crop']").click();
            test.info("✂️ Cropped and saved camera photo");

            test.pass("✅ Camera resume upload successful for TestCaseID: " + testCaseId);

            // Screenshot
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_camera_upload.png";
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
