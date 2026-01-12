package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.FeedbackExpectedTexts;
import com.automation.pages.FeedbackPopupPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.automation.utils.LoginUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeedbackLoginTest extends BaseClass {

    private HomepagePage home;
    private FeedbackPopupPage feedback;
    private LoginUtility login;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP_NAME = "Tester 1";
    private static final String FEEDBACK_TEXT =
            "This is automated feedback after login.";

    // ================= INIT =================
    @BeforeMethod(alwaysRun = true)
    public void init() {

        home     = new HomepagePage(page);
        feedback = new FeedbackPopupPage(page);
        login    = new LoginUtility(page);
        helper   = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    // ================= TEST =================
    @Test
    public void verifyFeedbackViaLogin() {

        try {

            helper.step("LOGIN USING SAVED SIGNUP ACCOUNT");

            // ---------- LOGIN ----------
            login.loginUsingSignupAccount();

            Assert.assertTrue(
                    home.getInternshipsTab().isVisible(),
                    "❌ Login failed — Internships tab not visible"
            );

            // ---------- OPEN INTERNSHIPS ----------
            helper.step("OPEN INTERNSHIPS");
            helper.safeClick(home.getInternshipsTab(), "Internships");

            // ---------- OPEN INTERNSHIP ----------
            helper.step("OPEN INTERNSHIP CARD");
            Locator card = home.getInternshipCard(INTERNSHIP_NAME);
            helper.waitForVisible(card, "Internship Card");
            helper.scrollAndClick(card, "Open Internship");

            // ---------- FEEDBACK CONTAINER ----------
            helper.step("VERIFY FEEDBACK CONTAINER");
            helper.waitForVisible(
                    feedback.getInlineFeedbackContainer(),
                    "Inline Feedback Container"
            );

            // ---------- BRAND NAME VALIDATION ----------
            helper.step("VALIDATE FEEDBACK BRAND NAME");

            String companyNameFromDescription =
                    home.getCompanyNameOnDescription().innerText().trim();

            helper.assertEquals(
                    feedback.getFeedbackBrandName().innerText().trim(),
                    companyNameFromDescription,
                    "Feedback brand name"
            );

            // ---------- FEEDBACK TEXTAREA ----------
            helper.step("SUBMIT FEEDBACK");

            helper.assertEquals(
                    feedback.getFeedbackTextarea()
                            .getAttribute("placeholder"),
                    FeedbackExpectedTexts.FEEDBACK_PLACEHOLDER,
                    "Feedback placeholder"
            );

            helper.safeFill(
                    feedback.getFeedbackTextarea(),
                    FEEDBACK_TEXT,
                    "Feedback Text"
            );

            helper.safeClick(
                    feedback.getFeedbackSubmitBtn(),
                    "Submit Feedback"
            );

         
            // ---------- THANK YOU ----------
            helper.step("VALIDATE THANK YOU POPUP");

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

            helper.safeClick(
                    feedback.getThankYouCloseBtn(),
                    "Close Thank You Popup"
            );

            helper.pass("🎉 FEEDBACK VIA LOGIN FLOW PASSED");

        } catch (Exception e) {
            helper.fail("❌ FEEDBACK VIA LOGIN FLOW FAILED");
            throw e;
        }
    }
}

