package com.promilo.automation.mentorship.menteenotifications;

<<<<<<< HEAD
import java.time.LocalDate;

=======
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BrandEndorsementEmailAndInAppNotifications{
	
	
	private final Page page;
	
	private final Locator brandEndorsementInAppNotifcation;
	private final Locator brandEndorsementEmailNotifcation;
	private final Locator brandEndorsementgreetingsText;
	private final Locator brandEndorsementThankYouText;
	private final Locator brandEndorsementIntrestText;
	private final Locator brandEndorsementMeantimeText;
	private final Locator brandEndorsementPendingButton;
	private final Locator brandEndorsementViewProfileButton;
	private final Locator brandEndorsementRejectedInAppNotification;
	private final Locator brandEndorsementRejectedEmailNotification;
	
	
	
	public BrandEndorsementEmailAndInAppNotifications(Page page) {
		
		this.page=page;
		this.brandEndorsementInAppNotifcation= page.locator("(//div[h6[contains(text(),'Brand Endorsement Form Submitted')]]/p)[1]");
		this.brandEndorsementEmailNotifcation= page.locator("//p[text()='Your Brand Endorsement Request form to assignment has been submitted']");
		this.brandEndorsementgreetingsText= page.locator("//span[contains(text(),'Greetings')]");
		this.brandEndorsementThankYouText= page.locator("//p[contains(text(),'Thank you for taking the time')]");
		this.brandEndorsementIntrestText= page.locator("//p[contains(text(),'Your interest in partnering')]");
		this.brandEndorsementMeantimeText= page.locator("//p[contains(text(),'In the meantime, feel free to')]");
		this.brandEndorsementPendingButton= page.locator("//button[contains(text(),'Pending')]");
		this.brandEndorsementViewProfileButton= page.locator("//button[contains(text(),'Profile')]");
		this.brandEndorsementRejectedInAppNotification= page.locator("//h6[contains(text(),'Your request was not accepted by ')]");
		this.brandEndorsementRejectedEmailNotification= page.locator("//p[contains(text(),'has been Declined')]");
		
		
	}
	
	
	public Locator brandEndorsementInAppNotifcation() {return brandEndorsementInAppNotifcation;}
	public Locator brandEndorsementEmailNotifcation() {return brandEndorsementEmailNotifcation;}
	public Locator brandEndorsementgreetingsText() {return brandEndorsementgreetingsText;}
	public Locator brandEndorsementThankYouText() {return brandEndorsementThankYouText;}
	public Locator brandEndorsementIntrestText() {return brandEndorsementIntrestText;}
	public Locator brandEndorsementMeantimeText() {return brandEndorsementMeantimeText;}
	public Locator brandEndorsementPendingButton() {return brandEndorsementPendingButton;}
	public Locator brandEndorsementViewProfileButton() {return brandEndorsementViewProfileButton;}
	public Locator brandEndorsementRejectedInAppNotification() {return brandEndorsementRejectedInAppNotification;}
	public Locator brandEndorsementRejectedEmailNotification() {return brandEndorsementRejectedEmailNotification;}
	
}