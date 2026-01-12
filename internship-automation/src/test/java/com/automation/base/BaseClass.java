package com.automation.base;

import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
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

    // ================= UI DATA VALIDATION =================
    public static String candidateName;
    public static String companyName;
    public static String location;
    public static String duration;

    // ================= RESCHEDULE DATA =================
    public static String rescheduledDate;
    public static String rescheduledTime;

    // ================= SCREENING STORAGE =================
    public static Map<String, String> screeningAnswers = new LinkedHashMap<>();

    // ================= HELPER =================
    public HelperUtility getHelper() {
        return helper;
    }

    // ================= BROWSER SETUP =================
    @BeforeMethod(alwaysRun = true)
    public void launchBrowser() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList(
                                "--start-maximized",
                                "--window-size=1920,1080",
                                "--disable-notifications"
                        ))
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(null)
                        .setGeolocation(12.9716, 77.5946) // Bangalore
                        .setPermissions(List.of("geolocation"))
        );

        page = context.newPage();
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();

        closeMilliIfPresent();
        closePreferenceModalIfPresent();
    }

    protected void closeMilliIfPresent() {

        page.addInitScript(
            "() => {" +
            "  const closeMilli = () => {" +
            "    const btn = document.querySelector('.chatbot-header img[src*=\"closeMiliIcon\"]');" +
            "    if (btn) btn.click();" +
            "  };" +

            "  closeMilli();" +

            "  const observer = new MutationObserver(() => closeMilli());" +
            "  observer.observe(document.body, { childList: true, subtree: true });" +
            "}"
        );

        helper.log("✅ Milli auto-close enabled");
    }



    
 // ================= PREFERENCE MODAL HANDLER =================
    protected void closePreferenceModalIfPresent() {

        try {
            HomepagePage home = new HomepagePage(page);

            
            home.getPreferenceModal()
                .waitFor(new Locator.WaitForOptions().setTimeout(4000));

            helper.log("Preference modal detected");

            if (home.getMaybeLaterBtn().isVisible()) {

                home.getMaybeLaterBtn().scrollIntoViewIfNeeded();
                home.getMaybeLaterBtn().click();

                helper.log("✅ Preference modal closed using 'May be later'");

            } else if (home.getPreferenceModalCloseIcon().isVisible()) {

                home.getPreferenceModalCloseIcon().click();
                helper.log("✅ Preference modal closed using close icon");

            }

        } catch (PlaywrightException e) {
            helper.log("ℹ️ Preference modal not present, continuing test");
        }
    }


    // ================= BROWSER TEARDOWN =================
    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {

        try { if (page != null) page.close(); } catch (Exception ignored) {}
        try { if (context != null) context.close(); } catch (Exception ignored) {}
        try { if (browser != null) browser.close(); } catch (Exception ignored) {}
        try { if (playwright != null) playwright.close(); } catch (Exception ignored) {}
    }
}
