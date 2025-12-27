package com.promilo.automation.emailnotifications.jobapply;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class WhenUsersRescheduleRequestIsDeclined extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test;

    private static final Logger logger = LogManager.getLogger(WhenUsersRescheduleRequestIsDeclined.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before entire suite using Mailosaur UI signup...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloJob");

        int rowCount = 0;
        for (int i = 1; i <= 1; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty())
                break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount][8];
        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 4); // InputValue (ignored)
            data[i - 1][3] = excel.getCellData(i, 6); // Password (ignored)
            data[i - 1][4] = excel.getCellData(i, 7); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
            data[i - 1][7] = i; // RowIndex
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
            int rowIndex) throws Exception {

        test = extent.createTest("📌 Job Application & Reschedule Reject Flow | TestCase: " + testCaseId);

        if (registeredEmail == null || registeredPassword == null) {
            test.fail("❌ Signup credentials not found.");
            Assert.fail("Signup not completed.");
            return;
        }

        // Override input credentials with signed up ones
        inputvalue = registeredEmail;
        password = registeredPassword;

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        test.info("🌐 Navigated to application URL");

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

        test.pass("✅ Job application & reschedule reject flow passed for TestCase: " + testCaseId);
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name, String otp,
            String mailphone) throws Exception {

        test.info("🔑 Logging in with registered user");

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
            test.info("✅ Closed popup successfully");
        } catch (Exception ignored) {
            test.warning("⚠️ Popup not displayed");
        }

        mayBeLaterPopUp.clickLoginButton();
        test.info("➡️ Clicked Login button");

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();
        test.info("✅ Login successful with email: " + inputvalue);

        applyJobDetailsFlow(page, name, otp, mailphone);
    }

    private void applyJobDetailsFlow(Page page, String name, String otp, String mailphone) throws Exception {
        JobListingPage jobPage = new JobListingPage(page);

        Thread.sleep(5000);
        jobPage.homepageJobs().click();
        test.info("✅ Clicked on homepage Jobs");

        page.locator("//input[@placeholder='Search Jobs']").fill("December Campaign Automation");
        page.keyboard().press("Enter");
        Thread.sleep(4000);

        page.locator("//button[text()='Apply Now']").first().click();
        jobPage.applyNameField().fill("karthik");
        test.info("📝 Entered Name: karthik");

        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty())
                ? mailphone
                : ("90000" + String.format("%05d", random.nextInt(100000)));
        jobPage.applyNowMobileTextField().fill(mobileToUse);
        test.info("📱 Entered Mobile: " + mobileToUse);

        jobPage.selectIndustryDropdown().click();
        test.info("📂 Opened Industry Dropdown");

        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX",
                "Healthcare", "Education");

        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            boolean found = false;
            for (int i = 0; i < options.count(); i++) {
                if (options.nth(i).innerText().trim().equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found)
                test.warning("⚠️ Industry not found: " + industry);
        }
        jobPage.applyNameField().click();

        Locator applyBtn = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyBtn.scrollIntoViewIfNeeded();
        applyBtn.click();
        test.info("➡️ Clicked on Apply button");

        // OTP input logic
        if (otp == null || otp.length() < 4)
            throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

        for (int i = 0; i < 4; i++) {
            String digit = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.click();
            otpField.fill("");
            otpField.fill(digit);
        }
        test.info("🔐 Entered OTP: " + otp);

        page.locator("//button[text()='Verify & Proceed']").click();
        test.info("✅ Clicked Verify & Proceed");

        Locator firstEnabledDate = page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
        firstEnabledDate.click();

        Thread.sleep(2000);
        page.locator("//li[@class='time-slot-box list-group-item']").first().click();

        page.locator("//button[text()='Next']").click();
        page.locator("//button[text()='Submit']").nth(1).click();
        test.info("✅ Submitted Job Application");

        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor();
        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, found: " + popupText);
        test.pass("🎉 Job applied successfully — Popup: " + popupText);

        page.locator("img[alt='closeIcon Ask us']").first().click();

        page.locator("//span[text()='My Interest']").click();
        page.locator("img[alt='Reschedule']").first().click();
        test.info("🔄 Initiated Reschedule Request");

        page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first().click();
        page.locator("//li[@class='time-slot-box list-group-item']").first().click();

        page.locator("//button[text()='Continue']").click();
        test.info("✅ Submitted Reschedule Request");

        // Advertiser Side
        Page page3 = page.context().newPage();
        page3.navigate("https://stagebusiness.promilo.com/");
        page3.setViewportSize(1000, 768);
        test.info("🌐 Opened Advertiser Portal");

        AdvertiserLoginPage login = new AdvertiserLoginPage(page3);
        login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
        login.loginPasswordField().fill("Karthik@88");
        login.signInButton().click();
        test.info("✅ Advertiser logged in");

        AdvertiserHomepage myaccount = new AdvertiserHomepage(page3);
        myaccount.hamburger().click();
        myaccount.myAccount().click();

        AdverstiserMyaccount prospect = new AdverstiserMyaccount(page3);
        prospect.myProspect().click();

        AdvertiserProspects approveFunctionality = new AdvertiserProspects(page3);
        approveFunctionality.Jobs().click();

        page3.locator("//span[text()='Reschedule Request']").first().click();
        test.info("📩 Advertiser viewed Reschedule Request");

        page3.locator("//button[text()='Cancel Request']").click();
        page3.locator("//button[contains(text(),'Reject')]").click();
        test.pass("❌ Advertiser rejected Reschedule Request");

        Locator rejectionMessage = page3.locator("//div[contains(text(),\"You have Successfully rejected user's\")]");
        rejectionMessage.waitFor();
        System.out.println("Reject functionality executed: " + rejectionMessage.textContent());
    }
}
