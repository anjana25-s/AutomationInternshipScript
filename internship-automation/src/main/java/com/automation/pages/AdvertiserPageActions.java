package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserPageActions {

    private final Page page;

    public AdvertiserPageActions(Page page) {
        this.page = page;
    }

    private final String myAccountMenu = "//span[text()='My Account']";
    private final String myProspectMenu = "//span[text()='My Prospect']";

    private String prospectCard(String userName) {
        return "//h2[contains(@class,'prospect-card-user-name') and contains(text(),'" + userName + "')]/ancestor::div[contains(@class,'acceptance-card-header')]";
    }

    private final String rescheduleBtn = ".//span[text()='Reschedule Request']/ancestor::button";
    private final String acceptBtn = "button.accept-btn-rescheduled";
    private final String selectedSlotDiv = ".//div[contains(@class,'rescheduledSlots-time') and contains(@class,'active')]";

    // Actions
    public AdvertiserPageActions goToMyProspect() {
        page.locator(myAccountMenu).click();
        page.locator(myProspectMenu).click();
        return this;
    }

    public AdvertiserPageActions clickRescheduleForUser(String userName) {
        Locator card = page.locator(prospectCard(userName)).first();
        card.waitFor();
        card.locator(rescheduleBtn).click();
        return this;
    }

    // Validate the slot
    public AdvertiserPageActions validateRescheduleSlot(String expectedSlot) {
        String displayedSlot = page.locator(selectedSlotDiv).textContent().trim();
        if (!displayedSlot.equals(expectedSlot)) {
            throw new RuntimeException("Slot mismatch! Expected: " + expectedSlot + " | Found: " + displayedSlot);
        }
        System.out.println("[Advertiser] Reschedule slot validated: " + displayedSlot);
        return this;
    }

    public AdvertiserPageActions acceptReschedule() {
        page.locator(acceptBtn).click();
        return this;
    }

    public Page getPage() {
        return page;
    }
}
