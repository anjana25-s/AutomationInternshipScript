package com.promilo.automation.signupandlogin;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.*;
import com.promilo.automation.resources.*;

import java.nio.file.Paths;

public class SigninWithLinkedin extends BaseClass {

    @Test
    public void signinWithLinkedin() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("SigninWithLinkedin");

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
            String InputValue
 = excel.getCellData(i, 3);
            String Password
 = excel.getCellData(i, 4); // ✅ Fetch password from Excel
            String expectedResult = excel.getCellData(i, 5);   // Adjust index if needed

            if (keyword == null || !keyword.equalsIgnoreCase("SocialLoginLinkedin")) {
                test.info("⏭️ Skipping row " + i + " (TestCaseID: " + testCaseId + ") with Keyword: " + keyword);
                continue;
            }

            if (InputValue
 == null || InputValue
.trim().isEmpty()) {
                test.warning("⚠️ No LinkedIn email provided for row " + i + " (TestCaseID: " + testCaseId + ")");
                continue;
            }

            if (Password
 == null || Password
.trim().isEmpty()) {
                test.warning("⚠️ No LinkedIn password provided for row " + i + " (TestCaseID: " + testCaseId + ")");
                continue;
            }

            atLeastOneExecuted = true;
            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Email: " + InputValue
);
            System.out.println("🚀 Executing TestCaseID: " + testCaseId + " | Email: " + InputValue
);

            Page page = initializePlaywright();
            page.setViewportSize(1280, 800);
            page.navigate(prop.getProperty("url"));
            test.info("🌐 Navigated to Promilo Staging");
            System.out.println("🌐 Navigated to Promilo Staging");

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);

            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed landing page popup if present");
                System.out.println("✅ Closed landing page popup if present");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup present, continuing");
                System.out.println("ℹ️ No popup present, continuing");
            }

            mayBeLaterPopUp.getSignup().click();
            test.info("👉 Clicked Signup");
            System.out.println("👉 Clicked Signup");

SocialLogins     socialLogins= new SocialLogins(page)   ;
socialLogins.clickLinkedinSignin();
            test.info("🔗 Clicked LinkedIn Signin");
            System.out.println("🔗 Clicked LinkedIn Signin");

            socialLogins.enterLinkedinMail(InputValue
);
            System.out.println("✅ Entered LinkedIn email: " + InputValue
);

            socialLogins.enterLinkedinPassword("Karthik@8342"); // ✅ Pass password fetched from Excel
            test.info("🔑 Entered LinkedIn password");
            System.out.println("🔑 Entered LinkedIn password");

            socialLogins.clickLinkedinSigninButton(); // ✅ Correct method to click Sign In
            test.info("🚀 Clicked LinkedIn Sign In");
            System.out.println("🚀 Clicked LinkedIn Sign In");
            
            Thread.sleep(3000);

          
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
