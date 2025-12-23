package com.promilo.automation.mentorship.mentee;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.BaseClass;

public class DescriptionPageValidation extends BaseClass {

    private static final Logger log = LogManager.getLogger(ShortListWithExistingPhoneAndEmail.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship ShortList Functionality Test =====");

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);

        // -------------------- Mentorship Module --------------------
        DashboardPage dashboard = new DashboardPage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill("siya patel");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);
        
        //description validation
        DescriptionPage descriptionValidation= new DescriptionPage(page);
        System.out.println(descriptionValidation.MentorName().textContent());  
        System.out.println( descriptionValidation.Specialization().textContent());
        System.out.println( descriptionValidation.location().textContent());
        System.out.println( descriptionValidation.experiance().textContent());
        System.out.println( descriptionValidation.shortlistedBy().textContent());
        System.out.println( descriptionValidation.myProfessionalJourney().textContent());
        System.out.println( descriptionValidation.servicesOffered().textContent());
        descriptionValidation.allLink().click();
        Thread.sleep(3000);
        
        
     // Count matching elements
        int cardCount = page.locator("//div[@class='swiper-card-title truncate w-full mb-0 leading-tight']").count();

        System.out.println("Number of Smilar cards: " + cardCount);
        
        //Feedback submission
        System.out.println(descriptionValidation.feedBack().textContent()); 
        descriptionValidation.feedbackTextfield().fill("FCHVJBNGVBJN M,GVBJN");
        descriptionValidation.submitButton().click();
        System.out.println("clicked on submit");

        
       Locator DivBox =  page.locator("//div[@class=' connect-with-us-mentor d-flex align-items-center justify-content-between px-4']");
       DivBox.isVisible();
       System.out.println(DivBox.textContent()); 
       page.locator("//button[text()='Connect Now']").isVisible();
       
       
       
       
       
       
       
       

          
        
    }
	
	
	

}
