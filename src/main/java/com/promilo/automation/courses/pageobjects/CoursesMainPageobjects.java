package com.promilo.automation.courses.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CoursesMainPageobjects {
	
	private final Locator coursesButton;
	
	private final Page page;
	
	
	
	public CoursesMainPageobjects(Page page) {
		
		this.page= page;
		this.coursesButton= page.locator("//a[text()='Courses']");
		
		
		
	}
	
	public Locator coursesButton() {return coursesButton;}

}
