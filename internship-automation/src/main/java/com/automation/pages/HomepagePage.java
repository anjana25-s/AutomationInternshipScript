package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomepagePage {

    private final Page page;

    // ------------------- Locators -------------------
    private final String maybeLaterBtn = "button:has-text('May be later!')";
    private final String internshipsTab = "a.nav-link[data-rr-ui-event-key='/internships-listing']";

    // Internship card locator base
    private final String internshipCardBase =
        "(//a[contains(@href, '/internships-description/') " +
        "and .//h3[normalize-space(text())='%s']])[1]";

    // ------------------- Constructor -------------------
    public HomepagePage(Page page) {
        this.page = page;
    }

    // ------------------- Locator Getters -------------------
    public Locator getMaybeLaterBtn() {
        return page.locator(maybeLaterBtn);
    }

    public Locator getInternshipsTab() {
        return page.locator(internshipsTab);
    }

    public Locator getInternshipCard(String internshipTitle) {
        return page.locator(String.format(internshipCardBase, internshipTitle)).first();
    }

    // ⭐ NEW — Internship Header
    public Locator getInternshipHeader(String internshipTitle) {
        return page.locator(
            "h2.Job_Internship-Head_title:has-text('" + internshipTitle + "')"
        );
    }

    // ------------------- Utility -------------------
    public void scrollUntilVisible(Locator element, int maxScrolls) {
        int attempts = 0;
        while (!element.isVisible() && attempts < maxScrolls) {
            page.mouse().wheel(0, 1000);
            page.waitForTimeout(800);
            attempts++;
        }
    }
}
