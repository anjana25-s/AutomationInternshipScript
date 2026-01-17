package com.promilo.automation.internship.utilities;

import com.microsoft.playwright.Page;
import com.promilo.automation.internship.assignment.LoginPage;

import java.util.Map;

public class LoginUtility {

    private final Page page;
    private final LoginPage loginPage;

    // ❌ DO NOT change constructor name
    public LoginUtility(Page page) {
        this.page = page;
        this.loginPage = new LoginPage(page);
    }

    // ❌ DO NOT change method name
    public void loginWithSavedUser() {

        Map<String, String> acc = TestAccountStore.get();

        if (acc == null) {
            throw new RuntimeException(
                "❌ No signup credentials found. Run signup test first."
            );
        }

        // ✅ FIX: use SAME key that TestAccountStore saves
        String email = acc.get("username");
        String password = acc.get("password");

       
     
        loginPage.clickMaybeLater();
        loginPage.clickLoginButtonOnHome();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmit();

        page.waitForLoadState();
    }
}
