package com.promilo.automation.mentorship.mentee;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorshipFormComponents {
    
    private final Page page;
    
    //short list
    private final Locator name;
    private final Locator MobileTextField;
    private final Locator emailTextfield;
    private final Locator shortListButton;
    
    
    //ask us
    private final Locator askUsSubmit;
    private final Locator askUsTextarea;
    private final Locator getMentorCall;
    private final Locator nextButton;
    private final Locator downloadResource;
    
    //personalized video message
    private final Locator RequestVideoMessageButton;
    private final Locator occationDropdown;
    private final Locator occationOptions;
    private final Locator contentDescriptionBox;
    private final Locator toggleButton;
    
    
    //Invoice Info
    private final Locator InvoiceNameField;
    private final Locator StreetAdress1;
    private final Locator StreetAdress2;
    private final Locator pinCode;
    private final Locator yesRadrioBox;
    private final Locator gstNumber;
    private final Locator panNumber;
    private final Locator verifyAndProceed;
    private final Locator typeofBrand;
    private final Locator brandOptions;
    private final Locator typeYourMessage;
    private final Locator brandEndorsementSubmit;
    private final Locator thankYouPopup;
    
    
    private final Locator registeredUserName;
    private final Locator registeredUserMobile;
    
    
    
    
    
    
    
    
    

    
    public MentorshipFormComponents(Page page) {
        this.page = page;
        this.name = page.locator("//input[@name='userName']").nth(1);
        this.MobileTextField= page.locator("//input[@name='userMobile']").nth(1);
        this.emailTextfield= page.locator("//div[@class=' askUs-feature-container']//input[@id='userEmail']");
        this.shortListButton= page.locator("//button[@type='button'][normalize-space()='Shortlist']");
        this.askUsSubmit= page.locator("//button[text()='Submit']").nth(1);
        this.askUsTextarea= page.locator("//textarea[@placeholder='Ask your questions here...']");
        this.getMentorCall= page.locator("//button[@class='submit-btm-askUs btn btn-primary']");
        this.nextButton = page.locator("//button[normalize-space()='Next']");
        this.InvoiceNameField= page.locator("//input[@placeholder='Add A Name']");
        this.StreetAdress1= page.locator("//input[@placeholder='Enter Street Address 1']");
        this.StreetAdress2= page.locator("//input[@placeholder='Enter Street Address 2']");
        this.pinCode= page.locator("//input[@placeholder='Enter Pincode']");
        this.yesRadrioBox= page.locator("//label[@for='YES']");
        this.gstNumber= page.locator("//input[@name='gstNumber']");
        this.panNumber= page.locator("//input[@name='panNumber']");
        this.downloadResource= page.locator("//button[normalize-space()='Download Resource']");
        this.RequestVideoMessageButton= page.locator("//button[normalize-space()='Request Video Message']");
        this.occationDropdown= page.locator("(//span[normalize-space()=\"Whats's The Occasion?\"])[1]");
        this.occationOptions= page.locator("//input[@type='radio']");
        this.contentDescriptionBox= page.locator("//textarea[@placeholder='Type your content here...']");
        this.toggleButton= page.locator("//input[@id='switch-primary pointer']");
        this.verifyAndProceed= page.locator("//button[text()='Verify & Proceed']");
        this.typeofBrand= page.locator("//span[text()='Type of brand endorsement']");
        this.brandOptions= page.locator("(//label)");
        this.typeYourMessage= page.locator("//textarea[@placeholder='Type your message here...']");
        this.brandEndorsementSubmit= page.locator("(//button[text()='Submit'])[2]");
        this.thankYouPopup= page.locator("//div[contains(text(),'Thank You!')]");
        this.registeredUserName= page.locator("//input[@name='userName']");  
        this.registeredUserMobile= page.locator("//input[@name='userMobile']");
        
        
             
    }

    public Locator name() {return name; }
    public Locator MobileTextField() {return MobileTextField;}
    public Locator emailTextfield() {return emailTextfield;}
    public Locator shortListButton() {return shortListButton;}
    public Locator askUsSubmit() {return askUsSubmit;}
    public Locator askUsTextarea() {return askUsTextarea;}
    public Locator getMentorCall() {return getMentorCall;}
    public Locator nextButton() {return nextButton;}
    public Locator InvoiceNameField() {return InvoiceNameField;}
    public Locator StreetAdress1() {return StreetAdress1;}
    public Locator StreetAdress2() {return StreetAdress2;}
    public Locator pinCode() {return pinCode;}
    public Locator yesRadrioBox() {return yesRadrioBox;}
    public Locator gstNumber() {return gstNumber;}
    public Locator panNumber() {return panNumber;}
    public Locator downloadResource() {return downloadResource;}
    public Locator RequestVideoMessageButton() {return RequestVideoMessageButton;}
    public Locator occationDropdown() {return occationDropdown;}
    public Locator occationOptions() {return occationOptions;}
    public Locator contentDescriptionBox() {return contentDescriptionBox;}
    public Locator toggleButton() {return toggleButton;}
    public Locator verifyAndProceed() {return verifyAndProceed;}
    public Locator typeofBrand() {return typeofBrand;}
    public Locator brandOptions() {return brandOptions;}
    public Locator typeYourMessage() {return typeYourMessage;}
    public Locator brandEndorsementSubmit() {return brandEndorsementSubmit;}
    public Locator thankYouPopup() {return thankYouPopup;}
    public Locator registeredUserName() {return registeredUserName;	}
    public Locator registeredUserMobile() {return registeredUserMobile;}
         
}
