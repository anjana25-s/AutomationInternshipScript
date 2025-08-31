package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Reschedulenotification {
	
	private final Locator GreetingText;
	private final Locator rescheduleText;
	private final Locator myIntrestButton;
	
	 private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;
	
	private final Page page;
	
	public Reschedulenotification(Page page) {
		this.page= page;
		this.rescheduleText= page.locator("//p[text()='We’ve received your request to reschedule your application for ']");
		this.GreetingText= page.locator("//span[@id='97fd52d8-b6f1-4a4b-b9be-941676e4ae64']");
		this.myIntrestButton= page.locator("//p[text()='My Interest']");
		
		
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
	 public Locator rescheduleText() {return rescheduleText;}
	 public Locator myIntrestButton() {return myIntrestButton;}
	 public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }
	

}
