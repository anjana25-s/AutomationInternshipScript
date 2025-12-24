package com.promilo.automation.courses.negativevalidations;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CourseShortListNegative extends Baseclass {

    // ✅ Move static boolean OUTSIDE method (required by Java)
    private static boolean hasRun = false;

    // ✅ DataProvider must be OUTSIDE any @Test method
    @DataProvider(name = "TalkToExpertNegativeValidation")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");

        int rowCount = excel.getRowCount();
        Object[][] data = new Object[rowCount][15];

        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0);
            data[i - 1][1] = excel.getCellData(i, 1);
            data[i - 1][2] = excel.getCellData(i, 2);
            data[i - 1][3] = excel.getCellData(i, 3);
            data[i - 1][4] = excel.getCellData(i, 4);
            data[i - 1][5] = excel.getCellData(i, 5);
            data[i - 1][6] = excel.getCellData(i, 6);
            data[i - 1][7] = excel.getCellData(i, 7);
            data[i - 1][8] = excel.getCellData(i, 8);
            data[i - 1][9] = excel.getCellData(i, 9);
            data[i - 1][10] = excel.getCellData(i, 10);
            data[i - 1][11] = excel.getCellData(i, 11);
            data[i - 1][12] = excel.getCellData(i, 12);
            data[i - 1][13] = excel.getCellData(i, 13);
            data[i - 1][14] = excel.getCellData(i, 14);
        }
        return data;
    }

    // ============================
    //        MAIN TEST
    // ============================

    @Test(dataProvider = "TalkToExpertNegativeValidation")
    public void TaltoExpertIntrest(
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
    ) throws InterruptedException, IOException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 TalkToExperts Functionality | Data-Driven");

        // Keyword match
        if (!"BookMeetingErrorValidation".equalsIgnoreCase(keyword)) {
            return;
        }

        // Prevent duplicate execution
        if (hasRun) {
            return;
        }
        hasRun = true;

        Page Talktopage = initializePlaywright();
        Talktopage.navigate(prop.getProperty("url"));
        Talktopage.setViewportSize(1000, 768);

        Thread.sleep(3000);
        test.info("⏱ Waited 3 seconds for page load.");

        // Close popup
        MayBeLaterPopUp popup = new MayBeLaterPopUp(Talktopage);
        popup.getPopup().click();
        test.info("✅ Popup closed successfully.");

        Thread.sleep(3000);

        // Step 1: Click Courses
        Talktopage.locator("//a[text()='Courses']").click();
        test.info("📚 Clicked on 'Courses'.");

        // Step 2: Search course
        Talktopage.locator("//input[@placeholder='Search Colleges and Courses']").fill("fill");
        test.info("🔍 Entered course search text: Course auto");

        Talktopage.keyboard().press("Enter");
        test.info("↩️ Pressed Enter to search courses.");

                
        
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(Talktopage);
        FormComponents feedbackTextFields= new FormComponents(Talktopage);
        
        Talktopage.locator("[class='pointer shortlist-btn border-chip  d-flex']").click();
        Talktopage.locator("//button[text()='Shortlist']").click();

        
                
        
        
        
        
        // ✅ Required field validations
        String actualNameError = ErrorMessageValidation.nameIsRequired().textContent();
        String actualMobileError = ErrorMessageValidation.mobileNumberIsRequired().textContent();
        String actualEmailError = ErrorMessageValidation.emailIsRequired().textContent();

        Assert.assertEquals(actualNameError, nameIsRequired, "❌ Name error message mismatch!");
        Assert.assertEquals(actualMobileError, mobileNumberIsRequired, "❌ Mobile number error message mismatch!");
        Assert.assertEquals(actualEmailError, emailIsRequired, "❌ Email error message mismatch!");

        // ✅ Invalid inputs validation
        feedbackTextFields.userNameField().nth(1).fill(NameMinimum);
        feedbackTextFields.mobileField().nth(1).fill(invalidMobileNumber);
        feedbackTextFields.emailTextField().fill(invalidEmailAdress);

        Talktopage.waitForTimeout(3000);
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
feedbackTextFields.userNameField().nth(1).fill("");
feedbackTextFields.mobileField().nth(1).fill("");
feedbackTextFields.emailTextField().fill("");



Talktopage.waitForTimeout(1000);

//Fill  negative values
feedbackTextFields.userNameField().nth(1).fill(invalidNameSpecialChars);
feedbackTextFields.mobileField().nth(1).fill(invalidMobileAlpha);
feedbackTextFields.emailTextField().fill(invalidEmailNoDomain);

//click submit button again to trigger validation message
Talktopage.locator("//button[text()='Shortlist']").click();


Talktopage.waitForTimeout(2000);


//✅ Assertions for invalid Error messages
Assert.assertEquals(actualNameErrorValidation, nameMinimumCharacter,
        "❌ Name minimum character validation failed!");
Assert.assertEquals(actualEmailErrorValidation, invalidEmailAdress,
        "❌ Invalid email address validation failed!");
Assert.assertEquals(actualMobileErrorValidation, invalidMobileNumber,
        "❌ Invalid mobile number validation failed!");




    }
}
