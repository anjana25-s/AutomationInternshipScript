package basetest;

import com.microsoft.playwright.*;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Baseclass {

    public Playwright playwright;
    public Browser browser;
    public BrowserContext context;
    public Page page;

    @BeforeMethod
    public void launchBrowser(ITestContext testContext) {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        context = browser.newContext(
        	    new Browser.NewContextOptions()
        	        .setGeolocation(12.9716, 77.5946)
        	        .setPermissions(java.util.List.of("geolocation"))
        	);

        page = context.newPage();

        testContext.setAttribute("page", page);

        page.navigate("https://stage.promilo.com");

        // ✅ CLOSE MILLI IF PRESENT
        closeMilliIfVisible();
    }

    /**
     * Closes Milli Assistant if it appears
     */
    public void closeMilliIfVisible() {
        Locator milliCloseBtn = page.locator(
                "//div[contains(@class,'chatbot-header')]//img[contains(@src,'closeMilliIcon')]"
        );

        try {
            milliCloseBtn.waitFor(
                    new Locator.WaitForOptions().setTimeout(5000)
            );

            if (milliCloseBtn.isVisible()) {
                milliCloseBtn.click();
                page.waitForTimeout(1000);
                System.out.println("✅ Milli Assistant closed successfully");
            }

        } catch (PlaywrightException e) {
            System.out.println("ℹ️ Milli Assistant not displayed");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
