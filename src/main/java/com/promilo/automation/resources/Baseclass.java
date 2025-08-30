package com.promilo.automation.resources;

import com.microsoft.playwright.*;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Baseclass {

    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(Baseclass.class);

    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    protected static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    public Properties prop;

    public Page initializePlaywright() throws IOException {
        prop = new Properties();
        String path = System.getProperty("user.dir") + "/src/main/java/com/promilo/automation/resources/data.properties";
        FileInputStream fis = new FileInputStream(path);
        prop.load(fis);

        String browserName = prop.getProperty("browser", "chromium").toLowerCase();
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

        playwright.set(Playwright.create());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(headless);

        switch (browserName) {
            case "chrome":
            case "chromium":
                browser.set(playwright.get().chromium().launch(launchOptions));
                break;
            case "firefox":
                browser.set(playwright.get().firefox().launch(launchOptions));
                break;
            case "webkit":
                browser.set(playwright.get().webkit().launch(launchOptions));
                break;
            default:
                throw new RuntimeException("❌ Browser not supported: " + browserName);
        }

        context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(null)));
        page.set(context.get().newPage());

        // 🔄 No navigation here
        return page.get();
    }

    public void maximizeWindow() {
        Page currentPage = page.get();
        if (currentPage != null) {
            try {
                currentPage.setViewportSize(1920, 1080);
                log.info("✅ Window maximized using setViewportSize(1920, 1080).");
            } catch (Exception e) {
                log.error("❌ Failed to maximize window: {}", e.getMessage());
            }
        } else {
            throw new RuntimeException("❌ Page is not initialized. Cannot maximize window.");
        }
    }

    public void closePlaywright() {
        if (context.get() != null) {
            context.get().close();
            context.remove();
        }
        if (browser.get() != null) {
            browser.get().close();
            browser.remove();
        }
        if (playwright.get() != null) {
            playwright.get().close();
            playwright.remove();
        }
        if (page.get() != null) {
            page.remove();
        }
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getContext() {
        return context.get();
    }

    public static Browser getBrowser() {
        return browser.get();
    }

    public static Playwright getPlaywright() {
        return playwright.get();
    }
}
