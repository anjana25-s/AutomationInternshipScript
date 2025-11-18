package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginpagePage {

    private final Page page;

    // Home login button
    private final Locator loginBtnOnHome;

    // Email/Mobile + Password login
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginSubmitBtn;

    // OTP login
    private final Locator loginWithOtpBtn;
    private final Locator otpInput;

    // Error toast (User Doesn't Exists)
    private final Locator userDoesNotExistError;

    // Profile icon after login
    private final Locator headerProfileImg;

    public LoginpagePage(Page page) {
        this.page = page;

        this.loginBtnOnHome = page.locator("//div[@class='Login-button']");
        this.emailInput = page.locator("input[placeholder='Enter Email Or Mobile No.']");
        this.passwordInput = page.locator("input[placeholder='Enter Password']");
        this.loginSubmitBtn = page.locator("//button[contains(@class,'signup-btn') and text()='Login']");

        this.loginWithOtpBtn = page.locator("p:has-text('Login with OTP')");
        this.otpInput = page.locator("#otp");

        this.userDoesNotExistError = page.getByRole(AriaRole.STATUS);
        this.headerProfileImg = page.locator("div.header-profile-img");
    }

    public Locator getLoginBtnOnHome() { return loginBtnOnHome; }
    public Locator getEmailInput() { return emailInput; }
    public Locator getPasswordInput() { return passwordInput; }
    public Locator getLoginSubmitBtn() { return loginSubmitBtn; }
    public Locator getLoginWithOtpBtn() { return loginWithOtpBtn; }
    public Locator getOtpInput() { return otpInput; }

    public Locator getUserDoesNotExistError() { return userDoesNotExistError; }
    public Locator getHeaderProfileImg() { return headerProfileImg; }
}

