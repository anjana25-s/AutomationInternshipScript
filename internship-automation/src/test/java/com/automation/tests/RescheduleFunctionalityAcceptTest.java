package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class RescheduleFunctionalityAcceptTest extends BaseClass {
	private HomepagePage homePage;
    private RescheduleFunctionalityPage reschedulePage;
    private AdvertiserPage advertiserPage;
    private LoginpagePage loginPage;

    // ------------------- Test Data -------------------
    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String ADVERTISER_URL = "https://stagebusiness.promilo.com/login";
    private static final String CANDIDATE_EMAIL = "testnidhi1234@yopmail.com";
    private static final String CANDIDATE_PASSWORD = "12345678";
    private static final String INTERNSHIP_NAME = "Designer0009";
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
        page.waitForSelector("body");
        Assert.assertTrue(page.url().contains("promilo"), "❌ URL validation failed.");
    }

    // ------------------- Test -------------------
    @Test
    public void verifyCandidateToAdvertiserRescheduleFlow() {
       
    try {
    	
            // Step 2: Handle “May be later” modal
            if (homePage.getMaybeLaterBtn().isVisible()) {
                homePage.getMaybeLaterBtn().click();
                Reporter.log("[Step 2] Clicked 'May be later!' button.", true);
            }
            // ✅ Step 1: Click on "Login" button
            Reporter.log("[Step 1] Clicking on Login button...", true);
            loginPage.getLoginBtnOnHome().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            loginPage.getLoginBtnOnHome().click();
            Reporter.log("[Step 1] Clicked on Login button successfully.", true);

            // ✅ Step 2: Perform candidate login
            Reporter.log("[Step 2] Logging in as Candidate...", true);
            loginPage.getEmailInput().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            loginPage.getEmailInput().fill(CANDIDATE_EMAIL);
            loginPage.getPasswordInput().fill(CANDIDATE_PASSWORD);
            loginPage.getLoginSubmitBtn().click();
            page.waitForLoadState();
            Reporter.log("[Step 2] ✅ Candidate logged in successfully.", true);

            // ✅ Step 3: Click on My Interest tab
            Locator myInterestTab = reschedulePage.getMyInterestTab();
            myInterestTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
            myInterestTab.click();
            Reporter.log("[Step 3] Clicked on My Interest tab.", true);

            // ✅ Step 4: Locate internship card
            Locator internshipCard = reschedulePage.getInternshipCard(INTERNSHIP_NAME);
            internshipCard.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
            Assert.assertTrue(internshipCard.isVisible(), "❌ Internship card not visible.");
            Reporter.log("[Step 4] Internship card located for: " + INTERNSHIP_NAME, true);

            // ✅ Step 5: Click on Reschedule icon
            Locator rescheduleIcon = reschedulePage.getCalendarIcon(INTERNSHIP_NAME);
            rescheduleIcon.scrollIntoViewIfNeeded();
            rescheduleIcon.click();
            Reporter.log("[Step 5] Clicked on Reschedule icon.", true);

            // ✅ Step 6: Go to next month if available
            Locator nextMonthBtn = reschedulePage.getNextMonthButton();
            if (nextMonthBtn.isVisible()) {
                nextMonthBtn.click();
                Reporter.log("[Step 6] Clicked Next Month button.", true);
            }

            // ✅ Step 7: Select first available date
            Locator activeDate = reschedulePage.getFirstActiveDate();
            activeDate.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
            activeDate.scrollIntoViewIfNeeded();
            activeDate.click();
            Reporter.log("[Step 7] Selected first active date.", true);

            // ✅ Step 8: Select first available time slot
            Locator timeSlot = reschedulePage.getFirstAvailableTimeSlot();
            timeSlot.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
            timeSlot.scrollIntoViewIfNeeded();
            timeSlot.click();
            Reporter.log("[Step 8] Selected first available time slot.", true);

            // ✅ Step 9: Click Continue
            Locator continueBtn = reschedulePage.getContinueButton();
            continueBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
            continueBtn.scrollIntoViewIfNeeded();
            continueBtn.click();
            Reporter.log("[Step 9] Clicked Continue to confirm reschedule.", true);

         // ✅ Step 10: Validate reschedule success popup
            Reporter.log("[Step 10] Waiting for Reschedule Success popup...", true);

            // Wait for popup to appear
            Locator successPopup = page.locator("//div[contains(@class,'modal-body')]");
            successPopup.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(15000));

            Assert.assertTrue(successPopup.isVisible(), "❌ Reschedule success popup not displayed.");
            Reporter.log("[Step 10] ✅ Reschedule success popup is visible.", true);

            // Validate success image is present
            //Locator successImage = successPopup.locator("//img[contains(@alt,'RescheduleSuccessMessage')]");
            //Assert.assertTrue(successImage.isVisible(), "❌ Reschedule success image not visible.");
            //Reporter.log("[Step 10] ✅ Success image displayed correctly.", true);

            // Validate confirmation text
            Locator successHeading = successPopup.locator("//div[contains(@class,'heading') and contains(text(),'Your request has been sent')]");
            Assert.assertTrue(successHeading.isVisible(), "❌ Success message text not visible.");

            String confirmationText = successHeading.innerText().trim();
            Reporter.log("[Step 10] ✅ Popup message: " + confirmationText, true);

            // Optional: Close popup if needed
            Locator closeBtn = successPopup.locator("//img[contains(@alt,'CloseIcon')]");
            if (closeBtn.isVisible()) {
                closeBtn.click();
                Reporter.log("[Step 11] Closed success popup.", true);
            }

            Reporter.log("[Step 11] ✅ Reschedule flow completed successfully (popup validated).", true);

            // ✅ Step 11: Navigate to advertiser portal and login
            Reporter.log("[Step 11] Navigating to advertiser portal: " + ADVERTISER_URL, true);
            page.navigate(ADVERTISER_URL);
            page.waitForSelector("//input[@id='login-email']", new Page.WaitForSelectorOptions().setTimeout(15000));
            advertiserPage.getEmailInput().fill(ADVERTISER_EMAIL);
            advertiserPage.getPasswordInput().fill(ADVERTISER_PASSWORD);
            advertiserPage.getSignInButton().click();
            page.waitForLoadState();
            Reporter.log("[Step 11] ✅ Advertiser logged in successfully.", true);

            // ✅ Step 12: Navigate to My Prospect > Internships
            advertiserPage.getMyAccountTab().click();
            advertiserPage.getMyProspectTab().click();
            advertiserPage.getInternshipsLink().click();
            page.waitForTimeout(3000);
            Reporter.log("[Step 12] Navigated to My Prospect > Internships.", true);

            // ✅ Step 13: Accept reschedule request
            Locator rescheduleReq = advertiserPage.getRescheduleRequest();
            rescheduleReq.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
            rescheduleReq.click();
            advertiserPage.getAcceptRequestButton().click();
            advertiserPage.getDoneButton().click();
            Reporter.log("[Step 13] ✅ Reschedule request accepted successfully.", true);

            Reporter.log("===== ✅ Full Reschedule Flow Completed Successfully (Candidate → Advertiser) =====", true);

        } catch (Exception e) {
            Reporter.log("❌ Test failed: " + e.getMessage(), true);
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
