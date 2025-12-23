package com.promilo.automation.mentorship.mentornotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PersonalizedVideoNotifications {
	
	private final Page page;
	
	private final Locator RejectedNotification;
	private final Locator dearText;
	private final Locator declinedMessage;
	private final Locator rejectedButton;
	private final Locator exploreOpportunities;
	private final Locator declinedInappNotifications;
	
	public PersonalizedVideoNotifications(Page page) {
		
		this.page= page;
				
		
		this.RejectedNotification= page.locator("//p[contains(text(),'You’ve Declined the Video Message Request from ')]");
		this.dearText= page.locator("//span[text()='Dear ']");
		this.declinedMessage= page.locator("//p[contains(text(),'We noticed you’ve declined the')]");
		this.rejectedButton= page.locator("//button[contains(text(),'Rejected')]");
		this.exploreOpportunities= page.locator("//span[contains(text(),'Opportunities')]");
		this.declinedInappNotifications= page.locator("//h6[text()='You’ve declined the request for a Personalized Video Message. New opportunities await whenever you’re ready!']");
		
		
		
		
		
	}

	public Locator RejectedNotification() {return RejectedNotification;}
	public Locator dearText() {return dearText;}
	public Locator declinedMessage() {return declinedMessage;}
	public Locator rejectedButton() {return rejectedButton;}
	public Locator exploreOpportunities() {return exploreOpportunities;}
	public Locator declinedInappNotifications() {return declinedInappNotifications;}
}
