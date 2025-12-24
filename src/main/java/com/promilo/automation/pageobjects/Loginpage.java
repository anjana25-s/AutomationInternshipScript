package com.promilo.automation.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Loginpage {

    private final Page page;

    // 📱 Email or Mobile number input
    private final Locator loginMailPhone;

    // 🔒 Password field
    private final Locator passwordField;

    // 🔢 OTP field
    private final Locator otpField;

    // 🔘 Login button (used for both flows)
    private final Locator loginButton;

    // 🔄 Resend OTP button
    private final Locator resendOtpButton;

    private final Locator invalidToaster;

    private final Locator forgotPassword;

    private final Locator loginWithOtp;

    public Loginpage(Page page) {
        this.page = page;

        this.loginMailPhone = page.locator("//input[@placeholder='Enter Email Or Mobile No.']");
        this.passwordField = page.locator("//input[@placeholder='Enter Password']");
        this.otpField = page.locator("//input[@id='otp']");
        this.loginButton = page.locator("//button[normalize-space()='Login']");
        this.resendOtpButton = page.locator("//button[@class='resend-btn rounded signup-btn w-100 font-16 fw-bold border-0 text-white p-1 btn btn-primary']");
        this.invalidToaster = page.locator("//div[@role='status']");
        this.forgotPassword = page.locator("//p[text()='Forgot Password']");
        this.loginWithOtp = page.locator("//p[text()='Login with OTP']");
    }

    // --- Getter methods returning Locator for flexibility ---

    public Locator loginMailPhone() {
        return loginMailPhone;
    }

    public Locator passwordField() {
        return passwordField;
    }

    public Locator otpField() {
        return otpField;
    }

    public Locator loginButton() {
        return loginButton;
    }

    public Locator resendOtpButton() {
        return resendOtpButton;
    }

    public Locator invalidToaster() {
        return invalidToaster;
    }

    public Locator forgotPassword() {
        return forgotPassword;
    }

    public Locator loginWithOtp() {
        return loginWithOtp;
    }
}
