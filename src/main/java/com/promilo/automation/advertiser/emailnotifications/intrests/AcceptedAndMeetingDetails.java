package com.promilo.automation.advertiser.emailnotifications.intrests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AcceptedAndMeetingDetails {
	private final Page page;
	private final Locator GreetingText;
	private final Locator ThankYouText;
	private final Locator meetingScheduleTime;
	private final Locator MeetingCard;
	private final Locator myMeetingButton;
	private final Locator excitedToMeet;
	private final Locator bestRegardsText;
	
	public AcceptedAndMeetingDetails(Page page) {
		
		this.page= page;
		this.GreetingText= page.locator("//span[contains(text(), 'Dear ')]");
		this.ThankYouText= page.locator("//p[text()='Thank you for accepting the meeting request from ']");
		this.meetingScheduleTime= page.locator("//p[text()='The meeting is now officially scheduled for ']");
		this.MeetingCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/1089417_1074785/Group%2012198_1.png']");
		this.myMeetingButton= page.locator("//p[text()='My Meeting']");
		this.excitedToMeet= page.locator("//p[text()=' is excited to meet you!']");
		this.bestRegardsText= page.locator("span[class='StageModuleParagraph_readonly__51BpS']");
		
		
	}
	public Locator GreetingText() {return GreetingText;}
	public Locator ThankYouText() {return ThankYouText;}
	public Locator meetingScheduleTime() {return meetingScheduleTime;}
	public Locator MeetingCard() {return MeetingCard;}
	public Locator myMeetingButton() {return myMeetingButton;}
	public Locator bestRegardsText() {return bestRegardsText;}
	

}
