package com.promilo.automation.resources;

import com.microsoft.playwright.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Baseclass {

    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
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
        String url = prop.getProperty("url");

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

        // Use null viewport to simulate maximizing the window
        context.set(browser.get().newContext(new Browser.NewContextOptions().setViewportSize(null)));
        page.set(context.get().newPage());
        page.get().navigate(url);

        return page.get();
    }

    /**
     * Maximizes the browser window to the screen's available width and height.
     * Works by retrieving screen dimensions using JS and resizing the viewport dynamically.
     */
    

    public void maximizeWindow() {
        Page currentPage = page.get();
        if (currentPage != null) {
            boolean maximized = false;

            // ---------- Method 1: Fixed 1920x1080 ----------
            try {
                currentPage.setViewportSize(1920, 1080);
                System.out.println("✅ Window maximized using Method 1: setViewportSize(1920, 1080).");
                maximized = true;
            } catch (Exception e) {
                System.out.println("⚠️ Method 1 failed: " + e.getMessage());
            }

            // ---------- Method 2: Using screen.availWidth/availHeight ----------
            if (!maximized) {
                try {
                    int width = ((Double) currentPage.evaluate("() => screen.availWidth")).intValue();
                    int height = ((Double) currentPage.evaluate("() => screen.availHeight")).intValue();
                    currentPage.setViewportSize(width, height);
                    System.out.println("✅ Window maximized using Method 2: screen.availWidth/availHeight.");
                    maximized = true;
                } catch (Exception e) {
                    System.out.println("⚠️ Method 2 failed: " + e.getMessage());
                }
            }

            // ---------- Method 3: Using window.moveTo and resizeTo ----------
            if (!maximized) {
                try {
                    currentPage.evaluate("() => { window.moveTo(0, 0); window.resizeTo(screen.availWidth, screen.availHeight); }");
                    System.out.println("✅ Window maximized using Method 3: window.moveTo + resizeTo.");
                    maximized = true;
                } catch (Exception e) {
                    System.out.println("⚠️ Method 3 failed: " + e.getMessage());
                }
            }

            if (!maximized) {
                System.out.println("❌ All maximize methods failed. Running with default window size.");
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
