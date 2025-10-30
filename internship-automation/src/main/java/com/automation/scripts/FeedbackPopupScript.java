package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.FeedbackPopupActions;

public class FeedbackPopupScript {

    private final FeedbackPopupActions actions;

    public FeedbackPopupScript(Page page) {
        this.actions = new FeedbackPopupActions(page);
    }

    public FeedbackPopupActions getActions() {
        return actions;
    }

    /**
     * Complete feedback flow with visible pauses
     * Works for both signed-up and not signed-up users.
     *
     * @param feedbackText Feedback message
     * @param name         User name
     * @param mobile       User mobile (empty if prefilled)
     * @param email        User email (empty if prefilled)
     * @param otp          OTP value (empty string to skip)
     * @return true if Thank You popup appears
     */
    public boolean feedbackFlow(String feedbackText, String name,
                                String mobile, String email, String otp) {

        // 1️⃣ Enter feedback
        actions.enterFeedback(feedbackText);
        pause(1000); // small pause to see it happen

        // 2️⃣ Submit feedback
        actions.submitFeedback();
        pause(1000);

        // 3️⃣ Fill user details if provided
        if ((name != null && !name.isEmpty()) ||
            (mobile != null && !mobile.isEmpty()) ||
            (email != null && !email.isEmpty())) {

            actions.fillUserDetails(
                name != null ? name : "",
                mobile != null ? mobile : "",
                email != null ? email : ""
            );
            pause(1000);

            actions.clickUserDetailsSubmit();
            pause(1000);
        }

        // 4️⃣ Verify OTP if provided
        if (otp != null && !otp.isEmpty()) {
            actions.verifyOtp(otp);
            pause(1000);
        }

        // 5️⃣ Check Thank You popup
        boolean displayed = actions.isThankYouDisplayed();
        if (displayed) {
            System.out.println("[FeedbackPopupScript] Thank You popup displayed");
        } else {
            System.out.println("[FeedbackPopupScript] Thank You popup NOT displayed");
        }

        // Optional: final pause to see popup
        pause(1500);

        return displayed;
    }

    // Simple pause utility (ms)
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



