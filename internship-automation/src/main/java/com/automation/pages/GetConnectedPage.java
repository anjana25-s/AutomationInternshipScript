package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetConnectedPage {

    private final Page page;

    // ------------------- BUTTON -------------------
    private final String getConnectedBtn = "//button[text()='Get Connected']";

    // ------------------- FORM FIELDS -------------------
    private final String nameField = "(//input[@placeholder='Name*'])[2]";
    private final String mobileField = "(//input[@placeholder='Mobile*'])[2]";
    private final String emailField = "(//input[@placeholder='Email*'])[2]";
    private final String passwordField = "(//input[@placeholder='Create Password*'])[2]";

    // ------------------- INDUSTRY DROPDOWN -------------------
    // Same locator opens + closes dropdown
    private final String industryDropdown =
            "(//div[contains(@class,'modal') and contains(@class,'show')]//div[@id='industry-dropdown'])[1]";

    // All industry checkboxes inside dropdown
    private final String industryCheckboxes =
            "//div[contains(@class,'modal') and contains(@class,'show')]//input[@type='checkbox']";

    // ------------------- REGISTER BUTTON -------------------
    private final String registerBtn = "//button[text()='Register']";

    // ------------------- OTP -------------------
    private final String otpInputTemplate = "input[aria-label='Please enter OTP character %d']";
    private final String verifyOtpBtn = "//button[text()='Verify & Proceed']";

    public GetConnectedPage(Page page) {
        this.page = page;
    }

    // ------------------- GETTERS -------------------
    public Locator getGetConnectedBtn() { return page.locator(getConnectedBtn); }

    public Locator getNameField() { return page.locator(nameField); }
    public Locator getMobileField() { return page.locator(mobileField); }
    public Locator getEmailField() { return page.locator(emailField); }

    public Locator getIndustryDropdown() { return page.locator(industryDropdown); }
    public Locator getIndustryCheckboxes() { return page.locator(industryCheckboxes); }

    public Locator getPasswordField() { return page.locator(passwordField); }
    public Locator getRegisterBtn() { return page.locator(registerBtn); }

    public Locator getOtpInput(int index) {
        return page.locator(String.format(otpInputTemplate, index));
    }

    public Locator getVerifyOtpBtn() { return page.locator(verifyOtpBtn); }
}
