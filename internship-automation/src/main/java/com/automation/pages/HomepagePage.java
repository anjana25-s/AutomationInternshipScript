package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomepagePage {

    private final Page page;

    // ------------------- Constructor -------------------
    public HomepagePage(Page page) {
        this.page = page;
    }

    // ==================================================
    // PREFERENCE MODAL (WHAT BRINGS YOU TO PROMILO?)
    // ==================================================

    public Locator getPreferenceModal() {
        return page.locator("div.modal-content").first();
    }

    public Locator getPreferenceModalTitle() {
        return page.locator("#preferenceModalTitle");
    }

    public Locator getMaybeLaterBtn() {
        return page.locator(
                "div.modal-content button.submit-btn:has-text('May be later!')"
        ).first();
    }

    public Locator getPreferenceModalCloseIcon() {
        return page.locator("#preferenceModalTitle img.close-icon");
    }

    // ==================================================
    // TOP NAVIGATION
    // ==================================================

    public Locator getInternshipsTab() {
        return page.locator("a.nav-link:has-text('Internships')").first();
    }

    // ==================================================
    // INTERNSHIP CARD (LIST PAGE)
    // ==================================================

    public Locator getInternshipTitle(String title) {
        return page.locator(
                "//h3[normalize-space()='" + title + "']"
        ).first();
    }

    public Locator getInternshipCard(String title) {
        return getInternshipTitle(title)
                .locator("xpath=ancestor::a")
                .first();
    }

    // ==================================================
    // INTERNSHIP DESCRIPTION PAGE
    // ==================================================

    public Locator getInternshipTitleOnDescription() {
        return page.locator("h2.Job_Internship-Head_title");
    }

    public Locator getCompanyNameOnDescription() {
        return page.locator("div.Job_Internship-Subheading_title");
    }

    public Locator getDurationOnDescription() {
        return page.locator(
                "//span[contains(@class,'Job_Internship-info')]" +
                "[.//span[text()='Duration:']]" +
                "//span[contains(@class,'Job_Internship-info-text')]"
        );
    }

    public Locator getLocationOnDescription() {
        return page.locator(
                "//span[contains(@class,'Job_Internship-info')]" +
                "[.//span[text()='Location:']]" +
                "//span[contains(@class,'Job_Internship-info-text')]"
        );
    }
}
