package com.promilo.automation.mentorship.datavalidation.objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetMentorCallPageObjects {

    private final Page page;

    public GetMentorCallPageObjects(Page page) {
        this.page = page;
    }

    public Locator featureContentHeader() {
        return page.locator("[class='feature-content-header']");
    }

    public Locator mentorDescription() {
        return page.locator("[class='d-none d-md-block ']");
    }

    public Locator termsAndConditions() {
        return page.locator("[class='text-center pt-2']");
    }

    public Locator loginLink() {
        return page.locator("[class='text-center']");
    }

    public Locator otpBanner(int index) {
        return page.locator("[class='otp-banner-info mb-2']").nth(index);
    }

    public Locator otpSuccessMessage() {
        return page.locator("[class='text-primary mb-2 text-center']");
    }

    public Locator otpVerificationHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    public Locator otpInstructionText() {
        return page.locator("//p[@class=' text-center']");
    }

    public Locator otpTroubleshootText() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

    public Locator languageHeader() {
        return page.locator("[class='fw-500 font-18 text-primary mb-50']");
    }

    public Locator languageDescription() {
        return page.locator("[class='text-center mx-1']");
    }

    public Locator languageNote() {
        return page.locator("[class='ask-query']");
    }

    public Locator thankYouTitle() {
        return page.locator("//div[text()='Thank You!']");
    }

    public Locator thankYouMessage() {
        return page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 ']");
    }
}
