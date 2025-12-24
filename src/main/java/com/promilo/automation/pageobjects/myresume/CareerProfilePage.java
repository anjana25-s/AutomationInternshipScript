package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CareerProfilePage {

    private final Page page;

    private final Locator careerProfileDescriptionValidation;
    private final Locator careerProfileCurrentIndustryDropdown;
    private final Locator careerProfileDepartmentDropdown;
    private final Locator careerProfileJobRoleDropdown;
    private final Locator careerProfilePermanentCheckbox;
    private final Locator careerProfileContractualCheckbox;
    private final Locator careerProfileFullTimeCheckbox;
    private final Locator careerProfilePartTimeCheckbox;
    private final Locator careerProfileShiftPreferredDay;
    private final Locator careerProfileShiftPreferredNight;
    private final Locator careerProfileShiftPreferredFlexible;
    private final Locator careerProfilePreferredLocationsDropdown;
    private final Locator careerProfileSelectSalaryDropdown;
    private final Locator careerProfileSaveButton;

    public CareerProfilePage(Page page) {
        this.page = page;

        this.careerProfileDescriptionValidation = page.locator("//p[@class='font-14 grey-text']");
        this.careerProfileCurrentIndustryDropdown = page.locator("#react-select-3-input");
        this.careerProfileDepartmentDropdown = page.locator("(//div[contains(@class, 'react-select__input-container')]/input[@class='react-select__input'])[2]");
        this.careerProfileJobRoleDropdown = page.locator("(//div[contains(@class, 'react-select__input-container')]/input[@class='react-select__input'])[3]");
        this.careerProfilePermanentCheckbox = page.locator("//input[@type='checkbox' and @value='Permanent']");
        this.careerProfileContractualCheckbox = page.locator("//label[text()='Contractual']");
        this.careerProfileFullTimeCheckbox = page.locator("//input[@type='checkbox' and @value='fulltime']");
        this.careerProfilePartTimeCheckbox = page.locator("//label[text()='Part Time']");
        this.careerProfileShiftPreferredDay = page.locator("//input[@id='Day']");
        this.careerProfileShiftPreferredNight = page.locator("//input[@id='Night']");
        this.careerProfileShiftPreferredFlexible = page.locator("//input[@id='Flexible' and @name='choose']");
        this.careerProfilePreferredLocationsDropdown = page.locator("//div[@class='preferred_location__value-container preferred_location__value-container--is-multi css-hlgwow']");
        this.careerProfileSelectSalaryDropdown = page.locator("(//div[contains(@class, 'react-select__input-container')]/input[@class='react-select__input'])[4]");
        this.careerProfileSaveButton = page.locator("//button[@type='submit']");
    }

    public Locator careerProfileDescriptionValidation() { return careerProfileDescriptionValidation; }
    public Locator careerProfileCurrentIndustryDropdown() { return careerProfileCurrentIndustryDropdown; }
    public Locator careerProfileDepartmentDropdown() { return careerProfileDepartmentDropdown; }
    public Locator careerProfileJobRoleDropdown() { return careerProfileJobRoleDropdown; }
    public Locator careerProfilePermanentCheckbox() { return careerProfilePermanentCheckbox; }
    public Locator careerProfileContractualCheckbox() { return careerProfileContractualCheckbox; }
    public Locator careerProfileFullTimeCheckbox() { return careerProfileFullTimeCheckbox; }
    public Locator careerProfilePartTimeCheckbox() { return careerProfilePartTimeCheckbox; }
    public Locator careerProfileShiftPreferredDay() { return careerProfileShiftPreferredDay; }
    public Locator careerProfileShiftPreferredNight() { return careerProfileShiftPreferredNight; }
    public Locator careerProfileShiftPreferredFlexible() { return careerProfileShiftPreferredFlexible; }
    public Locator careerProfilePreferredLocationsDropdown() { return careerProfilePreferredLocationsDropdown; }
    public Locator careerProfileSelectSalaryDropdown() { return careerProfileSelectSalaryDropdown; }
    public Locator careerProfileSaveButton() { return careerProfileSaveButton; }
}
