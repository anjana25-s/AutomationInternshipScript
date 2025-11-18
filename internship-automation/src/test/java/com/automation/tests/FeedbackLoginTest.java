package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class FeedbackLoginTest extends BaseClass {

    private HomepagePage home;
    private LoginpagePage login;
    private FeedbackPopupPage feedback;
    private HelperUtility helper;

    // ------------------- Test Data -------------------
    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String LOGIN_EMAIL = "testwork123@yopmail.com";
    private static final String LOGIN_PASSWORD = "12345678";
    private static final String INTERNSHIP = "Designer1";
    private static final String FEEDBACK_TEXT = "Feedback after login flow.";

    // ------------------- Setup -------------------
    @BeforeClass
    public void initPages() {
        home = new HomepagePage(page);
        login = new LoginpagePage(page);
        feedback = new FeedbackPopupPage(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openBaseUrl() {
        helper.log("[Step 1] Navigating to " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();

        Assert.assertTrue(page.url().contains("promilo"), 
                "❌ URL mismatch. Current: " + page.url());

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close 'May be later' popup");
        }
    }

    // ------------------- Test -------------------
    @Test
    public void verifyFeedbackPopupAfterLogin() {
        try {
            // ⭐ Step 2 — Login
            helper.log("[Step 2] Starting Login Flow...");

            Locator loginBtn = page.locator("//div[@class='Login-button']");
            helper.waitForVisible(loginBtn, "Login Button");
            helper.safeClick(loginBtn, "Open Login Form");

            helper.safeFill(login.getEmailInput(), LOGIN_EMAIL, "Email");
            helper.safeFill(login.getPasswordInput(), LOGIN_PASSWORD, "Password");
            helper.safeClick(login.getLoginSubmitBtn(), "Submit Login");

            page.waitForLoadState();
            helper.log("[Step 2 ✓] Logged in successfully.");

            // ⭐ Step 3 — Open Internships
            helper.safeClick(home.getInternshipsTab(), "Open Internships Tab");

            Locator card = home.getInternshipCard(INTERNSHIP);
            helper.waitForVisible(card, "Internship Card");
            helper.scrollAndClick(card, "Open Internship = " + INTERNSHIP);

            // ⭐ Step 4 — Wait for Feedback Modal
            Locator feedbackModal = page.locator("div.Job-Feedback-modal");
            feedbackModal.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(20000));

            helper.log("[Step 4 ✓] Feedback modal displayed.");

            // ⭐ Step 5 — Enter Feedback
            helper.safeFill(feedback.getFeedbackTextarea(), FEEDBACK_TEXT, "Feedback");
            helper.safeClick(feedback.getFeedbackSubmitBtn(), "Submit Feedback Text");

            // ⭐ Step 6 — Validate Thank You Popup
            Locator thankYou = feedback.getThankYouPopup();
            thankYou.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(15000));

            Assert.assertTrue(thankYou.isVisible(), "❌ Thank You popup NOT visible!");
            helper.log("[Step 6 ✓] Thank You popup displayed.");

            // ⭐ Step 7 — Close Popup
            helper.safeClick(feedback.getThankYouCloseBtn(), "Close Thank You Popup");

            helper.log("🎉 FEEDBACK FLOW AFTER LOGIN — PASSED!");
            helper.takeScreenshot("Feedback_Login_Success");

        } catch (Exception e) {
            helper.takeScreenshot("Feedback_Login_Failed");
            Reporter.log("❌ Test failed: " + e.getMessage(), true);
            Assert.fail("Test failed due to: " + e.getMessage());
        }
    }
}



