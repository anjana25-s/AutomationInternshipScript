package com.automation.tests;

import com.automation.scripts.HomePageScript;
import com.automation.scripts.LoginPageScript;
import com.automation.pages.RescheduleFunctionalityActions;
import com.automation.pages.AdvertiserPageActions;
import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class RescheduleFunctionalityTest {
    private Playwright playwright;
    private Browser browser;

    private Page candidatePage;
    private Page advertiserPage;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        // Candidate browser context
        candidatePage = browser.newContext().newPage();

        // Advertiser browser context
        advertiserPage = browser.newContext().newPage();
    }

    @Test
    public void testCandidateToAdvertiserRescheduleFlow() {
        // ========== Candidate Side ==========
        candidatePage.navigate("https://stage.promilo.com/login");

        LoginPageScript candidateLogin = new LoginPageScript(candidatePage);
        candidateLogin.performLogin("9000017878", "12345678");

        HomePageScript homePageScript = new HomePageScript(candidatePage);
        homePageScript.clickMaybeLater();

        RescheduleFunctionalityActions rescheduleActions = new RescheduleFunctionalityActions(candidatePage);
        rescheduleActions.clickMyInterests()
                         .clickCalendarIconForInternship("softwaretester");

        System.out.println("[Candidate] Reschedule request submitted!");

        // ========== Advertiser Side ==========
        advertiserPage.navigate("https://stagebusiness.promilo.com/login");

        // Advertiser login (simplified without a LoginPageScript, you can make one if needed)
        advertiserPage.locator("#login-email").fill("advertiser@example.com");
        advertiserPage.locator("input[placeholder='Enter Password']").fill("password123");
        advertiserPage.locator("button.sign-in-btn").click();

        AdvertiserPageActions advertiser = new AdvertiserPageActions(advertiserPage);
        advertiser.goToMyProspect()
                  .clickRescheduleForUser("Candidate Name")   // pass the dynamic candidate name
                  .acceptReschedule();

        System.out.println("[Advertiser] Reschedule request accepted!");
    }

    @AfterClass
    public void teardown() {
        browser.close();
        playwright.close();
    }
}

