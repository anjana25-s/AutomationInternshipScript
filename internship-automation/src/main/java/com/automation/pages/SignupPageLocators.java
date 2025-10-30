package com.automation.pages;

import com.microsoft.playwright.Page;

public class SignupPageLocators {

    private final Page page;

    public SignupPageLocators(Page page) {
        this.page = page;
    }

    // Locator for the first 'Sign Up' button (div)
    public final String initialSignupBtn = "//div[contains(@class,'sign-up-button') and normalize-space(text())='Sign Up']";

    // Locator for email/mobile input field
    public final String emailOrPhoneInput = "//input[@id='signup-email']";

    // Locator for 'Send Verification Code' button
    public final String sendVerificationCodeBtn = "//button[contains(@class,'signup-btn') and contains(text(),'Send Verification Code')]";

    // Locator for OTP input
    public final String otpInput = "//input[contains(@class,'otp-input')]";

    // Locator for Password input
    public final String passwordInput = "//input[@id='signup']";

    // Locator for final 'Sign Up' button
    public final String finalSignupBtn = "//button[contains(@class,'signup-btn') and contains(text(),'Sign Up')]";
}
