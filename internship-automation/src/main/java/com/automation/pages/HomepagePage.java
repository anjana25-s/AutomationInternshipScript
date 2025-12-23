package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomepagePage {

    private final Page page;

    // ------------------- Locators -------------------
    private final String maybeLaterBtn = "button:has-text('May be later!')";
    private final String internshipsTab = "a.nav-link:has-text('Internships')";

    // ------------------- Constructor -------------------
    public HomepagePage(Page page) {
        this.page = page;
    }

    // ------------------- Basic Getters -------------------
    public Locator getMaybeLaterBtn() {
        return page.locator(maybeLaterBtn);
    }

    public Locator getInternshipsTab() {
        return page.locator(internshipsTab);
    }

    // ------------------- INTERNSHIP (TITLE BASED – FINAL) -------------------

    /**
     * Returns the <h3> element that contains the internship title text.
     * Works for both card-title and additional-cards-title variants.
     */
    public Locator getInternshipTitle(String title) {
        return page.locator(
                "//h3[normalize-space()='" + title + "']"
        ).first();
    }

    /**
     * Returns the clickable card/container for the given internship title.
     * This is the element that should be clicked.
     */
    public Locator getInternshipCard(String title) {
        return getInternshipTitle(title)
                .locator("xpath=ancestor::a | ancestor::div[contains(@class,'card')]")
                .first();
    }
}

