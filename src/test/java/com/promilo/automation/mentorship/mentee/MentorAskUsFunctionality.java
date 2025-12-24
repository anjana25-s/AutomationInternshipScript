package com.promilo.automation.mentorship.mentee;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
<<<<<<< HEAD
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.pagepbjects.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;

public class MentorAskUsFunctionality extends Baseclass{
	

    private static final Logger log = LogManager.getLogger(MentorshipShortListFunctionality.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException {

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

public class MentorAskUsFunctionality extends BaseClass{
	

    private static final Logger log = LogManager.getLogger(MentorshipShortListFunctionality.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException {

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
        searchPage.SearchTextField().fill("karthik");
        
        DescriptionPage askUs= new DescriptionPage(page);
        askUs.askUs().click();
        
        
        // -------------------- Fill form details --------------------
        MentorshipFormComponents form = new MentorshipFormComponents(page);
        form.name().fill("karthik");

        // Hardcoded phone and email
        form.MobileTextField().fill("9000090105");
        form.emailTextfield().fill("testuser9003456@gmail.com");
        
        form.askUsTextarea().fill("Karthik");
        form.askUsSubmit().click();
        log.info("Filled shortlist form details");

        // -------------------- OTP Handling --------------------
        String otp = "9999";
        if (otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 digits: " + otp);
        }

        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            for (int attempt = 1; attempt <= 3 && !filled; attempt++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
            }
        }

        page.locator("//button[text()='Verify & Proceed']").click();

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
        
        ThankYouPopup thankyou= new ThankYouPopup(page);
       System.out.println(thankyou.askUsFooterSection().textContent()); 
        System.out.println(thankyou.AskusCongratulations().textContent());
       
        

}
    
}
