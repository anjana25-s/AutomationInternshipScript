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
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignUpLogoutUtil;

public class NotifySimilarJobsWithInvalidData extends Baseclass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
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
    public void applyForJobTestFromExcel(
            String testCaseId,
            String keyword,
            String inputvalue,
            String password,
            String name,
            String otp,
            String mailphone,
            int rowIndex
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Apply for Job as Registered User | " + testCaseId);

        if (!(keyword.equalsIgnoreCase("RegisteredUserJobShortList") ||
              keyword.equalsIgnoreCase("RegisteredUserJobShortListWithSignup"))) {
            return;
        }

        if (keyword.equalsIgnoreCase("RegisteredUserJobShortListWithSignup")) {
            SignUpLogoutUtil signupUtil = new SignUpLogoutUtil();
            String[] generatedCreds = signupUtil.createAccountAndLoginFromExcel(
                    new ExcelUtil(Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString(), "PromiloTestData"),
                    rowIndex);
            inputvalue = generatedCreds[0];
            password = generatedCreds[1];

            // Logout after signup
            Page tempPage = initializePlaywright();
            tempPage.navigate(prop.getProperty("url"));
            tempPage.setViewportSize(1366, 768);
            tempPage.close();
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone, testCaseId, test);

        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name,
                                            String otp, String mailphone, String testCaseId, ExtentTest test) throws Exception {
        LandingPage landingPage = new LandingPage(page);
        try { landingPage.getPopup().click(); } catch (Exception ignored) {}
        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        // Navigate to Job Listing and Fintech section
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.fintech();

        // Locate and click on the first Fintech job card
        Locator fintechJobCard = page.locator("//span[@class='font-12 additional-tags-text additional-cards-text-truncate jobs-brand-additional-title']");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        fintechJobCard.scrollIntoViewIfNeeded();
        fintechJobCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        fintechJobCard.click();

        // Click on 'Notify Similar Jobs' and fill required details
        homePage.notifySimilarJobs().click();
        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill(mailphone);
        homePage.sendSimilarJobs().click();

        // Fill OTP fields
        Locator otpFields = page.locator("//input[starts-with(@aria-label, 'Please enter OTP character')]");
        otpFields.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
        for (int i = 0; i < otpFields.count() && i < otp.length(); i++) {
            otpFields.nth(i).fill(Character.toString(otp.charAt(i)));
        }

        page.locator("//button[text()='Verify & Proceed']").click();

        // Validate invalid name
        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        test.info("⚡ Invalid Name Error Message Displayed: " + actualInvalidNameText);
        Assert.assertEquals(actualInvalidNameText, "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        // Enter invalid email
        page.locator("//input[@placeholder='Email*']").fill("invalidemail@");
        homePage.jobShortList().click();

        // Validate invalid email
        Locator invalidEmailError = page.locator("//div[text()='Invalid email address']");
        invalidEmailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidEmailText = invalidEmailError.innerText().trim();
        test.info("⚡ Invalid Email Error Message Displayed: " + actualInvalidEmailText);
        Assert.assertEquals(actualInvalidEmailText, "Invalid email address");

        // Screenshot for reporting
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_shortlist_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "Screenshot after job shortlist");

        byte[] fileContent = Files.readAllBytes(Paths.get(screenshotPath));
        String base64Screenshot = Base64.getEncoder().encodeToString(fileContent);
        test.addScreenCaptureFromBase64String(base64Screenshot, "Base64 Screenshot after job shortlist");

        page.close();
        test.info("Browser closed for TestCaseID: " + testCaseId);
    }
}
