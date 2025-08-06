package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PersonalDetailsPage {

    private final Page page;

    private final Locator personalDetailsGenderMaleButton;
    private final Locator personalDetailsGenderFemaleButton;
    private final Locator personalDetailsGenderTransgenderButton;
    private final Locator personalDetailsSingleParentButton;
    private final Locator personalDetailsWorkingMotherButton;
    private final Locator personalDetailsServerInMilitaryButton;
    private final Locator personalDetailsRetiredButton;
    private final Locator personalDetailsLgbtqButton;

    private final Locator personalDetailsMaritalStatusSingleButton;
    private final Locator personalDetailsMaritalStatusMarriedButton;
    private final Locator personalDetailsMaritalStatusWidowedButton;
    private final Locator personalDetailsMaritalStatusDivorcedButton;
    private final Locator personalDetailsMaritalStatusSeparatedButton;
    private final Locator personalDetailsMaritalStatusOtherButton;

    private final Locator personalDetailsDobField;
    private final Locator personalDetailsCategoryInformationText;

    private final Locator personalDetailsCategoryGeneralButton;
    private final Locator personalDetailsCategoryScheduledCasteButton;
    private final Locator personalDetailsCategoryScheduledTribeButton;
    private final Locator personalDetailsCategoryObcButton;
    private final Locator personalDetailsCategoryObcNonCreamyButton;
    private final Locator personalDetailsCategoryOthersButton;
    
    
    
    private final Locator personalDetailsDifferentlyAbledYes;
    private final Locator personalDetailsDifferentlyAbledNo;
    private final Locator personalDetailsDifferentlyAbledTypeDropdown;
    private final Locator personalDetailsDifferentlyAbledDescription;
    
    private final Locator personalDetailsCareerBreakYes;
    private final Locator personalDetailsCareerBreakNo;
    private final Locator personalDetailsCareerBreakEducationButton;
    private final Locator personalDetailsCareerBreakChildCareButton;
    private final Locator personalDetailsCareerBreakMedicalButton;
    private final Locator personalDetailsCareerBreakLayoffButton;
    private final Locator personalDetailsCareerBreakPersonalButton;
    private final Locator personalDetailsCurrentlyOnBreakCheckbox;
    private final Locator personalDetailsBreakStartedYearDropdown;
    private final Locator personalDetailsBreakStartedMonthDropdown;
    
    private final Locator personalDetailsWorkPermitUSA;
    private final Locator personalDetailsWorkPermitOtherCountries;
    
    private final Locator personalDetailsAddressField;
    private final Locator personalDetailsPincodeField;
    
    private final Locator personalDetailsEnterLanguageField;
    private final Locator personalDetailsLanguageProficiencyDropdown;
    private final Locator personalDetailsLanguageReadCheckbox;
    private final Locator personalDetailsLanguageWriteCheckbox;
    private final Locator personalDetailsLanguageSpeakCheckbox;
    private final Locator personalDetailsAddAnotherLanguageButton;
    
    private final Locator personalDetailsSaveButton;


    public PersonalDetailsPage(Page page) {
        this.page = page;

        this.personalDetailsGenderMaleButton = page.locator("//button[@id='Male']");
        this.personalDetailsGenderFemaleButton = page.locator("//button[@id='Female']");
        this.personalDetailsGenderTransgenderButton = page.locator("//button[@id='Transgender']");
        this.personalDetailsSingleParentButton = page.locator("//button[@value='Single Parent']");
        this.personalDetailsWorkingMotherButton = page.locator("//button[@value='Working Mother']");
        this.personalDetailsServerInMilitaryButton = page.locator("//button[@value='Server In Military']");
        this.personalDetailsRetiredButton = page.locator("//button[@value='Retired(60+)']");
        this.personalDetailsLgbtqButton = page.locator("//button[@value='LGBTQ+']");

        this.personalDetailsMaritalStatusSingleButton = page.locator("//button[@value='Single']");
        this.personalDetailsMaritalStatusMarriedButton = page.locator("//button[@value='Married']");
        this.personalDetailsMaritalStatusWidowedButton = page.locator("//button[@value='Widowed']");
        this.personalDetailsMaritalStatusDivorcedButton = page.locator("//button[@value='Divorced']");
        this.personalDetailsMaritalStatusSeparatedButton = page.locator("//button[@value='Separated']");
        this.personalDetailsMaritalStatusOtherButton = page.locator("//button[@value='Other']");

        this.personalDetailsDobField = page.locator("//input[@placeholder='Select Date']");
        this.personalDetailsCategoryInformationText = page.locator("//p[text()='Companies are focusing on equal opportunities and might be looking for candidates from diverse backgrounds.']");

        this.personalDetailsCategoryGeneralButton = page.locator("//button[@id='1']");
        this.personalDetailsCategoryScheduledCasteButton = page.locator("//button[@id='2']");
        this.personalDetailsCategoryScheduledTribeButton = page.locator("//button[@id='3']");
        this.personalDetailsCategoryObcButton = page.locator("//button[@id='4']");
        this.personalDetailsCategoryObcNonCreamyButton = page.locator("//button[@id='5']");
        this.personalDetailsCategoryOthersButton = page.locator("//button[@id='6']");
        
        
        
        this.personalDetailsDifferentlyAbledYes = page.locator("//input[@id='differentlyAbledCheck']");
        this.personalDetailsDifferentlyAbledNo = page.locator("//input[@id='differentlyAbledCheckNo']");
        this.personalDetailsDifferentlyAbledTypeDropdown = page.locator("//input[@id='react-select-33-input']");
        this.personalDetailsDifferentlyAbledDescription = page.locator("//textarea[@id='Description']");
        
        this.personalDetailsCareerBreakYes = page.locator("//input[@id='careerBreakYes']");
        this.personalDetailsCareerBreakNo = page.locator("//input[@id='careerBreakNo']");
        this.personalDetailsCareerBreakEducationButton = page.locator("//button[@value='Education']");
        this.personalDetailsCareerBreakChildCareButton = page.locator("//button[@value='ChildCare']");
        this.personalDetailsCareerBreakMedicalButton = page.locator("//button[@value='Medical']");
        this.personalDetailsCareerBreakLayoffButton = page.locator("//button[@value='Layoff']");
        this.personalDetailsCareerBreakPersonalButton = page.locator("//button[@value='personal']");
        this.personalDetailsCurrentlyOnBreakCheckbox = page.locator("//input[@id='flexCheckDefault']");
        
        this.personalDetailsBreakStartedYearDropdown = page.locator("#react-select-5-input");
        this.personalDetailsBreakStartedMonthDropdown = page.locator("#react-select-6-input");
        
        this.personalDetailsWorkPermitUSA = page.locator("#react-select-2-input");
        this.personalDetailsWorkPermitOtherCountries = page.locator("#react-select-3-input");
        
        this.personalDetailsAddressField = page.locator("//input[@id='address']");
        this.personalDetailsPincodeField = page.locator("//input[@id='pin_code']");
        
        this.personalDetailsEnterLanguageField = page.locator("//input[@placeholder='Enter language']");
        this.personalDetailsLanguageProficiencyDropdown = page.locator("#react-select-4-input");
        this.personalDetailsLanguageReadCheckbox = page.locator("//div[@class='demo-inline-spacing justify-content-between']//div[1]//input[1]");
        this.personalDetailsLanguageWriteCheckbox = page.locator("//div[@role='dialog']//div[@class='row']//div[2]//input[1]");
        this.personalDetailsLanguageSpeakCheckbox = page.locator("//body//div//div[@class='mt-2']//div//div[3]//input[1]");
        this.personalDetailsAddAnotherLanguageButton = page.locator("//span[text()='Add Another Language']");
        
        this.personalDetailsSaveButton = page.locator("//button[@class='save-resume-btn']");
    }

    public Locator personalDetailsGenderMaleButton() { return personalDetailsGenderMaleButton; }
    public Locator personalDetailsGenderFemaleButton() { return personalDetailsGenderFemaleButton; }
    public Locator personalDetailsGenderTransgenderButton() { return personalDetailsGenderTransgenderButton; }
    public Locator personalDetailsSingleParentButton() { return personalDetailsSingleParentButton; }
    public Locator personalDetailsWorkingMotherButton() { return personalDetailsWorkingMotherButton; }
    public Locator personalDetailsServerInMilitaryButton() { return personalDetailsServerInMilitaryButton; }
    public Locator personalDetailsRetiredButton() { return personalDetailsRetiredButton; }
    public Locator personalDetailsLgbtqButton() { return personalDetailsLgbtqButton; }

    public Locator personalDetailsMaritalStatusSingleButton() { return personalDetailsMaritalStatusSingleButton; }
    public Locator personalDetailsMaritalStatusMarriedButton() { return personalDetailsMaritalStatusMarriedButton; }
    public Locator personalDetailsMaritalStatusWidowedButton() { return personalDetailsMaritalStatusWidowedButton; }
    public Locator personalDetailsMaritalStatusDivorcedButton() { return personalDetailsMaritalStatusDivorcedButton; }
    public Locator personalDetailsMaritalStatusSeparatedButton() { return personalDetailsMaritalStatusSeparatedButton; }
    public Locator personalDetailsMaritalStatusOtherButton() { return personalDetailsMaritalStatusOtherButton; }

    public Locator personalDetailsDobField() { return personalDetailsDobField; }
    public Locator personalDetailsCategoryInformationText() { return personalDetailsCategoryInformationText; }

    public Locator personalDetailsCategoryGeneralButton() { return personalDetailsCategoryGeneralButton; }
    public Locator personalDetailsCategoryScheduledCasteButton() { return personalDetailsCategoryScheduledCasteButton; }
    public Locator personalDetailsCategoryScheduledTribeButton() { return personalDetailsCategoryScheduledTribeButton; }
    public Locator personalDetailsCategoryObcButton() { return personalDetailsCategoryObcButton; }
    public Locator personalDetailsCategoryObcNonCreamyButton() { return personalDetailsCategoryObcNonCreamyButton; }
    public Locator personalDetailsCategoryOthersButton() { return personalDetailsCategoryOthersButton; }
    
    
    
    public Locator personalDetailsDifferentlyAbledYes() { return personalDetailsDifferentlyAbledYes; }
    public Locator personalDetailsDifferentlyAbledNo() { return personalDetailsDifferentlyAbledNo; }
    public Locator personalDetailsDifferentlyAbledTypeDropdown() { return personalDetailsDifferentlyAbledTypeDropdown; }
    public Locator personalDetailsDifferentlyAbledDescription() { return personalDetailsDifferentlyAbledDescription; }
    public Locator personalDetailsCareerBreakYes() { return personalDetailsCareerBreakYes; }
    public Locator personalDetailsCareerBreakNo() { return personalDetailsCareerBreakNo; }
    public Locator personalDetailsCareerBreakEducationButton() { return personalDetailsCareerBreakEducationButton; }
    public Locator personalDetailsCareerBreakChildCareButton() { return personalDetailsCareerBreakChildCareButton; }
    public Locator personalDetailsCareerBreakMedicalButton() { return personalDetailsCareerBreakMedicalButton; }
    public Locator personalDetailsCareerBreakLayoffButton() { return personalDetailsCareerBreakLayoffButton; }
    public Locator personalDetailsCareerBreakPersonalButton() { return personalDetailsCareerBreakPersonalButton; }
    public Locator personalDetailsCurrentlyOnBreakCheckbox() { return personalDetailsCurrentlyOnBreakCheckbox; }
    public Locator personalDetailsBreakStartedYearDropdown() { return personalDetailsBreakStartedYearDropdown; }
    public Locator personalDetailsBreakStartedMonthDropdown() { return personalDetailsBreakStartedMonthDropdown; }
    
    public Locator personalDetailsWorkPermitUSA() { return personalDetailsWorkPermitUSA; }
    public Locator personalDetailsWorkPermitOtherCountries() { return personalDetailsWorkPermitOtherCountries; }
    
    public Locator personalDetailsAddressField() { return personalDetailsAddressField; }
    public Locator personalDetailsPincodeField() { return personalDetailsPincodeField; }
    
    public Locator personalDetailsEnterLanguageField() { return personalDetailsEnterLanguageField; }
    public Locator personalDetailsLanguageProficiencyDropdown() { return personalDetailsLanguageProficiencyDropdown; }
    public Locator personalDetailsLanguageReadCheckbox() { return personalDetailsLanguageReadCheckbox; }
    public Locator personalDetailsLanguageWriteCheckbox() { return personalDetailsLanguageWriteCheckbox; }
    public Locator personalDetailsLanguageSpeakCheckbox() { return personalDetailsLanguageSpeakCheckbox; }
    public Locator personalDetailsAddAnotherLanguageButton() { return personalDetailsAddAnotherLanguageButton; }
    
    
    public Locator personalDetailsSaveButton() { return personalDetailsSaveButton; }
}
