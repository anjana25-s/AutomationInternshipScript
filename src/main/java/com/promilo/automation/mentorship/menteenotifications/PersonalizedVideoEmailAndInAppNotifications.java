package com.promilo.automation.mentorship.menteenotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PersonalizedVideoEmailAndInAppNotifications {
	
	private  Page page;
	
	
	private final Locator greetingText;
	private final Locator thankyouText;
	private final Locator regretText;
	private final Locator exploreText;
	private final Locator refundNote;
	private final Locator mentorshipsButton;
	private final Locator rejectedNotification;
	private final Locator RejectedInAppNotification;
	
	
	public PersonalizedVideoEmailAndInAppNotifications(Page page) {
		
		this.page=page;
		this.greetingText= page.locator("//span[text()='Greetings ']");
		this.thankyouText= page.locator("//p[contains(text(),'for reaching out for a Personalized Video')]");
		this.exploreText= page.locator("//p[contains(text(),'You can explore other ways ')]");
		this.refundNote= page.locator("(//p)[10]");
		this.regretText= page.locator("//p[contains(text(),'We regret to inform you that your Personalized')]");
		this.mentorshipsButton= page.locator("//span[text()='Mentorships']");
		this.rejectedNotification= page.locator("//p[contains(text(),'Thank you for reaching out for a Personalized Video Message ')]");
		this.RejectedInAppNotification = page.locator("//h6[text()='Your Personalized Video Message request wasn’t approved, but don’t stop now! Explore other exciting services offered by our Mentors.']");
		}
	
	
	public Locator greetingText() {return greetingText;}
	public Locator thankyouText() {return thankyouText;}
	public Locator regretText() {return regretText;}
	public Locator exploreText() {return exploreText;}
	public Locator refundNote() {return refundNote;}
	public Locator mentorshipsButton() {return mentorshipsButton;}
	public Locator rejectedNotification() {return rejectedNotification;}
	public Locator RejectedInAppNotification() {return RejectedInAppNotification;}


}
