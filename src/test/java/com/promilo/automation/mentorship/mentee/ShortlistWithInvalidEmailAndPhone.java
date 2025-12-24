package com.promilo.automation.mentorship.mentee;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
<<<<<<< HEAD
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.pagepbjects.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;

public class ShortlistWithInvalidEmailAndPhone extends Baseclass {

    private static final Logger log = LogManager.getLogger(ShortlistWithInvalidEmailAndPhone.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship ShortList Functionality Test =====");

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);

        // -------------------- Mentorship Module --------------------
        HomePage dashboard = new HomePage(page);
=======
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.BaseClass;

public class ShortlistWithInvalidEmailAndPhone extends BaseClass {

    private static final Logger log = LogManager.getLogger(ShortlistWithInvalidEmailAndPhone.class);

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
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
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
        page.keyboard().press("Tab");
        form.name().fill("123");
        page.keyboard().press("Tab");
        Locator errorMessage = page.locator("div[class='text-danger']").first();
        String NameError = errorMessage.textContent();
        System.out.println(NameError);
        Assert.assertEquals(
            NameError.trim(),
            "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space",
            "❌ Name error message is incorrect!"
        );
        
        Thread.sleep(5000);

        // Invalid Phone Number
        page.keyboard().press("Tab");
        form.MobileTextField().fill("900ab");
        Locator PhoneerrorMessage = page.locator("div[class='text-danger']").nth(1);
        String PhoneError = PhoneerrorMessage.textContent();
        System.out.println(PhoneError);
        Assert.assertEquals(
            PhoneError.trim(),
            "Invalid Mobile number, must be exactly 10 digits",
            "❌ Phone error message is incorrect!"
        );

        // Invalid Email
        page.keyboard().press("Tab");
        form.emailTextfield().fill("abckkkk");
        Locator EmailErrorMessage = page.locator("div[class='text-danger']").nth(2);
        String MailError = EmailErrorMessage.textContent();
        System.out.println(MailError);
        Assert.assertEquals(
            MailError.trim(),
            "Invalid email address",
            "❌ Email error message is incorrect!"
        );

        form.shortListButton().click();
        log.info("Filled shortlist form details and validated all error messages.");

        // Cleanup
        page.close();
    }
}
