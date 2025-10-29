package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CampaignDetails {
	
	
	private final Page page;
	private final Locator campaignName;
	private final Locator skills;
	private final Locator startDate;
	private final Locator endDate;
	private final Locator saveButton;
	private final Locator clickheretoAddskills;
	private final Locator skillName;
	public CampaignDetails(Page page) {
		
		this.page= page;
		this.campaignName= page.locator("//input[@id='campaign_name']");
		this.skills= page.locator("//input[@placeholder='Search or add keyword...']");
		this.startDate= page.locator("#start_date");
		this.endDate= page.locator("(//input[@id='end_date'])[1]");
		this.saveButton= page.locator("//button[text()='Save and Next']");
		this.clickheretoAddskills= page.locator("//div[text()='+ Click here to add']");
		this.skillName= page.locator("//div[text()='Automation Testing']");
		
		
	}
	
	public Locator campaignName() {return campaignName;}
	public Locator skills() {return skills;}
	public Locator startDate() {return startDate;}
	public Locator endDate() {return endDate;}
	public Locator saveButton() {return saveButton;}
	public Locator clickheretoAddskills() {return clickheretoAddskills;}
	public Locator skillName() {return skillName;}
	

}
