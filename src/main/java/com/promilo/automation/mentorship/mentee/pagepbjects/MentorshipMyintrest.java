package com.promilo.automation.mentorship.mentee.pagepbjects;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorshipMyintrest {
	
	private final Page page;
	
	//Get Mentor Call
	private final Locator cardImage;
	private final Locator mentorName;
	private final Locator experianceString;
	private final Locator experianceValue;
	private final Locator locationValue;
	private final Locator locationString;
	private final Locator serviceName;
	private final Locator serviceIcon;
	private final Locator viewProfile;
	private final Locator mentorData;
	private final Locator payOnline;
	private final Locator payButton;
	private final Locator mobileNumber;
	private final Locator walletButton;
	private final Locator phonePe;
	
	
	//Book a Meeting
	private final Locator mentorDataMeeting;
	private final Locator meetingDate;
	private final Locator meetingTime;
	private final Locator statusTag;
	private final Locator sendReminder;
	private final Locator cancelButton;
	
	// ask a query 
	private final Locator chatButton;
	public MentorshipMyintrest(Page page) {
		
		this.page= page;
		this.cardImage= page.locator("//div[@class='my-preferance-card-image']");
		this.mentorName= page.locator("//a[@class='preferance-header-text']");
		this.experianceString= page.locator("//div[@class='card_detail-label']").first();
		this.experianceValue= page.locator("[class='card_detail-value']").first();
		this.locationValue= page.locator("[class='card_detail-value']").nth(1);
		this.locationString= page.locator("//div[@class='card_detail-label']").nth(1);
		this.serviceName= page.locator("//span[@class='service-name']");
		this.serviceIcon= page.locator("//div[@class='service-icon']");
		this.viewProfile= page.locator("//span[text()='View Profile']");
		this.mentorData= page.locator("//span[@data-tooltip-id='OneOnOneMeetupCard-title-tooltip']");
		this.payOnline= page.locator("//span[contains(text(),'Pay Online')]");
		this.payButton= page.locator("//button[contains(text(),'Pay')]");
		this.mobileNumber= page.locator("//input[@placeholder='Mobile number']");
		this.walletButton= page.locator("//span[text()='Wallet']");
		this.phonePe= page.locator("//span[text()='PhonePe']");
		
		
		//Book a Meeting
		this.mentorDataMeeting= page.locator("//div[@class='font-medium category-text-interest-card text-truncate text-wrap']");
		this.meetingDate= page.locator("//div[@class='card_detail-wrapper-normal card-separator']");
		this.meetingTime= page.locator("//div[@class='card_detail-wrapper-normal']");
		this.statusTag= page.locator("//div[@class='interest_status-tag']");
		this.sendReminder= page.locator("//button[text()='Send Reminder']");
		this.cancelButton= page.locator("//span[text()='Cancel']");
		
		
		//ask a query
		this.chatButton= page.locator("//img[@alt='chat-message']");
		
		 
		
		
		
		
	}
	
	public Locator cardImage() {return cardImage;}
	public Locator mentorName() {return mentorName;}
	public Locator experianceString() {return experianceString;}
	public Locator experianceValue() {return experianceValue;}
	public Locator locationValue() {return locationValue;}
	public Locator locationString() {return locationString;}
	public Locator serviceName() {return serviceName;}
	public Locator serviceIcon() {return serviceIcon;}
	public Locator viewProfile() {return viewProfile;}
	public Locator mentorData() {return mentorData;}
	public Locator payOnline() {return payOnline;}
	public Locator payButton() {return payButton;}
	public Locator mobileNumber() {return mobileNumber;}
	public Locator walletButton() {return walletButton;}
	public Locator phonePe() {return phonePe;}
	
	
	//
	public Locator mentorDataMeeting() {return mentorDataMeeting;}
	public Locator meetingDate() {return meetingDate;}
	public Locator meetingTime() {return meetingTime;}
	public Locator statusTag() {return statusTag;}
	public Locator sendReminder() {return sendReminder;}
	public Locator cancelButton() {return cancelButton;}
	public Locator chatButton() {return chatButton;}
	

}
