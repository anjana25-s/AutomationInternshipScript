package com.automation.tests;

import com.microsoft.playwright.*;
import com.automation.scripts.ApplyNowPopupScript;
import com.automation.scripts.HomePageScript;
import com.automation.scripts.LoginPageScript;
import org.testng.annotations.*;

public class ApplyNowLoginTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private HomePageScript homePageScript;
    private LoginPageScript loginPageScript;
    private ApplyNowPopupScript applyNowPopupScript;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(500));
        System.out.println("[Setup] Browser launched");
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();

        homePageScript = new HomePageScript(page);
        loginPageScript = new LoginPageScript(page);
        applyNowPopupScript = new ApplyNowPopupScript(page);

        page.navigate("https://stage.promilo.com");
        System.out.println("[Setup] Navigated to Promilo");
    }

    @Test(description = "Login → Apply Now flow using applyWithLogin() method")
    public void testApplyNowWithLogin() {
        // Dismiss modal if it appears
        homePageScript.clickMaybeLater();
        System.out.println("[Step] Dismissed 'Maybe Later' modal");

        // Perform login
        loginPageScript.performLogin("testwork123@yopmail.com", "12345678");
        System.out.println("[Step] Login performed");

        // Navigate to internships
        homePageScript.clickInternships();
        System.out.println("[Step] Clicked on 'Internships' tab");

        
        // Select internship
        homePageScript.selectInternship("Finance- Job role");
        System.out.println("[Step] Selected internship card: softwaretester");

        // Apply using login flow
        applyNowPopupScript.applyWithLogin("English", "September 23, 2025", "07:00 AM");
        System.out.println("[Step] Apply Now flow completed via applyWithLogin()");
    }

    @AfterMethod
    public void closeContext() {
        if (context != null) {
            context.close();
            System.out.println("[AfterMethod] Browser context closed");
        }
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
            System.out.println("[AfterClass] Browser closed");
        }
        if (playwright != null) {
            playwright.close();
            System.out.println("[AfterClass] Playwright closed");
        }
    }
}




