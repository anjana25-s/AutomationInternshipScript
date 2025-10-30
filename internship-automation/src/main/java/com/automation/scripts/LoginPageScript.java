package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.LoginPageActions;

public class LoginPageScript {
    private final LoginPageActions loginActions;

    public LoginPageScript(Page page) {
        this.loginActions = new LoginPageActions(page);
    }

    public void performLogin(String email, String password) {
        loginActions.clickLoginButtonOnHome();
        loginActions.enterEmail(email);
        loginActions.enterPassword(password);
        loginActions.clickLoginSubmit();
        System.out.println("[LoginPageScript] Login executed successfully!");
    }
}



