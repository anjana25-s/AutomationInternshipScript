package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BudgetAndCost {

	private final Page page;

	// Locators
	private final Locator sendEmail;
	private final Locator sendSMS;
	private final Locator notifications;
	private final Locator whatsapp;
	private final Locator minOfferedSalary;
	private final Locator maxOfferedSalary;
	private final Locator evaluate;
	private final Locator infoIcon;
	private final Locator costPerInterview;
	private final Locator audienceIndustryDropdown;
	private final Locator saveAndPreview;

	// Constructor
	public BudgetAndCost(Page page) {
		this.page = page;

		this.sendEmail = page.locator("//input[@id='Send Email']");
		this.sendSMS = page.locator("//input[@id='Send SMS']");
		this.notifications = page.locator("//input[@id='Notification']");
		this.whatsapp = page.locator("//input[@id='Whatsapp']");

		this.minOfferedSalary = page.locator("input[placeholder='Min Offered Salary']");
		this.maxOfferedSalary = page.locator("input[placeholder='Max Offered Salary']");

		this.evaluate = page.locator("//button[text()='Evaluate']");
		this.infoIcon = page.locator("img[alt='Information']");
		this.costPerInterview = page.locator("//label[contains(text(), 'Cost Per Interview')]/following::input[@class='form-control'][1]");
		this.audienceIndustryDropdown = page.locator("//label[contains(text(),'Audience Industry')]/following-sibling::div//div[contains(@class,'css-1hwfws3')]");
		this.saveAndPreview = page.locator("//button[text()='Save & Preview']");
	}

	// Getters
	public Locator sendEmail() { return sendEmail; }
	public Locator sendSMS() { return sendSMS; }
	public Locator notifications() { return notifications; }
	public Locator whatsapp() { return whatsapp; }

	public Locator minOfferedSalary() { return minOfferedSalary; }
	public Locator maxOfferedSalary() { return maxOfferedSalary; }
	public Locator evaluate() { return evaluate; }
	public Locator infoIcon() { return infoIcon; }
	public Locator costPerInterview() { return costPerInterview; }
	public Locator audienceIndustryDropdown() { return audienceIndustryDropdown; }
	public Locator saveAndPreview() { return saveAndPreview; }
}
