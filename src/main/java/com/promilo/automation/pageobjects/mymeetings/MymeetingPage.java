package com.promilo.automation.pageobjects.mymeetings;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MymeetingPage {

    private final Page page;
    private final Locator myMeetingsButton;
    private final Locator allbutton;
    private final Locator CardJionbutton;
    private final Locator MeetingJoinbutton;
    private final Locator TodayButton;
    private final Locator UpcomingButton;
    private final Locator Completed;
    private final Locator Cancelled;
    private final Locator Expired;
    private final Locator CompletedStatus;
    private final Locator MymeetingCard;
    private final Locator viewAllButton;
    private final Locator videoTogglebutton;
    private final Locator audioMuteButton;
    private final Locator BrandName;
    private final Locator Threedotoption;
    private final Locator videoMutebuttonafterjoin;
    private final Locator audioMutebuttonafterJoin;
    private final Locator HangupButton;
    private final Locator RisehandButton;
    private final Locator 	presentScreen;
    private final Locator chatButton;
    private final Locator Participants;
    private final Locator chatTextfield;
    private final Locator chatSendbutton;
    private final Locator chatExitbutton;
    private final Locator ReportAbuseTextfield;
    private final Locator reportAbuseSubmitbutton;
    private final Locator reportAbusebutton;
    private final Locator remarksExiticon;
    
    
    
    

    // Constructor to initialize locators
    public MymeetingPage(Page page) {
        this.page = page;
        this.myMeetingsButton = page.locator("//span[normalize-space()='My Meetings']");
        this.allbutton = page.locator("//div[text()='All']");
        this.CardJionbutton = page.locator("//span[text()='Join Now']");
        this.MeetingJoinbutton = page.locator("//button[text()='Join now']");
        this.TodayButton = page.locator("//div[text()='Today']");
        this.UpcomingButton = page.locator("//div[text()='Upcoming']");
        this.Completed = page.locator("//div[text()='Completed']");
        this.Cancelled = page.locator("//div[text()='Cancelled']");
        this.Expired = page.locator("//div[text()='Expired']");
        this.CompletedStatus = page.locator("//span[text()='Completed']");
        this.MymeetingCard= page.locator("div[class='my-preferance-card-body card-body']");
        this.viewAllButton = page.locator("span[class='view-all-total-element text-blue-600 font-semibold']");
        this.videoTogglebutton= page.locator("g[clip-path='url(#a)']");
        this.audioMuteButton = page.locator("g[mask='url(#mask0_27_238)']");
        this.BrandName = page.locator("div[class='meeting-brandName']");
        this.Threedotoption = page.locator("(//button[contains(@class, 'cursor-pointer')]//div[contains(@class, 'rounded-lg')])[1]/following::div[contains(@class, 'rounded-lg')][5]");
        this.videoMutebuttonafterjoin= page.locator("(//button[contains(@class, 'cursor-pointer')]//div[contains(@class, 'rounded-lg')])[1]/following::div[contains(@class, 'rounded-lg')][4]");
        this.audioMutebuttonafterJoin= page.locator("(//button[contains(@class, 'cursor-pointer')]//div[contains(@class, 'rounded-lg')])[1]/following::div[contains(@class, 'rounded-lg')][2]");
        this.HangupButton = page.locator("div[class='flex items-center justify-center  rounded-lg']");//have to use firs().click();
        this.RisehandButton = page.locator("//p[text()='Raise Hand']");
        this.presentScreen = page.locator("//p[text()='Present Screen']");
        this.chatButton = page.locator("//p[text()='Chat' and @class='text-gray-900 text-sm']");
        this.Participants= page.locator("//p[text()='Participants' and @class='text-gray-900 text-sm']");
        this.chatTextfield= page.locator("input[class='py-50 ps-1 text-white bg-dark rounded pr-10 focus:outline-none w-full']");
        this.chatSendbutton = page.locator("span[class='absolute inset-y-0 right-0 flex mr-50 rotate-90 ']");
        this.chatExitbutton= page.locator("button[class='text-white']");
        this.ReportAbuseTextfield = page.locator("//input[@id='remarks']");
        this.reportAbuseSubmitbutton = page.locator("button[class='save-btn']");
        this.reportAbusebutton = page.locator("//button[text()='Report Abuse']");
        this.remarksExiticon= page.locator("img[class='pointer close-icon']");
        
        
    
        
        
    }

    // Getter method for MyMeetings button
    public Locator myMeetingsButton() {return myMeetingsButton;}
    public Locator allbutton() {return allbutton;}
    public Locator CardJionbutton() {return CardJionbutton; }
    public Locator MeetingJoinbutton() {return MeetingJoinbutton;}
    public Locator TodayButton() {return TodayButton;}
    public Locator UpcomingButton() {return UpcomingButton;}
    public Locator Completed() {return Completed;}
    public Locator Cancelled() {return Cancelled;}
    public Locator Expired() {return Expired;}
    public Locator CompletedStatus() {return CompletedStatus;}
    public Locator MymeetingCard() {return MymeetingCard;}
    public Locator viewAllButton() {return viewAllButton;}
    public Locator videoTogglebutton() {return videoTogglebutton;}
    public Locator audioMuteButton() {return audioMuteButton;}
    public Locator BrandName() {return BrandName;}
    public Locator Threedotoption() {return Threedotoption;}
    public Locator RisehandButton() {return RisehandButton;}
    public Locator chatButton() {return chatButton;}
    public Locator Participants() {return Participants;}
    public Locator chatTextfield() {return chatTextfield;}
    public Locator chatSendbutton() {return chatSendbutton;}
    public Locator chatExitbutton() {return chatExitbutton;}
    public Locator ReportAbuseTextfield() {return ReportAbuseTextfield;}
    public Locator reportAbuseSubmitbutton() {return reportAbuseSubmitbutton;}
    public Locator reportAbusebutton() {return reportAbusebutton;}
    public Locator remarksExiticon() {return remarksExiticon;}
    
    
    
}
