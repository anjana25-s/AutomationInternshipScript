package com.promilo.automation.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateAccountPage {

    private final Page page;
    
    

    //phone or mail text field
    private final Locator phoneMailTextField;
    
    //send verification button
    private final Locator sendCodeButton;
    
    //OTP text field
    private final Locator otpField;
    
    //password Text field
    private final Locator passwordField;
    private final Locator submitButton;
    private final Locator existingUserToast;
    private final Locator invalidString;

    public CreateAccountPage(Page page) {
        this.page = page;

        this.phoneMailTextField = page.locator("//input[@id='signup-email']");
        this.sendCodeButton = page.locator("//button[text()='Send Verification Code']");
        this.otpField = page.locator("//input[@class='otp-input form-control']");
        this.passwordField = page.locator("//input[@placeholder='Enter Password']");
        this.submitButton = page.locator("//button[@class='signup-btn font-14 fw-bold border-0 btn btn-primary d-block w-100']");
        this.existingUserToast = page.locator("//div[@role='status']");
        this.invalidString = page.locator("//p[@class='text-danger font-12']");
    }

    public Locator phoneMailTextField() {
        return phoneMailTextField;
    }

    public Locator sendCodeButton() {
        return sendCodeButton;
    }

    public Locator otpField() {
        return otpField;
    }

    public Locator passwordField() {
        return passwordField;
    }

    public Locator submitButton() {
        return submitButton;
    }

    public Locator existingUserToast() {
        return existingUserToast;
    }

    public Locator invalidString() {
        return invalidString;
    }
}
