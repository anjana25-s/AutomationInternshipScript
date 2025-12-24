package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserMymeetingPage {

    private final Page page;

    // Button Locators
    private final Locator inviteButton;
    private final Locator hostButton;
    private final Locator joinMeetingButton;
    private final Locator acceptRequest;
    private final Locator rejectButton;
    private final Locator reschedule;
    private final Locator shortlistButton;
    private final Locator delegateButton;

    // Profile Locators
    private final Locator profileCard;
    private final Locator profileName;
    private final Locator profileLocation;
    private final Locator meetingDate;
    private final Locator meetingTime;
    private final Locator viewProfile;
    private final Locator activelyLookingTag;
    private final Locator jobs;

    // Comment and Communication Locators
    private final Locator commentButton;
    private final Locator commentTextfield;
    private final Locator sendButton;
    private final Locator commentCount;
    private final Locator reportAbuseButton;
    private final Locator reportAbuseTextfield;
    private final Locator reportAbuseSubmitButton;

    // Meeting Control Locators
    private final Locator audioMuteButton;
    private final Locator videoMuteButton;
    private final Locator hangUpButton;
    private final Locator riseHandButton;
    private final Locator presentScreen;
    private final Locator chatButton;
    private final Locator participants;
    private final Locator chatTextfield;
    private final Locator chatSendButton;
    private final Locator chatExitButton;
    private final Locator remarksExitIcon;

    // Video/Audio Mute After Join
    private final Locator videoMuteButtonAfterJoin;
    private final Locator audioMuteButtonAfterJoin;

    // Re-adding removed locators
    private final Locator meetingDateLocator;
    private final Locator meetingTimeLocator;
    private final Locator profileNameLocator;
    private final Locator profileLocationLocator;
    private final Locator campaignStatusLocator;
    private final Locator sourceLocator;
    private final Locator activeOnLocator;
    private final Locator updateLocator;

    // New locators that were previously missed
    private final Locator assignment;
    private final Locator screeningQuestions;

    // Redundant lowerCamelCase-corrected duplicate fields
    private final Locator videoMuteButtonAfterjoin;
    private final Locator audioMuteButtonAfterjoin;

    public AdvertiserMymeetingPage(Page page) {
        this.page = page;

        // Initialize Buttons
        this.inviteButton = page.locator("//button[text()='Invite']");
        this.hostButton = page.locator("//button[text()='Host']");
        this.joinMeetingButton = page.locator("//button[text()='Join a meeting']");
        this.acceptRequest = page.locator("//button[text()='Accept Request']");
        this.rejectButton = page.locator("//span[text()='Reject']");
        this.reschedule = page.locator("//span[text()='Reschedule']");
        this.shortlistButton = page.locator("//span[text()='Shortlist']");
        this.delegateButton = page.locator("//span[text()='Delegate']");

        // Profile Locators
        this.profileCard = page.locator("//div[@class='profile me-1 pointer my-acceptance-profile-pic-wrapper']");
        this.profileName = page.locator("//h2[@class='font-16 fw-bold mb-0 pt-50']");
        this.profileLocation = page.locator("p[class='location fw-400 font-12 mb-0']");
        this.meetingDate = page.locator("//div[@class='font-14 fw-500'][contains(text(),'Meeting Date')]/following-sibling::div");
        this.meetingTime = page.locator("//div[@class='font-14 fw-500'][contains(text(),'Meeting Time')]/following-sibling::div");
        this.viewProfile = page.locator("//span[text()='View Profile']");
        this.activelyLookingTag = page.locator("//span[contains(text(),'Actively looking for jobs')]");

        // Comment and Communication Locators
        this.commentButton = page.locator("//span[@class='comment-text']");
        this.commentTextfield = page.locator("//input[@placeholder='Add your comment']");
        this.sendButton = page.locator("//p[text()='Send']");
        this.commentCount = page.locator("//div[@class='comment-count']");
        this.reportAbuseButton = page.locator("//button[text()='Report Abuse']");
        this.reportAbuseTextfield = page.locator("//input[@id='remarks']");
        this.reportAbuseSubmitButton = page.locator("button[class='save-btn']");

        // Meeting Control Locators
        this.audioMuteButton = page.locator("button[class='rounded-full min-w-auto w-11 h-11 flex items-center justify-center bg-red-650 text-white']");
        this.videoMuteButton = page.locator("button[class='rounded-full min-w-auto w-11 h-11 flex items-center justify-center bg-red-650 text-white']");
        this.hangUpButton = page.locator("div[class='flex items-center justify-center  rounded-lg']");
        this.riseHandButton = page.locator("//p[text()='Raise Hand']");
        this.presentScreen = page.locator("//p[text()='Present Screen']");
        this.chatButton = page.locator("//p[text()='Chat' and @class='text-gray-900 text-sm']");
        this.participants = page.locator("//p[text()='Participants' and @class='text-gray-900 text-sm']");
        this.chatTextfield = page.locator("input[class='py-50 ps-1 text-white bg-dark rounded pr-10 focus:outline-none w-full']");
        this.chatSendButton = page.locator("span[class='absolute inset-y-0 right-0 flex mr-50 rotate-90 ']");
        this.chatExitButton = page.locator("button[class='text-white']");
        this.remarksExitIcon = page.locator("img[class='pointer close-icon']");

        // Video/Audio Mute After Join
        this.videoMuteButtonAfterJoin = page.locator("(//button[contains(@class, 'cursor-pointer')]//div[contains(@class, 'rounded-lg')])[1]/following::div[contains(@class, 'rounded-lg')][4]");
        this.audioMuteButtonAfterJoin = page.locator("(//button[contains(@class, 'cursor-pointer')]//div[contains(@class, 'rounded-lg')])[1]/following::div[contains(@class, 'rounded-lg')][2]");

        // Jobs Locator
        this.jobs = page.locator("//a[text()='Jobs']");

        // Assignment and ScreeningQuestions Locators
        this.assignment = page.locator("//span[text()='Assignment']");
        this.screeningQuestions = page.locator("//span[text()='Screening Questions']");

        // Re-adding removed locators
        this.meetingDateLocator = page.locator("//div[@class='font-14 fw-500'][contains(text(),'Meeting Date')]/following-sibling::div");
        this.meetingTimeLocator = page.locator("//div[@class='font-14 fw-500'][contains(text(),'Meeting Time')]/following-sibling::div");
        this.profileNameLocator = page.locator("//h2[@class='font-16 fw-bold mb-0 pt-50']");
        this.profileLocationLocator = page.locator("p[class='location fw-400 font-12 mb-0']");
        this.campaignStatusLocator = page.locator("//div[contains(text(),'Campaign Status')]");
        this.sourceLocator = page.locator("//div[contains(text(),'Source')]");
        this.activeOnLocator = page.locator("//div[contains(text(),'Active on')]");
        this.updateLocator = page.locator("//div[contains(text(),'Update')]");

        // Duplicates (redeclared with lowercase names)
        this.videoMuteButtonAfterjoin = this.videoMuteButtonAfterJoin;
        this.audioMuteButtonAfterjoin = this.audioMuteButtonAfterJoin;
    }

    // === Getters ===
    public Locator inviteButton() { return inviteButton; }
    public Locator hostButton() { return hostButton; }
    public Locator joinMeetingButton() { return joinMeetingButton; }
    public Locator acceptRequest() { return acceptRequest; }
    public Locator rejectButton() { return rejectButton; }
    public Locator reschedule() { return reschedule; }
    public Locator shortlistButton() { return shortlistButton; }
    public Locator delegateButton() { return delegateButton; }

    public Locator profileCard() { return profileCard; }
    public Locator profileName() { return profileName; }
    public Locator profileLocation() { return profileLocation; }
    public Locator meetingDate() { return meetingDate; }
    public Locator meetingTime() { return meetingTime; }
    public Locator viewProfile() { return viewProfile; }
    public Locator activelyLookingTag() { return activelyLookingTag; }

    public Locator commentButton() { return commentButton; }
    public Locator commentTextfield() { return commentTextfield; }
    public Locator sendButton() { return sendButton; }
    public Locator commentCount() { return commentCount; }
    public Locator reportAbuseButton() { return reportAbuseButton; }
    public Locator reportAbuseTextfield() { return reportAbuseTextfield; }
    public Locator reportAbuseSubmitButton() { return reportAbuseSubmitButton; }

    public Locator audioMuteButton() { return audioMuteButton; }
    public Locator videoMuteButton() { return videoMuteButton; }
    public Locator hangUpButton() { return hangUpButton; }
    public Locator riseHandButton() { return riseHandButton; }
    public Locator presentScreen() { return presentScreen; }
    public Locator chatButton() { return chatButton; }
    public Locator participants() { return participants; }
    public Locator chatTextfield() { return chatTextfield; }
    public Locator chatSendButton() { return chatSendButton; }
    public Locator chatExitButton() { return chatExitButton; }
    public Locator remarksExitIcon() { return remarksExitIcon; }

    public Locator jobs() { return jobs; }

    public Locator meetingDateLocator() { return meetingDateLocator; }
    public Locator meetingTimeLocator() { return meetingTimeLocator; }
    public Locator profileNameLocator() { return profileNameLocator; }
    public Locator profileLocationLocator() { return profileLocationLocator; }
    public Locator campaignStatusLocator() { return campaignStatusLocator; }
    public Locator sourceLocator() { return sourceLocator; }
    public Locator activeOnLocator() { return activeOnLocator; }
    public Locator updateLocator() { return updateLocator; }

    public Locator assignment() { return assignment; }
    public Locator screeningQuestions() { return screeningQuestions; }

    public Locator videoMuteButtonAfterjoin() { return videoMuteButtonAfterjoin; }
    public Locator audioMuteButtonAfterjoin() { return audioMuteButtonAfterjoin; }
}
