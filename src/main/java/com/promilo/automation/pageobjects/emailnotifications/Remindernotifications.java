package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Remindernotifications {

    private final Page page;
    private final Locator reminderText;
    
    private final Locator exploreButton;
    private final Locator greetingsText;
    private final Locator promiloLogo1;
    private final Locator promiloLogo2;
    private final Locator linkedin;
    private final Locator facebook;
    private final Locator instagram;
    private final Locator twitter;
    private final Locator getItonPlaystore;
    private final Locator getItonAppstore;

    public Remindernotifications(Page page) {
        this.page = page;
        this.reminderText = page.locator("(//p)[1]");
        this.greetingsText = page.locator("//span[@class='tinyMce-placeholder']");
        this.exploreButton = page.locator("//p[text()='Explore']");
        this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        this.linkedin = page.locator("//img[@alt='linkedin']");
        this.instagram = page.locator("//img[@alt='instagram']");
        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
        this.twitter = page.locator("//img[@alt='twitter']");
        this.facebook = page.locator("//img[@alt='facebook']");
    }

    public Locator reminderText() { return reminderText; }
    public Locator greetingsText() { return greetingsText; }
    public Locator exploreButton() { return exploreButton; }
    public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
    public Locator linkedin() { return linkedin; }
    public Locator twitter() { return twitter; }
    public Locator facebook() { return facebook; }
    public Locator instagram() { return instagram; }
    public Locator getItonPlaystore() { return getItonPlaystore; }
    public Locator getItonAppstore() { return getItonAppstore; }
}
