package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.ApplyNowPopupActions;

public class ApplyNowPopupScript {

    private final ApplyNowPopupActions actions;

    public ApplyNowPopupScript(Page page) {
        this.actions = new ApplyNowPopupActions(page);
    }

    public ApplyNowPopupActions getActions() {
        return actions;
    }

    // =========================
    // Scenario 1: Signup through Email and apply
    // =========================
    public void applyWithEmailSignup(String name,
                                     String phone,
                                     String email,
                                     boolean isPhonePrefilled,
                                     String industry,
                                     String language,
                                     String date,
                                     String time,
                                     String otp,
                                     String password) {
        try {
            actions.clickApplyNowCardPage();
            waitFor(2000);

            // Fill user details based on whether phone is prefilled
            if (!isPhonePrefilled) {
                actions.fillUserDetails(name, phone, email, industry);
            } else {
                actions.fillUserDetails(name, "", email, industry);
            }
            waitFor(1500);

            actions.clickPopupApplyNow();
            waitFor(3000);

            actions.enterOtp(otp, false); // OTP required for email signup
            waitFor(1000);

            actions.clickVerifyAndProceed();
            waitFor(2000);

            // Unified slot + screening handling
            actions.bookSlotAndHandleScreening(language, date, time);
            waitFor(2000);

            System.out.println("[ApplyNowPopupScript] Apply Now with Email Signup flow completed successfully.");
        } catch (Exception e) {
            System.err.println("[ApplyNowPopupScript] ERROR during Email Signup apply: " + e.getMessage());
            throw e;
        }
    }

    // =========================
    // Scenario 2: Signup through Mobile and apply
    // =========================
    public void applyWithMobileSignup(String name,
                                      String phone,
                                      String email,
                                      boolean isEmailPrefilled,
                                      boolean isMobileSignup,
                                      String industry,
                                      String language,
                                      String date,
                                      String time,
                                      String otp) {
        try {
            actions.clickApplyNowCardPage();
            waitFor(2000);

            actions.fillUserDetails(name, phone, email, industry);
            waitFor(1500);

            actions.clickPopupApplyNow();
            waitFor(3000);

            if (!isMobileSignup) {
                actions.enterOtp(otp, !isEmailPrefilled);
                waitFor(1000);
                actions.clickVerifyAndProceed();
                waitFor(2000);
            } else {
                System.out.println("[ApplyNowPopupScript] Skipping OTP inside popup for mobile signup.");
            }

            // Unified slot + screening handling
            actions.bookSlotAndHandleScreening(language, date, time);
            waitFor(2000);

            System.out.println("[ApplyNowPopupScript] Apply Now with Mobile Signup flow completed successfully.");
        } catch (Exception e) {
            System.err.println("[ApplyNowPopupScript] ERROR during Mobile Signup apply: " + e.getMessage());
            throw e;
        }
    }

    // =========================
    // Scenario 3: Login and apply (no OTP inside popup)
    // =========================
    public void applyWithLogin(String language,
                               String date,
                               String time) {
        try {
            actions.clickApplyNowCardPage();
            waitFor(2000);

            // Unified slot + screening handling
            actions.bookSlotAndHandleScreening(language, date, time);
            waitFor(2000);

            System.out.println("[ApplyNowPopupScript] Apply Now with Login flow completed successfully.");
        } catch (Exception e) {
            System.err.println("[ApplyNowPopupScript] ERROR during Login apply: " + e.getMessage());
            throw e;
        }
    }

    // =========================
    // Scenario 4: Apply without signup (Guest user)
    // =========================
    public void applyWithoutSignup(String name,
                                   String emailOrPhone,
                                   String industry,
                                   String language,
                                   String date,
                                   String time,
                                   String otp) {
        try {
            actions.clickApplyNowCardPage();
            waitFor(2000);

            // Here we pass emailOrPhone as phone field (if email, then user can enter OTP)
            actions.fillUserDetails(name, emailOrPhone, "", industry); 
            waitFor(1500);

            actions.enterOtp(otp, false);
            waitFor(1000);

            actions.clickVerifyAndProceed();
            waitFor(2000);

            // Unified slot + screening handling
            actions.bookSlotAndHandleScreening(language, date, time);
            waitFor(2000);

            System.out.println("[ApplyNowPopupScript] Apply Now guest flow with OTP completed successfully.");
        } catch (Exception e) {
            System.err.println("[ApplyNowPopupScript] ERROR during Guest apply flow with OTP: " + e.getMessage());
            throw e;
        }
    }

    // =========================
    // Helper method for waiting
    // =========================
    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted during wait: " + e.getMessage());
        }
    }
}
