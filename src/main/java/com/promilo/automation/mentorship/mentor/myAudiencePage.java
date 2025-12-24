package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class myAudiencePage {

	private final Page page;
	private final Locator audienceIndustry;
	private final Locator industryOption;
	private final Locator functionalArea;
	private final Locator functionalareaOption;
	private final Locator relevantTitle;
	private final Locator relevantTitleOption;
	private final Locator audienceLocation;
	private final Locator audiencelocationOption;
	private final Locator MinAge;
	private final Locator Maxage;
	private final Locator Keywords;
	private final Locator Savebutton;

	public myAudiencePage(Page page) {

		this.page = page;
		this.audienceIndustry = page.locator("(//div[@class='search-wrapper searchWrapper '])[1]");
		this.industryOption = page.locator("//li[text()='Advertising & Marketing']");
		this.functionalArea = page
				.locator("//div[@class='form-floating override-mb-0 mb-3']//div[@class='dropdown-heading']");
		this.functionalareaOption = page.locator("//div[text()='Accounting / Tax / Company']");
		this.relevantTitleOption = page.locator("//li[text()='Accounts']");
		this.audienceLocation = page.locator(
				"//div[@class='mb-1 audience-industry-multiselect industry-name-dropdown select-all bottom-option-dropdown col-md-6']//div[@class='form-floating override-mb-0 mb-3']//div//div[@class='search-wrapper searchWrapper ']");
		this.relevantTitle = page.locator(
				"div[class='mb-1 audience-industry-multiselect industry-name-dropdown bottom-option-dropdown col-md-6'] div[class='form-floating override-mb-0 mb-3'] div div[class='search-wrapper searchWrapper ']");
		this.audiencelocationOption = page.locator("//li[text()='Ahmedabad']");
		this.Keywords = page.locator("input[placeholder='Add new keyword here']");
		this.MinAge = page.locator(
				"form-floating.override-mb-0 > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container")
				.first();

		this.Maxage = page.locator("//input[@id='react-select-29-input']");
		this.Savebutton = page.locator("//button[@class='btn-next mx-1 btn btn-primary']");

	}

	public Locator audienceIndustry() {
		return audienceIndustry;
	}

	public Locator industryOption() {
		return industryOption;
	}

	public Locator functionalArea() {
		return functionalArea;
	}

	public Locator functionalareaOption() {
		return functionalareaOption;
	}

	public Locator relevantTitle() {
		return relevantTitle;
	}

	public Locator relevantTitleOption() {
		return relevantTitleOption;
	}

	public Locator audienceLocation() {
		return audienceLocation;
	}

	public Locator audiencelocationOption() {
		return audiencelocationOption;
	}

	public Locator Keywords() {
		return Keywords;
	}

	public Locator Maxage() {
		return Maxage;
	}

	public Locator MinAge() {
		return MinAge;
	}

	public Locator Savebutton() {
		return Savebutton;
	}

}
