package com.promilo.automation.internship.askus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;			 	
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignUpUtility;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;

import basetest.Baseclass;

public class AskUsGuestUser extends Baseclass {

    @Test
    public void AskUsGuestUser() throws InterruptedException {

        // -------------------------
        // Close Initial Popup
        // -------------------------
        page.locator("//button[text()='May be later!']").click();

        // -------------------------
        // Navigation Flow
        // -------------------------
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

        AskUsPage askUsPage = new AskUsPage(page);
        askUsPage.clickAskUs();

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

        // -------------------------
        // FORM SUBMISSION
        // -------------------------
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();

        askUsPage.typeUserName("Ammu");
        askUsPage.typenumber(mobile);
        askUsPage.typeEmail(email);
        askUsPage.enterQuery("Good evening");
        askUsPage.clickOnButton();

     // ===================== OTP PAGE VALIDATION =====================
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


        // Enter OTP
        askUsPage.enterOtp(otp);
        askUsPage.clickVerify();

     // ================= THANK YOU POPUP VALIDATION =================

     // Wait for Thank You header
     page.waitForSelector( ".ThankYouPopup-Modal .headerText",
         new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE)
     );

     // Visibility check
     Assert.assertTrue(
    		 validation.thankYouHeader().isVisible(),
         "❌ Thank You popup is not displayed"
     );

     
  // -------------------- FINAL SUCCESS MESSAGE --------------------
     System.out.println("✅ Test data validation passed successfully");

}}