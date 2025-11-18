package com.automation.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Reporter;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class HelperUtility {

    private final Page page;
    private final int timeout = 15000;

    public HelperUtility(Page page) {
        this.page = page;
    }

    // ------------------------------------------------------------
    // ⭐ SCREENSHOT UTILITY (usable everywhere)
    // ------------------------------------------------------------
    public void takeScreenshot(String stepName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "screenshots/" + stepName + "_" + timestamp + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(fileName))
                    .setFullPage(true));

            Reporter.log("📷 Screenshot: " + fileName, true);

        } catch (Exception e) {
            Reporter.log("⚠ Screenshot failed: " + stepName, true);
        }
    }

    // ------------------------------------------------------------
    // ⭐ SAFE CLICK (for any button, link, icon)
    // ------------------------------------------------------------
    public void safeClick(Locator locator, String stepName) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));
            locator.click();

            Reporter.log("[Click] " + stepName + " ✓", true);
            takeScreenshot(stepName);

        } catch (Exception e) {
            Reporter.log("[Click FAILED] " + stepName, true);
            takeScreenshot(stepName + "_FAILED");
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ⭐ SAFE FILL (for any input field)
    // ------------------------------------------------------------
    public void safeFill(Locator locator, String value, String field) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));
            locator.fill(value);

            Reporter.log("[Fill] " + field + " = " + value, true);
            takeScreenshot("Fill_" + field);

        } catch (Exception e) {
            Reporter.log("[Fill FAILED] " + field, true);
            takeScreenshot("Fill_" + field + "_FAILED");
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ⭐ SCROLL + CLICK (for long pages, carousels)
    // ------------------------------------------------------------
    public void scrollAndClick(Locator locator, String stepName) {
        try {
            // Scroll first time
            locator.scrollIntoViewIfNeeded();
            page.waitForTimeout(500);

            // Scroll second time (ensures carousel items come into view)
            locator.scrollIntoViewIfNeeded();
            page.waitForTimeout(300);

            // Try normal click
            locator.click(new Locator.ClickOptions().setTimeout(timeout));

            Reporter.log("[Click] " + stepName + " ✓", true);
            takeScreenshot(stepName);

        } catch (Exception e1) {
            Reporter.log("⚠ Normal click failed. Trying JS click...", true);

            try {
                // JS Click fallback
                page.evaluate("el => el.click()", locator.elementHandle());
                Reporter.log("[JS Click] " + stepName + " ✓", true);
                takeScreenshot(stepName + "_JS");

            } catch (Exception e2) {
                Reporter.log("[Click FAILED] " + stepName, true);
                takeScreenshot(stepName + "_FAILED");
                throw e2;
            }
        }
    }

    
    // ------------------------------------------------------------
    // ⭐ WAIT FOR ANY ELEMENT
    // ------------------------------------------------------------
    public void waitForVisible(Locator locator, String elementName) {
        Reporter.log("[Wait] " + elementName, true);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
    }

    // ------------------------------------------------------------
    // ⭐ UNIVERSAL RANDOM NAME → “Riya J”
    // ------------------------------------------------------------
    public String generateRandomName() {

        String[] firstNames = {
                "Riya", "Kavya", "Megh", "Anu", "Ishani",
                "Sana", "Diya", "Nisha", "Tara", "Aaradhya",
                "Mira", "Jhanvi", "Kiara", "Ritu", "Devika"
        };

        String firstName = firstNames[new Random().nextInt(firstNames.length)];

        char initial = (char) ('A' + new Random().nextInt(26));

        String name = firstName + " " + initial;

        Reporter.log("[Generated Name] " + name, true);
        return name;
    }

    // ------------------------------------------------------------
    // ⭐ UNIVERSAL RANDOM EMAIL → test<name><3digits>@yopmail.com
    // ------------------------------------------------------------
    public String generateEmailFromName(String name) {

        String cleaned = name.replace(" ", "").toLowerCase(); // Riya J → riyaj

        int digits = 100 + new Random().nextInt(900); // 3 digits

        String email = "test" + cleaned + digits + "@yopmail.com";

        Reporter.log("[Generated Email] " + email, true);
        return email;
    }

    // ------------------------------------------------------------
    // ⭐ OPTIONAL PHONE GENERATOR (if needed anywhere)
    // ------------------------------------------------------------
    public String generateRandomPhone() {
        int four = 1000 + new Random().nextInt(9000);
        String phone = "900001" + four;

        Reporter.log("[Generated Phone] " + phone, true);
        return phone;
    }

    // ------------------------------------------------------------
    // ⭐ LOG MESSAGE
    // ------------------------------------------------------------
    public void log(String message) {
        Reporter.log(message, true);
    }
}

