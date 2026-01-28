package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DescriptionPageValidation {
	
	
	
	private final Page page;
	private final Locator brandName;
	private final Locator productTitle;
	private final Locator selectedCourse;
	private final Locator location;
	private final Locator duration;
	private final Locator averageFee;
	private final Locator coursesTabsContent;
	private final Locator coursesTabs;
	private final Locator feedbackAboutText;
	private final Locator freeGuidanceExpert;
	private final Locator shortListedBy;
	
	
	public DescriptionPageValidation(Page page) {
		
		this.page=page;
		brandName= page.locator("[class='w-fit text-black fw-bolder font-18']");
		productTitle= page.locator("[class='text-dark-grey text-12 pb-50 mb-0 pointer']");
		selectedCourse= page.locator("[class='text-grey font-12 pb-50 mb-0 pointer']");
		location= page.locator("[class='text-black fw-700 ms-25']");
		duration= page.locator("[class='fw-700 text-black ms-25 inline-flex flex-nowrap whitespace-nowrap items-center']");
		averageFee= page.locator("[class='fw-700 text-black ms-50']");
		coursesTabsContent= page.locator("[class='ck-content font-sm-12 bullets']");
		coursesTabs= page.locator("[class='font-sm-12 nav-link  tab-link-searchlisting']");
		feedbackAboutText= page.locator("[class='text-black']").nth(1);
		freeGuidanceExpert= page.locator("[class='free-expert border-chip ']");
		shortListedBy= page.locator("[class='text-black']").first();
		
		
	}
	
	public Locator brandName() {return brandName;}
	public Locator productTitle() {return productTitle;}
	public Locator selectedCourse() {return selectedCourse;}
	public Locator location() {return location;}
	public Locator duration() {return duration;}
	public Locator averageFee() {return averageFee;}
	public Locator coursesTabsContent() {return coursesTabsContent;}
	public Locator coursesTabs() {return coursesTabs;}
	public Locator feedbackAboutText() {return feedbackAboutText;}
	public Locator freeGuidanceExpert() {return freeGuidanceExpert;}
	public Locator shortListedBy() {return shortListedBy;}
	
	
}
