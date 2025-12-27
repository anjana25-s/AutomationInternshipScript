package com.promilo.automation.myassignments;

import static org.testng.Assert.assertEquals;

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
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.emailnotifications.jobapply.UserInitiatesApplyingForJob;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MyassignmentDraft extends BaseClass {

	ExtentReports extent = ExtentManager.getInstance();
    // Test Description
    ExtentTest test = extent.createTest("🚀 promilo shortlist functionality with email notification validation");

    private static final Logger logger = LogManager.getLogger(UserInitiatesApplyingForJob.class);

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
    public void applyForJobTestFromExcel(String testCaseId, String keyword, String inputvalue, String password,
            String name, String otp, String mailphone, int rowIndex) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Apply for Job as Registered User | " + testCaseId);

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

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

        test.pass("✅ Job application test passed for TestCase: " + testCaseId);
        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name, String otp,
            String mailphone) throws Exception {
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
        } catch (Exception ignored) {
        }

        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();

        Locator developerJob = page.locator("//h3[text()='Developer']").first();
        developerJob.click();
        test.info("Clicked on Developer job listing");

        Thread.sleep(4000);

        page.locator("//button[text()='Apply Now']").first().click();

        jobPage.applyNameField().fill("karthik");
        // Generate random number if mailphone is null/empty
        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty()) ? mailphone
                : ("90000" + String.format("%05d", random.nextInt(100000)));

        jobPage.applyNowMobileTextField().fill(mobileToUse);
        jobPage.selectIndustryDropdown().click();
        Thread.sleep(1000);

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

        jobPage.applyNameField().click(); // blur to trigger validation
        Thread.sleep(1000);

        Locator applyBtn = page
                .locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyBtn.scrollIntoViewIfNeeded();
        applyBtn.click();
        Thread.sleep(2000);

        // OTP input logic
        if (otp == null || otp.length() < 4)
            throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

        for (int i = 0; i < 4; i++) {
            String digit = Character.toString(otp.charAt(i));
            Locator otpField = page
                    .locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            for (int retry = 0; retry < 3; retry++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(digit);

                if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                    break;
                page.waitForTimeout(500);
            }
        }

        page.locator("//button[text()='Verify & Proceed']").click();
        // Select today's date dynamically
        page.locator("span.flatpickr-day[aria-current='date']").click();

        // Select the first available time slot dynamically
        page.locator("li.time-slot-box.list-group-item").first().click();

        page.locator("//button[text()='Submit']").nth(1).click();
        
        
    page.locator("//img[@alt='closeIcon Ask us']").first().click();

        
        
        

        Hamburger hamburger = new Hamburger(page);
        hamburger.Mystuff().click();
        hamburger.MyAccount().click();

        page.locator("//a[text()='My Assignment']").click();
        page.locator("//span[text()='Start Assignment']").first().click();

        

        page.locator("//span[text()='Submit Assignment']").click();

        Locator editor = page.locator("div[contenteditable='true']").first();
        editor.click();
        editor.fill("Automated assignment submission with Mailosaur user.");

        page.locator("//span[text()='Save']").click();
        page.waitForTimeout(2000);

        page.locator("//span[text()='Edit Assignment']").click();
        page.waitForTimeout(2000);
        page.locator("//span[text()='Save']").click();

        page.locator("//div[text()='Drafted']").click();

        Locator draftedCard = page.locator(".preferance-card.interest-card");
        draftedCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        Assert.assertTrue(draftedCard.isVisible(), "Draft card not visible");

        page.close();
    }
}
