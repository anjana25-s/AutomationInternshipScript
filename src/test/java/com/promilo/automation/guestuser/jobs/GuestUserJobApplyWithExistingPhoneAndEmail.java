package com.promilo.automation.guestuser.jobs;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserJobApplyWithExistingPhoneAndEmail extends BaseClass{
	
	
    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        Object[][] data = new Object[rowCount - 1][6];

        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 3); // Email
            data[i - 1][3] = excel.getCellData(i, 6); // Password
            data[i - 1][4] = excel.getCellData(i, 4); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
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
            String otp
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        if (!keyword.equalsIgnoreCase("ValidSignup")) {
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
        homePage.applyNow().click();
        Thread.sleep(2000);

        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill("9000087669");

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
        Thread.sleep(1000);

        Locator applyButton = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyButton.scrollIntoViewIfNeeded();
        applyButton.click();
        Thread.sleep(2000);

        ;

        page.locator("//span[@aria-label='July 18, 2025']").click();
        page.locator("//li[@class='time-slot-box list-group-item']").click();
        page.locator("//button[text()='Submit' and @class='fw-bold w-100 font-16 fw-bold calendar-modal-custom-btn mt-2 btn btn-primary']").click();

        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(
                popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, but found: " + popupText
        );

        test.info("✅ 'Thank You!' popup appeared successfully with text: " + popupText);

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_signup_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after signup");

        page.close();
        test.info("Browser closed for TestCaseID: " + testCaseId);
        extent.flush();
    }

}

