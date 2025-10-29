package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CampaignlistPage {
	
	

	private final Page page;
	
	private final Locator searchField;
	private final Locator calenderButton;
	private final Locator fromTextfield;
	private final Locator toTextfield;
	private final Locator closeButton;
	private final Locator statusButton;
	private final Locator all;
	private final Locator active;
	private final Locator draft;
	private final Locator expired;
	private final Locator inReview;
	private final Locator rejected;
	private final Locator stopped;
	private final Locator checkBox;
	private final Locator copyButton;
	private final Locator editButton;
	private final Locator deletedButton;
	private final Locator excelDownload;
	private final Locator draftIcon;
	private final Locator draftText;
	private final Locator inreviewText;
	private final Locator inreviewIcon;
	private final Locator totalRecordsFound;
	private final Locator yesButton;
	private final Locator servicesLink;
	private final Locator createdDate;
	private final Locator popupDeleteButton;
	private final Locator deletedToaster;
	private final Locator searchIcon;
	private final Locator campaignName;
	private final Locator campaignStatus;
	public CampaignlistPage(Page page) {
		
		this.page=page;
this.searchField = page.locator("#searchKeywordBox");
this.calenderButton= page.locator("//button[text()='Start Date - End Date']");
this.fromTextfield= page.locator("//input[@placeholder='Early']");
this.toTextfield= page.locator("//input[@placeholder='Continuous']");
this.closeButton= page.locator("//button[text()='Close']");
this.statusButton= page.locator("//div[text()='Status']");
this.all= page.locator("//div[text()='All']");
this.active= page.locator("//div[text()='Active']");
this.draft= page.locator("//div[text()='Draft']");
this.expired= page.locator("//div[text()='Expired']");
this.inReview= page.locator("//div[text()='In review']");
this.rejected= page.locator("//div[text()='Rejected']");
this.stopped= page.locator("//div[text()='Stopped']");
this.checkBox= page.locator("//td[@class='camapign-list-td-checkbox last-checkbox']");
this.copyButton= page.locator("//img[@class='img-fluid d-inline fit-content']");
this.editButton= page.locator("//span[text()='Edit']");
this.deletedButton= page.locator("//img[@class='img-fluid d-inline delete-icon pointer']");
this.excelDownload= page.locator("//img[@class='img-fluid excel-icon pointer']");
this.draftIcon= page.locator("//img[@alt='DRAFT Icon']");	
this.draftText= page.locator("//div[text()='Draft']");
this.inreviewText= page.locator("//div[text()='In review']");
this.inreviewIcon= page.locator("//img[@alt='IN_REVIEW Icon']");
this.totalRecordsFound= page.locator("//span[text()='Total Records: ']");
this.yesButton= page.locator("//button[text()='Yes']");
this.servicesLink= page.locator("//span[@class='service-nav pointer']");
this.createdDate= page.locator("//div[@class='col-12']");
this.popupDeleteButton= page.locator("//button[text()='Delete']");
this.deletedToaster= page.locator("//div[text()='Campaign Deleted Successfully.']");
this.searchIcon= page.locator("//img[@alt='Search']");
this.campaignName= page.locator("//div[@class='col-12 campaign-title']");
this.campaignStatus= page.locator("//div[@class='text-nowrap text-start px-1 d-flex align-center gap-1']");
	
	}

	public Locator searchField() {return searchField;}
	public Locator calenderButton() {return calenderButton;}
	public Locator fromTextfield() {return fromTextfield;}
	public Locator toTextfield() {return toTextfield;}
	public Locator closeButton() {return closeButton;}
	public Locator statusButton() {return statusButton;}
	public Locator all() {return all;}
	public Locator active() {return active;}
	public Locator draft() {return draft;}
	public Locator expired() {return expired;}
	public Locator inReview() {return inReview;}
	public Locator rejected() {return rejected;}
	public Locator stopped() {return stopped;}
	public Locator checkBox() {return checkBox;}
	public Locator copyButton() {return copyButton;}
	public Locator editButton() {return editButton;}
	public Locator deletedButton() {return deletedButton;}
	public Locator excelDownload() {return excelDownload;	}
	public Locator draftIcon() {return draftIcon;}
	public Locator draftText() {return draftText;}
	public Locator inreviewText() {return inreviewText;}
	public Locator inreviewIcon() {return inreviewIcon;}
	public Locator totalRecordsFound() {return totalRecordsFound;}
	public Locator yesButton() {return yesButton;}
	public Locator servicesLink() {return servicesLink;}
	public Locator createdDate() {return createdDate;}
	public Locator popupDeleteButton() {return popupDeleteButton;}
	public Locator deletedToaster() {return deletedToaster;}
	public Locator searchIcon() {return searchIcon;}
	public Locator campaignName() {return campaignName;}
	public Locator campaignStatus() {return campaignStatus;}
}
