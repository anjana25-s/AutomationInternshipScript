package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.FeedbackPopupScript;
import com.automation.scripts.HomePageScript;
import com.automation.scripts.SignupPageScript;
import org.testng.Assert;
import org.testng.annotations.*;

public class FeedbackEmailTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePageScript homePageScript;
    private SignupPageScript signupPageScript;
    private FeedbackPopupScript feedbackPopupScript;

    @BeforeClass
    public void setup() {
        // 1️⃣ Initialize Playwright and browser
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));
        page = browser.newPage();

        // 2️⃣ Initialize scripts
        homePageScript = new HomePageScript(page);
        signupPageScript = new SignupPageScript(page);
        feedbackPopupScript = new FeedbackPopupScript(page);

        // 3️⃣ Navigate to the site
        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 1] Navigated to Promilo");
    }

    @Test
    public void feedbackFlow_EmailSignup() {
        // 4️⃣ Close 'Maybe Later' modal if visible
        homePageScript.clickMaybeLater();
        System.out.println("[Step 2] Dismissed 'Maybe Later' modal if appeared");

        // 5️⃣ Click on Internships tab
        homePageScript.clickInternships();
        System.out.println("[Step 3] Clicked on 'Internships' tab");

        // 6️⃣ Click Sign Up button
        signupPageScript.clickInitialSignupButton();
        System.out.println("[Step 4] Clicked on 'Sign Up' button");

        // 7️⃣ Enter email and request OTP
        signupPageScript.enterEmailOrPhone("testemail73@yopmail.com");
        signupPageScript.clickVerificationCode();
        System.out.println("[Step 5] Entered email and requested OTP");

        // 8️⃣ Enter OTP and complete signup
        signupPageScript.enterOtp("9999", true);
        signupPageScript.enterPassword("Test@1234");
        signupPageScript.clickFinalSignupButton();
        System.out.println("[Step 6] Completed signup process");

        // 9️⃣ Select internship
        homePageScript.selectInternship("Finance- Job role");
        System.out.println("[Step 7] Selected internship card: Finance- Job role");

        // 🔟 Enter feedback
        feedbackPopupScript.getActions().enterFeedback("This is feedback after email signup");
        feedbackPopupScript.getActions().submitFeedback();
        System.out.println("[Step 8] Feedback submitted");

        // 1️⃣1️⃣ Fill user details if fields are enabled
        feedbackPopupScript.getActions().fillUserDetails("Meghana", "9000010227", "");
        feedbackPopupScript.getActions().clickUserDetailsSubmit();
        System.out.println("[Step 9] User details submitted");

        // 1️⃣2️⃣ Enter OTP
        feedbackPopupScript.getActions().verifyOtp("9999");
        System.out.println("[Step 10] OTP verified");

        // 1️⃣3️⃣ Verify Thank You popup
        Assert.assertTrue(feedbackPopupScript.getActions().isThankYouDisplayed(), "Thank You popup not displayed!");
        System.out.println("[Step 11] ✅ Feedback flow executed successfully for email signup");
    }

    @AfterClass
    public void teardown() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("[Teardown] Browser and Playwright closed");
    }
}


