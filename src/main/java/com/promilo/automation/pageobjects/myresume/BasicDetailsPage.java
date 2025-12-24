package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BasicDetailsPage {

    private final Page page;

    

    private final Locator BasicDetailsFirstName;
    private final Locator BasicDetailsLastName;
    private final Locator AddEmployment;
    private final Locator locationDropdDrown; 
    private final Locator LocationName;
    private final Locator MobileNumber;
    private final Locator Email;
    private final Locator SaveButton;
    
    public BasicDetailsPage(Page page) {
        this.page = page;
        
 this.BasicDetailsFirstName = page.locator("//input[@placeholder='Enter First Name']");
this.BasicDetailsLastName = page.locator("//input[@placeholder='Enter Last Name']");
this.AddEmployment = page.locator("(//span[text()='ADD EMPLOYMENT'])[2]");
this.locationDropdDrown = page.locator("//input[contains(@class,'react-select__input') and @type='text']");
this.LocationName = page.locator("//input[@id='react-select-4-input']");
this.MobileNumber = page.locator("#mobile_number");
this.Email = page.locator("//input[@placeholder='Enter Email']");
this.SaveButton = page.locator("//button[@type='submit']']");

 
        
        
    }

    
    public Locator BasicDetailsFirstName() {return BasicDetailsFirstName;}
    public Locator BasicDetailsLastName() {return BasicDetailsLastName;}
    public Locator AddEmployment() {return AddEmployment;}
    public Locator locationDropdDrown() {return locationDropdDrown;}
    public Locator LocationName() {return LocationName; }
    public Locator MobileNumber() {return MobileNumber;}
    public Locator Email() {return Email;}
    public Locator SaveButton() {return SaveButton;} 

    

}
