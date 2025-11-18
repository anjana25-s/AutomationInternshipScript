package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.Reporter;

public class ApplyNowErrorMessagesPage {

    private final Page page;

    public ApplyNowErrorMessagesPage(Page page) {
        this.page = page;
    }

    // --------------------------- INTERNAL ASSERT METHODS ---------------------------
    private void assertVisible(String exactText) {
        Locator el = page.locator("//div[@class='text-danger' and normalize-space(text())='" + exactText + "']");
        el.waitFor();
        Assert.assertTrue(el.isVisible(), "Missing: " + exactText);
        Reporter.log("[VALIDATION ✓] " + exactText, true);
    }

    private void assertContains(String partial) {
        Locator el = page.locator("//div[contains(@class,'text-danger') and contains(text(),'" + partial + "')]");
        el.waitFor();
        Assert.assertTrue(el.isVisible(), "Missing: " + partial);
        Reporter.log("[VALIDATION ✓] " + partial, true);
    }

    // --------------------------- REQUIRED FIELDS ---------------------------
    public void validateRequiredErrors() {
        assertVisible("Name is required");
        assertVisible("Mobile number is required");
        assertVisible("Email is required");
        assertVisible("Industry is required");
    }

    // --------------------------- NAME ERRORS ---------------------------
    public void validateNameMin() {
        assertVisible("Must be 3 characters or greater");
    }

    public void validateNameInvalid() {
        assertContains("Invalid User Name");
    }

    public void validateNameMax() {
        assertVisible("Must be 50 characters or less");
    }

    // --------------------------- MOBILE ---------------------------
    public void validateInvalidMobile() {
        assertVisible("Invalid Mobile number, must be exactly 10 digits");
    }

    // --------------------------- EMAIL ---------------------------
    public void validateInvalidEmail() {
        assertVisible("Invalid email address");
    }

    // --------------------------- OTP ERRORS ---------------------------
    public Locator getVerifyBtn() {
        return page.locator("//button[contains(text(),'Verify') or contains(text(),'Proceed')]");
    }

    public void validateVerifyBtnDisabled() {
        Assert.assertTrue(getVerifyBtn().isDisabled(), "Verify button SHOULD be disabled!");
        Reporter.log("[VALIDATION ✓] Verify button is DISABLED", true);
    }

    public void validateVerifyBtnEnabled() {
        Assert.assertFalse(getVerifyBtn().isDisabled(), "Verify button SHOULD be enabled!");
        Reporter.log("[VALIDATION ✓] Verify button is ENABLED", true);
    }

    public void validateInvalidOtp() {
        Locator el = page.locator("//div[@role='status' and contains(text(),'Invalid OTP')]");
        el.waitFor();
        Assert.assertTrue(el.isVisible(), "Invalid OTP not displayed!");
        Reporter.log("[VALIDATION ✓] Invalid OTP message displayed", true);
    }
}
