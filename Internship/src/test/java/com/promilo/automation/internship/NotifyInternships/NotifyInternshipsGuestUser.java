package com.promilo.automation.internship.NotifyInternships;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.pageobjects.NotifyInternshipsDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class NotifyInternshipsGuestUser extends Baseclass {

    @Test
    public void SimilarInternships() throws InterruptedException {

        // ===================== INITIAL SETUP =====================
        page.locator("//button[text()='May be later!']").click();

        // Navigate to internships and open notify popup
        NotifyInternshipsPage notify = new NotifyInternshipsPage(page);
        notify.clickInternshipsTab();
        notify.clickBankManagerCard();
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

        // ===================== USER DATA ENTRY =====================
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();

        System.out.println("Generated Email: " + email);
        System.out.println("Generated Mobile: " + mobile);

        notify.typeUserName("Kamal");
        notify.enterPhoneNumber(mobile);
        notify.typeemail(email);
        notify.button();

        // ===================== DATA VALIDATION: OTP PAGE =====================
        assertEquals(data.otpPageDescription().textContent().trim(),
        		"Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.");

        assertEquals(data.otpThanksText().textContent().trim(), "Thanks for giving your Information!");
        
        assertEquals(data.otpVerificationHeader().textContent().trim(), "OTP Verification");
        
        assertTrue(data.otpSentText().textContent().trim()
        .contains("Enter the 4-digit verification code we just sent you to"));
        
        assertTrue(data.otpCantFindText().textContent().trim().contains("Still can’t find the OTP"));


        // Enter OTP
        notify.enterOtp(otp);
        notify.clickSubmitButton();

        // Wait for OTP Thanks message
        Locator thanksText = data.otpThanksText();
        thanksText.waitFor();

        Assert.assertTrue(
                thanksText.textContent().contains("Thanks"),
                "❌ OTP success text mismatch"
        );

        System.out.println("✅ OTP validation passed successfully.");

        // ===================== THANK YOU POPUP VALIDATION =====================
        Locator thankYouPopup = data.thankYouPopup();
        thankYouPopup.waitFor();

        assertEquals(data.thankYouMessageText().textContent().trim(),
        		"Thank You!Thank you for registering and requesting a similar internships alert. Check your email, notifications, and WhatsApp for the latest recommendations.Build, update, or upload your resume to get an interview approved and help the recruiter get to know you better.Build ResumeUpload Resume");
          System.out.println("Notify Internships data validations passed successfully!");
    }
}
