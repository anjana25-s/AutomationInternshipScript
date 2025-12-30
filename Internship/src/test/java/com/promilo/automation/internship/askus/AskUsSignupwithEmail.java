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
import com.promilo.automation.internship.assignment.SignUpUtility;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;

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
        String mobile=SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();  // → always 9999
        System.out.println("Generated Email: " + email);

        // Enter email
        signup.enterEmailOrPhone(email);
       
        // Request OTP
        signup.clickVerificationCode();
       
       
        // Enter fixed OTP 9999
        signup.enterEmailOtp(otp);
      
       // Enter Password
        signup.enterPassword("Testautomation123");

        // Final signup
        signup.clickFinalSignupButton();

        // Validate signup success
        signup.isSignupSuccess();


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

        // -------------------------
        // ASK US PAGE VALIDATIONS
        // -------------------------
        assertTrue(
                validation.askUsHeaderText().isVisible(),
                "❌ Ask Us header text is not visible"
        );

        assertEquals(
                validation.askUsDescription().textContent().trim(),
                "Ask Us Anything for FreeGet personalized responses tailored to your career needs.Learn & ConnectGain insights from industry experts and engage with a dynamic community of professionals and peers at Promilo.com.",
                "❌ Ask Us description text mismatch"
        );

        assertTrue(
                validation.askUsFooterText().isVisible(),
                "❌ Ask Us footer text is not visible"
        );
        askus.clickOnButton();


        // ------------------------
        // OTP CALLBACK VALIDATION
        // ------------------------
        CallbackPage callback = new CallbackPage(page);
        callback.enterNumber(otp);
      
        page.waitForSelector("//h5[text()='OTP Verification']");

        Assert.assertTrue(
            validation.otpHeader().isVisible(),
            "❌ OTP header is not visible"
        );

        Assert.assertEquals(
            validation.otpHeader().innerText().trim(),
            "OTP Verification",
            "❌ OTP header text mismatch"
        );

        Assert.assertTrue(
            validation.otpDescription().isVisible(),
            "❌ OTP description is not visible"
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

