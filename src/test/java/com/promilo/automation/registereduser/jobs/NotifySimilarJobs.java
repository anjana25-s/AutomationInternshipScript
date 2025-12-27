package com.promilo.automation.registereduser.jobs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.job.pageobjects.JobsMyInterestPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class NotifySimilarJobs extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signup = new SignupWithMailosaurUI();
        String[] creds = signup.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
    }

    @Test
    public void applyForJobAsRegisteredUser() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("Signup credentials missing!");
            return;
        }

        String inputvalue = registeredEmail;
        String password = registeredPassword;
        String otp = "9999";  
        String mailphone = registeredEmail;

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Notify Similar Jobs | SingleRun");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
        try { popup.getPopup().click(); } catch (Exception ignored) {}
        popup.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        test.info("Logged in as: " + inputvalue);

        JobListingPage home = new JobListingPage(page);
        FormComponents askUs = new FormComponents(page);
        com.promilo.automation.job.pageobjects.NotifySimilarJobsPageObjects objects = new com.promilo.automation.job.pageobjects.NotifySimilarJobsPageObjects(page);

        home.homepageJobs().click();
        Thread.sleep(5000);

        home.searchJob().fill("December Campaign Automation");
        page.keyboard().press("Enter");
        home.notifySimilarJobs().click();

        // ======================================================
        // Assertions using object class
        // ======================================================
        assertTrue(objects.headerText().textContent().trim()
                .contains("Get similar job alerts? Connect with Top companies to crack your dream jobs."));
        assertEquals(objects.registerDescriptionText().textContent().trim(),
                "Why Register for Personalized Job Recommendations?Discover Tailored Opportunities: Get job recommendations that align with your skills and career goals.Real-Time Job Alerts: Be notified instantly about roles similar to your profile and interests.Direct Connection to Recruiters: Increase your chances of receiving callbacks for interviews.Exclusive Career Insights: Unlock resources to enhance your application, interview skills, and employability.Privacy Matters: Enjoy a secure platform with no unsolicited communication or spam.Discover Tailored Opportunities: Get job recommendations that align with your skills and career goals.Real-Time Job Alerts: Be notified instantly about roles similar to your profile and interests.Direct Connection to Recruiters: Increase your chances of receiving callbacks for interviews.Exclusive Career Insights: Unlock resources to enhance your application, interview skills, and employability.Privacy Matters: Enjoy a secure platform with no unsolicited communication or spam.PreviousNext");
        assertEquals(objects.whatsappNotificationText().textContent().trim(),
                "Enable updates & important information on Whatsapp");
        assertEquals(objects.agreeText().textContent().trim(),
                "By proceeding ahead you expressly agree to the Promilo");

        // Fill form
        askUs.userNameField().fill("karthik");
        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        askUs.mobileField().fill(randomPhone);
        home.sendSimilarJobs().click();

        // OTP validation
        assertEquals(objects.otpPageDescription().textContent().trim(),
                "Accelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextAccelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.");

        for (int i = 0; i < 4; i++) {
            objects.otpDigit(i + 1).waitFor(new Locator.WaitForOptions().setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));
            objects.otpDigit(i + 1).fill(String.valueOf(otp.charAt(i)));
        }

        assertEquals(objects.otpThanksText().textContent().trim(), "Thanks for giving your Information!");
        assertEquals(objects.otpVerificationHeader().textContent().trim(), "OTP Verification");
        assertTrue(objects.otpSentText().textContent().trim()
                .contains("Enter the 4-digit verification code we just sent you to"));
        assertTrue(objects.otpCantFindText().textContent().trim().contains("Still can’t find the OTP"));
        objects.verifyAndProceedButton().click();

        objects.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(5000));
        Assert.assertTrue(objects.thankYouPopup().isVisible());
        assertEquals(objects.thankYouMessageText().textContent().trim(),
                "Thank You!Thank you for requesting  a similar job alert. Check your email, notifications, and WhatsApp for the latest recommendations.Build, update, or upload your resume to get an interview approved and help the recruiter get to know you better.Build ResumeUpload Resume");

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/NotifySimilarJobs_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath);

        String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(screenshotPath)));
        test.addScreenCaptureFromBase64String(base64);

        page.close();
        extent.flush();
    }
}
