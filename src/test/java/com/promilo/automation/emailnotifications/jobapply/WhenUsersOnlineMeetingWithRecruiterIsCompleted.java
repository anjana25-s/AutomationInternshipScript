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
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserMymeetingPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class WhenUsersOnlineMeetingWithRecruiterIsCompleted extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Online meeting completion & feedback flow validation");

    private static final Logger logger = LogManager.getLogger(WhenUsersOnlineMeetingWithRecruiterIsCompleted.class);

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
            data[i - 1][2] = excel.getCellData(i, 4); // InputValue
            data[i - 1][3] = excel.getCellData(i, 6); // Password
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

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone, test);

        test.pass("✅ Job application & meeting flow completed successfully | " + testCaseId);
        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name,
            String otp, String mailphone, ExtentTest test) throws Exception {

        // ---------- Candidate Flow ----------
        LandingPage landingPage = new LandingPage(page);
        try {
            landingPage.getPopup().click();
        } catch (Exception ignored) {}

        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();

        page.locator("//input[@placeholder='Search Jobs']").fill("operation ex");
        page.keyboard().press("Enter");

        page.locator("//button[text()='Apply Now']").first().click();
        jobPage.applyNameField().fill("karthik");

        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty())
                ? mailphone
                : ("90000" + String.format("%05d", random.nextInt(100000)));

        jobPage.applyNowMobileTextField().fill(mobileToUse);
        jobPage.selectIndustryDropdown().click();

        List<String> industries = Arrays.asList(
                "Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");

        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            for (int i = 0; i < options.count(); i++) {
                if (options.nth(i).innerText().trim().equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    break;
                }
            }
        }

        jobPage.applyNameField().click();
        page.waitForTimeout(1000);

        page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]").click();

        // OTP Handling
        for (int i = 0; i < 4; i++) {
            String digit = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
            otpField.fill(digit);
        }

        page.locator("//button[text()='Verify & Proceed']").click();
        page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first().click();
        page.locator("//li[@class='time-slot-box list-group-item']").first().click();
        page.locator("(//button[text()='Submit'])[2]").click();

        // ---------- Recruiter Flow ----------
        Page recruiterPage = page.context().newPage();
        recruiterPage.navigate(prop.getProperty("stageUrl"));

        AdvertiserLoginPage advLogin = new AdvertiserLoginPage(recruiterPage);
        advLogin.loginMailField().fill("adv@yopmail.com");
        advLogin.loginPasswordField().fill("devuttan2023");
        advLogin.signInButton().click();

        AdvertiserHomepage home = new AdvertiserHomepage(recruiterPage);
        home.hamburger().click();
        home.myAccount().click();

        AdverstiserMyaccount myAccount = new AdverstiserMyaccount(recruiterPage);
        myAccount.myProspect().click();

        AdvertiserProspects prospects = new AdvertiserProspects(recruiterPage);
        prospects.Jobs().click();
        
        Thread.sleep(3000);

        prospects.ApproveButton().first().click();

        recruiterPage.locator("//button[contains(text(),'Done')]").click();

        // Host meeting
        AdvertiserMymeetingPage meetingPage = new AdvertiserMymeetingPage(recruiterPage);
        home.hamburger().click();

        prospects.Jobs().click();
        
        recruiterPage.locator("//span[text()='My Meeting']").click();

        meetingPage.hostButton().first().click();
        meetingPage.joinMeetingButton().click();

        // ---------- Mailosaur Verification ----------
        Page mailPage = page.context().newPage();
        mailPage.navigate("https://mailosaur.com/app/servers/ofuk8kzb/messages/inbox");
        mailPage.locator("//input[@placeholder='Enter your email address']").fill("karthiku7026@gmail.com");
        mailPage.locator("//button[text()='Continue']").click();
        mailPage.locator("//input[@placeholder='Enter your password']").fill("Karthik@88");
        mailPage.locator("//button[text()='Log in']").click();

        mailPage.locator("//p[contains(text(),'Confirmation of Accepted Meeting Request')]").first().click();
        Page newPage = recruiterPage.waitForPopup(() -> {
            mailPage.locator("//button[contains(text(),'Join')]").click();
        });

        // ---------- Candidate Joins Meeting & Gives Feedback ----------
        newPage.locator("//span[text()='Join Now']").click();
        newPage.locator("//button[text()='Join now']").click();
        newPage.locator("//button[text()='Yes']").click();
        newPage.locator("[name='remark']").fill("Good");

        // Star rating
        Locator stars = newPage.locator("//span[text()='★']");
        for (int i = 0; i < 3; i++) {
            stars.nth(i).click();
        }

        newPage.locator("//button[text()='Submit']").click();
    }
}
