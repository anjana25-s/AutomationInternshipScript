package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RescheduleRejected {
	
	private final Locator GreetingText;
	private final Locator RegretText;
	private final Locator unfortunateText;
	private final Locator Explore;
	private final Locator promiloLogo1;
    private final Locator promiloLogo2;
    private final Locator linkedin;
    private final Locator facebook;
    private final Locator instagram;
    private final Locator twitter;
    private final Locator getItonPlaystore;
    private final Locator getItonAppstore;
	
	private final Page page;
	
	public RescheduleRejected(Page page) {
		
		this.GreetingText= page.locator("span[class='tinyMce-placeholder']");
		this.RegretText= page.locator("//div[@Style='position: relative;']").nth(2);
		this.unfortunateText= page.locator("(//p)[2]");
		this.Explore= page.locator("//p[text()='Explore']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        this.linkedin = page.locator("//img[@alt='linkedin']");
        this.instagram = page.locator("//img[@alt='instagram']");
        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
        this.twitter = page.locator("//img[@alt='twitter']");
        this.facebook = page.locator("//img[@alt='facebook']");
		
		
		
		this.page= page;
		
	}
	
    public Locator	GreetingText() {return GreetingText;}
    public Locator RegretText() {return RegretText;}
    public Locator unfortunateText() {return unfortunateText;}
    public Locator Explore() {return Explore;}
    public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
    public Locator linkedin() { return linkedin; }
    public Locator twitter() { return twitter; }
    public Locator facebook() { return facebook; }
    public Locator instagram() { return instagram; }
    public Locator getItonPlaystore() { return getItonPlaystore; }
    public Locator getItonAppstore() { return getItonAppstore; }

	
	

}
