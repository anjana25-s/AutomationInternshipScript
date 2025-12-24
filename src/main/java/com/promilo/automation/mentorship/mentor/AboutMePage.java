package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AboutMePage {
	
	private final Page page;
	private final Locator textArea;
	private final Locator addTittle;
	private final Locator saveButton;
	private final Locator addButton;
	private final Locator aboutmeOptions;
	private final Locator focus;
	private final Locator fluentIn;
	
public AboutMePage(Page page) {
	this.page = page;
	this.textArea= page.locator("//div[contains(@class,'tab-pane active')]//div[contains(@aria-label,'Editor editing area: main')]");
	this.addTittle= page.locator("//a[text()='+ Add Title']");
	this.saveButton= page.locator("//button[text()='Save and Next']");
	this.addButton= page.locator("button[class='add-btn  btn btn-secondary']");
	this.aboutmeOptions= page.locator("//li[@class='nav-item']");
	this.focus= page.locator("//label[text()='Focus']");
	this.fluentIn= page.locator("//label[text()='Fluent in']");
	
	
	
	
	
}

public Locator textArea() {return textArea;}
public Locator addTittle() {return addTittle;}
public Locator saveButton() {return saveButton;}
public Locator addButton() {return addButton;}
public Locator aboutmeOptions() {return aboutmeOptions;}
public Locator focus() {return focus;}
public Locator fluentIn() {return fluentIn;}

}
