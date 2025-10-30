package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPageActions {

    private final Page page;

    // Locators
    private final Locator loginBtnOnHome;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginSubmitBtn;

    public LoginPageActions(Page page) {
        this.page = page;
        this.loginBtnOnHome = page.locator("//div[text()='Login']");
        this.emailInput = page.locator("input[placeholder='Enter Email Or Mobile No.']");
        this.passwordInput = page.locator("input[placeholder='Enter Password']");
        this.loginSubmitBtn = page.locator("//button[text()='Login']");
    }

    // Actions
    public void clickLoginButtonOnHome() {
        try {
            loginBtnOnHome.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            loginBtnOnHome.click();
            waitForAction();
            System.out.println("[LoginPage] Clicked Login button on home.");
        } catch (PlaywrightException e) {
            System.out.println("[LoginPage] Exception clicking login button: " + e.getMessage());
        }
    }

    public void enterEmail(String email) {
        try {
            emailInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            emailInput.fill(email);
            waitForAction();
            System.out.println("[LoginPage] Entered email.");
        } catch (PlaywrightException e) {
            System.out.println("[LoginPage] Exception entering email: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            passwordInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            passwordInput.fill(password);
            waitForAction();
            System.out.println("[LoginPage] Entered password.");
        } catch (PlaywrightException e) {
            System.out.println("[LoginPage] Exception entering password: " + e.getMessage());
        }
    }

    public void clickLoginSubmit() {
        try {
            loginSubmitBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            loginSubmitBtn.click();
            waitForAction();
            System.out.println("[LoginPage] Clicked Submit button.");
        } catch (PlaywrightException e) {
            System.out.println("[LoginPage] Exception clicking submit: " + e.getMessage());
        }
    }

    private void waitForAction() {
        page.waitForTimeout(2000); // small wait for stability
    }

    // Complete login flow for convenience
    public void login(String email, String password) {
        clickLoginButtonOnHome();
        enterEmail(email);
        enterPassword(password);
        clickLoginSubmit();
    }
}


