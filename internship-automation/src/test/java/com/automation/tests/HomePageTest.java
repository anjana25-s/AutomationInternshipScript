package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.HomePageScript;
import org.testng.annotations.*;

public class HomePageTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private HomePageScript homePageScript;

    @BeforeClass
    public void setUp() {
        // Initialize Playwright and browser with slowMo to watch steps
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(false)     // false = browser visible
                .setSlowMo(500)         // slows each action by 500ms
        );

        page = browser.newPage();

        // Increase timeout for slow-loading pages
        page.setDefaultTimeout(45000);

        // Initialize HomePageScript
        homePageScript = new HomePageScript(page);
    }

    @Test
    public void selectFinanceInternship() {
        // Step 1: Navigate to the website
        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 1] Navigated to Promilo");

        // Step 2: Close 'Maybe Later' modal if visible
        homePageScript.clickMaybeLater();
        System.out.println("[Step 2] Dismissed 'Maybe Later' modal if it appeared");

        // Step 3: Click on Internships tab
        homePageScript.clickInternships();
        System.out.println("[Step 3] Clicked on 'Internships' tab");

        // Step 4: Select the internship card by title
        homePageScript.selectInternship("Finance- Job role");
        System.out.println("[Step 4] Selected internship card: Finance- Job role");

        System.out.println("✅ Finance internship selection flow completed");
    }

    @AfterClass
    public void tearDown() {
        // Pause before closing to visually inspect final state
        try {
            Thread.sleep(3000); // 3 seconds pause
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close browser and Playwright
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}


