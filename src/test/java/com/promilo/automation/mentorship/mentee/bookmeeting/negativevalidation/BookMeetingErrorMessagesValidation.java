package com.promilo.automation.mentorship.mentee.bookmeeting.negativevalidation;

import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentee.BookMeetingErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BookMeetingErrorMessagesValidation extends Baseclass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("Book A Video Call Error Messages Validation Functionality");

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    @DataProvider(name = "BookAMeetingErrorValidation")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");

        int rowCount = excel.getRowCount();
        Object[][] data = new Object[rowCount][15];

        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0);  // testCaseId
            data[i - 1][1] = excel.getCellData(i, 1);  // keyword
            data[i - 1][2] = excel.getCellData(i, 2);  // registeredEmail
            data[i - 1][3] = excel.getCellData(i, 3);  // password
            data[i - 1][4] = excel.getCellData(i, 4);  // name
            data[i - 1][5] = excel.getCellData(i, 5);  // otp
            data[i - 1][6] = excel.getCellData(i, 6);  // mail/phone
            data[i - 1][7] = excel.getCellData(i, 7);  // nameIsRequired
            data[i - 1][8] = excel.getCellData(i, 8);  // mobileNumberIsRequired
            data[i - 1][9] = excel.getCellData(i, 9);  // emailIsRequired
            data[i - 1][10] = excel.getCellData(i, 10); // nameMinimumCharacter
            data[i - 1][11] = excel.getCellData(i, 11); // invalidMobileNumber
            data[i - 1][12] = excel.getCellData(i, 12); // invalidEmailAdress
            data[i - 1][13] = excel.getCellData(i, 13); // MentorName
            data[i - 1][14] = excel.getCellData(i, 14); // NameMinimum
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
            String NameMinimum
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Mentorship Book A Meeting Error Messages Validation | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        // ✅ Run only when the correct keyword is found
        if ("BookMeetingErrorValidation".equalsIgnoreCase(keyword)) {
            logger.info("Executing BookMeetingErrorValidation for TestCaseID: {}", testCaseId);
            
            
            
            
            LandingPage landingPage = new LandingPage(page);
            
            try {
                landingPage.getPopup().click();
                test.info("✅ Popup closed.");
            } catch (Exception ignored) {
                test.info("ℹ️ No popup found.");
            }
            
            
            

            //click on Mentorship in Dashboard or Homepage
            DashboardPage dashboard = new DashboardPage(page);
            page.waitForTimeout(3000);
            dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
            logger.info("Clicked on Mentorship module");

            // Search for mentor
            MeetupsListingPage searchPage = new MeetupsListingPage(page);
            searchPage.SearchTextField().click();
            searchPage.SearchTextField().fill(MentorName);
            page.keyboard().press("Enter");
            page.waitForTimeout(2000);

            
            //Description page components
            DescriptionPage serviceClick = new DescriptionPage(page);
            serviceClick.allLink().click();
            serviceClick.bookOnlineMeeting().click();
            
            
            
            

            //click on book a video call button in the form
            BookMeetingErrorMessagesAndToasters ErrorMessageValidation = new BookMeetingErrorMessagesAndToasters(page);
            ErrorMessageValidation.bookVideoCallButton().click();

            // ✅ error validations without entering the data
            String actualNameError = ErrorMessageValidation.nameIsRequired().textContent();
            String actualMobileError = ErrorMessageValidation.mobileNumberIsRequired().textContent();
            String actualEmailError = ErrorMessageValidation.emailIsRequired().textContent();

            Assert.assertEquals(actualNameError, nameIsRequired, "❌ Name error message mismatch!");
            Assert.assertEquals(actualMobileError, mobileNumberIsRequired, "❌ Mobile number error message mismatch!");
            Assert.assertEquals(actualEmailError, emailIsRequired, "❌ Email error message mismatch!");

            // ✅ Entering Invalid inputs
            ErrorMessageValidation.nameTextField().nth(1).fill(NameMinimum);
            ErrorMessageValidation.mobileTextField().nth(1).fill(invalidMobileNumber);
            ErrorMessageValidation.emailTextField().nth(1).fill(invalidEmailAdress);

            String actualNameErrorValidation = ErrorMessageValidation.nameMinimumCharacter().textContent();
            String actualEmailErrorValidation = ErrorMessageValidation.invalidEmailAdress().textContent();
            String actualMobileErrorValidation = ErrorMessageValidation.invalidMobileNumber().textContent();

            //Assertion for the error string messages for invalid inputs
            Assert.assertEquals(actualNameErrorValidation, nameMinimumCharacter, "❌ Name minimum character validation failed!");
            Assert.assertEquals(actualEmailErrorValidation, invalidEmailAdress, "❌ Invalid email address validation failed!");
            Assert.assertEquals(actualMobileErrorValidation, invalidMobileNumber, "❌ Invalid mobile number validation failed!");

            test.pass("✅ Mentorship Book A Meeting Error Messages Validation passed for TestCase: " + testCaseId);

        } else {
            logger.info("Skipping TestCaseID: {} because keyword '{}' does not match expected 'BookMeetingErrorValidation'.", testCaseId, keyword);
            test.info("⏭️ Skipped TestCaseID: " + testCaseId + " due to unmatched keyword.");
        }

        extent.flush();
    }
}
