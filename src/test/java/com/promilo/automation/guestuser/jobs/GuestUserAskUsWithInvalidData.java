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

public class GuestUserAskUsWithInvalidData extends BaseClass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        // Keep Excel logic but don’t use it for launching the script
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount - 1][8];
        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 3); // InputValue (Email)
            data[i - 1][3] = excel.getCellData(i, 6); // Password
            data[i - 1][4] = excel.getCellData(i, 7); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
            data[i - 1][7] = excel.getCellData(i, 10); // TextArea
        }
        return data;
    }

    /**
     * Test to validate Ask Us form validation messages with invalid data.
     */
    @Test
    public void applyForJobAsGuestUserWithInvalidData() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Guest User | Ask Us with Invalid Data");

        // Launch Playwright browser and navigate
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Landing page popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        // Navigate to job listings → Ask Us
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        
        page.locator("//p[text()='python developer']").nth(1).click();

        Thread.sleep(5000);
        page.locator("//button[@class='pointer border-1 p-50 border-chip ']").first().click();
Thread.sleep(4000);


// Fill details
page.locator("input[name='userName']").nth(1).fill("karthik123");
page.locator("//input[@placeholder='Mobile*']").nth(1).fill("123");
page.locator("//textarea[@id='feedbackDetails']").first().fill("");
page.locator("input[placeholder='Email*']").nth(1).fill("testuser110112@gmail.");
homePage.askUsSubmitButton().click();

        // Validate invalid name
        Locator invalidNameError = page.locator(
                "//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        test.info("⚡ Invalid Name Error Message: " + actualInvalidNameText);
        Assert.assertEquals(actualInvalidNameText,
                "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        // Validate invalid phone
        Locator invalidPhoneError = page.locator("//div[text()='Invalid Mobile number, must be exactly 10 digits']");
        invalidPhoneError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidPhoneText = invalidPhoneError.innerText().trim();
        test.info("⚡ Invalid Phone Error Message: " + actualInvalidPhoneText);

        // Validate question required
        Locator questionRequiredError = page.locator("//div[text()='Question is required']");
        questionRequiredError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualQuestionRequiredText = questionRequiredError.innerText().trim();
        test.info("⚡ Question Required Error Message: " + actualQuestionRequiredText);
        Assert.assertEquals(actualQuestionRequiredText, "Question is required");

        // Screenshot
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/AskUs_InvalidData.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Invalid Data Errors Screenshot");

        extent.flush();
    }
}
