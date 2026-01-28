package com.promilo.automation.mentorship.mentee.getmentorcall.negativevalidation;

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
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GetMentorCallWithInvalidOtp extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    private static final Logger logger =
            LogManager.getLogger(GetMentorCallWithInvalidOtp.class);

    // Utility method
    private String safe(Object obj) {
        return (obj == null) ? "" : obj.toString().trim();
    }

    // ---------------------------------------------
    // DATA PROVIDER (CORRECTED)
    // ---------------------------------------------
    @DataProvider(name = "BookAMeetingErrorValidation")
    public Object[][] jobApplicationData() throws Exception {

        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "Mentorship Test Data.xlsx").toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");

        int rowCount = excel.getRowCount();
        int colCount = excel.getColumnCount();

        List<Object[]> filteredRows = new ArrayList<>();

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, 1);

            if (keyword != null && keyword.trim()
                    .equalsIgnoreCase("BookMeetingOtpValidation")) {

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

    // ---------------------------------------------
    // TEST METHOD (CORRECTED)
    // ---------------------------------------------
    @Test(dataProvider = "BookAMeetingErrorValidation")
    public void PersonalizedVideoWithInvaldOtpTest(Object[] rowData) throws Exception {

        // Mapping excel columns
        String testCaseId   = safe(rowData[0]);
        String keyword      = safe(rowData[1]);
        String otp          = safe(rowData[5]);
        String mentorName   = safe(rowData[13]);
        String toasterMsg   = safe(rowData[15]);

        ExtentTest test = extent.createTest(
                "Mentorship GetMentorCall Invalid OTP | " + testCaseId);

        Page page = initializePlaywright();
        test.info("Browser initialized.");

        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        try {
            if (!keyword.equalsIgnoreCase("BookMeetingOtpValidation")) {
                test.info("Skipping row because keyword does not match.");
                return;
            }

            logger.info("Executing test for {}", testCaseId);

            // Step 1: Close popup
            try {
                MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
                popup.getPopup().click(new Locator.ClickOptions().setForce(true));
                test.info("Popup closed.");
            } catch (Exception ignored) {
                test.info("Popup not displayed.");
            }

            // Step 2: Navigate to Mentorships
            HomePage home = new HomePage(page);
            page.waitForTimeout(2000);
            home.mentorships().click(new Locator.ClickOptions().setForce(true));

            // Step 3: Search mentor
            MeetupsListingPage search = new MeetupsListingPage(page);
            search.SearchTextField().click();
            search.SearchTextField().fill(mentorName);
            page.waitForTimeout(6000);    
            page.keyboard().press("Enter");
            page.waitForTimeout(1500);

            // Step 4: Open Get Mentor Call
            DescriptionPage desc = new DescriptionPage(page);
            desc.allLink().click();
            desc.getMentorCall().first().click();

            // Step 5: Enter name, mobile, email
            MentorshipErrorMessagesAndToasters err = new MentorshipErrorMessagesAndToasters(page);

            err.nameTextField().fill("karthik");

            int rand = (int) (Math.random() * 90000) + 10000;
            String phone = "90000" + rand;
            String email = "testuser-" + rand + "@mailosaur.net";

            err.mobileTextField().nth(1).fill(phone);
            err.emailTextField().nth(1).fill(email);

          //click on Download Resource button
            MentorshipFormComponents clickgetMentorCall= new MentorshipFormComponents(page);
            clickgetMentorCall.getMentorCall().click();

            // Step 6: Enter invalid OTP
            if (otp.length() < 4) {
                throw new RuntimeException("OTP must be 4 digits.");
            }

            String otpValue = "1234";  // your input OTP

            for (int i = 0; i < 4; i++) {

                String digitToEnter = String.valueOf(otpValue.charAt(i));

                Locator otpField = page.locator(
                        "//input[@aria-label='Please enter OTP character " + (i + 1) + "']");

                otpField.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE));

                otpField.fill(digitToEnter);
            }


            err.verifyAndProceedButton().click();

            // Step 7: Validate toaster
            String actualToaster = err.invalidOtp().textContent().trim();

            Assert.assertTrue(
                    actualToaster.contains("Invalid OTP"),
                    "Invalid OTP toaster mismatch!"
            );

            test.pass("Invalid OTP validation successful.");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            throw e;

        } finally {
            page.close();
            extent.flush();
        }
    }
}
