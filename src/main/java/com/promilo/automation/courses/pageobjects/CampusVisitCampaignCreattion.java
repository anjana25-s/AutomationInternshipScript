package com.promilo.automation.courses.pageobjects;

import java.rmi.registry.LocateRegistry;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CampusVisitCampaignCreattion {
	
	private Page page;
	
	private final Locator visitCheckBox;
	private Locator ChoseTimeSlot;
	private Locator Monday;
	private Locator Tuesday;
	private Locator Wednsday;
	private Locator Thursday;
	private Locator Friday;
	private Locator Saturday;
	private Locator Sunday;
	
	private Locator English;
	private Locator Hindi;
	private Locator Bangla;
	private Locator Marathi;
	private Locator Telugu;
	private Locator Tamil;
	private Locator Gujarati;
	private Locator Urdu;
	private Locator Kannada;
	private Locator Odia;
	private Locator Malayalam;
	private Locator Punjabi;
	private Locator Assamese;
	private Locator TimeslotButton;
	
	private Locator SaveButton;
    private final Locator addSlotButton;
    private final Locator timeSlotContainer;
    private final Locator questionInput;
    private final Locator addOptionButton;
    private final Locator optionInput;
    private final Locator selectCounselors;
    private final Locator mapLinkField;
    private final Locator selectCounselorsOption;




	
	
	
  public 	CampusVisitCampaignCreattion (Page page) {
	  
	  this.page=page;
	  
	  this.ChoseTimeSlot= page.locator("//div[@class='time-slot-select__value-container time-slot-select__value-container--has-value css-hlgwow']");
	  this.TimeslotButton= page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");
	this.Monday= page.locator("input[id='MONDAY_visit']");  
	this.Tuesday= page.locator("input[id='TUESDAY']");
	this.Wednsday= page.locator("input[id='WEDNESDAY']");
	this.Thursday= page.locator("input[id='THURSDAY']");
	this.Friday= page.locator("input[id='FRIDAY']");
	this.Saturday= page.locator("input[id='SATURDAY']");
	this.Sunday= page.locator("input[id='SUNDAY']");
	
	this.English= page.locator("label[for='English_visit']");
	this.Hindi= page.locator("label[for='Hindi_visit']");
	this.Bangla= page.locator("label[for='Bangla_visit']");
	this.Marathi= page.locator("label[for='Marathi_visit']");
	this.Telugu= page.locator("label[for='Telugu_visit']");
	this.Tamil= page.locator("label[for='Tamil_visit']");
	this.Gujarati= page.locator("label[for='Gujarati']");
	this.Urdu= page.locator("label[for='Urdu']");
	this.Kannada= page.locator("label[for='Kannada_video']");
	this.Odia= page.locator("label[for='Odia']");
	this.Malayalam= page.locator("label[for='Malayalam']");
	this.Punjabi= page.locator("label[for='Punjabi']");
	this.Assamese= page.locator("label[for='Assamese']");
	this.SaveButton= page.locator("//button[text()='Save & Next']");
    this.addSlotButton = page.locator("//button[@class='plus-icon btn btn-secondary']");
    this.timeSlotContainer = page.locator("//input[@id='MONDAY_visit']/following::div[contains(@class,'time-slot-select__input-container')]");
    this.questionInput = page.locator("input[placeholder='Type Question Here']");
    this.addOptionButton = page.locator("//button[@class='py-0 px-1 ms-0 btn btn-primary']");
    this.optionInput = page.locator("//input[@class='border-0 outline-0 ms-1 form-control']");
    this.visitCheckBox= page.locator("//label[text()=' Visits ']");
    this.selectCounselors= page.locator("//div[@class='css-19bb58m']");
    this.mapLinkField= page.locator("//input[@placeholder='Add google map link']");
    this.selectCounselorsOption= page.locator("[class='location-checkbox']");





	
	
	
	
	  
	  
	  
  }
  
  public Locator ChoseTimeSlot() {return ChoseTimeSlot;}
  public Locator Monday() {return Monday;}
  public Locator Tuesday() {return Tuesday;}
  public Locator Wednsday() {return Wednsday;}
  public Locator Thursday() {return Thursday;}
  public Locator Sunday() {return Sunday;}
  public Locator Friday() {return Friday;}
  public Locator Saturday() {return Saturday;}
  
  
  public Locator English() {return English;}
  public Locator Hindi() {return Hindi;}
  public Locator Telugu() {return Telugu;}
  public Locator Kannada() {return Kannada;}
  public Locator Tamil() {return Tamil;}
  public Locator Malayalam() {return Malayalam;}
  public Locator Odia() {return Odia;}
  public Locator Punjabi() {return Punjabi;}
  public Locator Assamese() {return English;}
  public Locator Gujarati() {return Gujarati;}
  public Locator Marathi() {return Marathi;}
  public Locator Urdu() {return Urdu;}
  public Locator Bangla () {return Bangla;}
  public Locator TimeslotButton() {return TimeslotButton;}
  
  public Locator SaveButton() {return SaveButton;}
  public Locator addSlotButton() { return addSlotButton; }
  public Locator timeSlotContainer() { return timeSlotContainer; }
  public Locator questionInput() { return questionInput; }
  public Locator addOptionButton() { return addOptionButton; }
  public Locator optionInput() { return optionInput; }
  public Locator visitCheckBox() {return visitCheckBox;}
  public Locator selectCounselors() {return selectCounselors;}
  public Locator mapLinkField() {return mapLinkField;}
  public Locator selectCounselorsOption() {return selectCounselorsOption;}




  
  
  
	
	

}
