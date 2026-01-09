package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyMeetingsPage {

    private final Page page;

    public MyMeetingsPage(Page page) {
        this.page = page;
    }

    // ================= MY MEETINGS TAB =================
    public Locator getMyMeetingsTab() {
        return page.locator(
                "a.nav-link:has(span:text-is('My Meetings'))"
        );
    }

    // ================= MEETING CARD =================
    private Locator meetingCardByInternship(String internshipName) {
        return page.locator(".my-meeting-cards .my-preferance-card-body")
                .filter(new Locator.FilterOptions()
                        .setHas(
                                page.locator(
                                        "a.preferance-header-text",
                                        new Page.LocatorOptions()
                                                .setHasText(internshipName)
                                )
                        )
                )
                .first();
    }

    // ================= STATUS =================
    public Locator getStatusTag(String internshipName) {
        return meetingCardByInternship(internshipName)
                .locator(".my-interest-status-tag");
    }

    // ================= DATE =================
    public Locator getMeetingDate(String internshipName) {
        return meetingCardByInternship(internshipName)
                .locator(".card_detail-label:text-is('Meeting Date')")
                .locator("xpath=following-sibling::div");
    }

    // ================= TIME =================
    public Locator getMeetingTime(String internshipName) {
        return meetingCardByInternship(internshipName)
                .locator(".card_detail-label:text-is('Meeting Time')")
                .locator("xpath=following-sibling::div");
    }

    // ================= INTERVIEW TYPE =================
    public Locator getInterviewType(String internshipName) {
        return meetingCardByInternship(internshipName)
                .locator(".free-councel span");
    }

    // ================= BUTTONS =================
    public Locator getCancelButton(String internshipName) {
        return meetingCardByInternship(internshipName)
                .locator("button:has-text('Cancel')");
    }

    public Locator getJoinNowButton(String internshipName) {
        return meetingCardByInternship(internshipName)
                .locator(".join-now-btn");
    }
}
