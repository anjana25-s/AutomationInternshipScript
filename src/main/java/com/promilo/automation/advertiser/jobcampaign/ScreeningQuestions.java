package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ScreeningQuestions {
	
	private  Page page;
	
	private final Locator infoIcon;
	private final Locator SkipButton;
	private final Locator AddButton;
	private final Locator SaveButton;
	
	public ScreeningQuestions(Page page) {
		
		this.infoIcon= page.locator("img[alt='Information']");
		this.SkipButton= page.locator("//span[text()='Skip']");
		this.AddButton= page.locator("//button[text()='Add']");
		this.SaveButton= page.locator("//button[text()='Save & Next']");
		
		
		
		
		
		
		
		
	}
	

	public Locator infoIcon() {return infoIcon;} 
	public Locator SkipButton() {return SkipButton;}
	public Locator AddButton() {return AddButton;}
	public Locator SaveButton() {return SaveButton;}
	
	
	
	

}
