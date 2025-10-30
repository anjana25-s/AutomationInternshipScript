package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class SignupPageActions {

    private final Page page;
    private final SignupPageLocators locators;

    public SignupPageActions(Page page) {
        this.page = page;
        this.locators = new SignupPageLocators(page);
    }

    private void safeClick(Locator locator, String description) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.scrollIntoViewIfNeeded();
        for (int i = 0; i < 3; i++) {
            try {
                locator.click();
                System.out.println("[SignupPageActions] Clicked: " + description);
                page.waitForTimeout(500); // give UI time to render next step
                break;
            } catch (Exception e) {
                System.out.println("[SignupPageActions] Retry click (" + (i + 1) + "): " + description);
            }
        }
    }

    private void safeFill(Locator locator, String value, String description) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.scrollIntoViewIfNeeded();
        locator.fill(value);
        System.out.println("[SignupPageActions] Filled: " + description);
        page.waitForTimeout(300); // small pause after fill
    }

    public void clickInitialSignupButton() {
        safeClick(page.locator(locators.initialSignupBtn), "initial 'Sign Up' button");
    }

    public void enterEmailOrPhone(String emailOrPhone) {
        safeFill(page.locator(locators.emailOrPhoneInput), emailOrPhone, "email or phone");
    }

    public void clickVerificationCode() {
        safeClick(page.locator(locators.sendVerificationCodeBtn), "'Send Verification Code'");
    }

    public void enterOtp(String otp, boolean isEmail) {
        safeFill(page.locator(locators.otpInput), otp, "OTP");
    }

    public void enterPassword(String password) {
        safeFill(page.locator(locators.passwordInput), password, "password");
    }

    public void clickFinalSignupButton() {
        safeClick(page.locator(locators.finalSignupBtn), "final 'Sign Up' button");
    }
}


