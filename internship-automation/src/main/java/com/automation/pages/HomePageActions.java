package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.PlaywrightException;

public class HomePageActions {

    private final Page page;

    // Locators
    private final Locator maybeLaterBtn;
    private final Locator internshipsTab;

    public HomePageActions(Page page) {
        this.page = page;
        this.maybeLaterBtn = page.locator("button:has-text('May be later!')");
        this.internshipsTab = page.locator("a.nav-link[data-rr-ui-event-key='/internships-listing']");
    }

    // Dynamic locator for internship card
    private Locator internshipCard(String title) {
        return page.locator("a.card-content-header-text:has-text('" + title + "')");
    }

    // Actions

    // Dismiss modal if visible
    public void clickMaybeLater() {
        try {
            if (maybeLaterBtn.isVisible()) {
                maybeLaterBtn.click();
                System.out.println("[HomePage] Modal dismissed via 'May be later!'");
            } else {
                System.out.println("[HomePage] No modal visible, skipping.");
            }
        } catch (PlaywrightException e) {
            System.out.println("[HomePage] Exception while dismissing modal: " + e.getMessage());
        }
    }

    // Click Internships tab
    public void clickInternshipsTab() {
        try {
            internshipsTab.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            internshipsTab.click();
            System.out.println("[HomePage] Clicked Internships tab.");
        } catch (PlaywrightException e) {
            System.out.println("[HomePage] Exception clicking Internships tab: " + e.getMessage());
        }
    }

    // Select internship card by title
    public void selectInternshipCard(String title) {
        try {
            Locator card = internshipCard(title);
            card.scrollIntoViewIfNeeded();
            card.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            card.click();
            System.out.println("[HomePage] Selected internship: " + title);
        } catch (PlaywrightException e) {
            System.out.println("[HomePage] Exception selecting internship card: " + e.getMessage());
        }
    }

    // Complete internship flow
    public void completeInternshipSelection(String title) {
        clickMaybeLater();
        clickInternshipsTab();
        selectInternshipCard(title);
    }
}

