package com.promilo.automation.registereduser.jobs;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;


public class AskUsWithInvalidData extends BaseClass {

	@DataProvider(name = "jobApplicationData")
	public Object[][] jobApplicationData() throws Exception {
	    String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	    ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

	    int rowCount = 0;
	    for (int i = 1; i <= 1000; i++) {  // Assumes max 1000 rows (tweak if needed)
	        String testCaseId = excel.getCellData(i, 0);
	        if (testCaseId == null || testCaseId.isEmpty()) break;
	        rowCount++;
	    }

	    Object[][] data = new Object[rowCount][8];
	    for (int i = 1; i <= rowCount; i++) {
	        data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
	        data[i - 1][1] = excel.getCellData(i, 1); // Keyword
	        data[i - 1][2] = excel.getCellData(i, 3); // InputValue
	        data[i - 1][3] = excel.getCellData(i, 6); // Password
	        data[i - 1][4] = excel.getCellData(i, 7); // Name
	        data[i - 1][5] = excel.getCellData(i, 5); // OTP
	        data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
	        data[i - 1][7] = i;                       // RowIndex
	    }
	    return data;
	}

    /**
     * Test to validate Ask Us form validation messages with invalid data.
     */
    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(String testCaseId, String keyword, String email, String password, String name, String otp, String mailphone, String TextArea) throws Exception {

        // Initialize Extent Report for structured reporting
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        // Skip test if the keyword does not match 'Askus?'
        if (!keyword.equalsIgnoreCase("Askus?")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        // Launch Playwright browser and navigate to the URL
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Interact with landing page to close popup and click login
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click();
        landingPage.clickLoginButton();

        // Perform login with provided credentials
        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(email);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        // Navigate to job listings and click on Ask Us
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        Thread.sleep(5000); // Wait for jobs to load
        homePage.askUs().click();
        Thread.sleep(4000); // Wait for page transition

        // Fill invalid data for name, mobile, and leave textarea empty
        page.locator("//input[@name='userName']").fill("123 U");
        page.locator("//input[@placeholder='Mobile*']").fill("abce");
        page.locator("//textarea[@id='feedbackDetails']").fill("");
        homePage.askUsSubmitButton().click();

        // Assertion for invalid name error message
        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        test.info("⚡ Invalid Name Error Message Displayed: " + actualInvalidNameText);
        Assert.assertEquals(actualInvalidNameText, "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        // Assertion for invalid phone number error message
        Locator invalidPhoneError = page.locator("//div[text()='Please enter valid phone number']");
        invalidPhoneError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidPhoneText = invalidPhoneError.innerText().trim();
        test.info("⚡ Invalid Phone Error Message Displayed: " + actualInvalidPhoneText);
        Assert.assertEquals(actualInvalidPhoneText, "Please enter valid phone number");

        // Assertion for empty question error message
        Locator questionRequiredError = page.locator("//div[text()='Question is required']");
        questionRequiredError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualQuestionRequiredText = questionRequiredError.innerText().trim();
        test.info("⚡ 'Question is required' Error Message Displayed: " + actualQuestionRequiredText);
        Assert.assertEquals(actualQuestionRequiredText, "Question is required");

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_shortlist_pass.png";

     // Take screenshot and save as file
     page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));

     // Attach file path screenshot
     test.addScreenCaptureFromPath(screenshotPath, "Screenshot after job shortlist");

     // Read as Base64
     byte[] fileContent = Files.readAllBytes(Paths.get(screenshotPath));
     String base64Screenshot = Base64.getEncoder().encodeToString(fileContent);

     // Attach Base64 screenshot
     test.addScreenCaptureFromBase64String(base64Screenshot, "Base64 Screenshot after job shortlist");


        // Flush Extent Report to persist logs and screenshots
        extent.flush();
    }
}
