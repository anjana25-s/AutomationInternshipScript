package com.promilo.automation.internship.assignment;

import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AskUsPage {
    private Page page;

    // Locators for Ask Us page
   private Locator askUs;
   private Locator nameTextField;
   private Locator userNameField;
   private Locator enterNumber;
   private Locator userNumberField;
   private Locator enterEmail;
   private Locator userEmailField;
   private Locator textBox;
   private Locator button;
// OTP Input Locators
    private final Locator enterOtp1;
	private final Locator enterOtp2;
	private final Locator enterOtp3;
	private final Locator enterOtp4;
	private final Locator clickProceed;
	private final Locator verifyProceed;
	
	/**
     * Constructor to initialize page and elements
     */
   
   public AskUsPage(Page page) {
	   this.page=page;
	   this.askUs=page.locator("//span[text()='Ask us?']");
	   this.nameTextField=page.locator("//input[@name='userName']");
	   this.userNameField=page.locator("(//input[@name='userName'])[2]");
	   this.enterNumber=page.locator("//input[@name='userMobile']");
	   this.userNumberField=page.locator("(//input[@placeholder='Mobile*'])[2]");
	   this.enterEmail=page.locator("//input[@name='userEmail']");
	   this.userEmailField=page.locator("(//input[@placeholder='Email*'])[2]");
	   this.textBox=page.locator("//textarea[@placeholder='Type your questions here...']");
	   this.button=page.locator("(//button[text()='Submit'])[2]");
	   this.enterOtp1=page.locator("//input[@aria-label='Please enter OTP character 1']");
		this.enterOtp2=page.locator("//input[@aria-label='Please enter OTP character 2']");
		this.enterOtp3=page.locator("//input[@aria-label='Please enter OTP character 3']");
		this.enterOtp4=page.locator("//input[@aria-label='Please enter OTP character 4']");
		this.clickProceed=page.locator("//button[text()='Verify & Proceed']");
		this.verifyProceed=page.locator("[class='submit-btm-askUs  btn btn-primary']");
		
   }
 
   /* Click on Ask Us button */
   public void clickAskUs() {
	   askUs.click();
	 //  page.pause();
	 
   }
    /** Enter user name */
   public void enterName(String name) {
	   nameTextField.fill(name);
	   page.waitForTimeout(1000);
	 
   }
   
   public void typeUserName(String name) {
	   userNameField.fill(name);
	   userNameField.nth(2);
	   page.waitForTimeout(1000);
   }
   
   /** Enter phone number */
   public void enterNumber(String number) {
	   enterNumber.fill(number);
	   page.waitForTimeout(1000);
   }
   public void typenumber(String number) {
	   userNumberField.fill(number);
	   userNumberField.nth(2);
	   page.waitForTimeout(1000);
   }
   
    /** Enter email id */
   public void enterMail(String email)
   {
	   enterEmail.fill(email);
	   page.waitForTimeout(1000);
   } 
   
   public void typeEmail(String mail) {
	   userEmailField.fill(mail);
	   userEmailField.nth(2);
	   page.waitForTimeout(1000);
   }
   
   /** Enter query / feedback */
	public void enterQuery(String characters) {
   textBox.fill(characters);
   page.waitForTimeout(1000);
	   }
	/** Click submit button to trigger OTP step */
	   public void clickOnButton() {
		   button.click();
	   }

	public void enterOtp(String number) {
		 enterOtp1.fill(number);
		 page.waitForTimeout(1000);
		
		
	 }
	 public void enterNum(String number) {
		enterOtp2.fill(number);
		 page.waitForTimeout(1000);
	 }
	 public void enterNum1(String number) {
		enterOtp3.fill(number);
		 page.waitForTimeout(1000);
	 }
	 public void enterNum2(String number) {
		enterOtp4.fill(number);
		 page.waitForTimeout(1000);
	 }
	
	 /** Click verify and proceed button */
   public void clickProceed() {
	  button.click();
	  page.waitForTimeout(2000);
   }
	 
	 public void clickVerify() {
		verifyProceed.first().click();
		 page.waitForTimeout(1000);
	 }

	 
	


	

	
	
	
}