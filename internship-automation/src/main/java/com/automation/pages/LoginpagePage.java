package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginpagePage {

    private final Page page;

    // ================= LOGIN =================

    // Home login button
    private final Locator loginBtnOnHome;

    // Email / Mobile + Password
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginSubmitBtn;

    // OTP login
    private final Locator loginWithOtpBtn;
    private final Locator otpInput;

    // Error toast
    private final Locator userDoesNotExistError;

    // ================= AFTER LOGIN =================

    // Profile initial (circle with letter)
    private final Locator headerProfileImg;

    // Dropdown trigger (Hi <Name> container)
    private final Locator userDropdownTrigger;

    // Sign Out button (menu item)
    private final Locator signOutButton;

    // Optional confirm logout button
    private final Locator confirmLogoutButton;

    // ================= CONSTRUCTOR =================

    public LoginpagePage(Page page) {
        this.page = page;

        // -------- Login --------
        this.loginBtnOnHome =
                page.locator("//div[@class='Login-button']");

        this.emailInput =
                page.locator("input[placeholder='Enter Email Or Mobile No.']");

        this.passwordInput =
                page.locator("input[placeholder='Enter Password']");

        this.loginSubmitBtn =
                page.locator("//button[contains(@class,'signup-btn') and normalize-space()='Login']");

        this.loginWithOtpBtn =
                page.locator("p:has-text('Login with OTP')");

        this.otpInput =
                page.locator("#otp");

        this.userDoesNotExistError =
                page.getByRole(AriaRole.STATUS);

        // -------- After Login --------
        this.headerProfileImg =
                page.locator("div.header-profile-img");

        /**
         * IMPORTANT:
         * Click the <a> wrapper, NOT the img.
         * This avoids strict-mode violations.
         */
        this.userDropdownTrigger =
                page.locator("li.dropdown-user a.dropdown-user-link").first();

        /**
         * Stable logout locator:
         * Targets the actual menu item, not text alone
         */
        this.signOutButton =
                page.locator("button:has(span:has-text('Sign Out'))").first();

        // Optional confirmation popup
        this.confirmLogoutButton =
                page.locator("button:has-text('Logout')").first();
    }

    // ================= GETTERS =================

    public Locator getLoginBtnOnHome() { return loginBtnOnHome; }
    public Locator getEmailInput() { return emailInput; }
    public Locator getPasswordInput() { return passwordInput; }
    public Locator getLoginSubmitBtn() { return loginSubmitBtn; }
    public Locator getLoginWithOtpBtn() { return loginWithOtpBtn; }
    public Locator getOtpInput() { return otpInput; }

    public Locator getUserDoesNotExistError() { return userDoesNotExistError; }
    public Locator getHeaderProfileImg() { return headerProfileImg; }

    public Locator getUserDropdownTrigger() { return userDropdownTrigger; }
    public Locator getSignOutButton() { return signOutButton; }
    public Locator getConfirmLogoutButton() { return confirmLogoutButton; }
}



