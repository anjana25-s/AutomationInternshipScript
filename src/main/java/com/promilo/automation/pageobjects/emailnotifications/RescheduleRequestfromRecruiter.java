package com.promilo.automation.pageobjects.emailnotifications;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RescheduleRequestfromRecruiter {
	
	
	private final Page page;
	private Locator GreetingText;
	private Locator rescheduleRequestText;
	private Locator rescheduleOption2;
	private Locator rescheduleOption1;
	
	private final Locator promiloLogo1;
    private final Locator promiloLogo2;
    private final Locator linkedin;
    private final Locator facebook;
    private final Locator instagram;
    private final Locator twitter;
    private final Locator getItonPlaystore;
    private final Locator getItonAppstore;
    
    

	
	public RescheduleRequestfromRecruiter(Page page) {
		
		this.page= page;
		GreetingText= page.locator("span[class='mce-content-body mce-edit-focus']");
		rescheduleRequestText= page.locator("//p[text()='We’ve received a request from the recruiter to reschedule your interview for ']");
		
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        this.linkedin = page.locator("//img[@alt='linkedin']");
        this.instagram = page.locator("//img[@alt='instagram']");
        this.getItonPlaystore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[3]");
        this.getItonAppstore = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[4]");
        this.twitter = page.locator("//img[@alt='twitter']");
        this.facebook = page.locator("//img[@alt='facebook']");
        this.rescheduleOption2= page.locator("(//p[@data-mce-style='word-break: break-word;'])[2]");
        this.rescheduleOption1= page.locator("//p[@data-mce-style='word-break: break-word;']").first();
		
		
		
		
		
		
		
	}
	public Locator GreetingText () {return GreetingText;}
	public Locator rescheduleRequestText() {return rescheduleRequestText;}
	
    public Locator promiloLogo1() { return promiloLogo1; }
    public Locator promiloLogo2() { return promiloLogo2; }
    public Locator linkedin() { return linkedin; }
    public Locator twitter() { return twitter; }
    public Locator facebook() { return facebook; }
    public Locator instagram() { return instagram; }
    public Locator getItonPlaystore() { return getItonPlaystore; }
    public Locator getItonAppstore() { return getItonAppstore; }
	public Locator rescheduleOption2() {return rescheduleOption2;}
	public Locator rescheduleOption1() {return rescheduleOption1;}
}
