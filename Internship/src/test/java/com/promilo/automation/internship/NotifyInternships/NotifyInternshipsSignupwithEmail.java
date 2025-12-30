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
        notify.closeMilli();
        notify.clickOnNotify();
        notify.enterUserName("Kalyan");
        notify.enterNumber(mobile); 
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

        notify.button();
        notify.enterOtp(otp);
        
     // ===================== DATA VALIDATION: OTP PAGE =====================
        Assert.assertTrue(
                data.otpPageDescription().isVisible(),
                "❌ OTP page description missing"
        );

        Assert.assertTrue(
                data.otpVerificationHeader().isVisible(),
                "❌ OTP verification header missing"
        );
        
        // Wait for OTP Thanks message
        Locator thanksText = data.otpThanksText();
        thanksText.waitFor();

        Assert.assertTrue(
                thanksText.textContent().contains("Thanks"),
                "❌ OTP success text mismatch"
        );

        System.out.println("✅ OTP validation passed successfully.");
       
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