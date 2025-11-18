package com.automation.utils;

import com.automation.pages.LoginpagePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginUtility {

    private final Page page;
    private final LoginpagePage loginPage;
    private final int timeout = 15000;

    public LoginUtility(Page page) {
        this.page = page;
        this.loginPage = new LoginpagePage(page);
    }

    // ------------------------------------------------------------
    // 🔹 STEP 1: Open Login Popup
    // ------------------------------------------------------------
    private void openLoginWindow() {
        loginPage.getLoginBtnOnHome().waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(timeout)
        );
        loginPage.getLoginBtnOnHome().click();
    }

    // ------------------------------------------------------------
    // 🔹 PASSWORD LOGIN
    // ------------------------------------------------------------
    public void loginWithPassword(String emailOrMobile, String password) {

        openLoginWindow();

        loginPage.getEmailInput().waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout)
        );

        loginPage.getPasswordInput().waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout)
        );

        loginPage.getEmailInput().fill(emailOrMobile);
        loginPage.getPasswordInput().fill(password);

        loginPage.getLoginSubmitBtn().waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout)
        );

        loginPage.getLoginSubmitBtn().click();
    }

    // ------------------------------------------------------------
    // 🔹 OTP LOGIN
    // ------------------------------------------------------------
    public void loginWithOtp(String mobile, String otp) {

        openLoginWindow();

        loginPage.getEmailInput().waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout)
        );
        loginPage.getEmailInput().fill(mobile);

        loginPage.getLoginWithOtpBtn().click();

        loginPage.getOtpInput().waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout)
        );
        loginPage.getOtpInput().fill(otp);

        loginPage.getLoginSubmitBtn().click();
    }

    // ------------------------------------------------------------
    // 🔹 SUCCESS CHECK (NEW: By CSS/XPath Selector)
    // ------------------------------------------------------------
    public boolean isLoginSuccessful(String successSelector) {
        try {
            page.locator(successSelector).waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(20000)
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ------------------------------------------------------------
    // 🔹 SUCCESS CHECK (ROBUST) — profile icon or popup disappeared
    // ------------------------------------------------------------
    public boolean isFullyLoggedIn() {

        // Popup closed check
        boolean popupClosed;
        try {
            popupClosed = loginPage.getEmailInput().isHidden();
        } catch (Exception e) {
            popupClosed = true;
        }

        // Profile icon check
        boolean profileVisible;
        try {
            loginPage.getHeaderProfileImg().waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(25000)
            );
            profileVisible = true;
        } catch (Exception e) {
            profileVisible = false;
        }

        return popupClosed || profileVisible;
    }

    // ------------------------------------------------------------
    // 🔹 USER DOESN'T EXIST ERROR
    // ------------------------------------------------------------
    public String getUserDoesNotExistError() {
        try {
            loginPage.getUserDoesNotExistError().waitFor(
                    new Locator.WaitForOptions()
                            .setState(WaitForSelectorState.VISIBLE)
                            .setTimeout(5000)
            );
            return loginPage.getUserDoesNotExistError().innerText();
        } catch (Exception e) {
            return "";
        }
    }
}




