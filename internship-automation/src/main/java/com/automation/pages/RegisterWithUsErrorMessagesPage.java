package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RegisterWithUsErrorMessagesPage {

    private final Page page;

    public RegisterWithUsErrorMessagesPage(Page page) {
        this.page = page;
    }

    // ---------- REQUIRED FIELD ERRORS ----------
    public Locator nameRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Name is required']");
    }

    public Locator mobileRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Mobile number is required']");
    }

    public Locator emailRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Email is required']");
    }

    public Locator preferredLocationRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Preferred location is required']");
    }

    public Locator industryRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Industry is required']");
    }

    public Locator passwordRequiredError() {
        return page.locator("//div[@class='text-danger' and text()='Password is required']");
    }

    // ---------- INVALID VALUE ERRORS ----------
    public Locator invalidNameError() {
        return page.locator("//div[@class='text-danger' and contains(text(),'Invalid User Name')]");
    }

    public Locator invalidMobileError() {
        return page.locator("//div[@class='text-danger' and contains(text(),'Invalid Mobile number')]");
    }

    public Locator invalidEmailError() {
        return page.locator("//div[@class='text-danger' and text()='Invalid email address']");
    }

    public Locator passwordMinError() {
        return page.locator("//div[@class='text-danger' and text()='Password must be at least 8 characters']");
    }

    public Locator passwordMaxError() {
        return page.locator("//div[@class='text-danger' and text()='Password must be less than 15 characters']");
    }

    // ---------- OTP ----------
    public Locator invalidOtpToast() {
        return page.locator("//div[@role='status' and contains(text(),'Invalid OTP')]");
    }

    // ⭐ THIS WAS MISSING — REQUIRED BY YOUR TEST
    public Locator verifyBtn() {
        return page.locator("//button[contains(text(),'Verify') or contains(text(),'Proceed')]");
    }

    // ---------- ALREADY REGISTERED ----------
    public Locator emailAlreadyRegisteredToast() {
        return page.locator("//div[@role='status' and contains(text(),'already registered')]");
    }
}


