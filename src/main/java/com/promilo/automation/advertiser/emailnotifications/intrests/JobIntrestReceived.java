package com.promilo.automation.advertiser.emailnotifications.intrests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class JobIntrestReceived{
	

	private final Page page;
	
	private final Locator regardingText;
	private final Locator GreetingText;
	private final Locator excitingNews;
	private final Locator scheduledText;
	private final Locator viewAndManageText;
	private final Locator prospectCard;
	private final Locator myProspectButton;
	private final Locator BestRegards;
	public JobIntrestReceived(Page page) {
		
		this.page= page;
		this.GreetingText= page.locator("//span[contains(text(), 'Dear ')]");
		this.regardingText= page.locator("//p[contains(text(), 'regarding')]");
		this.excitingNews= page.locator("//p[contains(text(), 'captured')]");
		this.scheduledText= page.locator("//p[contains(text(), 'has applied for ')]");
		this.viewAndManageText= page.locator("//p[contains(text(), 'manage')]");
		this.prospectCard= page.locator("//img[@src='https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/1089417_1074785/Group%2012198_1.png']");
		this.myProspectButton= page.locator("//p[text()='My Prospect']");
		this.BestRegards= page.locator("span[class='StageModuleParagraph_readonly__51BpS']");
		
		
		
		
	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator regardingText() {return regardingText;}
	public Locator excitingNews() {return excitingNews;}
	public Locator scheduledText() {return scheduledText;}
	public Locator viewAndManageText() {return viewAndManageText;}
	public Locator prospectCard() {return prospectCard;}
	public Locator myProspectButton() {return myProspectButton;}
	public Locator BestRegards() {return BestRegards;}
}