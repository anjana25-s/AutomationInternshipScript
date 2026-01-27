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
   private final Locator loginClick;
   private final Locator enterExistingEmail;
   private final Locator clickSendVerificationCode;
   private final Locator SignupClick;
   private final Locator NameTextField;
   private final Locator mobileNumberField;
   private final Locator emailIdTextField;
   private final Locator clickOnIndustry;
   private final Locator selectValue;
   private final Locator selectoneValue;
   private final Locator closeIndustryDropDown;
   private final Locator clickPassword;
   private final Locator clickEyeIcon;
   private final Locator clickToCloseIcon;
   private final Locator clickRegister;
   private final Locator toggleButton;
   private final Locator backButton;
   
// OTP Input Locators
    private final Locator enterOtp1;
	private final Locator enterOtp2;
	private final Locator enterOtp3;
	private final Locator enterOtp4;
	private final Locator resendOtp;
	private final Locator clickProceed;
	private final Locator verifyProceed;
	private final Locator closePopup;
	private final Locator bellIcon;
	
	
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
		this.resendOtp=page.locator("//p[text()='Resend OTP']");
		this.clickProceed=page.locator("//button[text()='Verify & Proceed']");
		this.verifyProceed=page.locator("[class='submit-btm-askUs  btn btn-primary']");
		this.loginClick = page.locator("//a[text()='Login']");
	    this.enterExistingEmail=page.locator("[placeholder='Enter Existing Email or Mobile Number']");
	    this.clickSendVerificationCode=page.locator("//button[text()='Send Verification Code']");
	    this.SignupClick = page.locator("//a[text()='Signup']");
	    this.NameTextField=page.locator("(//input[@placeholder='Name*'])[2]");
	    this.mobileNumberField=page.locator("(//input[@placeholder='Mobile*'])[2]");
	    this.emailIdTextField=page.locator("(//input[@placeholder='Email*'])[2]");
	    this.clickOnIndustry=page.locator("(//div[text()='Select your Industry*'])[2]");
		this.selectValue=page.locator("[id='checkbox-3']");
		this.selectoneValue=page.locator("[id='checkbox-34']");
		this.closeIndustryDropDown=page.locator("(//div[@id='industry-dropdown'])[2]");
		this.clickPassword=page.locator("(//input[@name='password'])[2]");
		this.clickEyeIcon=page.locator("(//img[@class='password-eye'])[2]");
		this.clickToCloseIcon=page.locator("(//img[@class='password-eye'])[2]");
		this.clickRegister=page.locator("//button[text()='Register']");
		this.closePopup=page.locator("[alt='closeIcon Ask us']");
		this.bellIcon=page.locator("(//img[@class='InAppNotification-icon'])[2]");
		this.toggleButton=page.locator("//input[@id='custom-switch']");
		this.backButton=page.locator("//span[text()='Back']");
		
		
   }
 
   /* Click on Ask Us button */
   public void clickAskUs() {
	   askUs.click();
	
	 
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
	
	   public void clickOnButton() {
		   button.click();
		   page.waitForTimeout(2000);
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
	 
	 public void resendClick() {
		 resendOtp.click();
		 page.waitForTimeout(2000);
	 }
	
	 /** Click verify and proceed button */
   public void clickProceed() {
	  button.click();
	  page.waitForTimeout(2000);
   }
	 
	 public void clickVerify() {
		verifyProceed.first().click();
		 page.waitForTimeout(4000);
	 }
	 
	 public void clickLogin() {
	    	loginClick.click();   
	    	page.waitForTimeout(2000);
	    }
	    
	 public void enterEmailId(String Value) {
	    	enterExistingEmail.fill(Value);
	    	page.waitForTimeout(2000);
	    }
	    
	  public void clickOnSendVerificationButton() {
	    	clickSendVerificationCode.click();
	    	page.waitForTimeout(2000);
	    }
	    
	  public void clickSignup() {
	    	   SignupClick.click();   
	       	page.waitForTimeout(2000);
	       }
	       
	  public void enterNameInTextfield(String Value) {
	    	   NameTextField.fill(Value);
	    	   page.waitForTimeout(2000);
	       }
	       
	 public void enterMobileNumber(String Value) {
	    	   mobileNumberField.fill(Value);
	    	   page.waitForTimeout(2000);
	       }
	       
	  public void enterEmail(String Value) {
		  emailIdTextField.fill(Value);
	  }
	  
	  public void industryDropdown() {
			 clickOnIndustry.click();
			 page.waitForTimeout(2000);
		 }

		 /** Select industry value */
		 public void clickOnValue() {
			 page.waitForTimeout(2000);
			 selectValue.scrollIntoViewIfNeeded();
			 selectValue.click();
			 }
		 
		 public void selectValue() {
			 selectoneValue.click();
			 page.waitForTimeout(2000);
		 }
		 
		 public void closeDropdown() {
			 closeIndustryDropDown.click();
			 page.waitForTimeout(2000);
		 }
		        
	     public void enterPassword(String Value)  {
	    	 clickPassword.fill(Value);
	    	 page.waitForTimeout(2000);
	     }
	     
	     public void clickOnIcon() {
	    	 clickEyeIcon.click();
	    	 page.waitForTimeout(2000);
	     }
	     
	     
	     public void clickCloseIcon() {
	    	 clickToCloseIcon.click();
	    	 page.waitForTimeout(2000);
	     }
	       
	      public void clickOnRegButton() {
	    	  clickRegister.click();
	    	  page.waitForTimeout(2000);
	      }
	       
	       public void closeThankyouPopup() {
	    	   closePopup.click();
	    	   page.waitForTimeout(1000);
	       }
	       
	       public void notificationIcon() {
	    	   bellIcon.click();
	    	   page.waitForTimeout(1000);
	    	   }
	       
	       public void toggleClick() {
	    	   toggleButton.click();
	    	   page.waitForTimeout(1000);
	       }
	       
	       public void backButtonClick() {
	    	   backButton.click();
	    	   page.waitForTimeout(1000);
	       }
	       
	       
	       
	       
	       
	       
	       
	       
	       
	       
	 }