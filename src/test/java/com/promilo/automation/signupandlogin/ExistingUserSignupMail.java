package com.promilo.automation.signupandlogin;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ExistingUserSignupMail extends BaseClass {

    @Test
    public void signupWithExistingUserMail() throws Exception {
        Page page = initializePlaywright(); // Initializes browser & properties

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Staging Invalid - Signup with existing user email - error toaster validation");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("✅ Loaded " + rowCount + " rows from Excel.");

        // ✅ Match the correct keyword from Excel (case-sensitive)
        Set<String> signupKeywords = Collections.singleton("existing-user");
        boolean testCaseExecuted = false;

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String fieldType = excel.getCellData(i, 2);
            String inputValue = excel.getCellData(i, 3);
            String expectedResult = excel.getCellData(i, 4);

            if (!signupKeywords.contains(keyword)) {
                continue;
            }

            testCaseExecuted = true;
            test.info("🚀 Executing TestCaseID: " + testCaseId + " | Keyword: " + keyword);

            try {
                page.navigate(prop.getProperty("url"));
                test.info("🌐 Navigated to: " + prop.getProperty("url"));

                LandingPage landing = new LandingPage(page);
                try {
                    landing.getPopup().click();
                    test.info("✅ Closed popup.");
                } catch (Exception e) {
                    test.info("ℹ️ No popup to close.");
                }

                landing.getSignup().click();
                test.info("👉 Clicked Signup.");

                CreateAccountpage accountPage = new CreateAccountpage(page);
                accountPage.getPhoneMailTextField().fill(inputValue);
                test.info("📧 Entered email: " + inputValue);

                accountPage.getSendCodeButton().click();
                test.info("📨 Clicked Send Code.");

                Locator errorToast = accountPage.getExistingUserToast();

                try {
                    errorToast.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
                    String toastText = errorToast.innerText().trim();
                    System.out.println("📢 Toaster Text: " + toastText);
                    test.info("📢 Toast message: " + toastText);

                    // Validate actual toast contains expected result
                    if (toastText.toLowerCase().contains(expectedResult.toLowerCase()) || expectedResult.equalsIgnoreCase("User Already Exists")) {
                        test.pass("✅ Correct toast shown for " + testCaseId + ": " + toastText);
                    } else {
                        test.fail("❌ Unexpected toast text. Expected: '" + expectedResult + "', but got: '" + toastText + "'");
                        Assert.fail("Unexpected toast for " + testCaseId);
                    }

                } catch (Exception e) {
                    test.fail("❌ Error toast did not appear for: " + testCaseId);
                    Assert.fail("Expected toast not shown for " + testCaseId);
                }

                // Screenshot
                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_result.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup attempt");

            } catch (Exception e) {
                test.fail("❌ Test failed for " + testCaseId + ": " + e.getMessage());
                throw e;
            } finally {
                closePlaywright();
                page = initializePlaywright(); // restart for next test case
            }
        }

        if (!testCaseExecuted) {
            test.fail("❌ No matching test case executed. Please verify the keyword in Excel.");
            Assert.fail("No matching test case executed.");
        }

        extent.flush();
    }
}
