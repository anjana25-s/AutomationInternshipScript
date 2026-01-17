package com.promilo.automation.internship.filters;
import java.nio.file.Paths;

import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.internship.assignment.Filters16cardsPages;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;

import basetest.Baseclass;


public class ListingPageFilters extends Baseclass {

    @Test
    void verifyInternshipFilters() {
    	page.locator("//button[text()='May be later!']").click();
        Filters16cardsPages internship = new Filters16cardsPages(page);

       internship.openWebsite("https://stage.promilo.com/");
      
        internship.clickInternshipsTab();
        page.waitForTimeout(2000);
        internship.clickCompanyType();
        internship.selectB2C();
        internship.clickLocation();
        internship.searchAndSelectLocation("Bihar");
        internship.clickDuration();
        internship.selectSixMonthsDuration();
        internship.clickWorkMode();
        internship.selectWFOOption();
        internship.clickStipend();
        internship.selectAbove20KOption();
        internship.clearAllFilters();
        
        
        
        
        
        
    }
}