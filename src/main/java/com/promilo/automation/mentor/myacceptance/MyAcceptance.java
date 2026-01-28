package com.promilo.automation.mentor.myacceptance;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.resources.BaseClass;

public class MyAcceptance extends BaseClass {

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
    private final Locator typeOfContent;
    private final Locator resourcesHighlightText;
    private final Locator brandEndorsementMoney;
    
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
    private final Locator videoServiceMenteeName;
    private final Locator videoServiceCampaignName;
    private final Locator videoServiceHighlight;
    private final Locator meetingTime;
    private final Locator meetingDate;
    private final Locator videoDurationAndTime;
    private final Locator personalizedVideoAmount;
    private final Locator personalizedVideoCampaignName;
    
    private final Locator oneOnOneCallMenteeName;
    private final Locator oneOnOneCallCampaignName;
    private final Locator oneOnOneCallHighLighttext;
    private final Locator oneOnOneCallLanguageChosen;
    private final Locator oneOnOneCallAmountPaid;
    
    private final Locator brandEndorsementMenteeName;
    private final Locator brandEndorsementCampaignName;
    private final Locator brandEndorsementHighLightText;
    private final Locator brandEndorsementType;
    private final Locator brandEndorsementStatus;
    
    private final Locator askQueryMenteeName;
    private final Locator askQueryCampaignName;
    private final Locator askQueryHighlightText;
    private final Locator askQueryMoney;
    private final Locator askQueryTime;
    private final Locator personalizedVideoAcceptanceStatus;
    private final Locator personalizedHighLightText;
    private final Locator personalizedMenteeName;

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
        this.viewQuery= page.locator("//p[text()='Ask Query']//following::span[text()='View Query']");
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
        this.videoServiceMenteeName= page.locator("(//div[@class='preferance-header-text'])[1]");
        this.videoServiceCampaignName= page.locator("//div[@class='text-gray-600 category-text-interest-card text-truncate text-wrap']");
        this.videoServiceHighlight= page.locator("//div[@class='category-text-interest-card text-truncate text-wrap']");
        this.meetingTime=page.locator("[class='card_detail-value time-date-tooltip']").first();
        this.meetingDate=page.locator("(//span[@class='card_detail-value'])[2]");
        this.typeOfContent=page.locator("//span[text()='Type:Birthday Wishes']");
        this.videoDurationAndTime=page.locator("(//p[text()='Personalized Video Message']//following::span[@class='card_detail-value time-date-tooltip'])[1]");
        this.personalizedVideoCampaignName=page.locator("(//p[text()='Personalized Video Message']//following::div[@class='font-10 text-gray-600 category-text-interest-card text-truncate  text-wrap'])[1]");
        this.personalizedVideoAmount=page.locator("(//p[text()='Personalized Video Message']//following::span[@class='card_detail-value'])[1]");
        this.oneOnOneCallCampaignName=page.locator("(//p[text()='1:1 Call']//following::div[@class='text-gray-600 category-text-interest-card text-truncate text-wrap'])[1]");
        this.oneOnOneCallMenteeName=page.locator("(//p[text()='1:1 Call']//following::div[@class='preferance-header-text'])[1]");
        this.oneOnOneCallHighLighttext=page.locator("(//p[text()='1:1 Call']//following::div[@class='category-text-interest-card text-truncate  text-wrap'])[1]");
        this.oneOnOneCallLanguageChosen=page.locator("(//p[text()='1:1 Call']//following::span[@class='card_detail-value language-field language-tooltip'])[1]");
        this.oneOnOneCallAmountPaid=page.locator("(//p[text()='1:1 Call']//following::span[@class='card_detail-value'])[1]");
        this.resourcesHighlightText=page.locator("(//p[text()='Resources']//following::div[@class='category-text-interest-card text-truncate text-wrap'])[1]");
        this.brandEndorsementMenteeName=page.locator("(//p[text()='Brand Endorsement']//following::div[@class='preferance-header-text'])[1]");
        this.brandEndorsementCampaignName=page.locator("(//p[text()='Brand Endorsement']//following::div[@class='font-10 text-gray-600 category-text-interest-card text-truncate  text-wrap'])[1]");
        this.brandEndorsementHighLightText=page.locator("(//p[text()='Brand Endorsement']//following::div[@class='font-10 category-text-interest-card text-truncate  text-wrap'])[1]");
        this.brandEndorsementMoney=page.locator("(//p[text()='Resources']//following::span[@class='card_detail-value'])[1]");
        this.brandEndorsementStatus=page.locator("//span[text()='Completed']").first();
        this.brandEndorsementType=page.locator("(//p[text()='Brand Endorsement']//following::span[text()='Type: Social Media Promotions'])[1]");
        this.askQueryMenteeName=page.locator("(//p[text()='Ask Query']//following::div[@class='preferance-header-text'])[1]");
        this.askQueryCampaignName=page.locator("(//p[text()='Ask Query']//following::div[@class='text-gray-600 category-text-interest-card text-truncate text-wrap'])[1]");
        this.askQueryHighlightText=page.locator("(//p[text()='Ask Query']//following::div[@class='category-text-interest-card text-truncate text-wrap'])[1]");
        this.askQueryMoney=page.locator("(//p[text()='Ask Query']//following::span[@class='card_detail-value'])[1]");
        this.askQueryTime=page.locator("(//p[text()='Ask Query']//following::div[@class='my_acceptance_card-keyword-normal'])[1]");
        this.personalizedVideoAcceptanceStatus= page.locator("//p[text()='Personalized Video Message']//following::div[@class='my-acceptance-status-tag'][1]");
        this.personalizedHighLightText= page.locator("(//p[text()='Personalized Video Message']//following::div[@class='font-10 category-text-interest-card text-truncate  text-wrap'])[1]");
        this.personalizedMenteeName= page.locator("(//p[text()='Personalized Video Message']//following::div[@class='preferance-header-text'])[1]");
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
    public Locator videoServiceMenteeName() {return videoServiceMenteeName;}
    public Locator videoServiceCampaignName() {return videoServiceCampaignName;}
    public Locator videoServiceHighlight() {return videoServiceHighlight;}
    public Locator meetingTime() {return meetingTime;}
    public Locator meetingDate() {return meetingDate;}
    public Locator typeOfContent() {return typeOfContent;}
    public Locator videoDurationAndTime() {return videoDurationAndTime;}
    public Locator personalizedVideoAmount() {return personalizedVideoAmount;}
    public Locator personalizedVideoCampaignName() {return personalizedVideoCampaignName;}
    public Locator oneOnOneCallMenteeName() {return oneOnOneCallMenteeName;}
    public Locator oneOnOneCallHighLighttext() {return oneOnOneCallHighLighttext;}
    public Locator oneOnOneCallCampaignName() {return oneOnOneCallCampaignName;}
    public Locator oneOnOneCallLanguageChosen() {return oneOnOneCallLanguageChosen;}
    public Locator oneOnOneCallAmountPaid() {return oneOnOneCallAmountPaid;}
    public Locator resourcesHighlightText() {return resourcesHighlightText;}
    public Locator resourcesmenteeName() {return resourcesmenteeName;}
    public Locator brandEndorsementMenteeName() {return brandEndorsementMenteeName;}
    public Locator brandEndorsementCampaignName() {return brandEndorsementCampaignName;}
    public Locator brandEndorsementHighLightText() {return brandEndorsementHighLightText;}
    public Locator brandEndorsementType() {return brandEndorsementType;}
    public Locator brandEndorsementMoney() {return brandEndorsementMoney;}
    public Locator brandEndorsementStatus() {return brandEndorsementStatus;}
    public Locator askQueryMenteeName() {return askQueryMenteeName;}
    public Locator askQueryHighlightText() {return askQueryHighlightText;}
    public Locator askQueryCampaignName() {return askQueryCampaignName;}
    public Locator askQueryMoney() {return askQueryMoney;}
    public Locator askQueryTime() {return askQueryTime;}
    public Locator personalizedVideoAcceptanceStatus() {return personalizedVideoAcceptanceStatus;}
    public Locator personalizedHighLightText() {return personalizedHighLightText;}
    public Locator personalizedMenteeName() {return personalizedMenteeName;}
}
