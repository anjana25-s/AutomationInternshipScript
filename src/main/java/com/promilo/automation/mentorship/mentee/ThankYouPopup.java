package com.promilo.automation.mentorship.mentee;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ThankYouPopup {
	
	private final Page page;
	//ShortList
	private final Locator ThankYouMessage;
	private final Locator subscriptionText;
	private final Locator thankYouFooter;
	private final Locator myPreference;
	
	//Buy Resources
	private final Locator congatulationsMessage;
	private final Locator downloadManually;
	private final Locator footerText;
	private final Locator myDownloadsButton;
	
	//Ask Us
	private final Locator AskusCongratulations;
	private final Locator askUsFooterSection;
	
	
	//personalized video
	private final Locator registeringText;
	
	public ThankYouPopup(Page page) {
		
		this.page=page;
		//shortList
		this.ThankYouMessage= page.locator("//p[contains(text(),'Check your email, notifications, and WhatsApp for details on exclusive access.')]");
		this.thankYouFooter=page.locator("//p[contains(text(),'Easy Access to Your Selections—Your dashboard shows you the details of your chosen mentor under My ')]");
		this.subscriptionText= page.locator("//p[contains(text(),'You can take further steps, such as exploring video calls, direct calls, asking for queries, resource downloads, personalized video, and brand collaborations.')]");
		this.myPreference= page.locator("//a[contains(text(),'My Preference')]");
		this.downloadManually= page.locator("//div[text()='Download Manually']");
		this.footerText= page.locator("//div[@class='text-center ThankYou-footer']");
		this.myDownloadsButton= page.locator("//a[text()='My Downloads']");
		
		//Ask Us
		this.AskusCongratulations= page.locator("//p[text()='Congratulations! You did it. Our expert will answer shortly!']");
		this.askUsFooterSection= page.locator("//div[contains(@class,'text-center ThankYou-footer')]//p[1]");
		this.congatulationsMessage= page.locator("//p[text()='Congratulations! You did it. Your Resource is downloaded automatically. ']");
		this.registeringText= page.locator("//div[@class='ThankYou-message text-center text-blue-600 pt-2 ']");
	}
	//shortlist
	public Locator ThankYouMessage() {return ThankYouMessage;}
	public Locator subscriptionText() {return subscriptionText;}
	public Locator thankYouFooter() {return thankYouFooter;}
	public Locator myPreference() {return myPreference;}
	public Locator AskusCongratulations() {return AskusCongratulations;}
	public Locator askUsFooterSection() {return askUsFooterSection;}

	public Locator congatulationsMessage() {return congatulationsMessage;}
	public Locator downloadManually() {return downloadManually;}
	public Locator footerText() {return footerText;}
	public Locator myDownloadsButton() {return myDownloadsButton;}
	public Locator registeringText() {return registeringText;}
}
