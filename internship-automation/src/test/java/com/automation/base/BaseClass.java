package com.automation.base;

import com.automation.utils.HelperUtility;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseClass {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected HelperUtility helper;

    // ================= SHARED TEST DATA =================
    public static String campaignName;
    public static String selectedLanguage;
    public static String selectedDate;
    public static String selectedTime;

    // ================= SCREENING STORAGE =================
    public static Map<String, String> screeningAnswers = new LinkedHashMap<>();

    // ✅ ADD THIS GETTER
    public HelperUtility getHelper() {
        return helper;
    }

    @BeforeMethod
    public void launchBrowser() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList(
                                "--start-maximized",
                                "--window-size=1920,1080"
                        ))
        );

        context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(null)
        );

        page = context.newPage();
        helper = new HelperUtility(page);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        try { if (page != null) page.close(); } catch (Exception ignored) {}
        try { if (context != null) context.close(); } catch (Exception ignored) {}
        try { if (browser != null) browser.close(); } catch (Exception ignored) {}
        try { if (playwright != null) playwright.close(); } catch (Exception ignored) {}
    }
}

