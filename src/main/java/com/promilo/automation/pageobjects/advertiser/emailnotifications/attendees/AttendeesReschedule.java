package com.promilo.automation.pageobjects.advertiser.emailnotifications.attendees;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AttendeesReschedule {
	
	private final Page page;
	private final Locator GreetingText;
	private final Locator regardsText;
	private final Locator  rescheduleText; 
	private final Locator prospectCard;
	private final Locator respondToRequest;
	private final Locator promiloLogo1;
    private final Locator promiloLogo2;
    private final Locator bestRegardsText;
	
	public AttendeesReschedule(Page page) {
		
		this.page= page;
		this.GreetingText= page.locator("//span[contains(text(), 'Dear ')]");
		this.regardsText= page.locator("//span[ contains(text(),'Posting')]");
		this.rescheduleText= page.locator("(//p)[2]");
		this.prospectCard= page.locator("//img[@src='https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/1089417_1074785/Group%2012198_1.png']");
		this.respondToRequest = page.locator("//p[text()='Respond to Request']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        this.bestRegardsText= page.locator("//span[@class='StageModuleParagraph_readonly__51BpS']");
	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator regardsText() {return regardsText;}
	public Locator rescheduleText() {return rescheduleText;}
	public Locator prospectCard() {return prospectCard;}
	public Locator respondToRequest() {return respondToRequest;}
	public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
    public Locator bestRegardsText() {return bestRegardsText;}

}
