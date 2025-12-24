package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ViewedIntrestPage {

    private final Page page;

    private final Locator coursesMenu;
    private final Locator seeMoreBtn;
    private final Locator lpuCard;
    private final Locator myInterestTab;
    private final Locator myPreferenceTab;
<<<<<<< HEAD
    private final Locator mentorshipCard;
    
    
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
	private final Locator myPreferenceTalkToExpertButton;
	private final Locator myPreferenceFreeVideoCounselling;

    public ViewedIntrestPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.seeMoreBtn = page.locator("//a[text()='See More']").nth(1);
        this.lpuCard = page.locator("//div[text()='LPU']").first();
        this.myInterestTab = page.locator("//span[normalize-space()='My Interest']");
        this.myPreferenceTab = page.locator("[class='tab text-center w-50 ms-1 pointer active']");
        this.mentorshipCard = page.locator("//div[@class='my-preferance-card-body card-body']");
        this.campaignName= page.locator("[class='preferance-header-text']");
		this.meetingDate= page.locator("[class='card_detail-value']").first();
		this.meetingTime= page.locator("[class='card_detail-value']").nth(1);
		this.serviceIcon= page.locator("//img[@alt='campaign-icon']");
		this.serviceName= page.locator("//span[text()='Viewed']");
		this.location=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").first();
		this.productTitle=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").nth(1);
		this.statusTag=page.locator("[class='interest_status-tag']");
		this.chooseSlotText= page.locator("[class='fw-500 font-18 text-primary mb-50']");
		this.currentMonth= page.locator("[class='flatpickr-current-month']");
		this.duration= page.locator("[class='text-truncate}']").first();
		this.fee= page.locator("[class='text-truncate}']").nth(1);
		this.thankYouPopUpExitIcon= page.locator("//img[@alt='closeIcon Ask us']");
		this.cardPicture= page.locator("[class='my-preferance-card-image']");
		this.myPreferenceTalkToExpertButton= page.locator("//span[text()='Talk to Experts']");
		this.myPreferenceFreeVideoCounselling= page.locator("//span[text()='Free Video Counselling']");
    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator seeMoreBtn() { return seeMoreBtn; }
    public Locator lpuCard() { return lpuCard; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator mentorshipCard() { return mentorshipCard; }
    
    
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
	public Locator myPreferenceTalkToExpertButton() {return myPreferenceTalkToExpertButton;}
	public Locator myPreferenceFreeVideoCounselling() {return myPreferenceFreeVideoCounselling;}

=======
    private final Locator preferenceCard;

    public ViewedIntrestPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.seeMoreBtn = page.locator("//a[text()='See More']").nth(1);
        this.lpuCard = page.locator("//div[text()='LPU']").first();
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceTab = page.locator("//div[text()='My Preference']");
        this.preferenceCard = page.locator("//div[@class='my-preferance-card']");
    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator seeMoreBtn() { return seeMoreBtn; }
    public Locator lpuCard() { return lpuCard; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator preferenceCard() { return preferenceCard; }
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
}
