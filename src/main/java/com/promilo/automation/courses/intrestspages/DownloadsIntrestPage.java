package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DownloadsIntrestPage {

    private final Locator coursesMenu;
    private final Locator downloadBrochureBtn;
    private final Locator userNameField;
    private final Locator userMobileField;
    private final Locator userEmailField;
    private final Locator finalDownloadBtn;
    private final Locator verifyProceedBtn;
    private final Locator thankYouPopup;
    private final Locator thankYouCloseIcon;
    private final Locator myInterestTab;
    private final Locator myPreferenceTab;
    private final Locator downloadsTab;
    private final Locator totalResults;
<<<<<<< HEAD
    
    
    private final Locator visitTab;
	private final Locator campaignName;
	private final Locator visitingDate;
	private final Locator visitingTime;
	private final Locator serviceIcon;
	private final Locator serviceName;
	private final Locator location;
	private final Locator productTitle;
	private final Locator statusTag;
	private final Locator chooseSlotText;
	private final Locator currentMonth;
	private final Locator availableDate;
	private final Locator firstTimeSlot;
	private final Locator thankYouPopUpExitIcon;
	private final Locator duration;
	private final Locator fee;
	private final Locator cardPicture;
	
	
	

    // Navigation
    private final Locator coursesTab;
    private final Locator downloadBrochureButton;

    
    private final Locator brochureSubmitButton;

    // OTP
    private final Locator verifyAndProceedButton;


    // Mailosaur content
    private final Locator mailosaurEmailCard;
    private final Locator mailosaurGreeting;
    private final Locator mailosaurInterestText;
    private final Locator mailosaurDownloadBrochureLink;
    private final Locator mailosaurFinalDownloadLink;
	
	

    private final Page page;

    public DownloadsIntrestPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.downloadBrochureBtn = page.locator("//span[text()='Download Brochure']").first();
        this.userNameField = page.locator("//input[@name='userName']").nth(1);
        this.userMobileField = page.locator("//input[@name='userMobile']").nth(1);
        this.userEmailField = page.locator("//input[@id='userEmail']").nth(1);
        this.finalDownloadBtn = page.locator("//button[text()='Download Brochure']");
        this.verifyProceedBtn = page.locator("//button[text()='Verify & Proceed']");
        this.thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
        );
        this.thankYouCloseIcon = page.locator("img[alt='closeIcon Ask us']");
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceTab = page.locator("//div[text()='My Preference']");
        this.downloadsTab = page.locator("//div[text()='Downloads']");
        this.totalResults = page.locator("//span[text()='Total Results of ']");
        
        
        
        this.visitTab=page.locator("//span[text()='My Visits']");
		this.campaignName= page.locator("[class='preferance-header-text']");
		this.visitingDate= page.locator("[class='card_detail-value']").first();
		this.visitingTime= page.locator("[class='card_detail-value']").nth(1);
		this.serviceIcon= page.locator("[class='service-icon']");
		this.serviceName= page.locator("[class='service-name']");
		this.location=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").first();
		this.productTitle=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").nth(1);
		this.statusTag=page.locator("[class='btn-reuested-blue-outlined filled my-interest-status-tag']");
		this.chooseSlotText= page.locator("[class='fw-500 font-18 text-primary mb-50']");
		this.currentMonth= page.locator("[class='flatpickr-current-month']");
		this.availableDate=page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
		this.firstTimeSlot=page.locator("//li[@class='time-slot-box list-group-item']").first();
		this.thankYouPopUpExitIcon= page.locator("//img[@alt='closeIcon Ask us']");
		this.duration= page.locator("[class='text-truncate}']").first();
		this.fee= page.locator("[class='text-truncate}']").nth(1);
		this.cardPicture= page.locator("[class='my-preferance-card-image']");
		
		
		
		
		// Navigation
        this.coursesTab = page.locator("//a[text()='Courses']");
        this.downloadBrochureButton = page.locator("//span[text()='Download Brochure']").first();

        
        this.brochureSubmitButton = page.locator("//button[text()='Download Brochure']");

        // OTP
        this.verifyAndProceedButton = page.locator("//button[text()='Verify & Proceed']");


        // Mailosaur Email content
        this.mailosaurEmailCard = page.locator("//p[contains(text(),'Download Your Meetup Details ')]").first();
        this.mailosaurGreeting = page.locator("//span[contains(text(),'Hi ')]");
        this.mailosaurInterestText = page.locator("//p[contains(text(),'You expressed interest in')]");
        this.mailosaurDownloadBrochureLink = page.locator("//a[contains(text(),'Click here for download brochure')]");
        this.mailosaurFinalDownloadLink = page.locator("(//a)[27]");
    

    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator downloadBrochureBtn() { return downloadBrochureBtn; }
    public Locator userNameField() { return userNameField; }
    public Locator userMobileField() { return userMobileField; }
    public Locator userEmailField() { return userEmailField; }
    public Locator finalDownloadBtn() { return finalDownloadBtn; }
    public Locator verifyProceedBtn() { return verifyProceedBtn; }
    public Locator thankYouPopup() { return thankYouPopup; }
    public Locator thankYouCloseIcon() { return thankYouCloseIcon; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator downloadsTab() { return downloadsTab; }
    public Locator totalResults() { return totalResults; }
    
    public Locator visitTab() {return visitTab;}
	public Locator campaignName() {return campaignName;}
	public Locator visitingDate() {return visitingDate;}
	public Locator visitingTime() {return visitingTime;}
	public Locator serviceIcon() {return serviceIcon;}
	public Locator serviceName() {return serviceName;}
	public Locator location() {return location;}
	public Locator productTitle() {return productTitle;}
	public Locator statusTag() {return statusTag;}
	public Locator chooseSlotText() {return chooseSlotText;}
	public Locator currentMonth() {return currentMonth;}
	public Locator availableDate() {return availableDate;}
	public Locator firstTimeSlot() {return firstTimeSlot;}
	public Locator duration() {return duration;}
	public Locator thankYouPopUpExitIcon() {return thankYouPopUpExitIcon;}
	public Locator cardPicture() {return cardPicture;}
	public Locator fee() {return fee;}
	
	
	
    public Locator coursesTab() { return coursesTab; }
    public Locator downloadBrochureButton() { return downloadBrochureButton; }

        public Locator brochureSubmitButton() { return brochureSubmitButton; }

    public Locator otpDigit(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }

    public Locator verifyAndProceedButton() { return verifyAndProceedButton; }


    public Locator mailosaurEmailCard() { return mailosaurEmailCard; }
    public Locator mailosaurGreeting() { return mailosaurGreeting; }
    public Locator mailosaurInterestText() { return mailosaurInterestText; }
    public Locator mailosaurDownloadBrochureLink() { return mailosaurDownloadBrochureLink; }
    public Locator mailosaurFinalDownloadLink() { return mailosaurFinalDownloadLink; }
}


=======

    private final Page page;

    public DownloadsIntrestPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.downloadBrochureBtn = page.locator("//span[text()='Download Brochure']").first();
        this.userNameField = page.locator("//input[@name='userName']").nth(1);
        this.userMobileField = page.locator("//input[@name='userMobile']").nth(1);
        this.userEmailField = page.locator("//input[@id='userEmail']").nth(1);
        this.finalDownloadBtn = page.locator("//button[text()='Download Brochure']");
        this.verifyProceedBtn = page.locator("//button[text()='Verify & Proceed']");
        this.thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
        );
        this.thankYouCloseIcon = page.locator("img[alt='closeIcon Ask us']");
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceTab = page.locator("//div[text()='My Preference']");
        this.downloadsTab = page.locator("//div[text()='Downloads']");
        this.totalResults = page.locator("//span[text()='Total Results of ']");
    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator downloadBrochureBtn() { return downloadBrochureBtn; }
    public Locator userNameField() { return userNameField; }
    public Locator userMobileField() { return userMobileField; }
    public Locator userEmailField() { return userEmailField; }
    public Locator finalDownloadBtn() { return finalDownloadBtn; }
    public Locator verifyProceedBtn() { return verifyProceedBtn; }
    public Locator thankYouPopup() { return thankYouPopup; }
    public Locator thankYouCloseIcon() { return thankYouCloseIcon; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator downloadsTab() { return downloadsTab; }
    public Locator totalResults() { return totalResults; }
}
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
