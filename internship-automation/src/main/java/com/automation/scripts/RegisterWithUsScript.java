package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.RegisterWithUsActions;

public class RegisterWithUsScript {

    private final RegisterWithUsActions actions;

    public RegisterWithUsScript(Page page) {
        this.actions = new RegisterWithUsActions(page);
    }

    /**
     * Complete registration flow:
     * 1. Fill all fields: Name, Mobile, Email, Password
     * 2. Select Preferred Location
     * 3. Select Industry
     * 4. Click Register Now
     * 5. Fill OTP and Verify
     * 6. Close Thank You popup
     */
    public void completeRegisterFlow(
            String name,
            String mobile,
            String email,
            String location,
            String industry,
            String password,
            String otp
    ) {
        // Fill basic info
        actions.enterName(name);
        actions.enterMobile(mobile);
        actions.enterEmail(email);
        actions.enterPassword(password);

        // Select dropdowns
        actions.selectPreferredLocation(location);
        actions.selectIndustry(industry);

        // Click Register Now
        actions.clickRegisterNow();

        // Fill OTP (assumes 4 digits)
        actions.fillOtp(otp);

        // Click Verify & Proceed
        actions.clickVerifyOtp();

        // Close Thank You popup if visible
        if (actions.isThankYouPopupVisible()) {
            actions.closeThankYouPopup();
        }
    }
}
