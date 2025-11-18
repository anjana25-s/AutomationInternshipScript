package com.promilo.automation.mentorship.mentee.personalizedvideo.negativevalidations;

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
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class PersonalizedVideoNegativeValidations extends Baseclass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("personalized  Video Message Error  Validation Functionality");

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    // ✅ Prevent duplicate execution
    private static boolean hasRun = false;

    @DataProvider(name = "PersonalizedVideoMessageErrorValidation")
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

    @Test(dataProvider = "PersonalizedVideoMessageErrorValidation")
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

        // ✅ Run only once and only for matching keyword
        if (!"PersonalizedVideoMessageErrorValidation".equalsIgnoreCase(keyword)) {
            return;
        }
        if (hasRun) {
            logger.info("Skipping duplicate execution for keyword: {}", keyword);
            return;
        }
        hasRun = true;

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Mentorship Book A Meeting Error Messages Validation | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        logger.info("Executing BookMeetingErrorValidation for TestCaseID: {}", testCaseId);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
            test.info("✅ Popup closed.");
        } catch (Exception ignored) {
            test.info("ℹ️ No popup found.");
        }

        HomePage dashboard = new HomePage(page);
        page.waitForTimeout(3000);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        logger.info("Clicked on Mentorship module");

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill(MentorName);
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);

        // Description page components
        DescriptionPage serviceClick = new DescriptionPage(page);
        serviceClick.allLink().click();
        serviceClick.personalizedVideoMessage().click();
        serviceClick.requestVideo().nth(1).click();

        // Click on Book a Video Call button
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(page);

        // ✅ Error validations without entering the data
        String actualNameError = ErrorMessageValidation.nameIsRequired().textContent();
        String actualMobileError = ErrorMessageValidation.mobileNumberIsRequired().textContent();
        String actualEmailError = ErrorMessageValidation.emailIsRequired().textContent();

        Assert.assertEquals(actualNameError, nameIsRequired, "❌ Name error message mismatch!");
        Assert.assertEquals(actualMobileError, mobileNumberIsRequired, "❌ Mobile number error message mismatch!");
        Assert.assertEquals(actualEmailError, emailIsRequired, "❌ Email error message mismatch!");

        page.waitForTimeout(2000);
        // ✅ Entering Invalid inputs
        ErrorMessageValidation.nameTextField().first().fill(NameMinimum);
        ErrorMessageValidation.mobileTextField().nth(1).fill(invalidMobileNumber);
        ErrorMessageValidation.emailTextField().nth(1).fill(invalidEmailAdress);

        String actualNameErrorValidation = ErrorMessageValidation.nameMinimumCharacter().textContent();
        String actualEmailErrorValidation = ErrorMessageValidation.invalidEmailAdress().textContent();
        String actualMobileErrorValidation = ErrorMessageValidation.invalidMobileNumber().textContent();

        // ✅ Assertions for invalid inputs
        Assert.assertEquals(actualNameErrorValidation, nameMinimumCharacter, "❌ Name minimum character validation failed!");
        Assert.assertEquals(actualEmailErrorValidation, invalidEmailAdress, "❌ Invalid email address validation failed!");
        Assert.assertEquals(actualMobileErrorValidation, invalidMobileNumber, "❌ Invalid mobile number validation failed!");

        test.pass("✅ Mentorship Book A Meeting Error Messages Validation passed for TestCase: " + testCaseId);
        extent.flush();
    }
}
