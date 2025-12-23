package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeedbackMobileTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private FeedbackPopupPage feedback;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String OTP = "9999";
    private static final String PASSWORD = "Test@123";
    private static final String INTERNSHIP = "Finance- Job role";
    private static final String FEEDBACK_TEXT =
            "This is feedback after mobile signup.";

    @BeforeMethod(alwaysRun = true)
    public void openBase() {

        home     = new HomepagePage(page);
        signup   = new SignUpPage(page);
        feedback = new FeedbackPopupPage(page);
        helper   = new HelperUtility(page);

        helper.log("[Step 1] Navigating to Homepage…");
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }
    }

    @Test
    public void verifyFeedbackPopupViaMobileSignup() {

        // ---------- TEST DATA ----------
        String name   = helper.generateRandomName();
        String mobile = helper.generateRandomPhone();
        String email  = helper.generateEmailFromName(name);

        helper.log("Generated Name = " + name);
        helper.log("Generated Mobile = " + mobile);
        helper.log("Generated Email = " + email);

        // ---------- SIGNUP (MOBILE) ----------
        helper.safeClick(signup.getInitialSignupButton(), "Click SignUp");
        helper.safeFill(signup.getEmailOrPhoneInput(), mobile, "Enter Mobile");
        helper.safeClick(signup.getSendVerificationCodeButton(),
                "Send Verification Code");

        helper.safeFill(signup.getOtpInput(), OTP, "Enter OTP");
        helper.safeFill(signup.getPasswordInput(),
                PASSWORD, "Enter Password");
        helper.safeClick(signup.getFinalSignupButton(),
                "Complete Signup");

        // ---------- ASSERT LOGIN ----------
        Assert.assertTrue(
                home.getInternshipsTab().isVisible(),
                "❌ Signup failed — Internships tab not visible"
        );
        helper.log("✔ Signup successful via Mobile");

        // ---------- OPEN INTERNSHIP ----------
        helper.safeClick(home.getInternshipsTab(),
                "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // ---------- FEEDBACK MODAL ----------
        Locator feedbackModal =
                page.locator("div.Job-Feedback-modal");

        helper.waitForVisible(feedbackModal, "Feedback Modal");
        Assert.assertTrue(feedbackModal.isVisible(),
                "❌ Feedback modal not visible");

        helper.log("✔ Feedback Modal Visible");

        // ---------- SUBMIT FEEDBACK ----------
        helper.safeFill(feedback.getFeedbackTextarea(),
                FEEDBACK_TEXT, "Enter Feedback");
        helper.safeClick(feedback.getFeedbackSubmitBtn(),
                "Submit Feedback");

        // ---------- USER DETAILS ----------
        helper.safeFill(feedback.getNameField(),
                name, "Enter Name");

        if (feedback.getMobileField().isEnabled()) {
            helper.safeFill(feedback.getMobileField(),
                    mobile, "Enter Mobile");
        } else {
            helper.log("✔ Mobile auto-filled");
        }

        helper.safeFill(feedback.getEmailField(),
                email, "Enter Email");

        helper.safeClick(feedback.getPopupSubmitBtn(),
                "Submit User Details");

        // ---------- THANK YOU ----------
        helper.waitForVisible(feedback.getThankYouPopup(),
                "Thank You Popup");

        Assert.assertTrue(
                feedback.getThankYouPopup().isVisible(),
                "❌ Thank You popup not visible"
        );

        helper.safeClick(feedback.getThankYouCloseBtn(),
                "Close Thank You Popup");

        helper.log("🎉 FEEDBACK FLOW VIA MOBILE PASSED!");
    }
}



