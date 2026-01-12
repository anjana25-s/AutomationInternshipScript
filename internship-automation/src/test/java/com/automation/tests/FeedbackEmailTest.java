package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.FeedbackExpectedTexts;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import com.automation.utils.TestAccountStore;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeedbackEmailTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private FeedbackPopupPage feedback;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String PASSWORD = "Test@123";
    private static final String INTERNSHIP_NAME = "Tester 1";
    private static final String FEEDBACK_TEXT =
            "This is automated feedback for validation.";

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
    public void verifyFeedbackViaEmailSignup_FullFlow_WithValidations() {

        helper.step("START – Feedback via Email Signup");

        // =====================================================
        // TEST DATA
        // =====================================================
        String name   = helper.generateRandomName();
        String email  = helper.generateEmailFromName(name);
        String mobile = helper.generateRandomPhone();

        // =====================================================
        // EMAIL SIGNUP
        // =====================================================
        helper.step("Email Signup");

        helper.safeClick(signup.getInitialSignupButton(), "Sign Up");
        helper.safeFill(signup.getEmailOrPhoneInput(), email, "Email");
        helper.safeClick(signup.getSendVerificationCodeButton(), "Send OTP");
        helper.safeFill(signup.getOtpInput(), "9999", "OTP");
        helper.safeFill(signup.getPasswordInput(), PASSWORD, "Password");
        helper.safeClick(signup.getFinalSignupButton(), "Complete Signup");

        Assert.assertTrue(
                home.getInternshipsTab().isVisible(),
                "Signup failed"
           );     
           TestAccountStore.save(email, PASSWORD);
        

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
                feedback.getFeedbackTextarea()
                        .getAttribute("placeholder"),
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
        helper.safeFill(feedback.getMobileField(), mobile, "Mobile");

        Assert.assertFalse(
                feedback.getEmailField().isEnabled(),
                "Email should be disabled"
        );

        helper.safeClick(
                feedback.getDetailsSubmitBtn(),
                "Submit Details"
        );

        // =====================================================
        // OTP VALIDATION
        // =====================================================
        helper.step("OTP Validation");

        Assert.assertEquals(
                feedback.getOtpHeader().innerText().trim(),
                FeedbackExpectedTexts.OTP_HEADER
        );

        for (int i = 1; i <= 4; i++) {
            feedback.getOtpInput(i)
                    .fill("9999".substring(i - 1, i));
        }

        Assert.assertEquals(
                feedback.getOtpVerifyBtn().innerText().trim(),
                FeedbackExpectedTexts.OTP_VERIFY_BUTTON
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
                FeedbackExpectedTexts.THANK_YOU_HEADER
        );

        Assert.assertEquals(
                feedback.getThankYouMessage().innerText().trim(),
                FeedbackExpectedTexts.THANK_YOU_MESSAGE
        );

        // Email flow → footer hidden
        Assert.assertTrue(
                feedback.getThankYouFooter().innerText().trim().isEmpty(),
                "Footer should be empty for email flow"
        );

        helper.safeClick(
                feedback.getThankYouCloseBtn(),
                "Close Thank You"
        );

        helper.log("🎉 FEEDBACK EMAIL FLOW – ALL DATA VALIDATIONS PASSED");
    }
}
