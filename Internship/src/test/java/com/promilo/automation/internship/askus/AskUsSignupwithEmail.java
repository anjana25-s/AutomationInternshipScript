package com.promilo.automation.internship.askus;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;
import com.promilo.automation.internship.utilities.TestAccountStore;

import basetest.Baseclass;

public class AskUsSignupwithEmail extends Baseclass {

    @Test
    public void AskUs1Test() throws InterruptedException {

        // ------------------------
        // SIGNUP
        // ------------------------
        SignupPage signup = new SignupPage(page);
        signup.clickMaybeLater();
        signup.clickInitialSignupButton();
        

        // Generate a random email & OTP
        String email = SignUpUtility.generateRandomEmail();
        String otp = SignUpUtility.getFixedOtp();;
        String password=SignUpUtility.generateRandomPassword();
        String mobile=SignUpUtility.generateRandomMobile();
       
         signup.enterEmailOrPhone(email);
        signup.clickVerificationCode();
        signup.enterEmailOtp(otp);
        signup.enterPassword(password);
        signup.clickFinalSignupButton();
        signup.isSignupSuccess();

        TestAccountStore.save(email, password);
         // ------------------------
        // NAVIGATE TO INTERNSHIP
        // ------------------------
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();


        // ------------------------
        // ASK US FORM
        // ------------------------
        AskUsPage askus = new AskUsPage(page);
        askus.clickAskUs();
        askus.enterName("Arya");
        askus.enterNumber(mobile);
        askus.enterQuery("Good evening");
        
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


        // ------------------------
        // OTP CALLBACK VALIDATION
        // ------------------------
        CallbackPage callback = new CallbackPage(page);
        callback.enterNumber(otp);
      
        assertEquals(
        		validation.otpPageDescription().textContent().trim(),
        		"Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams."
        		);
        assertEquals(
        		validation.otpSuccessText().textContent().trim(),
        		"Thanks for giving your Information!"
        		);

        		assertEquals(
        				validation.otpHeader().textContent().trim(),
        		"OTP Verification"
        		);

        		assertTrue(
        				validation.otpDescription().textContent().trim()
        		.contains("Enter the 4-digit verification code we just sent you to")
        		);

        		assertTrue(
        				validation.otpStillCantFind().textContent().trim()
        		.contains("Still can’t find the OTP")
        		);
        		
        callback.clickSubmitButton();


        // ------------------------
        // SUCCESS MESSAGE VALIDATION
        // ------------------------
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        String actualMessage = thankYou.successPopup().textContent().trim();
        System.out.println("Success message displayed: " + actualMessage);

        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");

    }
}

