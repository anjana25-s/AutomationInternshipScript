package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddEmploymentDetails {
	
    private final Page page;
    
    private Locator YesRadioBox;
    private Locator NoRadioBox;
    private Locator FullTimeButton;
    private Locator InternshipButton;
    private Locator CurrentCompanyDropdown;
    private Locator AddCompanyManually;
    private Locator CurrentDesignation;
    private Locator AddDesignationManually;
    private Locator DesignationAddButton;
    private Locator CompanyAddButton;
    private Locator JoiningYear;
    private Locator JoiningMonth;
    private Locator SkillsUsed;
    private Locator Jobprofile;
    private Locator NoticePeriod;
    private Locator SaveButton;
    private Locator currentSalary;
    
    public AddEmploymentDetails(Page page) {
        this.page = page;
        
        this.YesRadioBox = page.locator("//input[@id='Yes']");
        this.NoRadioBox = page.locator("//input[@id='No']");
        this.FullTimeButton = page.locator("//button[@id='FullTime']");
        this.InternshipButton = page.locator("//button[@id='Internship']");
        this.CurrentCompanyDropdown = page.locator("//div[@id='react-select-19-placeholder']");
        this.CurrentDesignation = page.locator("//div[text()='Search or select current designation']");
        this.AddCompanyManually = page.locator("//input[@placeholder='Add your Company Name']");
        this.AddDesignationManually = page.locator("//input[@placeholder='Add your Designation']");
        this.DesignationAddButton = page.locator("//button[@class='add-skills-btn add-company-name p-0 btn btn-primary']");
        this.CompanyAddButton = page.locator("//input[@placeholder='Add your Company Name']/following::button[contains(@class, 'add-company-name') and text()='Add'][1]");
        this.JoiningYear = page.locator("//input[@id='react-select-66-input']");
        this.JoiningMonth = page.locator("//div[text()='Select Month']/following-sibling::div//input[@type='text' and not(@aria-hidden='true')]");
        this.SkillsUsed = page.locator("//div[@id='react-select-69-placeholder']");
        this.Jobprofile = page.locator("//textarea[@placeholder='Job Profile ...']");
        this.NoticePeriod = page.locator("//div[text()='Choose notice period']");
        this.SaveButton = page.locator("button[class='save-resume-btn']");
        this.currentSalary = page.locator("//div[contains(text(), 'Current Salary')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
    }
    
    public Locator YesRadioBox() {return YesRadioBox;}
    public Locator NoRadioBox() {return NoRadioBox;}
    public Locator FullTimeButton() {return FullTimeButton;}
    public Locator InternshipButton() {return InternshipButton;}
    public Locator CurrentCompanyDropdown() {return CurrentCompanyDropdown;}
    public Locator AddCompanyManually() {return AddCompanyManually;}
    public Locator AddDesignationManually(){return AddDesignationManually;}
    public Locator DesignationAddButton(){return DesignationAddButton;}
    public Locator CompanyAddButton() {return CompanyAddButton;}
    public Locator JoiningYear() {return JoiningYear;}
    public Locator JoiningMonth() {return JoiningMonth;}
    public Locator SkillsUsed() {return SkillsUsed;}
    public Locator NoticePeriod() {return NoticePeriod;}
    public Locator SaveButton() {return SaveButton;}
    public Locator currentSalary() {return currentSalary;}

    


}
