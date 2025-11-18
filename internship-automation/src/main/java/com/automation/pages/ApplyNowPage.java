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

    // ===================== OTP =====================
    public Locator getOtpInputField(int index) {
        return page.locator("(//div[contains(@class,'modal-content')]//input[@aria-label])[" + index + "]");
    }

    public Locator getVerifyAndProceedButton() {
        return page.locator("//button[contains(.,'Verify') or contains(.,'Proceed')]");
    }

    // ===================== CALENDAR =====================
    public Locator getLanguageCard(String language) {
        return page.locator("//div[contains(@class,'language-card')]//span[normalize-space()='" + language + "']");
    }

    public Locator getFirstActiveDate() {
        return page.locator("(//span[contains(@class,'flatpickr-day') and not(contains(@class,'disabled'))])[1]");
    }

    public Locator getFirstActiveTimeSlot() {
        return page.locator("(//li[contains(@class,'time-slot-box') and not(contains(@class,'disabled'))])[1]");
    }

    public Locator getNextButton() {
        return page.locator("//button[normalize-space()='Next']");
    }

    public Locator getCalendarSubmitButton() {
        return page.locator("//button[contains(@class,'calendar-modal-custom-btn') and normalize-space()='Submit']");
    }

    // ===================== SCREENING QUESTIONS =====================
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

    // ===================== THANK YOU POPUP =====================
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




