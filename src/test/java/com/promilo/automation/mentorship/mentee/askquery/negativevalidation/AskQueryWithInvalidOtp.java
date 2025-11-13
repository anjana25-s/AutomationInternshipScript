package com.promilo.automation.mentorship.mentee.askquery.negativevalidation;

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
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AskQueryWithInvalidOtp extends Baseclass {

    ExtentReports extent = ExtentManager.getInstance();
    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    @DataProvider(name = "BookAMeetingErrorValidation")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");

        int rowCount = excel.getRowCount();
        int requiredCols = 19; // MATCHING TEST METHOD PARAMETERS

        List<Object[]> filteredRows = new ArrayList<>();

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            if (keyword != null && keyword.trim().equalsIgnoreCase("BookMeetingOtpValidation")) {

                Object[] rowData = new Object[requiredCols];

                for (int j = 0; j < requiredCols; j++) {
                    rowData[j] = excel.getCellData(i, j);
                }
                filteredRows.add(rowData);
            }
        }

        Object[][] data = new Object[filteredRows.size()][requiredCols];
        for (int i = 0; i < filteredRows.size(); i++) {
            data[i] = filteredRows.get(i);
        }
        return data;
    }


    @Test(dataProvider = "BookAMeetingErrorValidation")
    public void PersonalizedVideoWithInvaldOtpTest(
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
            if ("BookMeetingOtpValidation".equalsIgnoreCase(keyword.trim())) {
                test.info("🔍 Test started for keyword: " + keyword);

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}

                HomePage dashboard = new HomePage(page);
                page.waitForTimeout(3000);
                dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

                MeetupsListingPage searchPage = new MeetupsListingPage(page);
                searchPage.SearchTextField().click();
                searchPage.SearchTextField().fill(MentorName);
                page.keyboard().press("Enter");
                page.waitForTimeout(2000);

                DescriptionPage serviceClick = new DescriptionPage(page);
                serviceClick.allLink().click();
                serviceClick.askYourQuery().first().click();

                MentorshipErrorMessagesAndToasters err = new MentorshipErrorMessagesAndToasters(page);
                err.nameTextField().fill("karthik");

                int randomNum = (int) (Math.random() * 90000) + 10000;
                String randomPhone = "90000" + randomNum;
                String randomEmail = "testuser-" + randomNum + "@mailosaur.net";

                mobile = randomPhone;
                Email = randomEmail;

                err.mobileTextField().nth(1).fill(mobile);
                err.emailTextField().nth(1).fill(Email);

                serviceClick.askYourQuery().nth(2).click();

                if (otp.length() < 4) throw new IllegalArgumentException("OTP must be 4 digits: " + otp);

                for (int j = 0; j < 4; j++) {
                    String otpChar = String.valueOf(otp.charAt(j));
                    Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
                    otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
                    otpField.fill(otpChar);
                }

                err.verifyAndProceedButton().click();

                String actualToaster = err.invalidOtp().textContent().trim();
                Assert.assertTrue(actualToaster.contains("Invalid OTP"), "❌ Invalid OTP toaster not displayed");
                test.pass("✅ Invalid OTP validation successful for " + testCaseId);

            }

        } finally {
            page.close();
            extent.flush();
        }
    }
}
