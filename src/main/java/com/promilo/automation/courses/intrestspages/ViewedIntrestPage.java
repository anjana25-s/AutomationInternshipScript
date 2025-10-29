package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ViewedIntrestPage {

    private final Page page;

    private final Locator coursesMenu;
    private final Locator seeMoreBtn;
    private final Locator lpuCard;
    private final Locator myInterestTab;
    private final Locator myPreferenceTab;
    private final Locator preferenceCard;

    public ViewedIntrestPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.seeMoreBtn = page.locator("//a[text()='See More']").nth(1);
        this.lpuCard = page.locator("//div[text()='LPU']").first();
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceTab = page.locator("//div[text()='My Preference']");
        this.preferenceCard = page.locator("//div[@class='my-preferance-card']");
    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator seeMoreBtn() { return seeMoreBtn; }
    public Locator lpuCard() { return lpuCard; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator preferenceCard() { return preferenceCard; }
}
