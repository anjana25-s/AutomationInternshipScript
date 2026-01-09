package com.automation.utils;

import com.automation.pages.SignUpPage;
import com.microsoft.playwright.Page;

public class SignupUtility {

    private final Page page;
    private final SignUpPage signupPage;

    public SignupUtility(Page page) {
        this.page = page;
        this.signupPage = new SignUpPage(page);
    }

    // =====================================================
    // SIGNUP USING MOBILE → SAVE ACCOUNT
    // =====================================================
    public void signupWithMobile(String mobile, String password, String otp) {

        System.out.println("🚀 Starting mobile signup");

        signupPage.getInitialSignupButton().click();
        signupPage.getEmailOrPhoneInput().fill(mobile);
        signupPage.getSendVerificationCodeButton().click();

        signupPage.getOtpInput().fill(otp);
        signupPage.getPasswordInput().fill(password);
        signupPage.getFinalSignupButton().click();

        // wait till user is logged in
        page.waitForLoadState();
        page.waitForTimeout(2000);

        // ✅ Save for login tests
        TestAccountStore.save(mobile, password);

        System.out.println("✅ Signup successful & account saved: " + mobile);
    }
}


