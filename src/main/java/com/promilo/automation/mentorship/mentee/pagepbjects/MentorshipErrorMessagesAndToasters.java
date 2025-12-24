package com.promilo.automation.mentorship.mentee.pagepbjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorshipErrorMessagesAndToasters {
	
	
	private Page page;
	
	private final Locator nameIsRequired;
	private final Locator mobileNumberIsRequired;
	private final Locator emailIsRequired;
	
	private final Locator nameMinimumCharacter;
	private final Locator invalidMobileNumber;
	private final Locator invalidEmailAdress;
	
	private final Locator bookVideoCallButton;
	
	private final Locator nameTextField;
	private final Locator mobileTextField;
	private final Locator emailTextField;
	
	private final Locator invalidOtp;
	private final Locator verifyAndProceedButton;
	
	public MentorshipErrorMessagesAndToasters(Page page) {
		this.page=page;
		
		this.nameIsRequired= page.locator("//div[text()='Name is required']");
		this.mobileNumberIsRequired= page.locator("//div[text()='Mobile number is required']");
		this.emailIsRequired= page.locator("//div[text()='Email is required']");
		this.bookVideoCallButton= page.locator("//button[text()='Book a Video Call']");
		
		this.nameMinimumCharacter= page.locator("//div[text()='Must be 3 characters or greater']");
		this.invalidMobileNumber= page.locator("//div[normalize-space()='Invalid Mobile number, must be exactly 10 digits']");
		this.invalidEmailAdress= page.locator("//div[contains(text(),'Invalid email address')]");
		
		
		this.nameTextField= page.locator("//div[@class=' askUs-feature-container']//input[@id='userName']");
		this.mobileTextField= page.locator("//input[@placeholder='Mobile*']");
		this.emailTextField= page.locator("//input[@placeholder='Email*']");
		
		this.invalidOtp= page.locator("//div[text()='Invalid OTP.']");
		this.verifyAndProceedButton= page.locator("//button[normalize-space()='Verify & Proceed']");
		
		
		
	}
	
	public Locator nameIsRequired() {return nameIsRequired;}
	public Locator mobileNumberIsRequired() {return mobileNumberIsRequired;}
	public Locator emailIsRequired() {return emailIsRequired;}
	public Locator bookVideoCallButton() {return bookVideoCallButton;}
	
	
	public Locator nameTextField() {return nameTextField;}
	public Locator mobileTextField() {return mobileTextField;}
	public Locator emailTextField() {return emailTextField;}
	
	public Locator nameMinimumCharacter() {return nameMinimumCharacter;}
	public Locator invalidMobileNumber() {return invalidMobileNumber;}
	public Locator invalidEmailAdress() {return invalidEmailAdress;}
	
	public Locator invalidOtp() {return invalidOtp;}
	public Locator verifyAndProceedButton() {return verifyAndProceedButton;}

}
