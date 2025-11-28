package com.automation.utils;

import com.automation.pages.LoginpagePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Map;

public class LoginUtility {

    private final Page page;
    private final LoginpagePage loginPage;
    private final HelperUtility helper;

    public LoginUtility(Page page) {
        this.page = page;
        this.loginPage = new LoginpagePage(page);
        this.helper = new HelperUtility(page);
    }

    // ------------------------------------------------------------
    // 🔹 PASSWORD LOGIN
    // ------------------------------------------------------------
    public void loginWithPassword(String email, String password) {

        helper.safeClick(loginPage.getLoginBtnOnHome(), "Open Login Popup");

        helper.safeFill(loginPage.getEmailInput(), email, "Enter Email");
        helper.safeFill(loginPage.getPasswordInput(), password, "Enter Password");

        helper.safeClick(loginPage.getLoginSubmitBtn(), "Login");
        assertLoginSuccess();
    }

    // ------------------------------------------------------------
    // 🔹 OTP LOGIN
    // ------------------------------------------------------------
    public void loginWithOtp(String mobile, String otp) {

        helper.safeClick(loginPage.getLoginBtnOnHome(), "Open Login Popup");

        helper.safeFill(loginPage.getEmailInput(), mobile, "Enter Mobile Number");

        helper.safeClick(loginPage.getLoginWithOtpBtn(), "Click Login with OTP");

        loginPage.getOtpInput().waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(15000)
        );

        helper.safeFill(loginPage.getOtpInput(), otp, "Enter OTP");

        helper.safeClick(loginPage.getLoginSubmitBtn(), "Submit OTP");

        assertLoginSuccess();
    }

    // ------------------------------------------------------------
    // 🔹 LOGIN USING LAST SAVED JSON ACCOUNT
    // ------------------------------------------------------------
    public void loginWithSavedAccount() {

        Map<String, String> acc = TestAccountSave.loadLastAccount();

        if (acc == null) {
            throw new RuntimeException("❌ No saved account found! Run Signup test first.");
        }

        String email = acc.get("email");
        String password = acc.get("password");

        helper.log("🔐 Logging in with saved account: " + email);

        loginWithPassword(email, password);
    }

    // ------------------------------------------------------------
    // 🔹 SUCCESS CHECK
    // ------------------------------------------------------------
    public void assertLoginSuccess() {
        helper.assertVisible(loginPage.getHeaderProfileImg(), "Profile Icon Visible → Login Success");
    }

    // ------------------------------------------------------------
    // 🔹 LOGOUT
    // ------------------------------------------------------------
    public void logout() {
        try {
            helper.log("🔽 Opening user dropdown...");
            helper.safeClick(loginPage.getUserDropdownIcon(), "Open User Dropdown");

            helper.safeClick(loginPage.getLogoutButton(), "Click Sign Out");

            helper.log("✔ Logged out successfully");

        } catch (Exception e) {
            helper.log("⚠ Logout skipped — already logged out or dropdown missing");
        }
    }
}