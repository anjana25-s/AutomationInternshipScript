package com.promilo.automation.mentorship.mentee.pagepbjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MeetupsListingPage {
	
	
	private final Page page;
	private final Locator MentorshiButton;
	private final Locator SearchTextField;
	
	//Search category
	private final Locator Courseselection;
	private final Locator ExamGuidance;
	private final Locator AlumniGuidance;
	private final Locator ResumeBuilder;
	private final Locator PlacementPreparation;
	private final Locator mockInterview;
	private final Locator corporateMentors;
	private final Locator lifestyleMentors;
	private final Locator celebrityMentor;	
	
	//Mentorship Categories
	private final Locator seeMoreLintext;
	
	//Academic Guidance
	private final Locator Engineering;
	private final Locator managementLink;
	private final Locator scienceLink;
	private final Locator artsLink;
	
	//Exam guidance
	private final Locator privateSector;
	private final Locator governmentSector;
	
	//alumni guidance
	private final Locator postGraduation;
	private final Locator employedInPrivate;
	private final Locator selfEmployed;
	private final Locator governmentOfficer;
	
	//Resume building
	private final Locator itServices;
	private final Locator technology;
	private final Locator BFSI;
	private final Locator professionalServices;
	
	//placement preparation
	private final Locator placementItservices;
	private final Locator placementTechnology;
	
	
	//skill coaches locators
	
	
	//corporate mentors
	private final Locator athelete;
	private final Locator Author;
	private final Locator Buisiness;
	
	//lifestyleMentors
	private final Locator artsCulture;
	private final Locator healthandWellBeing;
	private final Locator filmMaking;
	
	private final Locator location;
	private final Locator mentorshiptType;
	private final Locator domain;
	private final Locator services;
	private final Locator cost;
	private final Locator language;
	private final Locator skill;
	private final Locator Experience;
	private final Locator associatedCompany;
	
	
	//Register with us
	private final Locator nameField;
	private final Locator mobileNumber;
	private final Locator emailTextfield;
	private final Locator typeofMentor;
	private final Locator password;
	private final Locator registerButton;
	
	private final Locator locationName;
	private final Locator coursedomnainName;
	private final Locator examGuidanceName;
	private final Locator alumniGuidanceName;
	private final Locator collegeCards;
    private final Locator noResultMsg;
    
    private final Locator languageOptions;
    private final Locator searchskill;
    private final Locator experianceOptions;
    private final Locator associatedCompanyoptions;
    private final Locator Registermentortype;
   
	
	
	public MeetupsListingPage(Page page) {
		
		this.page= page;
		this.MentorshiButton= page.locator("//a[text()='Mentorships']");
		this.SearchTextField= page.locator("//input[@placeholder='Search Mentorships']");
		
		
		//Search category
		this.Courseselection= page.locator("//li[text()='Course Selection']");
		this.ExamGuidance= page.locator("//li[text()='Exams Guidance']");
		this.AlumniGuidance=page.locator("//li[text()='Alumni Guidance']");
		this.ResumeBuilder= page.locator("//li[text()='Resume Building']");
		this.PlacementPreparation= page.locator("//li[text()='Placement Preparation']");
		this.mockInterview= page.locator("//li[text()='Mock Interview']");
		this.corporateMentors= page.locator("//li[text()='Corporate Mentors']");
		this.lifestyleMentors= page.locator("//li[text()='LifeStyle Mentors']");
		this.celebrityMentor= page.locator("//li[text()='Celebrity Mentors']");
		
		//Mentorship Categories

		this.seeMoreLintext= page.locator("//a[text()='See More']");
		
		//course guidance
		this.Engineering=page.locator("(//a[text()='Engineering'])[1 ]");
		this.managementLink= page.locator("(//a[text()='Management'])[1]");
		this.scienceLink= page.locator("(//a[text()='Science'])[1]");
		this.artsLink= page.locator("(//a[text()='Arts'])[1]");
		
		//Exam guidance
		this.privateSector= page.locator("(//a[text()='Private Sector'])[1]");
		this.governmentSector= page.locator("(//a[text()='Government Sector'])[1]");
		
		//alumni guidance
		this.postGraduation= page.locator("(//a[text()='Post Graduation'])[1]");
	    this.employedInPrivate= page.locator("(//a[text()='Employed in Private'])[1]");
	    this.selfEmployed= page.locator("(//a[text()='Self-Employed'])[1]");
	    this.governmentOfficer= page.locator("(//a[text()='Government Officer'])[1]");
	    this.itServices= page.locator("(//a[text()='IT Services'])[1]");
	    this.technology= page.locator("(//a[text()='Technology'])[1]");
	    this.BFSI= page.locator("(//a[text()='BFSI'])[1]");
	    this.professionalServices= page.locator("(//a[text()='Professional Services'])[1]");
	    
		//placement preparation
	    this.placementItservices= page.locator("(//a[text()='IT Services'])[2]");
		this.placementTechnology= page.locator("(//a[text()='Technology'])[2]");
		this.athelete= page.locator("(//a[text()='Athelete'])[1]");
		this.Author= page.locator("(//a[text()='Author'])[1]");
		this.Buisiness= page.locator("(//a[text()='Business Leaders'])[1]");
		this.artsCulture= page.locator("(//a[text()='Arts & Culture'])[1]");
		this.healthandWellBeing= page.locator("(//a[text()='Health & Wellbeing'])[1]");
		
		this.filmMaking= page.locator("(//a[text()='Film Making'])[1]");
		
		//filter section
		this.location= page.locator("//p[text()='Location']");
		this.mentorshiptType= page.locator("//p[text()='Mentor Type']");
		this.domain= page.locator("//p[text()='Domain']");
		this.services= page.locator("//p[text()='Services']");
		this.cost= page.locator("//p[text()='Cost']");
		this.language= page.locator("//p[text()='Language']");
		this.skill= page.locator("//p[text()='Skill']");
		this.Experience= page.locator("//p[text()='Experience']");
		this.associatedCompany= page.locator("//p[text()='Assosiated Company']");
		
		//Register with us
		this.nameField= page.locator("//input[@name='userName']");
		this.mobileNumber= page.locator("//input[@name='userMobile']");
		this.emailTextfield= page.locator("//input[@name='userEmail']");
		this.typeofMentor= page.locator("//div[contains(text(),'Type of mentor')]");
		this.password= page.locator("#password");
		this.registerButton= page.locator("//button[text()='Register Now']");
		this.locationName= page.locator("//span[@class='font-14 text-primary label-text pointer text-align-start-filter']").first();
		this.examGuidanceName= page.locator("//span[text()='Exams Guidance']");
		this.coursedomnainName= page.locator("//a[text()='Course Selection']");
		this.alumniGuidanceName= page.locator("//span[text()='Alumni Guidance']");
		
		this.collegeCards = page.locator("xpath=//div[contains(@id,'popular_college')]");
        this.noResultMsg = page.locator("xpath=//p[text()='No Result Found']"); 
        
        this.languageOptions= page.locator("//span[@class='font-14 text-primary label-text pointer text-align-start-filter']");
        this.searchskill= page.locator("#skills-filter");
        this.experianceOptions= page.locator("//span[@class='font-14 text-primary label-text pointer text-align-start-filter']");
        this.associatedCompanyoptions= page.locator("//input[@placeholder='Search Assosiated Company']");
        this.Registermentortype= page.locator("//span[text()='Academic Guidance']");
	}
	
	public Locator MentorshiButton() {return MentorshiButton;}
	public Locator SearchTextField() {return SearchTextField;}
	
	
	//Search category
	public Locator Courseselection() {return Courseselection;}
	public Locator ExamGuidance() {return ExamGuidance;}
	public Locator AlumniGuidance() {return AlumniGuidance;}
	public Locator ResumeBuilder() {return ResumeBuilder;}
	public Locator PlacementPreparation() {return PlacementPreparation;}
	public Locator mockInterview() {return mockInterview;}
	public Locator corporateMentors() {return corporateMentors;}
	public Locator lifestyleMentors() {return lifestyleMentors;}
	public Locator celebrityMentor() {return celebrityMentor;}
	
	
	//		//Mentorship Categories
	public Locator seeMoreLintext() {return seeMoreLintext;}
	
	//course selection
	public Locator managementLink() {return managementLink;}
	public Locator  engineering() {return Engineering;}
	public Locator scienceLink() {return scienceLink;}
	public Locator artsLink() {return artsLink;}
	
	//exam Guidance
	public Locator privateSector() {return privateSector;}
	public Locator governmentSector() {return governmentSector;}
	
	//alumni Guidance
	public Locator postGraduation() {return postGraduation;}
	public Locator employedInPrivate() {return employedInPrivate;}
	public Locator selfEmployed() {return selfEmployed;}
	public Locator governmentOfficer() {return governmentOfficer;}
	
	//Resume building
	public Locator itServices() {return itServices;}
	public Locator  Technology() {return technology;}
	public Locator BFSI() {return BFSI;}
	public Locator professionalServices() {return professionalServices;}
	
	//placement preparation
	public Locator placementItservices() {return placementItservices;}
	public Locator placementTechnology() {return placementTechnology;}
	
	//corporate mentors
	public Locator athelete() {return athelete;}
	public Locator author() {return Author;}
	public Locator Buisiness() {return Buisiness;}
	public Locator artsCulture() {return artsCulture;}
	public Locator healthandWellBeing() {return healthandWellBeing;}
	
	public Locator filmMaking() {return filmMaking;}
	
	public Locator location() {return location;}
	public Locator mentorshiptType() {return mentorshiptType;}
	public Locator domain() {return domain;}
	public Locator services() {return services; }
	public Locator cost() {return cost;}
	public Locator language() {return language;}
	public Locator skill() {return skill;}
	
	public Locator associatedCompany() {return associatedCompany;};
	public Locator Experience() {return Experience;}
	public Locator nameField() {return nameField;}
	public Locator mobileNumber() {return mobileNumber;}
	public Locator emailTextfield() {return emailTextfield;}
	public Locator typeofMentor() {return typeofMentor;}
	public Locator password() {return password;}
	public Locator registerButton() {return registerButton;}
	public Locator locationName() {return locationName;}
	public Locator coursedomnainName() {return coursedomnainName;}
	public Locator examGuidanceName() {return examGuidanceName;}
	public Locator alumniGuidanceName() {return alumniGuidanceName;}
	
	public Locator languageOptions() {return languageOptions;}
	public Locator searchskill() {return searchskill;}
	public Locator experianceOptions() {return experianceOptions;}
	public Locator associatedCompanyoptions() {return associatedCompanyoptions;}
	public Locator Registermentortype() {return Registermentortype;}
	
	
		
	
	
	
	
	
	
	
	
	

}
