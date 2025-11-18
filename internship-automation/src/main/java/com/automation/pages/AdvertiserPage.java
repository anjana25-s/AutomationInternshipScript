package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserPage {

    private final Page page;

    // ------------------- Locators -------------------
    private final String loginEmailInput = "//input[@id='login-email']";
    private final String loginPasswordInput = "//input[@placeholder='Enter Password']";
    private final String signInButton = "//button[text()='Sign In']";
    private final String myAccountTab = "//span[text()='My Account']";
    private final String myProspectTab = "//span[text()='My Prospect']";
    private final String internshipsLink = "//a[text()='Internships']";
    private final String rescheduleRequest = "(//span[text()='Reschedule Request'])[1]";
    private final String acceptRequestButton = "//button[text()='Accept Request']";
    private final String doneButton = "//button[@class='btn done-btn w-100']";
    private final String cancelRequestButton = "//button[contains(@class,'cancel-btn-rescheduled')]";
    private final String rejectButton = "//button[contains(@class,'done-btn') and contains(text(),'Reject')]";

    // ------------------- Constructor -------------------
    public AdvertiserPage(Page page) {
        this.page = page;
    }

    // ------------------- Locator Getters -------------------
    public Locator getEmailInput() { return page.locator(loginEmailInput); }
    public Locator getPasswordInput() { return page.locator(loginPasswordInput); }
    public Locator getSignInButton() { return page.locator(signInButton); }
    public Locator getMyAccountTab() { return page.locator(myAccountTab); }
    public Locator getMyProspectTab() { return page.locator(myProspectTab); }
    public Locator getInternshipsLink() { return page.locator(internshipsLink); }
    public Locator getRescheduleRequest() { return page.locator(rescheduleRequest); }
    public Locator getAcceptRequestButton() { return page.locator(acceptRequestButton); }
    public Locator getDoneButton() { return page.locator(doneButton); }
    public Locator getCancelRequestButton() { return page.locator(cancelRequestButton); }
    public Locator getRejectButton() { return page.locator(rejectButton); }
}

