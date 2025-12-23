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
    public void openBase() {

        home   = new HomepagePage(page);
        popup  = new FeedbackPopupPage(page);
        errors = new FeedbackErrorMessagesPage(page);
        helper = new HelperUtility(page);

        helper.log("Navigating to Promilo");
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Maybe Later");
        }
    }

    @Test
    public void fullFeedbackNegativeFlow() {

        helper.log("==== FEEDBACK FULL NEGATIVE FLOW STARTED ====");

        // ------------------------------------------------------------
        // OPEN INTERNSHIP
        // ------------------------------------------------------------
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // ------------------------------------------------------------
        // FEEDBACK TEXTAREA
        // ------------------------------------------------------------
        Locator feedbackBox = popup.getFeedbackTextarea();
        helper.waitForVisible(feedbackBox, "Feedback Textarea");

        feedbackBox.fill("Automated feedback for validation.");
        helper.safeClick(popup.getFeedbackSubmitBtn(),
                "Submit Feedback Text");

        // ------------------------------------------------------------
        // ASK US POPUP
        // ------------------------------------------------------------
        Locator askUsModal =
                page.locator("//div[contains(@class,'AskUs-Modal') and contains(@class,'show')]");

        helper.waitForVisible(askUsModal, "Ask Us Popup");

        // ------------------------------------------------------------
        // BLANK SUBMIT
        // ------------------------------------------------------------
        helper.safeClick(popup.getPopupSubmitBtn(),
                "Submit Blank Form");

        helper.assertVisible(errors.nameRequiredError(),
                "Name required error");
        helper.assertVisible(errors.mobileRequiredError(),
                "Mobile required error");
        helper.assertVisible(errors.emailRequiredError(),
                "Email required error");

        // ------------------------------------------------------------
        // NAME VALIDATIONS
        // ------------------------------------------------------------
        popup.getNameField().fill("ab");
        helper.safeClick(popup.getPopupSubmitBtn(),
                "Short Name Submit");
        helper.assertVisible(errors.nameMinError(),
                "Name min error");

        popup.getNameField().fill("Megh@");
        helper.safeClick(popup.getPopupSubmitBtn(),
                "Invalid Name Submit");
        helper.assertVisible(errors.nameInvalidError(),
                "Invalid name error");

        popup.getNameField().fill("A".repeat(51));
        helper.safeClick(popup.getPopupSubmitBtn(),
                "Long Name Submit");
        helper.assertVisible(errors.nameMaxError(),
                "Name max error");

        // ------------------------------------------------------------
        // MOBILE + EMAIL NEGATIVE
        // ------------------------------------------------------------
        popup.getMobileField().fill("12345");
        helper.safeClick(popup.getPopupSubmitBtn(),
                "Invalid Mobile Submit");
        helper.assertVisible(errors.mobileInvalidError(),
                "Invalid mobile error");

        popup.getEmailField().fill("megh@");
        helper.safeClick(popup.getPopupSubmitBtn(),
                "Invalid Email Submit");
        helper.assertVisible(errors.emailInvalidError(),
                "Invalid email error");

        helper.log("==== BASIC NEGATIVE VALIDATIONS COMPLETED ====");

        // ------------------------------------------------------------
        // VALID FEEDBACK (NEW USER)
        // ------------------------------------------------------------
        String name  = helper.generateRandomName();
        String email = helper.generateEmailFromName(name);
        String phone = helper.generateRandomPhone();

        popup.getNameField().fill(name);
        popup.getMobileField().fill(phone);
        popup.getEmailField().fill(email);

        helper.safeClick(popup.getPopupSubmitBtn(),
                "Submit Valid Feedback");

        // ------------------------------------------------------------
        // OTP
        // ------------------------------------------------------------
        Locator otpModal =
                page.locator("//h5[text()='OTP Verification']/ancestor::div[@class='modal-content']");
        helper.waitForVisible(otpModal, "OTP Modal");

        for (int i = 1; i <= 4; i++) {
            helper.safeFill(
                    popup.getOtpInput(i),
                    OTP.substring(i - 1, i),
                    "OTP Digit " + i
            );
        }

        helper.safeClick(popup.getOtpVerifyBtn(),
                "Verify OTP");

        helper.log("---- OTP VERIFIED SUCCESSFULLY ----");

        // ------------------------------------------------------------
        // NEW SESSION — ALREADY REGISTERED EMAIL
        // ------------------------------------------------------------
        helper.log("---- CHECKING ALREADY REGISTERED EMAIL ----");

        BrowserContext oldContext = page.context();
        oldContext.close();

        page = browser.newContext().newPage();

        home   = new HomepagePage(page);
        popup  = new FeedbackPopupPage(page);
        errors = new FeedbackErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate(BASE_URL);

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Maybe Later");
        }

        helper.safeClick(home.getInternshipsTab(),
                "Open Internships Again");

        Locator cardAgain = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(cardAgain, "Internship Card Again");
        helper.scrollAndClick(cardAgain, "Open Internship Again");

        popup.getFeedbackTextarea()
                .fill("Checking already registered email flow");

        helper.safeClick(popup.getFeedbackSubmitBtn(),
                "Submit Feedback Again");

        popup.getNameField().fill(name);
        popup.getMobileField().fill(helper.generateRandomPhone());
        popup.getEmailField().fill(email);

        helper.safeClick(popup.getPopupSubmitBtn(),
                "Submit with Existing Email");

        helper.assertToastAppeared(
                errors.emailAlreadyRegisteredToast(),
                "Already registered email toast appears"
        );

        helper.log("==== FEEDBACK FULL NEGATIVE FLOW COMPLETED ====");
    }
}


