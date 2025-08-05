package com.promilo.automation.pageobjects.signuplogin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class JobListingPage {

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
    private final Locator jobApplyPopup;
    private final Locator applyNameField;
    private final Locator getHrCall;
    private final Locator getHrCallSubmitButton;
    private final Locator getHrCallThankYouPopup;
    private final Locator getAnHrCallButton;
    public final Locator notifySimilarJobs;
    public final Locator sendSimilarJobs;
    public final Locator invalidOtpToaster;
    public final Locator jobShortList;
    public final Locator jobShortlist1;
    public final Locator askUs;
    public final Locator askUsSubmitButton;
    public final Locator RegisteredWithUsname;
    public final Locator RegisteredWithUsmail;
    public final Locator Rgeisteredwithuslocation;
    public final Locator Registeredwithusindustry;
    public final Locator RegisteredwithusPassword;
    public final Locator Registeredwithusbutton;
    public final Locator RgeisteredwithUsphone;

    public JobListingPage(Page page) {
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
        this.applyNameField = page.locator("//input[@name='userName']");
        this.jobApplyPopup = page.locator("//div[text()='Thank You!']");
        this.getHrCall = page.locator("//button[text()='Get HR Call' and @class='functional_btn-Get-call']");
        this.getHrCallSubmitButton = page.locator("//button[@class='fw-bold w-100 font-16 fw-bold calendar-modal-custom-btn mt-2 btn btn-primary']");
        this.getHrCallThankYouPopup = page.locator("//p[text()='Thank you for requesting an HR Call from Agile Technology. Check your email, notifications, and WhatsApp for details on exclusive access.']");
        this.getAnHrCallButton = page.locator("//button[text()='Get an HR Call']");
        this.notifySimilarJobs = page.locator("//button[text()='Notify Similar Jobs' and @class='functional_btn-Notify-jobs ']");
        this.sendSimilarJobs = page.locator("//button[text()='Send Similar Jobs']");
        this.invalidOtpToaster = page.locator("//div[@role='status' and text()='Invalid OTP.']");
        this.jobShortList = page.locator("//button[@type='button' and text()='Shortlist']");
        this.jobShortlist1 = page.locator("//span[text()='Shortlist']");
        this.askUs = page.locator("//textarea[@id='feedbackDetails']");
        this.askUsSubmitButton = page.locator("//button[@class='submit-btm-askUs btn btn-primary']");
        this.RegisteredWithUsname = page.locator("//input[@id='userName']");
        this.RegisteredWithUsmail = page.locator("//input[@id='userEmail']");
        this.Rgeisteredwithuslocation =page.locator("//input[@id='preferredLocation']");
        this.Registeredwithusindustry = page.locator("//div[@id='industry-dropdown']");
     this.RegisteredwithusPassword= page.locator("//input[@id='password']"); 
     this.Registeredwithusbutton = page.locator("//button[text()='Register Now']"); 
     this.RgeisteredwithUsphone = page.locator("//input[@id='userMobile']");
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
    public Locator applyNameField() { return applyNameField; }
    public Locator jobApplyPopup() { return jobApplyPopup; }
    public Locator getHrCall() { return getHrCall; }
    public Locator getHrCallSubmitButton() { return getHrCallSubmitButton; }
    public Locator getHrCallThankYouPopup() { return getHrCallThankYouPopup; }
    public Locator getAnHrCallButton() { return getAnHrCallButton; }
    public Locator notifySimilarJobs() { return notifySimilarJobs; }
    public Locator sendSimilarJobs() { return sendSimilarJobs; }
    public Locator invalidOtpToaster() { return invalidOtpToaster; }
    public Locator jobShortList() { return jobShortList; }
    public Locator jobShortlist1() { return jobShortlist1; }
    public Locator askUs() { return askUs; }
    public Locator askUsSubmitButton() { return askUsSubmitButton; }
    public Locator RegisteredWithUsname() {return RegisteredWithUsname;}
    public Locator RegisteredWithUsmail() {return RegisteredWithUsmail;}
    public Locator Rgeisteredwithuslocation  () {return Rgeisteredwithuslocation;}
    public Locator Registeredwithusindustry() {return Registeredwithusindustry;}
    public Locator RegisteredwithusPassword() {return RegisteredwithusPassword;}
    public Locator Registeredwithusbutton() {return Registeredwithusbutton;}
    public Locator RgeisteredwithUsphone () {return RgeisteredwithUsphone;}    
}
