package com.promilo.automation.mentorship.mentee;

import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserGetMentorCall extends Baseclass {
	
	
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test
    public void applyForJobWithInvalidData() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("❌ Apply for Job Invalid OTP | Hardcoded Test");

        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);
        
        
        
        
        
        
        
        
        
        
        
	        // -------------------- Mentorship Module --------------------
	        HomePage dashboard = new HomePage(page);
	        Thread.sleep(2000);
	        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

	        // Search for mentor
	        MeetupsListingPage searchPage = new MeetupsListingPage(page);
	        searchPage.SearchTextField().click();
	        searchPage.SearchTextField().fill("karthik");
	        page.keyboard().press("Enter");
	        page.waitForTimeout(2000);
	        
	        DescriptionPage GetMentorCall= new DescriptionPage(page);
	        GetMentorCall.allLink().click();
	        GetMentorCall.getMentorCall().click();
	        
	        MentorshipFormComponents fillForm= new MentorshipFormComponents(page);
	        page.locator("//input[@name='userName']").fill("karthik");
	     // Generate phone number starting with 90000 + 5 random digits
	        String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
	        page.locator("//input[@name='userMobile']").fill(randomPhone);

	        
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
	           
	        
	        
	        
	        page.waitForTimeout(10000);
	        
	        // -------------------- Validation --------------------
	           Locator thankYouPopup = page.locator(
	                   "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	           thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
	           Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
	           
	           
	           MentorshipMyintrest myintrest= new MentorshipMyintrest(page);
	           ThankYouPopup thankYou = new ThankYouPopup(page);
	           
	           thankYou.myPreference().click();
	           
	           
	           page.waitForTimeout(3000);
	           
	           
	           
	           
	           
	           MentorshipMyintrest myintrest1= new MentorshipMyintrest(page);
	           myintrest1.cardImage().isVisible();
	           myintrest1.mentorName().textContent();
	           myintrest1.mentorData().textContent();
	           myintrest1.experianceString().textContent();
	           myintrest1.experianceValue().textContent();
	           myintrest1.locationString().textContent();
	           myintrest1.locationValue().textContent();
	           
	           
	           
	           myintrest1.serviceIcon().isVisible();
	           myintrest1.serviceName().isVisible();
	           
	           
	        // Click on View Profile
	           myintrest1.viewProfile().click();

	           // Get the current URL
	           String currentUrl = page.url();
	           System.out.println("Current URL after clicking View Profile:" + currentUrl);

	           Assert.assertTrue(currentUrl.contains("meetups-description"), "❌ URL does not contain expected 'profile' path");
	           
	        
	        
	        
	        
	    }

        
        
        
        
    }
        



