package com.promilo.automation.internship.NotifyInternships;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.NotifyInternshipsDataValidation;
import com.promilo.automation.internship.assignment.SignUpUtility;


import basetest.Baseclass;

public class NotifyInternshipsSignupwithMobile extends Baseclass {

    @Test
    public void NotifyInternshipsMobile() throws InterruptedException {

        // -------------------------------
        // 1️⃣ Initialize Signup Page
        // -------------------------------
        SignupPage signup = new SignupPage(page);

        // Close the "Maybe Later" popup
        signup.clickMaybeLater();

        // Click the SIGN UP button
        
        signup.clickInitialSignupButton();


        // -------------------------------
        // 2️⃣ Generate Random Mobile Number
        // -------------------------------
        String Mobile = SignUpUtility.generateRandomMobile();
        System.out.println("Generated Random Mobile → " + Mobile);
        String email = SignUpUtility.generateRandomEmail();
        String otp = SignUpUtility.getFixedOtp();  // → always 9999


        // Enter random mobile number
        signup.enterEmailOrPhone(Mobile);

        // Click Send Verification Code
        signup.clickVerificationCode();

        // Enter OTP (fixed 9999 from utility)
       signup.enterMobileOtp(otp);

        // Enter password
        signup.enterPassword("Testanju12");

        // Complete signup
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
        // 4️⃣ Select Internship Card
        // -------------------------------
        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();


        // -------------------------------
        // 5️⃣ Notify Internship Flow
        // -------------------------------
        NotifyInternshipsPage notify = new NotifyInternshipsPage(page);
        notify.closeMilli();
        notify.clickOnNotify();
        
        // ===================== DATA VALIDATION: NOTIFY POPUP =====================
        NotifyInternshipsDataValidation data = new NotifyInternshipsDataValidation(page);

        // Wait for header to appear
        Locator header = data.headerText();
        header.waitFor();

        Assert.assertTrue(
                header.isVisible(),
                "❌ Notify popup header is missing"
        );
        System.out.println("✅ Header text visible: " + header.textContent());

        Assert.assertTrue(
                data.registerDescriptionText().isVisible(),
                "❌ Register description text is missing"
        );

        Assert.assertTrue(
                data.whatsappNotificationText().isVisible(),
                "❌ Whatsapp notification label is missing"
        );

        Assert.assertTrue(
                data.agreeText().isVisible(),
                "❌ Agreement text is missing"
        );

        System.out.println("✅ Notify popup static texts validated successfully.");

        notify.enterUserName("Kalyan");
        notify.enterEmail(email); 
        notify.button();

        // -------------------------------
        // 6️⃣ Validate Thank You Popup
        // -------------------------------
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        String actualMessage = thankYou.successPopup().textContent().trim();

        System.out.println("Success message displayed: " + actualMessage);
        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");
    }
}
