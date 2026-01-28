package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserShortListWithInvalidData extends BaseClass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // ✅ Restrict to only first non-empty row
        Object[][] data = new Object[1][7];
        data[0][0] = excel.getCellData(1, 0); // TestCaseID
        data[0][1] = excel.getCellData(1, 1); // Keyword
        data[0][2] = excel.getCellData(1, 3); // Email
        data[0][3] = excel.getCellData(1, 6); // Password
        data[0][4] = excel.getCellData(1, 7); // Name
        data[0][5] = excel.getCellData(1, 5); // OTP
        data[0][6] = excel.getCellData(1, 8); // MailPhone

        return data;
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(String testCaseId, String keyword, String email, String password, String name, String otp, String mailphone) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        if (!keyword.equalsIgnoreCase("JobShortList")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        Thread.sleep(5000);
        homePage.jobShortlist1().click();
        Thread.sleep(4000);

        // Enter intentionally invalid name and phone for negative testing
        page.locator("//input[@name='userName']").fill("123 U");
        page.locator("//input[@placeholder='Mobile*']").fill("karthik");

        homePage.jobShortList().click();

        // Assert invalid name error
        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        test.info("⚡ Invalid Name Error Message Displayed: " + actualInvalidNameText);
        Assert.assertEquals(actualInvalidNameText, "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        // Assert invalid phone error
        Locator invalidPhoneError = page.locator("//div[text()='Please enter valid phone number']");
        invalidPhoneError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidPhoneText = invalidPhoneError.innerText().trim();
        test.info("⚡ Invalid Phone Error Message Displayed: " + actualInvalidPhoneText);
        Assert.assertEquals(actualInvalidPhoneText, "Please enter valid phone number");

        // Enter invalid email for validation
        page.locator("//input[@placeholder='Email*']").fill("invalidemail@");
        homePage.jobShortList().click();

        // Assert invalid email error
        Locator invalidEmailError = page.locator("//div[text()='Invalid email address']");
        invalidEmailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidEmailText = invalidEmailError.innerText().trim();
        test.info("⚡ Invalid Email Error Message Displayed: " + actualInvalidEmailText);
        Assert.assertEquals(actualInvalidEmailText, "Invalid email address");

        // Capture screenshot post assertions
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup");

        test.pass("✅ All invalid data error messages validated successfully for TestCaseID: " + testCaseId);
    }

}
