package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EmailnotificationsShortlisted {
	
	
	private final Locator greetings;
	private final Locator applynowKickstart;
	private final Locator shortlistedText;
	private final Locator getHrcall;
	private final Locator momentumText;
	private final Locator applyNowbutton;
	private final Locator regards;
	private final Locator promiloLogo1;
	private final Locator promiloLogo2;
	private final Locator Linkedin;
	private final Locator facebook;
	private final Locator instagram;
	private final Locator twitter;
	private final Locator getItonPlaystore;
	private final Locator getItonAppstore;
	
	 private final Page page;

	    public EmailnotificationsShortlisted(Page page) {
	  
	        this.page = page;
	        
	        greetings= page.locator("span[class='tinyMce-placeholder']");
	        applynowKickstart=page.locator("//span[text()='Apply Now to Kickstart Your Career!']");
	        shortlistedText= page.locator("(//div[@class='mce-content-body'])[1]");
	        getHrcall= page.locator("(//div[@class='mce-content-body'])[3]");
	        momentumText= page.locator("(//div[@class='mce-content-body'])[2]");
	        applyNowbutton= page.locator("(//div[@class='mce-content-body'])[4]");
	        regards= page.locator("(//span[@class='StageModuleParagraph_readonly__51BpS'])[1]");
	        promiloLogo1= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        promiloLogo2= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        Linkedin = page.locator("//img[@alt='linkedin']");
	        instagram = page.locator("//img[@alt='instagram']");
	        getItonPlaystore= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        getItonAppstore= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");

	        twitter = page.locator("//img[@alt='twitter']");

	        facebook = page.locator("//img[@alt='facebook']");

	        
	        
	        
	        
	        
	        
	    }

	    
	    public Locator  greetings() {return greetings;}
	    public Locator applynowKickstart() {return applynowKickstart;}
	    public Locator shortlistedText() {return shortlistedText;}
	    public Locator getHrcall() {return getHrcall;}
	    public Locator momentumText() {return momentumText;}
	    public Locator applyNowbutton() {return applyNowbutton;}
	    public Locator regards() {return regards;}
	    public Locator promiloLogo1() {return promiloLogo1;}
	    public Locator promiloLogo2() {return promiloLogo2;}
	    public Locator Linkedin() {return Linkedin;}
	    public Locator twitter() {return twitter;}
	    public Locator facebook() {return facebook;}
	    public Locator instagram() {return instagram;}
	    public Locator getItonPlaystore() {return getItonPlaystore;}
	    public Locator getItonAppstore() {return getItonAppstore;}
}
