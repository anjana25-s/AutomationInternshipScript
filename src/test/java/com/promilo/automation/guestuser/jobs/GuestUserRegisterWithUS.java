package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserRegisterWithUS extends BaseClass {
    private static final Logger logger = LogManager.getLogger(GuestUserRegisterWithUS.class);

    // Reference only: read Excel data
    private void readExcelForReference() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        String testCaseId = excel.getCellData(1, 0);
        String keyword = excel.getCellData(1, 1);
        String email = excel.getCellData(1, 3);
        String otp = excel.getCellData(1, 5);
        String phone = excel.getCellData(1, 8);
        System.out.println("Excel reference -> " + testCaseId + ", " + keyword + ", " + email + ", " + otp + ", " + phone);
    }

    @Test
    public void registerWithUsFlow() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Guest User | Register With Us");

        // Hardcoded test data
        String testCaseId = "RegisterWithUS_001";
        String name = "Karthik";
        String password = "Karthik@88";
        String otp = "9999"; // Sample valid OTP for demo

        // Launch browser
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Landing page
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();
        page.waitForTimeout(15000);

        // Register With Us flow
        JobListingPage registerWithUs = new JobListingPage(page);
        registerWithUs.homepageJobs().click();

        registerWithUs.RegisteredWithUsname().fill(name);
     // Generate random phone (90000 + 5 random digits)
        String randomPhone = "90000" + (10000 + new java.util.Random().nextInt(90000));

        // Generate random email (testuser + random 6 digits)
        String randomEmail = "testuser" + System.currentTimeMillis() % 1000000 + "@gmail.com";

        // Fill into fields
        page.locator("//input[@placeholder='Mobile*']").fill(randomPhone);
        page.locator("input[placeholder='Email*']").fill(randomEmail);

        // For debugging/logging
        System.out.println("Generated Phone: " + randomPhone);
        System.out.println("Generated Email: " + randomEmail);

        registerWithUs.Rgeisteredwithuslocation().click();

        page.locator("//label[text()='Bengaluru/Bangalore']").click();
        registerWithUs.Rgeisteredwithuslocation().click();

        registerWithUs.RegisteredwithusPassword().fill(password);

        // Select industries
        List<String> industries = Arrays.asList("Animation & VFX");
        page.locator("//div[@id='industry-dropdown']").click();
        page.waitForTimeout(500);

        Locator optionElements = page
                .locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");

        for (String industry : industries) {
            boolean found = false;
            int count = optionElements.count();
            for (int i = 0; i < count; i++) {
                String text = optionElements.nth(i).innerText().trim();
                if (text.equalsIgnoreCase(industry)) {
                    optionElements.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found) {
                test.warning("⚠️ Industry not found: " + industry);
            }
            if (!industry.equals(industries.get(industries.size() - 1))) {
                page.locator("//div[@id='industry-dropdown']").click();
                page.waitForTimeout(300);
            }
        }

        
        registerWithUs.RegisteredwithusPassword().click();

        registerWithUs.Registeredwithusbutton().click();
        
        
        
     // OTP validation
        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be at least 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = page
                    .locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(
                    new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            int attempts = 0;
            while (!filled && attempts < 3) {
                attempts++;
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
            }
            test.info("Entered OTP digit: " + otpChar);
        }

        // Verify & proceed
        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();
        
        
        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));

        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You' popup not displayed.");
       
        
        test.pass("✅ 'Thank You' popup displayed");

    
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_register.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Registration Screenshot");

        // Cleanup
        page.close();
        extent.flush();
    }
}
