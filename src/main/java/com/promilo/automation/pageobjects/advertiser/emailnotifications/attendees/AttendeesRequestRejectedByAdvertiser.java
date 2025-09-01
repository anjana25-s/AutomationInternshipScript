package com.promilo.automation.pageobjects.advertiser.emailnotifications.attendees;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AttendeesRequestRejectedByAdvertiser{
	
	private final Page page;
	
	private final Locator GreetingText;
	private final Locator regardingText;
	private final Locator intrestText;
	private final Locator prospectCard;
	private final Locator myProspectButton;
	private final Locator promiloLogo1;
    private final Locator promiloLogo2;
	public AttendeesRequestRejectedByAdvertiser(Page page) {
		
		this.page= page;
		
		this.GreetingText= page.locator(" //span[contains(text(), 'Dear ')]");
		this.regardingText= page.locator("(//p)[1]").nth(1);
		this.intrestText= page.locator("(//p)[2]");
		this.prospectCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/1089417_1074785/Group%2012198_1.png']");
		this.myProspectButton= page.locator("//p[text()='My Prospect']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	    this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	
		
	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator regardingText() {return regardingText;}
	public Locator intrestText() {return intrestText;}
	public Locator prospectCard() {return prospectCard;}
	public Locator myProspectButton() {return myProspectButton;}
	public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
	
	
	
}
