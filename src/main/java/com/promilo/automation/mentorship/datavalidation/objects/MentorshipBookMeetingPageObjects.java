package com.promilo.automation.mentorship.datavalidation.objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorshipBookMeetingPageObjects {

    private final Page page;

    public MentorshipBookMeetingPageObjects(Page page) {
        this.page = page;
    }

    public Locator header() {
        return page.locator("[class='feature-content-header']");
    }

    public Locator registerWithUsText() {
        return page.locator("[class='text-content']");
    }

    public Locator termsAndConditionsText() {
        return page.locator("[class='text-center pt-2']");
    }

    public Locator loginLinkText() {
        return page.locator("[class='text-center']");
    }

    public Locator otpBanner(int index) {
        return page.locator("[class='otp-banner-info mb-2']").nth(index);
    }

    public Locator otpThankYouText() {
        return page.locator("[class='text-primary mb-2 text-center']");
    }

    public Locator otpHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    public Locator otpDescription() {
        return page.locator("//p[@class=' text-center']");
    }

    public Locator otpStillCannotFind() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

    public Locator verifyAndProceedButton() {
        return page.locator("//button[text()='Verify & Proceed']");
    }

    public Locator mentorshipLabel() {
        return page.locator("[class='font-bold font-18 mb-[5px]']");
    }

    public Locator calendarPopupDescription() {
        return page.locator("[class='text-center mx-1']");
    }

    public Locator chooseSlotText() {
        return page.locator("[class='fw-500 font-18 text-primary mb-50']");
    }

    public Locator currentMonth() {
        return page.locator("[class='flatpickr-current-month']");
    }

    public Locator availableDate() {
        return page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
    }

    public Locator firstTimeSlot() {
        return page.locator("//li[@class='time-slot-box list-group-item']").first();
    }

    public Locator thankYouPopup() {
        return page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
    }

    public Locator thankYouMessage() {
        return page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 ']");
    }
}
