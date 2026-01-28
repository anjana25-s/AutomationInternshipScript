package com.promilo.automation.mentorship.datavalidation.objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetMentorCallPageValidationObjects {

    private final Page page;

    // ========== HEADER TEXTS ==========
    private final Locator featureContentHeader;
    private final Locator mentorInfoBlock;
    private final Locator termsText;
    private final Locator alreadyHaveAccount;

    // ========== OTP SCREEN ==========
    private final Locator otpBannerInfo1;
    private final Locator otpBannerInfo2;
    private final Locator otpBannerInfo3;

    private final Locator otpThanksForInfo;
    private final Locator otpVerificationTitle;
    private final Locator otpVerificationPara;
    private final Locator otpStillCantFindText;

    // ========== LANGUAGE SELECTION ==========
    private final Locator languageHeader;
    private final Locator languageSubHeader;
    private final Locator askQueryText;

    // ========== THANK YOU MESSAGE ==========
    private final Locator thankYouHeader;
    private final Locator thankYouMessageText;

    public GetMentorCallPageValidationObjects(Page page) {
        this.page = page;

        // ========= Initialize locators directly from test class =========
        // Section Header
        this.featureContentHeader = page.locator("[class='feature-content-header']");
        this.mentorInfoBlock = page.locator("[class='d-none d-md-block ']");
        this.termsText = page.locator("[class='text-center pt-2']");
        this.alreadyHaveAccount = page.locator("[class='text-center']");

        // OTP banners
        this.otpBannerInfo1 = page.locator("[class='otp-banner-info mb-2']").first();
        this.otpBannerInfo2 = page.locator("[class='otp-banner-info mb-2']").nth(1);
        this.otpBannerInfo3 = page.locator("[class='otp-banner-info mb-2']").nth(2);

        // OTP verification section
        this.otpThanksForInfo = page.locator("[class='text-primary mb-2 text-center']");
        this.otpVerificationTitle = page.locator("//h5[text()='OTP Verification']");
        this.otpVerificationPara = page.locator("//p[@class=' text-center']");
        this.otpStillCantFindText = page.locator("//p[text()='Still can’t find the OTP?']");

        // Language selection
        this.languageHeader = page.locator("[class='fw-500 font-18 text-primary mb-50']");
        this.languageSubHeader = page.locator("[class='text-center mx-1']");
        this.askQueryText = page.locator("[class='ask-query']");

        // Thank you page
        this.thankYouHeader = page.locator("//div[text()='Thank You!']");
        this.thankYouMessageText = page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 ']");
    }

    // ========== GETTERS ==========

    public Locator featureContentHeader() { return featureContentHeader; }
    public Locator mentorInfoBlock() { return mentorInfoBlock; }
    public Locator termsText() { return termsText; }
    public Locator alreadyHaveAccount() { return alreadyHaveAccount; }

    public Locator otpBanner1() { return otpBannerInfo1; }
    public Locator otpBanner2() { return otpBannerInfo2; }
    public Locator otpBanner3() { return otpBannerInfo3; }

    public Locator otpThanksForInfo() { return otpThanksForInfo; }
    public Locator otpVerificationTitle() { return otpVerificationTitle; }
    public Locator otpVerificationPara() { return otpVerificationPara; }
    public Locator otpStillCantFindText() { return otpStillCantFindText; }

    public Locator languageHeader() { return languageHeader; }
    public Locator languageSubHeader() { return languageSubHeader; }
    public Locator askQueryText() { return askQueryText; }

    public Locator thankYouHeader() { return thankYouHeader; }
    public Locator thankYouMessageText() { return thankYouMessageText; }
}
