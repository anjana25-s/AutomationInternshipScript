package com.automation.utils;

import com.automation.pages.LoginpagePage;
import com.microsoft.playwright.Page;

public class LoginUtility {

    private final Page page;
    private final LoginpagePage loginPage;

    public LoginUtility(Page page) {
        this.page = page;
        this.loginPage = new LoginpagePage(page);
    }

    public void loginUsingSignupAccount() {

        var acc = TestAccountStore.get();

        if (acc == null) {
            throw new RuntimeException(
                "❌ No USER signup account saved. " +
                "Run ApplyNowEmailTest (signup) before login tests."
            );
        }

        String username = acc.get("username");
        String password = acc.get("password");

        loginPage.getLoginBtnOnHome().click();
        loginPage.getEmailInput().fill(username);
        loginPage.getPasswordInput().fill(password);
        loginPage.getLoginSubmitBtn().click();

        page.waitForLoadState();
    }
}