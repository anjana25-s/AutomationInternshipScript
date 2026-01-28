package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CoursesFilterPage {

    private final Page page;

    private final Locator coursesTab;
    private final Locator locationFilterButton;
    private final Locator streamFilterButton;
    private final Locator courseLevelFilterButton;
    private final Locator ahmedabadLocationOption;
    private final Locator engineeringStreamOption;
    private final Locator beBtechOption;
    private final Locator locationAssertion;
    private final Locator streamAssertion;
    private final Locator durationButton;
    private final Locator durationOption;
    private final Locator durationAssertion;
    private final Locator noResultsFound;
    private final Locator feeRange;
    private final Locator feeOptions;
    private final Locator feeAssertion;
    private final Locator modeOfStudy;
    private final Locator modeOfStudyOptions;
    private final Locator modeOfStudyAssertion;

    public CoursesFilterPage(Page page) {
        this.page = page;

        this.coursesTab = page.locator("//a[text()='Courses']");

        this.locationFilterButton = page.locator("[class='font-16  p-0 m-0 pointer']").first();
        this.streamFilterButton = page.locator("//p[text()='Stream']").first();
        this.courseLevelFilterButton = page.locator("//p[text()='Course Level']");

        this.ahmedabadLocationOption = page.locator("//span[text()='Ahmedabad']");
        this.engineeringStreamOption = page.locator("//span[text()='Engineering']").first();
        this.beBtechOption = page.locator("//span[text()='B.E. / B.Tech']").first();
        this.locationAssertion= page.locator("[class='sub-title-text text-black font-10 mb-0 pointer truncate w-full w-100']");
        this.streamAssertion= page.locator("[class='sub-title-text text-grey font-10 mb-0 pointer truncate w-full w-100']");
        this.durationOption= page.locator("[class='font-14 text-primary label-text pointer text-align-start-filter']");
        this.durationButton= page.locator("[class='font-16  p-0 m-0 pointer']").nth(3);
        this.durationAssertion= page.locator("[class='w-100 d-flex font-11 fw-medium text-dark-grey']").nth(1);
        this.noResultsFound= page.locator("[class='pt-2 pt-md-0']");
        this.feeRange= page.locator("[class='font-16  p-0 m-0 pointer']").nth(5);
        this.feeOptions= page.locator("[class='font-14 text-primary label-text pointer text-align-start-filter']");
        this.feeAssertion= page.locator("[class='w-100 d-flex font-11 fw-medium text-dark-grey']");
        this.modeOfStudy= page.locator("[class='font-16  p-0 m-0 pointer']").nth(4);
        this.modeOfStudyOptions= page.locator("[class='font-14 text-primary label-text pointer text-align-start-filter']");
        this.modeOfStudyAssertion= page.locator("[class='swiper-card-title truncate w-full mb-0 leading-tight']");
        
    }

    public Locator coursesTab() { return coursesTab; }
    public Locator locationFilterButton() { return locationFilterButton; }
    public Locator streamFilterButton() { return streamFilterButton; }
    public Locator courseLevelFilterButton() { return courseLevelFilterButton; }
    public Locator ahmedabadLocationOption() { return ahmedabadLocationOption; }
    public Locator engineeringStreamOption() { return engineeringStreamOption; }
    public Locator beBtechOption() { return beBtechOption; }
    public Locator locationAssertion() {return locationAssertion;}
    public Locator streamAssertion() {return streamAssertion;}
    public Locator durationButton () {return durationButton;}
    public Locator durationOption() {return durationOption;}
    public Locator durationAssertion() {return durationAssertion;}
    public Locator noResultsFound() {return noResultsFound;} 
    public Locator feeRange() {return feeRange;}
    public Locator feeOptions() {return feeOptions;}
    public Locator feeAssertion() {return feeAssertion;}
    public Locator modeOfStudy() {return modeOfStudy;}
    public Locator modeOfStudyOptions() {return modeOfStudyOptions;}
    public Locator modeOfStudyAssertion() {return modeOfStudyAssertion;}
}
