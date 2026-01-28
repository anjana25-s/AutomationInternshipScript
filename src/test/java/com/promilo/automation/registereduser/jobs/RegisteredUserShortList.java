package com.promilo.automation.registereduser.jobs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.job.pageobjects.JobsMyInterestPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserShortList extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 promilo shortlist functionality with email notification validation");

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];
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
            data[i - 1][0] = excel.getCellData(i, 0);
            data[i - 1][1] = excel.getCellData(i, 1);
            data[i - 1][2] = excel.getCellData(i, 4);
            data[i - 1][3] = excel.getCellData(i, 6);
            data[i - 1][4] = excel.getCellData(i, 7);
            data[i - 1][5] = excel.getCellData(i, 5);
            data[i - 1][6] = excel.getCellData(i, 8);
            data[i - 1][7] = i;
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

        inputvalue = registeredEmail;
        password = registeredPassword;

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password,
            String name, String otp, String mailphone) throws Exception {

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
        } catch (Exception ignored) {}

        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        applyJobDetailsFlow(page, name, otp, mailphone);
    }

    private void applyJobDetailsFlow(Page page, String name, String otp,
                                     String mailphone  )
            throws Exception {
    	
    	com.promilo.automation.job.pageobjects.RegisteredUserShortListPageObjects objects = new com.promilo.automation.job.pageobjects.RegisteredUserShortListPageObjects(page);


        JobListingPage homePage = new JobListingPage(page);

        Thread.sleep(5000);
        homePage.homepageJobs().click();
        page.waitForTimeout(5000);

        homePage.searchJob().fill("December");
        page.keyboard().press("Enter");

        page.waitForTimeout(15000);
        homePage.shortlist().first().click();

        // ======================================================
        // POPUP TEXT VALIDATION
        // ======================================================
        String shortListPopUpDescription = objects.shortListPopUpDescription().textContent().trim();
        String expectedShortListPopUpDescription =
                "Why register with us?Stay Ahead in Your Career: Access the latest job opportunities tailored to your skills, industry trends, and career aspirations.Instant Notifications: Be the first to know when new roles matching your profile are posted on Promilo.Get Trusted Insights: Discover authentic reviews about potential employers and insights shared by peers and professionals.Guaranteed Privacy: Your information is safe with us. We ensure no unsolicited third-party communications.Stay Ahead in Your Career: Access the latest job opportunities tailored to your skills, industry trends, and career aspirations.Instant Notifications: Be the first to know when new roles matching your profile are posted on Promilo.Get Trusted Insights: Discover authentic reviews about potential employers and insights shared by peers and professionals.Guaranteed Privacy: Your information is safe with us. We ensure no unsolicited third-party communications.PreviousNext";

        assertEquals(shortListPopUpDescription, expectedShortListPopUpDescription);

        assertEquals(objects.headerText().textContent().trim(),
                "Kickstart your career—apply for the Software Tester role today!");

        assertEquals(objects.whatsappNotificationText().textContent().trim(),
                "Enable updates & important information on Whatsapp");

        assertEquals(objects.agreeText().textContent().trim(),
                "By proceeding ahead you expressly agree to the Promilo");

        // ======================================================
        // FILL DETAILS
        // ======================================================
        page.waitForTimeout(2000);
        homePage.applyNameField().fill("karthik");

        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty())
                ? mailphone
                : ("90000" + String.format("%05d", random.nextInt(100000)));

        objects.mobileField().fill(mobileToUse);

        page.waitForTimeout(4000);
        homePage.jobShortList().click();

        // ======================================================
        // OTP PAGE TEXT VALIDATION
        // ======================================================
        assertEquals(objects.otpPageDescription().textContent().trim(),
                "Accelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextAccelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.");

        // OTP entry
        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = objects.otpDigit(i + 1);

            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            otpField.click();
            otpField.fill("");
            otpField.fill(otpChar);
        }

        assertEquals(objects.otpThanksText().textContent().trim(),
                "Thanks for giving your Information!");

        assertEquals(objects.otpVerificationHeader().textContent().trim(),
                "OTP Verification");

        assertTrue(objects.otpSentText().textContent().trim()
                .contains("Enter the 4-digit verification code we just sent you to"));

        assertTrue(objects.otpCantFindText().textContent().trim()
                .contains("Still can’t find the OTP"));

        objects.verifyAndProceedButton().click();

        // ======================================================
        // THANK YOU POPUP
        // ======================================================
        assertEquals(objects.thankYouValidationText().textContent().trim(),
                "Thank You!Thank You for Choosing December Campaign Automation! Check your email, notifications, and WhatsApp for details on exclusive access.You can take further steps, such as exploring apply now and get HR call.");

        objects.thankYouPopupVisible().waitFor(
                new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));

        Assert.assertTrue(objects.thankYouPopupVisible().isVisible());

        objects.myPreferenceLink().click();

        // ======================================================
        // MY INTEREST VALIDATION
        // ======================================================
        JobsMyInterestPage myintrest = new JobsMyInterestPage(page);

        Assert.assertEquals(myintrest.jobRole().innerText().trim(), "Software Tester");
        Assert.assertTrue(myintrest.experiance().first().innerText().trim().contains("0-1 Yrs"));
        Assert.assertEquals(myintrest.brandName().innerText().trim(), "December Campaign Automation");
        Assert.assertEquals(myintrest.serviceName().innerText().trim(), "ShortList");
        Assert.assertEquals(myintrest.salary().nth(1).innerText().trim(), "5.6L - 9.9L");
        Assert.assertEquals(myintrest.location().innerText().trim(), "Bengaluru/Bangalore");
    }
}
