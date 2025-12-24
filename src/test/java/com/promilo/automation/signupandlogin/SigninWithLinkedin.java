package com.promilo.automation.signupandlogin;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.SocialLogins;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class SigninWithLinkedin extends BaseClass {

    @Test
    public void signinWithLinkedin() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging LinkedIn Signin - Data Driven (Playwright)");

        test.info("🚀 Test started: LinkedIn Signin - Data Driven using Excel");
        System.out.println("🚀 Test started: LinkedIn Signin - Data Driven using Excel");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel for data-driven execution.");
        System.out.println("✅ Loaded " + rowCount + " rows from Excel for data-driven execution.");

        boolean atLeastOneExecuted = false;

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String linkedinEmail = excel.getCellData(i, 3);
            String linkedinPassword = excel.getCellData(i, 4); // ✅ Fetch password from Excel
            String expectedResult = excel.getCellData(i, 5);   // Adjust index if needed

            if (keyword == null || !keyword.equalsIgnoreCase("LinkedInLogin")) {
                test.info("⏭️ Skipping row " + i + " (TestCaseID: " + testCaseId + ") with Keyword: " + keyword);
                continue;
            }

            if (linkedinEmail == null || linkedinEmail.trim().isEmpty()) {
                test.warning("⚠️ No LinkedIn email provided for row " + i + " (TestCaseID: " + testCaseId + ")");
                continue;
            }

            if (linkedinPassword == null || linkedinPassword.trim().isEmpty()) {
                test.warning("⚠️ No LinkedIn password provided for row " + i + " (TestCaseID: " + testCaseId + ")");
                continue;
            }

            atLeastOneExecuted = true;
            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Email: " + linkedinEmail);
            System.out.println("🚀 Executing TestCaseID: " + testCaseId + " | Email: " + linkedinEmail);

            Page page = initializePlaywright();
            page.setViewportSize(1280, 800);
            page.navigate(prop.getProperty("url"));
            test.info("🌐 Navigated to Promilo Staging");
            System.out.println("🌐 Navigated to Promilo Staging");

            LandingPage landingPage = new LandingPage(page);

            try {
                landingPage.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed landing page popup if present");
                System.out.println("✅ Closed landing page popup if present");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup present, continuing");
                System.out.println("ℹ️ No popup present, continuing");
            }

            landingPage.getSignup().click();
            test.info("👉 Clicked Signup");
            System.out.println("👉 Clicked Signup");

SocialLogins     socialLogins= new SocialLogins(page)   ;
socialLogins.clickLinkedinSignin();
            test.info("🔗 Clicked LinkedIn Signin");
            System.out.println("🔗 Clicked LinkedIn Signin");

            socialLogins.enterLinkedinMail(linkedinEmail);
            test.info("✅ Entered LinkedIn email: " + linkedinEmail);
            System.out.println("✅ Entered LinkedIn email: " + linkedinEmail);

            socialLogins.enterLinkedinPassword(linkedinPassword); // ✅ Pass password fetched from Excel
            test.info("🔑 Entered LinkedIn password");
            System.out.println("🔑 Entered LinkedIn password");

            socialLogins.clickLinkedinSigninButton(); // ✅ Correct method to click Sign In
            test.info("🚀 Clicked LinkedIn Sign In");
            System.out.println("🚀 Clicked LinkedIn Sign In");

            DashboardPage dashboardPage = new DashboardPage(page);
            PlaywrightAssertions.assertThat(dashboardPage.mystuff()).isVisible();
            test.pass("✅ 'My Stuff' icon is visible after signup for " + testCaseId + ". Marking test as PASS.");

            page.close();
            test.info("🧹 Browser closed for TestCaseID: " + testCaseId);
            System.out.println("🧹 Browser closed for TestCaseID: " + testCaseId);
        }

        test.info("🗂️ Excel workbook closed.");
        System.out.println("🗂️ Excel workbook closed.");

        if (!atLeastOneExecuted) {
            test.warning("⚠️ No test data found for 'LinkedInLogin' in the Excel sheet. Test skipped.");
            System.out.println("⚠️ No test data found for 'LinkedInLogin' in the Excel sheet. Test skipped.");
        }

        test.info("✅ Test completed: LinkedIn Signin - Data Driven (Playwright)");
        System.out.println("✅ Test completed: LinkedIn Signin - Data Driven (Playwright)");
        extent.flush();
    }
}
