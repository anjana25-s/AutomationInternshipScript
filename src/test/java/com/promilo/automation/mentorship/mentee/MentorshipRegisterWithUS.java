package com.promilo.automation.mentorship.mentee;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.MentorFilterTest;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;

public class MentorshipRegisterWithUS extends Baseclass{

	
	private static final Logger log = LogManager.getLogger(MentorFilterTest.class);

    @Test
    public void mentorshipRegisterWithUSTest() throws IOException {

        log.info("===== Starting Mentor Filter Test =====");

        // -------------------- Initialize Page --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page Popup --------------------
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);
        
        
        MeetupsListingPage registerWithUs= new MeetupsListingPage(page);
        DashboardPage mentorshipClick = new DashboardPage(page);
        mentorshipClick.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");
        page.waitForTimeout(2000);
        registerWithUs.nameField().fill("RegisterWith");
        registerWithUs.typeofMentor().click();
        registerWithUs.Registermentortype().click();
        registerWithUs.mobileNumber().fill("9000098732");
        registerWithUs.emailTextfield().fill("testuser0984@gmail.com");
        registerWithUs.password().fill("Karthik@88");
        registerWithUs.registerButton().click();
        
        
        
        
        
        
    }
}
