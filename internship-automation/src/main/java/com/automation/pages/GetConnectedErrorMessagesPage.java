package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetConnectedErrorMessagesPage {

    private final Page page;

    public GetConnectedErrorMessagesPage(Page page) {
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

    public Locator passwordRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Password is required']");
    }

    // ---------- Name Errors ----------
    public Locator nameInvalidError() {
        return page.locator("//div[contains(@class,'text-danger') and contains(text(),'Invalid User Name')]");
    }

    public Locator nameMinError() {
        return page.locator("//div[@class='text-danger' and text()='Must be 3 characters or greater']");
    }

    public Locator nameMaxError() {
        return page.locator("//div[@class='text-danger' and text()='Must be 50 characters or less']");
    }

    // ---------- Mobile Invalid ----------
    public Locator mobileInvalidError() {
        return page.locator("//div[@class='text-danger' and contains(text(),'Invalid Mobile')]");
    }

    // ---------- Email Errors ----------
    public Locator emailInvalidError() {
        return page.locator("//div[@class='text-danger' and text()='Invalid email address']");
    }

    // ---------- Password Errors ----------
    public Locator passwordMinError() {
        return page.locator("//div[@class='text-danger' and text()='Must be 8 characters or greater']");
    }

    public Locator passwordMaxError() {
        return page.locator("//div[@class='text-danger' and text()='Must be 15 characters or less']");
    }

    // ---------- OTP ----------
    public Locator verifyBtn() {
        return page.locator("//button[contains(text(),'Verify') or contains(text(),'Proceed')]");
    }

    public Locator invalidOtpToast() {
        return page.locator("//div[@role='status' and contains(., 'Invalid OTP')]");
    }

    // ---------- Already Registered Email ----------
    public Locator emailAlreadyRegisteredToast() {
        return page.locator("//div[@role='status' and contains(., 'registered')]");
    }
}



