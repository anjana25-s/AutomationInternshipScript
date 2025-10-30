package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.FeedbackPopupScript;
import com.automation.scripts.HomePageScript;
import org.testng.Assert;
import org.testng.annotations.*;

public class FeedbackNoSignUpTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePageScript homePageScript;
    private FeedbackPopupScript feedbackPopupScript;

    @BeforeClass
    public void setup() {
        // 1️⃣ Initialize Playwright and browser
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false) // Important: see the browser
                        .setSlowMo(500)    // Optional: slow down actions for visibility
                );
        page = browser.newPage();

        // 2️⃣ Increase default timeout for slow-loading pages
        page.setDefaultTimeout(45000);

        // 3️⃣ Initialize scripts
        homePageScript = new HomePageScript(page);
        feedbackPopupScript = new FeedbackPopupScript(page);

        // 4️⃣ Navigate to the site
        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 1] Navigated to Promilo");
    }

    @Test
    public void feedbackFlow_UserNotSignedUp() {
        // 1️⃣ Close 'Maybe Later' modal if visible
        homePageScript.clickMaybeLater();
        System.out.println("[Step 2] Dismissed 'Maybe Later' modal if appeared");

        // 2️⃣ Click on Internships tab
        homePageScript.clickInternships();
        System.out.println("[Step 3] Clicked on 'Internships' tab");

        // 3️⃣ Select the internship card by title
        homePageScript.selectInternship("Finance- Job role");
        System.out.println("[Step 4] Selected internship card: Finance- Job role");

        // 4️⃣ Perform full feedback flow
        boolean isThankYouDisplayed = feedbackPopupScript.feedbackFlow(
                "Feedback from user not signed up",
                "Meghana",                 // Name
                "9000016369",               // Mobile
                "test0u1s0er@yopmail.com",  // Email
                "9999"                      // OTP
        );
        System.out.println("[Step 5] Completed feedback form submission");

        // 5️⃣ Assert Thank You popup
        Assert.assertTrue(isThankYouDisplayed, "Thank You popup not displayed!");
        System.out.println("[Step 6] ✅ Feedback flow executed successfully for user not signed up!");
    }

    @AfterClass
    public void teardown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("[Teardown] Browser and Playwright closed");
    }
}

