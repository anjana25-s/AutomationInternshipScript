package com.promilo.automation.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Homepage {

    private final Page page;

    private final Locator homepageJobs;
    private final Locator searchJob;
    private final Locator b2cIndustry;
    private final Locator fmcgRetail;
    private final Locator bankingAndFinance;
    private final Locator fintech;
    private final Locator healthcare;
    private final Locator hospitality;
    private final Locator fortune500;
    private final Locator internet;
    private final Locator mnc;
    private final Locator startup;
    private final Locator unicorns;
    private final Locator companyType;
    private final Locator experience;
    private final Locator location;
    private final Locator workMode;
    private final Locator salary;
    private final Locator industry;
    private final Locator clearAllButton;
    private final Locator applyNow;
    private final Locator shortlist;
    private final Locator applyNowMobileTextField;
    private final Locator applyNowMail;
    private final Locator applyNowEmailValidation;
    private final Locator applyNowMobileValidation;
    private final Locator selectIndustryDropdown;
    private final Locator selectYourIndustryTextField;
    private final Locator applyNowOtpField;

    public Homepage(Page page) {
        this.page = page;

        this.homepageJobs = page.locator("//a[text()='Jobs']");
        this.searchJob = page.locator("//input[@placeholder='Search Jobs']");
        this.b2cIndustry = page.locator("//img[@src='https://dbk2j665ntt61.cloudfront.net/s3:promilo-assests/fit-in/30x30/jobsv2/B2C.png']");
        this.fmcgRetail = page.locator("//img[@alt='FMCG & Retail']");
        this.bankingAndFinance = page.locator("//img[@alt='Banking & Finance']");
        this.fintech = page.locator("//img[@alt='Fintech']");
        this.healthcare = page.locator("//img[@alt='Healthcare']");
        this.hospitality = page.locator("//img[@src='https://dbk2j665ntt61.cloudfront.net/s3:promilo-assests/fit-in/30x30/jobsv2/Hospitality.png']");
        this.fortune500 = page.locator("//img[@alt='Fortune500']");
        this.internet = page.locator("//img[@alt='Internet']");
        this.mnc = page.locator("//img[@src='https://dbk2j665ntt61.cloudfront.net/s3:promilo-assests/fit-in/30x30/jobsv2/MNC_s.png']");
        this.startup = page.locator("//img[@alt='Startups']");
        this.unicorns = page.locator("//img[@alt='Unicorns']");
        this.companyType = page.locator("//p[text()='Company Type']");
        this.experience = page.locator("//p[text()='Experience']");
        this.location = page.locator("//p[text()='Location']");
        this.workMode = page.locator("//p[text()='Work Mode']");
        this.salary = page.locator("//p[text()='Salary']");
        this.industry = page.locator("//p[text()='Industry']");
        this.clearAllButton = page.locator("//div[text()='Clear all']");
        this.applyNow = page.locator("//button[text()='Apply Now']");
        this.shortlist = page.locator("//img[@alt='Bookmark']");
        this.applyNowMobileTextField = page.locator("//input[@name='userMobile']");
        this.applyNowMail = page.locator("//input[@name='userEmail' and @id='userEmail' and @class='grey-placeholder form-control']");
        this.applyNowEmailValidation = page.locator("//div[text()='Email is required']");
        this.applyNowMobileValidation = page.locator("//div[text()='Mobile number is required']");
        this.selectIndustryDropdown = page.locator("//div[text()='Select your Industry*']");
        this.selectYourIndustryTextField = page.locator("//input[@placeholder='Search your Industry']");
        this.applyNowOtpField = page.locator("//input[@aria-label='Please enter OTP character 1']");
    }

    // --- Public getters ---

    public Locator homepageJobs() { return homepageJobs; }
    public Locator searchJob() { return searchJob; }
    public Locator b2cIndustry() { return b2cIndustry; }
    public Locator fmcgRetail() { return fmcgRetail; }
    public Locator bankingAndFinance() { return bankingAndFinance; }
    public Locator fintech() { return fintech; }
    public Locator healthcare() { return healthcare; }
    public Locator hospitality() { return hospitality; }
    public Locator fortune500() { return fortune500; }
    public Locator internet() { return internet; }
    public Locator mnc() { return mnc; }
    public Locator startup() { return startup; }
    public Locator unicorns() { return unicorns; }
    public Locator companyType() { return companyType; }
    public Locator experience() { return experience; }
    public Locator location() { return location; }
    public Locator workMode() { return workMode; }
    public Locator salary() { return salary; }
    public Locator industry() { return industry; }
    public Locator clearAllButton() { return clearAllButton; }
    public Locator applyNow() { return applyNow; }
    public Locator shortlist() { return shortlist; }
    public Locator applyNowMobileTextField() { return applyNowMobileTextField; }
    public Locator applyNowMail() { return applyNowMail; }
    public Locator applyNowEmailValidation() { return applyNowEmailValidation; }
    public Locator applyNowMobileValidation() { return applyNowMobileValidation; }
    public Locator selectIndustryDropdown() { return selectIndustryDropdown; }
    public Locator selectYourIndustryTextField() { return selectYourIndustryTextField; }
    public Locator applyNowOtpField() { return applyNowOtpField; }
}
