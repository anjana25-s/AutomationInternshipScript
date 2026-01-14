package com.promilo.automation.internship.pageobjects;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyPreferenceCardValidation {
    
    private final Page page;
    
    private final Locator internshipRole;
    private final Locator location;
    private final Locator brandName;
    private final Locator duration;
    private final Locator stipend;
    private final Locator serviceName;
    private final Locator completedStatusTag;
    private final Locator rejectedStatusTag;
  
    
     public MyPreferenceCardValidation(Page page) {
            this.page = page;
            
            this.internshipRole= page.locator("[class='preferance-header-text']");
            this.brandName=page.locator("//span[text()='UST Global']");
            this.location= page.locator("//span[text()='Bihar - Other']");
            this.duration=page.locator("//div[text()='6 Months']");
            this.stipend=page.locator("//span[text()='25K - 35K']");
            this.serviceName=page.locator("[class='service-name']");
            this.completedStatusTag=page.locator("//span[text()='Completed']");
            this.rejectedStatusTag=page.locator("//span[text()='Rejected']");
            }

     public Locator internshipRole() {return internshipRole;}
     public Locator location() {return location;}
     public Locator brandName() {return brandName;}
     public Locator duration() {return duration;}
     public Locator stipend() {return stipend;}
     public Locator serviceName() {return serviceName;}
     public Locator completedStatusTag() {return completedStatusTag;}
     public Locator rejectedStatusTag() { return rejectedStatusTag;}
    
}