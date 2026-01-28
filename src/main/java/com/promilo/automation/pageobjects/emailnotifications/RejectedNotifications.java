package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RejectedNotifications {
	
	 private Locator GreetingText;
	 private Locator  unfortunateText;
	 private Locator updateText;
	 private Locator  howeverText;
	 private Locator jobCard;
	 private Locator exploreSimilarJobs;
	 private Locator exploreSection;
	 
	 private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;
	 private final Page page;

	    public RejectedNotifications(Page page) {
	    	
	    	this.page= page;
	    	
	    	this.GreetingText= page.locator("//span[text()='Hi ']");
	    	this.unfortunateText= page.locator("(//div[@class='mce-content-body'])[2]");
	    	this.updateText= page.locator("(//div[@class='mce-content-body'])[1]");
	    	this.howeverText= page.locator("(//div[@class='mce-content-body'])[3]");
	    	this.jobCard= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/pub/bfra/69vjd3ad/37u/mx9/x0t/Screenshot%202024-12-21%20121419.png']");
	    	this.exploreSimilarJobs= page.locator("(//p)[5]");
	    	this.exploreSection= page.locator("//span[text()='Explore a multitude of exciting experiences!']");
	    	
	        this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        this.linkedin = page.locator("//img[@alt='linkedin']");
	        this.instagram = page.locator("//img[@alt='instagram']");
	        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
	        this.twitter = page.locator("//img[@alt='twitter']");
	        this.facebook = page.locator("//img[@alt='facebook']");
	    	
	    	
	    	
	    }
	    	
	   public Locator  GreetingText(){return GreetingText;}
	   public Locator unfortunateText() {return unfortunateText;}
	   public Locator  updateText() {return updateText;}
	   public Locator howeverText() {return howeverText;}
	   public Locator jobCard() {return jobCard;}
	   public Locator exploreSimilarJobs() {return exploreSimilarJobs;}
	   public Locator exploreSection() {return exploreSection;}
	   
	   public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }
}
