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
    private Locator Currentdesignatipondropdown;
    private Locator JoiningYear;
    private Locator JoiningMonth;
    private Locator SkillsUsed;
    private Locator Jobprofile;
    private Locator NoticePeriod;
    private Locator SaveButton;
    private Locator currentSalary;
    private Locator joiningYearoption;
    private Locator joiningMonthoption;
    private Locator noticePeriodoption;
    private Locator currentSalaryoption;
    private Locator currentCompanyoption;
    private Locator currentDesignationoption;
    private Locator skillsOption;
    private Locator description;
    
    public AddEmploymentDetails(Page page) {
        this.page = page;
        
        this.YesRadioBox = page.locator("//input[@id='Yes']");
        this.NoRadioBox = page.locator("//input[@id='No']");
        this.FullTimeButton = page.locator("//button[@id='FullTime']");
        this.InternshipButton = page.locator("//button[@id='Internship']");
        this.CurrentCompanyDropdown = page.locator("(//div[@class='react-select__input-container css-19bb58m'])[1]");
        this.Currentdesignatipondropdown = page.locator("div.react-select__value-container");
        this.JoiningYear = page.locator("//div[contains(text(), 'Select Year')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
        this.JoiningMonth = page.locator("//div[contains(text(), 'Select Month')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
        this.SkillsUsed = page.locator("//div[@class='css-19bb58m']");
        this.Jobprofile = page.locator("//textarea[@placeholder='Job Profile ...']");
        this.NoticePeriod = page.locator("//div[contains(text(), 'Choose notice period')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
        this.SaveButton = page.locator("button[class='save-resume-btn']");
        this.currentSalary = page.locator("//div[contains(text(), 'Current Salary')]/ancestor::div[contains(@class, 'custom-select')]//div[contains(@class, 'react-select__control')]");
        this.joiningYearoption = page.locator("//div[text()='2022']");
        this.joiningMonthoption= page.locator("(//div[@id='react-select-5-option-0'])[1]");
        this.noticePeriodoption = page.locator("//div[text()='Serving Notice period']");
        this.currentSalaryoption= page.locator("//div[text()='50 k - 1 LPA']");
        this.currentCompanyoption= page.locator("//div[text()='Berkadia']");
        this.currentDesignationoption= page.locator("//div[text()='Sales - Financial Services (Insurance/Unit Trust/O ...']");
        this.skillsOption= page.locator("//div[text()='Automation']");
        this.description = page.locator("textarea[id='name']");
        
    }
    
    public Locator YesRadioBox() {return YesRadioBox;}
    public Locator NoRadioBox() {return NoRadioBox;}
    public Locator FullTimeButton() {return FullTimeButton;}
    public Locator InternshipButton() {return InternshipButton;}
    public Locator CurrentCompanyDropdown() {return CurrentCompanyDropdown;}
    public Locator AddCompanyManually() {return AddCompanyManually;}
    public Locator JoiningYear() {return JoiningYear;}
    public Locator JoiningMonth() {return JoiningMonth;}
    public Locator SkillsUsed() {return SkillsUsed;}
    public Locator NoticePeriod() {return NoticePeriod;}
    public Locator SaveButton() {return SaveButton;}
    public Locator currentSalary() {return currentSalary;}
    public Locator joiningYearoption() {return joiningYearoption;}
    public Locator joiningMonthoption() {return joiningMonthoption;}
    public Locator noticePeriodoption() {return noticePeriodoption;}
    public Locator currentSalaryoption() {return currentSalaryoption;}
    public Locator currentCompanyoption() {return currentCompanyoption;}
    public Locator currentDesignationoption() {return currentDesignationoption;}
    public Locator skillsOption() {return skillsOption;}
    public Locator description() {return description;}
    public Locator Currentdesignatipondropdown() {return Currentdesignatipondropdown;}

    


}
