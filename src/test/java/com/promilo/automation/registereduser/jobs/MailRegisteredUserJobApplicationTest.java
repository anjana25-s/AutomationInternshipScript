package com.promilo.automation.registereduser.jobs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.job.pageobjects.JobsMyInterestPage;
import com.promilo.automation.pageobjects.myresume.AddEmploymentDetails;
import com.promilo.automation.pageobjects.myresume.BasicDetailsPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MailRegisteredUserJobApplicationTest extends BaseClass {

    private static final Logger logger = LogManager.getLogger(MailRegisteredUserJobApplicationTest.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            if (excel.getCellData(i, 0) == null || excel.getCellData(i, 0).isEmpty())
                break;
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
            data[i - 1][7] = i; // RowIndex
        }
        return data;
    }

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test(alwaysRun = true)
    public void applyForJobAsRegisteredUserUsingMailosaur() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📩 Apply for Job as Registered User (Mailosaur) | Single Test");

        // ✅ Initialize browser page at the start
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        com.promilo.automation.job.pageobjects.MailJobApplyPageObjects objects = new com.promilo.automation.job.pageobjects.MailJobApplyPageObjects(page);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click();
        } catch (Exception ignored) {
        }
        mayBeLaterPopUp.clickLoginButton();

        // ✅ Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);

        // ✅ Apply for Job
        String name = "Karthik Test";
        String otp = "9999";

        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();

        // replaced: page.locator("//input[@placeholder='Search Jobs']")
        objects.searchJobsInput().fill("software");
        objects.searchJobsInput().click();
        page.keyboard().press("Enter");

        jobPage.applyNow().first().click();
        Thread.sleep(2000);

        // replaced: headerText locator
        String headerText = objects.featureContentHeader().textContent().trim();
        String expectedHeaderText = "Get an interview schedule from December Campaign Automation!";
        assertTrue(headerText.contains(expectedHeaderText));

        // replaced: registerText locator
        String registerText = objects.registerText().textContent().trim();
        String expectedRegisterText = "Why register with us?Take Charge of Your Career: Connect with recruiters and apply for roles that match your aspirations.Stay Updated: You can receive real-time notifications about job openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt Interview schedule opportunities.Personalized Opportunities: Tailored job alerts ensure you get all the suitable openings.Exclusive Resources: Unlock premium tools and tips for interviewing and securing your dream job.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.Take Charge of Your Career: Connect with recruiters and apply for roles that match your aspirations.Stay Updated: You can receive real-time notifications about job openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt Interview schedule opportunities.Personalized Opportunities: Tailored job alerts ensure you get all the suitable openings.Exclusive Resources: Unlock premium tools and tips for interviewing and securing your dream job.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.PreviousNext";
        assertEquals(registerText, expectedRegisterText);

        // replaced: whatsapp label
        String whatsappNotificationText = objects.whatsappNotificationLabel().textContent().trim();
        String expectedWhatsappNotificationText = "Enable updates & important information on Whatsapp";
        assertEquals(whatsappNotificationText, expectedWhatsappNotificationText);

        // replaced: agree text
        String agreeText = objects.agreeText().textContent().trim();
        String expectedAgreeText = "By proceeding ahead you expressly agree to the Promilo";
        assertEquals(agreeText, expectedAgreeText);

        jobPage.applyNameField().fill(name);

        int randomSuffix = (int) (Math.random() * 100000);
        String phoneNumber = "90000" + String.format("%05d", randomSuffix);
        jobPage.applyNowMobileTextField().fill(phoneNumber);

        jobPage.selectIndustryDropdown().click();
        Thread.sleep(1000);

        // replaced: healthcare option locator (kept .nth(0) logic)
        Locator option = objects.healthcareOption();
        if (option.isVisible())
            option.click();

        test.info("Opened Industry dropdown");
        Thread.sleep(1000);

        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");
        // replaced: options locator
        Locator options = objects.industryOptions();
        for (String industry : industries) {
            for (int i = 0; i < options.count(); i++) {
                String optionText = options.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    break;
                }
            }
        }

        // replaced: ask-us-popup userName input click
        objects.askUsPopupUserNameInput().click();

        // replaced: applyBtn locator
        Locator applyBtn = objects.applyBtn();
        applyBtn.scrollIntoViewIfNeeded();
        applyBtn.click();
        Thread.sleep(2000);

        // replaced: otpPageDescription locator
        String otpPageDescription = objects.otpPageDescription().textContent().trim();
        String expectedOtpPageDescription = "Accelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextAccelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.";
        assertEquals(otpPageDescription, expectedOtpPageDescription);

        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
        }

        // ✅ Enter OTP (replaced otp fields)
        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = objects.otpDigitField(i + 1);
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            int attempts = 0;
            boolean filled = false;
            while (!filled && attempts < 3) {
                attempts++;
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
            }
            test.info("Entered OTP digit: " + otpChar);
        }

        // replaced: otp texts
        String text3 = objects.otpThanksForInformation().textContent().trim();
        String expectedResult3 = "Thanks for giving your Information!";
        assertEquals(text3, expectedResult3);

        String text4 = objects.otpVerificationHeader().textContent().trim();
        String expectedResult4 = "OTP Verification";
        assertEquals(text4, expectedResult4);

        String text5 = objects.otpInstructionText().textContent().trim();
        String expectedResult5 = "Enter the 4-digit verification code we just sent you to";
        assertTrue(text5.contains(expectedResult5));

        String text6 = objects.otpStillCantFind().textContent().trim();
        String expectedResult6 = "Still can’t find the OTP";
        assertTrue(text6.contains(expectedResult6));

        // replaced: Verify & Proceed click by returning original locator method
        objects.verifyAndProceedButtonByText().click();

        page.waitForTimeout(2000);
        String nextButtonPageText = objects.nextButtonPageText().textContent().trim();
        String expectedNextButtonPageText = "Video Interview Enjoy a Video Interview schedule with your applied company to get selected. Select your preferred language and schedule an Interview meeting with a recruiter. This will ensure a smooth and personalized connection, making it easy for you and the recruiter to communicate effectively. ";
        assertEquals(nextButtonPageText, expectedNextButtonPageText);

        // replaced: date & time element selection
        Locator dateElement = objects.dateElement();
        dateElement.click();
        String datePicked = dateElement.innerText();

        // Extract only the first number (date) before any whitespace or newline
        String dateOnly = datePicked.split("\\s+")[0].trim();

        Locator timeElement = objects.timeElement();
        timeElement.click();
        String timePicked = timeElement.innerText();

        System.out.println("Selected Date: " + datePicked);
        System.out.println("Selected Time: " + timePicked);

        System.out.println("Date Only: " + dateOnly);

        // Store in BaseClass
        BaseClass.selectedDate = dateOnly;
        BaseClass.selectedTime = timePicked;

        System.out.println("Selected Date Stored: " + BaseClass.selectedDate);
        System.out.println("Selected Time Stored: " + BaseClass.selectedTime);

        // replaced: Submit button nth(1)
        objects.submitButtonNth1().click();

        // replaced: thank you pop up message
        String thankYouPopUpMessage = objects.thankYouContent().textContent().trim();
        String expectedThankYouPopUpMessage = "Thank You!Thank you for requesting an interview from December Campaign Automation. Check your email, notifications, and WhatsApp for details on exclusive access.Help Recruiter to know you better by building, Updating or Uploading your resume to get an interview approved.Build ResumeUpload Resume";
        assertEquals(thankYouPopUpMessage, expectedThankYouPopUpMessage);

        // ======================================================================
        // VALIDATE "JOBS MY INTEREST" CARD
        // ======================================================================
        JobsMyInterestPage myintrest = new JobsMyInterestPage(page);
        page.getByRole(AriaRole.DIALOG).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest")).click();

        String expectedJobRole = "Software Tester";
        String expectedBrandName = "December Campaign Automation";
        String expectedLocation = "Bengaluru/Bangalore";
        String expectedServiceName = "Online Interview";

        // Job Role
        String actualJobRole = myintrest.videoCallRole().innerText().trim();
        Assert.assertEquals(actualJobRole, expectedJobRole);

        // Brand Name
        String actualBrandName = myintrest.videoCallBrandName().innerText().trim();
        Assert.assertEquals(actualBrandName, expectedBrandName);

        // Service Name
        String actualServiceName = myintrest.videoCallServiceName().innerText().trim();
        Assert.assertEquals(actualServiceName, expectedServiceName);

        // Meeting Date
        String meetingDate = myintrest.meetingDate().first().textContent().trim();
        Pattern p = Pattern.compile("(\\d{1,2})");
        Matcher m = p.matcher(meetingDate);
        String displayedDayStr = "";
        if (m.find())
            displayedDayStr = m.group(1);
        else
            Assert.fail("❌ Could not extract the day from Meeting Date: " + meetingDate);
        int displayedDay = Integer.parseInt(displayedDayStr);
        int storedDay = Integer.parseInt(BaseClass.selectedDate.trim());
        Assert.assertEquals(displayedDay, storedDay, "❌ Meeting Date mismatch! Stored Date: " + storedDay + " | Displayed Day: " + displayedDay);

        // Meeting Time
        String meetingTime = myintrest.meetingTime().first().textContent().trim();
        Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2}\\s?[AP]M)");
        Matcher timeMatcher = timePattern.matcher(meetingTime);

        String displayedTime = "";
        if (timeMatcher.find()) {
            displayedTime = timeMatcher.group(1).replaceAll("\\s+", " ").trim();
        } else {
            Assert.fail("❌ Could not extract time from Meeting Time: " + meetingTime);
        }

        // Normalize both times (remove leading zero if present)
        String storedTime = BaseClass.selectedTime.replaceAll("\\s+", " ").trim();
        storedTime = storedTime.replaceFirst("^0", ""); // ← removes leading 0
        displayedTime = displayedTime.replaceFirst("^0", ""); // ← removes leading 0 (safe)

        // Final Assertion
        Assert.assertEquals(displayedTime, storedTime,
                "❌ Meeting Time mismatch! Stored Time: " + storedTime + " | Displayed: " + displayedTime);

        // Location
        String actualLocation = myintrest.videoCallLocation().textContent().trim();
        assertEquals(actualLocation, expectedLocation);

        // ✅ Screenshot capture
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/MailosaurJobApply.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "Job Application Attempt");

        byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
        test.addScreenCaptureFromBase64String(Base64.getEncoder().encodeToString(screenshotBytes), "📸 Screenshot (Base64)");

        page.close();
        test.info("🛑 Browser closed for Mailosaur test");
        extent.flush();
    }
}
