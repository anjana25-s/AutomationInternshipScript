package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SignupPage {
    private Page page;

    // Locators representing elements on the Signup Page
    private Locator maybeLater;
    private Locator internships;
    private Locator initialSignupButton;
    private Locator loginMailOrPhoneField;
    private Locator sendVerificationCode;
    private Locator emailOtpField;
    private Locator mobileOtpField;
    private Locator passwordField;
    private Locator finalSignupButton;
    private Locator successToaster;

    // Constructor → Initialize UI elements using locators
    public SignupPage(Page page) {
        this.page = page;
        // Optional dismiss button on popup
        this.maybeLater = page.locator("//button[text()='May be later!']");
        // Sign Up CTA available on homepage
        this.initialSignupButton = page.locator("(//div[contains(@class,'sign-up-button') and normalize-space(text())='Sign Up'])[last()]");
      // enter Email in input field for sign up
        this.loginMailOrPhoneField = page.locator("#signup-email");
      
        this.sendVerificationCode = page.locator("//button[text()='Send Verification Code']");
      this.emailOtpField = page.locator("//input[@placeholder='Enter OTP sent to email']");
     this.mobileOtpField=page.locator("//input[@placeholder='Enter OTP sent to mobile number']");
      
        this.passwordField = page.locator("input[placeholder='Enter Password']");
     // Final Sign Up button to register user
        this.finalSignupButton = page.locator("//button[text()='Sign Up']");
     // Toaster message shown after successful registration
        this.successToaster = page.locator("//div[text()='User registered successfully.']");
        // Internships Tab (navigation locator - optional)
        this.internships = page.locator("//a[text()='Internships']");
    }

    
    // Click "Maybe Later" on popup
    public void clickMaybeLater() {
        maybeLater.waitFor();
        maybeLater.click();
       page.waitForTimeout(2000); 
    
    }
    // Click initial Sign Up button from homepage
    public void clickInitialSignupButton() {
        initialSignupButton.click();
        page.waitForTimeout(2000); 
    }
 // Enter Email or Phone number into sign up field
    public void enterEmailOrPhone(String emailOrPhone) {
        loginMailOrPhoneField.fill(emailOrPhone);
        page.waitForTimeout(2000); 
    }

 // Click Send Verification Code (OTP trigger)
    public void clickVerificationCode() {
    	sendVerificationCode.click();
    	 page.waitForTimeout(2000); 
    }
    // Enter OTP received to continue sign up process
    public void enterEmailOtp(String otp) {
        emailOtpField.waitFor();    
        emailOtpField.fill(otp);
    }
    public void enterMobileOtp(String otp) {
        mobileOtpField.waitFor();    
        mobileOtpField.fill(otp);
    }
    // Enter Password for creating an account
    public void enterPassword(String password) {
        passwordField.fill(password);
        page.waitForTimeout(2000); 
           }
    // Final action to complete registration
    public void clickFinalSignupButton() {
        finalSignupButton.click();
        page.waitForTimeout(2000); 
    }
    // Validation method → Returns true if success message is visible
    public boolean isSignupSuccess() {
        return successToaster.isVisible();
    }



	
}