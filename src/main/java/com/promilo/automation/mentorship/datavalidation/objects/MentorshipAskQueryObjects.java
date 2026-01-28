package com.promilo.automation.mentorship.datavalidation.objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.FrameLocator;

public class MentorshipAskQueryObjects {

    private final Page page;

    public MentorshipAskQueryObjects(Page page) {
        this.page = page;
    }

    // -------------------- Mentor Description & Ask Query --------------------
    public Locator registerWithUsText() {
        return page.locator("[class='text-content']");
    }

    public Locator headerText() {
        return page.locator("[class='feature-content-header']");
    }

    public Locator text2() {
        return page.locator("[class='d-none d-md-block ']");
    }

    public Locator text3() {
        return page.locator("[class='text-center pt-2']");
    }

    public Locator text4() {
        return page.locator("[class='text-center']");
    }

    public Locator askYourQueryButton() {
        return page.locator("//button[normalize-space()='Ask Your Query']").nth(3);
    }

    public Locator otpBannerFirst() {
        return page.locator("[class='otp-banner-info mb-2']").first();
    }

    public Locator otpBannerSecond() {
        return page.locator("[class='otp-banner-info mb-2']").nth(1);
    }

    public Locator otpBannerThird() {
        return page.locator("[class='otp-banner-info mb-2']").nth(2);
    }

    public Locator otpThankYouText() {
        return page.locator("[class='text-primary mb-2 text-center']");
    }

    public Locator otpVerificationHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    public Locator otpVerificationText() {
        return page.locator("//p[@class=' text-center']");
    }

    public Locator otpVerificationStillNotFound() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

    public Locator verifyAndProceedButton() {
        return page.locator("//button[normalize-space()='Verify & Proceed']");
    }

    public Locator askQueryDescription() {
        return page.locator("[class='otp-banner-info mb-2 pt-2 align-items-start']");
    }

    public Locator shareYourQueryText() {
        return page.locator("[class='title mb-50']");
    }

    public Locator noteText() {
        return page.locator("//span[text()='To get your query answered process we request you to pay the amount']");
    }

    public Locator thankYouPopup() {
        return page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
    }

    public Locator thankYouMessage() {
        return page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 ']");
    }

    // -------------------- Payment iframe --------------------
    public FrameLocator paymentFrame() {
        return page.frameLocator("iframe");
    }

    public Locator phonePeWalletOption(FrameLocator frame) {
        return frame.getByTestId("nav-sidebar").locator("div")
                .filter(new Locator.FilterOptions().setHasText("Wallet")).nth(2);
    }

    public Locator phonePeScreenContainer(FrameLocator frame) {
        return frame.getByTestId("screen-container").locator("div")
                .filter(new Locator.FilterOptions().setHasText("PhonePe")).nth(2);
    }
}
