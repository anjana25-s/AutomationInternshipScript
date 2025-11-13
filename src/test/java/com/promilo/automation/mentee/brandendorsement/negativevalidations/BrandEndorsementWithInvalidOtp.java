package com.promilo.automation.mentee.brandendorsement.negativevalidations;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BrandEndorsementWithInvalidOtp extends Baseclass {

    ExtentReports extent = ExtentManager.getInstance();
    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    // ✅ Filter only BookMeetingOtpValidation rows dynamically
    @DataProvider(name = "BookAMeetingErrorValidation")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");

        int rowCount = excel.getRowCount();
        int colCount = excel.getColumnCount();

        List<Object[]> filteredRows = new ArrayList<>();

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            if (keyword != null && keyword.trim().equalsIgnoreCase("BookMeetingOtpValidation")) {
                Object[] rowData = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    rowData[j] = excel.getCellData(i, j);
                }
                filteredRows.add(rowData);
            }
        }

        Object[][] data = new Object[filteredRows.size()][colCount];
        for (int i = 0; i < filteredRows.size(); i++) {
            data[i] = filteredRows.get(i);
        }

        return data;
    }

    @Test(dataProvider = "BookAMeetingErrorValidation")
    public void applyForJobTestFromExcel(
            String testCaseId,
            String keyword,
            String registeredEmail,
            String password,
            String name,
            String otp,
            String mailphone,
            String nameIsRequired,
            String mobileNumberIsRequired,
            String emailIsRequired,
            String nameMinimumCharacter,
            String invalidMobileNumber,
            String invalidEmailAdress,
            String MentorName,
            String NameMinimum,
            String Toaster,
            String userName,
            String mobile,
            String Email
    ) throws Exception {

        ExtentTest test = extent.createTest("Mentorship Book A Meeting Validation | " + testCaseId);

        Page page = initializePlaywright();
        test.info("✅ Browser initialized successfully.");

        page.navigate(prop.getProperty("url"));
        test.info("🌐 Navigated to URL: " + prop.getProperty("url"));

        page.setViewportSize(1000, 768);
        logger.info("Executing BookMeetingOtpValidation for TestCaseID: {}", testCaseId);

        try {
            // Execute only for BookMeetingOtpValidation keyword
            if ("BookMeetingOtpValidation".equalsIgnoreCase(keyword.trim())) {
                test.info("🔍 Test started for keyword: " + keyword);

                // Step 1: Close popup if present
                MayBeLaterPopUp homePage = new MayBeLaterPopUp(page);
                try {
                	homePage.getPopup().click();
                    test.info("✅ Popup closed successfully.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                // Step 2: Navigate to Mentorships
                HomePage dashboard = new HomePage(page);
                page.waitForTimeout(3000);
                dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
                test.info("🧭 Clicked on Mentorships tab.");

                // Step 3: Search Mentor
                MeetupsListingPage searchPage = new MeetupsListingPage(page);
                searchPage.SearchTextField().click();
                searchPage.SearchTextField().fill(MentorName);
                page.keyboard().press("Enter");
                page.waitForTimeout(2000);
                test.info("👤 Searched for mentor: " + MentorName);

                // Step 4: Navigate to Description & Book Meeting
                DescriptionPage serviceClick = new DescriptionPage(page);
                serviceClick.allLink().click();
                serviceClick.bookOnlineMeeting().click();
                test.info("📅 Opened Book Meeting section.");

                // Step 5: Fill in valid data
                MentorshipErrorMessagesAndToasters err = new MentorshipErrorMessagesAndToasters(page);
                err.nameTextField().fill("karthik");

                int randomNum = (int) (Math.random() * 90000) + 10000;
                String randomPhone = "90000" + randomNum;
                String randomEmail = "testuser-" + randomNum + "@mailosaur.net";

                mobile = randomPhone;
                Email = randomEmail;

                err.mobileTextField().nth(1).fill(mobile);
                err.emailTextField().nth(1).fill(Email);
                err.bookVideoCallButton().click();
                test.info("📞 Filled details: Name=karthik, Phone=" + mobile + ", Email=" + Email);

                // Step 6: Handle OTP input
                if (otp.length() < 4) {
                    throw new IllegalArgumentException("OTP must be 4 digits: " + otp);
                }

                for (int j = 0; j < 4; j++) {
                    String otpChar = String.valueOf(otp.charAt(j));
                    Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
                    otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

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

                test.info("🔢 OTP entered successfully.");
                err.verifyAndProceedButton().click();

                // Step 7: Validate toaster for invalid OTP
                String actualToaster = err.invalidOtp().textContent().trim();
                Assert.assertTrue(actualToaster.contains("Invalid OTP"), "❌ Invalid OTP toaster not displayed");

                test.pass("✅ Invalid OTP validation successful for " + testCaseId);
                logger.info("✅ Invalid OTP validation passed for {}", testCaseId);
            } else {
                logger.info("Skipping TestCaseID: {} because keyword '{}' does not match.", testCaseId, keyword);
                test.info("⏭️ Skipped TestCaseID: " + testCaseId);
            }

        } catch (AssertionError ae) {
            test.fail("❌ Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            test.fail("💥 Exception occurred: " + e.getMessage());
            throw e;
        } finally {
            page.close();
            extent.flush();
            test.info("📘 Test execution completed for TestCaseID: " + testCaseId);
        }
    }
}
