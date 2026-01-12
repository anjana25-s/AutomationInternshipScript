package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FeedbackPopupPage {

    private final Page page;

    public FeedbackPopupPage(Page page) {
        this.page = page;
    }

    // =====================================================
    // INLINE FEEDBACK (INTERNSHIP PAGE)
    // =====================================================
    public Locator getInlineFeedbackContainer() {
        return page.locator(".feedback-parent-container");
    }
 // ================= FEEDBACK BRAND NAME =================
    public Locator getFeedbackBrandName() {
        return page.locator("span.feedback-brandname");
    }

    public Locator getFeedbackTextarea() {
        return page.locator(
                ".feedback-parent-container textarea.feedback-input"
        );
    }

    public Locator getFeedbackSubmitBtn() {
        return page.locator(
                ".feedback-parent-container button.feedback-save-btn"
        );
    }

    // =====================================================
    // DETAILS / OTP MODAL (AFTER SUBMIT)
    // =====================================================
    private Locator activeModal() {
        return page.locator("div[role='dialog']").last();
    }

    // ---------- LEFT PANEL ----------
    public Locator getLeftPanelHeaders() {
        return activeModal()
                .locator(".askUs-content-container p.header-text");
    }

    public Locator getLeftPanelDescriptions() {
        return activeModal()
                .locator(".askUs-content-container p.conent-description-text");
    }

    // ---------- DETAILS FORM ----------
    public Locator getDetailsHeader() {
        return activeModal()
                .locator("h3.feature-content-header");
    }

    public Locator getNameField() {
        return activeModal().locator("input#userName");
    }

    public Locator getMobileField() {
        return activeModal().locator("input#userMobile");
    }

    public Locator getEmailField() {
        return activeModal().locator("input#userEmail");
    }

    public Locator getDetailsSubmitBtn() {
        return activeModal().locator("button.submit-btm-askUs");
    }

    // ---------- OTP ----------
    public Locator getOtpHeader() {
        return activeModal().locator("h5:has-text('OTP')");
    }

    public Locator getOtpInput(int index) {
        return activeModal()
                .locator("input[aria-label*='OTP']")
                .nth(index - 1);
    }

    public Locator getOtpVerifyBtn() {
        return activeModal().locator("button:has-text('Verify')");
    }

    // ---------- THANK YOU ----------
    public Locator getThankYouPopup() {
        return page.locator(".ThankYouPopup-Modal").first();
    }

    public Locator getThankYouHeader() {
        return getThankYouPopup()
                .locator(".headerText");
    }

    public Locator getThankYouMessage() {
        return getThankYouPopup()
                .locator("p")
                .first();
    }

    public Locator getThankYouFooter() {
        return getThankYouPopup()
                .locator(".ThankYou-footer p");
    }

    public Locator getThankYouCloseBtn() {
        return getThankYouPopup().locator("button");
    }
}
