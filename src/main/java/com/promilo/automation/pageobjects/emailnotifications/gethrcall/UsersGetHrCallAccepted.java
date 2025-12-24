package com.promilo.automation.pageobjects.emailnotifications.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UsersGetHrCallAccepted {
	
	private final Page page;
	private final Locator GreetingText;
	private final Locator AcceptedText;
	private final Locator jobCard;
	private final Locator myPreference;
	private final Locator regardsText;
	private final Locator promiloLogo1;
    private final Locator promiloLogo2;
    private final Locator linkedin;
    private final Locator facebook;
    private final Locator instagram;
    private final Locator twitter;
    private final Locator getItonPlaystore;
    private final Locator getItonAppstore;
	
	public UsersGetHrCallAccepted(Page page) {
		this.page= page;
		
		this.GreetingText= page.locator("//span[contains(@class, 'Greetings')]");
		this.AcceptedText= page.locator("(//p)[1]");
		this.jobCard= page.locator("//img[@src='https://d15k2d11r6t6rl.cloudfront.net/pub/bfra/69vjd3ad/qxi/t4t/x8f/Job%20Get%20an%20HR%20Call%20Completed.png']");
		this.myPreference= page.locator("//p[text()='My Preference']");
		this.regardsText= page.locator("span[class='StageModuleParagraph_readonly__51BpS']");
		
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        this.linkedin = page.locator("//img[@alt='linkedin']");
        this.instagram = page.locator("//img[@alt='instagram']");
        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
        this.twitter = page.locator("//img[@alt='twitter']");
        this.facebook = page.locator("//img[@alt='facebook']");
		
	}
	

	public Locator GreetingText() {return GreetingText;}
	public Locator AcceptedText() {return AcceptedText;}
	public Locator jobCard() {return jobCard;}
	public Locator myPreference() {return myPreference;}
	public Locator regardsText() {return regardsText;}
	
	public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
    public Locator linkedin() { return linkedin; }
    public Locator twitter() { return twitter; }
    public Locator facebook() { return facebook; }
    public Locator instagram() { return instagram; }
    public Locator getItonPlaystore() { return getItonPlaystore; }
    public Locator getItonAppstore() { return getItonAppstore; }
	
	
}
