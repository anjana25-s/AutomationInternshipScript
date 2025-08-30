package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserHomepage {
    
    private final Page page;

    private final Locator myAccount;
    private final Locator overview;
    private final Locator makeProlet;
    private final Locator campaign;
    private final Locator helpAndSupport;
    private final Locator hamburger;
    private final Locator comingSoon;

    public AdvertiserHomepage(Page page) {
        this.page = page;

        this.myAccount = page.locator("//span[text()='My Account']");
        this.overview = page.locator("//span[text()='OverView']");
        this.makeProlet = page.locator("//span[text()='Make Prolet']");
        this.campaign = page.locator("//span[text()='Campaign +']");
        this.helpAndSupport = page.locator("//span[text()='Help & Support' and@class='menu-item text-truncate']");
        this.hamburger = page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']");
        this.comingSoon = page.locator("//p[text()='Coming Soon']");
    }

    public Locator myAccount() { return myAccount; }
    public Locator overview() { return overview; }
    public Locator makeProlet() { return makeProlet; }
    public Locator campaign() { return campaign; }
    public Locator helpAndSupport() { return helpAndSupport; }
    public Locator hamburger() { return hamburger; }
    public Locator comingSoon() { return comingSoon; }
}
