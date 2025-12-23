package com.automation.utils;

import com.automation.pages.SignUpPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SignupUtility {

    private final Page page;
    private final SignUpPage signupPage;

    public SignupUtility(Page page) {
        this.page = page;
        this.signupPage = new SignUpPage(page);
    }

    // ------------------------------------------------------------
    // SIGNUP WITH EMAIL → SAVE ACCOUNT
    // ------------------------------------------------------------
    public void signupWithEmail(String email, String password, String otp) {

        System.out.println("🚀 Starting Signup Flow");

        signupPage.getInitialSignupButton().click();

        signupPage.getEmailOrPhoneInput().fill(email);
        signupPage.getSendVerificationCodeButton().click();

        signupPage.getOtpInput().fill(otp);
        signupPage.getPasswordInput().fill(password);

        signupPage.getFinalSignupButton().click();

        Locator internshipsTab =
                page.locator("//a[normalize-space()='Internships']");
        internshipsTab.waitFor();

        // ⭐ Save account for login tests
        TestAccountSave.saveAccount(email, password);

        System.out.println("✔ Signup successful & account saved");
    }
}
