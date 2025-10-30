package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.GetConnectedActions;

public class GetConnectedScript {

    private final GetConnectedActions actions;

    public GetConnectedScript(Page page) {
        this.actions = new GetConnectedActions(page);
    }

    public GetConnectedActions getActions() {
        return actions;
    }

    // Step 1: Open popup and fill form
    public void registerUserInPopup(
            String name,
            String mobile,
            String email,
            String industry,
            String password
    ) {
        actions.registerUserInPopupWithoutWaitingBackdrop(name, mobile, email, industry, password);
    }

    // Step 2: Enter OTP and verify
    public void enterOtpAndVerify(String otp) {
        actions.enterOtp(otp);
        actions.clickVerify();
    }

    // Step 3: Full registration and verification flow
    public void registerAndVerify(
            String name,
            String mobile,
            String email,
            String industry,
            String password,
            String otp
    ) {
        registerUserInPopup(name, mobile, email, industry, password);
        enterOtpAndVerify(otp);
    }
}


