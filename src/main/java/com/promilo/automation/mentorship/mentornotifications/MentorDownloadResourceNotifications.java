package com.promilo.automation.mentorship.mentornotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorDownloadResourceNotifications {
	
	private Page page;
	
	private final Locator someoneDownloadedNotification;
	private final Locator dearText;
	private final Locator resourceDownloadedText;
	private final Locator appreciatedText;
	private final Locator keepInspiringText;
	private final Locator viewResouceButton;
	
	private final Locator paymentNotification;
	private final Locator hiText;
	private final Locator congratulationsText;
	private final Locator greateFullText;
	
	public MentorDownloadResourceNotifications(Page page) {
		
		
		this.page=page;
		this.someoneDownloadedNotification= page.locator("//p[text()='Someone Just Downloaded Your Resource!']");
		this.dearText= page.locator("//span[text()='Dear ']");
		this.resourceDownloadedText= page.locator("//p[contains(text(),'thrilled to share that')]");
		this.appreciatedText= page.locator("//p[contains(text(),'expertise is not just appreciated—it’s actively')]");
		this.keepInspiringText= page.locator("//p[contains(text(),'inspiring mentees and sharing your valuable')]");
		this.viewResouceButton= page.locator("//span[text()='View Resource']");
		this.paymentNotification= page.locator("//p[contains(text(),'completing a for resources request')]");
		this.hiText= page.locator("//span[text()='Hi ']");
		this.congratulationsText= page.locator("//p[contains(text(),'for resources request! You’ve earned')]");
		this.greateFullText= page.locator("//p[contains(text(),'commitment and expertise in delivering')]");
	}
	
	public Locator someoneDownloadedNotification() {return someoneDownloadedNotification;}
	public Locator dearText() {return dearText;}
	public Locator resourceDownloadedText() {return resourceDownloadedText;}
	public Locator appreciatedText() {return appreciatedText;}
	public Locator keepInspiringText() {return keepInspiringText;}
	public Locator viewResouceButton() {return viewResouceButton;}
	public Locator paymentNotification() {return paymentNotification;}
	public Locator hiText() {return hiText;}
	public Locator congratulationsText() {return congratulationsText;}
	public Locator greateFullText() {return greateFullText;}

}
