package com.promilo.automation.job.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

	
public class MailJobApplyPageObjects {

    private final Page page;

    public MailJobApplyPageObjects(Page page) {
        this.page = page;
    }

    // Search Jobs input
    public Locator searchJobsInput() {
        return page.locator("//input[@placeholder='Search Jobs']");
    }

    // Apply now (if anything relied on page.locator directly elsewhere you already have methods below)
    public Locator featureContentHeader() {
        return page.locator("[class='feature-content-header']");
    }

    public Locator registerText() {
        return page.locator("[class='text-content']");
    }

    public Locator whatsappNotificationLabel() {
        return page.locator("//label[text()='Enable updates & important information on Whatsapp']");
    }

    public Locator agreeText() {
        return page.locator("//p[text()='By proceeding ahead you expressly agree to the Promilo']");
    }

    public Locator healthcareOption() {
        return page.locator("//div[contains(text(),'Healthcare')]").nth(0);
    }

    public Locator industryOptions() {
        return page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
    }

    public Locator askUsPopupUserNameInput() {
        return page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']");
    }

    public Locator applyBtn() {
        return page.locator("//button[contains(@class,'submit-btm-askUs')]");
    }

    public Locator otpPageDescription() {
        return page.locator("[class='col-6 askUs-content-container free-counselling']");
    }

    public Locator otpDigitField(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }

    public Locator otpThanksForInformation() {
        return page.locator("[class='text-primary mb-2 text-center']");
    }

    public Locator otpVerificationHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    public Locator otpInstructionText() {
        return page.locator("//p[@class=' text-center']");
    }

    public Locator otpStillCantFind() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

    public Locator verifyAndProceedButtonByText() {
        return page.locator("//button[text()='Verify & Proceed']");
    }

    public Locator nextButtonPageText() {
        return page.locator("[class='otp-banner-info pt-2 mb-2 calendar-infobox']");
    }

    public Locator dateElement() {
        return page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
    }

    public Locator timeElement() {
        return page.locator("//li[@class='time-slot-box list-group-item']").first();
    }

    public Locator submitButtonNth1() {
        return page.locator("//button[text()='Submit']").nth(1);
    }

    public Locator thankYouContent() {
        return page.locator("[class='ThankYou-content justify-content-center']");
    }

    // If you need more access, return the base page to allow custom locators. But keep locators added here.
    public Page getPage() {
        return page;
    }
}
