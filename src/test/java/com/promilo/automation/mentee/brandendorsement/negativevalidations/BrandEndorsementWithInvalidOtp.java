package com.promilo.automation.mentee.brandendorsement.negativevalidations;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementWithInvalidOtp extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    String excelPath;
    ExcelUtil excel;

    // ⭐ Best practice: Initialize Excel here
    @BeforeClass
    public void setupExcel() throws IOException {
        excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "Mentorship Test Data.xlsx").toString();

        excel = new ExcelUtil(excelPath, "Mentorship");
    }

    @Test
    public void BrandEndorsementOtpValidation() throws Exception {

        ExtentTest test = extent.createTest("Mentorship Book A Meeting Validation");
        test.info("🌐 Starting test...");

        // Count valid rows
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows.");

        // Loop rows
        for (int i = 1; i <= rowCount; i++) {

            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);

            if (!"BookMeetingOtpValidation".equalsIgnoreCase(keyword)) {
                test.info("⏭️ Skipping TestCaseID: " + testCaseId);
                continue;
            }

            // Excel data
            String mentorName = excel.getCellData(i, 2);
            String otp = excel.getCellData(i, 3);

            test.info("➡️ Starting TestCaseID: " + testCaseId);

            Page page = null;

            try {
                page = initializePlaywright();
                page.navigate(prop.getProperty("url"));
                page.setViewportSize(1080, 720);

                // Popup handling
                MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
                try {
                    popup.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception e) {
                    test.info("ℹ️ No popup present.");
                }

                

                // Open mentorships
                HomePage home = new HomePage(page);
                page.waitForTimeout(2000);
                home.mentorships().click(new Locator.ClickOptions().setForce(true));

                // Search Mentor
                MeetupsListingPage search = new MeetupsListingPage(page);
                search.SearchTextField().click();
                search.SearchTextField().fill(mentorName);
                page.keyboard().press("Enter");
                page.waitForTimeout(2000);

                // Open details
                DescriptionPage desc = new DescriptionPage(page);
                desc.allLink().click();
                desc.bookOnlineMeeting().click();

                // Fill form
                MentorshipErrorMessagesAndToasters err = new MentorshipErrorMessagesAndToasters(page);

                err.nameTextField().fill("karthik");

                int r = (int) (Math.random() * 90000) + 10000;
                String randomPhone = "90000" + r;
                String randomEmail = "testuser-" + r + "@mailosaur.net";

                err.mobileTextField().nth(1).fill(randomPhone);
                err.emailTextField().nth(1).fill(randomEmail);
                err.bookVideoCallButton().click();
                

             // -------------------- OTP Handling --------------------
                String otp1 = "1234";
                if (otp1.length() < 4) {
                    throw new IllegalArgumentException("OTP must be 4 digits: " + otp1);
                }

                for (int j = 0; j < 4; j++) {
                    String otpChar = String.valueOf(otp1.charAt(j));
                    Locator otpField = page.locator(
                            "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

                    otpField.waitFor(new Locator.WaitForOptions()
                            .setTimeout(10000)
                            .setState(WaitForSelectorState.VISIBLE));

                    boolean filled = false;
                    for (int attempt = 1; attempt <= 3 && !filled; attempt++) {
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
                        throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
                    }
                }

                err.verifyAndProceedButton().click();

                // Validate toaster
                page.waitForTimeout(3000);
                String toaster = err.invalidOtp().textContent().trim();
                Assert.assertTrue(toaster.contains("Invalid OTP"),
                        "❌ Invalid OTP toaster not displayed");

                test.pass("✅ Passed Invalid OTP validation for " + testCaseId);

            } catch (Exception e) {
                test.fail("💥 Exception: " + e.getMessage());
                throw e;

            } finally {
                if (page != null) page.close();
                extent.flush();
                test.info("📘 Completed: " + testCaseId);
            }
        }
    }
}
