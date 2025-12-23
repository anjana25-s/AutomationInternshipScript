package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeedbackEmailTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private FeedbackPopupPage feedback;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String PASSWORD = "Test@123";
    private static final String INTERNSHIP = "Finance- Job role";
    private static final String FEEDBACK_TEXT =
            "This is automated feedback for validation.";

    @BeforeMethod(alwaysRun = true)
    public void openBaseUrl() {

        home     = new HomepagePage(page);
        signup   = new SignUpPage(page);
        feedback = new FeedbackPopupPage(page);
        helper   = new HelperUtility(page);

        helper.log("[Step 1] Navigating to Promilo...");
        page.navigate(BASE_URL);
        page.waitForLoadState();

        Assert.assertTrue(
                page.url().contains("promilo"),
                "❌ Incorrect URL loaded!"
        );

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }
    }

    @Test
    public void verifyFeedbackViaEmailSignup() {

        // ---------- TEST DATA ----------
        String name   = helper.generateRandomName();
        String email  = helper.generateEmailFromName(name);
        String mobile = helper.generateRandomPhone();

        helper.log("Generated Name = " + name);
        helper.log("Generated Email = " + email);
        helper.log("Generated Phone = " + mobile);

        // ---------- SIGNUP ----------
        helper.safeClick(signup.getInitialSignupButton(), "Click SignUp");
        helper.safeFill(signup.getEmailOrPhoneInput(), email, "Enter Email");
        helper.safeClick(signup.getSendVerificationCodeButton(),
                "Send Verification Code");

        helper.safeFill(signup.getOtpInput(), "9999", "Enter OTP");
        helper.safeFill(signup.getPasswordInput(),
                PASSWORD, "Enter Password");
        helper.safeClick(signup.getFinalSignupButton(),
                "Complete Signup");

        // ---------- OPEN INTERNSHIP ----------
        helper.safeClick(home.getInternshipsTab(),
                "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // ---------- FEEDBACK POPUP ----------
        Locator feedbackModal =
                page.locator("div.Job-Feedback-modal");

        helper.waitForVisible(feedbackModal, "Feedback Modal");
        Assert.assertTrue(feedbackModal.isVisible(),
                "❌ Feedback modal not visible!");

        // ---------- SUBMIT FEEDBACK ----------
        helper.safeFill(feedback.getFeedbackTextarea(),
                FEEDBACK_TEXT, "Feedback Input");
        helper.safeClick(feedback.getFeedbackSubmitBtn(),
                "Submit Feedback");

        // ---------- DETAILS FORM ----------
        helper.safeFill(feedback.getNameField(),
                name, "Name");
        helper.safeFill(feedback.getMobileField(),
                mobile, "Mobile");

        if (feedback.getEmailField().isEnabled()) {
            helper.safeFill(feedback.getEmailField(),
                    email, "Email");
        }

        helper.safeClick(feedback.getPopupSubmitBtn(),
                "Submit Details");

        // ---------- OTP ----------
        Locator otpModal =
                page.locator("div[role='dialog']");
        helper.waitForVisible(otpModal, "OTP Modal");

        String OTP = "9999";
        for (int i = 1; i <= 4; i++) {
            helper.safeFill(
                    feedback.getOtpInput(i),
                    OTP.substring(i - 1, i),
                    "OTP Digit " + i
            );
        }

        Assert.assertFalse(
                feedback.getOtpVerifyBtn().isDisabled(),
                "❌ Verify button disabled after OTP"
        );

        helper.safeClick(feedback.getOtpVerifyBtn(),
                "Verify OTP");

        // ---------- THANK YOU ----------
        helper.waitForVisible(
                feedback.getThankYouPopup(),
                "Thank You Popup"
        );

        Assert.assertTrue(
                feedback.getThankYouPopup().isVisible(),
                "❌ Thank You popup not visible!"
        );

        helper.safeClick(
                feedback.getThankYouCloseBtn(),
                "Close Thank You Popup"
        );

        helper.log(
                "🎉 FEEDBACK FLOW VIA EMAIL SIGNUP PASSED"
        );
    }
}


