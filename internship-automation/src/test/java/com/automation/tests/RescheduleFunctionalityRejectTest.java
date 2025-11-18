package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class RescheduleFunctionalityRejectTest extends BaseClass {
	 private HomepagePage homePage;
    private RescheduleFunctionalityPage reschedulePage;
    private AdvertiserPage advertiserPage;
    private LoginpagePage loginPage;

    // ------------------- Test Data -------------------
    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String ADVERTISER_URL = "https://stagebusiness.promilo.com/login";
    private static final String CANDIDATE_EMAIL = "9000017872";
    private static final String CANDIDATE_PASSWORD = "123456789";
    private static final String INTERNSHIP_NAME = "Designer1";
    private static final String ADVERTISER_EMAIL = "nidhiadvemailtesting@yopmail.com";
    private static final String ADVERTISER_PASSWORD = "promilo@123";

    // ------------------- Setup -------------------
    @BeforeClass
    public void initPages() {
    	homePage = new HomepagePage(page);
        reschedulePage = new RescheduleFunctionalityPage(page);
        advertiserPage = new AdvertiserPage(page);
        loginPage = new LoginpagePage(page);
    }

    @BeforeMethod
    public void openCandidatePortal() {
        Reporter.log("[Step 1] Navigating to Candidate Portal: " + BASE_URL, true);
        page.navigate(BASE_URL);
        page.waitForLoadState();
        Assert.assertTrue(page.url().contains("promilo"), "❌ URL validation failed.");
    }

    // ------------------- Test -------------------
    @Test
    public void verifyCandidateToAdvertiserRescheduleCancelFlow() {
        try {
        
                // Step 2: Handle “May be later” modal
                if (homePage.getMaybeLaterBtn().isVisible()) {
                    homePage.getMaybeLaterBtn().click();
                    Reporter.log("[Step 2] Clicked 'May be later!' button.", true);
                }
            // ✅ Step 1: Candidate login
            Reporter.log("[Step 1] Performing candidate login...", true);
            loginPage.getLoginBtnOnHome().click();
            loginPage.getEmailInput().fill(CANDIDATE_EMAIL);
            loginPage.getPasswordInput().fill(CANDIDATE_PASSWORD);
            loginPage.getLoginSubmitBtn().click();
            page.waitForLoadState();
            Reporter.log("[Step 2] Candidate logged in successfully.", true);

            // ✅ Step 3: Open My Interest tab
            reschedulePage.getMyInterestTab().click();
            Reporter.log("[Step 3] Clicked on My Interest tab.", true);

            // ✅ Step 4: Select internship and click reschedule
            Locator internshipCard = reschedulePage.getInternshipCard(INTERNSHIP_NAME);
            internshipCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            reschedulePage.getCalendarIcon(INTERNSHIP_NAME).click();
            Reporter.log("[Step 4] Clicked on Reschedule icon.", true);

            // ✅ Step 5–9: Select next month, date, time, and confirm
            if (reschedulePage.getNextMonthButton().isVisible()) reschedulePage.getNextMonthButton().click();
            reschedulePage.getFirstActiveDate().click();
            reschedulePage.getFirstAvailableTimeSlot().click();
            reschedulePage.getContinueButton().click();
            Reporter.log("[Step 9] Reschedule confirmed.", true);

            // ✅ Step 10: Validate success popup
            Locator successPopup = page.locator("//div[contains(@class,'modal-body')]");
            successPopup.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
            Assert.assertTrue(successPopup.isVisible(), "❌ Reschedule popup not visible.");
            Reporter.log("[Step 10] Success popup verified.", true);

            // ✅ Step 11: Advertiser login
            page.navigate(ADVERTISER_URL);
            advertiserPage.getEmailInput().fill(ADVERTISER_EMAIL);
            advertiserPage.getPasswordInput().fill(ADVERTISER_PASSWORD);
            advertiserPage.getSignInButton().click();
            page.waitForLoadState();
            Reporter.log("[Step 11] Advertiser logged in successfully.", true);

            // ✅ Step 12–13: Navigate and cancel reschedule request
            advertiserPage.getMyAccountTab().click();
            advertiserPage.getMyProspectTab().click();
            advertiserPage.getInternshipsLink().click();
            advertiserPage.getRescheduleRequest().click();
            advertiserPage.getCancelRequestButton().click();
            Reporter.log("[Step 13] Clicked Cancel Request button.", true);

            advertiserPage.getRejectButton().click();
            Reporter.log("[Step 14] Clicked Reject button on popup.", true);

            Reporter.log("===== ✅ Full Reschedule Cancel Flow Completed Successfully =====", true);

        } catch (Exception e) {
            Reporter.log("❌ Test failed: " + e.getMessage(), true);
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}

