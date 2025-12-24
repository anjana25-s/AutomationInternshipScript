package com.promilo.automation.advertiser.mybilling;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Billing {

    private Locator Billing;
    private Locator AddFunds;
    private Locator AllFilter;
    private Locator StartDate;
    private Locator EndDate;
    private Locator SearchField;
    private Locator previewIcon;
    private Locator sendMail;
    private Locator download;
    private Locator postpaidBalance;
    private Locator CustomButton;
    private Locator pagination;
    private Locator InvoiceHeading;
    private Locator For;
    private Locator client;
    private Locator amountHeading;
    private Locator debitCredit;
    private Locator TransactionDate;
    private Locator PaymentMethod;
    private Locator Status;
    private Locator Action;
    private Locator buisinessName;
    private Locator adress1;
    private Locator adress2;
    private Locator pinCode;
    private Locator yesOption;
    private Locator noOption;
    private Locator gstNumber;
    private Locator panNumber;
    private Locator checkBox;
    private Locator saveButton;

    // Add funds pop-up
    private Locator amountTextfield;
    private Locator payButton;
    private Locator exitIcon;

    private Page page;

    public Billing(Page page) {
        this.page = page;
        Billing = page.locator("//span[text()='My Billing']");
        AddFunds = page.locator("//button[@class='font-16 billingAndPaymentAddFund btn btn-primary']");
        AllFilter = page.locator("//div[text()='All ']");
        StartDate = page.locator("//input[@placeholder='Start date']");
        EndDate = page.locator("input[placeholder='End date']");
        SearchField = page.locator("//input[@placeholder='Search by Invoice No. & Name']");
        previewIcon = page.locator("[class='mx-50 pointer me-2']");
        sendMail = page.locator("div[class='me-2 pt-25 pointer']");
        download = page.locator("svg[class='pointer text-black']");
        postpaidBalance = page.locator("[class='me-md-1 me-xxl-1']");
        CustomButton = page.locator("//div[text()='Custom']");
        pagination = page.locator("//button[@class='dropdown-toggle show btn btn-primary']");
        amountTextfield = page.locator("input[class='amount-input form-control']");
        payButton = page.locator("//button[text()='Pay']");
        exitIcon = page.locator("svg[class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium css-t85znn-MuiSvgIcon-root']");
        InvoiceHeading = page.locator("//th[text()='Invoice No']");
        For = page.locator("//th[text()='For']");
        client = page.locator("//th[text()='Client']");
        amountHeading = page.locator("//th[text()='Amount']");
        debitCredit = page.locator("//th[text()='Debit/Credit']");
        TransactionDate = page.locator("//th[text()='Transaction Date']");
        PaymentMethod = page.locator("//th[text()='Payment Method']");
        Status = page.locator("//th[text()='Status']");
        Action = page.locator("//th[text()='Action']");
        buisinessName= page.locator("input[name='organisationName']");
        adress1= page.locator("//input[@name='addressLine1']");
        adress2= page.locator("//input[@name='addressLine2']");
        pinCode= page.locator("//input[@placeholder='Enter Pincode']");
        yesOption= page.locator("//label[text()='Yes, I am buying ads for business purposes']");
        noOption= page.locator("//label[text()='No, I am not buying ads for business purposes']");
        gstNumber= page.locator("input[name='gstNumber']");
        panNumber= page.locator("input[name='panNumber']");
        checkBox= page.locator("input[id='flexCheckDefault']");
        saveButton= page.locator("//button[text()='Save']");
        
    }

    // Getters
    public Locator Billing() { return Billing; }
    public Locator AddFunds() { return AddFunds; }
    public Locator AllFilter() { return AllFilter; }
    public Locator StartDate() { return StartDate; }
    public Locator EndDate() { return EndDate; }
    public Locator SearchField() { return SearchField; }
    public Locator previewIcon() { return previewIcon; }
    public Locator sendMail() { return sendMail; }
    public Locator download() { return download; }
    public Locator postpaidBalance() { return postpaidBalance; }
    public Locator CustomButton() { return CustomButton; }
    public Locator pagination() { return pagination; }
    public Locator amountTextfield() { return amountTextfield; }
    public Locator payButton() { return payButton; }
    public Locator exitIcon() { return exitIcon; }
    public Locator InvoiceHeading() { return InvoiceHeading; }
    public Locator For() { return For; }
    public Locator client() { return client; }
    public Locator amountHeading() { return amountHeading; }
    public Locator debitCredit() { return debitCredit; }
    public Locator TransactionDate() { return TransactionDate; }
    public Locator PaymentMethod() { return PaymentMethod; }
    public Locator Status() { return Status; }
    public Locator Action() { return Action; }
    public Locator buisinessName() {return buisinessName;}
    public Locator adress1() {return adress1;}
    public Locator adress2() {return adress2;}
    public Locator yesOption() {return yesOption;}
    public Locator noOption() {return noOption;}
    public Locator gstNumber() {return gstNumber;}
    public Locator panNumber() {return panNumber;}
    public Locator checkBox() {return checkBox;}
    public Locator saveButton() {return saveButton;}
    public Locator pinCode() {return pinCode;}
}
