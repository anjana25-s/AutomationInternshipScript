package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserProspectCardPage {

    private final Page page;

    public AdvertiserProspectCardPage(Page page) {
        this.page = page;
    }

    private String cardRoot(String candidateName) {
        return "//div[contains(@class,'my-acceptance-card')]" +
               "[.//span[contains(@class,'prospect-card-user-name') and " +
               "normalize-space()='" + candidateName + "']]";
    }

    // ================= WAIT =================
    public void waitForProspectCard(String candidateName) {
        page.locator(cardRoot(candidateName))
            .waitFor(new Locator.WaitForOptions()
                    .setTimeout(45000));
    }

    // ================= BASIC INFO =================
    public Locator getCandidateName(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[contains(@class,'prospect-card-user-name')]"
        );
    }

    public Locator getCampaignName(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[text()='Campaign Name']/ancestor::li//span[contains(@class,'text-primary')]"
        );
    }

 // ================= MEETING =================

    public Locator getMeetingDate(String candidateName) {
        return page.locator(
            cardRoot(candidateName) +
            "//p[normalize-space()='Meeting Date']/ancestor::div[contains(@class,'student')]" +
            "//p[contains(@class,'fw-bold')]"
        );
    }

    public Locator getMeetingTime(String candidateName) {
        return page.locator(
            cardRoot(candidateName) +
            "//p[normalize-space()='Meeting Time']/ancestor::div[contains(@class,'student')]" +
            "//p[contains(@class,'fw-bold')]"
        );
    }


    public Locator getMeetingStatus(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[text()='Meeting Status']/ancestor::li//span[contains(@class,'text-primary')]"
        );
    }

    // ================= PREFERENCES =================
    public Locator getPreferredLanguage(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[text()='Prefered Language']/ancestor::li//span[contains(@class,'text-primary')]"
        );
    }

    public Locator getInterestShownDate(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[text()='Interest Shown on']/ancestor::li//span[contains(@class,'text-primary')]"
        );
    }

 // ================= VERIFICATION =================

    public Locator isEmailVerified(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//img[contains(@src,'email-verified')]"
        ).first();
    }

    public Locator isEmailNotVerified(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[normalize-space()='Not Verified']"
        ).first();
    }

    public Locator isPhoneVerified(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//img[contains(@src,'mobile-verified')]"
        ).first();
    }

    public Locator isPhoneNotVerified(String candidateName) {
        return page.locator(
                cardRoot(candidateName) +
                "//span[normalize-space()='Not Verified']"
        ).first();
    }
}


