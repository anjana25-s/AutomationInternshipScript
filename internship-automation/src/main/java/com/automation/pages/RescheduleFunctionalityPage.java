package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RescheduleFunctionalityPage {

    private final Page page;

    // ------------------- Locators -------------------
    private final String myInterestsTab =
            "//a[normalize-space()='My Interest']";

    private final String internshipCardBase =
            "//div[contains(@class,'my-interest-card-contianer') and contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]";

    private final String calendarIcon =
            "//img[contains(@alt,'Reschedule') or contains(@src,'rescheduleIcon') or contains(@src,'reschedule')]";

    private final String nextMonthButton =
            "(//span[contains(@class,'custom-next-month')])[2]";

    private final String activeDate =
            "//span[contains(@class,'flatpickr-day') and not(contains(@class,'flatpickr-disabled'))]";

    private final String timeSlot =
            "//li[contains(@class,'time-slot-box') and not(contains(@class,'disabled'))]";

    private final String continueButton =
            "//button[contains(text(),'Continue') or contains(text(),'Confirm') or contains(text(),'Reschedule')]";

    // ✅ Use label-based approach (most stable)
    private final String meetingDateLabel =
            ".//div[div[contains(normalize-space(),'Meeting Date')]]//div[contains(@class,'card_detail-value')]";

    private final String meetingTimeLabel =
            ".//div[div[contains(normalize-space(),'Meeting Time')]]//div[contains(@class,'card_detail-value')]";

    // ------------------- Constructor -------------------
    public RescheduleFunctionalityPage(Page page) {
        this.page = page;
    }

    // ------------------- Locator Getters -------------------
    public Locator getMyInterestTab() {
        return page.locator(myInterestsTab);
    }

    public Locator getInternshipCard(String internshipTitle) {
        return page.locator(String.format(internshipCardBase, internshipTitle.toLowerCase()));
    }

    public Locator getCalendarIcon(String internshipTitle) {
        return getInternshipCard(internshipTitle).locator(calendarIcon);
    }

    public Locator getNextMonthButton() {
        return page.locator(nextMonthButton);
    }

    public Locator getFirstActiveDate() {
        return page.locator(activeDate).first();
    }

    public Locator getFirstAvailableTimeSlot() {
        return page.locator(timeSlot).first();
    }

    public Locator getContinueButton() {
        return page.locator(continueButton).first();
    }

    public Locator getMeetingDateLabel(String internshipTitle) {
        return getInternshipCard(internshipTitle).locator(meetingDateLabel);
    }

    public Locator getMeetingTimeLabel(String internshipTitle) {
        return getInternshipCard(internshipTitle).locator(meetingTimeLabel);
    }
}


