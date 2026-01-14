package com.promilo.automation.internship.utilities;

import com.promilo.automation.internship.assignment.LoginPage;
import com.microsoft.playwright.Page;
import java.util.Map;

public class LoginUtility {

    private final Page page;
    private final LoginPage loginPage;

    public LoginUtility(Page page) {
        this.page = page;
        this.loginPage = new LoginPage(page);
    }

    public void loginUsingSavedAccount() {
        Map<String, String> acc = TestAccountStore.get();

        if (acc == null) {
            throw new RuntimeException(
                "❌ No saved signup account found. Run Signup test first."
            );
        }

        String username = acc.get("username");
        String password = acc.get("password");

        System.out.println("🔐 Logging in with saved account: " + username);

        loginPage.clickMaybeLater();
        loginPage.clickLoginButtonOnHome();
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginSubmit();

        page.waitForLoadState();
        System.out.println("✅ Login successful for: " + username);
    }
}
