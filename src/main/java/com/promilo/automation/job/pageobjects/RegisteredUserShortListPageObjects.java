package com.promilo.automation.job.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class RegisteredUserShortListPageObjects {

    private final Page page;

    public RegisteredUserShortListPageObjects(Page page) {
        this.page = page;
    }

    // ======================================================
    // POPUP TEXT VALIDATION
    // ======================================================
    public Locator shortListPopUpDescription() {
        return page.locator("[class='text-content']");
    }

    public Locator headerText() {
        return page.locator("[class='feature-content-header']");
    }

    public Locator whatsappNotificationText() {
        return page.locator("//label[text()='Enable updates & important information on Whatsapp']");
    }

    public Locator agreeText() {
        return page.locator("//p[text()='By proceeding ahead you expressly agree to the Promilo']");
    }

    // ======================================================
    // USER DETAILS
    // ======================================================
    public Locator mobileField() {
        return page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']");
    }

    // ======================================================
    // OTP PAGE TEXT
    // ======================================================
    public Locator otpPageDescription() {
        return page.locator("[class='col-6 askUs-content-container free-counselling']");
    }

    // OTP digit fields
    public Locator otpDigit(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }

    // OTP screen texts
    public Locator otpThanksText() {
        return page.locator("[class='text-primary mb-2 text-center']");
    }

    public Locator otpVerificationHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    public Locator otpSentText() {
        return page.locator("//p[@class=' text-center']");
    }

    public Locator otpCantFindText() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

    public Locator verifyAndProceedButton() {
        return page.locator("//button[text()='Verify & Proceed']");
    }

    // ======================================================
    // THANK YOU POPUP
    // ======================================================
    public Locator thankYouValidationText() {
        return page.locator("[class='ThankYou-content justify-content-center']");
    }

    public Locator thankYouPopupVisible() {
        return page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
    }

    // ======================================================
    // NAVIGATION
    // ======================================================
    public Locator myPreferenceLink() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("My Preference"));
    }
}
