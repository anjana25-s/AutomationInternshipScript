package com.promilo.automation.mentor.myacceptance;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
<<<<<<< HEAD
import com.promilo.automation.resources.Baseclass;

public class MyAcceptance extends Baseclass {
=======

public class MyAcceptance  {
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation

    private final Page page;
    private final Locator myAcceptance;
    private final Locator videorescheduleButton;
    private final Locator firstDate;
    private final Locator slot;
    private final Locator continueButton;
    private final Locator modalContent;
    private final Locator videocallrejectButton;
    private final Locator proceedpopupCancelButton;
    private final Locator proceedButton;
    private final Locator videoCallstatusTag;
    private final Locator callStatustag;
    private final Locator videocallAcceptButton;
    private final Locator brandendorsementAccept;
    private final Locator brandendorsementReject;
    private final Locator persoanlizedVideoMessageReject;
    private final Locator personalizedVideoMessageAccept;
    private final Locator askQueryReject;
    private final Locator askQuery;
    private final Locator askquerycontinueButton;
    private final Locator viewQuery;
    private final Locator askqueryDays;
    private final Locator resourcesMoneyTag;
    private final Locator brandEnodorsementViewMessage;
    private final Locator enableDate;
    private final Locator availablesSlots;
    
    private final Locator oneOnoneCallAcceptButton;
    private final Locator oneOnoneCallRejectButton;
    private final Locator answerQuery;
    private final Locator messageTextfield;
    private final Locator messageSendbutton;
    private final Locator chatExitIcon;
    
    private final Locator resourcesCompletedTag;
    private final Locator resourcesmenteeName;
    private final Locator resourcesCampaginname;
    private final Locator resourcesCampaign;
    private final Locator resourceMoney;
    
    

    public MyAcceptance(Page page) {
        this.page = page;
        this.myAcceptance = page.locator("//a[normalize-space()='My Acceptance']");
        this.videorescheduleButton= page.locator("(//p[text()='Video Call']//following::img[@alt='AcceptanceReschedule'])[1]");
        this.firstDate= page.locator("//span[@class='flatpickr-day']");
        this.slot= page.locator("//li[@class='time-slot-box list-group-item']");
        this.continueButton= page.locator("//button[text()='Continue']");
        this.modalContent= page.locator("//div[@class='modal-content']");
        this.videocallrejectButton= page.locator("(//p[text()='Video Call']//following::span[text()='Reject'])[1]");
        this.proceedpopupCancelButton= page.locator("//button[text()='Cancel']");
        this.proceedButton= page.locator("//span[text()='Proceed']");
        this.videoCallstatusTag= page.locator("//p[text()='Video Call']//following::div[@class='my-acceptance-status-tag'][1]");
        this.callStatustag= page.locator("//p[text()='1:1 Call']//following::div[@class='my-acceptance-status-tag'][1]");
        this.videocallAcceptButton= page.locator("(//p[text()='Video Call']//following::span[text()='Accept'])[1]");
        this.brandendorsementAccept= page.locator("(//p[text()='Brand Endorsement']//following::span[text()='Accept'])[1]");
        this.brandendorsementReject= page.locator("(//p[text()='Brand Endorsement']//following::span[text()='Reject'])[1]");
        this.persoanlizedVideoMessageReject= page.locator("(//p[text()='Personalized Video Message']//following::span[text()='Reject'])[1]");
        this.personalizedVideoMessageAccept= page.locator("(//p[text()='Personalized Video Message']//following::span[text()='Accept'])[1]");
        this.askQueryReject= page.locator("(//p[text()='Ask Query']//following::span[text()='Reject'])[1]");
        this.askQuery= page.locator("(//p[text()='Ask Query']//following::span[text()='Answer Query'])[1]");
        this.askquerycontinueButton= page.locator("(//p[text()='Ask Query']//following::span[text()='Continue'])[1]");
        this.viewQuery= page.locator("(//p[text()='Ask Query']//following::span[text()='View Query'])[1]");
        this.askqueryDays= page.locator("(//span[contains(text(),'Days')])[1]");
        this.resourcesMoneyTag= page.locator("(//p[text()='Resources']//following::div[@class='my_acceptance_card-keyword-normal border-0'])[1]");
        this.brandEnodorsementViewMessage= page.locator("(//p[text()='Brand Endorsement']//following::span[text()='View Message'])[1]");
        this.enableDate= page.locator("//span[@class='flatpickr-day selected']");
        this.availablesSlots= page.locator("//li[@class='time-slot-box list-group-item']");
        this.oneOnoneCallAcceptButton=  page.locator("(//p[text()='1:1 Call']//following::span[text()='Accept'])[1]");
        this.oneOnoneCallRejectButton= page.locator("(//p[text()='1:1 Call']//following::span[text()='Reject'])[1]");
        this.answerQuery= page.locator("//span[text()='Answer Query']");
        this.messageTextfield=             page.locator("//input[@class='chat-body-user-input-bar']");
        this.messageSendbutton=  page.locator("[class='chat-body-user-input-send ']");
        this.chatExitIcon=             page.locator("//img[@alt='close chat popup']");
        this.resourcesCompletedTag= page.locator("(//p[text()='Resources']//following::span[text()='Completed'])[1]");
        this.resourcesmenteeName= page.locator("(//p[text()='Resources']//following::div[@class='preferance-header-text'])[1]");
        this.resourcesCampaginname= page.locator("(//p[text()='Resources']//following::div[@class='text-gray-600 category-text-interest-card text-truncate text-wrap'])[1]");
        this.resourcesCampaign= page.locator("(//p[text()='Resources']//following::div[@class='category-text-interest-card text-truncate text-wrap'])[1]");
        this.resourceMoney= page.locator("(//p[text()='Resources']//following::span[@class='card_detail-value'])[1]");


    }

    // Getter method for locator
    public Locator myAcceptance() {return myAcceptance; }
    public Locator videorescheduleButton() {return videorescheduleButton;}
    public Locator firstDate() {return firstDate;}
    public Locator slot() {return slot;}
    public Locator continueButton() {return continueButton;}
    public Locator modalContent() {return modalContent;}
    public Locator proceedButton() {return proceedButton;}
    public Locator videocallrejectButton() {return videocallrejectButton;}
    public Locator proceedpopupCancelButton() {return proceedpopupCancelButton;}
    public Locator videoCallstatusTag() {return videoCallstatusTag;}
    public Locator callStatustag() {return callStatustag;}
    public Locator videocallAcceptButton() {return videocallAcceptButton;}
    public Locator brandEndorsementAccept() {return brandendorsementAccept;}
    public Locator brandEndorsementReject() {return brandendorsementReject;}
    public Locator persoanlizedVideoMessageReject() {return persoanlizedVideoMessageReject;};
    public Locator personalizedVideoMessageAccept() {return personalizedVideoMessageAccept;}
    public Locator askQueryReject() {return askQueryReject;}
    public Locator askQuery() {return askQuery;}
    public Locator askquerycontinueButton() {return askquerycontinueButton;}
    public Locator viewQuery() {return viewQuery;}
    public Locator askqueryDays() {return askqueryDays;}
    public Locator resourcesMoneyTag() {return resourcesMoneyTag;}
    public Locator brandEnodorsementViewMessage() {return brandEnodorsementViewMessage;}
    public Locator enableDate() {return enableDate;}
    public Locator availablesSlots() {return availablesSlots;}
    public Locator oneOnoneCallAcceptButton() {return oneOnoneCallAcceptButton;}
    public Locator oneOnoneCallRejectButton() {return oneOnoneCallRejectButton;}
    public Locator answerQuery() {return answerQuery;}
    public Locator messageTextfield() {return messageTextfield;}
    public Locator messageSendbutton() {return messageSendbutton;}
    public Locator chatExitIcon() {return chatExitIcon;}
    public Locator ResourcesCompletedTag() {return resourcesCompletedTag;}
    public Locator menteeName() {return resourcesmenteeName;}
    public Locator resourcesCampaginname() {return resourcesCampaginname;}
    public Locator resourcesCampaign() {return resourcesCampaign;}
    public Locator resourceMoney() {return resourceMoney;}
}
