package com.promilo.automation.resumemodule;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;

public class UploadProfilePicture extends BaseClass {

    @Test
    public void ResumeUpload() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🖼 Upload Profile Picture | TC_UploadProfilePicture");

        Page page = null;

        try {
            // Launch browser
            page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            test.info("🌐 Navigated to application URL");

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            mayBeLaterPopUp.getPopup().click();
            test.info("✅ Closed popup");
            mayBeLaterPopUp.clickLoginButton();
            test.info("➡️ Clicked Login button");

            // Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("testuserppp4@gmail.com");
            test.info("📧 Entered email: testuserppp4@gmail.com");
            loginPage.passwordField().fill("Karthik@88");
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
            test.info("📄 Opened My Resume page");

            Thread.sleep(3000);

            // Step 1: Click edit icon
            page.locator("//img[@alt='Edit']").click();
            test.info("✏️ Clicked Edit icon to update profile picture");
            Thread.sleep(3000);

            // Step 2: Click on profile image to open upload dialog
            Locator setInputFiles = page.locator("//img[@alt='prolet']");
            setInputFiles.first().click();
            test.info("🖼 Clicked on profile image to open upload dialog");
            Thread.sleep(5000);

            // Step 3: Upload file
            page.locator("#upload-img")
                .setInputFiles(Paths.get("C:\\Users\\Admin\\Downloads\\capture.png"));
            test.info("📤 Uploaded profile picture from local path");

            Thread.sleep(5000);

            // Step 4: Click Crop
            page.locator("//button[text()='Crop']").click();
            test.info("✂️ Cropped and saved uploaded picture");

            test.pass("✅ Profile picture uploaded successfully");

        } catch (Exception e) {
            test.fail("❌ Profile picture upload test failed: " + e.getMessage());
            throw e;
        } finally {
            closePlaywright();
            test.info("🧹 Browser closed");
            extent.flush();
        }
    }
}
