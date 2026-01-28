package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ConnectNowPage {

    private Page page;

    public ConnectNowPage(Page page) {
        this.page = page;
    }

    // -----------------------------
    // Headers & Static Texts
    // -----------------------------
    public Locator needHelpHeader() {
        return page.locator("//h1[text()='Need help choosing the right college & course?']");
    }

    public Locator registerWithUsHeader() {
        return page.locator("//h1[text()='Register with us.']");
    }

    public Locator whatsappUpdatesLabel() {
        return page.locator("//label[text()='Enable updates & important information on Whatsapp']");
    }

    public Locator rightSideInfoBlock() {
        return page.locator("//div[@class='d-none d-md-block ']");
    }

    public Locator whyShouldYouSignupText() {
        return page.locator("//p[text()='Why Should you sign up?']");
    }

    // -----------------------------
    // Input Fields
    // -----------------------------
    public Locator userNameField() {
        return page.locator("//input[@name='userName']").nth(1);
    }

    public Locator mobileField() {
        return page.locator("//input[@name='userMobile']").nth(1);
    }

    public Locator emailField() {
        return page.locator("//input[@id='userEmail']").nth(1);
    }

    public Locator preferredStreamDropdown() {
        return page.locator("//input[@placeholder='Preferred Stream*']");
    }

    public Locator engineeringStreamOption() {
        return page.locator("//label[text()='Engineering']");
    }

    public Locator firstCheckBox() {
        return page.locator("//input[@type='checkbox']").first();
    }

    public Locator preferredLocationDropdown() {
        return page.locator("//input[@id='preferredLocation']").nth(1);
    }

    public Locator preferredLocationOptions() {
        return page.locator("//div[@class='option w-100']");
    }

    public Locator passwordField() {
        return page.locator("//input[@placeholder='Create Password*']").nth(1);
    }

    // -----------------------------
    // Buttons
    // -----------------------------
    public Locator registerButton() {
        return page.locator("//button[text()='Register']");
    }

    public Locator connectNowButton() {
        return page.locator("//button[text()='Connect Now']");
    }

    // -----------------------------
    // OTP Fields
    // -----------------------------
    public Locator otpDigitField(int digitIndex) {
        return page.locator("//input[@aria-label='Please enter OTP character " + digitIndex + "']");
    }

    public Locator verifyAndProceedButton() {
        return page.locator("//button[text()='Verify & Proceed']");
    }

    // -----------------------------
    // Popup: Thank You!
    // -----------------------------
    public Locator thankYouPopup() {
        return page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='thank you!']"
        );
    }

    public Locator thankYouCloseIcon() {
        return page.locator("img[alt='closeIcon Ask us']");
    }

    // -----------------------------
    // Notification Icon
    // -----------------------------
    public Locator inAppNotificationIcon() {
        return page.locator("//img[@alt='In-App-Notification-Icon']").nth(1);
    }
}
