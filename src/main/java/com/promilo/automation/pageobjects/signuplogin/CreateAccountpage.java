package com.promilo.automation.pageobjects.signuplogin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateAccountpage {

    private final Page page;

    // Locators for Signup Page
    private final Locator phoneMailTextField;
    private final Locator sendCodeButton;
    private final Locator otpField;
    private final Locator passwordField;
    private final Locator submitButton;
    private final Locator existingUserToast;
    private final Locator invalidString;

    // Constructor to initialize locators
    public CreateAccountpage(Page page) {
        this.page = page;

        this.phoneMailTextField = page.locator("//input[@id='signup-email']");
        this.sendCodeButton = page.locator("//button[text()='Send Verification Code']");
        this.otpField = page.locator("//input[@placeholder='Enter OTP sent to email']");
        this.passwordField = page.locator("//input[@placeholder='Enter Password']");
        this.submitButton = page.locator("//button[contains(@class,'signup-btn') and contains(@class,'btn-primary')]");
        this.existingUserToast = page.locator("//div[@role='status']");
        this.invalidString = page.locator("//p[contains(@class,'text-danger')]");
    }

    // Getter methods for locators

    public Locator getPhoneMailTextField() {
        return phoneMailTextField;
    }

    public Locator getSendCodeButton() {
        return sendCodeButton;
    }

    public Locator getOtpField() {
        return otpField;
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getExistingUserToast() {
        return existingUserToast;
    }

    public Locator getInvalidString() {
        return invalidString;
    }
}