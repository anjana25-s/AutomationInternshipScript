package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.FeedbackExpectedTexts;
import com.automation.pages.FeedbackPopupPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeedbackNoSignupTest extends BaseClass {

    private HomepagePage home;
    private FeedbackPopupPage feedback;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP_NAME = "Tester 1";
    private static final String FEEDBACK_TEXT =
            "Automated feedback for no-signup flow.";
    private static final String OTP = "9999";

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        home     = new HomepagePage(page);
        feedback = new FeedbackPopupPage(page);
        helper   = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    @Test
    public void verifyFeedbackWithoutSignup_FullFlow_WithValidations() {

        helper.step("START – Feedback without Signup");

        // =====================================================
        // OPEN INTERNSHIP
        // =====================================================
        helper.step("Open Internship");

        helper.safeClick(home.getInternshipsTab(), "Internships");

        Locator card = home.getInternshipCard(INTERNSHIP_NAME);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // =====================================================
        // INLINE FEEDBACK VALIDATION
        // =====================================================
        helper.step("Inline Feedback Validation");

        helper.waitForVisible(
                feedback.getInlineFeedbackContainer(),
                "Feedback Container"
        );

        Assert.assertEquals(
                feedback.getFeedbackTextarea().getAttribute("placeholder"),
                FeedbackExpectedTexts.FEEDBACK_PLACEHOLDER,
                "Feedback placeholder"
        );

        Assert.assertEquals(
                feedback.getFeedbackSubmitBtn().innerText().trim(),
                FeedbackExpectedTexts.FEEDBACK_SUBMIT_BUTTON,
                "Feedback submit button"
        );

        helper.safeFill(
                feedback.getFeedbackTextarea(),
                FEEDBACK_TEXT,
                "Enter Feedback"
        );

        helper.safeClick(
                feedback.getFeedbackSubmitBtn(),
                "Submit Feedback"
        );

        // =====================================================
        // DETAILS FORM (MANDATORY FOR NO SIGNUP)
        // =====================================================
        helper.step("Details Form Validation");

        helper.waitForVisible(
                feedback.getDetailsHeader(),
                "Details Form Header"
        );

        Assert.assertEquals(
                feedback.getDetailsHeader().innerText().trim(),
                FeedbackExpectedTexts.DETAILS_HEADER,
                "Details header"
        );

        // =====================================================
        // TEST DATA
        // =====================================================
        String name   = helper.generateRandomName();
        String mobile = helper.generateRandomPhone();
        String email  = helper.generateEmailFromName(name);

        helper.safeFill(feedback.getNameField(), name, "Name");

        Assert.assertTrue(
                feedback.getMobileField().isEnabled(),
                "Mobile field should be editable"
        );
        helper.safeFill(feedback.getMobileField(), mobile, "Mobile");

        Assert.assertTrue(
                feedback.getEmailField().isEnabled(),
                "Email field should be editable"
        );
        helper.safeFill(feedback.getEmailField(), email, "Email");

        helper.safeClick(
                feedback.getDetailsSubmitBtn(),
                "Submit Details"
        );

        // =====================================================
        // OTP VALIDATION (MANDATORY)
        // =====================================================
        helper.step("OTP Validation");

        for (int i = 1; i <= 4; i++) {
            feedback.getOtpInput(i)
                    .fill(String.valueOf(OTP.charAt(i - 1)));
        }

        Assert.assertFalse(
                feedback.getOtpVerifyBtn().isDisabled(),
                "Verify OTP button enabled"
        );

        helper.safeClick(
                feedback.getOtpVerifyBtn(),
                "Verify OTP"
        );

        // =====================================================
        // THANK YOU VALIDATION
        // =====================================================
        helper.step("Thank You Validation");

        helper.waitForVisible(
                feedback.getThankYouPopup(),
                "Thank You Popup"
        );

        Assert.assertEquals(
                feedback.getThankYouHeader().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_HEADER,
                "Thank You header"
        );

        Assert.assertEquals(
                feedback.getThankYouMessage().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_MESSAGE,
                "Thank You message"
        );

        Assert.assertEquals(
                feedback.getThankYouFooter().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_FOOTER_NON_EMAIL,
                "Thank You footer"
        );

        helper.safeClick(
                feedback.getThankYouCloseBtn(),
                "Close Thank You"
        );

        helper.log("🎉 FEEDBACK NO-SIGNUP FLOW – ALL DATA VALIDATIONS PASSED");
    }
}



