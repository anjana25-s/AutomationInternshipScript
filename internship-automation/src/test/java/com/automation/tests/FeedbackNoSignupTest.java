package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.FeedbackPopupPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class FeedbackNoSignupTest extends BaseClass {

    private HomepagePage home;
    private FeedbackPopupPage feedback;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP = "Finance- Job role";
    private static final String FEEDBACK_TEXT = "Automated feedback for no-signup flow.";
    private static final String OTP = "9999";

    @BeforeClass
    public void init() {
        home = new HomepagePage(page);
        feedback = new FeedbackPopupPage(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openBaseUrl() {
        helper.log("[Step 1] Navigating to: " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close 'May be later' Popup");
        }

        Assert.assertTrue(page.url().contains("promilo"), "❌ URL is incorrect after navigation.");
    }

    @Test
    public void verifyFeedbackFlowWithoutSignup() {
        try {

            // ------------------ Step 2: Open Internships ------------------
            helper.safeClick(home.getInternshipsTab(), "Open Internships");

            Locator card = home.getInternshipCard(INTERNSHIP);
            helper.waitForVisible(card, "Internship Card");
            helper.scrollAndClick(card, "Open Internship");

            // ------------------ Step 3: Wait for Feedback Modal ------------------
            Locator feedbackModal = page.locator("div.Job-Feedback-modal");
            feedbackModal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(20000));
            helper.log("[Step 3] Feedback modal is visible.");

            // ------------------ Step 4: Fill Feedback ------------------
            helper.safeFill(feedback.getFeedbackTextarea(), FEEDBACK_TEXT, "Feedback Text");
            helper.safeClick(feedback.getFeedbackSubmitBtn(), "Submit Feedback");

            // ------------------ Step 5: Fill User Details ------------------
            String name = helper.generateRandomName();
            String mobile = helper.generateRandomPhone();
            String email = helper.generateEmailFromName(name);

            helper.safeFill(feedback.getNameField(), name, "Name Field");

            if (feedback.getMobileField().isEnabled()) {
                helper.safeFill(feedback.getMobileField(), mobile, "Mobile Field");
            } else {
                helper.log("[INFO] Mobile auto-filled. Skipping.");
            }

            helper.safeFill(feedback.getEmailField(), email, "Email Field");

            helper.safeClick(feedback.getPopupSubmitBtn(), "Submit User Details");

            // ------------------ Step 6: OTP MODAL ------------------
            Locator otpModal = page.locator("div[role='dialog']");
            otpModal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(15000));
            helper.log("[Step 6] OTP modal visible.");

            for (int i = 1; i <= 4; i++) {
                helper.safeFill(
                        feedback.getOtpInput(i),
                        OTP.substring(i - 1, i),
                        "OTP Digit " + i
                );
            }

            Locator verifyBtn = feedback.getOtpVerifyBtn();
            verifyBtn.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));
            Assert.assertTrue(verifyBtn.isEnabled(), "❌ Verify button should be enabled");

            helper.safeClick(verifyBtn, "Verify OTP");

            // ------------------ Step 7: Thank You Popup ------------------
            Locator thankYouPopup = feedback.getThankYouPopup();
            thankYouPopup.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(15000));

            Assert.assertTrue(thankYouPopup.isVisible(), "❌ Thank You popup not visible!");
            helper.log("[Step 7] Thank You popup verified successfully.");

            helper.safeClick(feedback.getThankYouCloseBtn(), "Close Thank You Popup");

            helper.log("🎉 FEEDBACK NO-SIGNUP FLOW COMPLETED SUCCESSFULLY 🎉");

        } catch (Exception e) {
            Reporter.log("❌ Test Failed: " + e.getMessage(), true);
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
