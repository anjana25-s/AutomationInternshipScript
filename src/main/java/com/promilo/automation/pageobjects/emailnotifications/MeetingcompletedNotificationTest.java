package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MeetingcompletedNotificationTest {
	private final Locator GreetingText;
	private final Locator completedDescription;
	private final Locator jobCard;
	private final Locator myMeetings;
	private final Locator regardsText;
	
	 private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;

	
	private final Page page;
	
	public  MeetingcompletedNotificationTest(Page page) {
		this.page= page;
		this.GreetingText= page.locator("//span[@class='tinyMce-placeholder']");
		this.completedDescription= page.locator("//p[text()='Congrats on completing your online interview for the ']");
		this.jobCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/pub/bfra/69vjd3ad/d2v/j52/8os/Screenshot%202024-12-23%20121601.png']");
		this.myMeetings= page.locator("//p[text()='My Meetings']");
		
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        this.linkedin = page.locator("//img[@alt='linkedin']");
        this.instagram = page.locator("//img[@alt='instagram']");
        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
        this.twitter = page.locator("//img[@alt='twitter']");
        this.facebook = page.locator("//img[@alt='facebook']");
        this.regardsText= page.locator("(//span[@class='StageModuleParagraph_readonly__51BpS'])[1]");
        
		
		
	}
	
	public Locator GreetingText() {return GreetingText;}
	public Locator completedDescription() {return completedDescription;}
	public Locator jobCard() {return jobCard;}
	public Locator myMeetings() {return myMeetings;}
	
	 public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }

}
