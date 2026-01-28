package com.promilo.automation.pageobjects.emailnotifications.advertiser.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class JobCampaignExpired {
	
	 private Locator greetText;
	 private Locator regardingText;
	 private Locator expiredText;
	 private Locator linkText;
	 private Locator campainListButton;
	 private final Locator promiloLogo1;
	 private final Locator promiloLogo2;
	
	private final Page page;
	
	public JobCampaignExpired(Page page) {
		this.page= page;
		
		this.greetText= page.locator("//span[contains(text(), 'Dear ')]");
		this.regardingText= page.locator("(//p)[1]");
		this.expiredText= page.locator("(//p)[2]");
		this.linkText= page.locator("(//p)[3]");
		this.campainListButton= page.locator("//p[text()='Campaign list']");
		this.promiloLogo1 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[1]");
        this.promiloLogo2 = page.locator("(//div[@class='image-wrap StageModuleImage_sidebarModule__cOFvM'])[2]");
        
		
	}
	
	public Locator greetText() {return greetText;}
	public Locator regardingText() {return regardingText;}
	public Locator expiredText() {return expiredText;}
	public Locator linkText() {return linkText;}
	public Locator campainListButton() {return campainListButton;}

}
