package com.automation.pages;

import com.microsoft.playwright.Page;

public class GetConnectedLocators {

    private final Page page;

    public GetConnectedLocators(Page page) {
        this.page = page;
    }

    // Popup wrapper
    public final String popupWrapper = "//div[contains(@class,'askUs-feature-container')]";
    public final String getConnectedBtn = "//button[contains(text(),'Get Connected')]";

    // Input fields inside popup
    public final String userName = popupWrapper + "//input[@name='userName']";
    public final String userMobile = popupWrapper + "//input[@name='userMobile']";
    public final String userEmail = popupWrapper + "//input[@name='userEmail']";
    public final String password = popupWrapper + "//input[@name='password']";

    // Industry dropdown and options
    public final String industryDropdown = popupWrapper + "//div[@id='industry-dropdown']";
    public final String industryOption(String industry) {
        return "//label[text()='" + industry + "']";
    }

    // Buttons
    public final String registerBtn = popupWrapper + "//button[text()='Register']";

    // OTP and verification
    public final String otpInput(int index) {
        return popupWrapper + "//input[@aria-label='Please enter OTP character " + index + "']";
    }
    public final String verifyBtn = popupWrapper + "//button[text()='Verify & Proceed']";

    // Thank You popup
    public final String thankYouPopup = "//div[contains(@class,'ThankYouPopup-Modal')]";
}
