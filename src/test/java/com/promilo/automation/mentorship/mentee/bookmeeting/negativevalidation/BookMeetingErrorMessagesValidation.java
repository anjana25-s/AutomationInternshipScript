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
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class BookMeetingErrorMessagesValidation extends BaseClass {

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);
    private static boolean hasRun = false; // Prevent duplicate execution

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
    public void BookMeetingErrorMessagesValidationTest(
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

        // ✅ Run only once and only for matching keyword
        if (!"BookMeetingErrorValidation".equalsIgnoreCase(keyword) || hasRun) {
            logger.info("Skipping TestCaseID: {} due to unmatched keyword or duplicate execution.", testCaseId);
            return;
        }
        hasRun = true;

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Mentorship Book A Meeting Error Messages Validation | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        logger.info("Executing BookMeetingErrorValidation for TestCaseID: {}", testCaseId);

        // Handle landing page popup
        LandingPage landingPage = new LandingPage(page);
        try {
            landingPage.getPopup().click();
            test.info("✅ Popup closed.");
        } catch (Exception ignored) {
            test.info("ℹ️ No popup found.");
        }

        // Click Mentorship module
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

        // Description page actions
        DescriptionPage serviceClick = new DescriptionPage(page);
        serviceClick.allLink().click();
        serviceClick.bookOnlineMeeting().click();

        // Book Video Call
        BookMeetingErrorMessagesAndToasters errorValidation = new BookMeetingErrorMessagesAndToasters(page);
        errorValidation.bookVideoCallButton().click();

        // ✅ Validate required field errors
        Assert.assertEquals(errorValidation.nameIsRequired().textContent(), nameIsRequired, "❌ Name error message mismatch!");
        Assert.assertEquals(errorValidation.mobileNumberIsRequired().textContent(), mobileNumberIsRequired, "❌ Mobile number error message mismatch!");
        Assert.assertEquals(errorValidation.emailIsRequired().textContent(), emailIsRequired, "❌ Email error message mismatch!");

        // ✅ Enter invalid inputs
        errorValidation.nameTextField().nth(1).fill(NameMinimum);
        errorValidation.mobileTextField().nth(1).fill(invalidMobileNumber);
        errorValidation.emailTextField().nth(1).fill(invalidEmailAdress);

        // Validate errors for invalid inputs
        Assert.assertEquals(errorValidation.nameMinimumCharacter().textContent(), nameMinimumCharacter, "❌ Name minimum character validation failed!");
        Assert.assertEquals(errorValidation.invalidEmailAdress().textContent(), invalidEmailAdress, "❌ Invalid email address validation failed!");
        Assert.assertEquals(errorValidation.invalidMobileNumber().textContent(), invalidMobileNumber, "❌ Invalid mobile number validation failed!");

        test.pass("✅ Mentorship Book A Meeting Error Messages Validation passed for TestCase: " + testCaseId);

        // Cleanup
        page.close();
        extent.flush();
    }
}
