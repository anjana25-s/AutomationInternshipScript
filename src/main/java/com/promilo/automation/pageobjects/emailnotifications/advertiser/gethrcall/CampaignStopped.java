package com.promilo.automation.pageobjects.emailnotifications.advertiser.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CampaignStopped {
	
	private final Page page;
	
	
	private final Locator  GreetingText;
	private final Locator  regretText;
	private final Locator understandText;
	private final Locator managePost1;
	private final Locator managePost2;
	private final Locator approvedText;
	private final Locator patienceText;
	private final Locator whatYoucanDoText;
	
	public CampaignStopped(Page page) {
		this.page= page;
		
this.GreetingText= page.locator("//span[contains(text(), 'Dear ')]");
this.regretText= page.locator("(//p)[1]");
this.understandText= page.locator("(//p)[2]");
this.managePost1= page.locator("//p[text()='Manage Posting']").first();
this.managePost2= page.locator("//p[text()='Manage Posting']").nth(1);
this.approvedText= page.locator("//p[text()='Once approved we will promptly notify you.']");
this.patienceText= page.locator("//p[text()='Thank you for your patience and adherence to our guidelines.']");
this.whatYoucanDoText= page.locator("//ul");


	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator regretText() {return regretText;}
	public Locator understandText() {return understandText;}
	public Locator managePost1() {return managePost1;}
	public Locator managePost2() {return managePost2;}
	public Locator approvedText() {return approvedText;}
	public Locator patienceText() {return patienceText;}
	public Locator whatYoucanDoText() {return whatYoucanDoText;}
}

