package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
/**
 * Page Object Model for Internship filters functionality.
 */
public class Filters16cardsPages {
    private final Page page;

 // Popup and Navigation
    private final Locator closePopup;
    private final Locator internshipsTab;
// Filter Elements
    private final Locator b2cOption;
    private final Locator locationDropdown;
    private final Locator searchLocation;
    private final Locator selectBihar;
    private final Locator durationDropdown;
    private final Locator selectSixMonths;
    private final Locator workModeDropdown;
    private final Locator selectWFO;
    private final Locator stipendDropdown;
    private final Locator selectAbove20K;
    private final Locator clearAllButton;

    /**
     * Constructor to initialize locators using Playwright Page.
     */
    public Filters16cardsPages(Page page) {
        this.page = page;

        this.closePopup = page.locator("//img[@alt='Close']");
        this.internshipsTab = page.locator("//a[text()='Internships']");
        this.b2cOption = page.locator("//p[text()='B2C']");
        this.locationDropdown = page.locator("//p[text()='Location']");
        this.searchLocation = page.locator("//input[@placeholder='Search Location']");
        this.selectBihar = page.locator("(//span[text()='Bihar - Other'])[2]");
        this.durationDropdown = page.locator("//p[text()='Duration']");
        this.selectSixMonths = page.locator("(//span[text()='6 Months'])[2]");
        this.workModeDropdown = page.locator("//p[text()='Work Mode']");
        this.selectWFO = page.locator("(//span[text()='WFO'])[2]");
        this.stipendDropdown = page.locator("//p[text()='Stipend']");
        this.selectAbove20K = page.locator("//span[text()='Above 20K']");
        this.clearAllButton = page.locator("//div[text()='Clear all']");
    }


    /** Open website URL */
    public void openWebsite(String url) {
        page.navigate(url);
        page.waitForTimeout(4000);
    }

    /** Close popup displayed on homepage */
    public void closePopup() {
        closePopup.click();
        page.waitForTimeout(2000);
    }

    /** Click on Internship tab */
    public void clickInternshipsTab() {
        internshipsTab.click();
        page.waitForTimeout(2000);
       }
    /** Select B2C filter category */
     public void selectB2C() {
        b2cOption.click();
        page.waitForTimeout(2000);
    }
     /** Click on Location filter dropdown */
    public void clickLocation() {
    	locationDropdown.click();
    	locationDropdown.scrollIntoViewIfNeeded();
    	page.waitForTimeout(2000);
         }
    /**
     * Searches location inside dropdown and selects Bihar location
     * @param location dynamic location value to search
     */

    public void searchAndSelectLocation(String location) {
        searchLocation.fill(location);
        page.waitForTimeout(1000);
        selectBihar.click();
        page.waitForTimeout(5000);
    }


    /** Click on Duration filter */
    public void clickDuration() {
        durationDropdown.click();
       page.waitForTimeout(1000);
    }
    /** Select 6 months duration */
    public void selectSixMonthsDuration() {
        selectSixMonths.click();
        page.waitForTimeout(1000);
    }
    /** Click Work Mode dropdown */
    public void clickWorkMode() {
        workModeDropdown.click();
        page.waitForTimeout(1000);
    }
    /** Select Work From Office option */
    public void selectWFOOption() {
        selectWFO.click();
        page.waitForTimeout(1000);
    }
    /** Click on Stipend dropdown */
    public void clickStipend() {
        stipendDropdown.click();
        page.waitForTimeout(1000);
    }

    /** Select stipend filter above 20K */
    public void selectAbove20KOption() {
        selectAbove20K.click();
        page.waitForTimeout(1000);
    }
    /** Clear all applied filters */
    public void clearAllFilters() {
        clearAllButton.click();
        page.waitForTimeout(2000);
    }



	
}