package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.SignupPageActions;

public class SignupPageScript {

    private final SignupPageActions actions;

    public SignupPageScript(Page page) {
        this.actions = new SignupPageActions(page);
    }

    // Full signup flow
    public void completeSignupFlow(String emailOrPhone, String otp, String password, boolean isEmail) {
        // Click initial sign up button
        actions.clickInitialSignupButton();

        // Enter email or phone
        actions.enterEmailOrPhone(emailOrPhone);

        // Click 'Send Verification Code'
        actions.clickVerificationCode();

        // Enter OTP (from email or SMS)
        actions.enterOtp(otp, isEmail);

        // Enter password
        actions.enterPassword(password);

        // Click final sign up button
        actions.clickFinalSignupButton();
    }

    // Individual steps (optional, if you need granular control)
    public void clickInitialSignupButton() {
        actions.clickInitialSignupButton();
    }

    public void enterEmailOrPhone(String emailOrPhone) {
        actions.enterEmailOrPhone(emailOrPhone);
    }

    public void clickVerificationCode() {
        actions.clickVerificationCode();
    }

    public void enterOtp(String otp, boolean isEmail) {
        actions.enterOtp(otp, isEmail);
    }

    public void enterPassword(String password) {
        actions.enterPassword(password);
    }

    public void clickFinalSignupButton() {
        actions.clickFinalSignupButton();
    }
}



