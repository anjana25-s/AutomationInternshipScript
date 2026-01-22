package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class HomePage {
    private final Page page;

    private final Locator internshipsTab;
    private final Locator searchBarClick;
    private final Locator clickInternships;
    private final Locator searchValueClick;
    
    public HomePage(Page page) {
        this.page = page;
        this.internshipsTab = page.locator("//a[text()='Internships']");
        this.searchBarClick=page.locator("[placeholder='Search Colleges, Courses, Internships, Jobs and Mentorships']");
        this.clickInternships=page.locator("(//span[text()='Internships'])[2]");
        this.searchValueClick=page.locator("[placeholder='Search Internships']");
    }
  
    public void clickInternships() {
     
        internshipsTab.click();
        page.waitForTimeout(2000); 
    }
    
    public void clickSearchBar() {
    	searchBarClick.click();
    	 page.waitForTimeout(2000);
    }
    
    public void clickInternshipsTab() {
    	clickInternships.click();
    	 page.waitForTimeout(2000);
    }
    public void clickSearchValue(String Value) {
    	searchValueClick.fill(Value);
    	 page.waitForTimeout(2000);
    }
}