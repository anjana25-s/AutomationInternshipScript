package com.promilo.automation.advertiser.loginandregister;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AdvertiserValidLogin extends Baseclass {

    @Test
    public void performLoginTest() {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Advertiser Valid Login | Data-Driven");

        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            int rowCount = 0;
            for (int i = 1; i <= 1000; i++) {
                String testCaseId = excel.getCellData(i, 0);
                if (testCaseId == null || testCaseId.trim().isEmpty()) break;
                rowCount++;
            }

            test.info("📘 Loaded " + rowCount + " rows from Excel.");

            for (int i = 1; i < rowCount; i++) {
                String testCaseId = excel.getCellData(i, 0);
                String keyword = excel.getCellData(i, 1);
                String email = excel.getCellData(i, 7);
                String password = excel.getCellData(i, 6);

                if (!"AdvertiserValidLogin".equalsIgnoreCase(keyword)) {
                    continue;
                }

                Page page = initializePlaywright();
                page.navigate(prop.getProperty("stageUrl"));
                page.setViewportSize(1000, 768);

                Thread.sleep(3000);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                // UI validations
                Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
                Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk to expert text is not visible.");

                login.loginMailField().fill(email);
                login.loginPasswordField().fill(password);
                login.signInButton().click();

                Locator navbar = page.locator("//p[text()='Coming Soon']");
                Assert.assertTrue(navbar.isVisible(), "❌ Navbar with 'Coming Soon' not visible.");

                test.pass("✅ Login successful for: " + email);
                page.close();
            }

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
