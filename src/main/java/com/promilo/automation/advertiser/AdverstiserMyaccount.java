package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdverstiserMyaccount {

    private final Page page;

    private final Locator myProspect;
    private final Locator myCandidate;
    private final Locator myMeeting;
    private final Locator myBilling;
    private final Locator faq;
    private final Locator basicInfo;
    private final Locator password;
    private final Locator userManagement;
    private final Locator privacyPolicy;
    private final Locator termsAndCondition;

    public AdverstiserMyaccount(Page page) {
        this.page = page;

        this.myProspect = page.locator("//span[text()='My Prospect']");
        this.myCandidate = page.locator("//span[text()='My Prospect']");
        this.myMeeting = page.locator("//span[text()='My Meeting']");
        this.myBilling = page.locator("//span[text()='My Billing']");
        this.faq = page.locator("//span[text()='FAQ' and@class='menu-item text-truncate']");
        this.basicInfo = page.locator("//span[text()='Basic Info' and@class='menu-item text-truncate']");
        this.password = page.locator("//span[text()='Password']");
        this.userManagement = page.locator("//span[text()='User Management']");
        this.privacyPolicy = page.locator("//span[text()='Privacy Policy']");
        this.termsAndCondition = page.locator("//span[text()='Terms & Condition']");
    }

    public Locator myProspect() { return myProspect; }
    public Locator myCandidate() { return myCandidate; }
    public Locator myMeeting() { return myMeeting; }
    public Locator myBilling() { return myBilling; }
    public Locator faq() { return faq; }
    public Locator basicInfo() { return basicInfo; }
    public Locator password() { return password; }
    public Locator userManagement() { return userManagement; }
    public Locator privacyPolicy() { return privacyPolicy; }
    public Locator termsAndCondition() { return termsAndCondition; }
}
