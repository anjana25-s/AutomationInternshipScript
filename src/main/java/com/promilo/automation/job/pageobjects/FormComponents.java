package com.promilo.automation.job.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FormComponents {
	
	
    private final Page page;
    
    //ask us
	private final Locator askUsButton;
    private final Locator userNameField;
    private final Locator mobileField;
    private final Locator feedbackMessageField;
    private final Locator verifyProceedButton;
    private final Locator thankYouPopup;
    private final Locator emailTextField;
    
    
    
    //getHrCall
    private final Locator nextButton;
    private final Locator submitButton;
    
    
    
    public FormComponents(Page page) {
        this.page = page;

        this.askUsButton = page.locator("//button[text()='Ask us?']");
        this.userNameField = page.locator("//input[@name='userName']");
        this.mobileField = page.locator("//input[@placeholder='Mobile*']");
        this.feedbackMessageField = page.locator("//textarea[@id='feedbackDetails']");
        this.verifyProceedButton = page.locator("//button[text()='Verify & Proceed']");

        this.thankYouPopup = page.locator(
                "[class='ThankYou-content justify-content-center']");
        
        
        this.nextButton = page.locator("//button[text()='Next']");
        this.submitButton = page.locator("//button[text()='Submit']");
        this.emailTextField=page.locator("[placeholder='Email*']");


    }
	
    
    
    public Locator askUsButton() { return askUsButton; }
    public Locator userNameField() { return userNameField; }
    public Locator mobileField() { return mobileField; }
    public Locator feedbackMessageField() { return feedbackMessageField; }
    public Locator verifyProceedButton() { return verifyProceedButton; }
    public Locator thankYouPopup() { return thankYouPopup; }
    public Locator nextButton() {return nextButton;}
    public Locator submitButton() {return submitButton;}
    public Locator emailTextField() {return emailTextField;}

}