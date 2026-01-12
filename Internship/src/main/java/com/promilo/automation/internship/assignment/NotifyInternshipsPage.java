package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NotifyInternshipsPage {
	 private final Page page;
	// Locators representing UI elements on the Internship page
	private final Locator internshipsTab;
    private final Locator bankManagerCard;
    private final Locator clickNotifyInternship;
	private final Locator userName;
	private final Locator guestUserName;
	private final Locator mobileNumber;
	private final Locator phoneNumber;
	private final Locator emailId;
	private final Locator enterMail;
	private final Locator clickButton;
	private final Locator enterOtp1;
	private final Locator enterOtp2;
	private final Locator enterOtp3;
	private final Locator enterOtp4;
	private final Locator clickOnButton;
	private final Locator buildResume;
	private final Locator successPopup;
	// Constructor initializing locators with corresponding page elements
	public NotifyInternshipsPage(Page page) {
		this.page = page;
		// Navigation to Internships tab
		this.internshipsTab = page.locator("//a[text()='Internships']");
		// Internship card selection (Artificial Intelligence card)
		
		this.bankManagerCard = page.locator("//h3[text()='Artificial Intelligence']");
		// Click button for "Notify Similar Internships"
		 
		this.clickNotifyInternship=page.locator("//button[text()='Notify Similar Internships']");
		// Form input fields
		this.userName=page.locator("[placeholder='Name*']");
		this.guestUserName=page.locator("(//input[@placeholder='Name*'])[2]");
        this.mobileNumber=page.locator("//input[@placeholder='Mobile*']");
        this.phoneNumber=page.locator("(//input[@placeholder='Mobile*'])[2]");
		this.emailId=page.locator("[placeholder='Email*']");
		this.enterMail=page.locator("(//input[@placeholder='Email*'])[2]");
		// Submit details to get similar internship notifications
		this.clickButton=page.locator("//button[text()='Send Similar Internships']");
		// OTP input fields
		this.enterOtp1=page.locator("//input[@aria-label='Please enter OTP character 1']");
		this.enterOtp2=page.locator("//input[@aria-label='Please enter OTP character 2']");
		this.enterOtp3=page.locator("//input[@aria-label='Please enter OTP character 3']");
		this.enterOtp4=page.locator("//input[@aria-label='Please enter OTP character 4']");
		// Button to verify OTP and proceed
		this.clickOnButton=page.locator("//button[text()='Verify & Proceed']");
		// CTA after successful OTP verification
		this.buildResume=page.locator("//div[text()='Build Resume']");
	    this.successPopup=page.locator("//div[text()='Thank You!']");
		
	}
	// Click on Internships tab
	public void clickInternshipsTab() {
		internshipsTab.waitFor();
		internshipsTab.click();
		
		page.waitForTimeout(2000);

	}
	// Click on selected Internship card
	public void clickBankManagerCard() {
		bankManagerCard.waitFor();
		bankManagerCard.click();
		page.waitForTimeout(2000);

	}
	
	// Click Notify Similar Internships CTA
	public void clickOnNotify() {
		clickNotifyInternship.first().click();
		page.waitForTimeout(4000);
	}

	// Enter Username into input field
	public void enterUserName(String name) {
		userName.fill(name);
		userName.waitFor();
		page.waitForTimeout(2000);
	}
	
	public void typeUserName(String name) {
		guestUserName.fill(name);
		guestUserName.nth(2);
		page.waitForTimeout(2000);
	}

	// Enter Mobile Number
	public void enterNumber(String number) {
		mobileNumber.fill(number);
		mobileNumber.waitFor();
		page.waitForTimeout(2000);
	}
	public void enterPhoneNumber(String number ) {
		phoneNumber.fill(number);
		phoneNumber.nth(2);
		page.waitForTimeout(2000);
	}
	
	// Enter Email ID
	public void enterEmail(String email) {
		emailId.fill(email);
		emailId.waitFor();
		page.waitForTimeout(2000);
	}

	public void typeemail(String email) {
		enterMail.fill(email);
		enterMail.nth(2);
	}
	// Click send internship button
	public void button() {
		clickButton.click();
		clickButton.waitFor();
		page.waitForTimeout(2000);
		
	}
	// Enter OTP - each digit separately
	public void enterOtp(String number) {
		 enterOtp1.waitFor();
		 enterOtp1.fill(number);
		 page.waitForTimeout(2000);
		 
	 }
	 public void enterNum(String number) {
		 enterOtp2.waitFor();
		 enterOtp2.fill(number);
		 page.waitForTimeout(2000);
		 
	 }
	 public void enterNum1(String number) {
		 enterOtp3.waitFor();
		 enterOtp3.fill(number);
		 page.waitForTimeout(2000);
		
	 }
	 public void enterNum2(String number) {
		 enterOtp4.waitFor();
		 enterOtp4.fill(number);
		 page.waitForTimeout(2000);
		 
	 }
	// click on verify and proceed
	public void clickSubmitButton() {
		clickOnButton.waitFor();
		clickOnButton.click();
	}
	// Click Build Resume in thank you popup
	public void cliclBuildResume() {
		buildResume.waitFor();
		buildResume.click();
		}
	public Locator successPopup() throws InterruptedException {
		Thread.sleep(4000);
		return successPopup;
	}
	
	
	
}