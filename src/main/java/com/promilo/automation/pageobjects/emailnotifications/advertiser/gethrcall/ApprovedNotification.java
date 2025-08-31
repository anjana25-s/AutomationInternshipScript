package com.promilo.automation.pageobjects.emailnotifications.advertiser.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ApprovedNotification {
	
	private final Page page;
	
	private final Locator greetingText;
	private final Locator regardingText;
	private final Locator approvedText;
	private final Locator candidateCard;
	private final Locator myProspect;
	private final Locator promiloLogo1;
	private final Locator promiloLogo2;
	public  ApprovedNotification(Page page) {
		
		this.page=page;
		this.greetingText= page.locator("//span[contains(text(), 'Dear ')]");
		this.regardingText= page.locator("//p[text()='This is regarding your ']");
		this.approvedText= page.locator("(//p)[2]");
		this.candidateCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/1089417_1074785/Group%2012198.png']");
		this.myProspect= page.locator("//p[text()='My Prospect']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
		
		
		
	}
	public Locator greetingText() {return greetingText;}
	public Locator regardingText() {return regardingText;}
	public Locator approvedText() {return approvedText;}
	public Locator candidateCard() {return candidateCard;}
	public Locator myProspect() {return myProspect;}
	public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }

}
