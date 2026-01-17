package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class FiltersSearchPage {
	    private final Page page;
	    /**
	     * Constructor to initialize Playwright Page instance
	     */
	    public FiltersSearchPage(Page page) {
	        this.page = page;
	    }
	    /**
	     * Navigate to the Internships section through the menu
	     */
	    public void goToInternshipsSection() {
	    	  page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Colleges, Courses,")).click();
	         page.waitForTimeout(2000);
	         page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Internships")).click();
		        page.waitForTimeout(2000);
	    }
	    /**
	     * Search internships using the search bar and filter list suggestion
	     */
	    public void searchInternships() {
	       
	        // Click on the first suggestion from dropdown list
	       page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Internships")).locator("span").click();
	        page.waitForTimeout(1000);
	    }

	    /**
	     * Apply filter → Company Type → Select "B2C"
	     */
	    public void filterByCompanyType() {
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Company Type")).click();
	        page.waitForTimeout(2000);
	        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Company Type")).click();
	        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search Company Type")).fill("b");
	        page.waitForTimeout(2000);
	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("B2C")).click();
	        page.waitForTimeout(2000);
	    }
	    /**
	     * Apply filter → Location → Select "Bihar - Other"
	     */
	    public void filterByLocation() {
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Location")).click();
	        page.waitForTimeout(2000);
	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Bihar - Other")).click();
	        page.waitForTimeout(2000);
	    }
	    /**
	     * Apply filter → Duration → Select "6 Months"
	     */
	    public void filterByDuration() {
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Duration")).click();
	        page.waitForTimeout(2000);
	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("6 Months")).click();
	        page.waitForTimeout(2000);
	    }
	    /**
	     * Apply filter → Work Mode → Select "WFO"
	     */
	    public void filterByWorkMode() {
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Work Mode")).click();
	        page.waitForTimeout(2000);
	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("WFO")).click();
	        page.waitForTimeout(2000);
	    }
	    /**
	     * Apply filter → Stipend → Select "Above 20K"
	     */
	    public void filterByStipend() {
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Stipend")).click();
	        page.waitForTimeout(2000);
	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Above 20K")).click();
	        
	    }
	    /**
	     * Apply filter → Industry and Job Role filters
	     */
	        public void filterByIndustryAndJobRole() {
	           
	            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Industry")).click();
	            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Software Development - ALL")).click();
	            page.waitForTimeout(2000);
	            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Job Role")).click();
	            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("QA/Testing/Documentation")).click();
	            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Technical Writer")).click();
	         
	        }
	        
	        /**
	         * Clears all applied filters if visible
	         */
	        public void clearAllFilters() {
	            Locator clearAll = page.getByText("Clear all");
	            page.waitForTimeout(2000);
	            if (clearAll.isVisible()) {
	                clearAll.click();
	            }
	            
	            
	         
	        }

	

		
	}