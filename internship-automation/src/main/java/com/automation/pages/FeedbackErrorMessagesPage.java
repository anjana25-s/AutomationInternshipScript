package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.Reporter;

public class FeedbackErrorMessagesPage {

    private final Page page;

    // Actual Ask Us popup container
    private final String base = "//div[contains(@class,'AskUs-Modal') and contains(@class,'show')]";

    public FeedbackErrorMessagesPage(Page page) {
        this.page = page;
    }

    // Generic assertion
    private void assertErrorVisible(String expectedText) {
        String xpath = base + "//div[contains(@class,'text-danger') and normalize-space()='" + expectedText + "']";

        Locator msg = page.locator(xpath);
        msg.waitFor();  // waits until visible

        Assert.assertTrue(msg.isVisible(),
                "❌ ERROR NOT FOUND: " + expectedText);

        Reporter.log("[ERROR FOUND] " + expectedText, true);
    }

    // ---------------- REQUIRED ERRORS ----------------
    public void assertRequiredErrorMessages() {
        assertErrorVisible("Name is required");
        assertErrorVisible("Mobile number is required");
        assertErrorVisible("Email is required");
        assertErrorVisible("Must be 3 characters or greater");
        assertErrorVisible("Invalid Mobile number, must be exactly 10 digits");
        assertErrorVisible("Invalid email address");
        assertErrorVisible("Must be 500 characters or less");
    }

    // ---------------- SPECIFIC FIELD VALIDATIONS ----------------
    public void assertNameMin() {
        assertErrorVisible("Must be 3 characters or greater");
    }

    public void assertNameInvalid() {
        assertErrorVisible("Invalid name");
    }

    public void assertNameMax() {
        assertErrorVisible("Must be 50 characters or less");
    }

    public void assertMobileInvalid() {
        assertErrorVisible("Invalid Mobile number, must be exactly 10 digits");
    }

    public void assertEmailInvalid() {
        assertErrorVisible("Invalid email address");
    }

    public void assertFeedbackMax() {
        assertErrorVisible("Must be 500 characters or less");
    }
}

