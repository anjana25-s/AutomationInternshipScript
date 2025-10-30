package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.FeedbackPopupScript;
import com.automation.scripts.HomePageScript;
import com.automation.scripts.SignupPageScript;
import org.testng.Assert;
import org.testng.annotations.*;

public class FeedbackMobileTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePageScript homePageScript;
    private SignupPageScript signupPageScript;
    private FeedbackPopupScript feedbackPopupScript;

    @BeforeClass
    public void setup() {
        // Initialize Playwright and browser
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500));
        page = browser.newPage();

        // Initialize script objects
        homePageScript = new HomePageScript(page);
        signupPageScript = new SignupPageScript(page);
        feedbackPopupScript = new FeedbackPopupScript(page);

        // Navigate to the application
        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 1] Navigated to Promilo");
    }

    @Test
    public void feedbackFlow_MobileSignup() {
        // Dismiss 'Maybe Later' modal if present
        homePageScript.clickMaybeLater();
        System.out.println("[Step 2] Dismissed 'Maybe Later' modal if appeared");

        // Click on Internships tab
        homePageScript.clickInternships();
        System.out.println("[Step 3] Clicked on 'Internships' tab");

        // Click initial Sign Up button
        signupPageScript.clickInitialSignupButton();
        System.out.println("[Step 4] Clicked on 'Sign Up' button");

        // Enter mobile number and request OTP
        signupPageScript.enterEmailOrPhone("9000018583");
        signupPageScript.clickVerificationCode();
        System.out.println("[Step 5] Entered mobile number and requested OTP");

        // Enter OTP and complete signup
        signupPageScript.enterOtp("9999", false);
        signupPageScript.enterPassword("Test@1234");
        signupPageScript.clickFinalSignupButton();
        System.out.println("[Step 6] ✅ Mobile signup completed");

        // Select internship card
        homePageScript.selectInternship("Finance- Job role");
        System.out.println("[Step 7] Selected internship card: Finance- Job role");

        // Enter feedback
        feedbackPopupScript.getActions().enterFeedback("This is feedback after mobile signup");
        feedbackPopupScript.getActions().submitFeedback();
        System.out.println("[Step 8] Feedback submitted");

        // Fill user details and submit
        feedbackPopupScript.getActions().fillUserDetails(
                "Meghana",
                "",
                "testmeghana234@yopmail.com"
        );
        feedbackPopupScript.getActions().clickUserDetailsSubmit();
        System.out.println("[Step 9] User details submitted");

        // Verify Thank You popup is displayed
        Assert.assertTrue(feedbackPopupScript.getActions().isThankYouDisplayed(), "Thank You popup not displayed!");
        System.out.println("[Step 10] ✅ Feedback flow executed successfully for mobile signup");
    }

    @AfterClass
    public void teardown() {
        // Close browser and Playwright
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("[Teardown] Browser and Playwright closed");
    }
}

