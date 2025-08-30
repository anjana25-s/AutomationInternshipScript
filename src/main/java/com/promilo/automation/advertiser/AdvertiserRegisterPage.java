package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserRegisterPage {

    private final Page page;

    private final Locator firstnameInput;
    private final Locator lastnameInput;
    private final Locator JobTittle;
    private final Locator typeofBuisiness;
    private final Locator mailTextfield;
    private final Locator phoneNumbertextfield;
    private final Locator SendotpButton;
    private final Locator Companywebsite;
    private final Locator otpfield;
    private final Locator companyName;
    private final Locator IndustryDropdown;
    private final Locator EmployeesDropdown;
    private final Locator Pincode;
    private final Locator CreatePassword;
    private final Locator Submitbutton;
    private final Locator ReferralField;
    private final Locator LoginButton;
    private final Locator TermsAndconditionsText;
    private final Locator SignupHeading;
    private final Locator promiloCustomerReach;
    private final Locator NocreditcardRequired;
    private final Locator TalktoanExpert;
    
    
    public AdvertiserRegisterPage(Page page) {
        this.page = page;

        this.firstnameInput = page.locator("//input[@placeholder='First Name*']");
        this.lastnameInput = page.locator("//input[@placeholder='Last Name*']");
        this.JobTittle = page.locator("#react-select-2-input");
        this.typeofBuisiness = page.locator("#react-select-3-input");
        this.mailTextfield = page.locator("//input[@placeholder='Email ID*']");
        this.phoneNumbertextfield = page.locator("input[placeholder='Phone Number*']");
        this.SendotpButton = page.locator("//span[text()='Send OTP']");
        this.Companywebsite = page.locator("//input[@placeholder='Company Website*']");
        this.otpfield = page.locator("input[placeholder='Enter OTP sent to your number']");
        this.companyName = page.locator("#free-solo-demo");
        this.IndustryDropdown= page.locator("#react-select-4-input");
        this.EmployeesDropdown= page.locator("#react-select-5-input");
        this.Pincode = page.locator("input[placeholder='Pincode*']");
        this.CreatePassword = page.locator("input[placeholder='Create Password*']");
        this.Submitbutton = page.locator("//p[text()='Submit']");
        this.ReferralField= page.locator("input[placeholder='Referral Code']");
        this.LoginButton= page.locator("//a[text()='Login']");
        this.TermsAndconditionsText= page.locator("[for='remember-me']");
        this.SignupHeading= page.locator("div[class='Signup_heading p-4 col']");
        this.promiloCustomerReach= page.locator("div[class=' container  lg-4 sm-4 md-4 mt-5']");
        this.NocreditcardRequired= page.locator("//p[text()='Get Started Free, No Credit Card Required']");
        this.TalktoanExpert = page.locator("p[class='mt-2']");
        
        
        
        
    }

    public Locator FirstnameInput() {return firstnameInput;}
    public Locator LastnameInput() {return lastnameInput;}
    public Locator JobTittle() {return JobTittle;}
    public Locator typeofBuisiness() {return typeofBuisiness;}
    public Locator phoneNumbertextfield() {return phoneNumbertextfield;}
    public Locator SendotpButton() {return SendotpButton; }
    public Locator otpfield() {return otpfield;}
    public Locator companyName() {return companyName;}
    public Locator IndustryDropdown() {return IndustryDropdown;}
    public Locator EmployeesDropdown() {return EmployeesDropdown;}
    public Locator Pincode() {return Pincode;}
    public Locator CreatePassword() {return CreatePassword;}
    public Locator Submitbutton() {return Submitbutton;}
    public Locator ReferralField() {return ReferralField;}
    public Locator LoginButton() {return LoginButton;}
    public Locator TermsAndconditionsText() {return TermsAndconditionsText;}
    public Locator SignupHeading() {return SignupHeading;}
    public Locator promiloCustomerReach() {return promiloCustomerReach;}
    public Locator NocreditcardRequired() {return NocreditcardRequired;}
    public Locator TalktoanExpert() {return TalktoanExpert;}
    public Locator mailTextfield() {return mailTextfield;}
    public Locator Companywebsite() {return Companywebsite;}
    
    
}
