package com.promilo.automation.mentorship.mentornotifications;

import com.microsoft.playwright.Locator;

public class MentorBrandEndorsementsNotifications{
	
	private final com.microsoft.playwright.Page page;
	
	private final Locator brandEndorsementdearText;
	private final Locator brandEndorsementgoodNewsText;
	private final Locator brandEndorsementCollaborationText;
	private final Locator brandEndorsementPendingButton;
	private final Locator brandEndorsementViewMessageLink;
	private final Locator brandEndorsementAcceptHere;
	
	private final Locator brandEndorsementInAppNotification;
	private final Locator brandEndorsementRejectEmailNotification;
	private final Locator brandEndorsementRejectInAppNotification;
	
	private final Locator intrestShownMailNotification;
	private final Locator dearText;
	private final Locator goodnewsText;
	private final Locator pendingCard;
	private final Locator acceptButton;
	private final Locator backButton;


	
	
	public MentorBrandEndorsementsNotifications(com.microsoft.playwright.Page page) {
		
		this.page= page;
		this.brandEndorsementdearText= page.locator("//span[text()='Dear ']");
		this.brandEndorsementgoodNewsText= page.locator("//p[text()='Good news! ']");
		this.brandEndorsementCollaborationText= page.locator("//p[contains(normalize-space(.), 'This could be the start of an exciting')]");
		this.brandEndorsementPendingButton= page.locator("//button[contains(normalize-space(.), 'Pending')]");
		this.brandEndorsementViewMessageLink= page.locator("//p[contains(normalize-space(.), 'Message')]");
		this.brandEndorsementAcceptHere= page.locator("//span[contains(text(),'Accept')]");
		this.brandEndorsementInAppNotification= page.locator("//h6[contains(text(),'waiting to connect with you for a Brand Endorsement opportunity! Accept the request to unlock their contact details')]");
		this.brandEndorsementRejectEmailNotification= page.locator("//p[contains(text(),'Request Declined')]");
		this.brandEndorsementRejectInAppNotification= page.locator("//h6[contains(text(),'You’ve declined the Brand Endorsement inquiry from ')]");
		this.intrestShownMailNotification= page.locator("//p[contains(text(),'Opportunity Alert')]");
		this.dearText= page.locator("//span[@class='tinyMce-placeholder']");
		this.goodnewsText= page.locator("//p[contains(text(),'Good news!')]");
		this.pendingCard= page.locator("//button[normalize-space()='Pending']");
		this.acceptButton= page.locator("//span[contains(text(),'Accept')]");
		this.backButton= page.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']//*[name()='path' and contains(@fill,'currentCol')]");
				
		
		
		
	}
	
	public Locator brandEndorsementdearText() {return brandEndorsementdearText;}
	public Locator brandEndorsementgoodNewsText() {return brandEndorsementgoodNewsText;}
	public Locator brandEndorsementCollaborationText() {return brandEndorsementCollaborationText;}
	public Locator brandEndorsementPendingButton() {return brandEndorsementPendingButton;}
	public Locator brandEndorsementViewMessageLink() {return brandEndorsementViewMessageLink;}
	public Locator brandEndorsementAcceptHere() {return brandEndorsementAcceptHere;}
	public Locator brandEndorsementInAppNotification() {return brandEndorsementInAppNotification;}
	public Locator brandEndorsementRejectEmailNotification() {return brandEndorsementRejectEmailNotification;}
	public Locator brandEndorsementRejectInAppNotification() {return brandEndorsementRejectInAppNotification;}
	public Locator intrestShownMailNotification() {return intrestShownMailNotification;}
	public Locator dearText() {return dearText;}
	public Locator goodnewsText() {return goodnewsText;}
	public Locator pendingCard() {return pendingCard;}
	public Locator acceptButton() {return acceptButton;}
	public Locator backButton() {return backButton;}


	
	
	
}