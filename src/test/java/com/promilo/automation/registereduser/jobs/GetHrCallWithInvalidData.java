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
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GetHrCallWithInvalidData extends BaseClass {

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


    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String email,
            String password,
            String name,
            String otp,
            String mailphone,
            String expectedResult
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        if (!keyword.equalsIgnoreCase("GetHRCall")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click();
        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(email);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.fintech();

        Locator fintechJobCard = page.locator("//span[@class='font-12 additional-tags-text additional-cards-text-truncate jobs-brand-additional-title']");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        fintechJobCard.scrollIntoViewIfNeeded();
        fintechJobCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        fintechJobCard.click();

        homePage.getHrCall().click();
        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill(mailphone);

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

        homePage.applyNameField().click();
        Thread.sleep(2000);
        homePage.getAnHrCallButton().click();

        Locator otpFields = page.locator("//input[starts-with(@aria-label, 'Please enter OTP character')]");
        otpFields.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
        for (int i = 0; i < otpFields.count(); i++) {
            otpFields.nth(i).fill(Character.toString(otp.charAt(i)));
        }
        page.locator("//button[text()='Verify & Proceed']").click();
        Thread.sleep(5000);

        homePage.getHrCallSubmitButton().click();

        // Invalid Name assertion
        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        test.info("⚡ Invalid Name Error Message Displayed: " + actualInvalidNameText);
        Assert.assertEquals(actualInvalidNameText, "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        // Invalid Email assertion
        page.locator("//input[@placeholder='Email*']").fill("invalidemail@");
        homePage.jobShortList().click();

        Locator invalidEmailError = page.locator("//div[text()='Invalid email address']");
        invalidEmailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidEmailText = invalidEmailError.innerText().trim();
        test.info("⚡ Invalid Email Error Message Displayed: " + actualInvalidEmailText);
        Assert.assertEquals(actualInvalidEmailText, "Invalid email address");
        
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

    }
}
