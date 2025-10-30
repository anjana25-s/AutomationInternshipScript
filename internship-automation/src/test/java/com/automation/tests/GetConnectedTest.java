package com.automation.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.automation.scripts.GetConnectedScript;
import com.automation.scripts.HomePageScript;
import org.testng.Assert;
import org.testng.annotations.*;

public class GetConnectedTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    private HomePageScript homePageScript;
    private GetConnectedScript getConnectedScript;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(300));
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();

        homePageScript = new HomePageScript(page);
        getConnectedScript = new GetConnectedScript(page);

        page.navigate("https://stage.promilo.com");
        System.out.println("[Step 1] Navigated to Promilo");
    }

    @Test(description = "Get Connected flow with OTP verification")
    public void testGetConnectedFlow() {
        // Dismiss Modal if present
        homePageScript.clickMaybeLater();
        System.out.println("[Step 2] Dismissed 'Maybe Later' modal if appeared");

        // Click on 'Internships' tab
        homePageScript.clickInternships();
        System.out.println("[Step 3] Clicked on 'Internships' tab");

        // Select an internship card
        homePageScript.selectInternship("Finance- Job role");
        System.out.println("[Step 4] Selected internship: Finance- Job role");

        // Wait until 'Get Connected' button is visible
        page.waitForSelector("//button[contains(text(),'Get Connected')]",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        System.out.println("[Step 5] 'Get Connected' button is visible");

        // Click 'Get Connected' button
        getConnectedScript.getActions().clickGetConnected();
        System.out.println("[Step 6] Clicked 'Get Connected'");

        // Fill form and verify OTP
        getConnectedScript.registerAndVerify(
                "Meghana",
                "9000011480",
                "testgetconnected@yopmail.com",
                "Animation & VFX",
                "Test@1234",
                "9999"
        );
        System.out.println("[Step 7] Completed Get Connected registration and verification");

        // Verify Thank You popup
        Assert.assertTrue(getConnectedScript.getActions().isThankYouDisplayed(),
                "❌ Thank You popup not displayed after Get Connected registration!");
        System.out.println("[Step 8] Thank You popup displayed successfully");
    }

    @AfterMethod
    public void closeContext() {
        if (context != null) context.close();
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("[Teardown] Browser and Playwright closed");
    }
}
