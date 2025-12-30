package com.promilo.automation.resources;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

import java.nio.file.Paths;
import java.util.*;

public class PhoneRegisteredSignupUtility extends BaseClass {

    private static ExtentReports extent = ExtentManager.getInstance();

    /** Generate random Indian phone number like 90000xxxxx */
    private String generateRandomPhone() {
        return "90000" + String.format("%05d", new Random().nextInt(100000));
    }

    /**
     * Performs signup using phone.
     * Returns Map containing "phone" and "password" to be used in other tests.
     */
    public Map<String, String> signupWithPhone() throws Exception {

        Map<String, String> credentials = new HashMap<>();

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String tc = excel.getCellData(i, 0);
            if (tc == null || tc.trim().isEmpty()) break;
            rowCount++;
        }

        System.out.println("📘 Loaded " + rowCount + " rows from Excel.");

        Set<String> allowedKeywords = Collections.singleton("ValidSignup");

        for (int i = 1; i < rowCount; i++) {

            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);

            if (!allowedKeywords.contains(keyword)) continue;

            ExtentTest test = extent.createTest("📱 Phone Signup | TestCaseID: " + testCaseId);

            Page page = initializePlaywright();

            try {
                page.navigate(prop.getProperty("url"));
                page.setViewportSize(1000, 768);
                Thread.sleep(4000);

                MayBeLaterPopUp mayBeLater = new MayBeLaterPopUp(page);

                try {
                    mayBeLater.getPopup().click(new Locator.ClickOptions().setTimeout(4000));
                    test.info("Popup closed.");
                    Thread.sleep(1500);
                } catch (PlaywrightException ignored) {
                    test.info("No popup shown.");
                }

                mayBeLater.clickSignup();
                test.info("Clicked Signup.");

                CreateAccountpage account = new CreateAccountpage(page);

                /** 👉 Generate and enter random phone number */
                String randomPhone = generateRandomPhone();
                account.getPhoneMailTextField().fill(randomPhone);
                test.info("📱 Entered Phone: " + randomPhone);

                // Save phone to Excel if needed
                excel.setCellData(i, 3, randomPhone);

                account.getSendCodeButton().click();
                test.info("OTP requested.");

                // Fixed OTP for testing
                page.locator("//input[@placeholder='Enter OTP sent to mobile number']").fill("9999");

                // Use fixed password here (not from Excel)
                String password = "Test@123";
                account.getPasswordField().fill(password);
                test.info("Entered Password: " + password);

                account.getSubmitButton().click();
                page.waitForTimeout(5000);
                test.info("Clicked Submit.");

                
                HomePage home = new HomePage(page);
                home.mystuff().waitFor(new Locator.WaitForOptions()
                        .setTimeout(15000)
                        .setState(WaitForSelectorState.VISIBLE));

                PlaywrightAssertions.assertThat(home.mystuff()).isVisible();
                test.pass("🎉 Signup successful! My Stuff icon visible.");

                
                // Return phone and password for other tests
                credentials.put("phone", randomPhone);
                credentials.put("password", password);

                break; // exit after first signup for this keyword

            } catch (Exception e) {
                test.fail("❌ Failed: " + e.getMessage());
                throw e;
            } finally {
                closePlaywright();
                test.info("Browser closed.");
            }
        }

        extent.flush();
        return credentials;
    }
}
