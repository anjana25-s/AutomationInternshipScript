package com.promilo.automation.myassignments;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MyassignmentDraft extends BaseClass {

    @Test
    public void myAssignmentDraftDataDrivenTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📚 My Assignment Draft Test | Data Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);     // MailPhone
            String password = excel.getCellData(i, 6);  // Password

            if (!"MyAssignmentDraft".equalsIgnoreCase(keyword)) {
                continue;
            }

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Landing page interaction
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // Navigate to MyResume > My Account > My Assignment
            MyResumePage resumePage = new MyResumePage(page);
            resumePage.Mystuff().click();
            Assert.assertTrue(resumePage.Mystuff().isVisible(), "🔍 'MyStuff' menu should be visible after login");
            resumePage.MyAccount().click();

            page.locator("//a[text()='My Assignment']").click();
            page.locator("//span[text()='Edit Assignment']").click();

            Locator interestCard = page.locator("div[class='preferance-card interest-card myIntrest-card-responsive']");
            String cardText = interestCard.innerText();
            System.out.println("📝 Card Text: " + cardText);

            // This section below appears broken/hardcoded – kept as placeholder (you can remove if unnecessary)
            /*
            page.locator("✅ NASA’s “Virtual Control Room (VCR) Proof of Concept” White Paper..."); // skipped
            */

            page.locator("(//span[text()='Edit Assignment'])[2]").click();
            page.locator("//span[text()='Save']").click();

            // Toaster validation
            Locator toast = page.locator("//div[@role='status']");
            toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            if (!toast.isVisible()) {
                throw new AssertionError("❌ Toast was not displayed.");
            }

            System.out.println("✅ Toast displayed successfully.");

            Thread.sleep(5000);

            // Verify 'Drafted' tab
            page.locator("//div[text()='Drafted']").click();
            Locator interestCard1 = page.locator("div[class='preferance-card interest-card myIntrest-card-responsive']");
            interestCard1.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            Assert.assertTrue(interestCard1.isVisible(), "✅ Interest card should be visible");

            // Cleanup browser
            page.close();
        }
    }
}
