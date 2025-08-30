package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Myaudience {
	
	private Page page;
	private final Locator SelectIndustryDopdown;
	private final Locator FunctionalAreadropdown;
	private final Locator SelectRelevantTitle;
	private final Locator Keywords;
	private final Locator MinAge;
	private final Locator Maxage;
	private final Locator LocationDropdown;
	private final Locator Savebutton;
	private final Locator FunctionalAreaOption;
	private final Locator AudienceIndustryDropdwon;

	
   public 	Myaudience(Page page){
	   this.page=page;
	   this.SelectIndustryDopdown= page.locator("//input[@class='searchBox ' and @placeholder='Select Audience Industry']");
	   this.FunctionalAreadropdown= page.locator("[class='dropdown-heading-value']");
	   this.SelectRelevantTitle= page.locator("//input[@placeholder='Select Relevant Title']");
	   this.Keywords= page.locator("input[placeholder='Add new keyword here']");
	   this.MinAge= page.locator("//div[@class='custom-select__input-container css-19bb58m']");
	   this.Maxage= page.locator("//div[@class='custom-select__input-container css-19bb58m']");
	   this.LocationDropdown=page.locator("input[placeholder='Select Audience Location']");
	   this.FunctionalAreaOption= page.locator("//span[text()='Accounting / Tax / Company Secretary / Audit']");
	   this.Savebutton= page.locator("//button[text()='Save & Next']");
	 this.AudienceIndustryDropdwon= page.locator("//div[@id='multiselectContainerReact']");

	   
	   
	   
	   
   }
   
   public Locator SelectIndustryDopdown() {return SelectIndustryDopdown;}
   public Locator FunctionalAreadropdown() {return FunctionalAreadropdown;}
   public Locator SelectRelevantTitle() {return SelectRelevantTitle;}
   public Locator Keywords() {return Keywords;}
   public Locator Maxage() {return Maxage;}
   public Locator  MinAge () {return MinAge;}
   public Locator LocationDropdown() {return LocationDropdown;}
   public Locator Savebutton() {return Savebutton;}
   public Locator FunctionalAreaOption() {return FunctionalAreaOption;}
	public Locator AudienceIndustryDropdwon() {return AudienceIndustryDropdwon;}

  
   

}
