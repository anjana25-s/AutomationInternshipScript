package com.promilo.automation.pageobjects.emailnotifications.advertiser.gethrcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RejectedNotification {
	
	private Locator greetingTex;
	private Locator rejectedText;
	private Locator exploreText;
	private Locator candidateDetails;
	private final Locator regards;
	private final Locator myProspect;
	
	private final Page page;
	public RejectedNotification(Page page) {
		
		this.page= page;
		
		this.greetingTex= page.locator("//span[contains(text(),'Dear ')]");
		this.rejectedText= page.locator("(//p)[1]");
		this.exploreText= page.locator("(//p)[2]");
		this.candidateDetails= page.locator("img[src='https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/1089417_1074785/Group%2012198.png']");
		this.regards= page.locator("span[class='StageModuleParagraph_readonly__51BpS']");
		this.myProspect= page.locator("//p[text()='My Prospect']");
		
		
	}
	public Locator greetingTex() {return greetingTex;}
	public Locator rejectedText() {return rejectedText;}
	public Locator exploreText() {return exploreText;}
	public Locator candidateDetails() {return candidateDetails;}
	public Locator regards() {return regards;}
	public Locator myProspect() {return myProspect;}
}
