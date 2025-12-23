package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ApplyNowPage {

    private final Page page;

    public ApplyNowPage(Page page) {
        this.page = page;
    }

    // ===================== APPLY NOW BUTTON =====================
    public Locator getApplyNowButton() {
        return page.locator("//button[contains(text(),'Apply Now')]").first();
    }

    // ===================== USER DETAILS =====================
    public Locator getNameField() {
        return page.locator("(//div[contains(@class,'modal') and contains(@class,'show')]//input[@id='userName'])[1]");
    }

    public Locator getPhoneField() {
        return page.locator("(//div[contains(@class,'modal') and contains(@class,'show')]//input[@id='userMobile'])[1]");
    }

    public Locator getEmailField() {
        return page.locator("(//div[contains(@class,'modal') and contains(@class,'show')]//input[@id='userEmail'])[1]");
    }

    public Locator getIndustryDropdown() {
        return page.locator("(//div[contains(@class,'modal') and contains(@class,'show')]//div[@id='industry-dropdown'])[1]");
    }

    public Locator getAllIndustryCheckboxes() {
        return page.locator("//div[contains(@class,'modal') and contains(@class,'show')]//input[@type='checkbox']");
    }

    public Locator getAskUsApplyNowButton() {
        return page.locator("//div[contains(@class,'modal show')]//button[normalize-space()='Apply Now']");
    }

    // ===================== APPLY POPUP TEXT =====================
    public Locator getApplyHeader() {
        return page.locator(".modal.show .feature-content-header");
    }

    public Locator getWhyRegisterHeader() {
        return page.locator(".text-content .header-text");
    }

    public Locator getWhyRegisterBullets() {
        return page.locator(".text-content ul li.pb-1");
    }

    public Locator getWhatsappLabel() {
        return page.locator("label.form-check-label").first();
    }

    // ===================== OTP =====================
    public Locator getOtpInputField(int index) {
        return page.locator("(//div[contains(@class,'modal-content')]//input[@aria-label])[" + index + "]");
    }

    public Locator getVerifyAndProceedButton() {
        return page.locator("//button[contains(.,'Verify') or contains(.,'Proceed')]");
    }

    public Locator getOtpThankYouText() {
        return page.locator(".text-primary.mb-2.text-center");
    }

    public Locator getOtpHeader() {
        return page.locator("//h5[text()='OTP Verification']");
    }

    public Locator getOtpInstructionText() {
        return page.locator("//p[contains(text(),'Enter the 4-digit')]");
    }

    public Locator getOtpStillCantFindText() {
        return page.locator("//p[text()='Still can’t find the OTP?']");
    }

   
 // ===================== VIDEO INTERVIEW =====================

 // TITLE — uniquely identified by fw-500
 public Locator getVideoInterviewTitle() {
     return page.locator(
         ".otp-banner-info p.font-18.fw-500"
     );
 }

 // DESCRIPTION — specific font-16
 public Locator getVideoInterviewDesc() {
     return page.locator(
         ".otp-banner-info p.font-16"
     );
 }


    

    // ===================== LANGUAGE =====================
    public Locator getLanguageCard(String language) {
        return page.locator("//div[contains(@class,'language-card')]//span[normalize-space()='" + language + "']");
    }

    public Locator getSelectedLanguage() {
        return page.locator(".language-card.checked .label-text");
    }

    // ===================== CALENDAR =====================
    public Locator getFirstActiveDate() {
        return page.locator("(//span[contains(@class,'flatpickr-day') and not(contains(@class,'disabled'))])[1]");
    }

    public Locator getFirstActiveTimeSlot() {
        return page.locator("(//li[contains(@class,'time-slot-box') and not(contains(@class,'disabled'))])[1]");
    }

    public Locator getSelectedDate() {
        return page.locator(".flatpickr-day.selected, .flatpickr-day.today.selected");
    }

    public Locator getSelectedTime() {
        return page.locator(".time-slot-box.active");
    }

    public Locator getNextButton() {
        return page.locator("//button[normalize-space()='Next']");
    }

    public Locator getCalendarSubmitButton() {
        return page.locator(
                "//div[contains(@class,'modal') and contains(@class,'show')]//button[contains(@class,'calendar-modal-custom-btn') and normalize-space()='Submit']"
        );
    }

    // ===================== SCREENING =====================
    public Locator getScreeningQuestions() {
        return page.locator("//div[contains(@class,'interested-question-wrapper')]//div[contains(@class,'pb-1')]");
    }

    public Locator getObjectiveOptions(Locator question) {
        return question.locator("input[type='checkbox'], input[type='radio']");
    }

    public Locator getSubjectiveTextAreas(Locator question) {
        return question.locator("textarea");
    }

    public Locator getScreeningSubmitButton() {
        return page.locator("//button[contains(@class,'calendar-modal-custom-btn') and normalize-space()='Submit']");
    }

    // ===================== THANK YOU =====================
    public Locator getThankYouHeader() {
        return page.locator("//div[contains(@class,'headerText') and text()='Thank You!']");
    }

    public Locator getThankYouMessage() {
        return page.locator("//div[contains(@class,'ThankYou-message')]");
    }

    public Locator getThankYouMyInterestLink() {
        return page.locator("//div[contains(@class,'ThankYouPopup-Modal')]//a[contains(@href,'myinterest')]");
    }
}
