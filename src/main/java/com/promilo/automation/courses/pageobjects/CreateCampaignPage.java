package com.promilo.automation.courses.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateCampaignPage {

    private Page page;

    public CreateCampaignPage(Page page) {
        this.page = page;
    }

    // Navigation / Campaign Section
    public Locator menuToggle() { return page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']"); }
    public Locator campaign() { return page.locator("//button[text()='B2B Appointment']"); }

    // Campaign Details
    public Locator campaignNameField() { return page.locator("//input[@placeholder='Enter Campaign Name']"); }
    public Locator productTitleField() { return page.locator("//input[@placeholder='Enter Product Title']"); }
    public Locator brandNameField() { return page.locator("//input[@placeholder='Enter your Display Brand Name content']"); }
    public Locator categoryDropdown() { return page.locator("#dropdown-basic"); }
    public Locator categoryEngineering() { return page.locator("//button[text()='Engineering']"); }
    public Locator subCategoryBE() { return page.locator("//a[text()='B.E. / B.Tech']"); }
    public Locator descriptionArea() { return page.locator("//textarea[@name='description']"); }

    // Experience & Location
    public Locator selectDropdown() { return page.locator("//div[text()='Select']"); }
    public Locator yearOption() { return page.locator("//div[text()='Year']"); }
    public Locator numbersField() { return page.locator("//input[@placeholder='Enter Numbers']"); }
    public Locator locationDropdown() { return page.locator("//div[text()='Select Your Current Location ']"); }
    public Locator locationAhmedabad() { return page.locator("//div[text()='Ahmedabad']"); }
    public Locator startDateField() { return page.locator("//input[@name='startDate']"); }
    public Locator endDateField() { return page.locator("//input[@name='endDate']"); }
    public Locator deliveryModeDropdown() { return page.locator("//div[text()='Select Delivery Mode']"); }
    public Locator offlineMode() { return page.locator("//div[text()='Regular / Offline']"); }

    // Image Upload
    public Locator uploadImage() { return page.locator("//span[text()='Upload Image']"); }
    public Locator cropNextButton() { return page.locator("//button[@class='crop-next-buttonN btn btn-secondary']"); }
    public Locator saveNextButton() { return page.locator("//button[text()='Save & Next']"); }

    // Title & Checkboxes
    public Locator addTitle() { return page.locator("//a[text()='+ Add Title']"); }
    public Locator allCheckboxes() { return page.locator("//input[@type='checkbox']"); }
    public Locator addButton() { return page.locator("//button[text()='Add']"); }

    // Upload Link & Video
    public Locator uploadLink() { return page.locator("//div[text()='Upload Link']"); }
    public Locator linkInput() { return page.locator("//input[@placeholder='E.g. https://drive.google.com/mydocument']"); }
    public Locator linkSubmit() { return page.locator("//input[@type='submit']"); }
    public Locator uploadVideo() { return page.locator("//div[text()='Add Photos or Videos']"); }
    public Locator cropButton() { return page.locator("//button[text()='Crop']"); }
    public Locator saveNextParagraph() { return page.locator("//p[text()='Save & Next']"); }

    // Time Slot Section
    public Locator timeSlotInput() { return page.locator("//div[@class='time-slot-select__input-container css-19bb58m']"); }
    public Locator plusIcon() { return page.locator("//button[@class='plus-icon btn btn-secondary']"); }

    // Questions
    public Locator typeQuestionField() { return page.locator("//input[@placeholder='Type Question Here']"); }
    public Locator requiredToggle() { return page.locator("(//label[@for='required0'])[1]"); }
    public Locator saveNextSpan() { return page.locator("//span[text()='Save & Next']"); }

    // Feedback
    public Locator addButtonFeedback() { return page.locator("//button[@class='btn-icon btn-next align-items-center d-flex justify-content-between btn btn-primary']"); }

    // Campaign URLs & Docs
    public Locator urlTitleField() { return page.locator("//input[@placeholder='URL Title']"); }
    public Locator campaignUrlField() { return page.locator("//input[@name='campaignUrl']"); }
    public Locator documentTitleField() { return page.locator("//input[@placeholder='Document Title']"); }
    public Locator uploadDocument() { return page.locator("//span[text()='Upload Document']"); }

    // Audience
    public Locator audienceIndustryDropdown() { return page.locator("//input[@placeholder='Select Audience Industry']"); }
    public Locator industryOption() { return page.locator("//li[text()='Animation & VFX']"); }
    public Locator functionalAreaDropdown() { return page.locator("//span[text()='Select Functional Area']"); }
    public Locator functionalOption() { return page.locator("//span[text()='Engineering - Electrical']"); }
    public Locator relevantTitleDropdown() { return page.locator("//input[@placeholder='Select Relevant Title']"); }
    public Locator relevantOption() { return page.locator("//li[text()='Electrical Engineer']"); }
    public Locator keywords() { return page.locator("//button[text()='Electrical Engineer']"); }
    public Locator minAgeField() { return page.locator("//div[text()='Select Audience Min Age']"); }
    public Locator maxAgeField() { return page.locator("//div[text()='Select Audience Max Age']"); }
    public Locator searchNameInput() { return page.locator("[name='search_name_input']").nth(1); }

    // Notifications
    public Locator sendEmail() { return page.locator("//input[@id='Send Email']"); }
    public Locator sendSMS() { return page.locator("//input[@id='Send SMS']"); }
    public Locator notification() { return page.locator("//input[@id='Notification']"); }

    // Budget
    public Locator costField() { return page.locator("//input[@placeholder='Enter the total cost of solution']"); }
    public Locator prospectsField() { return page.locator("//input[@placeholder='Enter the number of prospect you want to connect']"); }

    // Submit & Publish
    public Locator submitButton() { return page.locator("//button[@type='submit']").first(); }
    public Locator closeButton() { return page.locator("//button[@class='btn-close']"); }
    public Locator publishButton() { return page.locator("//span[text()='Publish']"); }
}
