package com.automation.tests;
import com.automation.base.BaseClass;
import com.automation.pages.*;
import com.automation.utils.LoginUtility;
import com.automation.utils.TestAccountSave;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class RescheduleFunctionalityAcceptTest extends BaseClass {

    private HomepagePage homePage;
    private RescheduleFunctionalityPage reschedulePage;
    private AdvertiserPage advertiserPage;
    private LoginUtility login;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String ADVERTISER_URL = "https://stagebusiness.promilo.com/login";

    private static final String ADVERTISER_EMAIL = "nidhiadvemailtesting@yopmail.com";
    private static final String ADVERTISER_PASSWORD = "promilo@123";

    private String internshipName; // Loaded dynamically

    @BeforeClass
    public void initPages() {
        homePage = new HomepagePage(page);
        reschedulePage = new RescheduleFunctionalityPage(page);
        advertiserPage = new AdvertiserPage(page);
        login = new LoginUtility(page);

        // Load internship name saved from ApplyNow test
        internshipName = TestAccountSave.loadAppliedInternship();

        if (internshipName == null) {
            throw new RuntimeException("❌ NO internship found — run ApplyNowEmailTest first.");
        }
    }

    @BeforeMethod
    public void openCandidatePortal() {
        page.navigate(BASE_URL);
        page.waitForLoadState();
        Assert.assertTrue(page.url().contains("promilo"), "❌ URL validation failed.");
    }

    @Test
    public void verifyCandidateToAdvertiserRescheduleFlow() {

        try {
            // Close popup
            if (homePage.getMaybeLaterBtn().isVisible()) {
                homePage.getMaybeLaterBtn().click();
            }

            // ⭐ LOGIN USING SAVED ACCOUNT
            Reporter.log("Logging in using saved ApplyNow account...", true);
            login.loginWithSavedAccount();

            // My Interest
            Locator myInterestTab = reschedulePage.getMyInterestTab();
            myInterestTab.waitFor();
            myInterestTab.click();

            // Internship card
            Locator internshipCard = reschedulePage.getInternshipCard(internshipName);
            internshipCard.waitFor();
            internshipCard.click();

            // Reschedule
            Locator rescheduleIcon = reschedulePage.getCalendarIcon(internshipName);
            rescheduleIcon.click();

            Locator activeDate = reschedulePage.getFirstActiveDate();
            activeDate.click();

            Locator timeSlot = reschedulePage.getFirstAvailableTimeSlot();
            timeSlot.click();

            reschedulePage.getContinueButton().click();

            // Success popup
            Locator successPopup = page.locator("//div[contains(@class,'modal-body')]");
            successPopup.waitFor();

            // Close popup
            successPopup.locator("//img[contains(@alt,'CloseIcon')]").click();

            // Advertiser Login
            page.navigate(ADVERTISER_URL);
            advertiserPage.getEmailInput().fill(ADVERTISER_EMAIL);
            advertiserPage.getPasswordInput().fill(ADVERTISER_PASSWORD);
            advertiserPage.getSignInButton().click();

            advertiserPage.getMyAccountTab().click();
            advertiserPage.getMyProspectTab().click();
            advertiserPage.getInternshipsLink().click();

            Locator req = advertiserPage.getRescheduleRequest();
            req.waitFor();
            req.click();

            advertiserPage.getAcceptRequestButton().click();
            advertiserPage.getDoneButton().click();

            Reporter.log("✔ Reschedule + Accept Flow Completed!", true);

        } catch (Exception e) {
            Assert.fail("❌ Test failed due to: " + e.getMessage());
        }
    }
}

