package com.promilo.automation.mentorship.mentee.downloadresource.negativevalidation;

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
import com.promilo.automation.mentorship.mentee.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPop;
import com.promilo.automation.registereduser.jobs.RegisteredUserShortList;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class DownloadResourceNegativeValidations extends Baseclass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("Brand Endorsement Error Messages Validation Functionality");

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    // =========================================================
    //                🔹 DATA PROVIDER SECTION
    // =========================================================
    @DataProvider(name = "BrandEndorsementErrorMessagesValidation")
    public Object[][] mentorshipDataProvider() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");
        return new Object[][] { { excel } };
    }

    // =========================================================
    //                🔹 MAIN TEST SECTION
    // =========================================================
    @Test(dataProvider = "BrandEndorsementErrorMessagesValidation")
    public void mentorshipShortListFunctionalityTest(ExcelUtil excel) throws Exception {

        // ✅ Read all test data values from Excel
        int testCaseIdCol = excel.getColumnIndex("testCaseId");
        int mentorNameCol = excel.getColumnIndex("MentorName");
        int nameIsRequiredCol = excel.getColumnIndex("nameIsRequired");
        int mobileIsRequiredCol = excel.getColumnIndex("mobileNumberIsRequired");
        int emailIsRequiredCol = excel.getColumnIndex("emailIsRequired");
        int nameMinCharCol = excel.getColumnIndex("nameMinimumCharacter");
        int invalidMobileCol = excel.getColumnIndex("invalidMobileNumber");
        int invalidEmailCol = excel.getColumnIndex("invalidEmailAdress");
        int nameMinimumCol = excel.getColumnIndex("NameMinimum");

        // Assuming test data is on row 1 for simplicity
        String testCaseId = excel.getCellData(1, testCaseIdCol).trim();
        String MentorName = excel.getCellData(1, mentorNameCol).trim();
        String nameIsRequired = excel.getCellData(1, nameIsRequiredCol).trim();
        String mobileNumberIsRequired = excel.getCellData(1, mobileIsRequiredCol).trim();
        String emailIsRequired = excel.getCellData(1, emailIsRequiredCol).trim();
        String nameMinimumCharacter = excel.getCellData(1, nameMinCharCol).trim();
        String invalidMobileNumber = excel.getCellData(1, invalidMobileCol).trim();
        String invalidEmailAdress = excel.getCellData(1, invalidEmailCol).trim();
        String NameMinimum = excel.getCellData(1, nameMinimumCol).trim();

        // =========================================================
        //                🔸 PLAYWRIGHT INITIALIZATION
        // =========================================================
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        // ✅ This will only run for filtered rows
        logger.info("Executing BrandEndorsementErrorMessagesValidation for TestCaseID: {}", testCaseId);

        // =========================================================
        //                🔸 LANDING PAGE
        // =========================================================
        LandingPop landingPop = new LandingPop(page);

        try {
            landingPop.getPopup().click();
            test.info("✅ Popup closed.");
        } catch (Exception ignored) {
            test.info("ℹ️ No popup found.");
        }

        // =========================================================
        //                🔸 DASHBOARD NAVIGATION
        // =========================================================
        DashboardPage dashboard = new DashboardPage(page);
        page.waitForTimeout(3000);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        logger.info("Clicked on Mentorship module");

        // =========================================================
        //                🔸 SEARCH MENTOR
        // =========================================================
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill(MentorName);
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);

        // =========================================================
        //                🔸 DESCRIPTION PAGE
        // =========================================================
        DescriptionPage serviceClick = new DescriptionPage(page);
        serviceClick.allLink().click();
        Locator buyRessuource=  serviceClick.buyResources().first();
        buyRessuource.scrollIntoViewIfNeeded();
        buyRessuource.click();
        
        
        
        
        
        
        // =========================================================
        //                🔸 ERROR MESSAGE VALIDATION
        // =========================================================
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(page);
        
        
        //click on Download Resource button
        MentorshipFormComponents clickDownloadResouce= new MentorshipFormComponents(page);
        clickDownloadResouce.downloadResource().click();

        // ✅ Required field validations
        String actualNameError = ErrorMessageValidation.nameIsRequired().textContent();
        String actualMobileError = ErrorMessageValidation.mobileNumberIsRequired().textContent();
        String actualEmailError = ErrorMessageValidation.emailIsRequired().textContent();

        Assert.assertEquals(actualNameError, nameIsRequired, "❌ Name error message mismatch!");
        Assert.assertEquals(actualMobileError, mobileNumberIsRequired, "❌ Mobile number error message mismatch!");
        Assert.assertEquals(actualEmailError, emailIsRequired, "❌ Email error message mismatch!");

        // ✅ Invalid inputs validation
        ErrorMessageValidation.nameTextField().first().fill(NameMinimum);
        ErrorMessageValidation.mobileTextField().nth(1).fill(invalidMobileNumber);
        ErrorMessageValidation.emailTextField().nth(1).fill(invalidEmailAdress);

        page.waitForTimeout(3000);
        String actualNameErrorValidation = ErrorMessageValidation.nameMinimumCharacter().textContent();
        String actualEmailErrorValidation = ErrorMessageValidation.invalidEmailAdress().textContent();
        String actualMobileErrorValidation = ErrorMessageValidation.invalidMobileNumber().textContent();
        
        
        

        // ✅ Assertions for invalid input messages
        Assert.assertEquals(actualNameErrorValidation, nameMinimumCharacter,
                "❌ Name minimum character validation failed!");
        Assert.assertEquals(actualEmailErrorValidation, invalidEmailAdress,
                "❌ Invalid email address validation failed!");
        Assert.assertEquals(actualMobileErrorValidation, invalidMobileNumber,
                "❌ Invalid mobile number validation failed!");
        
        
        
        
 //Invalid Negative data

String invalidNameSpecialChars = "@#$%^^";
String invalidMobileAlpha = "98767abcde";
String invalidEmailNoDomain = "testmail@";

//Clear fields
ErrorMessageValidation.nameTextField().first().fill("");
ErrorMessageValidation.mobileTextField().nth(1).fill("");
ErrorMessageValidation.emailTextField().nth(1).fill("");

page.waitForTimeout(1000);

//Fill  negative values
ErrorMessageValidation.nameTextField().first().fill(invalidNameSpecialChars);
ErrorMessageValidation.mobileTextField().nth(1).fill(invalidMobileAlpha);
ErrorMessageValidation.emailTextField().nth(1).fill(invalidEmailNoDomain);

//click submit button again to trigger validation message
clickDownloadResouce.downloadResource().click();

page.waitForTimeout(2000);


//✅ Assertions for invalid Error messages
Assert.assertEquals(actualNameErrorValidation, nameMinimumCharacter,
        "❌ Name minimum character validation failed!");
Assert.assertEquals(actualEmailErrorValidation, invalidEmailAdress,
        "❌ Invalid email address validation failed!");
Assert.assertEquals(actualMobileErrorValidation, invalidMobileNumber,
        "❌ Invalid mobile number validation failed!");



 
        
        

        test.pass("✅ Brand Endorsement Error Messages Validation passed for TestCase: " + testCaseId);

        extent.flush();
    }
}
