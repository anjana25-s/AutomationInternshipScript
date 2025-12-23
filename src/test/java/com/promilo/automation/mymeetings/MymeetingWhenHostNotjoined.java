package com.promilo.automation.mymeetings;


import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MymeetingWhenHostNotjoined extends BaseClass {

    @Test
    public void joinMeetingHostNotJoinedDataDriven() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Join Meeting When Host Not Joined | Data Driven");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        SoftAssert softAssert = new SoftAssert();

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
            String keyword = excel.getCellData(i, 1);
            if (!"JoinMeetingHostNotJoined".equalsIgnoreCase(keyword)) continue;

            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);
            String remark = excel.getCellData(i, 10); // Assuming remark is stored in column 10

            try {
                // Landing and login
                LandingPage landingPage = new LandingPage(page);
                landingPage.getPopup().click();
                landingPage.clickLoginButton();

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(email);
                loginPage.passwordField().fill(password);
                loginPage.loginButton().click();

                // Navigate and join meeting
                page.locator("//span[normalize-space()='My Meetings']").click();
                page.locator("//div[text()='All']").click();
                page.locator("//span[text()='Join Now']").first().click();
                page.locator("//button[text()='Join now']").first().click();

                page.onDialog((Dialog dialog) -> {
                    System.out.println("Dialog message: " + dialog.message());
                    dialog.accept();
                });

                Thread.sleep(4000);

                // Click first feedback panel
                page.locator("//div[@class='flex items-center justify-center  rounded-lg'][position()=1]").first().click();
                Thread.sleep(4000);

                // Verify popup
                Locator popup = page.locator("//div[text()='Would you like us to share your contact details?']");
                popup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                softAssert.assertTrue(popup.isVisible(), "❌ Popup should be visible.");

                // Fill feedback
                page.locator("//button[text()='Yes']").click();
                page.locator("//input[@name='remark']").fill(remark);

                Locator stars = page.locator("//span[contains(@class, 'star-icon')]");
                stars.first().waitFor(new Locator.WaitForOptions().setTimeout(3000));

                int rating = 3;
                if (rating > 0 && rating <= stars.count()) {
                    stars.nth(rating - 1).click();
                } else {
                    test.warning("⚠️ Invalid rating: " + rating);
                }

                page.locator("//button[text()='Report Abuse']").click();
                page.locator("//button[text()='Submit']").click();

                test.pass("✅ Feedback submitted successfully for: " + email);

            } catch (Exception e) {
                test.fail("❌ Exception for " + email + ": " + e.getMessage());
            }
        }

        softAssert.assertAll();
    }
}
