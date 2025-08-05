package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Feedback {


	private final Page page;
	
	private final Locator AddButton;
	private final Locator SkipButton;
	private final Locator SaveButton;
	private final Locator infoIcon;
	private final Locator Yesbutton;
	private final Locator Nobutton;
	private final Locator Objective;
	private final Locator Subjective;
	private final Locator EnteroptionField;
	private final Locator Addoption;
	private final Locator DeleteIcon;
	private final Locator CopyOption;
	private final Locator RequiredToggle;
	private final Locator FeedbackSavebutton;
	private final Locator FeedbackRequiredToggle;
	private final Locator TypeyourQuestionHere;
	
	
	public Feedback(Page page) {
		this.page= page;
		this.AddButton= page.locator("//button[text()=' Add']");
		this.SkipButton= page.locator("//span[text()='Skip']");
		this.SaveButton= page.locator("//span[text()='Save & Next']");
		this.infoIcon= page.locator("img[alt='Information']");
		this.Yesbutton= page.locator("//button[text()='Yes']");
		this.Nobutton= page.locator("//button[text()='No']");
		this.Objective= page.locator("//div[text()='OBJECTIVE']");
		this.Subjective= page.locator("//div[text()='SUBJECTIVE']");
		this.EnteroptionField = page.locator("//input[@placeholder='Enter option']");
		this.Addoption= page.locator("input[placeholder='Type Question Here']");
		this.DeleteIcon = page.locator("img[alt='Delete']");
		this.CopyOption= page.locator("[alt='Copy']");
		this.RequiredToggle= page.locator("//label[@for='required0']");
		this.FeedbackSavebutton = page.locator("//span[text()='Save & Next']");
		this.FeedbackRequiredToggle = page.locator("//span[@class='me-50']");
		this.TypeyourQuestionHere = page.locator("input[placeholder='Type Question Here']");
		
		
		
		
		
		
		
		
		
		
	}
	
	public Locator AddButton() {return AddButton;}
	public Locator SkipButton() {return SkipButton;}
	public Locator SaveButton() {return SaveButton;}
	public Locator infoIcon() {return infoIcon;}
	public Locator Yesbutton() {return Yesbutton;}
	public Locator Nobutton() {return Nobutton;}
	public Locator Objective() {return Objective;}
	public Locator Subjective() {return Subjective;}
	public Locator EnteroptionField() {return EnteroptionField;}
	public Locator Addoption() {return Addoption;}
	public Locator DeleteIcon() {return DeleteIcon;}
	public Locator CopyOption() {return CopyOption;}
	public Locator RequiredToggle() {return RequiredToggle;}
	public Locator FeedbackSavebutton() {return FeedbackSavebutton;}
	public Locator FeedbackRequiredToggle() {return FeedbackRequiredToggle;}
	public Locator TypeyourQuestionHere() {return TypeyourQuestionHere;}
}
