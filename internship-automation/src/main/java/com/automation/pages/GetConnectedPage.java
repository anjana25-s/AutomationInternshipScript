package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetConnectedPage {

    private final Page page;

    // =====================================================
    // TOP – GET CONNECTED BANNER (INTERNSHIP PAGE)
    // =====================================================
    private final String connectBannerTitle =
            "//p[contains(text(),'Looking for Your Dream Internship')]";

    private final String connectBannerDesc =
            "//p[contains(text(),'exclusive opportunities')]";

    private final String getConnectedBtn =
            "//button[normalize-space()='Get Connected']";

    // =====================================================
    // REGISTER MODAL – RIGHT FORM
    // =====================================================
    private final String registerTitle =
            "//h1[contains(text(),'Need help to choose the right Internship')]";

    private final String registerSubtitle =
            "//h1[normalize-space()='Register with us.']";

    private final String nameField =
            "(//input[@placeholder='Name*'])[2]";

    private final String mobileField =
            "(//input[@placeholder='Mobile*'])[2]";

    private final String emailField =
            "(//input[@placeholder='Email*'])[2]";

    private final String industryDropdown =
            "(//div[contains(@class,'modal') and contains(@class,'show')]//div[@id='industry-dropdown'])[1]";

    private final String industryCheckboxes =
            "//div[contains(@class,'modal') and contains(@class,'show')]//input[@type='checkbox']";

    private final String passwordField =
            "(//input[@placeholder='Create Password*'])[2]";

    private final String registerBtn =
            "//button[normalize-space()='Register']";

    private final String whatsappToggleLabel =
            "//label[contains(text(),'Enable updates')]";

    // =====================================================
    // REGISTER MODAL – FOOTER
    // =====================================================
    private final String termsText =
            "//p[contains(text(),'By proceeding ahead')]";

    private final String termsLink =
            "//a[contains(text(),'Terms')]";

    private final String alreadyAccountText =
            "//span[contains(text(),'Already have an account')]";

    private final String loginLink =
            "//a[normalize-space()='Login']";

    // =====================================================
    // OTP – RIGHT PANEL
    // =====================================================
    private final String otpThankYouText =
            "//h4[contains(text(),'Thanks for giving your Information')]";

    private final String otpHeader =
            "//h5[normalize-space()='OTP Verification']";

    private final String otpInstructionText =
            "//p[contains(text(),'Enter the 4-digit verification code')]";

    private final String otpStillCantFindText =
            "//p[contains(text(),'Still can’t find the OTP')]";

    private final String resendOtpText =
            "//p[contains(text(),'Resend OTP')]";

    private final String backBtn =
            "//span[normalize-space()='Back']";

    private final String otpInputTemplate =
            "input[aria-label='Please enter OTP character %d']";

    private final String verifyOtpBtn =
            "//button[normalize-space()='Verify & Proceed']";

    // =====================================================
    // OTP – LEFT BANNER (STRICT-MODE SAFE ✅)
    // =====================================================
    private final String banner1Title =
            "//div[contains(@class,'left') or contains(@class,'banner')]//h6[normalize-space()='Start Your Career Journey']";

    private final String banner1Desc =
            "//div[contains(@class,'left') or contains(@class,'banner')]//p[contains(text(),'exclusive internships')]";

    private final String banner2Title =
            "//div[contains(@class,'left') or contains(@class,'banner')]//h6[normalize-space()='Tailored Internship Matches']";

    private final String banner2Desc =
            "//div[contains(@class,'left') or contains(@class,'banner')]//p[contains(text(),'customized internship')]";

    private final String banner3Title =
            "//div[contains(@class,'left') or contains(@class,'banner')]//h6[normalize-space()='Unlock Your Potential']";

    private final String banner3Desc =
            "//div[contains(@class,'left') or contains(@class,'banner')]//p[contains(text(),'world of opportunities')]";

    // =====================================================
    // FINAL THANK YOU MODAL
    // =====================================================
    private final String finalThankYouTitle =
            "//div[@class='headerText' and normalize-space()='Thank You!']";

    private final String finalThankYouMessage =
            "//p[contains(text(),'Congratulations')]";

    private final String finalThankYouSubMessage =
            "//div[contains(text(),'further steps')]";

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public GetConnectedPage(Page page) {
        this.page = page;
    }

    // =====================================================
    // GETTERS – TOP BANNER
    // =====================================================
    public Locator getConnectBannerTitle() { return page.locator(connectBannerTitle); }
    public Locator getConnectBannerDesc() { return page.locator(connectBannerDesc); }
    public Locator getGetConnectedBtn() { return page.locator(getConnectedBtn); }

    // =====================================================
    // GETTERS – REGISTER FORM
    // =====================================================
    public Locator getRegisterTitle() { return page.locator(registerTitle); }
    public Locator getRegisterSubtitle() { return page.locator(registerSubtitle); }

    public Locator getNameField() { return page.locator(nameField); }
    public Locator getMobileField() { return page.locator(mobileField); }
    public Locator getEmailField() { return page.locator(emailField); }

    public Locator getIndustryDropdown() { return page.locator(industryDropdown); }
    public Locator getIndustryCheckboxes() { return page.locator(industryCheckboxes); }

    public Locator getPasswordField() { return page.locator(passwordField); }
    public Locator getRegisterBtn() { return page.locator(registerBtn); }

    public Locator getWhatsappToggleLabel() { return page.locator(whatsappToggleLabel); }

    // =====================================================
    // GETTERS – REGISTER FOOTER
    // =====================================================
    public Locator getTermsText() { return page.locator(termsText); }
    public Locator getTermsLink() { return page.locator(termsLink); }
    public Locator getAlreadyAccountText() { return page.locator(alreadyAccountText); }
    public Locator getLoginLink() { return page.locator(loginLink); }

    // =====================================================
    // GETTERS – OTP
    // =====================================================
    public Locator getOtpThankYouText() { return page.locator(otpThankYouText); }
    public Locator getOtpHeader() { return page.locator(otpHeader); }
    public Locator getOtpInstructionText() { return page.locator(otpInstructionText); }
    public Locator getOtpStillCantFindText() { return page.locator(otpStillCantFindText); }
    public Locator getResendOtpText() { return page.locator(resendOtpText); }
    public Locator getBackBtn() { return page.locator(backBtn); }

    public Locator getOtpInput(int index) {
        return page.locator(String.format(otpInputTemplate, index));
    }

    public Locator getVerifyOtpBtn() { return page.locator(verifyOtpBtn); }

    // =====================================================
    // GETTERS – OTP LEFT BANNER
    // =====================================================
    public Locator getBanner1Title() { return page.locator(banner1Title); }
    public Locator getBanner1Desc() { return page.locator(banner1Desc); }

    public Locator getBanner2Title() { return page.locator(banner2Title); }
    public Locator getBanner2Desc() { return page.locator(banner2Desc); }

    public Locator getBanner3Title() { return page.locator(banner3Title); }
    public Locator getBanner3Desc() { return page.locator(banner3Desc); }

    // =====================================================
    // GETTERS – FINAL THANK YOU
    // =====================================================
    public Locator getFinalThankYouTitle() { return page.locator(finalThankYouTitle); }
    public Locator getFinalThankYouMessage() { return page.locator(finalThankYouMessage); }
    public Locator getFinalThankYouSubMessage() { return page.locator(finalThankYouSubMessage); }
}
