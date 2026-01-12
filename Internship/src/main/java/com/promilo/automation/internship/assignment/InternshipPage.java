package com.promilo.automation.internship.assignment;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InternshipPage {
	private final Page page;
	// Locators for Internship related operations
	private final Locator internshipsTab;
	private final Locator automationTesterCard;
	private final Locator applyNowButton;
	private final Locator selectLanguage;
	private final Locator selectDate;
	private final Locator selectTime;
	private final Locator bookSlotButton;
	private final Locator myInterestTab;
	
	 /**
     * Constructor initializes the Page instance and locators
     * @param page Playwright Page instance for UI interactions
     */
	public InternshipPage(Page page) {
		this.page = page;
		// Initialize element locators
		this.internshipsTab = page.locator("//a[text()='Internships']");
		this.automationTesterCard = page.locator("//h3[text()='Artificial Intelligence']");
		

		this.applyNowButton = page.locator("(//button[@class='functional_btn-Apply-now'])[1]");

		this.selectLanguage=page.locator("(//span[@class='font-14 text-dark-grey label-text'])[1]");

		this.selectDate = page.locator("//span[text()='19']");

		this.selectTime = page.locator("//li[text()='12:30 PM']");

		this.bookSlotButton = page.locator("(//button[text()='Submit'])[2]");

		this.myInterestTab = page.locator("//a[text()='My Interest']");
		   
	}
	/**
     * Clicks on the Internships tab to navigate to internships section
     */
	public void clickInternshipsTab() {
		
		internshipsTab.click();
		

	}
	/**
     * Clicks on the selected internship card
     */
	public void clickAutomationTesterCard() {
	
		automationTesterCard.click();
		//page.pause();
		
}
	/**
     * Clicks the Apply Now button on the internship card
     */
	public void clickApplyNow() {
		
		applyNowButton.click();
	

	}
	/**
     * Selects the preferred communication language for booking a slot
     */
	public void clickLanguage() {
		selectLanguage.click();
	}
	/**
     * Selects a date from the calendar for the slot
     */
	public void clickDate() {
		
		selectDate.click();
		
	}
	/**
     * Selects a preferred time for the interview slot
     */
	public void clickTime() {
	    selectTime.click();
	}
	 /**
     * Clicks the Submit button to confirm slot booking
     */
	public void clickBookSlot() {
		
		bookSlotButton.click();
		
	}
	/**
     * Navigates to the My Interest tab to view booked internships
     */
	public void clickMyInterestTab() {
	
		myInterestTab.click();
			}


	
}