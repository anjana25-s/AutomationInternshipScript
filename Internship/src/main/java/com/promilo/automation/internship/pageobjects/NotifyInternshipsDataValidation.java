package com.promilo.automation.internship.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NotifyInternshipsDataValidation {

    private final Page page;

    public NotifyInternshipsDataValidation(Page page) {
        this.page = page;
    }

    // ===================== NOTIFY POPUP TEXT =====================

    public Locator headerText() {
        return page.locator("[class='feature-content-header']");
    }

    public Locator registerDescriptionText() {
        return page.locator("[class='text-content']");
    }

    public Locator whatsappNotificationText() {
        return page.locator("//label[text()='Enable updates & important information on Whatsapp']");
    }

    public Locator agreeText() {
        return page.locator("//p[text()='By proceeding ahead you expressly agree to the Promilo']");
    }

    // ===================== OTP PAGE =====================

    public Locator otpPageDescription() {
        return page.locator("[class='col-6 askUs-content-container free-counselling']");
    }

    public Locator otpInputField(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }

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

    // ===================== THANK YOU POPUP =====================

    public Locator thankYouPopup() {
        return page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='thank you!']");
    }

    public Locator thankYouMessageText() {
        return page.locator("[class='ThankYou-content justify-content-center']");
    }
}
