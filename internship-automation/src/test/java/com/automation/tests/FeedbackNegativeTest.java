package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.FeedbackErrorMessagesPage;
import com.automation.pages.FeedbackPopupPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class FeedbackNegativeTest extends BaseClass {

    @Test
    public void fullFeedbackNegativeFlow() {

        HomepagePage home = new HomepagePage(page);
        FeedbackPopupPage popup = new FeedbackPopupPage(page);
        FeedbackErrorMessagesPage errors = new FeedbackErrorMessagesPage(page);
        HelperUtility helper = new HelperUtility(page);

        String INTERNSHIP = "Designer1";

        Reporter.log("======== FEEDBACK NEGATIVE FLOW STARTED ========", true);

        // -------------------- NAVIGATE --------------------
        helper.log("[STEP] Navigate to website");
        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Maybe Later");
        }

        helper.safeClick(home.getInternshipsTab(), "Open Internships");
        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // -------------------- FEEDBACK POPUP 1 --------------------
        helper.waitForVisible(popup.getFeedbackTextarea(), "Feedback Textarea");

        helper.safeFill(popup.getFeedbackTextarea(),
                "Automated feedback for validation.",
                "Feedback Text");

        helper.safeClick(popup.getFeedbackSubmitBtn(), "Submit Feedback Text");

        // -------------------- OPEN ASK US POPUP --------------------
        helper.waitForVisible(popup.getPopupSubmitBtn(), "Ask Us Popup");

        // -------------------- SUBMIT BLANK --------------------
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Blank Ask-Us Form");
        errors.assertRequiredErrorMessages();

        // ======================================================
        //                 FIELD-BY-FIELD NEGATIVE CASES
        // ======================================================

        // -------------------- NAME MIN --------------------
        popup.getNameField().fill("ab");
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Name Min");
        errors.assertNameMin();

        // -------------------- NAME INVALID --------------------
        popup.getNameField().fill("Megh@");
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Name Invalid");
        errors.assertNameInvalid();

        // -------------------- NAME MAX --------------------
        popup.getNameField().fill("A".repeat(51));
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Name Max");
        errors.assertNameMax();

        // -------------------- MOBILE INVALID --------------------
        popup.getMobileField().fill("12345");
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Mobile Invalid");
        errors.assertMobileInvalid();

        // -------------------- EMAIL INVALID --------------------
        popup.getEmailField().fill("megh@");
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Email Invalid");
        errors.assertEmailInvalid();

        // -------------------- FEEDBACK MAX --------------------
        popup.getFeedbackTextarea().fill("A".repeat(550));
        helper.safeClick(popup.getPopupSubmitBtn(), "Submit Long Feedback");
        errors.assertFeedbackMax();

        Reporter.log("======== FEEDBACK NEGATIVE FLOW COMPLETED SUCCESSFULLY ========", true);
    }
}



