package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class KeySkillsPage {
	
    private final Page page;

    private final Locator SkillsDropdown;
    private final Locator AddSkillsManually;
    private final Locator AddButton;
    private final Locator ExitIcon;
	
	 public KeySkillsPage(Page page) {
	        this.page = page;
	 
	 this.SkillsDropdown = page.locator("//input[@id='react-select-49-input']");
	 this.AddSkillsManually = page.locator("//input[@placeholder='Or you can Add your skills here']");
	 this.AddButton = page.locator("//button[text()='Add']");
	 this.ExitIcon = page.locator("//div[contains(@class, 'css-1xc3v61-indicatorContainer')] [1]");
	 }
	 public Locator SkillsDropdown() {return SkillsDropdown;}
	 public Locator AddSkillsManually() {return AddSkillsManually;}
	 public Locator AddButton() {return AddButton;}
	 public Locator ExitIcon() {return ExitIcon; }
	

}
