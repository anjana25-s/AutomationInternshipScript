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

public class ShortListWithExistingPhoneAndEmail extends BaseClass {

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

        // Shortlist mentor
        DescriptionPage description = new DescriptionPage(page);
        description.shortlist().click();
        log.info("Clicked on shortlist button");

      
     // -------------------- Fill form details --------------------
        MentorshipFormComponents form = new MentorshipFormComponents(page);
        form.name().fill("karthik");

        // Generate phone number starting with 90000 + 5 random digits
        String randomPhone = "90000" + String.format("%05d", (int)(Math.random() * 100000));
        form.MobileTextField().fill(randomPhone);

        
        form.emailTextfield().fill("7dd4a049@8mgfvj1x.mailosaur.net");

       

        Locator Button= form.shortListButton();
        Button.scrollIntoViewIfNeeded();
        Button.click();
        log.info("Filled shortlist form details");
        
        Thread.sleep(2000);
       System.out.println(page.locator("//div[text()='This email ID is already registered. Please try login.']").textContent()); 
        


        // Cleanup
        page.close();
    }
}
