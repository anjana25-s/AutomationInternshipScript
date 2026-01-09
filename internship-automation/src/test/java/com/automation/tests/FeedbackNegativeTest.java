package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FeedbackNegativeTest extends BaseClass {

    private HomepagePage home;
    private FeedbackPopupPage popup;
    private FeedbackErrorMessagesPage errors;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP = "Tester 1";
    private static final String OTP = "9999";

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        home   = new HomepagePage(page);
        popup  = new FeedbackPopupPage(page);
        errors = new FeedbackErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    @Test
    public void fullFeedbackNegativeFlow() {

        helper.log("==== FEEDBACK FULL NEGATIVE FLOW STARTED ====");

        // =====================================================
        // OPEN INTERNSHIP
        // =====================================================
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // =====================================================
        // INLINE FEEDBACK
        // =====================================================
        helper.waitForVisible(
                popup.getInlineFeedbackContainer(),
                "Inline Feedback Container"
        );

        popup.getFeedbackTextarea()
                .fill("Automated feedback for validation.");

        helper.safeClick(
                popup.getFeedbackSubmitBtn(),
                "Submit Feedback Text"
        );

        // =====================================================
        // DETAILS FORM — BLANK SUBMIT
        // =====================================================
        helper.waitForVisible(
                popup.getDetailsHeader(),
                "Details Form"
        );

        helper.safeClick(
                popup.getDetailsSubmitBtn(),
                "Submit Blank Form"
        );

        helper.assertVisible(errors.nameRequiredError(), "Name required error");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required error");
        helper.assertVisible(errors.emailRequiredError(), "Email required error");

        // =====================================================
        // NAME NEGATIVE CASES
        // =====================================================
        popup.getNameField().fill("ab");
        helper.safeClick(popup.getDetailsSubmitBtn(), "Short Name Submit");
        helper.assertVisible(errors.nameMinError(), "Name min error");

        popup.getNameField().fill("Megh@");
        helper.safeClick(popup.getDetailsSubmitBtn(), "Invalid Name Submit");
        helper.assertVisible(errors.nameInvalidError(), "Invalid name error");

        popup.getNameField().fill("A".repeat(51));
        helper.safeClick(popup.getDetailsSubmitBtn(), "Long Name Submit");
        helper.assertVisible(errors.nameMaxError(), "Name max error");

        // =====================================================
        // MOBILE + EMAIL NEGATIVE
        // =====================================================
        popup.getMobileField().fill("12345");
        helper.safeClick(popup.getDetailsSubmitBtn(), "Invalid Mobile Submit");
        helper.assertVisible(errors.mobileInvalidError(), "Invalid mobile error");

        popup.getEmailField().fill("megh@");
        helper.safeClick(popup.getDetailsSubmitBtn(), "Invalid Email Submit");
        helper.assertVisible(errors.emailInvalidError(), "Invalid email error");

        helper.log("==== BASIC NEGATIVE VALIDATIONS COMPLETED ====");

        // =====================================================
        // VALID DETAILS (NEW USER)
        // =====================================================
        String name  = helper.generateRandomName();
        String email = helper.generateEmailFromName(name);
        String phone = helper.generateRandomPhone();

        popup.getNameField().fill(name);
        popup.getMobileField().fill(phone);
        popup.getEmailField().fill(email);

        helper.safeClick(
                popup.getDetailsSubmitBtn(),
                "Submit Valid Feedback"
        );

        // =====================================================
        // OTP
        // =====================================================
        helper.waitForVisible(
                popup.getOtpHeader(),
                "OTP Screen"
        );

        for (int i = 1; i <= 4; i++) {
            popup.getOtpInput(i)
                    .fill(OTP.substring(i - 1, i));
        }

        helper.safeClick(
                popup.getOtpVerifyBtn(),
                "Verify OTP"
        );

        helper.log("---- OTP VERIFIED SUCCESSFULLY ----");

        // =====================================================
        // NEW SESSION — ALREADY REGISTERED EMAIL
        // =====================================================
        helper.log("---- CHECKING ALREADY REGISTERED EMAIL ----");

        BrowserContext oldContext = page.context();
        oldContext.close();

        page = browser.newContext().newPage();

        home   = new HomepagePage(page);
        popup  = new FeedbackPopupPage(page);
        errors = new FeedbackErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
        
        closeMilliIfPresent();
        closePreferenceModalIfPresent();

        helper.safeClick(home.getInternshipsTab(), "Open Internships Again");

        Locator cardAgain = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(cardAgain, "Internship Card Again");
        helper.scrollAndClick(cardAgain, "Open Internship Again");

        popup.getFeedbackTextarea()
                .fill("Checking already registered email flow");

        helper.safeClick(
                popup.getFeedbackSubmitBtn(),
                "Submit Feedback Again"
        );

        popup.getNameField().fill(name);
        popup.getMobileField().fill(helper.generateRandomPhone());
        popup.getEmailField().fill(email);

        helper.safeClick(
                popup.getDetailsSubmitBtn(),
                "Submit with Existing Email"
        );

        helper.assertToastAppeared(
                errors.emailAlreadyRegisteredToast(),
                "Already registered email toast appears"
        );

        helper.log("==== FEEDBACK FULL NEGATIVE FLOW COMPLETED ====");
    }
}



