package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Askusnotifications {
	
	private final Locator greetingText;
	private final Locator  noticeText;
	private final Locator expectText;
	private final Locator bestRegards;
	private final Locator ExploreButton;
	
	private final Locator promiloLogo1;
	private final Locator promiloLogo2;
	private final Locator Linkedin;
	private final Locator facebook;
	private final Locator instagram;
	private final Locator twitter;
	private final Locator getItonPlaystore;
	private final Locator getItonAppstore;
	
	
	 private final Page page;

	    public Askusnotifications(Page page) {
	    	
	    	greetingText= page.locator("//span[@class='tinyMce-placeholder']");
	    	noticeText= page.locator("//div[@class='content-labels content-labels--cs content-labels--paragraph StageColumn_contentLabels__u4zmN']").first();
	    	expectText= page.locator("//p[text()='Expect an update from us shortly. Your journey to the perfect role just got smoother!']");
	    	bestRegards= page.locator("span[class='StageModuleParagraph_readonly__51BpS']").first();
	    	
	    	ExploreButton= page.locator("//p[text()='Explore']");
	    	
	    	 promiloLogo1= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
		        promiloLogo2= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
		        Linkedin = page.locator("//img[@alt='linkedin']");
		        instagram = page.locator("//img[@alt='instagram']");
		        getItonPlaystore= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
		        getItonAppstore= page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");

		        twitter = page.locator("//img[@alt='twitter']");

		        facebook = page.locator("//img[@alt='facebook']");

		        
		        
		        
		        
		        
		        
	    	
	    	
	    	
	    	
	    	
	  
	        this.page = page;}
	    public Locator greetingText() {return greetingText;}
	    public Locator noticeText() {return noticeText;}
	    public Locator expectText() {return expectText;}
	    public Locator bestRegards() {return bestRegards;}
	    
	    public Locator promiloLogo1() {return promiloLogo1;}
	    public Locator promiloLogo2() {return promiloLogo2;}
	    public Locator Linkedin() {return Linkedin;}
	    public Locator twitter() {return twitter;}
	    public Locator facebook() {return facebook;}
	    public Locator instagram() {return instagram;}
	    public Locator getItonPlaystore() {return getItonPlaystore;}
	    public Locator getItonAppstore() {return getItonAppstore;}
	    

}
