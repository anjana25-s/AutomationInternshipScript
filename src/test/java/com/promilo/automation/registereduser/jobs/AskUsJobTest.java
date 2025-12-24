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
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AskUsJobTest extends Baseclass {

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
     * Test to apply for a job as a registered user on the 'Ask Us' job section.
     */
    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(String testCaseId, String keyword, String email, String password, String name, String otp, String mailphone, String TextArea) throws Exception {

        // Initialize Extent Report for structured reporting
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        // Skip test execution if the keyword does not match 'Askus?'
        if (!keyword.equalsIgnoreCase("Askus?")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        // Launch browser using Playwright
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Handle landing page popup and proceed to login
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click();
        landingPage.clickLoginButton();

        // Perform login with provided email and password
        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(email);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        // Navigate to homepage jobs and select the 'Ask Us' job
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        Thread.sleep(5000); // Wait for jobs to load
        homePage.askUs().click();
        Thread.sleep(4000); // Wait for Ask Us page to load

        // Fill user details for Ask Us form
        page.locator("//input[@name='userName']").fill("karthik U");
        page.locator("//input[@placeholder='Mobile*']").fill("9000090780");
        page.locator("//textarea[@id='feedbackDetails']").fill("something");
        homePage.askUsSubmitButton().click();

        //Enter the otp to otp textFields.
        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP provided is less than 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            int attempts = 0;

            while (!filled && attempts < 3) {
                attempts++;
                otpField.click(); // force focus
                otpField.fill(""); // clear previous
                otpField.fill(otpChar);

                // Validate the field actually has the entered digit
                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500); // wait before retry
                }
            }

            if (!filled) {
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1) + " correctly after retries.");
            }
        }



        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();

        // Locate 'Thank You!' popup and validate its appearance
        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String popupText = thankYouPopup.innerText().trim();

        // Assert ignoring case sensitivity for popup text
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"), "Expected 'Thank You!' popup, but found: " + popupText);
        test.info("✅ 'Thank You!' popup appeared successfully with text: " + popupText);

        // Pause to stabilize screenshot capture
        Thread.sleep(3000);

        // Mark test as passed in Extent Report
        test.pass("✅ Flow completed successfully for TestCaseID: " + testCaseId);

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

        // Close Playwright page and context
        page.context().close();
        page.close();

        // Log browser closure in the report
        test.info("Browser closed for TestCaseID: " + testCaseId);

        // Flush Extent Report to ensure logs and screenshots are saved
        extent.flush();
    }

}
