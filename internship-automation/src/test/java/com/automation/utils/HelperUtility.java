package com.automation.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Reporter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class HelperUtility {

    private final Page page;
    private final int timeout = 15000;
    private String lastStep = "";

    public HelperUtility(Page page) {
        this.page = page;
    }

    // ------------------------------------------------------------
    // STEP LOGGING
    // ------------------------------------------------------------
    public void step(String message) {
        lastStep = message;
        Reporter.log("➡️ STEP: " + message, true);
    }

    public void validate(String field, String expected, String actual) {
        Reporter.log("🔎 VALIDATE: " + field, true);
        Reporter.log("   Expected: " + expected, true);
        Reporter.log("   Actual  : " + actual, true);
    }

    public void pass(String message) {
        Reporter.log("✅ PASS: " + message, true);
    }

    public void fail(String message) {
        Reporter.log("❌ FAIL: " + message, true);
        Reporter.log("📍 Failed at step: " + lastStep, true);
        Reporter.log("📍 Current URL: " + page.url(), true);
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
                    .setFullPage(false));

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
            step(stepName);

            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.click();
            Reporter.log("[Click] " + stepName + " ✓", true);

        } catch (Exception e) {
            fail("Click failed: " + stepName);
            takeScreenshot("CLICK_FAIL_" + stepName);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // SAFE FILL
    // ------------------------------------------------------------
    public void safeFill(Locator locator, String value, String fieldName) {
        try {
            step("Fill " + fieldName);

            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.fill(value);
            Reporter.log("[Fill] " + fieldName + " = " + value, true);

        } catch (Exception e) {
            fail("Fill failed: " + fieldName);
            takeScreenshot("FILL_FAIL_" + fieldName);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // SCROLL + CLICK
    // ------------------------------------------------------------
    public void scrollAndClick(Locator locator, String stepName) {
        try {
            step(stepName);

            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));

            locator.scrollIntoViewIfNeeded();
            page.waitForTimeout(150);
            locator.click();

            Reporter.log("[Scroll Click] " + stepName + " ✓", true);

        } catch (Exception e) {
            fail("Scroll click failed: " + stepName);
            takeScreenshot("SCROLL_CLICK_FAIL_" + stepName);
            throw e;
        }
    }

    // ------------------------------------------------------------
    // TEXT NORMALIZER
    // ------------------------------------------------------------
    public String normalizeText(String text) {
        if (text == null) return "";
        return text
                .replace('\u00A0', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }

    // ------------------------------------------------------------
    // WAIT FOR ELEMENT
    // ------------------------------------------------------------
    public void waitForVisible(Locator locator, String elementName) {
        step("Wait for " + elementName);

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
            step("Assert visible: " + message);

            if (!locator.isVisible()) {
                fail(message);
                takeScreenshot("ASSERT_VISIBLE_FAIL_" + message);
                throw new AssertionError("❌ Not visible: " + message);
            }

            pass(message);

        } catch (Exception e) {
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ASSERT TRUE
    // ------------------------------------------------------------
    public void assertTrue(boolean condition, String message) {
        try {
            step("Assert true: " + message);

            if (!condition) {
                fail(message);
                takeScreenshot("ASSERT_TRUE_FAIL_" + message);
                throw new AssertionError("❌ Assertion failed: " + message);
            }

            pass(message);

        } catch (Exception e) {
            throw e;
        }
    }

    // ------------------------------------------------------------
    // ASSERT EQUALS (NULL SAFE)
    // ------------------------------------------------------------
    public void assertEquals(String actual, String expected, String message) {
        try {
            step("Assert equals: " + message);
            validate(message, expected, actual);

            if (actual == null || !actual.equals(expected)) {
                fail(message);
                takeScreenshot("ASSERT_EQUALS_FAIL_" + message);
                throw new AssertionError(
                        "❌ " + message + " | Expected: [" + expected + "] Found: [" + actual + "]"
                );
            }

            pass(message);

        } catch (Exception e) {
            throw e;
        }
    }
    
    public String normalizeUiText(String text) {
        return text
                .replace("\u00A0", " ")   // remove NBSP
                .replaceAll("\\s+", " ")  // collapse spaces
                .trim();
    }

    
 // ------------------------------------------------------------
 // DATE NORMALIZER (Advertiser → User format)
 // ------------------------------------------------------------
 public String normalizeAdvertiserDate(String uiDate) {
     try {
         // Example: "14 Jan 2026" → "14/01/2026"
         DateTimeFormatter advertiserFormat =
                 DateTimeFormatter.ofPattern("dd MMM yyyy");

         DateTimeFormatter expectedFormat =
                 DateTimeFormatter.ofPattern("dd/MM/yyyy");

         return LocalDate.parse(uiDate.trim(), advertiserFormat)
                 .format(expectedFormat);

     } catch (Exception e) {
         Reporter.log("⚠ Unable to normalize date: " + uiDate, true);
         return uiDate;
     }
 }
//------------------------------------------------------------
//TIME NORMALIZER (01:00 PM → 1:00 PM)
//------------------------------------------------------------
public String normalizeTime(String time) {
  if (time == null) return "";

  return time
          .replaceFirst("^0", "")   // remove leading zero
          .replaceAll("\\s+", " ")
          .trim();
}

    // ------------------------------------------------------------
    // ASSERT TOAST APPEARED
    // ------------------------------------------------------------
    public void assertToastAppeared(Locator locator, String message) {
        try {
            step("Assert toast: " + message);

            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED)
                    .setTimeout(3000));

            pass("Toast appeared: " + message);

        } catch (Exception e) {
            fail("Toast missing: " + message);
            takeScreenshot("ASSERT_TOAST_FAIL_" + message);
            throw new AssertionError("❌ Toast did not appear: " + message);
        }
    }
}


