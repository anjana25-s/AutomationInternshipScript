package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SignUpPage {

    private final Page page;

    // ------------------- Locators -------------------

    // ✔ Updated and stable locator
    private final String initialSignupBtn =
            "//nav//div[@class='sign-up-button' and normalize-space(text())='Sign Up']";

    private final String emailOrPhoneInput =
            "//input[@placeholder='Enter Email or Mobile Number']";

    private final String sendVerificationCodeBtn =
            "//button[normalize-space(text())='Send Verification Code']";

    private final String otpInput =
            "//input[@placeholder='Enter OTP sent to email' or @placeholder='Enter OTP sent to mobile number']";

    private final String passwordInput =
            "//input[@placeholder='Enter Password']";

    private final String finalSignupBtn =
            "//button[normalize-space(text())='Sign Up']";

    // ------------------- Constructor -------------------
    public SignUpPage(Page page) {
        this.page = page;
    }

    // ------------------- Locator Getters -------------------
    public Locator getInitialSignupButton() {
        return page.locator(initialSignupBtn);
    }

    public Locator getEmailOrPhoneInput() {
        return page.locator(emailOrPhoneInput);
    }

    public Locator getSendVerificationCodeButton() {
        return page.locator(sendVerificationCodeBtn);
    }

    public Locator getOtpInput() {
        return page.locator(otpInput);
    }

    public Locator getPasswordInput() {
        return page.locator(passwordInput);
    }

    public Locator getFinalSignupButton() {
        return page.locator(finalSignupBtn);
    }
}



