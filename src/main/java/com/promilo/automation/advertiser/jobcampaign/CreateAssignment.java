package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateAssignment {
	
	private Page page;
	private final Locator AssignmentTextfield;
	private final Locator Jobrole;
	private final Locator assignmenttopic;
	private final Locator GnerateassignmentWithAI;
	private final Locator Enterskill;
	private final Locator difficultyLevel;
	private final Locator AssignmentTextarea;
	private final Locator UploadFile;
	private final Locator InsertPicture;
	private final Locator SubmitButton;
	private final Locator ExitIcon;
	private final Locator SelectButton;
	private final Locator SaveAndNextButton;
	
   public CreateAssignment(Page page) {
	   
	   this.page=page;
	   this.AssignmentTextfield= page.locator("input[id='assignment_title']");
	   this.Jobrole=page.locator("[id='job_role']");
	   this.assignmenttopic= page.locator("//input[@id='assignment_topic']");
	   this.GnerateassignmentWithAI=page.locator("input[class='pointer']");
	   this.Enterskill= page.locator("//div[text()='Enter Skill']");
	   this.difficultyLevel= page.locator("[placeholder='Select']");
	   this.AssignmentTextarea= page.locator("//div[@role='textbox']");
	   this.UploadFile= page.locator("label[for='upload_resource']");
	   this.InsertPicture= page.locator("[data-cke-tooltip-text='Insert image']");
	   this.SubmitButton= page.locator("//button[text()='Submit']");
	   this.ExitIcon= page.locator("[class='text-end close-img position-absolute']");
	   this.SelectButton= page.locator("//button[@class='btn-primary px-1 py-25 rounded me-50 font-12 fw-500']");
	   this.SaveAndNextButton= page.locator("//button[text()='Save & Next']");
	   
	   
	   
	   
	   
   }
   
   public Locator AssignmentTextfield() {return AssignmentTextfield;}
   public Locator Jobrole() {return Jobrole;}
   public Locator assignmenttopic() {return assignmenttopic; }
   public Locator GnerateassignmentWithAI() {return GnerateassignmentWithAI;}
   public Locator Enterskill() {return Enterskill;}
   public Locator difficultyLevel() {return difficultyLevel;}
   public Locator AssignmentTextarea() {return AssignmentTextarea;}
   public Locator UploadFile() {return UploadFile;}
   public Locator InsertPicture() {return InsertPicture;}
   public Locator SubmitButton() {return SubmitButton;}
   public Locator ExitIcon() {return ExitIcon;}
   public Locator SelectButton() {return SelectButton;}
   public Locator SaveAndNextButton() {return SaveAndNextButton;}
   
	
	

}
