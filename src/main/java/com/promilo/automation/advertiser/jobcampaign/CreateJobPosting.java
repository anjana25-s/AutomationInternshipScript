package com.promilo.automation.advertiser.jobcampaign;

import java.rmi.registry.LocateRegistry;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreateJobPosting {
	
	private Page page;
	
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
	
	
	
  public 	CreateJobPosting (Page page) {
	  
	  this.page=page;
	  
	  this.ChoseTimeSlot= page.locator("//div[@class='time-slot-select__value-container time-slot-select__value-container--has-value css-hlgwow']");
	  this.TimeslotButton= page.locator("//div[@class='time-slot-select__input-container css-19bb58m']");
	this.Monday= page.locator("input[id='MONDAY']");  
	this.Tuesday= page.locator("input[id='TUESDAY']");
	this.Wednsday= page.locator("input[id='WEDNESDAY']");
	this.Thursday= page.locator("input[id='THURSDAY']");
	this.Friday= page.locator("input[id='FRIDAY']");
	this.Saturday= page.locator("input[id='SATURDAY']");
	this.Sunday= page.locator("input[id='SUNDAY']");
	
	this.English= page.locator("label[for='English']");
	this.Hindi= page.locator("label[for='Hindi']");
	this.Bangla= page.locator("label[for='Bangla']");
	this.Marathi= page.locator("label[for='Marathi']");
	this.Telugu= page.locator("label[for='Telugu']");
	this.Tamil= page.locator("label[for='Tamil']");
	this.Gujarati= page.locator("label[for='Gujarati']");
	this.Urdu= page.locator("label[for='Urdu']");
	this.Kannada= page.locator("label[for='Kannada']");
	this.Odia= page.locator("label[for='Odia']");
	this.Malayalam= page.locator("label[for='Malayalam']");
	this.Punjabi= page.locator("label[for='Punjabi']");
	this.Assamese= page.locator("label[for='Assamese']");
	this.SaveButton= page.locator("//button[text()='Save & Next']");
	
	
	
	
	  
	  
	  
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
  
  
  
	
	

}
