package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Campaign {
	
	private Page page;
	
	private final Locator AddnewCampaign;
	private final Locator SmartEhire;
	private final Locator JobButton;
	
	
   public Campaign (Page page){
	   this.page=page;
	   this.AddnewCampaign= page.locator("//span[text()='Add New Campaign']");
	   this.SmartEhire= page.locator("//p[text()='Search less & hire more with Smart E-Hire: To streamline your hiring process & top talent.']");
	   this.JobButton= page.locator("//button[text()='Job']");
	   
	   
	   
   }
   public Locator AddnewCampaign() {return AddnewCampaign;}
   public Locator SmartEhire() {return SmartEhire;}
   public Locator JobButton() {return JobButton;}

}
