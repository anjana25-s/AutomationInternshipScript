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
    // SCREENSHOT (ONLY ON FAILURE)
    // ------------------------------------------------------------
    public String takeScreenshot(String stepName) {
        try {
            new java.io.File("screenshots").mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "screenshots/" + stepName + "_" + timestamp + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(fileName))
                    .setFullPage(false)
            );

            Reporter.log("📷 Screenshot saved: " + fileName, true);
            return fileName;

        } catch (Exception e) {
            Reporter.log("⚠ Screenshot failed: " + stepName, true);
            return null;
        }
    }

    // ------------------------------------------------------------
    // SAFE CLICK
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
    // SAFE FILL
    // ------------------------------------------------------------
    public void safeFill(Locator locator, String value, String fieldName) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.fill(value);
            Reporter.log("[Fill] " + fieldName + " = " + value, true);

        } catch (Exception e) {
            Reporter.log("[Fill FAILED] " + fieldName, true);
            takeScreenshot("Fill_" + fieldName + "_FAILED");
            throw e;
        }
    }

    // ------------------------------------------------------------
    // SCROLL + CLICK
    // ------------------------------------------------------------
    public void scrollAndClick(Locator locator, String stepName) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.scrollIntoViewIfNeeded();
            page.waitForTimeout(150);
            locator.click();

            Reporter.log("[Click] " + stepName + " ✓", true);

        } catch (Exception e) {
            Reporter.log("[Scroll Click FAILED] " + stepName, true);
            takeScreenshot(stepName + "_FAILED");
            throw e;
        }
    }
    
    public String normalizeText(String text) {
        return text
                .replace('\u00A0', ' ')   // remove non-breaking space
                .replaceAll("\\s+", " ")  // collapse extra spaces
                .trim();
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
    // RANDOM DATA GENERATORS
    // ------------------------------------------------------------
    public String generateRandomName() {
        String[] names = {"Riya", "Kavya", "Sana", "Diya", "Ritu", "Megha"};
        String name = names[new Random().nextInt(names.length)] + " "
                + (char) ('A' + new Random().nextInt(26));

        Reporter.log("[Generated Name] " + name, true);
        return name;
    }

    public String generateEmailFromName(String name) {
        String cleaned = name.replace(" ", "").toLowerCase();
        String email = "test" + cleaned + (100 + new Random().nextInt(900)) + "@yopmail.com";

        Reporter.log("[Generated Email] " + email, true);
        return email;
    }

    public String generateRandomPhone() {
        String phone = "900001" + (1000 + new Random().nextInt(9000));
        Reporter.log("[Generated Phone] " + phone, true);
        return phone;
    }

    // ------------------------------------------------------------
    // LOG WRAPPER
    // ------------------------------------------------------------
    public void log(String message) {
        Reporter.log(message, true);
    }

    // ------------------------------------------------------------
    // ASSERT VISIBLE
    // ------------------------------------------------------------
    public void assertVisible(Locator locator, String message) {
        try {
            if (!locator.isVisible()) {
                takeScreenshot("ASSERT_VISIBLE_FAIL_" + message);
                throw new AssertionError("❌ " + message);
            }
            Reporter.log("✔ " + message, true);

        } catch (Exception e) {
            takeScreenshot("ASSERT_VISIBLE_EXCEPTION_" + message);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ASSERT TRUE
    // ------------------------------------------------------------
    public void assertTrue(boolean condition, String message) {
        try {
            if (!condition) {
                takeScreenshot("ASSERT_TRUE_FAIL_" + message);
                throw new AssertionError("❌ " + message);
            }
            Reporter.log("✔ " + message, true);

        } catch (Exception e) {
            takeScreenshot("ASSERT_TRUE_EXCEPTION_" + message);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ASSERT EQUALS
    // ------------------------------------------------------------
    public void assertEquals(String actual, String expected, String message) {
        try {
            if (!actual.equals(expected)) {
                takeScreenshot("ASSERT_EQUALS_FAIL_" + message);
                throw new AssertionError(
                        "❌ " + message + " | Expected: " + expected + ", Found: " + actual
                );
            }
            Reporter.log("✔ " + message, true);

        } catch (Exception e) {
            takeScreenshot("ASSERT_EQUALS_EXCEPTION_" + message);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ASSERT TOAST APPEARED
    // ------------------------------------------------------------
    public void assertToastAppeared(Locator locator, String message) {
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED)
                    .setTimeout(3000)
            );

            Reporter.log("✔ Toast Appeared: " + message, true);

        } catch (Exception e) {
            takeScreenshot("ASSERT_TOAST_FAIL_" + message);
            throw new AssertionError("❌ Toast did not appear: " + message);
        }
    }
}

