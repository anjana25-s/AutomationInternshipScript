package com.promilo.automation.job.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NotifySimilarJobsPageObjects {

    private final Page page;

    public NotifySimilarJobsPageObjects(Page page) {
        this.page = page;
    }

    // Header text in notify popup
    public Locator headerText() {
        return page.locator("[class='feature-content-header']");
    }

    // Registration description text
    public Locator registerDescriptionText() {
        return page.locator("[class='text-content']");
    }

    // Whatsapp notification text
    public Locator whatsappNotificationText() {
        return page.locator("//label[text()='Enable updates & important information on Whatsapp']");
    }

    // Agreement text
    public Locator agreeText() {
        return page.locator("//p[text()='By proceeding ahead you expressly agree to the Promilo']");
    }

    // OTP description text
    public Locator otpPageDescription() {
        return page.locator("[class='col-6 askUs-content-container free-counselling']");
    }

    // OTP input field by index (1 to 4)
    public Locator otpDigit(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }

    // OTP thanks text
    public Locator otpThanksText() {
        return page.locator("[class='text-primary mb-2 text-center']");
    }

    // OTP Verification header
    public Locator otpVerificationHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    // OTP sent description
    public Locator otpSentText() {
        return page.locator("//p[@class=' text-center']");
    }

    // OTP can't find text
    public Locator otpCantFindText() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

    // Verify & Proceed button
    public Locator verifyAndProceedButton() {
        return page.locator("//button[text()='Verify & Proceed']");
    }

    // Thank You popup container
    public Locator thankYouPopup() {
        return page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='thank you!']");
    }

    // Thank You message text
    public Locator thankYouMessageText() {
        return page.locator("[class='ThankYou-content justify-content-center']");
    }
}
