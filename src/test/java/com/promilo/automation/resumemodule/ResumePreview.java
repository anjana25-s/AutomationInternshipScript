package com.promilo.automation.resumemodule;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.*;

public class ResumePreview extends BaseClass {

    @Test
    public void resumePreviewTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📄 Resume Preview Test | Row 18 Data");

        Page page = null;
        String email = "";
        String password = "";
        String testCaseId = "";

        try {
            // Read Excel row 18 (index 17)
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            testCaseId = excel.getCellData(17, 0); // Assuming column 0 has TC ID
            email = excel.getCellData(17, 3);      // Column 3 = email
            password = excel.getCellData(17, 6);   // Column 6 = password
            test.info("📊 Test Data Loaded → TCID: " + testCaseId + ", Email: " + email);

            // Launch and navigate
            page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            test.info("🌐 Navigated to URL: " + prop.getProperty("url"));

            // Landing Page
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click();
                test.info("✅ Closed popup if present.");
            } catch (Exception e) {
                test.info("ℹ️ No popup appeared on landing page.");
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
            test.info("🔐 Logged in successfully as: " + email);

            // Navigate to Resume Preview
            Hamburger resumePage = new Hamburger(page);
            resumePage.Mystuff().click();
            test.info("📂 Clicked on 'MyStuff'.");

            resumePage.MyAccount().click();
            test.info("👤 Clicked on 'MyAccount'.");

            resumePage.MyResume().click();
            test.info("📄 Opened 'My Resume' section.");

            resumePage.ResumePreview().click();
            test.info("🖼️ Clicked on 'Resume Preview'.");

            // Assertions on resume preview
            Locator profileImage = page.locator("//img[@class='resume-profile-img']");
            Thread.sleep(4000);
            test.info("🖼️ Located profile image on Resume Preview.");

            Locator userName = page.locator("//p[@class='text-blue fw-bold ms-1 font-18']");
            Thread.sleep(4000);
            test.info("👤 Located user name on Resume Preview.");

            Locator previewText = page.locator("//h5[text()='Resume Preview']");
            test.info("📑 Located Resume Preview header text.");

            // You can add assertions if required
            Assert.assertTrue(previewText.isVisible(), "Resume Preview header should be visible");
            test.info("✅ Verified Resume Preview header is visible.");

            test.pass("🎉 Resume preview validated successfully for test case: " + testCaseId);

        } catch (Exception e) {
            test.fail("❌ Test failed for test case: " + testCaseId + " | " + e.getMessage());
            throw e;
        } finally {
            closePlaywright();
            test.info("🧹 Browser closed for test case: " + testCaseId);
            extent.flush();
        }
    }
}
