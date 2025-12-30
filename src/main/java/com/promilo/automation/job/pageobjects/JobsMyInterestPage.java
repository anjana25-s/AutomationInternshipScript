package com.promilo.automation.job.pageobjects;

import java.rmi.registry.LocateRegistry;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class JobsMyInterestPage {
	
	private final Page page;
	
	private final Locator jobRole;
	private final Locator location;
	private final Locator brandName;
	private final Locator experiance;
	private final Locator salary;
	private final Locator serviceName;
	private final Locator serviceIcon;
	private final Locator videoCallRole;
	private final Locator videoCallLocation;
	private final Locator videoCallBrandName;
	private final Locator meetingTime;
	private final Locator meetingDate;
	private final Locator videoCallServiceName;
	
	
	 public JobsMyInterestPage(Page page) {
	        this.page = page;
	        
	        this.jobRole= page.locator("//a[@class='preferance-header-text']");
	        this.brandName=page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").nth(1);
	        this.location= page.locator("[class='font-normal category-text-interest-card text-truncate text-wrap']").first();
	        this.experiance=page.locator("[class='card_detail-value']");
	        this.salary=page.locator("[class='card_detail-value']");
	        this.serviceName=page.locator("[class='service-name']");
	        this.serviceIcon= page.locator("[class='service-icon']");
	        this.videoCallRole=page.locator("[class='card-content-header-text fw-bolder mb-0 text-wrap pointer text-truncate']");
	        this.videoCallBrandName=page.locator("[class='font-normal card-content-sub_header-text category-text-interest-card text-truncate text-wrap']");
	        this.videoCallLocation=page.locator("[class='card-content-sub_header-text category-text-interest-card text-wrap']");
	        this.meetingTime=page.locator("[class='card_detail-value']").nth(1);
	        this.meetingDate=page.locator("[class='card_detail-value']").first();
	        this.videoCallServiceName=page.locator("//span[text()='Online Interview']");
	 }

	 
	 public Locator jobRole() {return jobRole;}
	 public Locator location() {return location;}
	 public Locator brandName() {return brandName;}
	 public Locator experiance() {return experiance;}
	 public Locator salary() {return salary;}
	 public Locator serviceName() {return serviceName;}
	 public Locator serviceIcon() {return serviceIcon;}
	 public Locator videoCallRole() {return videoCallRole;}
	 public Locator videoCallLocation() {return videoCallLocation;}
	 public Locator videoCallBrandName() {return videoCallBrandName;}
	 public Locator meetingTime() {return meetingTime;}
	 public Locator meetingDate() {return meetingDate;}
	 public Locator videoCallServiceName() {return videoCallServiceName;}
}

