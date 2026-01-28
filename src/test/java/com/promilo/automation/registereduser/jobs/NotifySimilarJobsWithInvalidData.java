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
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class NotifySimilarJobsWithInvalidData extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        // Keep Excel snippet intact for reference
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // Return only the first relevant test row
        Object[][] data = new Object[1][8];
        data[0][0] = excel.getCellData(1, 0); // TestCaseID
        data[0][1] = excel.getCellData(1, 1); // Keyword
        data[0][2] = excel.getCellData(1, 3); // InputValue
        data[0][3] = excel.getCellData(1, 6); // Password
        data[0][4] = excel.getCellData(1, 7); // Name
        data[0][5] = excel.getCellData(1, 5); // OTP
        data[0][6] = excel.getCellData(1, 8); // MailPhone
        data[0][7] = 1;                        // RowIndex

        return data;
    }

    // Perform Mailosaur signup once
    @Test(priority = 0)
    public void performMailosaurSignup() throws Exception {
        if (registeredEmail == null || registeredPassword == null) {
            SignupWithMailosaurUI mailosaurSignup = new SignupWithMailosaurUI();
            String[] creds = mailosaurSignup.performSignupAndReturnCredentials();
            registeredEmail = creds[0];
            registeredPassword = creds[1];
            System.out.println("✅ Mailosaur Signup completed. Email: " + registeredEmail);
        }
    }

    @Test(dataProvider = "jobApplicationData", priority = 1)
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

        // Only proceed if keyword matches expected
        if (!(keyword.equalsIgnoreCase("RegisteredUserJobShortList") ||
              keyword.equalsIgnoreCase("RegisteredUserJobShortListWithSignup"))) {
            return;
        }

        // Use Mailosaur credentials for login/signup
        inputvalue = registeredEmail;
        password = registeredPassword;

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone, testCaseId, test);

        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name,
                                            String otp, String mailphone, String testCaseId, ExtentTest test) throws Exception {
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();
        test.info("✅ Logged in as Mailosaur user: " + inputvalue);

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.fintech();

        Locator fintechJobCard = page.locator("//span[@class='font-12 additional-tags-text additional-cards-text-truncate jobs-brand-additional-title']");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        fintechJobCard.scrollIntoViewIfNeeded();
        fintechJobCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        fintechJobCard.click();

        homePage.notifySimilarJobs().click();
        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill(mailphone);
        homePage.sendSimilarJobs().click();

        Locator otpFields = page.locator("//input[starts-with(@aria-label, 'Please enter OTP character')]");
        otpFields.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
        for (int i = 0; i < otpFields.count() && i < otp.length(); i++) {
            otpFields.nth(i).fill(Character.toString(otp.charAt(i)));
        }

        page.locator("//button[text()='Verify & Proceed']").click();

        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        test.info("⚡ Invalid Name Error Message Displayed: " + actualInvalidNameText);
        Assert.assertEquals(actualInvalidNameText, "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        page.locator("//input[@placeholder='Email*']").fill("invalidemail@");
        homePage.jobShortList().click();

        Locator invalidEmailError = page.locator("//div[text()='Invalid email address']");
        invalidEmailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidEmailText = invalidEmailError.innerText().trim();
        test.info("⚡ Invalid Email Error Message Displayed: " + actualInvalidEmailText);
        Assert.assertEquals(actualInvalidEmailText, "Invalid email address");

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
