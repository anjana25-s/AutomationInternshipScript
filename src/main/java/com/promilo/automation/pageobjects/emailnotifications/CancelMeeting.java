package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CancelMeeting {
	
	
	 private final Locator greetingsText;
	 private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;
	    
	    private final Locator cancelledText;
	    private final Locator exploreSimilar;
	 
	 private final Page page;
	 
	 public CancelMeeting(Page page) {
	        this.page = page;
	        
	        this.greetingsText= page.locator("//span[text()='Hi']");
	        this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        this.linkedin = page.locator("//img[@alt='linkedin']");
	        this.instagram = page.locator("//img[@alt='instagram']");
	        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
	        this.twitter = page.locator("//img[@alt='twitter']");
	        this.facebook = page.locator("//img[@alt='facebook']");
	        this.cancelledText= page.locator("//p[text()='We wanted to inform you that, as per your request, we have successfully cancelled the meeting with ']");
	        this.exploreSimilar= page.locator("//span[text()='Explore similar opportunities at your location']");
	 }
	 
	 public Locator greetingsText() {return greetingsText;}
	    public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }
	    
	    public Locator cancelledText() { return cancelledText;}
	    public Locator exploreSimilar() {return exploreSimilar;}

}
