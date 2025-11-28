package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.FeedbackErrorMessagesPage;
import com.automation.pages.FeedbackPopupPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

public class FeedbackNegativeTest extends BaseClass {

    @Test
    public void fullFeedbackNegativeFlow() {

        HomepagePage home = new HomepagePage(page);
        FeedbackPopupPage popup = new FeedbackPopupPage(page);
        FeedbackErrorMessagesPage errors = new FeedbackErrorMessagesPage(page);
        HelperUtility helper = new HelperUtility(page);

        helper.log("==== FEEDBACK FULL NEGATIVE FLOW STARTED ====");

        String INTERNSHIP = "Designer1";

        // ------------------------------------------------------------
        // NAVIGATION
        // ------------------------------------------------------------
        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible())
            helper.safeClick(home.getMaybeLaterBtn(), "Close Maybe Later");

        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // ------------------------------------------------------------
        // PRE-FEEDBACK TEXTAREA
        // ------------------------------------------------------------
        Locator preBox = popup.getFeedbackTextarea();
        helper.waitForVisible(preBox, "Feedback Textarea");

        helper.safeClick(preBox, "Focus Feedback Box");
        preBox.fill("Automated feedback for validation.");
        helper.log("[Fill] Feedback Text = Automated feedback for validation.");

        helper.safeClick(popup.getFeedbackSubmitBtn(), "Submit Feedback Text");

        // ------------------------------------------------------------
        // ASK US POPUP
        // ------------------------------------------------------------
        Locator askUsModal = page.locator("//div[contains(@class,'AskUs-Modal') and contains(@class,'show')]");
        helper.waitForVisible(askUsModal, "Ask Us Popup");

        popup.getNameField().waitFor();

        // ------------------------------------------------------------
        // BLANK SUBMIT
        // ------------------------------------------------------------
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Blank Form");

        helper.assertVisible(errors.nameRequiredError(), "Name required");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required");
        helper.assertVisible(errors.emailRequiredError(), "Email required");

        // ------------------------------------------------------------
        // NAME MIN
        // ------------------------------------------------------------
        popup.getNameField().fill("ab");
        helper.safeClick(popup.getPopupSubmitBtn(), "Short Name Submit");
        helper.assertVisible(errors.nameMinError(), "Name min error");

        // ------------------------------------------------------------
        // NAME INVALID
        // ------------------------------------------------------------
        popup.getNameField().fill("Megh@");
        helper.safeClick(popup.getPopupSubmitBtn(), "Invalid Name Submit");
        helper.assertVisible(errors.nameInvalidError(), "Invalid name error");

        // ------------------------------------------------------------
        // NAME MAX
        // ------------------------------------------------------------
        popup.getNameField().fill("A".repeat(51));
        helper.safeClick(popup.getPopupSubmitBtn(), "Long Name Submit");
        helper.assertVisible(errors.nameMaxError(), "Name max error");

        // ------------------------------------------------------------
        // MOBILE INVALID
        // ------------------------------------------------------------
        popup.getMobileField().fill("12345");
        helper.safeClick(popup.getPopupSubmitBtn(), "Invalid Mobile Submit");
        helper.assertVisible(errors.mobileInvalidError(), "Invalid mobile error");

        // ------------------------------------------------------------
        // EMAIL INVALID
        // ------------------------------------------------------------
        popup.getEmailField().fill("megh@");
        helper.safeClick(popup.getPopupSubmitBtn(), "Invalid Email Submit");
        helper.assertVisible(errors.emailInvalidError(), "Invalid email error");

        helper.log("==== BASIC NEGATIVE FLOW COMPLETED ====");

        // ------------------------------------------------------------
        // VALID FEEDBACK — REGISTER USER
        // ------------------------------------------------------------
        String NAME = helper.generateRandomName();
        String EMAIL = helper.generateEmailFromName(NAME);
        String PHONE = helper.generateRandomPhone();

        helper.log("Registering new user via feedback:");
        helper.log(NAME);
        helper.log(EMAIL);
        helper.log(PHONE);

        popup.getNameField().fill(NAME);
        popup.getMobileField().fill(PHONE);
        popup.getEmailField().fill(EMAIL);
        popup.getFeedbackTextarea().fill("Valid feedback submission.");

        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Valid Feedback");

        // ------------------------------------------------------------
        // OTP SCREEN
        // ------------------------------------------------------------
        helper.log("---- OTP SCREEN APPEARED ----");

        Locator otpModal = page.locator("//h5[text()='OTP Verification']/ancestor::div[@class='modal-content']");
        helper.waitForVisible(otpModal, "OTP Modal");

        // Enter OTP = 9999
        String OTP = "9999";
        for (int i = 1; i <= 4; i++) {
            helper.safeFill(popup.getOtpInput(i), OTP.substring(i - 1, i), "OTP Digit " + i);
        }

        helper.safeClick(popup.getOtpVerifyBtn(), "Verify OTP");
        helper.log("---- OTP VERIFIED SUCCESSFULLY ----");

        // ------------------------------------------------------------
        // NEW SESSION - CHECK ALREADY REGISTERED EMAIL
        // ------------------------------------------------------------
        helper.log("---- TESTING ALREADY REGISTERED EMAIL ----");

        BrowserContext oldCtx = page.context();
        oldCtx.close();

        // New clean page
        page = browser.newContext().newPage();

        home = new HomepagePage(page);
        popup = new FeedbackPopupPage(page);
        errors = new FeedbackErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");

        if (home.getMaybeLaterBtn().isVisible())
            helper.safeClick(home.getMaybeLaterBtn(), "Close Maybe Later");

        helper.safeClick(home.getInternshipsTab(), "Open Internships Again");

        Locator card2 = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card2, "Internship Card");
        helper.scrollAndClick(card2, "Open Internship Again");

        Locator preBox2 = popup.getFeedbackTextarea();
        helper.waitForVisible(preBox2, "Feedback Textarea Again");
        preBox2.fill("Checking already registered email flow...");
        helper.safeClick(popup.getFeedbackSubmitBtn(), "Submit First Box Again");

        popup.getNameField().fill(NAME);
        popup.getMobileField().fill(helper.generateRandomPhone());
        popup.getEmailField().fill(EMAIL);

        helper.safeClick(popup.getPopupSubmitBtn(), "Submit with Already Registered Email");

        helper.assertToastAppeared(
                errors.emailAlreadyRegisteredToast(),
                "Already registered email toast appears"
        );

        helper.log("==== FEEDBACK FULL NEGATIVE FLOW COMPLETED ====");
    }
}


