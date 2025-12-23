package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateProfile {
	
	private Page page;
	private final Locator firstName;
	private final Locator lastName;
	private final Locator locationDropdown;
	private final Locator genderDropdown;
	private final Locator experianceDropdwon;
	private final Locator typeofMentor;
	private final Locator domain;
	private final Locator highlightTextfield;
	private final Locator uploadCampaignImage;
	private final Locator associatedCompany;
	private final Locator socialmediaLinks;
	private final Locator pasteLinks;
	private final Locator categoryDropdown;
	private final Locator coursesDropdown;
	private final Locator specialization;
	private final Locator saveButton;
	private final Locator mobileTextfield;
	private final Locator cropButton;
	private final Locator dateOfBirth;
	private final Locator instagramOption;

	public CreateProfile(Page page) {
		
		this.page= page;
		
		this.firstName= page.locator("//input[@id='firstName']");
		this.lastName= page.locator("//input[@id='lastName']");
		this.locationDropdown= page.locator(".react-select__input-container");
		this.genderDropdown= page.locator(".react-select__control.css-13cymwt-control > .react-select__value-container > .react-select__input-container");
this.experianceDropdwon= page.locator("#experience > .react-select__control > .react-select__value-container > .react-select__input-container");	
this.typeofMentor= page.locator("#typeofmentors > .react-select__control > .react-select__value-container > .react-select__input-container");
this.domain= page.locator("#domain > .react-select__control > .react-select__value-container > .react-select__input-container");
this.highlightTextfield= page.locator("//input[@id='highlight']");
this.uploadCampaignImage= page.locator("//label[text()='Upload Campaign Image']");
this.associatedCompany= page.locator("div[id='assosiatedCompany'] div[class='react-select__input-container css-19bb58m']");
this.socialmediaLinks= page.locator("//div[@id='socialmedia_0']//div[@class='react-select__input-container css-19bb58m']");
this.pasteLinks= page.locator("//input[contains(@id,'social_media_list')]");
this.categoryDropdown= page.locator("(//div[contains(@class,'dropdown-heading')])[1]");
this.coursesDropdown= page.locator("div:nth-child(4) > .mb-3 > .rmsc > .dropdown-container > .dropdown-heading");
this.specialization = page.locator("div:nth-child(5) > .mb-3 > .rmsc > .dropdown-container > .dropdown-heading");
this.saveButton= page.locator("(//button[text()='Save and Next'])[1]");
this.mobileTextfield= page.locator("//input[@id='mobile']");
this.cropButton= page.locator("//button[text()='Crop']");
this.dateOfBirth= page.locator("#dateOfBirth");
this.instagramOption= page.locator("//div[text()='Instagram']");
		
	}
	
	public Locator firstName() {return firstName;}
	public Locator lastName() {return lastName;}
	public Locator locationDropdown() {return locationDropdown;}
	public Locator genderDropdown() {return genderDropdown;}
	public Locator experianceDropdwon() {return experianceDropdwon;}
	public Locator typeofMentor() {return typeofMentor;}
	public Locator domain() {return domain;}
	public Locator highlightTextfield() {return highlightTextfield;}
	public Locator uploadCampaignImage() {return uploadCampaignImage;}
	public Locator associatedCompany() {return associatedCompany;}
	public Locator socialmediaLinks() {return socialmediaLinks;}
	public Locator pasteLinks() {return pasteLinks;}
	public Locator categoryDropdown() {return categoryDropdown;}
	public Locator coursesDropdown() {return coursesDropdown;}
	public Locator specialization() {return specialization;}
	public Locator saveButton() {return saveButton;}
	public Locator mobileTextfield() {return mobileTextfield;}
	public Locator cropButton() {return cropButton;}
	public Locator dateOfBirth() {return dateOfBirth;}
	public Locator instagramOption() {return instagramOption;}

}
