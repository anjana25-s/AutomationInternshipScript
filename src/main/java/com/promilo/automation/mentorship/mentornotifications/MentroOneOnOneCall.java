package com.promilo.automation.mentorship.mentornotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentroOneOnOneCall {
	
	
	private final Page page;
	
	//payment confirmation locators
	private final Locator paymentConfirmation;
	private final Locator hiText;
	private final Locator congratulationsText;
	private final Locator gratefulText;
	
	
	
	
	//connection begin notification
	private final Locator detailUnlockedNotification;
	private final Locator dearText;
	private final Locator acceptedText;
	private final Locator completedButton;
	private final Locator totalAmount;
	private final Locator whatNextText;
	private final Locator reachoutText;
	private final Locator viewContactsButton;
	
	
	//In-App Notifications
	private final Locator contactDetailsUnlocked;
	private final Locator earnedNotifications;
	
	
	//Rejected Notification
	private final Locator declinedNotification;
	private final Locator RejecteddearText;
	private final Locator rejectedDeclineText;
	private final Locator rejectedButton;
	private final Locator decisionText;
	private final Locator reviewRequestButton;
	private final Locator rejectedInAppNotification;
	
	
	
	public MentroOneOnOneCall(Page page) {
		
		this.page=page;
		//payment confirmation locators
		this.paymentConfirmation= page.locator("//p[contains(text(),'Congratulations on completing a for one on one call request! You’ve earned ')]");
		this.hiText= page.locator("//span[contains(text(),'Hi ')]");
		this.congratulationsText= page.locator("//p[contains(text(),'Congratulations on completing')]");
		this.gratefulText= page.locator("//p[contains(text(),'We’re grateful for your')]");
		
		
		
		//connection begin notification
		this.detailUnlockedNotification= page.locator("//p[contains(text(),'Let the Connection Begin')]");
		this.dearText= page.locator("//span[contains(text(),'Dear ')]");
		this.acceptedText= page.locator("//p[contains(text(),'You’ve successfully accepted')]");
		this.completedButton= page.locator("//button[text()='Completed']");
		this.totalAmount= page.locator("//p[contains(.,'₹')]/text()");
		this.whatNextText= page.locator("//strong[text()='What’s next?']");
		this.reachoutText= page.locator("//ul");
		this.viewContactsButton=page.locator("//span[contains(text(),'Contacts')]");
		
		
		//In-App Notifications
		this.contactDetailsUnlocked= page.locator("//h6[contains(text(),'You’ve unlocked the mentee’s contact details ')]");
		this.earnedNotifications= page.locator("//h6[contains(text(),'for completing a one on one call request.')]");
		
		
		this.declinedNotification= page.locator("//p[text()='Declined the 1:1 Call Request? No Problem!']");
		this.RejecteddearText= page.locator("//span[text()='Dear ']");
		this.rejectedDeclineText= page.locator("//p[contains(text(),'1:1 call request for your campaign')]");
		this.rejectedButton= page.locator("//button[contains(text(),'Rejected')]");
		this.decisionText= page.locator("//p[contains(text(),'We respect your decision and')]");
		this.reviewRequestButton= page.locator("//span[contains(text(),'Review')]");
		
		this.rejectedInAppNotification= page.locator("//h6[contains(text(),'Explore more opportunities to connect in the future!')]");
	
		

		
		
		
		
		
		
		
	}
	//payment confirmation locators
	public Locator paymentConfirmation() {return paymentConfirmation;}
	public Locator hiText() {return hiText;} 
	public Locator congratulationsText() {return congratulationsText;}
	public Locator gratefulText() {return gratefulText;}
	
	//connection begin notification
	public Locator detailUnlockedNotification() {return detailUnlockedNotification;}
	public Locator dearText() {return dearText;}
	public Locator acceptedText() {return acceptedText;}
	public Locator completedButton() {return completedButton;}
	public Locator totalAmount() {return totalAmount;}
	public Locator whatNextText() {return whatNextText;}
	public Locator reachoutText() {return reachoutText;}
	public Locator viewContactsButton() {return viewContactsButton;}
	
	//In-App Notifications
	public Locator contactDetailsUnlocked() {return contactDetailsUnlocked;}
	public Locator earnedNotifications() {return earnedNotifications;}
	
	public Locator declinedNotification() {return declinedNotification;}
	public Locator RejecteddearText() {return RejecteddearText;}
	public Locator rejectedDeclineText() {return rejectedDeclineText;}
	public Locator rejectedButton() {return rejectedButton;}
	public Locator decisionText() {return decisionText;}
	public Locator reviewRequestButton() {return reviewRequestButton;}
	public Locator rejectedInAppNotification() {return rejectedInAppNotification;}

	
	

	

	
	
	
	
	

}
