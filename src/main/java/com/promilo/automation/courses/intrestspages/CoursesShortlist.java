package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CoursesShortlist {

    private final Page page;

    // Navigation
    private final Locator coursesTab;
    private final Locator searchCourseInput;

    // Shortlist Form
    private final Locator shortlistIcon;
    private final Locator userNameField;
    private final Locator userEmailField;
    private final Locator userMobileField;
    private final Locator preferredLocationDropdown;
    private final Locator locationOptions;
    private final Locator submitShortlistButton;

    private final Locator verifyAndProceedButton;

    // Thank you popup
    private final Locator thankYouPopup;
    private final Locator thankYouCloseIcon;

    // Mailosaur email content
    private final Locator emailCard;
    private final Locator emailGreeting;
    private final Locator emailGreatNewsText;
    private final Locator emailHelpText;
    private final Locator emailDoText;
    private final Locator counselingLink;
    private final Locator talkToExpertLink;
  

    // My Interest & Preference Tabs
    private final Locator myInterestTab;
    private final Locator myPreferenceTab;

    //My interest
    private final Locator visitTab;
    private final Locator campaignName;
    private final Locator visitingDate;
    private final Locator visitingTime;
    private final Locator serviceIcon;
    private final Locator serviceName;
    private final Locator productTitle;
    private final Locator statusTag;
    private final Locator chooseSlotText;
    private final Locator currentMonth;
    private final Locator availableDate;
    private final Locator firstTimeSlot;
    private final Locator duration;
    private final Locator fee;
    private final Locator cardPicture;
    private final Locator location;

    public CoursesShortlist(Page page) {
        this.page = page;

        // Navigation
        this.coursesTab = page.locator("//a[text()='Courses']");
        this.searchCourseInput = page.locator("//input[@placeholder='Search Colleges and Courses']");

        // Shortlist Form
        this.shortlistIcon = page.locator("//img[@class='shortlist-icon shortlist']");
        this.userNameField = page.locator("//input[@name='userName']").nth(1);
        this.userEmailField = page.locator("//input[@id='userEmail']").nth(1);
        this.userMobileField = page.locator("//input[@name='userMobile']").nth(1);
        this.preferredLocationDropdown = page.locator("[id='preferredLocation']").nth(1);
        this.locationOptions = page.locator("//div[@class='option w-100']");
        this.submitShortlistButton = page.locator("//button[@type='button' and @class='submit-btm-askUs btn btn-primary']");

        // OTP
        this.verifyAndProceedButton = page.locator("//button[text()='Verify & Proceed']");

        // Thank you popup
        this.thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        this.thankYouCloseIcon = page.locator("img[alt='closeIcon Ask us']");

        // Mailosaur email content
        this.emailCard = page.locator("//p[contains(text(),'Great news')]").first();
        this.emailGreeting = page.locator("//span[contains(text(),'Hi ')]");
        this.emailGreatNewsText = page.locator("//p[contains(text(),'Great news!')]");
        this.emailHelpText = page.locator("//p[contains(text(),'To help you make the best')]");
        this.emailDoText = page.locator("//p[contains(text(),'Do')]");
        this.counselingLink = page.locator("//span[contains(text(),'Counseling')]");
        this.talkToExpertLink = page.locator("//span[contains(text(),'Talk to Expert ')]");

        // My Interest & Preference Tabs
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceTab = page.locator("//div[text()='My Preference']");

        // Campus Visit related
        this.visitTab = page.locator("//span[text()='My Visits']");
        this.campaignName = page.locator("[class='preferance-header-text']");
        this.visitingDate = page.locator("[class='card_detail-value']").first();
        this.visitingTime = page.locator("[class='card_detail-value']").nth(1);
        this.serviceIcon = page.locator("[class='service-icon']");
        this.serviceName = page.locator("[class='service-name']");
        this.location = page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").first();
        this.productTitle = page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").nth(1);
        this.statusTag = page.locator("[class='btn-reuested-blue-outlined filled my-interest-status-tag']");
        this.chooseSlotText = page.locator("[class='fw-500 font-18 text-primary mb-50']");
        this.currentMonth = page.locator("[class='flatpickr-current-month']");
        this.availableDate = page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
        this.firstTimeSlot = page.locator("//li[@class='time-slot-box list-group-item']").first();
        this.duration = page.locator("[class='text-truncate}']").first();
        this.fee = page.locator("[class='text-truncate}']").nth(1);
        this.cardPicture = page.locator("[class='my-preferance-card-image']");
    }

    // Getters
    public Locator coursesTab() { return coursesTab; }
    public Locator searchCourseInput() { return searchCourseInput; }
    public Locator shortlistIcon() { return shortlistIcon; }
    public Locator userNameField() { return userNameField; }
    public Locator userEmailField() { return userEmailField; }
    public Locator userMobileField() { return userMobileField; }
    public Locator preferredLocationDropdown() { return preferredLocationDropdown; }
    public Locator locationOptions() { return locationOptions; }
    public Locator submitShortlistButton() { return submitShortlistButton; }
    public Locator verifyAndProceedButton() { return verifyAndProceedButton; }
    public Locator thankYouPopup() { return thankYouPopup; }
    public Locator thankYouCloseIcon() { return thankYouCloseIcon; }
    public Locator emailCard() { return emailCard; }
    public Locator emailGreeting() { return emailGreeting; }
    public Locator emailGreatNewsText() { return emailGreatNewsText; }
    public Locator emailHelpText() { return emailHelpText; }
    public Locator emailDoText() { return emailDoText; }
    public Locator counselingLink() { return counselingLink; }
    public Locator talkToExpertLink() { return talkToExpertLink; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator visitTab() { return visitTab; }
    public Locator campaignName() { return campaignName; }
    public Locator visitingDate() { return visitingDate; }
    public Locator visitingTime() { return visitingTime; }
    public Locator serviceIcon() { return serviceIcon; }
    public Locator serviceName() { return serviceName; }
    public Locator location() { return location; }
    public Locator productTitle() { return productTitle; }
    public Locator statusTag() { return statusTag; }
    public Locator chooseSlotText() { return chooseSlotText; }
    public Locator currentMonth() { return currentMonth; }
    public Locator availableDate() { return availableDate; }
    public Locator firstTimeSlot() { return firstTimeSlot; }
    public Locator duration() { return duration; }
    public Locator fee() { return fee; }
    public Locator cardPicture() { return cardPicture; }
    
    
 // OTP
    public Locator otpDigit(int index) {
        return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
    }
}
