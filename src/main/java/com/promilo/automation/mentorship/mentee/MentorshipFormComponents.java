package com.promilo.automation.mentorship.mentee;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorshipFormComponents {
    
    private final Page page;
    private final Locator name;
    private final Locator MobileTextField;
    private final Locator emailTextfield;
    private final Locator shortListButton;
    
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
     
}
