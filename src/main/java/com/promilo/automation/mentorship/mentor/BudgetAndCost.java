package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BudgetAndCost {

	private final Page page;
	private final Locator emailCheckbox;
	private final Locator smsCheckbox;
	private final Locator notificationCheckboc;
	private final Locator whatsappCheckbox;
	private final Locator addbankAccount;
	private final Locator addInvoiceinfo;

	// add bank account
	private final Locator accountHoldername;
	private final Locator accountNumber;
	private final Locator ifscCode;
	private final Locator branchName;
	private final Locator panNumber;
	private final Locator billingCountry;
	private final Locator saveButton;

	// add

	public BudgetAndCost(Page page) {

		this.page = page;
		this.emailCheckbox = page.locator("//label[@for='email-checkbox']");
		this.smsCheckbox = page.locator("//label[normalize-space()='SMS']");
		this.notificationCheckboc = page.locator("//label[normalize-space()='Notification']");
		this.whatsappCheckbox = page.locator("//label[normalize-space()='Whatsapp']");
		this.addbankAccount = page.locator("//span[@class='d-flex flex-nowrap']");
		this.addInvoiceinfo = page.locator("//button[normalize-space()='Add Invoice Info']");

		this.accountHoldername = page.locator("//input[@placeholder='Enter account holder name']");
		this.accountNumber = page.locator("//input[@placeholder='Enter account number']");
		this.ifscCode = page.locator("//input[@placeholder='Enter IFSC Code']");
		this.branchName = page.locator("//input[@placeholder='Enter branch name']");
		this.panNumber = page.locator("//input[@placeholder='Enter PAN Number']");
		this.billingCountry = page.locator("//div[@class='react-select__input-container css-19bb58m']");
		this.saveButton = page.locator("//button[normalize-space()='Save']");
	}

	public Locator emailCheckbox() {
		return emailCheckbox;
	}

	public Locator smsCheckbox() {
		return smsCheckbox;
	}

	public Locator notificationCheckboc() {
		return notificationCheckboc;
	}

	public Locator whatsappCheckbox() {
		return whatsappCheckbox;
	}

	public Locator addbankAccount() {
		return addbankAccount;
	}

	public Locator addInvoiceinfo() {
		return addInvoiceinfo;
	}

	public Locator accountHoldername() {
		return accountHoldername;
	}

	public Locator accountNumber() {
		return accountNumber;
	}

	public Locator ifscCode() {
		return ifscCode;
	}

	public Locator branchName() {
		return branchName;
	}

	public Locator panNumber() {
		return panNumber;
	}

	public Locator billingCountry() {
		return billingCountry;
	}

	public Locator saveButton() {
		return saveButton;
	}

}
