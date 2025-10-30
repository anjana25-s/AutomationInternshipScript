package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class RegisterWithUsActions {

    private final Page page;
    private final RegisterWithUsLocators locators;

    public RegisterWithUsActions(Page page) {
        this.page = page;
        this.locators = new RegisterWithUsLocators(page);
    }

    // ------------------- Fill fields -------------------
    public void enterName(String name) {
        page.locator(locators.nameField).fill(name);
    }

    public void enterMobile(String mobile) {
        page.locator(locators.mobileField).fill(mobile);
    }

    public void enterEmail(String email) {
        page.locator(locators.emailField).fill(email);
    }

    public void enterPassword(String password) {
        page.locator(locators.passwordField).fill(password);
    }

    public void selectPreferredLocation(String location) {
        Locator dropdown = page.locator(locators.preferredLocationDropdown);
        dropdown.click();

        Locator option = page.locator(locators.locationOption(location));
        option.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        option.click();

        page.locator("body").click(); // Close dropdown
        page.waitForTimeout(300);
    }

    // ------------------- Industry -------------------
    public void selectIndustry(String industry) {
        Locator dropdown = page.locator(locators.industryDropdown);
        dropdown.click();

        Locator option = page.locator(locators.industryOption(industry));
        option.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        option.click();

        page.locator("body").click(); // Close dropdown
        page.waitForTimeout(300);
    }

    // ------------------- Register -------------------
    public void clickRegisterNow() {
        Locator button = page.locator(locators.registerButton);
        button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        button.click();
    }

    // ------------------- OTP -------------------
    public void fillOtp(String otp) {
        if (otp.length() != 4) throw new IllegalArgumentException("OTP must be 4 digits");

        Locator otpBoxes = page.locator(locators.otpInputBoxes);
        for (int i = 0; i < 4; i++) {
            otpBoxes.nth(i).fill(Character.toString(otp.charAt(i)));
        }
    }

    public void clickVerifyOtp() {
        Locator button = page.locator(locators.verifyOtpBtn);
        button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        button.click();
    }

    // ------------------- Thank You popup -------------------
    public boolean isThankYouPopupVisible() {
        return page.locator(locators.thankYouPopup).isVisible();
    }

    public void closeThankYouPopup() {
        Locator button = page.locator(locators.thankYouCloseBtn);
        button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        button.click();
    }
}




