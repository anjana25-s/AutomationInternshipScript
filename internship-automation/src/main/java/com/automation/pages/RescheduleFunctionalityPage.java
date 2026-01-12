package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RescheduleFunctionalityPage {

    private final Page page;

    public RescheduleFunctionalityPage(Page page) {
        this.page = page;
    }

    // ================= CALENDAR =================

    public Locator getNextMonthButton() {
        return page.locator("(//span[contains(@class,'custom-next-month')])[2]");
    }

    // enabled, clickable date
    public Locator getFirstActiveDate() {
        return page.locator(
                "//span[contains(@class,'flatpickr-day')" +
                " and not(contains(@class,'disabled'))" +
                " and not(contains(@class,'prevMonthDay'))" +
                " and not(contains(@class,'nextMonthDay'))]"
        ).first();
    }

    // ================= TIME =================

    public Locator getFirstAvailableTimeSlot() {
        return page.locator(
                "//li[contains(@class,'time-slot-box')" +
                " and not(contains(@class,'disabled'))" +
                " and not(contains(@style,'display: none'))]"
        ).first();
    }

    // ================= ACTION =================

    public Locator getContinueButton() {
        return page.locator(
                "//button[contains(text(),'Continue')" +
                " or contains(text(),'Confirm')" +
                " or contains(text(),'Reschedule')]"
        ).first();
    }

    // ================= WAITS =================

    public void waitForCalendarToLoad() {
        getFirstActiveDate()
                .waitFor(new Locator.WaitForOptions().setTimeout(10000));
    }

    public void waitForTimeSlotsToLoad() {
        getFirstAvailableTimeSlot()
                .waitFor(new Locator.WaitForOptions().setTimeout(10000));
    }
}




