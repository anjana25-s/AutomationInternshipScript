package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class RescheduleFunctionalityActions {

    private final Page page;

    public RescheduleFunctionalityActions(Page page) {
        this.page = page;
    }

    private final String myInterestsTab = "//span[text()='My Interest']";
    private String internshipCard(String title) {
        return "//a[contains(@class,'card-content-header-text') and " +
               "contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" +
               title.toLowerCase() + "')]/ancestor::div[contains(@class,'card')]";
    }
    private final String calendarIcon = ".//img[@alt='Reschedule']";
    private final String selectedSlotDiv = ".//div[contains(@class,'rescheduledSlots-time') and contains(@class,'active')]";

    // Actions
    public RescheduleFunctionalityActions clickMyInterests() {
        page.locator(myInterestsTab)
            .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator(myInterestsTab).click();
        return this;
    }

    public RescheduleFunctionalityActions clickCalendarIconForInternship(String title) {
        Locator card = page.locator(internshipCard(title)).first();
        card.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        card.locator(calendarIcon).click();
        return this;
    }

    // Fetch selected slot text
    public String getSelectedRescheduleSlot(String title) {
        Locator card = page.locator(internshipCard(title)).first();
        return card.locator(selectedSlotDiv).textContent().trim();
    }

    public Page getPage() {
        return page;
    }
}
