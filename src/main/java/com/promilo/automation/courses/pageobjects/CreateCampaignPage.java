package com.promilo.automation.courses.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateCampaignPage {

    private final Page page;

    // Locators
    private final Locator addNewCampaignButton;
    private final Locator firstCampaignCard;
    private final Locator campaignNameField;
    private final Locator productTitleField;
    private final Locator displayBrandNameField;
    private final Locator dropdownBasic;
    private final Locator engineeringButton;
    private final Locator beBtechOption;
    private final Locator descriptionField;
    private final Locator selectYearDropdown;
    private final Locator yearOption;
    private final Locator numbersInput;
    private final Locator chooseAppointmentSvg;
    private final Locator locationDropdown;
    private final Locator startDateField;
    private final Locator endDateField;
    private final Locator deliveryModeDropdown;
    private final Locator regularOfflineOption;
    private final Locator uploadImageButton;
    private final Locator cropNextButton;
    private final Locator saveAndNextButton;
    private final Locator addTitleButton;
    private final Locator checkboxes;
    private final Locator addButton;
    private final Locator tabTextbox;
    private final Locator coursesAndFeesTab;
    private final Locator syllabusTab;
    private final Locator saveNextSpan;
    private final Locator uploadLinkDiv;
    private final Locator driveLinkInput;
    private final Locator submitInput;
    private final Locator addPhotosOrVideosDiv;
    private final Locator cropButton;
    private final Locator saveNextP;
    private final Locator monday;
    private final Locator plusIconButton;
    private final Locator timeSlotInputContainer;
    private final Locator typeQuestionInput;
    private final Locator feedbackOptionField;
    private final Locator feedbackAddButton;
    private final Locator feedbackSQuestion;
    private final Locator urlTitleInput;
    private final Locator campaignUrlInput;
    private final Locator documentTitleInput;
    private final Locator uploadDocumentButton;
    private final Locator audienceIndustryDropdown;
    private final Locator animationVfxOption;
    private final Locator functionalAreaDropdown;
    private final Locator engineeringElectricalOption;
    private final Locator relevantTitleDropdown;
    private final Locator electricalEngineerOption;
    private final Locator keywordButton;
    private final Locator minAgeDropdown;
    private final Locator maxAgeDropdown;
    private final Locator searchNameInput;
    private final Locator emailCheckbox;
    private final Locator smsCheckbox;
    private final Locator notificationCheckbox;
    private final Locator totalCostInput;
    private final Locator prospectCountInput;
    private final Locator savePreviewButton;
    
    
    
    
    private final Locator brandName;
    private final Locator productTitle;
    private final Locator location;
    private final Locator duration;
    private final Locator averageFees;
    private final Locator highlightText;
    private final Locator highlightTextDescription;
    private final Locator collegeInfoTab;
    private final Locator coursesAndFees;
    private final Locator syllubus;
    private final Locator tabsContent;
    private final Locator closePreview;
    private final Locator publishButton;
    
    
    
    private Locator ChoseTimeSlot;
	private Locator Monday;
	private Locator Tuesday;
	private Locator Wednsday;
	private Locator Thursday;
	private Locator Friday;
	private Locator Saturday;
	private Locator Sunday;
	
	private Locator English;
	private Locator Hindi;
	private Locator Bangla;
	private Locator Marathi;
	private Locator Telugu;
	private Locator Tamil;
	private Locator Gujarati;
	private Locator Urdu;
	private Locator Kannada;
	private Locator Odia;
	private Locator Malayalam;
	private Locator Punjabi;
	private Locator Assamese;
	private Locator TimeslotButton;
	private Locator timeSlotContainer;
	
	private Locator SaveButton;

    public CreateCampaignPage(Page page) {
        this.page = page;

        addNewCampaignButton = page.locator("//span[text()='Add New Campaign']");
        firstCampaignCard = page.locator("//div[@class='pointer card-body']");
        campaignNameField = page.locator("//input[@placeholder='Enter Campaign Name']");
        productTitleField = page.locator("//input[@placeholder='Enter Product Title']");
        displayBrandNameField = page.locator("//input[@placeholder='Enter your Display Brand Name content']");
        dropdownBasic = page.locator("#dropdown-basic");
        engineeringButton = page.locator("//button[text()='Engineering']");
        beBtechOption = page.locator("//a[text()='B.E. / B.Tech']");
        descriptionField = page.locator("//textarea[@name='description']");
        selectYearDropdown = page.locator("//div[text()='Select']");
        yearOption = page.locator("//div[text()='Year']");
        numbersInput = page.locator("//input[@placeholder='Enter Numbers']");
        chooseAppointmentSvg = page.locator("#ChooseAppointmentPage svg");
        locationDropdown = page.locator("//div[text()='Ahmedabad']");
        startDateField = page.locator("//input[@name='startDate']");
        endDateField = page.locator("//input[@name='endDate']");
        deliveryModeDropdown = page.locator("//div[text()='Select Delivery Mode']");
        regularOfflineOption = page.locator("//div[text()='Regular / Offline']");
        uploadImageButton = page.locator("//span[text()='Upload Image']");
        cropNextButton = page.locator("//button[@class='crop-next-buttonN btn btn-secondary']");
        saveAndNextButton = page.locator("//button[text()='Save & Next']");
        addTitleButton = page.locator("//a[text()='+ Add Title']");
        checkboxes = page.locator("//input[@type='checkbox']");
        addButton = page.locator("//button[text()='Add']");
        tabTextbox = page.locator("//div[@role='textbox']");
        coursesAndFeesTab = page.locator("//a[text()='Courses & Fees']");
        syllabusTab = page.locator("//a[text()='Syllabus']");
        saveNextSpan = page.locator("//span[text()='Save & Next']");
        uploadLinkDiv = page.locator("//div[text()='Upload Link']");
        driveLinkInput = page.locator("//input[@placeholder='E.g. https://drive.google.com/mydocument']");
        submitInput = page.locator("//input[@type='submit']");
        addPhotosOrVideosDiv = page.locator("//div[text()='Add Photos or Videos']");
        cropButton = page.locator("//button[text()='Crop']");
        saveNextP = page.locator("//p[text()='Save & Next']");
        monday = page.locator("input[id='MONDAY_video']");
        plusIconButton = page.locator("//button[@class='plus-icon btn btn-secondary']");
        timeSlotInputContainer = page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");
        typeQuestionInput = page.locator("input[placeholder='Type Question Here']");
        feedbackOptionField = page.locator("//input[@placeholder='Type Option']");
        feedbackAddButton = page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']");
        feedbackSQuestion = page.locator("//input[@class='border-0 outline-0 ms-1 form-control']");
        urlTitleInput = page.locator("//input[@placeholder='URL Title']");
        campaignUrlInput = page.locator("//input[@name='campaignUrl']");
        documentTitleInput = page.locator("//input[@placeholder='Document Title']");
        uploadDocumentButton = page.locator("//span[text()='Upload Document']");
        audienceIndustryDropdown = page.locator("//input[@placeholder='Select Audience Industry']");
        animationVfxOption = page.locator("//li[text()='Animation & VFX']");
        functionalAreaDropdown = page.locator("//span[text()='Select Functional Area']");
        engineeringElectricalOption = page.locator("//span[text()='Engineering - Electrical']");
        relevantTitleDropdown = page.locator("//input[@placeholder='Select Relevant Title']");
        electricalEngineerOption = page.locator("//li[text()='Electrical Engineer']");
        keywordButton = page.locator("//button[text()='Electrical Engineer']");
        minAgeDropdown = page.locator("//div[@class='min-age']");
        maxAgeDropdown = page.locator("//div[@class='max-age']");
        searchNameInput = page.locator("[name='search_name_input']");
        emailCheckbox = page.locator("//input[@id='EMAIL']");
        smsCheckbox = page.locator("//input[@id='SMS']");
        notificationCheckbox = page.locator("//input[@id='NOTIFICATION']");
        totalCostInput = page.locator("//input[@placeholder='Enter total cost of solution']");
        prospectCountInput = page.locator("//input[@placeholder='Enter the no. of prospect you want to connect']");
        savePreviewButton = page.locator("//button[text()='Save & Preview']");
        brandName = page.locator("//p[@class='font-18 text-black fw-500 mb-25']");
        productTitle = page.locator("//p[@class='font-14 text-dark-grey fw-500 mb-50']");
        location = page.locator("//div[@class='d-flex logo-text col-6 align-items-center']");
        duration = page.locator("//div[@class='font-16 ']");
        averageFees = page.locator("//div[@class='font-16 ']");
        highlightText = page.locator("(//p)[13]");
        highlightTextDescription = page.locator("//div[@class='row mt-2']");
        collegeInfoTab = page.locator("//li[@id='tab_0']");
        coursesAndFees = page.locator("//li[@id='tab_1']");
        syllubus = page.locator("//li[@id='tab_2']");
        tabsContent = page.locator("[class='ck-content']");
        closePreview = page.locator("//img[@alt='close']");
        publishButton = page.locator("//button[text()='Publish']");
        this.timeSlotContainer = page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");

        
        
        this.ChoseTimeSlot= page.locator("//div[@class='time-slot-select__value-container time-slot-select__value-container--has-value css-hlgwow']");
  	  this.TimeslotButton= page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");
  	this.Monday= page.locator("input[id='MONDAY']");  
  	this.Tuesday= page.locator("input[id='TUESDAY']");
  	this.Wednsday= page.locator("input[id='WEDNESDAY']");
  	this.Thursday= page.locator("input[id='THURSDAY']");
  	this.Friday= page.locator("input[id='FRIDAY']");
  	this.Saturday= page.locator("input[id='SATURDAY']");
  	this.Sunday= page.locator("input[id='SUNDAY']");
  	
  	this.English= page.locator("label[for='English_video']");
  	this.Hindi= page.locator("label[for='Hindi_video']");
  	this.Bangla= page.locator("label[for='Bangla']");
  	this.Marathi= page.locator("label[for='Marathi']");
  	this.Telugu= page.locator("label[for='Telugu']");
  	this.Tamil= page.locator("label[for='Tamil']");
  	this.Gujarati= page.locator("label[for='Gujarati']");
  	this.Urdu= page.locator("label[for='Urdu']");
  	this.Kannada= page.locator("label[for='Kannada_video']");
  	this.Odia= page.locator("label[for='Odia']");
  	this.Malayalam= page.locator("label[for='Malayalam']");
  	this.Punjabi= page.locator("label[for='Punjabi']");
  	this.Assamese= page.locator("label[for='Assamese']");
  	this.SaveButton= page.locator("//button[text()='Save & Next']");

    }

    // Getter methods
    public Locator addNewCampaignButton() { return addNewCampaignButton; }
    public Locator firstCampaignCard() { return firstCampaignCard; }
    public Locator campaignNameField() { return campaignNameField; }
    public Locator productTitleField() { return productTitleField; }
    public Locator displayBrandNameField() { return displayBrandNameField; }
    public Locator dropdownBasic() { return dropdownBasic; }
    public Locator engineeringButton() { return engineeringButton; }
    public Locator beBtechOption() { return beBtechOption; }
    public Locator descriptionField() { return descriptionField; }
    public Locator selectYearDropdown() { return selectYearDropdown; }
    public Locator yearOption() { return yearOption; }
    public Locator numbersInput() { return numbersInput; }
    public Locator chooseAppointmentSvg() { return chooseAppointmentSvg; }
    public Locator locationDropdown() { return locationDropdown; }
    public Locator startDateField() { return startDateField; }
    public Locator endDateField() { return endDateField; }
    public Locator deliveryModeDropdown() { return deliveryModeDropdown; }
    public Locator regularOfflineOption() { return regularOfflineOption; }
    public Locator uploadImageButton() { return uploadImageButton; }
    public Locator cropNextButton() { return cropNextButton; }
    public Locator saveAndNextButton() { return saveAndNextButton; }
    public Locator addTitleButton() { return addTitleButton; }
    public Locator checkboxes() { return checkboxes; }
    public Locator addButton() { return addButton; }
    public Locator tabTextbox() { return tabTextbox; }
    public Locator coursesAndFeesTab() { return coursesAndFeesTab; }
    public Locator syllabusTab() { return syllabusTab; }
    public Locator saveNextSpan() { return saveNextSpan; }
    public Locator uploadLinkDiv() { return uploadLinkDiv; }
    public Locator driveLinkInput() { return driveLinkInput; }
    public Locator submitInput() { return submitInput; }
    public Locator addPhotosOrVideosDiv() { return addPhotosOrVideosDiv; }
    public Locator cropButton() { return cropButton; }
    public Locator saveNextP() { return saveNextP; }
    public Locator monday() { return monday; }
    public Locator plusIconButton() { return plusIconButton; }
    public Locator timeSlotInputContainer() { return timeSlotInputContainer; }
    public Locator typeQuestionInput() { return typeQuestionInput; }
    public Locator feedbackOptionField() { return feedbackOptionField; }
    public Locator feedbackAddButton() { return feedbackAddButton; }
    public Locator feedbackSQuestion() { return feedbackSQuestion; }
    public Locator urlTitleInput() { return urlTitleInput; }
    public Locator campaignUrlInput() { return campaignUrlInput; }
    public Locator documentTitleInput() { return documentTitleInput; }
    public Locator uploadDocumentButton() { return uploadDocumentButton; }
    public Locator audienceIndustryDropdown() { return audienceIndustryDropdown; }
    public Locator animationVfxOption() { return animationVfxOption; }
    public Locator functionalAreaDropdown() { return functionalAreaDropdown; }
    public Locator engineeringElectricalOption() { return engineeringElectricalOption; }
    public Locator relevantTitleDropdown() { return relevantTitleDropdown; }
    public Locator electricalEngineerOption() { return electricalEngineerOption; }
    public Locator keywordButton() { return keywordButton; }
    public Locator minAgeDropdown() { return minAgeDropdown; }
    public Locator maxAgeDropdown() { return maxAgeDropdown; }
    public Locator searchNameInput() { return searchNameInput; }
    public Locator emailCheckbox() { return emailCheckbox; }
    public Locator smsCheckbox() { return smsCheckbox; }
    public Locator notificationCheckbox() { return notificationCheckbox; }
    public Locator totalCostInput() { return totalCostInput; }
    public Locator prospectCountInput() { return prospectCountInput; }
    public Locator savePreviewButton() { return savePreviewButton; }
    public Locator brandName() { return brandName; }
    public Locator productTitle() { return productTitle; }
    public Locator location() { return location; }
    public Locator duration() { return duration; }
    public Locator averageFees() { return averageFees; }
    public Locator highlightText() { return highlightText; }
    public Locator highlightTextDescription() { return highlightTextDescription; }
    public Locator collegeInfoTab() { return collegeInfoTab; }
    public Locator coursesAndFees() { return coursesAndFees; }
    public Locator syllubus() { return syllubus; }
    public Locator tabsContent() { return tabsContent; }
    public Locator closePreview() { return closePreview; }
    public Locator publishButton() { return publishButton; }
    
    
    public Locator ChoseTimeSlot() {return ChoseTimeSlot;}
    public Locator Monday() {return Monday;}
    public Locator Tuesday() {return Tuesday;}
    public Locator Wednsday() {return Wednsday;}
    public Locator Thursday() {return Thursday;}
    public Locator Sunday() {return Sunday;}
    public Locator Friday() {return Friday;}
    public Locator Saturday() {return Saturday;}
    
    
    public Locator English() {return English;}
    public Locator Hindi() {return Hindi;}
    public Locator Telugu() {return Telugu;}
    public Locator Kannada() {return Kannada;}
    public Locator Tamil() {return Tamil;}
    public Locator Malayalam() {return Malayalam;}
    public Locator Odia() {return Odia;}
    public Locator Punjabi() {return Punjabi;}
    public Locator Assamese() {return English;}
    public Locator Gujarati() {return Gujarati;}
    public Locator Marathi() {return Marathi;}
    public Locator Urdu() {return Urdu;}
    public Locator Bangla () {return Bangla;}
    public Locator TimeslotButton() {return TimeslotButton;}
    
    public Locator SaveButton() {return SaveButton;}
    public Locator timeSlotContainer() {return timeSlotContainer;}

}
