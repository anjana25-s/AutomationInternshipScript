package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyInterestPage {

    private final Page page;

    public MyInterestPage(Page page) {
        this.page = page;
    }

    // ================= TOP MENU =================
    private Locator myInterestMenu() {
        return page.locator(
                "//li[contains(@class,'top-menu-bar-items')]//span[normalize-space()='My Interest']"
        );
    }

    // ================= OPEN PAGE =================
    public void open() {
        myInterestMenu().click();
        page.waitForLoadState();
    }

    // ================= BASE CARD =================
    private Locator interestCard(String internshipName) {
        return page.locator(".my-preferance-card-body")
                .filter(new Locator.FilterOptions().setHas(
                        page.locator(
                                "a.card-content-header-text",
                                new Page.LocatorOptions().setHasText(internshipName)
                        )
                ))
                .first();
    }

    // ================= STATUS =================
    public Locator getStatusTag(String internshipName) {
        return interestCard(internshipName)
                .locator(".my-interest-status-tag");
    }

    // ================= INTERNSHIP TITLE =================
    public Locator getInternshipTitle(String internshipName) {
        return interestCard(internshipName)
                .locator("a.card-content-header-text");
    }

    // ================= LOCATION =================
    public Locator getLocation(String internshipName) {
        return interestCard(internshipName)
                .locator(".category-text-interest-card")
                .first();
    }

    // ================= COMPANY NAME =================
    public Locator getCompanyName(String internshipName) {
        return interestCard(internshipName)
                .locator(".card-content-sub_header-text")
                .nth(1);
    }

    // ================= MEETING DATE =================
    public Locator getMeetingDate(String internshipName) {
        return interestCard(internshipName)
                .locator(".card_detail-label:text-is('Meeting Date')")
                .locator("xpath=following-sibling::div");
    }

    // ================= MEETING TIME =================
    public Locator getMeetingTime(String internshipName) {
        return interestCard(internshipName)
                .locator(".card_detail-label:text-is('Meeting Time')")
                .locator("xpath=following-sibling::div");
    }

    // ================= INTERVIEW MODE =================
    public Locator getInterviewMode(String internshipName) {
        return interestCard(internshipName)
                .locator(".free-councel span");
    }

    // ================= RESCHEDULE ICON =================
    public Locator getCalendarIcon(String internshipName) {
        return interestCard(internshipName)
                .locator("img[alt='Reschedule']")
                .first();
    }

    // ================= FOOTER BUTTONS =================
    public Locator getCancelButton(String internshipName) {
        return interestCard(internshipName)
                .locator("button:has-text('Cancel')");
    }

    public Locator getSendReminderButton(String internshipName) {
        return interestCard(internshipName)
                .locator("button:has-text('Send Reminder')");
    }
}


