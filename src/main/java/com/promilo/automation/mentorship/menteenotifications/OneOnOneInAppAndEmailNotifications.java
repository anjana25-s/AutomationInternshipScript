package com.promilo.automation.mentorship.menteenotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OneOnOneInAppAndEmailNotifications {
	
	
	
	private Page page;
	
	private final Locator rejectedNotification;
	private final Locator greetingText;
	private final Locator rejectedText;
	private final Locator rejectedButton;
	private final Locator dontWorryText;
	private final Locator pleaseNoteText;
	private final Locator learnText;
	private final Locator mentorshipsButton;
	
	private final Locator rejectedInAppNotification;
	
	public OneOnOneInAppAndEmailNotifications(Page page) {
		
		
		this.page= page;
		
		this.rejectedNotification= page.locator("//p[contains(text(),'Thank you for your 1:1 call request. Unfortunately, mentor')]");
		this.greetingText= page.locator("//span[contains(text(),'Greetings')]");
		this.rejectedText = page.locator("//p[contains(text(),'Thank you for your 1:1 call')]");
		this.rejectedButton= page.locator("//button[contains(text(),'Rejected')]");
		this.dontWorryText= page.locator("(//p)[12]");
		this.pleaseNoteText= page.locator("(//p)[11]");
		this.learnText= page.locator("//strong[contains(text(),'Keep learning, keep')]");
		this.mentorshipsButton= page.locator("//span[text()='Mentorships']");
		
		this.rejectedInAppNotification= page.locator("//h6[contains(text(),'has been rejected. Time to grow Promilite, Lets discover many other mentors.')]");
		
		
		
	}
	
	public Locator rejectedNotification() {return rejectedNotification;}
	public Locator greetingText() {return greetingText;}
	public Locator rejectedText() {return rejectedText;}
	public Locator rejectedButton() {return rejectedButton;}
	public Locator dontWorryText() {return dontWorryText;}
	public Locator pleaseNoteText() {return pleaseNoteText;}
	public Locator learnText() {return learnText;}
	public Locator mentorshipsButton() {return mentorshipsButton;}
	
	public Locator rejectedInAppNotification() {return rejectedInAppNotification;}
	
	
	

}
