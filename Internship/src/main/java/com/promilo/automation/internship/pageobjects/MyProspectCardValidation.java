package com.promilo.automation.internship.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class MyProspectCardValidation {

    private final Page page;
    private final Locator prospectCard;

    public MyProspectCardValidation(Page page) {
        this.page = page;

        // ✅ REAL prospect card container from DOM
        this.prospectCard = page.locator(
            "div.my-acceptance-card.card"
        ).first();
    }

    // ================= WAIT FOR CARD =================
    public void waitForProspectCard() {

        // Ensure page is fully loaded
        page.waitForLoadState();

        // Scroll to trigger lazy load (VERY IMPORTANT)
        page.mouse().wheel(0, 800);

        prospectCard.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
    }

    // ================= USER NAME =================
    public String getUserName() {
        return prospectCard
                .locator("span.text-dark")
                .first()
                .innerText()
                .trim();
    }

    // ================= CAMPAIGN NAME =================
    public String getCampaignName() {
        return prospectCard.locator(
            "span:has-text('Campaign Name') >> xpath=following-sibling::span[1]"
        ).innerText().trim();
    }

    // ================= INTEREST SHOWN DATE =================
    public String getInterestShownDate() {
        return prospectCard.locator(
            "span:has-text('Interest Shown') >> xpath=following-sibling::span[1]"
        ).innerText().trim();
    }

    // ================= MEETING STATUS =================
    public String getMeetingStatus() {
        return prospectCard.locator(
            "span:has-text('Status') >> xpath=following-sibling::span[1]"
        ).innerText().trim();
    }

    // ================= PREFERRED LANGUAGE =================
    public String getPreferredLanguage() {
        return prospectCard.locator(
            "span:has-text('Language') >> xpath=following-sibling::span[1]"
        ).innerText().trim();
    }
}
