package com.promilo.automation.mentorship.mentor;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ServicesPage {
	
	
	private final Page page;
	
	private final Locator oneonecall;
	private final Locator serviceName;
	private final Locator langauges;
	private final Locator serviceFee;
	private final Locator evaluate;
	private final Locator addService;
	private final Locator noserviceAdded;
	private final Locator saveAndNextButton;
	private final Locator serviceCard;
	
	//Video call
	private final Locator videoCall;
	private final Locator calenderButton;
	private Locator Monday;
	private Locator Tuesday;
	private Locator Wednsday;
	private Locator Thursday;
	private Locator Friday;
	private Locator Saturday;
	private Locator Sunday;
	private Locator plusIcon;
	private Locator submitButton;
	
	
	private final Locator askQuery;
	
	
	
	private final Locator resources;
	private final Locator uploadFile;
	
	private final Locator brandEndoursement;
	private final Locator personalizedVideo;
	private final Locator videoDuration;
	private final Locator deliveryTime;
	
	
	
	
	public ServicesPage(Page page) {
		
		this.page= page;
		this.oneonecall= page.locator("//span[text()='1:1 Call']");
		this.videoCall= page.locator("//span[text()='Video Call']");
		this.askQuery= page.locator("//span[text()='Ask Query']");
		this.resources= page.locator("//span[text()='Resources']");
		this.brandEndoursement= page.locator("//span[text()='Brand Endorsement']");
		this.personalizedVideo= page.locator("//span[text()='Personalized Video']");
		this.serviceName= page.locator("#serviceName");
		this.langauges= page.locator("//div[@class='rmsc service-react-multi-select font-12']//div[@class='dropdown-heading']");
		this.serviceFee= page.locator("#serviceFee");
		this.evaluate= page.locator("//button[text()='Evaluate']");
		this.addService= page.locator("//button[text()='Add Service']");
		this.noserviceAdded= page.locator("//h4[text()='No Services Added']");
		this.saveAndNextButton= page.locator("//button[@class='btn btn-primary']");
		this.serviceCard= page.locator("//div[@class='service-card']");
		this.calenderButton= page.locator("//img[@alt='calendar']");
		
		this.Monday= page.locator("input[id='MONDAY']");  
		this.Tuesday= page.locator("input[id='TUESDAY']");
		this.Wednsday= page.locator("input[id='WEDNESDAY']");
		this.Thursday= page.locator("input[id='THURSDAY']");
		this.Friday= page.locator("input[id='FRIDAY']");
		this.Saturday= page.locator("input[id='SATURDAY']");
		this.Sunday= page.locator("input[id='SUNDAY']");
		this.plusIcon= page.locator("//button[@class='btn-primary rounded min-w-6']");
		this.submitButton= page.locator("//button[@class='btn btn-primary float-right']");
		this.uploadFile= page.locator("//label[text()='Upload File']");
		this.videoDuration= page.locator("//div[@id='video_duration']//div[@class='react-select__input-container css-19bb58m']");
		this.deliveryTime= page.locator("//div[@id='delivery_time']//div[@class='react-select__input-container css-19bb58m']");
		
		
		
		
	}
	public Locator oneonecall() {return oneonecall;}
	public Locator videoCall() {return videoCall;}
	public Locator askQuery() {return askQuery;}
	public Locator resources() {return resources;}
	public Locator brandEndoursement() {return brandEndoursement;}
	public Locator personalizedVideo() {return personalizedVideo;}
	public Locator serviceName() {return serviceName;}
	public Locator langauges() {return langauges;}
	public Locator serviceFee() {return serviceFee;}
	public Locator evaluate() {return evaluate;}
	public Locator addService() {return addService;}
	public Locator noserviceAdded() {return noserviceAdded;}
	public Locator saveAndNextButton() {return saveAndNextButton;}
    public Locator serviceCard() {return serviceCard;}
    public Locator calenderButton() {return calenderButton;}
    
    
    public Locator Monday() {return Monday;}
    public Locator Tuesday() {return Tuesday;}
    public Locator Wednsday() {return Wednsday;}
    public Locator Thursday() {return Thursday;}
    public Locator Sunday() {return Sunday;}
    public Locator Friday() {return Friday;}
    public Locator Saturday() {return Saturday;}
    public Locator plusIcon() {return plusIcon;}
    public Locator submitButton() {return submitButton;}
    public Locator uploadFile() {return uploadFile;}
    public Locator videoDuration() {return videoDuration;}
    public Locator deliveryTime() {return deliveryTime;}
}
