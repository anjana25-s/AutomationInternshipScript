package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MeetingRequestAccepted {
	
	 private final Page page;
	 private final Locator GreetingText;
	 private final Locator confirmationText;
	 private final Locator Jobcard;
	 private final Locator Reschedule;
	 private final Locator Cancel;
	 private final Locator ExicitingSession;
	 private final Locator ExploreMagnitude;
	 
	 private final Locator greetingsText;
	    private final Locator promiloLogo1;
	    private final Locator promiloLogo2;
	    private final Locator linkedin;
	    private final Locator facebook;
	    private final Locator instagram;
	    private final Locator twitter;
	    private final Locator getItonPlaystore;
	    private final Locator getItonAppstore;

	    public MeetingRequestAccepted(Page page) {
	  
	        this.page = page;
	        
	        GreetingText= page.locator("//span[text()='Hi ']"); 
	        confirmationText= page.locator("(//p)[1]");
	        Jobcard= page.locator("div[class='StageModuleImage_uploaderDropZone__cOFvM  ']");
	        Reschedule= page.locator("//p[text()='Reschedule']");
	        Cancel= page.locator("//p[text()='Cancel']");
	        ExicitingSession= page.locator("//p[text()='Get ready for an exciting session!' ]");  
	        ExploreMagnitude= page.locator("//span[text()='Explore a multitude of exciting experiences!']");
	        
	        this.greetingsText = page.locator("//span[@class='tinyMce-placeholder']");
	        this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
	        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
	        this.linkedin = page.locator("//img[@alt='linkedin']");
	        this.instagram = page.locator("//img[@alt='instagram']");
	        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
	        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
	        this.twitter = page.locator("//img[@alt='twitter']");
	        this.facebook = page.locator("//img[@alt='facebook']");
	        
	        
	        
	        

    }
	    
	    public Locator GreetingText() { return GreetingText; }
	    public Locator confirmationText() {return confirmationText;}
	    public Locator Jobcard() {return Jobcard;}
	    public Locator Reschedule() {return Reschedule;}
	    public Locator Cancel() {return Cancel;}
	    public Locator ExicitingSession() {return ExicitingSession;}
	    public Locator ExploreMagnitude() {return ExploreMagnitude;}
	    
	    
	    
	    public Locator promiloLogo1() { return promiloLogo1; }
	    public Locator promiloLogo2() { return promiloLogo2; }
	    public Locator linkedin() { return linkedin; }
	    public Locator twitter() { return twitter; }
	    public Locator facebook() { return facebook; }
	    public Locator instagram() { return instagram; }
	    public Locator getItonPlaystore() { return getItonPlaystore; }
	    public Locator getItonAppstore() { return getItonAppstore; }
	    
	    
	    

}
