package com.promilo.automation.courses.negativevalidations;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class RegisterWithUsNegativeValidation extends Baseclass {

	 // ✅ Move static boolean OUTSIDE method (required by Java)
    private static boolean hasRun = false;

    // ✅ DataProvider must be OUTSIDE any @Test method
    @DataProvider(name = "RegisterWithUsNegativeValidation")
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

    @Test(dataProvider = "RegisterWithUsNegativeValidation")
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


        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        Thread.sleep(3000);

        // Close popup if present
        try {
            new MayBeLaterPopUp(page).getPopup().click();
        } catch (Exception ignored) {}

        Thread.sleep(2000);

        // Navigate to Courses
        page.locator("//a[text()='Courses']").click();
        Thread.sleep(5000);

        // Register Now
        page.locator("//button[text()='Register Now']").click();

        MentorshipErrorMessagesAndToasters errorMsgs = new MentorshipErrorMessagesAndToasters(page);
        FormComponents fields = new FormComponents(page);

        // ------------------------------------------------------
        //              REQUIRED FIELD VALIDATIONS
        // ------------------------------------------------------
        String actualNameError = errorMsgs.nameIsRequired().textContent();
        String actualMobileError = errorMsgs.mobileNumberIsRequired().textContent();
        String actualEmailError = errorMsgs.emailIsRequired().textContent();
      

        Assert.assertEquals(actualNameError, nameIsRequired, "❌ Name is required validation mismatch!");
        Assert.assertEquals(actualMobileError, mobileNumberIsRequired, "❌ Mobile number is required mismatch!");
        Assert.assertEquals(actualEmailError, emailIsRequired, "❌ Email is required mismatch!");
        
        
        String locationError= page.locator("//div[text()='Preferred location to study is required']").textContent().trim();
        Assert.assertEquals(locationError, "Preferred location to study is required");
        
        String passwordError= page.locator("//div[text()='Password is required']").textContent().trim();
        Assert.assertEquals(passwordError, "Password is required");
        
        
        String preferredCourseError= page.locator("//div[text()='Preferred courses is required']").textContent().trim();
        Assert.assertEquals(preferredCourseError, "Preferred courses is required");
        
        
        
        // ------------------------------------------------------
        //              INVALID INPUTS VALIDATIONS
        // ------------------------------------------------------
        fields.userNameField().fill(NameMinimum);
        fields.mobileField().fill(invalidMobileNumber);
        fields.emailTextField().fill(invalidEmailAdress);

        page.waitForTimeout(2000);

        String actualNameMinError = page.locator("//div[text()='Must be alteast 3 character']").textContent().trim();
        String actualEmailInvalid = errorMsgs.invalidEmailAdress().textContent();
        String actualMobileInvalid = errorMsgs.invalidMobileNumber().textContent();

        Assert.assertEquals(actualNameMinError, "Must be alteast 3 character");
        Assert.assertEquals(actualEmailInvalid, invalidEmailAdress);
        Assert.assertEquals(actualMobileInvalid, invalidMobileNumber);

        // ------------------------------------------------------
        //              EXTRA NEGATIVE VALIDATION
        // ------------------------------------------------------
        fields.userNameField().fill("@#$%^^");
        fields.mobileField().fill("98767abcde");
        fields.emailTextField().fill("testmail@");

        page.waitForTimeout(2000);

        Assert.assertEquals(actualNameMinError, "Must be alteast 3 character");
        Assert.assertEquals(actualEmailInvalid, invalidEmailAdress);
        Assert.assertEquals(actualMobileInvalid, invalidMobileNumber);
        
        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        String serverId = "qtvjnqv9";
        int randomNum = 10000 + new Random().nextInt(90000);
        String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

        
        fields.userNameField().fill("karthik");
        fields.mobileField().fill(randomPhone);
        fields.emailTextField().fill(randomEmail);
        
        page.locator("#preferredLocation").click();
        test.info("📍 Clicked Preferred Location dropdown.");
        Thread.sleep(2000);

        page.locator("//label[text()='Ahmedabad']").click();
        test.info("✅ Selected Location: Ahmedabad");
        Thread.sleep(2000);

        page.locator("//input[@placeholder='Preferred Course to Study*']").click();
        test.info("🎓 Clicked Preferred Course to Study field.");
        Thread.sleep(2000);

        page.locator("//label[text()='Engineering']").click();
        test.info("✅ Selected Course: Engineering");
        Thread.sleep(2000);

        page.locator("//label[text()='B.E. / B.Tech']").click();
        test.info("✅ Selected Specialization: B.E. / B.Tech");
        Thread.sleep(2000);

        page.locator("#password").fill("123456kk@");
        test.info("🔑 Entered Password: 123456kk@");


        
        
        page.locator("//button[text()='Register Now']").click();


        
        
     // -------------------- ENTER OTP --------------------
        
        String validOtp="9999";
        if (validOtp.length() != 4)
            throw new RuntimeException("Invalid OTP in Excel!");

        for (int j = 0; j < 4; j++) {
            String digit = validOtp.substring(j, j + 1);
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(8000)
                    .setState(WaitForSelectorState.VISIBLE));
            otpField.fill(digit);
        }
        errorMsgs.verifyAndProceedButton().click();
        
        
        
        page.waitForTimeout(5000);
        
        
        
        
        

        
        

        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
        );

        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));

        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "❌ Expected 'Thank You!', found: " + popupText);

        test.pass("🎉 Thank You popup validated successfully");

        extent.flush();
    }
}
