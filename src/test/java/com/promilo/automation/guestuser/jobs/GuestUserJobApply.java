package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

public class GuestUserJobApply extends BaseClass {

    // Excel reference (not used in execution)
    private void readExcelForReference() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        String testCaseId = excel.getCellData(1, 0);
        String keyword = excel.getCellData(1, 1);
        String email = excel.getCellData(1, 3);
        String password = excel.getCellData(1, 6);
        String name = excel.getCellData(1, 4);
        String otp = excel.getCellData(1, 5);
        System.out.println("Excel reference -> TestCaseID: " + testCaseId + ", Keyword: " + keyword);
    }

    @Test
    public void applyForJobAsGuestUser() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Guest User | Single Run");

        // Example hardcoded input data for run
        String name = "Guest User";
        String mobile = "9000087669";
        String otp = "9999";

        // Launch browser and navigate
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Close popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        // Navigate to job listing and apply
        JobListingPage homePage = new JobListingPage(page);
        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();
       
        
        Locator developerJob = page.locator("//h3[text()='Developer']").first();
        developerJob.click();
        test.info("Clicked on Developer job listing");
        
        Thread.sleep(4000);
        
        page.waitForTimeout(15000);

        page.locator("//button[text()='Apply Now']").first().click();

        homePage.applyNameField().nth(1).fill(name);
     // Generate random phone (90000 + 5 random digits)
        String randomPhone = "90000" + (10000 + new java.util.Random().nextInt(90000));

        // Generate random email (testuser + random 6 digits)
        String randomEmail = "testuser" + System.currentTimeMillis() % 1000000 + "@gmail.com";

        // Fill into fields
        page.locator("//input[@placeholder='Mobile*']").nth(1).fill(randomPhone);
        page.locator("input[placeholder='Email*']").nth(1).fill(randomEmail);

        // For debugging/logging
        System.out.println("Generated Phone: " + randomPhone);
        System.out.println("Generated Email: " + randomEmail);
        

        // Select industries
        homePage.selectIndustryDropdown().nth(1).click();
        Thread.sleep(1000);
        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");
        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            boolean found = false;
            for (int i = 0; i < options.count(); i++) {
                String optionText = options.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found) {
                test.warning("⚠️ Industry not found: " + industry);
            }
        }

        // Click apply
        homePage.applyNameField().nth(1).click();
        Thread.sleep(1000);
        Locator applyButton = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyButton.scrollIntoViewIfNeeded();
        applyButton.click();
        Thread.sleep(2000);

        // Enter OTP
        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
        }
        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

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
        page.locator("//button[text()='Verify & Proceed']").click();

        // Select date and time, submit
page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first().click();
page.locator("//li[@class='time-slot-box list-group-item']").first().click();

        page.locator("//button[text()='Submit' and @class='fw-bold w-100 font-16 fw-bold calendar-modal-custom-btn mt-2 btn btn-primary']").click();

        // Validate Thank You popup
        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String popupText = thankYouPopup.innerText().trim();

        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"), "Expected 'Thank You!' popup, but found: " + popupText);
        test.pass("✅ 'Thank You!' popup validated successfully");

        extent.flush();
        page.close();
    }
}
