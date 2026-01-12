package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FeedbackErrorMessagesPage {

    private final Page page;

    public FeedbackErrorMessagesPage(Page page) {
        this.page = page;
    }

    // ---------------- REQUIRED ERRORS ----------------
    public Locator nameRequiredError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Name is required']");
    }

    public Locator mobileRequiredError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Mobile number is required']");
    }

    public Locator emailRequiredError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Email is required']");
    }

    // ---------------- NAME ERRORS ----------------
    public Locator nameMinError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Must be 3 characters or greater']");
    }

    public Locator nameInvalidError() {
        return page.locator("//div[contains(@class,'text-danger') and contains(text(),'Invalid User Name')]");
    }


    public Locator nameMaxError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Must be 50 characters or less']");
    }

    // ---------------- MOBILE ERRORS ----------------
    public Locator mobileInvalidError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Invalid Mobile number, must be exactly 10 digits']");
    }

    // ---------------- EMAIL ERRORS ----------------
    public Locator emailInvalidError() {
        return page.locator("//div[contains(@class,'text-danger') and text()='Invalid email address']");
    }

    

 // ---------------- TOASTS / GENERAL ERRORS ----------------
    public Locator emailAlreadyRegisteredToast() {
        return page.locator("//div[contains(text(),'already') and contains(text(),'registered')]");
    }

}
