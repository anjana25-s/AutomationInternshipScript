package com.promilo.automation.advertiser.loginandregister;

import java.nio.file.Paths;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AdvertiserValidLogin extends BaseClass {

    @Test
    public void performLoginTest() {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Advertiser Valid Login | Hardcoded Random Data");

        try {
            // ✅ Excel initialized (not used directly here)
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", 
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
            test.info("📘 Excel Utility Initialized (Using hardcoded/random values instead)");

            // ✅ Hardcoded + random login test data
            String email = "agree-laugh@ofuk8kzb.mailosaur.net";
            String password = "Karthik@88"; 

            // ✅ Initialize browser
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            // ✅ UI validations
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk to expert text is not visible.");

            // ✅ Perform login
            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();
            
            Thread.sleep(3000);

            // ✅ Post-login validation
            Locator navbar = page.locator("//p[text()='Coming Soon']");
            Assert.assertTrue(navbar.isVisible(), "❌ Navbar with 'Coming Soon' not visible.");

            test.pass("✅ Login attempted with email: " + email + " and password: " + password);

            page.close();

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("❌ Exception during login test: " + e.getMessage());
        }
        extent.flush();
    }
}
