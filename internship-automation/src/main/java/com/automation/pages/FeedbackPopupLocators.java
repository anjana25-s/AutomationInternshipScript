package com.automation.pages;

import com.microsoft.playwright.Page;

public class FeedbackPopupLocators {
    private final Page page;

    public FeedbackPopupLocators(Page page) {
        this.page = page;
    }

    // Root modal container
    public final String feedbackModal = "div.Job-Feedback-modal";

    // Feedback textarea inside modal
    public final String feedbackTextarea = feedbackModal + " textarea.feedback-input";

    // Submit button inside modal
    public final String feedbackSubmitBtn = feedbackModal + " button.feedback-save-btn";

    // User details popup
    public final String nameField = "div[role='dialog'] input#userName";
    public final String mobileField = "div[role='dialog'] input#userMobile";
    public final String emailField = "div[role='dialog'] input#userEmail";
    public final String popupSubmitBtn = "div[role='dialog'] button.submit-btm-askUs";

    // OTP fields
    public final String otpInputTemplate = "div[role='dialog'] input[aria-label='Please enter OTP character %d']";
    public final String otpVerifyBtn = "div[role='dialog'] button.submit-btm-askUs";

    // Thank You popup
    public final String thankYouPopup = "div.ThankYouPopup-Modal";
    public final String thankYouCloseBtn = thankYouPopup + " button";
}

