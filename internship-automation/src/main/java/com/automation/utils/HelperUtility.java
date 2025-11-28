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
    // SCREENSHOT (VIEWPORT ONLY)
    // ------------------------------------------------------------
    public void takeScreenshot(String stepName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "screenshots/" + stepName + "_" + timestamp + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(fileName))
                    .setFullPage(false)   // ⭐ ONLY VIEWPORT
            );

            Reporter.log("📷 Screenshot: " + fileName, true);

        } catch (Exception e) {
            Reporter.log("⚠ Screenshot failed: " + stepName, true);
        }
    }

    // ------------------------------------------------------------
    // SAFE CLICK (Screenshot only on FAILURE)
    // ------------------------------------------------------------
    public void safeClick(Locator locator, String stepName) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.click();
            Reporter.log("[Click] " + stepName + " ✓", true);

        } catch (Exception e) {
            Reporter.log("[Click FAILED] " + stepName, true);
            takeScreenshot(stepName + "_FAILED");
            throw e;
        }
    }

    // ------------------------------------------------------------
    // SAFE FILL (Screenshot only on FAILURE)
    // ------------------------------------------------------------
    public void safeFill(Locator locator, String value, String field) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.fill(value);
            Reporter.log("[Fill] " + field + " = " + value, true);

        } catch (Exception e) {
            Reporter.log("[Fill FAILED] " + field, true);
            takeScreenshot("Fill_" + field + "_FAILED");
            throw e;
        }
    }

    // ------------------------------------------------------------
    // STABLE SCROLL + CLICK (Screenshot only on FAILURE)
    // ------------------------------------------------------------
    public void scrollAndClick(Locator locator, String stepName) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.scrollIntoViewIfNeeded();
            page.waitForTimeout(150);

            page.evaluate("el => el.scrollIntoView({behavior:'auto', block:'center'})",
                    locator.elementHandle());

            page.waitForTimeout(100);

            locator.click();
            Reporter.log("[Click] " + stepName + " ✓", true);

        } catch (Exception e1) {
            Reporter.log("⚠ Scroll click failed. Trying JS click...", true);

            try {
                page.evaluate("el => el.click()", locator.elementHandle());
                Reporter.log("[JS Click] " + stepName + " ✓", true);

            } catch (Exception e2) {
                Reporter.log("[Click FAILED] " + stepName, true);
                takeScreenshot(stepName + "_FAILED");
                throw e2;
            }
        }
    }

    // ------------------------------------------------------------
    // WAIT FOR ELEMENT
    // ------------------------------------------------------------
    public void waitForVisible(Locator locator, String elementName) {
        Reporter.log("[Wait] " + elementName, true);

        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
    }

    // ------------------------------------------------------------
    // STABILIZE UI
    // ------------------------------------------------------------
    public void stabilize() {
        page.waitForTimeout(120);
    }

    // ------------------------------------------------------------
    // RANDOM DATA GENERATORS
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

    public String generateEmailFromName(String name) {
        String cleaned = name.replace(" ", "").toLowerCase();
        int digits = 100 + new Random().nextInt(900);
        String email = "test" + cleaned + digits + "@yopmail.com";

        Reporter.log("[Generated Email] " + email, true);
        return email;
    }

    public String generateRandomPhone() {
        int four = 1000 + new Random().nextInt(9000);
        String phone = "900001" + four;

        Reporter.log("[Generated Phone] " + phone, true);
        return phone;
    }

    public void log(String message) {
        Reporter.log(message, true);
    }

    // ------------------------------------------------------------
    // ASSERTIONS (Screenshots ONLY on failure)
    // ------------------------------------------------------------
    public void assertVisible(Locator locator, String message) {
        try {
            if (!locator.isVisible()) {
                takeScreenshot("ASSERT_FAIL_visible_" + message);
                throw new AssertionError("❌ " + message);
            }
            Reporter.log("✔ " + message, true);
        } catch (Exception e) {
            takeScreenshot("ASSERT_EXCEPTION_visible_" + message);
            throw e;
        }
    }

    public void assertEquals(String actual, String expected, String message) {
        try {
            if (!actual.equals(expected)) {
                takeScreenshot("ASSERT_FAIL_equals_" + message);
                throw new AssertionError(
                        "❌ " + message + " | Expected: " + expected + ", Found: " + actual
                );
            }
            Reporter.log("✔ " + message, true);
        } catch (Exception e) {
            takeScreenshot("ASSERT_EXCEPTION_equals_" + message);
            throw e;
        }
    }

    public void assertTrue(boolean condition, String message) {
        try {
            if (!condition) {
                takeScreenshot("ASSERT_FAIL_true_" + message);
                throw new AssertionError("❌ " + message);
            }
            Reporter.log("✔ " + message, true);
        } catch (Exception e) {
            takeScreenshot("ASSERT_EXCEPTION_true_" + message);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // SPECIAL ASSERTION FOR TOASTS (ATTACHED instead of visible)
    // ------------------------------------------------------------
    public void assertToastAppeared(Locator locator, String message) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED) // ⭐ does NOT require visibility
                    .setTimeout(3000)
            );

            Reporter.log("✔ " + message, true);

        } catch (Exception e) {
            takeScreenshot("ASSERT_FAIL_TOAST_" + message);
            throw new AssertionError("❌ Toast not found: " + message);
        }
    }
}




