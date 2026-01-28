package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RescheduleAcceptedNotification {
	
	private final Locator GreetingText;
	private final Locator MymeetingButton;
	private final Locator jobCard;
	private final Locator newSchedule;
	private final Locator promiloLogo1;
    private final Locator promiloLogo2;
    private final Locator linkedin;
    private final Locator facebook;
    private final Locator instagram;
    private final Locator twitter;
    private final Locator getItonPlaystore;
    private final Locator getItonAppstore;
	
	private final Page page;
	
	public RescheduleAcceptedNotification(Page page) {
		
		this.page=page;
		this.GreetingText= page.locator("span[class='mce-content-body mce-edit-focus']");
		this.MymeetingButton= page.locator("//p[text()='My Meeting']");
		this.jobCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/pub/bfra/69vjd3ad/g5o/ht5/7km/Job%20Sent%20Reminder%20Active.png']");
		this.newSchedule= page.locator("//strong[text()='New Schedule:']");
		
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
	public Locator MymeetingButton() {return MymeetingButton;}
	public Locator jobCard() {return jobCard;}
	public Locator newSchedule() {return newSchedule;}
	
	 public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }


}
