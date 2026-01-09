package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.FeedbackExpectedTexts;
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
    private static final String INTERNSHIP_NAME = "Tester 1";
    private static final String FEEDBACK_TEXT =
            "This is feedback after mobile signup.";

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        home     = new HomepagePage(page);
        signup   = new SignUpPage(page);
        feedback = new FeedbackPopupPage(page);
        helper   = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    @Test
    public void verifyFeedbackViaMobileSignup_FullFlow_WithValidations() {

        helper.step("START – Feedback via Mobile Signup");

        // =====================================================
        // TEST DATA
        // =====================================================
        String name   = helper.generateRandomName();
        String mobile = helper.generateRandomPhone();
        String email  = helper.generateEmailFromName(name);

        // =====================================================
        // MOBILE SIGNUP
        // =====================================================
        helper.step("Mobile Signup");

        helper.safeClick(signup.getInitialSignupButton(), "Sign Up");
        helper.safeFill(signup.getEmailOrPhoneInput(), mobile, "Mobile");
        helper.safeClick(signup.getSendVerificationCodeButton(), "Send OTP");
        helper.safeFill(signup.getOtpInput(), OTP, "OTP");
        helper.safeFill(signup.getPasswordInput(), PASSWORD, "Password");
        helper.safeClick(signup.getFinalSignupButton(), "Complete Signup");

        Assert.assertTrue(
                home.getInternshipsTab().isVisible(),
                "Signup failed"
        );

        // =====================================================
        // OPEN INTERNSHIP
        // =====================================================
        helper.step("Open Internship");

        helper.safeClick(home.getInternshipsTab(), "Internships");
        Locator card = home.getInternshipCard(INTERNSHIP_NAME);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // =====================================================
        // CAPTURE COMPANY NAME FROM DESCRIPTION PAGE
        // =====================================================
        helper.step("Capture Company Name from Internship Description");

        String companyNameFromDescription =
                home.getCompanyNameOnDescription()
                        .innerText()
                        .trim();

        helper.log("Captured Company Name = " + companyNameFromDescription);

        helper.assertTrue(
                !companyNameFromDescription.isEmpty(),
                "Company name captured from description"
        );

        // =====================================================
        // INLINE FEEDBACK VALIDATION
        // =====================================================
        helper.step("Inline Feedback Validation");

        helper.waitForVisible(
                feedback.getInlineFeedbackContainer(),
                "Feedback Container"
        );

        // =====================================================
        // FEEDBACK BRAND NAME VALIDATION
        // =====================================================
        helper.step("Validate Feedback Brand Name");

        helper.waitForVisible(
                feedback.getFeedbackBrandName(),
                "Feedback Brand Name"
        );

        helper.assertEquals(
                feedback.getFeedbackBrandName().innerText().trim(),
                companyNameFromDescription,
                "Feedback brand name matches internship company"
        );

        Assert.assertEquals(
                feedback.getFeedbackTextarea().getAttribute("placeholder"),
                FeedbackExpectedTexts.FEEDBACK_PLACEHOLDER,
                "Feedback placeholder"
        );

        Assert.assertEquals(
                feedback.getFeedbackSubmitBtn().innerText().trim(),
                FeedbackExpectedTexts.FEEDBACK_SUBMIT_BUTTON,
                "Feedback submit button text"
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
        // LEFT PANEL VALIDATION
        // =====================================================
        helper.step("Left Panel Validation");

        Locator headers = feedback.getLeftPanelHeaders();
        Locator descs   = feedback.getLeftPanelDescriptions();

        helper.waitForVisible(headers.first(), "Left Panel Header");

        Assert.assertEquals(
                headers.nth(0).innerText().trim(),
                FeedbackExpectedTexts.EMPOWER_HEADER
        );

        Assert.assertEquals(
                descs.nth(0).innerText().trim(),
                FeedbackExpectedTexts.EMPOWER_DESCRIPTION
        );

        Assert.assertEquals(
                headers.nth(1).innerText().trim(),
                FeedbackExpectedTexts.HONEST_HEADER
        );

        Assert.assertEquals(
                descs.nth(1).innerText().trim(),
                FeedbackExpectedTexts.HONEST_DESCRIPTION
        );

        // =====================================================
        // DETAILS FORM VALIDATION
        // =====================================================
        helper.step("Details Form Validation");

        Assert.assertEquals(
                feedback.getDetailsHeader().innerText().trim(),
                FeedbackExpectedTexts.DETAILS_HEADER
        );

        Assert.assertEquals(
                feedback.getNameField().getAttribute("placeholder"),
                FeedbackExpectedTexts.NAME_PLACEHOLDER
        );

        Assert.assertEquals(
                feedback.getMobileField().getAttribute("placeholder"),
                FeedbackExpectedTexts.MOBILE_PLACEHOLDER
        );

        Assert.assertEquals(
                feedback.getEmailField().getAttribute("placeholder"),
                FeedbackExpectedTexts.EMAIL_PLACEHOLDER
        );

        helper.safeFill(feedback.getNameField(), name, "Name");

        // Mobile auto-filled & disabled
        Assert.assertFalse(
                feedback.getMobileField().isEnabled(),
                "Mobile should be disabled"
        );

        Assert.assertEquals(
                feedback.getMobileField().getAttribute("value"),
                mobile,
                "Auto-filled mobile"
        );

        // Email editable for mobile flow
        helper.safeFill(feedback.getEmailField(), email, "Email");

        helper.safeClick(
                feedback.getDetailsSubmitBtn(),
                "Submit Details"
        );

        // =====================================================
        // THANK YOU VALIDATION (NO OTP HERE)
        // =====================================================
        helper.step("Thank You Validation");

        helper.waitForVisible(
                feedback.getThankYouPopup(),
                "Thank You Popup"
        );

        Assert.assertEquals(
                feedback.getThankYouHeader().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_HEADER
        );

        Assert.assertEquals(
                feedback.getThankYouMessage().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_MESSAGE
        );

        // Mobile flow → footer MUST be visible
        Assert.assertEquals(
                feedback.getThankYouFooter().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_FOOTER_NON_EMAIL,
                "Footer should be visible for mobile flow"
        );

        helper.safeClick(
                feedback.getThankYouCloseBtn(),
                "Close Thank You"
        );

        helper.log("🎉 FEEDBACK MOBILE FLOW – ALL DATA VALIDATIONS PASSED");
    }
}
