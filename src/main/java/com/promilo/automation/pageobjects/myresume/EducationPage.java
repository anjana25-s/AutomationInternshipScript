package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EducationPage {

    private final Page page;

    private Locator UniversityDropdown;
    private Locator UniversityManualTextField;
    private Locator UniversityAddButton;
    private Locator EducationLevelDropdown;
    private Locator CourseDropdown;
    private Locator SpecializationDropdown;
    private Locator FullTimeRadio;
    private Locator PartTimeRadio;
    private Locator DistanceLearningRadio;
    private Locator CourseStartMonthDropdown;
    private Locator CourseStartYearDropdown;
    private Locator CourseEndMonthInput;
    private Locator CourseEndYearDropdown;
    private Locator GradeSystemDropdown;
    private Locator MarksTextField;
    private Locator SaveButton;

    public EducationPage(Page page) {
        this.page = page;

        this.UniversityDropdown = page.locator("//div[text()='Search or select university/institute']");
        this.UniversityManualTextField = page.locator("//input[@placeholder='Add your university/institute']");
        this.UniversityAddButton = page.locator("//button[@class='add-skills-btn add-company-name btn btn-primary']");
        this.EducationLevelDropdown = page.locator("//div[text()='Search or select education level']");
        this.CourseDropdown = page.locator("//div[text()='Search or select course']");
        this.SpecializationDropdown = page.locator("//div[text()='Search or select specialization']");
        this.FullTimeRadio = page.locator("//input[@id='FULL_TIME']");
        this.PartTimeRadio = page.locator("//input[@id='PART_TIME']");
        this.DistanceLearningRadio = page.locator("//input[@id='DISTANCE_LEARNING']");
        this.CourseStartMonthDropdown = page.locator("//div[text()='Select Month' and @id='react-select-75-placeholder']");
        this.CourseStartYearDropdown = page.locator("//div[text()='Select Year' and @id='react-select-76-placeholder']");
        this.CourseEndMonthInput = page.locator("//input[@id='react-select-77-input']");
        this.CourseEndYearDropdown = page.locator("//div[@id='react-select-78-placeholder']");
        this.GradeSystemDropdown = page.locator("//div[text()='Select grade system']");
        this.MarksTextField = page.locator("//input[@name='marks']");
        this.SaveButton = page.locator("//button[@class='save-resume-btn']");
    }

    public Locator UniversityDropdown() { return UniversityDropdown; }
    public Locator UniversityManualTextField() { return UniversityManualTextField; }
    public Locator UniversityAddButton() { return UniversityAddButton; }
    public Locator EducationLevelDropdown() { return EducationLevelDropdown; }
    public Locator CourseDropdown() { return CourseDropdown; }
    public Locator SpecializationDropdown() { return SpecializationDropdown; }
    public Locator FullTimeRadio() { return FullTimeRadio; }
    public Locator PartTimeRadio() { return PartTimeRadio; }
    public Locator DistanceLearningRadio() { return DistanceLearningRadio; }
    public Locator CourseStartMonthDropdown() { return CourseStartMonthDropdown; }
    public Locator CourseStartYearDropdown() { return CourseStartYearDropdown; }
    public Locator CourseEndMonthInput() { return CourseEndMonthInput; }
    public Locator CourseEndYearDropdown() { return CourseEndYearDropdown; }
    public Locator GradeSystemDropdown() { return GradeSystemDropdown; }
    public Locator MarksTextField() { return MarksTextField; }
    public Locator SaveButton() { return SaveButton; }
}