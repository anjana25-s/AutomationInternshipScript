package com.promilo.automation.courses.myintrests;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.courses.intrestspages.ViewedIntrestPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MyIntrestViewedTest extends Baseclass {

    @Test
    public void MyIntrestViewedFunctionalityTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 My Interest Viewed | With Mailosaur Signup & Login");

        // ✅ Excel file
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // ✅ Count rows
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing TestCase: " + testCaseId);

            // ✅ Step 1: Signup with Mailosaur (fetch OTP via UI)
            SignupWithMailosaurUI signupUtil = new SignupWithMailosaurUI();
            String[] creds = signupUtil.performSignupAndReturnCredentials(); // {email, password}
            String email = creds[0];
            String password = creds[1];
            test.info("📧 Signed up with: " + email);

            // ✅ Step 2: Start fresh session and login with same creds
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            Thread.sleep(2000);

            LandingPage landingPage = new LandingPage(page);
            try {
                landingPage.getPopup().click();
            } catch (Exception ignored) {}

            landingPage.clickLoginButton();  // go to login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();
            test.info("🔑 Logged in with Mailosaur account");
            ViewedIntrestPage viewedPage = new ViewedIntrestPage(page);

            viewedPage.coursesMenu().click();
            viewedPage.seeMoreBtn().click();
            
            Thread.sleep(3000);

            viewedPage.lpuCard().click();
            
            Thread.sleep(3000);
            viewedPage.myInterestTab().click();
            viewedPage.myPreferenceTab().click();

            String cardText = viewedPage.preferenceCard().textContent();

            if (cardText == null || cardText.isEmpty()) {
                test.fail("❌ My Interest card content is empty!");
            } else {
                test.pass("✅ My Interest viewed successfully!");
            }

            page.close();
        }
    }
}
