package com.promilo.automation.pageobjects.emailnotifications.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UserRecievesRejectedGetHrCallNotification {
	
	private final Page page;
	private final Locator ExploreButton;
	private final Locator RejectedText;
	private final Locator DisappointText;
	
	    private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;
	    private final Locator regards;

	  public  UserRecievesRejectedGetHrCallNotification(Page page){
		  
		  this.page= page;
		  this.ExploreButton= page.locator("//p[text()='Explore']");
		  this.RejectedText= page.locator("(//p)[1]");
		  this.DisappointText= page.locator("(//p)[2]");
		  
		  this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        this.linkedin = page.locator("//img[@alt='linkedin']");
	        this.instagram = page.locator("//img[@alt='instagram']");
	        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
	        this.twitter = page.locator("//img[@alt='twitter']");
	        this.facebook = page.locator("//img[@alt='facebook']");
	        this.regards= page.locator("//span[contains(text(), 'Greetings')]");
		  
		  
	  }
	  

	  public Locator ExploreButton() {return ExploreButton;}
	  public Locator RejectedText() {return RejectedText;}
	  public Locator DisappointText() {return DisappointText;}
	  public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }
	    public Locator regards(){return regards;}
	
}
