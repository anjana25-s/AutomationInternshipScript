package com.automation.utils;

import com.automation.pages.LoginpagePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Map;

public class LoginUtility {

    private final Page page;
    private final LoginpagePage loginPage;

    public LoginUtility(Page page) {
        this.page = page;
        this.loginPage = new LoginpagePage(page);
    }

    // ------------------------------------------------------------
    // LOGIN WITH EMAIL & PASSWORD
    // ------------------------------------------------------------
    public void loginWithPassword(String email, String password) {

        System.out.println("🔐 Logging in with email & password");

        loginPage.getLoginBtnOnHome().click();

        loginPage.getEmailInput().fill(email);
        loginPage.getPasswordInput().fill(password);

        loginPage.getLoginSubmitBtn().click();
    }

    // ------------------------------------------------------------
    // LOGIN USING LAST SAVED SIGNUP ACCOUNT
    // ------------------------------------------------------------
    public void loginWithSavedAccount() {

        Map<String, String> acc = TestAccountSave.loadLastAccount();

        if (acc == null) {
            throw new RuntimeException(
                    "❌ No saved signup account found. Run signup test first.");
        }

        loginWithPassword(acc.get("email"), acc.get("password"));
    }

    // ------------------------------------------------------------
    // ASSERT LOGIN SUCCESS (PURE PLAYWRIGHT)
    // ------------------------------------------------------------
    public boolean isLoginSuccessful() {

        if (loginPage.getHeaderProfileImg().isVisible()) return true;
        if (loginPage.getLogoutButton().isVisible()) return true;

        Locator internshipsTab =
                page.locator("//a[normalize-space()='Internships']");

        return internshipsTab.isVisible();
    }

    // ------------------------------------------------------------
    // LOGOUT
    // ------------------------------------------------------------
    public void logout() {
        try {
            loginPage.getUserDropdownIcon().click();
            loginPage.getLogoutButton().click();
        } catch (Exception ignored) {
            // user already logged out
        }
    }
}

