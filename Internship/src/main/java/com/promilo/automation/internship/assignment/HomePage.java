package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class HomePage {
    private final Page page;
 // Locator for the Internships tab element
    private final Locator internshipsTab;
    public HomePage(Page page) {
        this.page = page;
        this.internshipsTab = page.locator("//a[text()='Internships']");
    }
    /**
     * Method to click on the 'Internships' tab
     * Navigates the user to the Internships section
     */
    public void clickInternships() {
     
        internshipsTab.click();
        page.waitForTimeout(2000); 
    }
}