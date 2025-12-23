package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FillApplication {

	
	
	
	private final Page page;
	
	private final Locator visitTab;
	private final Locator campaignName;
	private final Locator submittedDate;
	private final Locator paidAmount;
	private final Locator serviceIcon;
	private final Locator serviceName;
	private final Locator location;
	private final Locator courseName;
	private final Locator statusTag;
	private final Locator chooseSlotText;
	private final Locator currentMonth;
	private final Locator availableDate;
	private final Locator firstTimeSlot;
	private final Locator thankYouPopUpExitIcon;
	
	
	
    
    

    
	public FillApplication(Page page) {
		
		
		this.page=page;
		
		this.visitTab=page.locator("//span[text()='My Visits']");
		this.campaignName= page.locator("[class='card-content-header-text fw-bolder mb-0 text-wrap pointer text-truncate']");
		this.submittedDate= page.locator("[class='card_detail-value']").first();
		this.paidAmount= page.locator("[class='card_detail-value']").nth(1);
		this.serviceIcon= page.locator("//img[@alt='campaign-icon']");
		this.serviceName= page.locator("//span[text()='Application']");
		this.location=page.locator("[class='card-content-sub_header-text category-text-interest-card text-wrap']");
		this.courseName=page.locator("[class='font-normal card-content-sub_header-text category-text-interest-card text-truncate text-wrap']");
		this.statusTag=page.locator("[class='interest_status-tag']");
		this.chooseSlotText= page.locator("[class='fw-500 font-18 text-primary mb-50']");
		this.currentMonth= page.locator("[class='flatpickr-current-month']");
		this.availableDate=page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
		this.firstTimeSlot=page.locator("//li[@class='time-slot-box list-group-item']").first();
		this.thankYouPopUpExitIcon= page.locator("//img[@alt='closeIcon Ask us']");
	}
	
	public Locator visitTab() {return visitTab;}
	public Locator campaignName() {return campaignName;}
	public Locator submittedDate() {return submittedDate;}
	public Locator paidAmount() {return paidAmount;}
	public Locator serviceIcon() {return serviceIcon;}
	public Locator serviceName() {return serviceName;}
	public Locator location() {return location;}
	public Locator courseName() {return courseName;}
	public Locator statusTag() {return statusTag;}
	public Locator chooseSlotText() {return chooseSlotText;}
	public Locator currentMonth() {return currentMonth;}
	public Locator availableDate() {return availableDate;}
	public Locator firstTimeSlot() {return firstTimeSlot;}
	public Locator thankYouPopUpExitIcon() {return thankYouPopUpExitIcon;}
	
}
