package com.promilo.automation.mentorship.mentee;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DescriptionPage {

	
	private final Page page;
	private final Locator bookAnEnquiry;
	private final Locator MentorName;
	private final Locator Specialization;
	private final Locator location;
	private final Locator experiance;
	private final Locator shortlist;
	private final Locator askUs;
	private final Locator shortlistedBy;
	private final Locator keySkills;
	private final Locator servicesOffered;
	private final Locator allLink;
	private final Locator oneOnoneCall;
	private final Locator videoCallLink;
	private final Locator askQuery;
	private final Locator brandEndorsement;
	private final Locator personalizedVideoMessage;
	private final Locator getMentorCall;
	private final Locator askYourQuery;
	private final Locator bookOnlineMeeting;
	private final Locator bookEnquiry;
	private final Locator requestVideo;
	private final Locator aboutMe;
	private final Locator myProfessionalJourney;
	private final Locator expertise;
	private final Locator workExperiance;
	private final Locator Specializations;
	private final Locator careerHighlights;
	private final Locator tabContent;
	private final Locator knowMoreAbout;
	private final Locator feedBack;
	private final Locator feedbackTextfield;
	private final Locator submitButton;
	private final Locator similarMentorships;
	private final Locator buyResources;
	private final Locator resourceName;
	private final Locator aboutMeTittle;
	private final Locator aboutMeContent;
	private final Locator typeOfMentorSection;
	private final Locator socialMediaLink;
	private final Locator leftButton;
	private final Locator rightButton;
	
	public DescriptionPage(Page page) {
		
		
		 this.page=page;
		 this.MentorName= page.locator("//div[@class='d-flex font-28 mb-0 pointer box-fit-content mentorship-heading']");
		 this.Specialization= page.locator("//h2[@class='font-16 pb-50 pt-25 mb-0 pointer box-fit-content sub-heading']");
		 this.location= page.locator("(//div[@class='col col-md-auto'])[1]");
		 this.experiance= page.locator("(//div[@class='col col-md-auto'])[2]");
		 this.shortlist= page.locator("//div[text()='Shortlist']").first();
		 this.askUs= page.locator("//button[text()='Ask us?']").first();
		 this.shortlistedBy= page.locator("//div[@class='shortlisted my-auto d-flex ']");
		 this.keySkills= page.locator("//div[@class='keySkills position-relative']");
		 this.servicesOffered= page.locator("//a[text()='Services Offered']");
		 this.allLink= page.locator("//a[text()='All']");
		 this.oneOnoneCall= page.locator("//a[text()='1:1 Call']");
		 this.videoCallLink= page.locator("//a[text()='Video Call']");
		 this.askQuery= page.locator("//a[text()='Ask Query']");
		 this.brandEndorsement= page.locator("//a[text()='Brand Endorsement']");
		 this.personalizedVideoMessage= page.locator("//a[text()='Personalized Video Message']");
		 this.getMentorCall= page.locator("//button[text()='Get a Mentor Call']");
		 this.askYourQuery= page.locator("//button[text()='Ask Your Query']");
		 this.bookOnlineMeeting= page.locator("//button[text()='Book Online Meeting']").first();
		 this.bookEnquiry= page.locator("//button[text()='Book Inquiry']");
		 this.requestVideo= page.locator("//button[text()='Request Video']");
		 this.aboutMe= page.locator("//a[text()='About Me']").first();
		 this.myProfessionalJourney= page.locator("//a[text()='My Professional Journey']").first();
		 this.expertise= page.locator("//a[text()='Expertise']").first();
		 this.workExperiance= page.locator("//a[text()='Work Experience']").first();
		 this.Specializations= page.locator("//a[text()='Specializations']").first();
		 this.careerHighlights= page.locator("//a[text()='Career Highlights']").first();
		 this.tabContent= page.locator("//div[@class='tab-content']").nth(1);
		 this.knowMoreAbout= page.locator("//span[text()='Know more about'] | //span[@class='know-more-mentor']").first();
		 this.feedBack= page.locator("//div[text()='Feedback about']|/span[@class='feedback-brandname']");
		 this.feedbackTextfield= page.locator("//textarea[@placeholder='Write a feedback']");
		 this.submitButton= page.locator("//button[text()='Submit']");
		 this.similarMentorships= page.locator("//div[text()='Similar Mentorships ']");
		 this.buyResources= page.locator("//button[text()='Buy Resources']");
		 this.resourceName= page.locator("//div[contains(@class,'resources-name col')]");
		 this.bookAnEnquiry = page.locator("//button[text()='Book an Inquiry']");
		 this.aboutMeTittle = page.locator("//a[@class='font-sm-12 nav-link mentorship-pill tab-link-searchlisting']");
		 this.aboutMeContent= page.locator("//div[@class='tab-content']");
		 this.socialMediaLink= page.locator("//div[@class='mentorship-heading d-flex']//img[@alt='Location']");
		 this.typeOfMentorSection= page.locator("//h5[@class='font-14 fw-normal pb-2 mb-0 pointer']");
		 this.leftButton= page.locator("[class='left border-0 pointer']");
		 this.rightButton= page.locator("[class='right border-0 pointer']");
		 
		 
		 
		 
		
		
	}
	public Locator MentorName() {return MentorName;}
	public Locator Specialization() {return Specialization;}
	public Locator location() {return location;}
	public Locator experiance() {return experiance;}
	public Locator shortlist() {return shortlist;}
	public Locator askUs() {return askUs;}
	public Locator shortlistedBy() {return shortlistedBy;}
	public Locator keySkills() {return keySkills;}
	public Locator servicesOffered() {return servicesOffered;}
	public Locator allLink() {return allLink;}
	public Locator oneOnoneCall() {return oneOnoneCall;}
	public Locator videoCallLink() {return videoCallLink;}
	public Locator askQuery() {return askQuery;}
	public Locator brandEndorsement() {return brandEndorsement;}
	public Locator personalizedVideoMessage() {return personalizedVideoMessage;}
	public Locator getMentorCall() {return getMentorCall;}
	public Locator askYourQuery() {return askYourQuery;}
	public Locator bookOnlineMeeting() {return bookOnlineMeeting;}
	public Locator bookEnquiry() {return bookEnquiry;}
	public Locator requestVideo() {return requestVideo;}
	public Locator aboutMe() {return aboutMe;}
	public Locator myProfessionalJourney() {return myProfessionalJourney;}
	public Locator expertise() {return expertise;}
	public Locator workExperiance() {return workExperiance;}
	public Locator Specializations() {return Specializations;}
	public Locator careerHighlights() {return careerHighlights;}
	public Locator tabContent() {return tabContent;}
	public Locator knowMoreAbout() {return knowMoreAbout;}
	public Locator feedBack() {return feedBack;}
	public Locator feedbackTextfield() {return feedbackTextfield;}
	public Locator submitButton() {return submitButton;}
	public Locator similarMentorships() {return similarMentorships;}
	public Locator buyResources() {return buyResources;}
	public Locator resourceName() {return resourceName;}
	public Locator bookAnEnquiry() {return bookAnEnquiry;}
	public Locator aboutMeTittle() {return aboutMeTittle;}
	public Locator aboutMeContent() {return aboutMeContent;}
	public Locator typeOfMentorSection() {return typeOfMentorSection;}
	public Locator socialMediaLink() {return socialMediaLink;}
	public Locator leftButton() {return leftButton;}
	public Locator rightButton() {return rightButton;}
	
	
	
}
