package com.promilo.automation.mentorship.mentee;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;

public class FeedbackAutomation extends Baseclass{
	
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
	        
	        
	        DescriptionPage descriptionValidation= new DescriptionPage(page);
	        descriptionValidation.feedbackTextfield().fill("something  vhbnmhb jnmhhjn dcihn v hjb nb nvjhb n");
	        descriptionValidation.submitButton().click();
	        
	        MentorshipFormComponents fillForm= new MentorshipFormComponents(page);
	        fillForm.name().fill("karthik feedback");
	        fillForm.MobileTextField().fill("9000098657");
	        fillForm.emailTextfield().fill("testuser0865456@gmail.com");
	        page.getByRole(AriaRole.DIALOG).getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Submit")).click();

	        //fillForm.askUsSubmit().click();
	        
	        
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
	        
	        System.out.println(page.getByText("Congratulations! You did it.").textContent()); 
	        System.out.println(page.getByText("Don't forget to click the").textContent()); 
	        
	        
	        
	    }
	

}
