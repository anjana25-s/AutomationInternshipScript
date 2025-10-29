package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccomplishmentsPage {

    private final Page page;
//social profile
    private final Locator addSocialProfileField;
    private final Locator socialProfileUrlField;
    private final Locator socialDescriptionBox;
    private final Locator saveButton;
    private final Locator socialProfileNameError;
    private final Locator socialProfileUrlError;
    
    
    
    // work samples
    private final Locator workTitleTextField;
    private final Locator urlTextField;
    private final Locator currentlyWorkingCheckbox;
    private final Locator durationFromYearDropdown;
    private final Locator durationFromMonthDropDown;
    private final Locator durationToYearDropdown;
    private final Locator durationToMonthDropdown;
    private final Locator descriptionBox;
    private final Locator worksamplesaveButton;
    
    //whitepaper
    private final Locator whitepaperTitleTextField;
    private final Locator whitepaperUrlField;
    private final Locator whitepaperPublishedOnYear;
    private final Locator whitepaperPublishedOnMonth;
    private final Locator whitepaperDescriptionBox;
    private final Locator whitepaperSaveButton;

    
    //patent
    private final Locator patentTitleTextField;
    private final Locator patentUrlTextField;
    private final Locator patentPublishedYear;
    private final Locator patentPublishedMonth;
    private final Locator patentDescriptionTextArea;
    private final Locator patentSaveButton;
    
    
    //Certifications
    private final Locator certificationsNameTextField;
    private final Locator certificationsCompletionIdField;
    private final Locator certificationsUrlField;
    private final Locator certificationsValidityFromYear;
    private final Locator certificationsValidityFromMonth;
    private final Locator certificationsValidityToYear;
    private final Locator certificationsValidityToMonth;
    private final Locator certificationsDoesNotExpireCheckbox;
    
    
    //presentation
    private final Locator presentationTitleTextField;
    private final Locator presentationUrlTextField;
    private final Locator presentationDescriptionBox;
    private final Locator presentationSaveButton;
    
    
    


    public AccomplishmentsPage(Page page) {
        this.page = page;

        //social profile
        this.addSocialProfileField = page.locator("//input[@placeholder='Add social profile']");
        this.socialProfileUrlField = page.locator("//input[@placeholder='Past profile link']");
        this.socialDescriptionBox = page.locator("//textarea[@placeholder='Description ...']");
        this.saveButton = page.locator("//button[@class='save-resume-btn btn btn-secondary']");
        this.socialProfileNameError = page.locator("//input[@placeholder='Add social profile']/following-sibling::span[@class='text-danger']");
        this.socialProfileUrlError = page.locator("//input[@placeholder='Past profile link']/following-sibling::span[@class='text-danger']");


        
        
        //work samples
        
        this.workTitleTextField = page.locator("//body/div/div/div[@role='dialog']/div[@role='document']/div[@class='modal-content']/form/div[@class='px-0 modal-body']/div/div[1]/div[1]/input[1]");
        this.urlTextField = page.locator("//div[@class='my-2']//div//input[@type='text']");
        this.currentlyWorkingCheckbox = page.locator("//input[@id='currentlyWorking']");
        this.durationFromYearDropdown = page.locator("#react-select-2-input");
        this.durationFromMonthDropDown = page.locator("#react-select-3-input");
        this.durationToYearDropdown = page.locator("//input[@id='react-select-6-input']");
        this.durationToMonthDropdown = page.locator("//input[@id='react-select-7-input']");
        this.descriptionBox = page.locator("//textarea[@id='Description']");
        this.worksamplesaveButton = page.locator("//button[text()='Save']");
        
        //whitepaper
        this.whitepaperTitleTextField = page.locator("//input[@placeholder='Enter Title']");
        this.whitepaperUrlField = page.locator("//input[@placeholder='Past URL']");
        this.whitepaperPublishedOnYear = page.locator("#react-select-2-input");
        this.whitepaperPublishedOnMonth = page.locator("#react-select-3-input");
        this.whitepaperDescriptionBox = page.locator("//textarea[@id='Description']");
        this.whitepaperSaveButton = page.locator("//button[text()='Save']");
        
        //patent
        this.patentTitleTextField = page.locator("//input[@placeholder='Enter title']");
        this.patentUrlTextField = page.locator("//input[@placeholder='Paste link']");
        this.patentPublishedYear = page.locator("//input[contains(@class,'react-select__input')][1]");
        this.patentPublishedMonth = page.locator("//input[@id='react-select-11-input']");
        this.patentDescriptionTextArea = page.locator("//textarea[@id='Description']");
        this.patentSaveButton = page.locator("//button[@class='save-resume-btn']");
        
        
        //certifications
        this.certificationsNameTextField = page.locator("//input[@placeholder='Enter certificate name']");
        this.certificationsCompletionIdField = page.locator("//input[@placeholder='Enter certificate completion ID']");
        this.certificationsUrlField = page.locator("//input[@placeholder='Paste link']");
        this.certificationsValidityFromYear = page.locator("//div[contains(text(), 'Select Year')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
        this.certificationsValidityFromMonth = page.locator("//div[contains(text(), 'Select Month')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
        this.certificationsValidityToYear = page.locator("//div[contains(@class,'react-select__control')])[3]");
        this.certificationsValidityToMonth = page.locator("(//div[contains(text(), 'Select Month')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')])[2]");
        this.certificationsDoesNotExpireCheckbox = page.locator("//label[text()='This certification does not expire']");
        
        //presentation
        this.presentationTitleTextField = page.locator("//input[@placeholder='Enter presentation title']");
        this.presentationUrlTextField = page.locator("//input[@placeholder='Paste link']");
        this.presentationDescriptionBox = page.locator("//textarea[@id='Description']");
        this.presentationSaveButton = page.locator("//button[@type='submit']");

    }

    public Locator addSocialProfileField() { return addSocialProfileField; }
    public Locator socialProfileUrlField() { return socialProfileUrlField; }
    public Locator socialDescriptionBox() { return socialDescriptionBox; }
    public Locator saveButton() { return saveButton; }
    public Locator socialProfileNameError() { return socialProfileNameError; }
    public Locator socialProfileUrlError() { return socialProfileUrlError; }


    //Work samples
    public Locator workTitleTextField() {return workTitleTextField;}
    public Locator urlTextField() {return urlTextField;}
    public Locator currentlyWorkingCheckbox() {return currentlyWorkingCheckbox;}
    public Locator durationFromYearDropdown() {return durationFromYearDropdown;}
    public Locator durationFromMonthDropDown() {return durationFromMonthDropDown;}
    public Locator durationToYearDropdown() {return durationToYearDropdown;}
    public Locator durationToMonthDropdown() {return durationToMonthDropdown;}
    public Locator descriptionBox() {return descriptionBox;}
    public Locator worksamplesaveButton() {return worksamplesaveButton;}
    
    
    //whitepaper
    public Locator whitepaperTitleTextField() { return whitepaperTitleTextField; }
    public Locator whitepaperUrlField() { return whitepaperUrlField; }
    public Locator whitepaperPublishedOnYear() { return whitepaperPublishedOnYear; }
    public Locator whitepaperPublishedOnMonth() { return whitepaperPublishedOnMonth; }
    public Locator whitepaperDescriptionBox() { return whitepaperDescriptionBox; }
    public Locator whitepaperSaveButton() { return whitepaperSaveButton; }
    
    //patent
    public Locator patentTitleTextField() { return patentTitleTextField; }
    public Locator patentUrlTextField() { return patentUrlTextField; }
    public Locator patentPublishedYear() { return patentPublishedYear; }
    public Locator patentPublishedMonth() { return patentPublishedMonth; }
    public Locator patentDescriptionTextArea() { return patentDescriptionTextArea; }
    public Locator patentSaveButton() { return patentSaveButton; }
    
    //Certifications
    public Locator certificationsNameTextField() { return certificationsNameTextField; }
    public Locator certificationsCompletionIdField() { return certificationsCompletionIdField; }
    public Locator certificationsUrlField() { return certificationsUrlField; }
    public Locator certificationsValidityFromYear() { return certificationsValidityFromYear; }
    public Locator certificationsValidityFromMonth() { return certificationsValidityFromMonth; }
    public Locator certificationsValidityToYear() { return certificationsValidityToYear; }
    public Locator certificationsValidityToMonth() { return certificationsValidityToMonth; }
    public Locator certificationsDoesNotExpireCheckbox() { return certificationsDoesNotExpireCheckbox; }

    //Presentation 
    public Locator presentationTitleTextField() { return presentationTitleTextField; }
    public Locator presentationUrlTextField() { return presentationUrlTextField; }
    public Locator presentationDescriptionBox() { return presentationDescriptionBox; }
    public Locator presentationSaveButton() { return presentationSaveButton; }
    
    
    
    
    
    
    
    


    


}

