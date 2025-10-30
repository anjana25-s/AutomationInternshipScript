package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.FeedbackPopupScript;
import com.automation.scripts.HomePageScript;
import com.automation.scripts.LoginPageScript;
import org.testng.Assert;
import org.testng.annotations.*;

public class FeedbackLoginTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePageScript homePageScript;
    private LoginPageScript loginPageScript;
    private FeedbackPopupScript feedbackPopupScript;

    @BeforeClass
    public void setup() {
        // Initialize Playwright and browser
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500));
        page = browser.newPage();

        // Initialize script objects
        homePageScript = new HomePageScript(page);
        loginPageScript = new LoginPageScript(page);
        feedbackPopupScript = new FeedbackPopupScript(page);

        // Navigate to the application
        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 1] Navigated to Promilo");
    }
@Test
public void feedbackLoginTest() {
    // Step 1: Dismiss Maybe Later popup
    homePageScript.clickMaybeLater();

    // Step 2: Perform login
    loginPageScript.performLogin("testwork123@yopmail.com", "12345678");

    // Step 3: Navigate to Internships
    homePageScript.clickInternships();

    // Step 4: Select a specific internship
    homePageScript.selectInternship("Finance- Job role");

    // Step 5: Enter feedback
    feedbackPopupScript.getActions().enterFeedback("This is feedback");

    // Step 6: Submit feedback
    feedbackPopupScript.getActions().submitFeedback();
    

    // Step 8: Verify Thank You popup
    Assert.assertTrue(feedbackPopupScript.getActions().isThankYouDisplayed(), "Thank You popup not displayed!");
}
}

