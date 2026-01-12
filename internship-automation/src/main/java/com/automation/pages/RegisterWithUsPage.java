package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RegisterWithUsPage {

    private final Page page;

    public RegisterWithUsPage(Page page) {
        this.page = page;
    }

    // ================= BASIC DETAILS =================
    public Locator getNameField() {
        return page.locator("#userName");
    }

    public Locator getMobileField() {
        return page.locator("#userMobile");
    }

    public Locator getEmailField() {
        return page.locator("#userEmail");
    }

    public Locator getPasswordField() {
        return page.locator("#password");
    }

    // ================= LOCATION =================
    public Locator getPreferredLocationDropdown() {
        return page.locator("#preferredLocation");
    }

    public Locator getLocationSuggestions() {
        return page.locator(
                "//div[contains(@class,'dropdown')]//div[contains(@class,'option')]"
        );
    }

    // ================= INDUSTRY =================
    public Locator getIndustryDropdown() {
        return page.locator("#industry-dropdown");
    }

    public Locator getIndustryCheckboxes() {
        return page.locator(
                "//div[contains(@class,'ThreeLevelmulti-select-dropdown')]//input[@type='checkbox']"
        );
    }

    // ================= REGISTER =================
    public Locator getRegisterNowButton() {
        return page.locator("//button[normalize-space()='Register Now']");
    }

    // ================= OTP =================
    public Locator getOtpInputs() {
        return page.locator("//div[contains(@class,'otp-input')]//input");
    }

    public Locator getOtpThankYouHeader() {
        return page.locator("//h4[contains(text(),'Thanks')]");
    }

    public Locator getOtpVerificationTitle() {
        return page.locator("//h5[normalize-space()='OTP Verification']");
    }

    public Locator getOtpInstructionText() {
        return page.locator("//p[contains(text(),'4-digit verification code')]");
    }

    public Locator getOtpChannelText() {
        return page.locator("//span[normalize-space()='SMS']");
    }

    public Locator getCantFindOtpText() {
        return page.locator("//p[contains(text(),'can’t find the OTP')]");
    }

    public Locator getResendOtpText() {
        return page.locator("//p[contains(text(),'Resend OTP')]");
    }

    public Locator getVerifyOtpButton() {
        return page.locator("//button[normalize-space()='Verify & Proceed']");
    }

    // ================= THANK YOU =================
    public Locator getThankYouPopup() {
        return page.locator("//div[contains(@class,'ThankYouPopup-Modal')]");
    }

    public Locator getFinalThankYouTitle() {
        return page.locator(
                "//div[contains(@class,'ThankYouPopup-Modal')]//div[@class='headerText']"
        );
    }

    
    public Locator getFinalThankYouMessage() {
        return page.locator(
                "//div[contains(@class,'ThankYouPopup-Modal')]//p[normalize-space()!='']"
        ).first();
    }

    public Locator getFinalThankYouSubMessage() {
        return page.locator(
                "//div[contains(@class,'ThankYouPopup-Modal')]//div[contains(@class,'ThankYou-message-subDescriptionText')]"
        );
    }

    public Locator getThankYouCloseButton() {
        return page.locator(
                "//div[contains(@class,'ThankYouPopup-Modal')]//button"
        ).first();
    }
}


