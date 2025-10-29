package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BecomeMentor{
	
	
	private  Page page;
	
	private final Locator becomeMentorButton;
	private final Locator createMentorshipSession;
	private final Locator campaignList;
	
	
	

	
	public BecomeMentor(Page page) {
		
		this.page= page;
		this.becomeMentorButton= page.locator("//button[normalize-space()='Become a Mentor']");
		this.createMentorshipSession= page.locator("//a[text()='Create Mentorship Session']");
		this.campaignList= page.locator("//a[normalize-space()='Campaign List']");
		
		
		
		
		
		
		
		
	}
	
	public Locator becomeMentorButton() {return becomeMentorButton;}
	public Locator createMentorshipSession() {return createMentorshipSession;}
	public Locator campaignList() {return campaignList;}
	
}