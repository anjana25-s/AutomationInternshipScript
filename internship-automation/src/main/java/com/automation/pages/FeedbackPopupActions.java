package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class FeedbackPopupActions {

    private final Page page;
    private final FeedbackPopupLocators locators;

    public FeedbackPopupActions(Page page) {
        this.page = page;
        this.locators = new FeedbackPopupLocators(page);
    }

    // Enter feedback text
    public void enterFeedback(String feedbackText) {
        Locator feedbackInput = page.locator(locators.feedbackTextarea);

        // Wait for textarea to be visible
        feedbackInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(20000));

        feedbackInput.scrollIntoViewIfNeeded();
        feedbackInput.fill(feedbackText);
        System.out.println("[FeedbackPopupActions] Entered feedback: " + feedbackText);
    }

    // Submit feedback
    public void submitFeedback() {
        Locator submitBtn = page.locator(locators.feedbackSubmitBtn);

        // Scroll button into view
        submitBtn.scrollIntoViewIfNeeded();

        // Wait until attached to DOM and enabled
        submitBtn.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE) // visible includes enabled
                .setTimeout(15000));

        // Click the button
        submitBtn.click();
        System.out.println("[FeedbackPopupActions] Submitted feedback");
    }

    // Fill user details
    public void fillUserDetails(String name, String mobile, String email) {
        Locator nameInput = page.locator(locators.nameField);
        Locator mobileInput = page.locator(locators.mobileField);
        Locator emailInput = page.locator(locators.emailField);

        if (nameInput.isEnabled()) nameInput.fill(name != null ? name : "");
        if (mobileInput.isEnabled()) mobileInput.fill(mobile != null ? mobile : "");
        if (emailInput.isEnabled()) emailInput.fill(email != null ? email : "");

        System.out.println("[FeedbackPopupActions] Filled user details (skipped disabled fields)");
    }

    // Submit user details
    public void clickUserDetailsSubmit() {
        Locator submitBtn = page.locator(locators.popupSubmitBtn);
        submitBtn.scrollIntoViewIfNeeded();
        submitBtn.click();
        System.out.println("[FeedbackPopupActions] Submitted user details");
    }

    // Verify OTP
    public void verifyOtp(String otp) {
        Locator otpInput = page.locator(locators.otpInputTemplate.replace("%d", "1"));
        Locator verifyBtn = page.locator(locators.otpVerifyBtn);

        otpInput.scrollIntoViewIfNeeded();
        otpInput.fill(otp);

        verifyBtn.scrollIntoViewIfNeeded();
        verifyBtn.click();

        System.out.println("[FeedbackPopupActions] Verified OTP: " + otp);
    }

    // Check if Thank You popup is displayed
    public boolean isThankYouDisplayed() {
        Locator thankYouPopup = page.locator(locators.thankYouPopup);
        thankYouPopup.scrollIntoViewIfNeeded();
        boolean visible = thankYouPopup.isVisible();
        System.out.println("[FeedbackPopupActions] Thank You popup visible: " + visible);
        return visible;
    }
}

