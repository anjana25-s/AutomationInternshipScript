package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TalkToExpertPage {

        
    private final Locator visitTab;
	private final Locator campaignName;
	private final Locator meetingDate;
	private final Locator meetingTime;
	private final Locator serviceIcon;
	private final Locator serviceName;
	private final Locator location;
	private final Locator productTitle;
	private final Locator statusTag;
	private final Locator chooseSlotText;
	private final Locator currentMonth;
	private final Locator duration;
	private final Locator fee;
	private final Locator thankYouPopUpExitIcon;
	private final Locator cardPicture;
	private final Locator myPreferenceButton;
    private final Locator myInterestTab;
    private final Locator talktoExpertNotification;
    private final Locator notificationIcon;
    private final Locator talkToExpertRejectNotification;


    private final Page page;

    public TalkToExpertPage(Page page) {
        this.page = page;

                
        this.visitTab=page.locator("//span[text()='My Visits']");
		this.campaignName= page.locator("[class='preferance-header-text']");
		this.meetingDate= page.locator("[class='card_detail-value']").first();
		this.meetingTime= page.locator("[class='card_detail-value']").nth(1);
		this.serviceIcon= page.locator("//img[@alt='campaign-icon']");
		this.serviceName= page.locator("//span[text()='Talk to Experts']");
		this.location=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").first();
		this.productTitle=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").nth(1);
		this.statusTag=page.locator("[class='interest_status-tag']");
		this.chooseSlotText= page.locator("[class='fw-500 font-18 text-primary mb-50']");
		this.currentMonth= page.locator("[class='flatpickr-current-month']");
		this.duration= page.locator("[class='text-truncate}']").first();
		this.fee= page.locator("[class='text-truncate}']").nth(1);
		this.thankYouPopUpExitIcon= page.locator("//img[@alt='closeIcon Ask us']");
		this.cardPicture= page.locator("[class='my-preferance-card-image']");
		this.myPreferenceButton= page.locator("[class='tab text-center w-50 ms-1 pointer active']");
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.talktoExpertNotification= page.locator("//h6[contains(text(), \"Official! Your Talk with an Expert\")]");
        this.notificationIcon= page.locator("//img[@alt='In-App-Notification-Icon']");
        this.talkToExpertRejectNotification= page.locator("//h6[contains(text(),'Update on Your')]").first();


    }

    
    
    public Locator visitTab() {return visitTab;}
	public Locator campaignName() {return campaignName;}
	public Locator meetingDate() {return meetingDate;}
	public Locator meetingTime() {return meetingTime;}
	public Locator serviceIcon() {return serviceIcon;}
	public Locator serviceName() {return serviceName;}
	public Locator location() {return location;}
	public Locator productTitle() {return productTitle;}
	public Locator statusTag() {return statusTag;}
	public Locator chooseSlotText() {return chooseSlotText;}
	public Locator currentMonth() {return currentMonth;}
	public Locator duration() {return duration;}
	public Locator fee() {return fee;}
	public Locator thankYouPopUpExitIcon() {return thankYouPopUpExitIcon;}
	public Locator cardPicture() {return cardPicture;}
	public Locator myPreferenceButton() {return myPreferenceButton;}
    public Locator myInterestTab() { return myInterestTab; }
    public Locator talktoExpertAcceptNotification() {return talktoExpertNotification;}
    public Locator notificationIcon() {return notificationIcon;}
    public Locator talkToExpertRejectNotification() {return talkToExpertRejectNotification;}



}
