package com.promilo.automation.internship.NotifyInternships;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.NotifyInternshipsDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class NotifyInternshipsSignupwithEmail extends Baseclass {

    @Test
    public void NotifyInternshipsEmail() throws InterruptedException {

        // -------------------------------
        // 1️⃣ Initialize Signup Page
        // -------------------------------
        SignupPage signup = new SignupPage(page);

        // Close the "Maybe Later" popup
        signup.clickMaybeLater();

        // Click the SIGN UP button
        signup.clickInitialSignupButton(); 

        // -------------------------------
        // 2️⃣ Generate Random Email
        // -------------------------------
        String email = SignUpUtility.generateRandomEmail();
        System.out.println("Generated Random Email → " + email);
        String mobile=SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();  // → always 9999

        // Enter random email into signup field
        signup.enterEmailOrPhone(email);

        // Click on Send Verification Code
        signup.clickVerificationCode();

        // Enter OTP (fixed 9999)
        signup.enterEmailOtp(otp);

        // Enter password
        signup.enterPassword("Testautomation123");

        // Click the final Signup button
        signup.clickFinalSignupButton();

        // Validate signup success
        signup.isSignupSuccess();


        // -------------------------------
        // 3️⃣ Navigate to Internships
        // -------------------------------
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");


        // -------------------------------
        // 4️⃣ Select an Internship Card
        // -------------------------------
        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();


        // -------------------------------
        // 5️⃣ Notify Internship Flow
        // -------------------------------
        NotifyInternshipsPage notify = new NotifyInternshipsPage(page);
        closeMilliIfVisible();
        notify.clickOnNotify();
        
        // ===================== DATA VALIDATION: NOTIFY POPUP =====================
        NotifyInternshipsDataValidation data = new NotifyInternshipsDataValidation(page);

        // Wait for header to appear
        Locator header = data.headerText();
        header.waitFor();

        assertTrue(data.headerText().textContent().trim()
        		.contains("Get similar internship alerts? Connect with Top companies to crack your dream internships."));
        
        		assertEquals(data.registerDescriptionText().textContent().trim(),
        		"Why Register for Personalized Internship Recommendations?Discover Tailored Opportunities: Get internship recommendations that align with your skills and career goals.Real-Time Internship Alerts: Be notified instantly about internships matching your profile and interests.Direct Connection to Recruiters: Increase your chances of being shortlisted for exciting opportunities.Exclusive Career Insights: Unlock resources to enhance your application, interview skills, and professional growth.Privacy Matters: Enjoy a secure platform with no unsolicited communication or spam.Discover Tailored Opportunities: Get internship recommendations that align with your skills and career goals.Real-Time Internship Alerts: Be notified instantly about internships matching your profile and interests.Direct Connection to Recruiters: Increase your chances of being shortlisted for exciting opportunities.Exclusive Career Insights: Unlock resources to enhance your application, interview skills, and professional growth.Privacy Matters: Enjoy a secure platform with no unsolicited communication or spam.PreviousNext");
        		assertEquals(data.whatsappNotificationText().textContent().trim(),
        		"Enable updates & important information on Whatsapp");
        		
        		assertEquals(data.agreeText().textContent().trim(),
        		"By proceeding ahead you expressly agree to the Promilo");

      System.out.println("✅ Notify popup static texts validated successfully.");

        notify.enterUserName("Kalyan");
        notify.enterNumber(mobile); 
        notify.button();
        notify.enterOtp(otp);
        
        // ===================== DATA VALIDATION: OTP PAGE =====================
        assertEquals(data.otpPageDescription().textContent().trim(),
        		"Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.");

        assertEquals(data.otpThanksText().textContent().trim(), "Thanks for giving your Information!");
        
        assertEquals(data.otpVerificationHeader().textContent().trim(), "OTP Verification");
        
        assertTrue(data.otpSentText().textContent().trim()
        .contains("Enter the 4-digit verification code we just sent you to"));
        
        assertTrue(data.otpCantFindText().textContent().trim().contains("Still can’t find the OTP"));


        notify.clickSubmitButton();
        
       // -------------------------------
        // 6️⃣ Validate Thank You Popup
        // -------------------------------
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        String actualMessage = thankYou.successPopup().textContent().trim();

        System.out.println("Success message displayed: " + actualMessage);
        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");
    }
}