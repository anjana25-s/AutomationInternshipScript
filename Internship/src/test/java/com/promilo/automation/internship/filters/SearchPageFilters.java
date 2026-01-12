package com.promilo.automation.internship.filters;

import org.testng.annotations.Test;

import com.promilo.automation.internship.assignment.FiltersSearchPage;
import basetest.Baseclass;

public class SearchPageFilters extends Baseclass {

 @Test
    	public void FilterTest(){
	 page.locator("//button[text()='May be later!']").click();
         FiltersSearchPage internship = new FiltersSearchPage(page);

         internship.goToInternshipsSection();
         internship.searchInternships();
         internship.filterByCompanyType();
         internship.filterByLocation();
         internship.filterByDuration();
         internship.filterByWorkMode();
         internship.filterByStipend();
         internship.filterByIndustryAndJobRole();
         internship.clearAllFilters();   

    }
    	}	

	