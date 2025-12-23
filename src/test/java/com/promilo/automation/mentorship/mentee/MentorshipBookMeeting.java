package com.promilo.automation.mentorship.mentee;

import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.pagepbjects.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;

public class MentorshipBookMeeting extends Baseclass{
	
	 private static final Logger log = LogManager.getLogger(ShortListWithExistingPhoneAndEmail.class);
	 
	 
	 


	    @Test
	    public void mentorshipbook() throws IOException, InterruptedException {

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
	        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
	        log.info("Clicked on Mentorship module");

	        // Search for mentor
	        MeetupsListingPage searchPage = new MeetupsListingPage(page);
	        searchPage.SearchTextField().click();
	        searchPage.SearchTextField().fill("karthik");
	        page.keyboard().press("Enter");
	        page.waitForTimeout(2000);
	        
	        
	        
	        
	        DescriptionPage GetMentorCall= new DescriptionPage(page);
	        GetMentorCall.allLink().click();
	        GetMentorCall.videoCallLink().click();
	        Thread.sleep(3000);
	        
	        Locator bookMeeting =page.locator("//button[text()='Book Online Meeting']").nth(1);
	        bookMeeting.scrollIntoViewIfNeeded();
	        bookMeeting.click();
	        
	        MentorshipFormComponents fillForm= new MentorshipFormComponents(page);
	        fillForm.name().fill("karthik");
	        // random phone + email
	        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
	        String serverId = "8mgfvj1x";
	        int randomNum = 10000 + new Random().nextInt(90000);
	        String randomEmail = "VideoCall" + randomNum + "@" + serverId + ".mailosaur.net";

	        page.locator("//input[@id='userEmail']").nth(1).fill(randomEmail);
	        page.locator("//input[@name='userMobile']").nth(1).fill(randomPhone);

	        Baseclass.generatedEmail = randomEmail;
	        Baseclass.generatedPhone = randomPhone;
	        fillForm.getMentorCall().click();
	        
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
	        page.locator("//span[@class='flatpickr-next-month']").click();
	        page.locator("//span[contains(text(),'Slots')]").first().click();	 
	        page.locator("//li[@class='time-slot-box list-group-item']").first().click();
	        
	        
	        fillForm.nextButton().click();
	        
	       DescriptionPage clickGetHr= new DescriptionPage(page);
	        
	        fillForm.InvoiceNameField().fill("Karthik");
	        fillForm.StreetAdress1().fill("Dasarahalli");
	        fillForm.StreetAdress2().fill("Bangalore");
	        fillForm.pinCode().fill("560057");
	        fillForm.yesRadrioBox().click();
	        fillForm.gstNumber().fill("22AAAAA9999A1Z5");
	        fillForm.panNumber().fill("AAAAA9999A");
	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
	        
	        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
	        paymentFunctionality.payOnline().click();
	        paymentFunctionality.payButton().click();

	        page.waitForTimeout(5000);

	        // Switch to iframe for payment
	        FrameLocator frame = page.frameLocator("iframe");

	        // Fill contact number
	        frame.getByTestId("contactNumber").fill("9000098765");

	        // Select "Wallet" option from sidebar
	        frame.getByTestId("nav-sidebar")
	            .locator("div")
	            .filter(new Locator.FilterOptions().setHasText("Wallet"))
	            .nth(2)
	            .click();
	        
	        page.waitForTimeout(3000);
	        
	        Page page8 = page.waitForPopup(() -> {
	               page.locator("iframe").contentFrame().getByTestId("screen-container").locator("div").filter(new Locator.FilterOptions().setHasText("PhonePe")).nth(2).click();
	               
	               
	           });
	           page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
	           
	           
	           page.waitForTimeout(8000);
	           

	           // -------------------- Validation --------------------
	           Locator thankYouPopup = page.locator(
	                   "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	           thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
	           Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");

	           page.waitForTimeout(3000);
	           
	           MentorshipMyintrest myintrest= new MentorshipMyintrest(page);
	           ThankYouPopup thankYou = new ThankYouPopup(page);
	           // Verify that the Thank You popup messages are displayed
	          // System.out.println(page.getByText("Thank you for registering and").textContent()); 
	           Thread.sleep(1000);
	           
	           page.getByRole(AriaRole.DIALOG).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest")).click();
	           
	           myintrest.cardImage().isVisible();
	          System.out.println(myintrest.meetingDate().textContent()); 
	          System.out.println(myintrest.meetingTime().textContent()); 
	          System.out.println(myintrest.statusTag().textContent()); 

	           myintrest.serviceIcon().isVisible();
	           myintrest.serviceName().isVisible();
	        // Click on View Profile
	           myintrest.sendReminder().click();
	           log.info("Clicked on send remainder");

	           System.out.println(page.getByText("Reminder has been").textContent()); 
	           page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Sign Out")).click();
	         
	          	      
	           
	         
	        
	        	        
	        
	        
	    }

}
