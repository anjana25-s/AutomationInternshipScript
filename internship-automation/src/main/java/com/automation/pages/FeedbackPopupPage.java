package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FeedbackPopupPage {

    private final Page page;

    // ------------------- Locators -------------------
    private final String feedbackModal = "div.Job-Feedback-modal";
    private final String feedbackTextarea = feedbackModal + " textarea.feedback-input";
    private final String feedbackSubmitBtn = feedbackModal + " button.feedback-save-btn";

    private final String nameField = "div[role='dialog'] input#userName";
    private final String mobileField = "div[role='dialog'] input#userMobile";
    private final String emailField = "div[role='dialog'] input#userEmail";
    private final String popupSubmitBtn = "div[role='dialog'] button.submit-btm-askUs";

    private final String otpInputTemplate = "div[role='dialog'] input[aria-label='Please enter OTP character %d']";
    private final String otpVerifyBtn = "div[role='dialog'] button.submit-btm-askUs";

    private final String thankYouPopup = "div.ThankYouPopup-Modal";
    private final String thankYouCloseBtn = thankYouPopup + " button";

    // ------------------- Constructor -------------------
    public FeedbackPopupPage(Page page) {
        this.page = page;
    }

    // ------------------- Getters -------------------
    public Locator getFeedbackTextarea() { return page.locator(feedbackTextarea); }
    public Locator getFeedbackSubmitBtn() { return page.locator(feedbackSubmitBtn); }

    public Locator getNameField() { return page.locator(nameField); }
    public Locator getMobileField() { return page.locator(mobileField); }
    public Locator getEmailField() { return page.locator(emailField); }
    public Locator getPopupSubmitBtn() { return page.locator(popupSubmitBtn); }

    public Locator getOtpInput(int index) {
        return page.locator(String.format(otpInputTemplate, index));
    }

    public Locator getOtpVerifyBtn() { return page.locator(otpVerifyBtn); }

    public Locator getThankYouPopup() { return page.locator(thankYouPopup); }
    public Locator getThankYouCloseBtn() { return page.locator(thankYouCloseBtn); }
}


