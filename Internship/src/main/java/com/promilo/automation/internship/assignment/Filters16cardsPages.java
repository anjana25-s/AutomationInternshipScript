package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Page Object Model for Internship filters functionality.
 */
public class Filters16cardsPages {

    private final Page page;

    // Popup and Navigation
    private final Locator closePopup;
    private final Locator internshipsTab;

    // Filters
    private final Locator companyType;
    private final Locator companyTypeSearchInput;
    private final Locator b2cOption;

    private final Locator locationDropdown;
    private final Locator locationSearchInput;
    private final Locator selectBihar;

    private final Locator durationDropdown;
    private final Locator selectSixMonths;

    private final Locator workModeDropdown;
    private final Locator selectWFO;

    private final Locator stipendDropdown;
    private final Locator selectAbove20K;

    private final Locator clearAllButton;

    public Filters16cardsPages(Page page) {
        this.page = page;

        // Popup & Navigation
        closePopup = page.locator("//img[@alt='Close']");
        internshipsTab = page.locator("//a[text()='Internships']");

        // Company Type Filter
        companyType = page.locator("//p[text()='Company Type']");
        companyTypeSearchInput = page.locator("//input[@placeholder='Search Company Type']");
        b2cOption = page.locator("//span[normalize-space()='B2C']"); // ✅ fixed

        // ✅ Updated Location Filter
        locationDropdown = page.locator("//p[text()='Location']");
        locationSearchInput = page.locator("//input[@placeholder='Search Location']");
        selectBihar = page.locator("//span[normalize-space()='Bihar - Other']"); // ✅ fixed

        // Duration Filter
        durationDropdown = page.locator("//p[text()='Duration']");
        selectSixMonths = page.locator("(//span[normalize-space()='6 Months'])[2]");

        // Work Mode Filter
        workModeDropdown = page.locator("//p[text()='Work Mode']");
        selectWFO = page.locator("(//span[normalize-space()='WFO'])[2]");

        // Stipend Filter
        stipendDropdown = page.locator("//p[text()='Stipend']");
        selectAbove20K = page.locator("//span[normalize-space()='Above 20K']");

        // Clear All
        clearAllButton = page.locator("//div[text()='Clear all']");
    }

    /** Close popup if visible */
    public void closePopupIfPresent() {
        try {
            if (closePopup.isVisible()) {
                closePopup.click();
            }
        } catch (Exception e) {
            System.out.println("No popup found or already closed.");
        }
    }

    public void clickInternshipsTab() {
        internshipsTab.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        internshipsTab.click();
        page.waitForLoadState();
    }

    public void openCompanyTypeFilter() {
        companyType.scrollIntoViewIfNeeded();
        companyType.click();
    }

    public void searchCompanyType(String value) {
        companyTypeSearchInput.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        companyTypeSearchInput.fill(value);
        page.waitForTimeout(1000); // wait for dropdown results
    }

    public void selectB2C() {
        page.waitForSelector("//span[normalize-space()='B2C']", 
            new Page.WaitForSelectorOptions().setTimeout(5000));
        b2cOption.scrollIntoViewIfNeeded();
        b2cOption.click();
        page.waitForTimeout(1000); 
    }

    // ✅ Updated Location methods
    public void openLocationFilter() {
        locationDropdown.scrollIntoViewIfNeeded();
        locationDropdown.click();

    }
    public void searchLocation(String value) {
        locationSearchInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
        locationSearchInput.fill(value);}
    
    public void SelectLocation() {
         // Wait for the matching option to appear
        page.waitForSelector("//span[normalize-space()='Bihar - Other']", 
            new Page.WaitForSelectorOptions().setTimeout(5000));
        selectBihar.scrollIntoViewIfNeeded();
        selectBihar.click();
    }

    public void openDurationFilter() {
        durationDropdown.scrollIntoViewIfNeeded();
        durationDropdown.click();
    }

    public void selectSixMonthsDuration() {
        selectSixMonths.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        selectSixMonths.click();
    }

    public void openWorkModeFilter() {
        workModeDropdown.scrollIntoViewIfNeeded();
        workModeDropdown.click();
    }

    public void selectWFOOption() {
        selectWFO.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        selectWFO.click();
    }

    public void openStipendFilter() {
        stipendDropdown.scrollIntoViewIfNeeded();
        stipendDropdown.click();
    }

    public void selectAbove20KOption() {
        selectAbove20K.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        selectAbove20K.click();
    }

    public void clearAllFilters() {
        clearAllButton.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        clearAllButton.click();
    }
}
