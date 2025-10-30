package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.HomePageScript;
import com.automation.scripts.RegisterWithUsScript;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterWithUsTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePageScript homePageScript;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500));
        page = browser.newPage();

        homePageScript = new HomePageScript(page);
        System.out.println("[Step 1] Playwright initialized and browser launched");
    }

    @Test
    public void testRegisterWithUs() {
        // Step 2: Navigate to the website
        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 2] Navigated to Promilo");

        // Step 3: Dismiss preference modal if present
        homePageScript.clickMaybeLater();
        System.out.println("[Step 3] Dismissed 'Maybe Later' modal if appeared");

        // Step 4: Click on Internships tab
        homePageScript.clickInternships();
        System.out.println("[Step 4] Clicked on 'Internships' tab");

        // Step 5: Initialize RegisterWithUsScript and complete registration
        RegisterWithUsScript registerScript = new RegisterWithUsScript(page);
        System.out.println("[Step 5] Initialized registration script");

        // Step 6: Fill registration details and submit
        registerScript.completeRegisterFlow(
                "John Doe",              // Name
                "9000019278",            // Mobile
                "johw4jwjsn@example.com",  // Email
                "Ahmedabad",             // Preferred Location
                "Animation & VFX",       // Industry
                "SecurePass123",         // Password
                "9999"                   // OTP
        );
        System.out.println("[Step 6] Completed registration with user details");

        // Step 7: Wait for final confirmation (optional but helpful)
        page.waitForTimeout(2000);
        System.out.println("[Step 7] Waited for confirmation");
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("[Teardown] Browser and Playwright closed");
    }
}

