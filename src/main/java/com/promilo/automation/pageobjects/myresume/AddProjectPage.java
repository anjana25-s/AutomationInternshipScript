package com.promilo.automation.pageobjects.myresume;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddProjectPage {

    private final Page page;

    private final Locator ProjectTitle;
    private final Locator EducationOrEmploymentDropdown;
    private final Locator ClientTextField;
    private final Locator InProgressStatus;
    private final Locator FinishedStatus;
    private final Locator WorkedFromYear;
    private final Locator WorkedFromMonth;
    private final Locator WorkedTillYear;
    private final Locator WorkedTillMonth;
    private final Locator ProjectDescription;
    private final Locator AddMoreDetailsButton;
    private final Locator TeamSizeInput;
    private final Locator ProjectLocationInput;
    private final Locator OffsiteRadio;
    private final Locator OnsiteRadio;
    private final Locator FullTimeRadio;
    private final Locator PartTimeRadio;
    private final Locator ContractualRadio;
    private final Locator RoleInput;
    private final Locator RoleDescription;
    private final Locator SkillsUsedTextarea;
    private final Locator SaveButton;

    public AddProjectPage(Page page) {
        this.page = page;

        this.ProjectTitle = page.locator("//input[@id='projectTitle']");
        this.EducationOrEmploymentDropdown = page.locator("//input[@id='react-select-80-input']");
        this.ClientTextField = page.locator("//input[@id='client']");
        this.InProgressStatus = page.locator("//input[@id='inProgress']");
        this.FinishedStatus = page.locator("//input[@id='finished']");
        this.WorkedFromYear = page.locator("(//div[contains(@class, 'react-select__input-container')]//input[contains(@class, 'react-select__input')])[position()=2]");
        this.WorkedFromMonth = page.locator("(//div[contains(@class, 'react-select__input-container')]//input[contains(@class, 'react-select__input')])[position()=3]");
        this.WorkedTillYear = page.locator("//input[@id='react-select-87-input']");
        this.WorkedTillMonth = page.locator("//input[@id='react-select-88-input']");
        this.ProjectDescription = page.locator("//textarea[@id='Description']");
        this.AddMoreDetailsButton = page.locator("//span[text()='Add More Details']");
        this.TeamSizeInput = page.locator("//input[@id='react-select-8-input']");
        this.ProjectLocationInput = page.locator("//input[@id='react-select-7-input']");
        this.OffsiteRadio = page.locator("//input[@id='offsite']");
        this.OnsiteRadio = page.locator("//input[@id='Onsite']");
        this.FullTimeRadio = page.locator("//input[@id='fulltime']");
        this.PartTimeRadio = page.locator("//input[@id='partime']");
        this.ContractualRadio = page.locator("//input[@id='contractual']");
        this.RoleInput = page.locator("//input[@id='react-select-9-input']");
        this.RoleDescription = page.locator("//body//div//div[@class='px-0 modal-body']//div//div//div[6]//div[1]//textarea[1]");
        this.SkillsUsedTextarea = page.locator("//textarea[@placeholder='Skill Used ...']");
        this.SaveButton = page.locator("//button[@class='save-resume-btn']");
    }

    public Locator ProjectTitle() { return ProjectTitle; }
    public Locator EducationOrEmploymentDropdown() { return EducationOrEmploymentDropdown; }
    public Locator ClientTextField() { return ClientTextField; }
    public Locator InProgressStatus() { return InProgressStatus; }
    public Locator FinishedStatus() { return FinishedStatus; }
    public Locator WorkedFromYear() { return WorkedFromYear; }
    public Locator WorkedFromMonth() { return WorkedFromMonth; }
    public Locator WorkedTillYear() { return WorkedTillYear; }
    public Locator WorkedTillMonth() { return WorkedTillMonth; }
    public Locator ProjectDescription() { return ProjectDescription; }
    public Locator AddMoreDetailsButton() { return AddMoreDetailsButton; }
    public Locator TeamSizeInput() { return TeamSizeInput; }
    public Locator ProjectLocationInput() { return ProjectLocationInput; }
    public Locator OffsiteRadio() { return OffsiteRadio; }
    public Locator OnsiteRadio() { return OnsiteRadio; }
    public Locator FullTimeRadio() { return FullTimeRadio; }
    public Locator PartTimeRadio() { return PartTimeRadio; }
    public Locator ContractualRadio() { return ContractualRadio; }
    public Locator RoleInput() { return RoleInput; }
    public Locator RoleDescription() { return RoleDescription; }
    public Locator SkillsUsedTextarea() { return SkillsUsedTextarea; }
    public Locator SaveButton() { return SaveButton; }
}