package com.promilo.automation.internship.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyProspectCardValidation {

    private final Page page;

    // ✅ ADD THIS CONSTRUCTOR
    public MyProspectCardValidation(Page page) {
        this.page = page;
    }

    
    public void waitForProspectCard() {
        page.locator("span.prospect-card-user-name")
            .first()
            .waitFor(new Locator.WaitForOptions()
                    .setTimeout(15000));
    }

    // User Name
    public String getUserName() {
        return page.locator("span.prospect-card-user-name")
                .first()
                .innerText()
                .trim();
    }

    // Campaign Name
    public String getCampaignName() {
        return page.locator("span.text-primary.font-10")
                .first()
                .innerText()
                .trim();
    }

    public String getInterestShownDate() {
        return page.locator(
                "span.font-10.d-flex.justify-content-start.align-items-center.pointer.text-primary"
        ).nth(5)
         .innerText()
         .trim();
    }

    public String getMeetingStatus() {
        return page.locator(
                "span.font-10.d-flex.justify-content-start.align-items-center.pointer.text-primary"
        ).nth(6)
         .innerText()
         .trim();
    }

    public String getPreferredLanguage() {
        return page.locator(
                "span.font-10.d-flex.justify-content-start.align-items-center.pointer.text-primary"
        ).nth(7)
         .innerText()
         .trim();
    }

}