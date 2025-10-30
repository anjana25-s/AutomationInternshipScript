package com.automation.pages;

import com.microsoft.playwright.Page;

public class ApplyNowPopupLocators {
    private final Page page;

    public ApplyNowPopupLocators(Page page) {
        this.page = page;
    }

    // =========================
    // BUTTON LOCATORS
    // =========================
    public final String applyNowBtn = "(//button[text()='Apply Now'])[1]";
    public final String popupApplyNowBtn = "(//button[text()='Apply Now'])[4]"; // Prefer CSS if possible
    public final String bookSlotBtn = "button:has-text('Book Slot')";
    public final String bookSlotNextBtn = "button:has-text('Next')"; // For screening questions
    public final String bookSlotDirectSubmitBtn = "button.fw-bold.w-100.font-16.calendar-modal-custom-btn.btn.btn-primary:has-text('Submit')";
    public final String bookSlotAfterScreeningSubmitBtn = "button.w-100.calendar-modal-custom-btn.btn.btn-primary:has-text('Submit')";

    public final String otpVerifyAndProceedBtn = "button.submit-btm-askUs.btn.btn-primary:has-text('Verify & Proceed')";

    // =========================
    // INPUT FIELD LOCATORS
    // =========================
    public final String userNameField = "//input[@placeholder='Name*']";
    public final String userEmailField = "#userEmail";


    public final String userPhoneField = "//input[@placeholder='Mobile*']";
    public final String industryDropdown = "#industry-dropdown";

    // =========================
    // CONTAINERS / SECTIONS
    // =========================
    public final String popupContainer = ".modal-content";

    // =========================
    // MESSAGES / OTHERS
    // =========================
    public final String alreadyInterestedMsg = "div:has-text('already shown interest')";

    // =========================
    // DYNAMIC LOCATORS
    // =========================
    public final String languageMenu = "#language";
    public final String languageOption = "//div[@id='language']//li[text()='%s']";

    private final String dateSlotBase = "//span[@aria-label='%s']";
    private final String timeSlotBase = "//li[text()='%s']";

    public String getDateSlot(String dateLabel) {
        return String.format(dateSlotBase, dateLabel);
    }

    public String getTimeSlot(String time) {
        return String.format(timeSlotBase, time);
    }

    // =========================
    // OTP Inputs by index (1 to 4)
    // =========================
    public String getOtpInput(int index) {
        return "input[aria-label='Please enter OTP character " + index + "']";
    }

    // =========================
    // SCREENING QUESTIONS
    // =========================
    public final String screeningQuestions = ".screening-question"; // Container for all questions
    public final String screeningSubmitBtn = "button:has-text('Submit')"; // After screening
}





