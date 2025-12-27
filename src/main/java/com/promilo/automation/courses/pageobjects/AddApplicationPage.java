package com.promilo.automation.courses.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddApplicationPage {

    private final Page page;

    // ===== Common =====
    private final Locator subOptions;
    private final Locator selectedOptionCross;
    private final Locator fieldSelectionAndPreview;
    private final Locator nextButton;

    // ===== Application Creation =====
    private final Locator addApplicationBtn;
    private final Locator createApplicationBtn;
    private final Locator createManuallyBtn;

    private final Locator enterApplicationFormName;
    private final Locator openingDate;
    private final Locator closingDate;
    private final Locator streetAdress;
    private final Locator pinCode;
    private final Locator city;
    private final Locator state;
    private final Locator applicationYear;
    private final Locator applicationCost;
    private final Locator selectAccreditation;

    // ===== Personal Info =====
    private final Locator selectPersonalIdentificationAndDemographics;
    private final Locator selectLegalName;

    // ===== Academic History =====
    private final Locator selectAcademicHistory;
    private final Locator class10Details;
    private final Locator class12Details;
    private final Locator universityLastAttended;
    private final Locator degreeCertificate;
    private final Locator workExperienceCertificate;

    // ===== Entrance Exams =====
    private final Locator entranceExamsSection;
    private final Locator nationalLevelExams;
    private final Locator stateLevelExams;
    private final Locator universitySpecificExams;
    private final Locator selectNationalLevelExam;
    private final Locator addMoreLink;

    // ===== Category & Eligibility =====
    private final Locator categoryEligibilitySection;
    private final Locator categoryOfAdmission;
    private final Locator documentaryEvidence;
    private final Locator pwdStatus;
    private final Locator sportsQuota;
    private final Locator ecaQuota;
    private final Locator domicileStatus;

    // ===== Documentation =====
    private final Locator selectDocumentation;
    private final Locator personalIdentityDocuments;
    private final Locator academicDocuments;
    private final Locator conditionalStatutoryDocuments;
    private final Locator affidavitsUndertakings;

    // ===== Custom Field =====
    private final Locator customFieldDropdown;
    private final Locator customFieldCategoryText;
    private final Locator customFieldNameInput;
    private final Locator addButton;
    private final Locator requiredCheckboxes;

    // ===== Close =====
    private final Locator closePopupIcon;
    private final Locator createButton;

    public AddApplicationPage(Page page) {
        this.page = page;

        // ===== Common =====
        subOptions = page.locator(".sub-option");
        selectedOptionCross = page.locator(".selected-option-cross");
        fieldSelectionAndPreview = page.locator("//div[text()='Field Selection & Preview']");
        nextButton = page.locator("//button[text()=' Next ']");

        // ===== Application Creation =====
        addApplicationBtn = page.locator("//button[text()='Add Application ']");
        createApplicationBtn = page.locator("//button[text()='Create Application']");
        createManuallyBtn = page.locator("//button[text()='Create Manually']");

        enterApplicationFormName = page.locator("#application_form_name");
        openingDate = page.locator("#opening_date");
        closingDate = page.locator("#closing_date");
        streetAdress = page.locator("#street_address");
        pinCode = page.locator("#pin_code");
        city = page.locator("#city");
        state = page.locator("#state");
        applicationYear = page.locator("//select[@id='application_year']");
        applicationCost = page.locator("#application_cost");
        selectAccreditation = page.locator("//div[@class='accreditation-select__input-container css-19bb58m']");

        // ===== Personal Info =====
        selectPersonalIdentificationAndDemographics = page.locator("//div[@id='PERSONAL_INFO']");
        selectLegalName = page.locator("//div[text()='Legal Name']");

        // ===== Academic History =====
        selectAcademicHistory = page.locator("//div[text()='Select Academic History']");
        class10Details = page.locator("//div[text()='Class 10th Details']");
        class12Details = page.locator("//div[contains(text(),'Class 12th Details')]");
        universityLastAttended = page.locator("//div[contains(text(),'University Last Attended')]");
        degreeCertificate = page.locator("//div[contains(text(),'Provisional/Final Degree Certificate')]");
        workExperienceCertificate = page.locator("//div[contains(text(),'Work Experience Certificates')]");

        // ===== Entrance Exams =====
        entranceExamsSection = page.locator("//div[@id='ENTRANCE_EXAMS_AND_TESTS']");
        nationalLevelExams = page.locator("//div[contains(text(),'National Level Exams')]");
        stateLevelExams = page.locator("//div[contains(text(),'State Level Exams')]");
        universitySpecificExams = page.locator("//div[contains(text(),'University Specific Exams')]");
        selectNationalLevelExam = page.locator("//div[text()='Select National Level Exam']");
        addMoreLink = page.locator("[class='text-primary pointer']");

        // ===== Category & Eligibility =====
        categoryEligibilitySection = page.locator("//div[@id='CATEGORY_AND_ELIGIBILITY']");
        categoryOfAdmission = page.locator("//div[contains(text(),'Category of Admission')]");
        documentaryEvidence = page.locator("//div[contains(text(),'Documentary Evidence')]");
        pwdStatus = page.locator("//div[contains(text(),'Person with Disability (PWD) Status')]");
        sportsQuota = page.locator("//div[contains(text(),'Sports Quota')]");
        ecaQuota = page.locator("//div[contains(text(),'Extra-Curricular Activities (ECA) Quota')]");
        domicileStatus = page.locator("//div[contains(text(),'Domicile/Residence Status')]");

        // ===== Documentation =====
        selectDocumentation = page.locator("//div[contains(text(),'Select Documentation')]");
        personalIdentityDocuments = page.locator("//div[text()='Personal & Identity Documents']");
        academicDocuments = page.locator("//div[text()='Academic Documents']");
        conditionalStatutoryDocuments = page.locator("//div[text()='Conditional & Statutory Documents']");
        affidavitsUndertakings = page.locator("//div[text()='Special Affidavits & Undertakings']");

        // ===== Custom Field =====
        customFieldDropdown = page.locator("#customFieldMulti > .css-13cymwt-control > .css-hlgwow > .css-19bb58m");
        customFieldCategoryText = page.getByText(
                "Personal Identification & DemographicsAcademic HistoryEntrance Exam & Test"
        );
        customFieldNameInput = page.locator("//input[@placeholder='Enter Field Name']");
        addButton = page.locator("//button[text()='Add']");
        requiredCheckboxes = page.locator(".applications-field-required-checkbox");

        // ===== Close =====
        closePopupIcon = page.locator("[class='text-end close-img position-absolute']");
        createButton= page.locator("//button[text()='Create']");
    }

    // ===== Common =====
    private void clickAllSubOptions() {
        int count = subOptions.count();
        for (int i = 0; i < count; i++) {
            subOptions.nth(i).click();
        }
    }

    // ===== Getters (following your pattern) =====
    public Locator clickAddApplication() { return addApplicationBtn; }
    public Locator clickCreateApplication() { return createApplicationBtn; }
    public Locator createManually() { return createManuallyBtn; }

    public Locator enterApplicationFormName() { return enterApplicationFormName; }
    public Locator openingDate() { return openingDate; }
    public Locator closingDate() { return closingDate; }
    public Locator streetAdress() { return streetAdress; }
    public Locator pinCode() { return pinCode; }
    public Locator city() { return city; }
    public Locator state() { return state; }
    public Locator applicationYear() { return applicationYear; }
    public Locator applicationCost() { return applicationCost; }
    public Locator selectAccreditation() { return selectAccreditation; }

    public Locator selectPersonalIdentificationAndDemographics() { return selectPersonalIdentificationAndDemographics; }
    public Locator selectLegalName() { return selectLegalName; }

    public Locator fieldSelectionAndPreview() { return fieldSelectionAndPreview; }
    public Locator nextButton() { return nextButton; }
    public Locator selectedOptionCross() { return selectedOptionCross; }

    public Locator selectAcademicHistory() { return selectAcademicHistory; }
    public Locator class10Details() { return class10Details; }
    public Locator class12Details() { return class12Details; }
    public Locator universityLastAttended() { return universityLastAttended; }
    public Locator degreeCertificate() { return degreeCertificate; }
    public Locator workExperienceCertificate() { return workExperienceCertificate; }

    public Locator entranceExamsSection() { return entranceExamsSection; }
    public Locator nationalLevelExams() { return nationalLevelExams; }
    public Locator stateLevelExams() { return stateLevelExams; }
    public Locator universitySpecificExams() { return universitySpecificExams; }
    public Locator selectNationalLevelExam() { return selectNationalLevelExam; }
    public Locator addMoreLink() { return addMoreLink; }

    public Locator categoryEligibilitySection() { return categoryEligibilitySection; }
    public Locator categoryOfAdmission() { return categoryOfAdmission; }
    public Locator documentaryEvidence() { return documentaryEvidence; }
    public Locator pwdStatus() { return pwdStatus; }
    public Locator sportsQuota() { return sportsQuota; }
    public Locator ecaQuota() { return ecaQuota; }
    public Locator domicileStatus() { return domicileStatus; }

    public Locator selectDocumentation() { return selectDocumentation; }
    public Locator personalIdentityDocuments() { return personalIdentityDocuments; }
    public Locator academicDocuments() { return academicDocuments; }
    public Locator conditionalStatutoryDocuments() { return conditionalStatutoryDocuments; }
    public Locator affidavitsUndertakings() { return affidavitsUndertakings; }

    public Locator customFieldDropdown() { return customFieldDropdown; }
    public Locator customFieldCategoryText() { return customFieldCategoryText; }
    public Locator customFieldNameInput() { return customFieldNameInput; }
    public Locator addButton() { return addButton; }
    public Locator requiredCheckboxes() { return requiredCheckboxes; }

    public Locator closePopupIcon() { return closePopupIcon; }
    public Locator createButton() {return createButton;}
}
