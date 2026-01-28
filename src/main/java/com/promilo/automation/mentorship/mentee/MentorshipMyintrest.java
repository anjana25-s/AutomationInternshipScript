package com.promilo.automation.mentorship.mentee;


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
	
	private final Locator deliverTime;
	private final Locator languageChosen;
	private final Locator totalAmount;
	
	
	//Book a Meeting
	private final Locator mentorDataMeeting;
	private final Locator meetingDate;
	private final Locator meetingTime;
	private final Locator statusTag;
	private final Locator sendReminder;
	private final Locator cancelButton;
	private final Locator bookMeetingMentorName;
	private final Locator bookMeetingMentorData;
	private final Locator bookMeetingServiceName;
	
	// ask a query 
	private final Locator chatButton;
	private final Locator rescheduleIcon;
	private final Locator rescheduleSlots;
	private final Locator rescheduleSuccesPopup;
	private final Locator rescheduleAcceptBuuton;
	private final Locator myInterestTab;
	private final Locator askQueryMentorName;
	private final Locator askQueryMentorData;
	private final Locator askQueryDuration;
	private final Locator askQueryServiceName;
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
		this.deliverTime=page.locator("[class='card_detail-value']");
		this.languageChosen=page.locator("[class='card_detail-value language-field language-tooltip']").first();
		this.totalAmount=page.locator("[class='card_detail-value']").first();
		
		
		
		
		//Book a Meeting
		this.mentorDataMeeting= page.locator("//div[@class='font-medium category-text-interest-card text-truncate text-wrap']");
		this.meetingDate= page.locator("//div[@class='card_detail-wrapper-normal card-separator']");
		this.meetingTime= page.locator("[class='card_detail-wrapper-normal']");
		this.statusTag= page.locator("//div[@class='interest_status-tag']");
		this.sendReminder= page.locator("//button[text()='Send Reminder']");
		this.cancelButton= page.locator("//span[text()='Cancel']");
		this.bookMeetingMentorName=page.locator("[class='card-content-header-text fw-bolder mb-0 text-wrap pointer text-truncate']");
		this.bookMeetingServiceName=page.locator("[class='d-flex justify-content-center align-items-center']");
		this.bookMeetingMentorData=page.locator("[class='font-medium category-text-interest-card text-truncate text-wrap']");
		//ask a query
		this.chatButton= page.locator("//img[@alt='chat-message']");
		this.rescheduleSuccesPopup= page.locator("//div[@class='modal-content']");
		this.myInterestTab= page.locator("//span[text()='My Interest']");
		this.rescheduleIcon= page.locator("img.rescheduled-notification-icon");
		this.rescheduleSlots= page.locator("//div[@class='rescheduledSlots-time w-100 font-12 text-center mb-50 ']");
		this.rescheduleAcceptBuuton= page.locator("//button[text()='Accept Request']");
		this.askQueryMentorName= page.locator("//a[@class='card-content-header-text fw-bolder mb-0 text-wrap pointer text-truncate']");
		this.askQueryMentorData= page.locator("//div[@class='font-medium category-text-interest-card text-truncate text-wrap']");
		this.askQueryDuration= page.locator("//div[@class='card_detail-value']");
		this.askQueryServiceName= page.locator("//span[text()='Ask Query']");
				
		 
		
		
		
		
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
	public Locator deliverTime() {return deliverTime;}
	public Locator languageChosen() {return languageChosen;}
	public Locator totalAmount() {return totalAmount;}
	
	
	
	
	//
	public Locator mentorDataMeeting() {return mentorDataMeeting;}
	public Locator meetingDate() {return meetingDate;}
	public Locator meetingTime() {return meetingTime;}
	public Locator statusTag() {return statusTag;}
	public Locator sendReminder() {return sendReminder;}
	public Locator cancelButton() {return cancelButton;}
	public Locator chatButton() {return chatButton;}
	public Locator myInterestTab() {return myInterestTab;}
	public Locator rescheduleIcon() {return rescheduleIcon;}
	public Locator rescheduleSlots() {return rescheduleSlots;}
	public Locator rescheduleAcceptBuuton() {return rescheduleAcceptBuuton;}
	public Locator rescheduleSuccesPopup() {return rescheduleSuccesPopup;}
	public Locator askQueryMentorName() {return askQueryMentorName;}
	public Locator askQueryMentorData() {return askQueryMentorData;}
	public Locator askQueryDuration() {return askQueryDuration;}
	public Locator askQueryServiceName() {return askQueryServiceName;}
	public Locator bookMeetingMentorName() {return bookMeetingMentorName;}
	public Locator bookMeetingMentorData() {return bookMeetingMentorData;}
	public Locator bookMeetingServiceName() {return bookMeetingServiceName;}

}
