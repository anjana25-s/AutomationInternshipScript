package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RegisterWithUsPage {

    private final Page page;

    // ---------- INPUT FIELDS ----------
    private final String nameField = "input#userName";
    private final String mobileField = "input#userMobile";
    private final String emailField = "input#userEmail";
    private final String passwordField = "input#password";

    // ---------- LOCATION DROPDOWN ----------
    private final String preferredLocationDropdown = "input#preferredLocation";

    // FIRST location option radio
    private final String firstLocationOption =
            "(//div[contains(@class,'option-section')]//input[@type='radio'])[1]";

    // ---------- INDUSTRY DROPDOWN ----------
    // This dropdown is NOT inside modal
    private final String industryDropdown = "//div[@id='industry-dropdown']";

    // All checkboxes inside industry dropdown
    private final String industryCheckboxes = "//input[@type='checkbox']";

    // ---------- REGISTER BUTTON ----------
    private final String registerNowButton = "//button[contains(text(),'Register Now')]";

    // ---------- OTP ----------
    private final String otpInputs = "//div[contains(@style,'display: flex')]//input";
    private final String verifyOtpButton = "//button[contains(text(),'Verify & Proceed')]";

    // ---------- THANK YOU ----------
    private final String thankYouPopup = "//div[contains(@class,'ThankYouPopup-Modal')]";
    private final String thankYouCloseButton = "//div[contains(@class,'ThankYouPopup-Modal')]//button";

    public RegisterWithUsPage(Page page) {
        this.page = page;
    }

    // ---------- GETTERS ----------
    public Locator getNameField() { return page.locator(nameField); }
    public Locator getMobileField() { return page.locator(mobileField); }
    public Locator getEmailField() { return page.locator(emailField); }
    public Locator getPasswordField() { return page.locator(passwordField); }

    public Locator getPreferredLocationDropdown() { return page.locator(preferredLocationDropdown); }
    public Locator getFirstLocationOption() { return page.locator(firstLocationOption); }

    public Locator getIndustryDropdown() { return page.locator(industryDropdown); }
    public Locator getIndustryCheckboxes() { return page.locator(industryCheckboxes); }

    public Locator getRegisterNowButton() { return page.locator(registerNowButton); }

    public Locator getOtpInputs() { return page.locator(otpInputs); }
    public Locator getVerifyOtpButton() { return page.locator(verifyOtpButton); }

    public Locator getThankYouPopup() { return page.locator(thankYouPopup); }
    public Locator getThankYouCloseButton() { return page.locator(thankYouCloseButton); }
}





