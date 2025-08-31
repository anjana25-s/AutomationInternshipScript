package com.promilo.automation.advertiser.emailnotifications.intrests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class JobsIntrestsReceivedCancelled {
	
	private final Page page;
	private final Locator GreetingText;
	private final Locator regardingText;
	private final Locator MeetingCancelledText;
	private final Locator bestRegardsText;
	private final Locator promiloLogo1;
	private final Locator promiloLogo2;
	
	public  JobsIntrestsReceivedCancelled (Page page) {
		this.page= page;
		this.GreetingText= page.locator("//span[contains(text(), 'Dear ')]");
		this.regardingText= page.locator("//p[text()='This is regarding your ']");
		this.MeetingCancelledText= page.locator("(//p)[2]");
		this.bestRegardsText= page.locator("span[class='StageModuleParagraph_readonly__51BpS']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
		
		
	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator regardingText() {return regardingText;}
	public Locator MeetingCancelledText() {return MeetingCancelledText;}
	public Locator bestRegardsText() {return bestRegardsText;}
	public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }

}
