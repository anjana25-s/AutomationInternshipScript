package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ApplyJobNotifications {
    private final Page page;

    private final Locator greetingsText;
    private final Locator paraText;
    private final Locator getHrCallButton;
    private final Locator applyNowButton;
    private final Locator regardsText;
    private final Locator takeActionNow;
    
    private final Locator promiloLogo1;
	private final Locator promiloLogo2;
	private final Locator Linkedin;
	private final Locator facebook;
	private final Locator instagram;
	private final Locator twitter;
	private final Locator getItonPlaystore;
	private final Locator getItonAppstore;

    public ApplyJobNotifications(Page page) {
        this.page = page;

        this.greetingsText = page.locator("//span[@class='tinyMce-placeholder']");
        this.paraText = page.locator("//div[@class='content-labels content-labels--cs content-labels--paragraph StageColumn_contentLabels__u4zmN']").first();
        this.getHrCallButton = page.locator("//p[text()='Get HR Call']");
        this.applyNowButton = page.locator("//p[text()='Apply Now']");
        this.regardsText = page.locator("//span[@class='StageModuleParagraph_readonly__51BpS']s"); // ✅ Added initialization
        this.takeActionNow = page.locator("//strong[text()='Take Action Now']");
        

   	 promiloLogo1= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        promiloLogo2= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        Linkedin = page.locator("//img[@alt='linkedin']");
	        instagram = page.locator("//img[@alt='instagram']");
	        getItonPlaystore= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        getItonAppstore= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");

	        twitter = page.locator("//img[@alt='twitter']");

	        facebook = page.locator("//img[@alt='facebook']");

    }

    public Locator greetingsText() { return greetingsText; }
    public Locator paraText() { return paraText; }
    public Locator getHrCallButton() { return getHrCallButton; }
    public Locator applyNowButton() { return applyNowButton; }
    public Locator regardsText() { return regardsText; }
    public Locator takeActionNow() { return takeActionNow; }
    
    public Locator promiloLogo1() {return promiloLogo1;}
    public Locator promiloLogo2() {return promiloLogo2;}
    public Locator Linkedin() {return Linkedin;}
    public Locator twitter() {return twitter;}
    public Locator facebook() {return facebook;}
    public Locator instagram() {return instagram;}
    public Locator getItonPlaystore() {return getItonPlaystore;}
    public Locator getItonAppstore() {return getItonAppstore;}
}
