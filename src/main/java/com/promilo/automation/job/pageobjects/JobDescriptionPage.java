package com.promilo.automation.job.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class JobDescriptionPage {
	
	private final Page page;
	
	private final Locator JobTitle;
	private final Locator brandName;
	private final Locator salaryValidation;
	private final Locator experiance;
	private final Locator location;
	private final Locator workMode;
	private final Locator shortListed;
	private final Locator jobDescriptionValidation;
	private final Locator perksAndBenifitsValidation;
	private final Locator companyName;
	private final Locator companyType;
	private final Locator brandValidation;
	private final Locator aboutTheCompany;
	private final Locator feedbackAboutValidation;
	private final Locator similarJobsAcrossCity;
	private final Locator similarJobsAcrossIndia;
	private final Locator getConnectedSection;
	private final Locator jobDescription;
	private final Locator benifitsAndPerks;
	
	public JobDescriptionPage(Page page) {
		
		
		this.page=page;
		
		this.JobTitle=page.locator("[class='Job_Internship-Head_title']");
		this.brandName=page.locator("[class='Job_Internship-Subheading_title']");
		this.salaryValidation=page.locator("//span[@class='font-medium ellipsis Job_Internship-info-text']").first();
		this.experiance=page.locator("//span[@class='font-medium ellipsis Job_Internship-info-text']").nth(1);
		this.location=page.locator("//span[@class='font-medium ellipsis Job_Internship-info-text']").nth(2);
		this.workMode=page.locator("//span[@class='font-medium ellipsis Job_Internship-info-text']").nth(3);
		this.shortListed=page.locator("[class='text-black flex flex-row flex-nowrap text-nowarp whitespace-nowrap']");
		this.jobDescriptionValidation=page.locator("[class='ck-content font-sm-12 bullets']").first();
		this.perksAndBenifitsValidation=page.locator("[class='ck-content font-sm-12 bullets']").nth(1);
		this.companyName=page.locator("[class='company-info-text ellipsis']").first();
		this.companyType=page.locator("[class='company-info-text ellipsis']").nth(1);
		this.brandValidation=page.locator("[class='company-info-text ellipsis']").nth(2);
		this.aboutTheCompany=page.locator("[class='font-normal text-gray-600 bg-white']");
		this.feedbackAboutValidation=page.locator("[class='text-black']");
		this.similarJobsAcrossCity=page.locator("[class='card swiper-block-camapign mx-1 my-0 pt-0']").first();
		this.similarJobsAcrossIndia=page.locator("[class='card swiper-block-camapign mx-1 my-0 pt-0']").nth(1);
		this.getConnectedSection=page.locator("[class='py-2 job-and-internship-connect-us']");
		this.jobDescription=page.locator("[class='font-sm-12 nav-link  tab-link-searchlisting']").first();
		this.benifitsAndPerks=page.locator("[class='font-sm-12 nav-link  tab-link-searchlisting']").nth(1);
	}

	public Locator JobTitle() {return JobTitle;}
	public Locator salaryValidation() {return salaryValidation;}
	public Locator brandName() {return brandName;}
	public Locator experiance() {return experiance;}
	public Locator location() {return location;}
	public Locator workMode() {return workMode;}
	public Locator shortListed() {return shortListed;}
	public Locator jobDescriptionValidation() {return jobDescriptionValidation;}
	public Locator perksAndBenifitsValidation() {return perksAndBenifitsValidation;}
	public Locator companyName() {return companyName;}
	public Locator companyType() {return companyType;}
	public Locator brandValidation() {return brandValidation;}
	public Locator aboutTheCompany() {return aboutTheCompany;}
	public Locator feedbackAboutValidation() {return feedbackAboutValidation;}
	public Locator similarJobsAcrossCity() {return similarJobsAcrossCity;}
	public Locator similarJobsAcrossIndia() {return similarJobsAcrossIndia;}
	public Locator getConnectedSection() {return getConnectedSection;}
	public Locator jobDescription() {return jobDescription;}
	public Locator benifitsAndPerks() {return benifitsAndPerks;}
}
