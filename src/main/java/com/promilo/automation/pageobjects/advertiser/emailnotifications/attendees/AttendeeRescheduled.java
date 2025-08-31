package com.promilo.automation.pageobjects.advertiser.emailnotifications.attendees;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AttendeeRescheduled {
	
	private final Page page;
	private final Locator GreetingText;
	private final Locator regardingText;
	private final Locator rescheduleMeetingText;
	private final Locator scheduledTimeText;
	private final Locator notifyText;
	private final Locator thankYouForPatience;

    private final Locator promiloLogo1;
    private final Locator promiloLogo2;
	
	public AttendeeRescheduled(Page page) {
		
		this.page= page;
		this.GreetingText= page.locator("//span[contains(text(), 'Hi ')]");
		this.regardingText= page.locator("(//p)[1]");
		this.rescheduleMeetingText= page.locator("(//p)[2]");
		this.scheduledTimeText= page.locator("(//p)[3]");
		this.notifyText= page.locator("(//p)[4]");
		this.thankYouForPatience= page.locator("//p[text()='Thank you for your patience.']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
		
		
		
	}
	

	public Locator GreetingText() {return GreetingText;}
	public Locator regardingText() {return regardingText;}
	public Locator rescheduleMeetingText() {return rescheduleMeetingText;}
	public Locator scheduledTimeText() {return scheduledTimeText;}
	public Locator notifyText() {return notifyText;}
	public Locator thankYouForPatience() {return thankYouForPatience;}
    public Locator promiloLogo1() { return promiloLogo1; }
	public Locator promiloLogo2() { return promiloLogo2; }
}
