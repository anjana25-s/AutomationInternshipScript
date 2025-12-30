package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyBillingPage
{
private final Page page;
// Locators for UI elements in My Billing section
private final Locator myBilling;
private final Locator sendEmail;
private final Locator sendInvoice;
private final Locator preview;
private final Locator closeButton;

/**
 * Constructor initializes My Billing page locators using Playwright Page instance.
 * 
 * @param page Playwright Page instance for interacting with UI
 */
	public MyBillingPage(Page page) {
		this.page=page;
		this.myBilling=page.locator("//span[text()='My Billing']");
		this.preview=page.locator(".mx-50 pointer me-2");
		this.closeButton=page.locator("(//button[@type='button'])[1]");
		this.sendEmail=page.locator("(//img[@src='/assets/email-send-disabled-760f8f4f.svg'])[1]");
		this.sendInvoice=page.locator("//button[@class='btn done-btn w-100']");
		
	}
	/**
     * Clicks on My Billing tab in the dashboard.
     *
     * @throws InterruptedException if thread sleep is interrupted
     */
	public void clickMyBilling() throws InterruptedException {
		myBilling.waitFor();
		myBilling.click();
		Thread.sleep(3000);
		}
	
	 /**
     * Opens invoice preview popup.
     */
	public void clickOnPreiew() {
		preview.waitFor();
		preview.click();
	}
	 /**
     * Closes the invoice preview popup.
     */
	public void clickClose() {
		closeButton.waitFor();
		closeButton.click();
	}
	/**
     * Clicks the button to send the invoice to email.
     */
	public void clicksendEmail() {
		sendEmail.waitFor();
		sendEmail.click();
	
		
	}
	  /**
     * Confirms sending of the invoice.
     */
	public void clickSendInvoice() {
		sendInvoice.waitFor();
		sendInvoice.click();
		
	}
	
}