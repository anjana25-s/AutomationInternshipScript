package com.automation.pages;

import com.microsoft.playwright.Page;

public class RegisterWithUsLocators {
    private final Page page;

    public RegisterWithUsLocators(Page page) {
        this.page = page;
    }

    // Input fields
    public String nameField = "input#userName";
    public String mobileField = "input#userMobile";
    public String emailField = "input#userEmail";
    public String preferredLocationDropdown = "input#preferredLocation";
    public String passwordField = "input#password";

    // Location option
    public String locationOption(String location) {
        return "//label[text()='" + location + "']";
    }

    // Industry dropdown
    public String industryDropdown = "div#industry-dropdown";
    public String industryOption(String industry) {
        return "//label[normalize-space(text())='" + industry + "']";
    }

    // Register button
    public String registerButton = "button.submit-btm-askUs:text('Register Now')";

    public String registerSectionHeader = "//h3[contains(text(),'Register With Us')]";

    // OTP
    public String otpInputBoxes = "div[style*='display: flex'] input";
 // Register button
    
    // Verify OTP button
    public String verifyOtpBtn = "button.submit-btm-askUs:text('Verify & Proceed')";

    // Thank You popup
    public String thankYouPopup = "div.ThankYouPopup-Modal";
    public String thankYouCloseBtn = "div.ThankYouPopup-Modal button";
}



