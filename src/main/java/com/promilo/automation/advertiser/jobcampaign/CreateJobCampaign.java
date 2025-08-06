
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
	   this.CampaingnStartDate= page.locator("//input[@placeholder='Campaign Start Date']");
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
	

}
