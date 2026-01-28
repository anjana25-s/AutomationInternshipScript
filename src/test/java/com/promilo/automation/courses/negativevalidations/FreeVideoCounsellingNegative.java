package com.promilo.automation.courses.negativevalidations;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.courses.intrestspages.FreeVideoCounsellingPage;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class FreeVideoCounsellingNegative extends BaseClass {

    // ✅ Prevent duplicate execution
    private static boolean hasRun = false;

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

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 ConnectNow Functionality | Data-Driven");

        // Run only when keyword matches
        if (!"BookMeetingErrorValidation".equalsIgnoreCase(keyword)) {
            return;
        }

        // Prevent duplicate execution
        if (hasRun) {
            return;
        }
        hasRun = true;

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        Thread.sleep(3000);
        test.info("⏱ Waited 3 seconds for page load.");

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();
        test.info("✅ Popup closed successfully.");
        
        
        
        page.waitForTimeout(3000);
        page.locator("//a[text()='Courses']").click();

        

        FreeVideoCounsellingPage freeVideoCounselling = new FreeVideoCounsellingPage(page);
        page.locator("//input[@placeholder='Search Colleges and Courses']").fill("BTWIN");
        page.locator("//input[@placeholder='Search Colleges and Courses']").click();
        page.keyboard().press("Enter");
	        

        test.info("🔍 Entered course search text: Course auto");

        page.locator("//button[text()='Free Video Counselling']").click();

      
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(page);

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

page.waitForTimeout(2000);

page.locator("//button[text()='Free Video Counselling']").click();

//✅ Assertions for invalid Error messages
Assert.assertEquals(actualNameErrorValidation, nameMinimumCharacter,
        "❌ Name minimum character validation failed!");
Assert.assertEquals(actualEmailErrorValidation, invalidEmailAdress,
        "❌ Invalid email address validation failed!");
Assert.assertEquals(actualMobileErrorValidation, invalidMobileNumber,
        "❌ Invalid mobile number validation failed!");



 
        
        

        test.pass("✅ Brand Endorsement Error Messages Validation passed for TestCase: " + testCaseId);

    }
}
