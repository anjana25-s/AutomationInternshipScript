
package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateJobCampaign {
	
	private Page page;
	
	private final Locator campaingnName;
	private final Locator JobRole;
	private final Locator BrandName;
	private final Locator CompanyName;
	private final Locator JobRoleDropdown;
	private final Locator IndustryDropdown;
	private final Locator Descrption;
	private final Locator minExperiance;
	private final Locator maxExperiance;
	private final Locator CampaingnStartDate;
	private final Locator CampaignEndDate;
	private final Locator Workmode;
	private final Locator UploadCompanyLogo;
	private final Locator SaveButton;
	private final Locator CompanyDetailsText;
	private final Locator AddTitle;
	private final Locator BenefitsAndPerks;
	private final Locator Eligibility;
	private final Locator RecruitmentProcess;
	private final Locator CompanyCulture;
	private final Locator others;
	private final Locator ValuesandMission;
	private final Locator AddButton;
	private final Locator textArea;
	private final Locator saveAndnextButton;
	private final Locator CompanyType;
	private final Locator LocationDropdown;
	
	// ===== CAMPAIGN DETAILS =====
    private final Locator industryAccountingOption;
    private final Locator jobRoleFinanceOption;
    private final Locator cropNextButton;
    private final Locator benefitsTab;
    private final Locator benefitsTextArea;

    // ===== TIME SLOT SECTION =====
    private final Locator mondayCheckbox;
    private final Locator addSlotButton;
    private final Locator timeSlotContainer;

    // ===== SCREENING QUESTIONS =====
    private final Locator questionInput;
    private final Locator addOptionButton;
    private final Locator optionInput;

    // ===== PREVIEW PAGE =====
    private final Locator previewTitle;
    private final Locator previewSubtitle;
    private final Locator previewSalaryBlock;
    private final Locator previewJobDetailBlock;
    private final Locator jobDescriptionTab;
    private final Locator ckEditorContent;
    private final Locator previewBenefitsTab;
    private final Locator previewOtherDetailsBlock;
    private final Locator previewAboutTitle;
    private final Locator previewParagraph9;

    private final Locator previewCloseButton;
    private final Locator publishButton;

	
	
   public 	CreateJobCampaign(Page page) {
	   this.page=page;
	   
	   this.campaingnName= page.locator("//input[@placeholder='Campaign Name']");
	   this.JobRole= page.locator("//input[@placeholder='Job Role']");
	   this.BrandName= page.locator("//input[@placeholder='Brand Name']");
	   this.CompanyName= page.locator("//input[@id='react-select-9-input']");
	   this.JobRoleDropdown= page.locator("#react-select-4-input");
	   this.IndustryDropdown= page.locator("//div[normalize-space(text())='Industry']/following-sibling::div//input[@type='text']");
	   this.Descrption= page.locator("textarea[name='description']");
	   this.minExperiance= page.locator("//div[text()='Min Experience']");
	   this.maxExperiance= page.locator("//div[text()='Max Experience']");
	   this.CampaingnStartDate= page.locator("//input[@class='react-select form-control']");
	   this.CampaignEndDate= page.locator("[placeholder='Campaign End Date']");
	   this.Workmode= page.locator("//div[text()='Work Mode']");
	   this.UploadCompanyLogo= page.locator("//span[text()='Upload Company Logo']");
	   this.SaveButton= page.locator("//button[text()='Save & Next']");
	   this.CompanyDetailsText= page.locator("div[aria-label='Editor editing area: main']");
	   this.AddTitle= page.locator("//a[text()='+ Add Title']");
	   this.BenefitsAndPerks=page.locator("input[id='Benefits & Perks']");
	   this.Eligibility= page.locator("//input[@id='Eligibility']");
	   this.RecruitmentProcess= page.locator("//label[@for='Recruitment Process']");
	   this.CompanyCulture= page.locator("//label[@for='Company Culture']");
	   this.others = page.locator("//label[text()='Others']");
	   this.ValuesandMission= page.locator("//label[text()='Values & Mission']");
	   this.AddButton = page.locator("//button[text()='Add']");
	   this.textArea = page.locator("div[role='textbox']");
	   this.saveAndnextButton= page.locator("//button[@class='btn-next me-2 btn btn-primary']");
	   this.CompanyType= page.locator("#react-select-2-input");
	   this.LocationDropdown= page.locator("#react-select-7-input");
	   
	   
	// ================================
       // CAMPAIGN DETAILS
       // ================================
       this.industryAccountingOption = page.locator("//div[contains(text(),'Accounting / Tax / Company Secretary / Audit')]");
       this.jobRoleFinanceOption = page.locator("//div[text()='Head/VP/GM-Finance/Audit']");
       this.cropNextButton = page.locator("button[class='crop-next-buttonN btn btn-secondary']");
       this.benefitsTab = page.locator("//a[text()='Benefits & Perks']");
       this.benefitsTextArea = page.locator("//div[@class='ck-content']");

       // ================================
       // TIME SLOTS
       // ================================
       this.mondayCheckbox = page.locator("//label[text()='MONDAY']");
       this.addSlotButton = page.locator("//button[@class='plus-icon btn btn-secondary']");
       this.timeSlotContainer = page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");

       // ================================
       // SCREENING QUESTIONS
       // ================================
       this.questionInput = page.locator("input[placeholder='Type Question Here']");
       this.addOptionButton = page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']");
       this.optionInput = page.locator("//input[@class='border-0 outline-0 ms-1 form-control']");

       // ================================
       // PREVIEW PAGE
       // ================================
       this.previewTitle = page.locator("//p[@class='font-28 text-black fw-500']");
       this.previewSubtitle = page.locator("//p[@class='font-16 text-dark-grey  fw-500']");
       this.previewSalaryBlock = page.locator("//div[@class='d-flex logo-text col-6']");
       this.previewJobDetailBlock = page.locator("//div[@class='font-16 ']");
       this.jobDescriptionTab = page.locator("//a[text()='Job Description']");
       this.ckEditorContent = page.locator("//div[@class='ck-content']");
       this.previewBenefitsTab = page.locator("//a[text()='Benefits & Perks']");
       this.previewOtherDetailsBlock = page.locator("//div[@class='font-14 ']");
       this.previewAboutTitle = page.locator("//p[@class='font-20 text-black fw-700']");
       this.previewParagraph9 = page.locator("(//p)[9]");

       this.previewCloseButton = page.locator("//img[@alt='close']");
       this.publishButton = page.locator("//span[text()='Publish']");

	   
	   
	   
   }
	public Locator campaingnName() {return campaingnName;}
	public Locator JobRole() {return JobRole;}
	public Locator BrandName() {return BrandName;}
	public Locator CompanyName() {return CompanyName;}
	public Locator JobRoleDropdown() {return JobRoleDropdown;}
	public Locator IndustryDropdown() {return IndustryDropdown;}
	public Locator Descrption() {return Descrption;}
	public Locator minExperiance() {return minExperiance;}
	public Locator maxExperiance() {return maxExperiance;}
	public Locator CampaingnStartDate() {return CampaingnStartDate;}
	public Locator CampaignEndDate() {return CampaignEndDate;}
	public Locator Workmode() {return Workmode;}
	public Locator UploadCompanyLogo() {return UploadCompanyLogo;}
	public Locator SaveButton() {return SaveButton;}
	public Locator CompanyDetailsText() {return CompanyDetailsText;}
	public Locator AddTitle() {return AddTitle;}
	public Locator BenefitsAndPerks() {return BenefitsAndPerks;}
	public Locator Eligibility() {return Eligibility;}
	public Locator RecruitmentProcess() {return RecruitmentProcess;}
	public Locator others() {return others;}
	public Locator ValuesandMission() {return ValuesandMission;}
	public Locator AddButton() {return AddButton;}
	public Locator textArea() {return textArea;}
	public Locator saveAndnextButton() {return saveAndnextButton;}
	public Locator CompanyType() {return CompanyType;}
	public Locator LocationDropdown() {return LocationDropdown;}
	
	public Locator industryAccountingOption() { return industryAccountingOption; }
    public Locator jobRoleFinanceOption() { return jobRoleFinanceOption; }
    public Locator cropNextButton() { return cropNextButton; }
    public Locator benefitsTab() { return benefitsTab; }
    public Locator benefitsTextArea() { return benefitsTextArea; }

    public Locator mondayCheckbox() { return mondayCheckbox; }
    public Locator addSlotButton() { return addSlotButton; }
    public Locator timeSlotContainer() { return timeSlotContainer; }

    public Locator questionInput() { return questionInput; }
    public Locator addOptionButton() { return addOptionButton; }
    public Locator optionInput() { return optionInput; }

    public Locator previewTitle() { return previewTitle; }
    public Locator previewSubtitle() { return previewSubtitle; }
    public Locator previewSalaryBlock() { return previewSalaryBlock; }
    public Locator previewJobDetailBlock() { return previewJobDetailBlock; }
    public Locator jobDescriptionTab() { return jobDescriptionTab; }
    public Locator ckEditorContent() { return ckEditorContent; }
    public Locator previewBenefitsTab() { return previewBenefitsTab; }
    public Locator previewOtherDetailsBlock() { return previewOtherDetailsBlock; }
    public Locator previewAboutTitle() { return previewAboutTitle; }
    public Locator previewParagraph9() { return previewParagraph9; }

    public Locator previewCloseButton() { return previewCloseButton; }
    public Locator publishButton() { return publishButton; }
	

}
