package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

public class FillApplicationFormComponents {

    private final Page page;

    // =======================
    // FORM INPUT FIELDS
    // =======================
    private final Locator nameTextField;
    private final Locator mobileTextField;
    private final Locator emailTextField;
    private final Locator preferredLocationDropdown;
    private final Locator locationOptions;
    private final Locator fillApplicationButton;

    // =======================
    // OTP FIELDS
    // =======================
    private final List<Locator> otpFields;
    private final Locator verifyAndProceedButton;

    // =======================
    // CONSTRUCTOR
    // =======================
    public FillApplicationFormComponents(Page page) {
        this.page = page;

        // Input fields
        this.nameTextField = page.locator("//input[@id='userName']");
        this.mobileTextField = page.locator("//input[@id='userMobile']");
        this.emailTextField = page.locator("//input[@id='userEmail']");
        this.preferredLocationDropdown = page.locator("[id='preferredLocation']");
        this.locationOptions = page.locator("//div[@class='option w-100']");
        this.fillApplicationButton = page.locator("//button[text()='Fill Application']");

        // OTP fields (4 digits)
        this.otpFields = List.of(
                page.locator("//input[@aria-label='Please enter OTP character 1']"),
                page.locator("//input[@aria-label='Please enter OTP character 2']"),
                page.locator("//input[@aria-label='Please enter OTP character 3']"),
                page.locator("//input[@aria-label='Please enter OTP character 4']")
        );
        this.verifyAndProceedButton = page.locator("//button[text()='Verify & Proceed']");
    }

    // =======================
    // GETTERS
    // =======================
    public Locator nameTextField() { return nameTextField; }
    public Locator mobileTextField() { return mobileTextField; }
    public Locator emailTextField() { return emailTextField; }
    public Locator preferredLocationDropdown() { return preferredLocationDropdown; }
    public Locator locationOptions() { return locationOptions; }
    public Locator fillApplicationButton() { return fillApplicationButton; }

    public List<Locator> otpFields() { return otpFields; }
    public Locator verifyAndProceedButton() { return verifyAndProceedButton; }

    // =======================
    // HELPER METHODS
    // =======================

    /**
     * Selects the given locations from the dropdown
     */
    public void selectLocations(List<String> locations) {
        for (String loc : locations) {
            boolean found = false;
            for (int i = 0; i < locationOptions.count(); i++) {
                String optionText = locationOptions.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(loc)) {
                    locationOptions.nth(i).click();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.err.println("⚠️ Location not found: " + loc);
            }
        }
    }

    /**
     * Fill OTP digits one by one
     */
    public void enterOtp(String otp) {
        for (int i = 0; i < otp.length(); i++) {
            String digit = Character.toString(otp.charAt(i));
            Locator otpField = otpFields.get(i);
            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            for (int retry = 0; retry < 3; retry++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(digit);
                if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                    break;
                page.waitForTimeout(500);
            }
        }
    }
}
