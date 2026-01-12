package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ApplyNowErrorMessagesPage {

    private final Page page;

    public ApplyNowErrorMessagesPage(Page page) {
        this.page = page;
    }

    // ---------- Required Field Errors ----------
    public Locator nameRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Name is required']");
    }

    public Locator mobileRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Mobile number is required']");
    }

    public Locator emailRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Email is required']");
    }

    public Locator industryRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Industry is required']");
    }

    // ---------- Name Errors ----------
    public Locator nameMinError() {
        return page.locator("//div[@class='text-danger' and text()='Must be 3 characters or greater']");
    }

    public Locator nameInvalidError() {
        return page.locator("//div[contains(@class,'text-danger') and contains(text(),'Invalid User Name')]");
    }

    public Locator nameMaxError() {
        return page.locator("//div[@class='text-danger' and text()='Must be 50 characters or less']");
    }

    // ---------- Mobile ----------
    public Locator mobileInvalidError() {
        return page.locator("//div[@class='text-danger' and text()='Invalid Mobile number, must be exactly 10 digits']");
    }

    // ---------- Email ----------
    public Locator emailInvalidError() {
        return page.locator("//div[@class='text-danger' and text()='Invalid email address']");
    }

    // ---------- OTP ----------
    public Locator verifyBtn() {
        return page.locator("//button[contains(text(),'Verify') or contains(text(),'Proceed')]");
    }

    public Locator invalidOtpError() {
        return page.locator("//div[@role='status' and contains(text(),'Invalid OTP')]");
    }

    // ---------- Screening Mandatory ----------
    public Locator screeningMandatoryError() {
        return page.locator("//div[@role='status' and contains(text(),'Answer required questions')]");
    }

    // ---------- ⭐ ALREADY REGISTERED EMAIL TOAST ----------
    public Locator emailAlreadyRegisteredToast() {
        return page.locator("//div[@role='status' and contains(text(),'already registered')]");
    }
}

