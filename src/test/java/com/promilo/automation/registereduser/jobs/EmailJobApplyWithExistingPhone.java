package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class EmailJobApplyWithExistingPhone extends Baseclass {

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
     * Test to apply for a job as a registered user using email and existing phone with dynamic field handling.
     */
    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String fieldType,
            String inputValue,
            String expectedResult,
            String otp,
            String password,
            String name,
            String applyPhone
    ) throws Exception {

        // Initialize Extent Report for structured reporting
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        // Skip test if keyword does not match 'MailJobInvalidOtp'
        if (!keyword.equalsIgnoreCase("MailJobInvalidOtp")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        // Launch Playwright browser and navigate to application URL
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 1000);

        // Interact with landing page to close popup and click login
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click();
        landingPage.clickLoginButton();

        // Login page initialization and dynamic field handling
        LoginPage loginPage = new LoginPage(page);

        if ("email".equalsIgnoreCase(fieldType) || "phone".equalsIgnoreCase(fieldType)) {
            loginPage.loginMailPhone().fill(inputValue);
        } else {
            test.warning("⚠️ Unknown fieldType for " + testCaseId + ": " + fieldType);
        }

        // Fill password if provided
        if (password != null && !password.trim().isEmpty()) {
            loginPage.passwordField().fill(password);
        }

        loginPage.loginButton().click();

        // Navigate to job listing and click 'Apply Now'
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.applyNow().click();
        Thread.sleep(2000);

        // Fill name if provided
        if (name != null && !name.trim().isEmpty()) {
            homePage.applyNameField().fill(name);
        }

        // Fill apply phone if provided
        if (applyPhone != null && !applyPhone.trim().isEmpty()) {
            homePage.applyNowMobileTextField().fill(applyPhone);
        }

        // Select multiple industries dynamically
        homePage.selectIndustryDropdown().click();
        Thread.sleep(1000);

        List<String> industries = Arrays.asList(
                "Telecom / ISP",
                "Advertising & Marketing",
                "Animation & VFX",
                "Healthcare",
                "Education"
        );

        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            boolean found = false;
            for (int i = 0; i < options.count(); i++) {
                String optionText = options.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found) {
                test.warning("⚠️ Industry not found: " + industry);
            }
        }

        homePage.applyNameField().click(); // Close dropdown focus
        Thread.sleep(1000);

        // Locate and click Apply button
        Locator applyButton = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyButton.scrollIntoViewIfNeeded();
        applyButton.click();
        Thread.sleep(2000);

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

        // Close page after test
        page.close();
        test.info("Browser closed for TestCaseID: " + testCaseId);

        // Flush Extent Report to persist logs and screenshots
        extent.flush();
    }
}
