package com.promilo.automation.internship.askus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class AskUsSignupwithMobile extends Baseclass {

    @Test
    public void AskUs2Test() throws InterruptedException {

        SignupPage signup = new SignupPage(page);

        // Step 1: Popup
        signup.clickMaybeLater();

        // Step 2: Sign Up
        signup.clickInitialSignupButton();

        // Step 3: Generate random mobile
        String mobileNumber = SignUpUtility.generateRandomMobile();
        System.out.println("Generated Mobile: " + mobileNumber);
        String otp = SignUpUtility.getFixedOtp();  // → always 9999
        String email = SignUpUtility.generateRandomEmail();

        signup.enterEmailOrPhone(mobileNumber);

        // Step 4: OTP flow
        signup.clickVerificationCode();
        
     // Enter fixed OTP 9999
      signup.enterMobileOtp(otp);
        
        // Step 5: Password
        signup.enterPassword("Testautomation123");

        // Step 6: Final Sign Up
        
        signup.clickFinalSignupButton();
        signup.isSignupSuccess();

        // Step 7: Internship navigation
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

        // Step 8: AskUs form
        AskUsPage askus = new AskUsPage(page);
        askus.clickAskUs();
        askus.enterName("Sree");
        askus.enterMail(email);
        askus.enterQuery("Good evening");
        
        // -------------------------
        // ASK US PAGE VALIDATIONS
        // -------------------------
        AskusDataValidation validation = new AskusDataValidation(page);

        assertEquals(
        		validation.askUsDescription().textContent().trim(),
        		"Ask Us Anything for FreeGet personalized responses tailored to your career needs.Learn & ConnectGain insights from industry experts and engage with a dynamic community of professionals and peers at Promilo.com."
        		);

        		assertEquals(
        				validation.askUsHeaderText().textContent().trim(),
        		"Share your query to get help!"
        		);

        		assertEquals(
        				validation.askUsFooterText().textContent().trim(),
        		"By proceeding ahead you expressly agree to the PromiloTerms & Conditions"
        		);
             
        		askus.clickOnButton();

        // Step 9: Success message
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        String actualMessage = thankYou.successPopup().textContent().trim();

        System.out.println("Success message displayed: " + actualMessage);
        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");
    }
}