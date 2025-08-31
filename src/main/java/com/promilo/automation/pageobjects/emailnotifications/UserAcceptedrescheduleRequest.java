package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UserAcceptedrescheduleRequest {
	
	private final Page page;
	private final Locator GreetingText;
	private final Locator confirmationText;
	private final Locator jobCard;
	private final Locator myMeetings;
	private final Locator wishText;
	
	 private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;
	
	public UserAcceptedrescheduleRequest(Page page) {
		this.page= page;
		
		this.GreetingText= page.locator(".tinyMce-placeholder");
		this.confirmationText= page.locator("//p[text()='Great news! Your confirmation for the rescheduled interview for ']");
		this.jobCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/pub/bfra/69vjd3ad/cax/ynt/7lt/Screenshot%202024-12-23%20114445.png']");
		this.myMeetings = page.locator("//p[text()='My Meetings']");
		this.wishText= page.locator("//p[text()='We wish you the best for your interview. If you have any further questions or need assistance, feel free to reach out.']");
		
		 this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        this.linkedin = page.locator("//img[@alt='linkedin']");
	        this.instagram = page.locator("//img[@alt='instagram']");
	        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
	        this.twitter = page.locator("//img[@alt='twitter']");
	        this.facebook = page.locator("//img[@alt='facebook']");
		
		
		
	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator confirmationText() {return confirmationText;}
	public Locator jobCard() {return jobCard;}
	public Locator myMeetings() {return myMeetings;}
	public Locator wishText() {return wishText;}
	
	public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
    public Locator linkedin() { return linkedin; }
    public Locator twitter() { return twitter; }
    public Locator facebook() { return facebook; }
    public Locator instagram() { return instagram; }
    public Locator getItonPlaystore() { return getItonPlaystore; }
    public Locator getItonAppstore() { return getItonAppstore; }

}
