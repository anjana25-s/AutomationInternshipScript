package com.promilo.automation.mentor.mybilling;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MentorMyBilling  {

    private final Page page;

    // Top section locators
    private final Locator myBillingButton;
    private final Locator paymentSetting;
    private final Locator nameAndInvoiceSearchField;
    private final Locator searchIcon;
    private final Locator addFunds;
    private final Locator editButton;
    private final Locator addButton;

    // Bank account locators
    private final Locator accountHolderName;
    private final Locator accountNumber;
    private final Locator ifscCode;
    private final Locator branchName;
    private final Locator panNumber;
    private final Locator billingCountry;
    private final Locator saveButton;

    // Invoice / Business info locators
    private final Locator businessName;
    private final Locator address1;
    private final Locator address2;
    private final Locator pinCode;
    private final Locator yesOption;
    private final Locator noOption;
    private final Locator gstNumber;
    private final Locator invoicePanNumber;
    private final Locator checkBox;
    private final Locator invoiceSaveButton;
    
    
    //Report a problem
    private final Locator reportProblem;
    private final Locator accountSetting;
    private final Locator billingOption;
    private final Locator creatingAd;
    private final Locator editingAd;
    private final Locator buisinessMail;
    private final Locator uploadButton;
    private final Locator sendRequest;
    private final Locator reportProblemOption;
    private final Locator askQuestionOption;
    private final Locator amountField;
    private final Locator payButton;
    private final Locator prepaidBalance;
    private final Locator startDate;
    private final Locator endDate;
    private final Locator enterStartDate;
    private final Locator enterEndDate;
    private final Locator lastSevenDays;
    private final Locator lastfourteenDays;
    private final Locator lastthirtyDays;
    private final Locator lastnintyDays;
    private final Locator billingtableHead;
    private final Locator suspenseBalance;
    private final Locator mybillingPrepaidBalance;
    private final Locator reportDownload;
    private final Locator mailIcon;
    private final Locator mailIconn;

	private final Locator billingData;

    public MentorMyBilling(Page page) {
        this.page = page;

        // Top section
        this.paymentSetting = page.locator("//button[text()='Payment Settings']");
        this.nameAndInvoiceSearchField = page.locator("//input[@placeholder='Search by Invoice No. & Name ']");
        this.searchIcon = page.locator("//img[@alt='Search']");
        this.addFunds = page.locator("//button[text()='Add Funds']");
        this.editButton = page.locator("//button[text()='Edit']");
        this.addButton = page.locator("//button[text()='Add']");

        // Bank account
        this.accountHolderName = page.locator("//input[@placeholder='Enter account holder name']");
        this.accountNumber = page.locator("//input[@placeholder='Enter account number']");
        this.ifscCode = page.locator("//input[@placeholder='Enter IFSC Code']");
        this.branchName = page.locator("//input[@placeholder='Enter branch name']");
        this.panNumber = page.locator("//input[@placeholder='Enter PAN Number']");
        this.billingCountry = page.locator("//div[@class='react-select__input-container css-19bb58m']");
        this.saveButton = page.locator("//button[normalize-space()='Save']");

        // Invoice / Business info
        this.businessName = page.locator("//input[@name='businessInfoOrganisationName']");
        this.address1 = page.locator("//input[@name='addressLine1']");
        this.address2 = page.locator("//input[@name='addressLine2']");
        this.pinCode = page.locator("//input[@placeholder='Enter Pincode']");
        this.yesOption = page.locator("//label[text()='Yes, I am buying ads for business purposes']");
        this.noOption = page.locator("//label[text()='No, I am not buying ads for business purposes']");
        this.gstNumber = page.locator("input[name='gstNumber']");
        this.invoicePanNumber = page.locator("input[name='panNumber']");
        this.checkBox = page.locator("input[id='flexCheckDefault']");
        this.invoiceSaveButton = page.locator("//button[text()='Save']");
        
        
        //Report a Problem
        this.reportProblem= page.locator("//div[@class='report-select__input-container css-19bb58m']");
        this.accountSetting= page.locator("//div[text()='Account Setting ']");
        this.billingOption= page.locator("//div[text()='Billing']");    
        this.creatingAd= page.locator("//div[text()='Creating an ad']");
        this.editingAd= page.locator("//div[text()='Editing an ad']");     
        this.buisinessMail= page.locator("//input[@type='text']");
        this.uploadButton= page.locator("//input[@id='uploadFile']");
        this.sendRequest= page.locator("//button[text()='Send Request']");
        this.reportProblemOption= page.locator("//div[text()='Report a Problem']");
        this.askQuestionOption= page.locator("//div[text()='Ask a Question']");
        this.amountField= page.locator("//input[@class='amount-input form-control']");
        this.payButton= page.locator("//button[text()='Pay']");
        this.prepaidBalance= page.locator("//div[text()='₹ ']");
        this.startDate= page.locator("//input[@placeholder='Start date']");
        this.endDate= page.locator("//input[@placeholder='End date']");
        this.enterStartDate= page.locator("(//div[@class='ant-picker-cell-inner'][normalize-space()='9'])[1]");
        this.enterEndDate= page.locator("//div[@class='ant-picker-cell-inner'][normalize-space()='11'])[1]");
        this.lastSevenDays= page.locator("//li[normalize-space()='Last 7 Days']");
        this.lastfourteenDays= page.locator("//li[normalize-space()='Last 14 Days']");
        this.lastthirtyDays= page.locator("//li[normalize-space()='Last 30 Days']");
        this.lastnintyDays= page.locator("//li[normalize-space()='Last 90 Days']");
        this.billingtableHead= page.locator("(//thead)[1]");
        this.billingData= page.locator("(//tr)[2]");
        this.suspenseBalance= page.locator("//label[text()='Suspense Balance']");
        this.mybillingPrepaidBalance= page.locator("//label[text()='Prepaid Balance']");
        this.reportDownload= page.locator("svg[class='pointer text-black']");
        this.mailIcon= page.locator("svg[class='mx-50 pointer me-2']");
        this.mailIconn= page.locator("img[alt='icon']");
        this.myBillingButton= page.locator("//a[text()='My Billing']");
        
        		
        		
        		
    }

    // Top section getters
    public Locator paymentSetting() { return paymentSetting; }
    public Locator nameAndInvoiceSearchField() { return nameAndInvoiceSearchField; }
    public Locator searchIcon() { return searchIcon; }
    public Locator addFunds() { return addFunds; }
    public Locator editButton() { return editButton; }
    public Locator addButton() { return addButton; }

    // Bank account getters
    public Locator accountHolderName() { return accountHolderName; }
    public Locator accountNumber() { return accountNumber; }
    public Locator ifscCode() { return ifscCode; }
    public Locator branchName() { return branchName; }
    public Locator panNumber() { return panNumber; }
    public Locator billingCountry() { return billingCountry; }
    public Locator saveButton() { return saveButton; }

    // Invoice / Business info getters
    public Locator businessName() { return businessName; }
    public Locator address1() { return address1; }
    public Locator address2() { return address2; }
    public Locator pinCode() { return pinCode; }
    public Locator yesOption() { return yesOption; }
    public Locator noOption() { return noOption; }
    public Locator gstNumber() { return gstNumber; }
    public Locator invoicePanNumber() { return invoicePanNumber; }
    public Locator checkBox() { return checkBox; }
    public Locator invoiceSaveButton() { return invoiceSaveButton; }
    
    //Report a Problem 
    public Locator reportProblem() {return reportProblem;}
    public Locator accountSetting() {return accountSetting;}
    public Locator billingOption() {return billingOption;}
    public Locator creatingAd() {return creatingAd;}
    public Locator editingAd() {return editingAd;}
    public Locator buisinessMail() {return buisinessMail;}
    public Locator uploadButton() {return uploadButton;}
    public Locator sendRequest() {return sendRequest;}
    public Locator reportProblemOption() {return reportProblemOption;}
    public Locator askQuestionOption() {return askQuestionOption;}
    public Locator amountField() {return amountField;}
    public Locator payButton() {return payButton;}
    public Locator prepaidBalance() {return prepaidBalance;}
    public Locator startDate() {return startDate;}
    public Locator endDate() {return endDate;}
    public Locator enterStartDate() {return enterStartDate;}
    public Locator enterEndDate() {return enterEndDate;}
    public Locator lastSevenDays() {return lastSevenDays;}
    public Locator lastfourteenDays() {return lastfourteenDays;}
    public Locator lastthirtyDays() {return lastthirtyDays;}
    public Locator lastnintyDays() {return lastnintyDays;}
    public Locator billingtableHead() {return billingtableHead;}
    public Locator billingData() {return billingData;}
    public Locator suspenseBalance() {return suspenseBalance;}
    public Locator mybillingPrepaidBalance() {return mybillingPrepaidBalance;}
    public Locator reportDownload() {return reportDownload;}
    public Locator mailIcon() {return mailIcon;}
    public Locator mailIconn() {return mailIconn;}
    public Locator myBillingButton() {return myBillingButton;}
}
