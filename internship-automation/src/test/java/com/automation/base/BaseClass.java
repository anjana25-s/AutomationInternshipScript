package com.automation.base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.Arrays;

public class BaseClass {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();

        // Launch browser in FULL screen size (static window)
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(200) // optional: slow for stability/visibility
                        .setArgs(Arrays.asList(
                                "--start-maximized",
                                "--window-size=1920,1080"
                        ))
        );

        // Context with NO viewport = NO resizing = NO fluctuation
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(null)        // ⭐ Key fix
        );

        page = context.newPage();
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}

