package com.promilo.automation.mentorship.datavalidation.objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BuyResourcesPageObjects {

    private final Page page;

    // ======================= PAGE OBJECT LOCATORS =======================

    public final Locator popupClose;
    public final Locator buyResourcesButton;
    public final Locator descriptionBannerText;
    public final Locator featureHeader;
    public final Locator whatsappToggleText;
    public final Locator proceedAgreementText;
    public final Locator alreadyHaveAccountText;

    // Form fields
    public final Locator userEmailField;
    public final Locator downloadResourceButton;

    // OTP banners
    public final Locator otpBanner1;
    public final Locator otpBanner2;
    public final Locator otpBanner3;

    // OTP Verification elements
    public final Locator otpThanksText;
    public final Locator otpVerificationHeader;
    public final Locator otpInstructionText;
    public final Locator otpStillCantFindText;
    public final Locator verifyProceedButton;

    // After OTP
    public final Locator descriptionAfterOtp;
    public final Locator noteText;

    // Payment "Thank You"
    public final Locator thankYouPopup;
    public final Locator congratulationsText;
    public final Locator footerInfoText;

    
    public BuyResourcesPageObjects(Page page) {
        this.page = page;

        // ======================= LOCATOR ASSIGNMENTS =======================

        popupClose = page.locator("[class='modal-content']");
        buyResourcesButton = page.locator("//button[contains(text(),'Buy Resources')]");

        descriptionBannerText = page.locator("[class='d-none d-md-block ']");
        featureHeader = page.locator("[class='feature-content-header']");
        whatsappToggleText = page.locator("[class='form-check-label']");
        proceedAgreementText = page.locator("//p[text()='By proceeding ahead you expressly agree to the Promilo']");
        alreadyHaveAccountText = page.locator("//span[text()='Already have an account?']");

        userEmailField = page.locator("//input[@id='userEmail']").nth(1);
        downloadResourceButton = page.locator("//button[contains(text(),'Download Resource')]");

        otpBanner1 = page.locator("[class='otp-banner-info mb-2']").nth(0);
        otpBanner2 = page.locator("[class='otp-banner-info mb-2']").nth(1);
        otpBanner3 = page.locator("[class='otp-banner-info mb-2']").nth(2);

        otpThanksText = page.locator("[class='text-primary mb-2 text-center']");
        otpVerificationHeader = page.locator("//h5[text()='OTP Verification']");
        otpInstructionText = page.locator("//p[@class=' text-center']");
        otpStillCantFindText = page.locator("//p[text()='Still can’t find the OTP?']");
        verifyProceedButton = page.locator("//button[text()='Verify & Proceed']");

        descriptionAfterOtp = page.locator("[class='description pt-1']");
        noteText = page.locator("[class='ask-query']");

        thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        congratulationsText = page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 w-100']");
        footerInfoText = page.locator("[class='text-center ThankYou-footer']");

        
    }
}
